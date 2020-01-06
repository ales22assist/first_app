import configuration.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;


public class Application {

	
		private static final Logger LOGGER = Logger.getLogger(Application.class);
		private static String tableName;

		public static void main(String[] args) {
			
				PropertyConfigurator.configure(ApplicationConf.getLogPropertyFile());
				
			LOGGER.debug("BEGIN");
			
			try {
				Class.forName(ApplicationConf.getDriverClass());
			} catch (ClassNotFoundException exception) {
				System.out.println("failed to load driver");
				System.out.println(exception);
				return;
			}
			
			tableName = ApplicationConf.getTabName();

			try {
				Connection connection = DriverManager.getConnection(ApplicationConf.getUrl(), ApplicationConf.getUserName(), ApplicationConf.getUserPassword());
				Statement statement = connection.createStatement();
				String queryString = "select * from inventory";
				ResultSet resultSet = statement.executeQuery(queryString);
				
				while (resultSet.next()) {
					int id = resultSet.getInt("id");
					String tool = resultSet.getString("tool");
					Integer amount = resultSet.getInt("total_amount");
					
					System.out.println(" id: " + Integer.toString(id) + " || tool: " + tool + " || amount: " + amount.toString());
				
				// statement.execute("insert into " + tableName + ("1"));			
				connection.close();
				
			} }catch (SQLException sqlException){
				int errCode= sqlException.getErrorCode();
				String SQLState = sqlException.getSQLState();
				System.out.println("ErrorCode: " + Integer.toString(errCode) + " SQLState " + SQLState);	
				
			} catch (Exception exception) {
				System.out.println(exception);
			}
			
			LOGGER.debug("END");
	}
}