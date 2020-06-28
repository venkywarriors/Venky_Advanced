package testbase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBase {

	public static void main(String[] args) {

		String DB_DRIVER = "com.ibm.db2.jcc.DB2Driver";
		String url = "jdbc:oracle:thin:@localhost:1521:orcl";
		String username = "system";
		String password = "Password123";
		StringBuffer firstRowResult = new StringBuffer();
		try {
			Class.forName(DB_DRIVER);
			DriverManager.setLoginTimeout(10);
			Connection connection = DriverManager.getConnection(url, username, password);
			if (connection != null) {
				System.out.println("Connection Established!!!");
			} else {
				System.out.println("Connection Not established...");
			}
			Statement stmt = connection.createStatement();

			String memQuery = "Select DISTINCT PERSON_ID from PERSON_COMPANY where UNIQUE_IDENTIFIER = 'U105386'";
			System.out.println(memQuery);
			ResultSet rs = stmt.executeQuery(memQuery);

			while (rs.next()) {
				for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
					firstRowResult = firstRowResult
							.append(rs.getMetaData().getColumnName(i) + " = " + rs.getString(i) + " | ");
				}
				System.out.println("output " + firstRowResult.toString());
				
				String ColumnName = GetDbDetails(rs, "TableColName");
				System.out.println("ColumnName " + ColumnName.toString());

			}
			
			rs.close();
			
			if (!connection.isClosed()) {
				connection.close();
				System.out.println("Connection closed!!!");
			}
		} catch (SQLException e) {
			System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String GetDbDetails(ResultSet DbRs, String ColNam) throws Exception {
		try {
			String dbData = "-";
			if (DbRs.getString(ColNam) != "" && DbRs.getString(ColNam) != null) {
				dbData = DbRs.getString(ColNam).trim();
			}
			return dbData;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
}
