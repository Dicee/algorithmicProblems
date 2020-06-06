package miscellaneous.skiller.indexation.model;

import static com.dici.exceptions.ExceptionUtils.uncheckExceptionsAndGet;
import static com.dici.exceptions.ExceptionUtils.withAutoCloseableResource;
import static com.dici.strings.StringUtils.join;
import static java.lang.String.format;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.function.Function;
import java.util.stream.Stream;

import com.dici.check.Check;
import com.dici.collection.ArrayUtils;
import com.dici.collection.StreamUtils;
import com.dici.exceptions.ExceptionUtils.ThrowingFunction;
import com.dici.strings.StringUtils;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class DBManager {
	public static final Properties connectionProp = new Properties();
	static {
		try {
			connectionProp.load(DBManager.class.getResourceAsStream("db.properties"));
			connect();
		} catch (IOException | SQLException e) {
			e.printStackTrace();
		}
	}
	
	private static Connection conn;
	
	private static void connect() throws SQLException {
		conn = DriverManager.getConnection(
			format("jdbc:mysql://%s:%s/",connectionProp.getProperty("server_name"),connectionProp.getProperty("port_number")),connectionProp);
		log.info("Connected to database");
	}
	
	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(
				format("jdbc:mysql://%s:%s/",connectionProp.getProperty("server_name"),connectionProp.getProperty("port_number")),connectionProp);
	}
	
	public static ResultSet executeQuery(String query) {
		return withStatement(stmt -> stmt.executeQuery(query));
	}
	
	public static Stream<ResultSet> executeQueryAndGetStream(String query) {
		return StreamUtils.iteratorToStream(iterate(executeQuery(query)));
	}

	public static void persist(Table table, Object... values) {
		withStatement(stmt -> stmt.executeUpdate(
			String.format("insert into %s.%s values (%s)",
				databaseName(),
				table.getName(),
				join(", ",DBManager::formatDataType,values))
			)
		);
	}
	
	public static void persist(Table table, String names[], Object[] values) {
		Check.areEqual(names.length,values.length,"The two parameter arrays should have the same length");
		withStatement(stmt -> stmt.executeUpdate(
			String.format("insert into %s.%s (%s) values (%s)",
				databaseName(),
				table.getName(),
				join(", ",names),
				join(", ",DBManager::formatDataType,values))
			)
		);
	}
	
	public static long newLongId(Table table) {
		Check.notBlank(table.getName());
		return withStatement(stmt -> {
			ResultSet rs = stmt.executeQuery(String.format("select id, max(id) as max from %s.%s",databaseName(),table.getName()));
			return rs.first() ? rs.getLong("max") + 1 : 0;
		});
	}
	
	private static String formatDataType(Object obj) {
		return (obj instanceof String) ? "'" + obj + "'" : obj.toString();
	}
	
	private static <T> T withStatement(ThrowingFunction<Statement,T> function) {
		return withAutoCloseableResource(conn::createStatement,function);
	}
	
	public static <T> T getSingleResult(String query, Function<ResultSet,T> mapper) {
		return executeQueryAndGetStream(query).map(mapper).findFirst().orElseThrow(NoSuchElementException::new);
	}
	
	public static String databaseName() { return connectionProp.getProperty("db_name"); }
	
	public static String prefixTablesByDatabaseName(String formattedQuery) { 
		int      count = StringUtils.count(formattedQuery, "%s");
		String[] args  = ArrayUtils.ofDim(String.class,count,databaseName());
		return String.format(formattedQuery,(Object[]) args);
	}
	
	private static Iterator<ResultSet> iterate(ResultSet resultSet) {
		return new Iterator<ResultSet>() {
			@Override
			public boolean hasNext() { return uncheckExceptionsAndGet(resultSet::isAfterLast); }

			@Override
			public ResultSet next() { 
				uncheckExceptionsAndGet(resultSet::next); 
				return resultSet;
			}
		};
	}
}	
