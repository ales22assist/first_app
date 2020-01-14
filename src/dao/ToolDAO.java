package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

import connection_manager.DatabaseConnectionManager;

public class ToolDAO {

	public static final Logger LOGGER = Logger.getLogger(ToolDAO.class);

	private ToolDAO() {
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
				try (ResultSet rsKmenovaInventory = connection.createStatement().executeQuery(
						"SELECT id, tool_description, total_amount FROM inventory_kmenova ORDER BY id");) {

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

								LOGGER.error("Operation not successfull due to lack of "
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
					LOGGER.error(e);
				}
				LOGGER.debug("END OF OPERATION");
			}
		} catch (SQLException s) {
			LOGGER.error(s);
		} catch (Exception e) {
			LOGGER.error(e);
		}
	}
}