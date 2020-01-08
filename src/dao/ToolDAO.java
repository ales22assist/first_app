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

	public static void createInventoryTables(Statement statement) throws SQLException {

		
			statement.execute("CREATE TABLE IF NOT EXISTS public.inventory_kmenova(id INTEGER NOT NULL, "
					+ "tool_description TEXT COLLATE pg_catalog.\"default\", total_amount integer, "
					+ "CONSTRAINT inventory_kmenova_pkey PRIMARY KEY (id))");
		
			statement.execute("CREATE TABLE IF NOT EXISTS public.inventory_zmenova(id INTEGER NOT NULL, "
					+ "tool_description text COLLATE pg_catalog.\"default\", " + "tool_amount_change integer NOT NULL, "
					+ "operation_successed BOOLEAN DEFAULT false, "
					+ "CONSTRAINT inventory_zmenova_pkey PRIMARY KEY (id))");

			statement.execute("CREATE TABLE IF NOT EXISTS public.inventory_protocol(id INTEGER NOT NULL, "
					+ "tool_description text COLLATE pg_catalog.\"default\", " + "tool_amount_change integer NOT NULL, "
					+ "created_at timestamp(0) without time zone NOT NULL DEFAULT now(), "
					+ "updated_at timestamp(0) without time zone NOT NULL DEFAULT now(), "
					+ "operation_successed BOOLEAN DEFAULT false, "
					+ "CONSTRAINT inventory_protocol_pkey PRIMARY KEY (id))");
			
	}

	public static void saveNewToolToKmenova(int toolId, String toolName, int toolAmount) throws SQLException {
		try {
			DatabaseConnectionManager databaseConnection = DatabaseConnectionManager.getManagerInstance();
			databaseConnection.connect();

			PreparedStatement saveNewTool = DatabaseConnectionManager.getManagerInstance().getConnectionObject()
					.prepareStatement("INSERT INTO inventory_kmenova VALUES(?,?,?) "); 															
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
			Statement statement = DatabaseConnectionManager.getManagerInstance().getConnectionObject().createStatement();
			String queryString = "select * from inventory_kmenova";
			ResultSet resultSet = statement.executeQuery(queryString);

			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				String tool = resultSet.getString("tool_description");
				Integer amount = resultSet.getInt("total_amount");
				System.out.println(
						"|| id: " + Integer.toString(id) + " || tool_description: " + tool + " || total_amount: " + amount.toString()
								+ " || ");
			}

		} catch (SQLException sqlException) {
			int errCode = sqlException.getErrorCode();
			String SQLState = sqlException.getSQLState();
			System.out.println("ErrorCode: " + Integer.toString(errCode) + " SQLState " + SQLState);
		}
	}
	
	public static void displayDataZmenova() {
		try {
			Statement statement = DatabaseConnectionManager.getManagerInstance().getConnectionObject().createStatement();
			String queryString = "select * from inventory_zmenova";
			ResultSet resultSet = statement.executeQuery(queryString);

			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				String tool = resultSet.getString("tool_description");
				Integer amount = resultSet.getInt("tool_amount_change");
				Boolean operationStatus = resultSet.getBoolean("operation_status");

				System.out.println(
						"|| id: " + Integer.toString(id) + " || tool_description: " + tool + " || tool_amount_change: " + amount.toString()
								+ " || " + "operation_status: " + operationStatus + " || ");
			}

		} catch (SQLException sqlException) {
			int errCode = sqlException.getErrorCode();
			String SQLState = sqlException.getSQLState();
			System.out.println("ErrorCode: " + Integer.toString(errCode) + " SQLState " + SQLState);
		}
	}

	public static void displayDataProtocol() {
		try {
			Statement statement = DatabaseConnectionManager.getManagerInstance().getConnectionObject().createStatement();
			String queryString = "select * from inventory_protocol";
			ResultSet resultSet = statement.executeQuery(queryString);

			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				String tool = resultSet.getString("tool_description");
				Integer amount = resultSet.getInt("tool_amount_change");
				Timestamp tsTimestamp = resultSet.getTimestamp("created_at");
				Timestamp updTimestamp = resultSet.getTimestamp("updated_at");
				Boolean operationStatus = resultSet.getBoolean("operation_status");

				System.out.println(
						"|| id: " + Integer.toString(id) + " || tool_description: " + tool + " || tool_amount_change: " + amount.toString()
								+ " || " + "created at: " + tsTimestamp + " || " + "updated at: " + updTimestamp + " || " + "operation_status: " + operationStatus + " || ");
			}

		} catch (SQLException sqlException) {
			int errCode = sqlException.getErrorCode();
			String SQLState = sqlException.getSQLState();
			System.out.println("ErrorCode: " + Integer.toString(errCode) + " SQLState " + SQLState);
		}
	}	
}