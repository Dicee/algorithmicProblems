package miscellaneous.skiller.indexation.model;

import java.sql.ResultSet;

public interface StatPublisher {
	public ResultSet getMostPopularWords(int minLength, int limit);
	public ResultSet getMostPopularQuestions();
	
	public int getUsersCount();
	public int getTotalContributions();
	public int getTotalWords(int minLength);

	public ResultSet getBiggestQuestioners();
	public ResultSet getBiggestAnswerers();
	public ResultSet getBiggestContributers();
	public int getUserNeverPostedQuestionCount();
	public int getUserNeverPostedAnswerCount();
	public int getUserNeverPostedAnythingCount();
	
	public int getSkillerRank(int minLength);
	public int getSkillerUsages();
	public int getFamilyRank(int minLength, String familyExpr);
	public int getFamilyUsages(String familyExpr);
}
