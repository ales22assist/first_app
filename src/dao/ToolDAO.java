package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import org.apache.log4j.Logger;

import connection_manager.DatabaseConnectionManager;

public class ToolDAO {
	
	private static final Logger LOGGER = Logger.getLogger(ToolDAO.class);

	public ToolDAO() {
	}

	public static void createInventoryTables(Statement statement) throws SQLException {

		statement.execute("CREATE TABLE IF NOT EXISTS public.inventory_kmenova(id SERIAL UNIQUE, "
				+ "tool_description TEXT COLLATE pg_catalog.\"default\", total_amount integer)");

		statement.execute("CREATE TABLE IF NOT EXISTS public.inventory_zmenova(id SERIAL PRIMARY KEY, "
				+ "tool_description text COLLATE pg_catalog.\"default\", " + "tool_amount_change integer NOT NULL, "
				+ "operation_successed BOOLEAN DEFAULT false)");

		statement.execute("CREATE TABLE IF NOT EXISTS public.inventory_protocol(id SERIAL PRIMARY KEY, "
				+ "tool_description text COLLATE pg_catalog.\"default\", " + "tool_amount_change integer NOT NULL, "
				+ "created_at timestamp(0) without time zone NOT NULL DEFAULT now(), "
				+ "updated_at timestamp(0) without time zone NOT NULL DEFAULT now(), "
				+ "operation_successed BOOLEAN DEFAULT false)");
	}

	public static String updateInventoryZmenova(int id) {

		return "UPDATE inventory_zmenova SET operation_successed = true WHERE id = " + id;
	}

	public static String updateInventoryKmenova(int id, int amountChanged, int actualAmmount) {

		int newAmount = actualAmmount + amountChanged;
		return ("UPDATE inventory_kmenova SET total_amount = " + newAmount + " WHERE id = " + id);
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
			LOGGER.debug("Id already present");
		}
	}

	public static void createChangeInInventoryZmenova(int toolId, String toolName, int changeAmount)
			throws SQLException {

		DatabaseConnectionManager databaseConnection = DatabaseConnectionManager.getManagerInstance();
		databaseConnection.connect();

		PreparedStatement saveNewTool = DatabaseConnectionManager.getManagerInstance().getConnectionObject()
				.prepareStatement("INSERT INTO inventory_zmenova VALUES(?,?,?) ");
		saveNewTool.setInt(1, toolId);
		saveNewTool.setString(2, toolName);
		saveNewTool.setInt(3, changeAmount);
		saveNewTool.executeUpdate();
	}

	public static void createChangeInInventoryProtocol(int toolId, String toolName, int changeAmount,
			boolean operationSucceded) throws SQLException {

		DatabaseConnectionManager databaseConnection = DatabaseConnectionManager.getManagerInstance();
		databaseConnection.connect();

		PreparedStatement saveNewTool = DatabaseConnectionManager.getManagerInstance().getConnectionObject()
				.prepareStatement("INSERT INTO inventory_protocol VALUES(?,?,?) ");
		saveNewTool.setInt(1, toolId);
		saveNewTool.setString(2, toolName);
		saveNewTool.setInt(3, changeAmount);
		saveNewTool.setBoolean(4, operationSucceded);
		saveNewTool.executeUpdate();
	}
	
	public static void makeChange(Connection connection) {
		try (ResultSet rsKmenovaInventory = connection.createStatement()
				.executeQuery("SELECT id, tool_description, total_amount FROM inventory_kmenova ORDER BY id");
				ResultSet rsZmenovaInventory = connection.createStatement().executeQuery(
						"SELECT id, tool_description, tool_amount_change, id FROM inventory_zmenova WHERE operation_status = false ");
				Statement statement = connection.createStatement();) {
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public static void displayDataKmenova() {
		try {
			Statement statement = DatabaseConnectionManager.getManagerInstance().getConnectionObject()
					.createStatement();
			String queryString = "select * from inventory_kmenova";
			ResultSet resultSet = statement.executeQuery(queryString);

			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				String tool = resultSet.getString("tool_description");
				Integer amount = resultSet.getInt("total_amount");
				System.out.println("|| id: " + Integer.toString(id) + " || tool_description: " + tool
						+ " || total_amount: " + amount.toString() + " || ");
			}

		} catch (SQLException sqlException) {
			int errCode = sqlException.getErrorCode();
			String SQLState = sqlException.getSQLState();
			System.out.println("ErrorCode: " + Integer.toString(errCode) + " SQLState " + SQLState);
		}
	}

	public static void displayDataZmenova() {
		try {
			Statement statement = DatabaseConnectionManager.getManagerInstance().getConnectionObject()
					.createStatement();
			String queryString = "select * from inventory_zmenova";
			ResultSet resultSet = statement.executeQuery(queryString);

			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				String tool = resultSet.getString("tool_description");
				Integer amount = resultSet.getInt("tool_amount_change");
				Boolean operationStatus = resultSet.getBoolean("operation_status");

				System.out.println(
						"|| id: " + Integer.toString(id) + " || tool_description: " + tool + " || tool_amount_change: "
								+ amount.toString() + " || " + "operation_status: " + operationStatus + " || ");
			}

		} catch (SQLException sqlException) {
			int errCode = sqlException.getErrorCode();
			String SQLState = sqlException.getSQLState();
			System.out.println("ErrorCode: " + Integer.toString(errCode) + " SQLState " + SQLState);
		}
	}

	public static void displayDataProtocol() {
		try {
			Statement statement = DatabaseConnectionManager.getManagerInstance().getConnectionObject()
					.createStatement();
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
						"|| id: " + Integer.toString(id) + " || tool_description: " + tool + " || tool_amount_change: "
								+ amount.toString() + " || " + "created at: " + tsTimestamp + " || " + "updated at: "
								+ updTimestamp + " || " + "operation_status: " + operationStatus + " || ");
			}

		} catch (SQLException sqlException) {
			int errCode = sqlException.getErrorCode();
			String SQLState = sqlException.getSQLState();
			System.out.println("ErrorCode: " + Integer.toString(errCode) + " SQLState " + SQLState);
		}
	}
}