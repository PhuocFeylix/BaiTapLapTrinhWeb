package feylix.vn.connection;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

	public Connection getConnection() throws Exception {
		// Chuỗi kết nối Windows Authentication
		String url = "jdbc:sqlserver://localhost:1433;databaseName=ShoppingDB;integratedSecurity=true;encrypt=false;trustServerCertificate=true;";

		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		return DriverManager.getConnection(url);
	}

	// Hàm test nhanh kết nối
	public static void main(String[] args) {
		try {
			Connection conn = new DBConnection().getConnection();
			if (conn != null) {
				System.out.println("Kết nối cơ sở dữ liệu thành công!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}