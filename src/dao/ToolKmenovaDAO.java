package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import database_connection_mng.DatabaseConnectionManager;

public class ToolKmenovaDAO {

	public ToolKmenovaDAO() {
	}

	public void saveNewToolToKmenova(int toolId, String toolName, int toolAmount) throws SQLException {
		try {
			DatabaseConnectionManager databaseConnection = DatabaseConnectionManager.getManagerInstance();
			databaseConnection.connect();

			PreparedStatement saveNewTool = databaseConnection.getManagerInstance().getConnectionObject()
					.prepareStatement("INSERT INTO inventory VALUES(?,?,?)");
			saveNewTool.setInt(1, toolId);
			saveNewTool.setString(2, toolName);
			saveNewTool.setInt(3, toolAmount);
			saveNewTool.executeUpdate();
			System.out.println("New tool successfully saved to database...");

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void displayDataKmenova() {
		try {
			Statement statement = DatabaseConnectionManager.getManagerInstance().getConnectionObject()
					.createStatement();
			String queryString = "select * from inventory";
			ResultSet resultSet = statement.executeQuery(queryString);

			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				String tool = resultSet.getString("tool");
				Integer amount = resultSet.getInt("total_amount");
				Timestamp tsTimestamp = resultSet.getTimestamp("created_at");
				Timestamp updTimestamp = resultSet.getTimestamp("updated_at");

				System.out.println(
						"|| id: " + Integer.toString(id) + " || tool: " + tool + " || amount: " + amount.toString()
								+ " || " + "created at: " + tsTimestamp + " || " + "updated at: " + updTimestamp);
			}

		} catch (SQLException sqlException) {
			int errCode = sqlException.getErrorCode();
			String SQLState = sqlException.getSQLState();
			System.out.println("ErrorCode: " + Integer.toString(errCode) + " SQLState " + SQLState);
		}
	}
}