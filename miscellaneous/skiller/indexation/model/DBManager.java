package miscellaneous.skiller.indexation.model;

import static java.lang.String.format;
import static miscellaneous.utils.exceptions.IgnoreCheckedExceptions.ignoreCheckedExceptions;
import static miscellaneous.utils.exceptions.IgnoreCheckedExceptions.withAutoCloseableResource;
import static miscellaneous.utils.strings.StringUtils.join;

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

import miscellaneous.utils.collection.ArrayUtils;
import miscellaneous.utils.collection.StreamUtils;
import miscellaneous.utils.exceptions.IgnoreCheckedExceptions.ThrowingFunction;
import miscellaneous.utils.strings.StringUtils;

public class DBManager {
	public static final Properties connectionProp = new Properties();
	static {
		try {
			connectionProp.load(DBManager.class.getResourceAsStream("db.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static Connection conn;
	
	public static void connect() throws SQLException {
		conn = DriverManager.getConnection(
			format("jdbc:mysql://%s:%s/",connectionProp.getProperty("server_name"),connectionProp.getProperty("port_number")),connectionProp);
		System.out.println("Connected to database");
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

	public static void persist(String table, Object... values) {
		withStatement(stmt -> stmt.executeUpdate(
			String.format("insert into %s.%s values (%s)",
				databaseName(),
				table,
				join(", ",DBManager::formatDataType,values))
			)
		);
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
	
	public static String databaseName() { return connectionProp.getProperty("db.name"); }
	
	public static String prefixTablesByDatabaseName(String formattedQuery) { 
		int      count = StringUtils.count(formattedQuery, "%s");
		String[] args  = ArrayUtils.ofDim(String.class,count,databaseName());
		return String.format(formattedQuery,(Object[]) args);
	}
	
	private static Iterator<ResultSet> iterate(ResultSet resultSet) {
		return new Iterator<ResultSet>() {
			@Override
			public boolean hasNext() { return ignoreCheckedExceptions(resultSet::isAfterLast); }

			@Override
			public ResultSet next() { 
				ignoreCheckedExceptions(resultSet::next); 
				return resultSet;
			}
		};
	}
}	
