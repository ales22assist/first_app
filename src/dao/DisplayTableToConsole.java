package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import org.apache.log4j.Logger;

import connection_manager.DatabaseConnectionManager;

public class DisplayTableToConsole {
	
	public static final Logger LOGGER = Logger.getLogger(DisplayTableToConsole.class);
	
		static SQLException sqlException;
		static int errCode = sqlException.getErrorCode();
		static String sQLState = sqlException.getSQLState();
		static String errorMessage = ("ErrorCode: " + Integer.toString(errCode) + " SQLState " + sQLState);
		
		
	
	private DisplayTableToConsole() {
	}

	public static void displayDataKmenova() {
		System.out.println("***THIS IS INVENTORY.KMENOVA***");
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
			LOGGER.debug(errorMessage);
		}
	}

	public static void displayDataZmenova() {
		System.out.println("***THIS IS INVENTORY.ZMENOVA***");
		try {
			Statement statement = DatabaseConnectionManager.getManagerInstance().getConnectionObject()
					.createStatement();
			String queryString = "select * from inventory_zmenova";
			ResultSet resultSet = statement.executeQuery(queryString);

			while (resultSet.next()) {
				int id = resultSet.getInt("tool_id");
				String tool = resultSet.getString("tool_description");
				Integer amount = resultSet.getInt("tool_amount_change");
				Boolean operationStatus = resultSet.getBoolean("operation_successed");

				System.out.println(
						"|| id: " + Integer.toString(id) + " || tool_description: " + tool + " || tool_amount_change: "
								+ amount.toString() + " || " + "operation_successed: " + operationStatus + " || ");
			}

		} catch (SQLException sqlException) {
			LOGGER.debug(errorMessage);
		}
	}

	public static void displayInventoryProtocol() {
		System.out.println("***THIS IS INVENTORY.PROTOCOL***");
		try {
			Statement statement = DatabaseConnectionManager.getManagerInstance().getConnectionObject()
					.createStatement();
			String queryString = "select * from inventory_protocol";
			ResultSet resultSet = statement.executeQuery(queryString);

			while (resultSet.next()) {
				int id = resultSet.getInt("tool_id");
				String tool = resultSet.getString("tool_description");
				Integer amount = resultSet.getInt("tool_amount_change");
				Boolean operationStatus = resultSet.getBoolean("operation_successed");
				Timestamp tsTimestamp = resultSet.getTimestamp("created_at");
				Timestamp updTimestamp = resultSet.getTimestamp("updated_at");


				System.out.println(
						"|| id: " + Integer.toString(id) + " || tool_description: " + tool + " || tool_amount_change: "
								+ amount.toString() + " || " + "operation_successed: " + operationStatus + " || "
								+ "created at: " + tsTimestamp + " || " + "updated at: " + updTimestamp + " || ");
			}

		} catch (SQLException sqlException) {
			LOGGER.debug(errorMessage);
		}
	}
}