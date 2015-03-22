package DAO;
//Mao Tang
//maot
//08-600
//HW8
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Beans.FavoriteBean;
import Beans.UserBean;

public class UserDAO {
	private List<Connection> connectionPool = new ArrayList<Connection>();

	private String jdbcDriver;
	private String jdbcURL;
	private String tableName;
	
	public UserDAO(String jdbcDriver, String jdbcURL, String tableName) throws MyDAOException {
		this.jdbcDriver = jdbcDriver;
		this.jdbcURL    = jdbcURL;
		this.tableName  = tableName;
		
		if (!tableExists()) createTable();
	}
	
	private synchronized Connection getConnection() throws MyDAOException {
		if (connectionPool.size() > 0) {
			return connectionPool.remove(connectionPool.size()-1);
		}
		
        try {
            Class.forName(jdbcDriver);
        } catch (ClassNotFoundException e) {
            throw new MyDAOException(e);
        }

        try {
            return DriverManager.getConnection(jdbcURL);
        } catch (SQLException e) {
            throw new MyDAOException(e);
        }
	}
	
	private synchronized void releaseConnection(Connection con) {
		connectionPool.add(con);
	}


	public void create(UserBean user) throws MyDAOException {
		Connection con = null;
        try {
        	con = getConnection();
        	PreparedStatement pstmt = con.prepareStatement("INSERT INTO " + tableName + " (email,password,last,first) VALUES (?,?,?,?)");
        	
        	pstmt.setString(1,user.getEmailAddress());
        	pstmt.setString(2,user.getPassword());
        	pstmt.setString(3,user.getLastName());
        	pstmt.setString(4,user.getFirstName());
        	int count = pstmt.executeUpdate();
        	if (count != 1) throw new SQLException("Insert updated "+count+" rows");
        	pstmt.close();
        	releaseConnection(con);
        	
        } catch (Exception e) {
            try { if (con != null) con.close(); } catch (SQLException e2) { /* ignore */ }
        	throw new MyDAOException(e);
        }
	}

	public UserBean read(String emailAddress) throws MyDAOException {
		Connection con = null;
        try {
        	con = getConnection();

        	PreparedStatement pstmt = con.prepareStatement("SELECT * FROM " + tableName + " WHERE email=?");
        	pstmt.setString(1,emailAddress);
        	ResultSet rs = pstmt.executeQuery();
        	
        	UserBean user;
        	if (!rs.next()) {
        		user = null;
        	} else {
        		user = new UserBean();
        		user.setEmailAddress(rs.getString("email"));
        		user.setPassword(rs.getString("password"));
        		user.setFirstName(rs.getString("first"));
        		user.setLastName(rs.getString("last"));
        		user.setId(rs.getInt("id"));
        	}
        	
        	rs.close();
        	pstmt.close();
        	releaseConnection(con);
            return user;
            
        } catch (Exception e) {
            try { if (con != null) con.close(); } catch (SQLException e3) { /* ignore */ }
        	throw new MyDAOException(e);
        }
	}
	
	public UserBean readFromId(int id) throws MyDAOException {
		Connection con = null;
        try {
        	con = getConnection();

        	PreparedStatement pstmt = con.prepareStatement("SELECT * FROM " + tableName + " WHERE id=?");
        	pstmt.setInt(1,id);
        	ResultSet rs = pstmt.executeQuery();
        	
        	UserBean user;
        	if (!rs.next()) {
        		user = null;
        	} else {
        		user = new UserBean();
        		user.setEmailAddress(rs.getString("email"));
        		user.setPassword(rs.getString("password"));
        		user.setFirstName(rs.getString("first"));
        		user.setLastName(rs.getString("last"));
        		user.setId(rs.getInt("id"));
        	}
        	
        	rs.close();
        	pstmt.close();
        	releaseConnection(con);
            return user;
            
        } catch (Exception e) {
            try { if (con != null) con.close(); } catch (SQLException e3) { /* ignore */ }
        	throw new MyDAOException(e);
        }
	}

	public UserBean[] getUsers() throws MyDAOException {
		Connection con = null;
    	try {
        	con = getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM " + tableName );
            List<UserBean> list = new ArrayList<UserBean>();
            while (rs.next()) {
            	UserBean bean = new UserBean();
            	bean.setEmailAddress(rs.getString("email"));
            	bean.setPassword(rs.getString("password"));
            	bean.setLastName(rs.getString("last"));
            	bean.setFirstName(rs.getString("first"));
            	list.add(bean);
            }
            stmt.close();
            releaseConnection(con);

            return list.toArray(new UserBean[list.size()]);
            
    	} catch (SQLException e) {
            try { if (con != null) con.close(); } catch (SQLException e2) { /* ignore */ }
        	throw new MyDAOException(e);
		}
	}
	
	public void changePwd(String email,String password) throws MyDAOException{
		Connection con = null;           
            try {
            	con = getConnection();
            	PreparedStatement pstmt = con.prepareStatement("UPDATE " + tableName + " SET password = ?  WHERE email = ?");
            	pstmt.setString(1,password);
            	pstmt.setString(2,email);
            	int count = pstmt.executeUpdate();
            	if (count != 1) throw new SQLException("Insert updated "+count+" rows");
            	pstmt.close();
            	releaseConnection(con);
            } catch (Exception e) {
                try { if (con != null) con.close(); } catch (SQLException e2) { /* ignore */ }
            	throw new MyDAOException(e);
            }

	}
	
	private boolean tableExists() throws MyDAOException {
		Connection con = null;
        try {
        	con = getConnection();
        	DatabaseMetaData metaData = con.getMetaData();
        	ResultSet rs = metaData.getTables(null, null, tableName, null);
        	
        	boolean answer = rs.next();
        	
        	rs.close();
        	releaseConnection(con);
        	
        	return answer;

        } catch (SQLException e) {
            try { if (con != null) con.close(); } catch (SQLException e2) { /* ignore */ }
        	throw new MyDAOException(e);
        }
    }

	private void createTable() throws MyDAOException {
		Connection con = null;
        try {
        	con = getConnection();
            Statement stmt = con.createStatement();
            stmt.executeUpdate  (
            		"CREATE TABLE " + tableName +
            		" (email VARCHAR(50)," +
            		" password VARCHAR(50)," +
            		" last VARCHAR(255)," +
            		" first VARCHAR(255), " +
            		" id INT NOT NULL AUTO_INCREMENT," +
            		" PRIMARY KEY(id))");
          
            stmt.close();
        	
        	releaseConnection(con);

        } catch (SQLException e) {
            try { if (con != null) con.close(); } catch (SQLException e2) { /* ignore */ }
        	throw new MyDAOException(e);
        }
    }
}
