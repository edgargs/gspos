package com.gs.hacom.dcs.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConnectMSSQLServer {
	
	private static final Logger logger = LogManager.getLogger(ConnectMSSQLServer.class);
			
	private static Connection conn = null;
	private static ConnectMSSQLServer connServer = new ConnectMSSQLServer();

	private ConnectMSSQLServer() {
	}

	private void dbConnect(String db_connect_string, String db_userid, String db_password) {
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement preparedStatement = null;
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			conn = DriverManager.getConnection(db_connect_string, db_userid, db_password);
			System.out.println("connected");
			Statement statement = conn.createStatement();
			String queryString = "select * from MTXEvent where 'u'='u'";
			rs = statement.executeQuery(queryString);
			while (rs.next()) {
				System.out.println(rs.getString(1));
			}

			// insert
			conn.setAutoCommit(false);
			String insertString = "INSERT INTO MTXEvent(accountID) VALUES(4332)";
			preparedStatement = conn.prepareStatement(insertString);
			int rows = preparedStatement.executeUpdate();
			System.out.println("Filas afectadas:" + rows);
			conn.rollback();
			preparedStatement.close();

			insertString = "INSERT INTO MTXEvent(accountID) VALUES(?)";
			preparedStatement = conn.prepareStatement(insertString);
			preparedStatement.setInt(1, 4333);
			rows = preparedStatement.executeUpdate();
			System.out.println("Filas afectadas:" + rows);
			conn.commit();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				preparedStatement.close();
				conn.close();
			} catch (SQLException | NullPointerException e) {
				;
			}

		}
	}

	public static void main(String[] args) {
		ConnectMSSQLServer connServer = new ConnectMSSQLServer();
		connServer.dbConnect("jdbc:sqlserver://192.168.5.35;databaseName=MATRIX", "sa", "hcm123");
	}

	public static ConnectMSSQLServer newInstance(String db_connect_string, String db_userid, String db_password) {

		if (conn != null) {
			logger.info("return connection");
		} else {
			try {
				Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
				conn = DriverManager.getConnection(db_connect_string, db_userid, db_password);
				conn.setAutoCommit(false);
				logger.info("connected");
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
		}
		return connServer;
	}

	public void endConnection() {
		try {
			conn.close();
			conn = null;
		} catch (SQLException | NullPointerException e) {
			;
		} finally {
			logger.warn("Connection closed");
		}
	}

	public boolean executeSQL(String insertString, Object[] parameters) {
		boolean stateExecute = false;
		// String insertString = "INSERT INTO MTXEvent(accountID) VALUES(?)";
		if (conn != null)
			try (PreparedStatement preparedStatement = conn.prepareStatement(insertString);) {

				for (Object param : parameters) {
					if (param instanceof String) {

					} else if (param instanceof Integer) {
						preparedStatement.setInt(1, (Integer) param);
					}
				}

				int rows = preparedStatement.executeUpdate();
				logger.trace("Filas afectadas:" + rows);
				conn.commit();
				stateExecute = true;
			} catch (SQLException e) {
				try {
					conn.rollback();
				} catch (SQLException e1) {
					;
				}
				logger.error("",e);
			}
		return stateExecute;
	}

	public boolean startConnection() {

		try {
			return !conn.isClosed();
		} catch (SQLException e) {
			logger.error("",e);
			return false;
		}
	}

	public static ConnectMSSQLServer getInstance() {

		return connServer;
	}
}