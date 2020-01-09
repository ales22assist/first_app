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

	public static final Logger LOGGER = Logger.getLogger(ToolDAO.class);

	public ToolDAO() {
	}

	public static void createInventoryTables(Statement statement) throws SQLException {

		statement.execute("CREATE TABLE IF NOT EXISTS public.inventory_kmenova(id SERIAL UNIQUE, "
				+ "tool_description TEXT COLLATE pg_catalog.\"default\", total_amount integer)");

		statement.execute(
				"CREATE TABLE IF NOT EXISTS public.inventory_zmenova(id SERIAL PRIMARY KEY, tool_id serial NOT NULL, "
						+ "tool_description text COLLATE pg_catalog.\"default\", "
						+ "tool_amount_change integer NOT NULL, " + "operation_successed BOOLEAN DEFAULT false)");

		statement.execute(
				"CREATE TABLE IF NOT EXISTS public.inventory_protocol(id SERIAL PRIMARY KEY, tool_id serial NOT NULL, "
						+ "tool_description text COLLATE pg_catalog.\"default\", "
						+ "tool_amount_change integer NOT NULL, " + "operation_successed BOOLEAN DEFAULT false, "
						+ "created_at timestamp(0) without time zone NOT NULL DEFAULT now(), "
						+ "updated_at timestamp(0) without time zone NOT NULL DEFAULT now()) ");
	}

	public static String updateInventoryZmenova(int id) {

		return "UPDATE inventory_zmenova SET operation_successed = true WHERE id = " + id;
	}

	public static String updateInventoryKmenova(int id, int amountChanged, int actualAmmount) {

		return ("UPDATE inventory_kmenova SET total_amount = " + (actualAmmount + amountChanged) + " WHERE id = " + id);
	}

	public static String saveNewToolToKmenova(int toolId, String toolName, int toolAmount) throws SQLException {

		DatabaseConnectionManager databaseConnection = DatabaseConnectionManager.getManagerInstance();
		databaseConnection.connect();
		PreparedStatement saveNewTool = DatabaseConnectionManager.getManagerInstance().getConnectionObject()
				.prepareStatement("INSERT INTO inventory_kmenova(id, tool_description, total_amount) VALUES(?,?,?) ");
		saveNewTool.setInt(1, toolId);
		saveNewTool.setString(2, toolName);
		saveNewTool.setInt(3, toolAmount);

		return saveNewTool.toString();
	}

	public static String createChangeInInventoryZmenova(int toolId, String toolName, int changeAmount)
			throws SQLException {

		DatabaseConnectionManager databaseConnection = DatabaseConnectionManager.getManagerInstance();
		databaseConnection.connect();
		PreparedStatement saveNewTool = DatabaseConnectionManager.getManagerInstance().getConnectionObject()
				.prepareStatement(
						"INSERT INTO inventory_zmenova(tool_id, tool_description, tool_amount_change) VALUES(?,?,?) ");
		saveNewTool.setInt(1, toolId);
		saveNewTool.setString(2, toolName);
		saveNewTool.setInt(3, changeAmount);

		return saveNewTool.toString();
	}

	public static String createChangeInInventoryProtocol(int toolId, int amountChange, boolean operationSucess,
			String toolName) throws SQLException {
		DatabaseConnectionManager databaseConnection = DatabaseConnectionManager.getManagerInstance();
		databaseConnection.connect();

		PreparedStatement saveNewTool = DatabaseConnectionManager.getManagerInstance().getConnectionObject()
				.prepareStatement(
						"INSERT INTO inventory_protocol(tool_id, tool_amount_change, operation_successed, tool_description) VALUES(?,?,?,?) ");
		saveNewTool.setInt(1, toolId);
		saveNewTool.setInt(2, amountChange);
		saveNewTool.setBoolean(3, operationSucess);
		saveNewTool.setString(4, toolName);

		return saveNewTool.toString();
	}

	public static void makeChange(Connection connection) {

		try (ResultSet rsZmenovaInventory = connection.createStatement().executeQuery(
				"SELECT tool_id, tool_description, tool_amount_change, id FROM inventory_zmenova WHERE operation_successed = false ");
				

				Statement statement = connection.createStatement();) {

			while (rsZmenovaInventory.next()) {
				boolean condition = true;
				try (ResultSet rsKmenovaInventory = connection.createStatement()
						.executeQuery("SELECT id, tool_description, total_amount FROM inventory_kmenova ORDER BY id");){

					while (rsKmenovaInventory.next()) {
						if (rsKmenovaInventory.getInt(1) == rsZmenovaInventory.getInt(1)) {
							if (rsKmenovaInventory.getInt(3) + rsZmenovaInventory.getInt(3) >= 0) {

								statement.execute(ToolDAO.updateInventoryKmenova(rsZmenovaInventory.getInt(1),
										rsZmenovaInventory.getInt(3), rsKmenovaInventory.getInt(3)));
								statement.execute(ToolDAO.createChangeInInventoryProtocol(rsZmenovaInventory.getInt(1),
										rsZmenovaInventory.getInt(3), true, rsKmenovaInventory.getString(2)));
								statement.execute(ToolDAO.updateInventoryZmenova(rsZmenovaInventory.getInt(4)));
								LOGGER.debug("Operation successfull: " + rsKmenovaInventory.getString(2));

							} else {
								statement.execute(ToolDAO.createChangeInInventoryProtocol(rsZmenovaInventory.getInt(1),
										rsZmenovaInventory.getInt(3), false, rsKmenovaInventory.getString(2)));
								statement.execute(ToolDAO.updateInventoryZmenova(rsZmenovaInventory.getInt(4)));

								LOGGER.debug("Operation not successfull due to lack of "
										+ rsKmenovaInventory.getString(2) + " in inventory");
							}
							condition = false;
							break;
						}
					}
					if (condition) {
						statement.execute(ToolDAO.saveNewToolToKmenova(rsZmenovaInventory.getInt(1),
								rsZmenovaInventory.getString(2), rsZmenovaInventory.getInt(3)));
						statement.execute(ToolDAO.createChangeInInventoryProtocol(rsZmenovaInventory.getInt(1),
								rsZmenovaInventory.getInt(3), true, rsZmenovaInventory.getString(2)));
						statement.execute(ToolDAO.updateInventoryZmenova(rsZmenovaInventory.getInt(4)));
						LOGGER.debug("Saved new tool: " + rsZmenovaInventory.getString(2));
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
				LOGGER.debug("END OF OPERATION");
			}
		} catch (SQLException s) {
			LOGGER.error(s);
		} catch (Exception e) {
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
				int id = resultSet.getInt("tool_id");
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
				int id = resultSet.getInt("tool_id");
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