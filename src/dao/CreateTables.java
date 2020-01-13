package dao;

import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

public class CreateTables {
	
	public static final Logger LOGGER = Logger.getLogger(ToolDAO.class);
	
	public static void createInventoryTables(Statement statement) throws SQLException {

		statement.execute("CREATE TABLE IF NOT EXISTS public.inventory_kmenova(id SERIAL UNIQUE, "
				+ "tool_description TEXT COLLATE pg_catalog.\"default\", total_amount integer)");
		LOGGER.debug(" - - - - - - - - TABLE inventory.kmenova READY - - - - - - - - ");

		statement.execute(
				"CREATE TABLE IF NOT EXISTS public.inventory_zmenova(id SERIAL PRIMARY KEY, tool_id serial NOT NULL, "
						+ "tool_description text COLLATE pg_catalog.\"default\", "
						+ "tool_amount_change integer NOT NULL, " + "operation_successed BOOLEAN DEFAULT false)");
		LOGGER.debug(" - - - - - - - - TABLE inventory.zmenova READY - - - - - - - - ");
		statement.execute(
				"CREATE TABLE IF NOT EXISTS public.inventory_protocol(id SERIAL PRIMARY KEY, tool_id serial NOT NULL, "
						+ "tool_description text COLLATE pg_catalog.\"default\", "
						+ "tool_amount_change integer NOT NULL, " + "operation_successed BOOLEAN DEFAULT false, "
						+ "created_at timestamp(0) without time zone NOT NULL DEFAULT now(), "
						+ "updated_at timestamp(0) without time zone NOT NULL DEFAULT now()) ");
		LOGGER.debug(" - - - - - - - - TABLE inventory.protocol READY - - - - - - - - ");
	}
}