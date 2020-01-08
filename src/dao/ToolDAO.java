package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import connection_manager.DatabaseConnectionManager;

public class ToolDAO {

	public ToolDAO() {
	}
	
	public static void createInventoryTables(Statement statement) {
		
		try {
			statement.execute("CREATE TABLE inventory_kmenova(id INTEGER NOT NULL, "
															+ "tool_description TEXT COLLATE pg_catalog.\"default\", total_amount integer, "
															+ "created_at timestamp(0) without time zone NOT NULL DEFAULT now(), "
															+ "updated_at timestamp(0) without time zone NOT NULL DEFAULT now(),"
															+ "CONSTRAINT inventory_kmenova_pkey PRIMARY KEY (id))");
		} catch (SQLException exception) {
			// to be modified as>>>>  LOGGER debug "......."
		} 
		
		try {
			statement.execute("CREATE TABLE inventory_zmenova(id INTEGER NOT NULL, " 
															+ "tool_description text COLLATE pg_catalog.\"default\", "
															+ "tool_amount_change integer NOT NULL, "
															+ "created_at timestamp(0) without time zone NOT NULL DEFAULT now(), "
															+ "updated_at timestamp(0) without time zone NOT NULL DEFAULT now(), "
															+ "operation_successed BOOLEAN DEFAULT false, "
															+ "CONSTRAINT inventory_zmenova_pkey PRIMARY KEY (id))");
			
		} catch (SQLException exception) {
			// to be modified as>>>>  LOGGER debug "......."
		}	
	}

	public void saveNewToolToKmenova(int toolId, String toolName, int toolAmount) throws SQLException {
		try {
			DatabaseConnectionManager databaseConnection = DatabaseConnectionManager.getManagerInstance();
			databaseConnection.connect();
	
			PreparedStatement saveNewTool = DatabaseConnectionManager.getManagerInstance().getConnectionObject()
					.prepareStatement("INSERT INTO inventory VALUES(?,?,?)"); // INSERT INTO inventory (toolId, toolName, toolAmount) VALUES(1, "xxx", 19) ON DUPLICATE KEY UPDATE toolName="xxx", age=19  
			saveNewTool.setInt(1, toolId);
			saveNewTool.setString(2, toolName);
			saveNewTool.setInt(3, toolAmount);
			saveNewTool.executeUpdate();
			System.out.println("New tool successfully saved to database...");

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void displayDataKmenova() {
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