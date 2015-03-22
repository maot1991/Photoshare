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
import java.util.List;

import DAO.MyDAOException;
import Beans.UserBean;
import Beans.FavoriteBean;




public class FavoriteDAO {
	private List<Connection> connectionPool = new ArrayList<Connection>();

	private String jdbcDriver;
	private String jdbcURL;
	private String tableName;
	
	public FavoriteDAO(String jdbcDriver, String jdbcURL, String tableName) throws MyDAOException {
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
	
	public void changeCount(FavoriteBean favorite) throws MyDAOException {
		Connection con = null;
		try {
        	con = getConnection();
        	con.setAutoCommit(false);

            Statement stmt = con.createStatement();

            
       //     update phonelist set phonenumber = '202-456-1414' where lastname = 'Obama';
            PreparedStatement pstmt = con.prepareStatement(
            		"UPDATE " + tableName + " SET count = "+(favorite.getCount()+1)+" WHERE favoriteid = "+favorite.getFavoriteId());
           
            pstmt.executeUpdate();
            pstmt.close();
            stmt = con.createStatement();
            con.commit();
            con.setAutoCommit(true);
            
            releaseConnection(con);
            
    	} catch (SQLException e) {
            try { if (con != null) con.close(); } catch (SQLException e2) { /* ignore */ }
        	throw new MyDAOException(e);
		}
	}
	
	public void addToList(FavoriteBean favorite) throws MyDAOException {
		Connection con = null;
        try {
        	con = getConnection();
        	PreparedStatement pstmt = con.prepareStatement("INSERT INTO " + tableName + " (id ,URL ,comment ,count) VALUES (?,?,?,?)");
        	
        	pstmt.setInt(1,favorite.getId());
        	pstmt.setString(2,favorite.getURL());
        	pstmt.setString(3,favorite.getComment());
        	pstmt.setInt(4,favorite.getCount());
        	pstmt.executeUpdate();
        	
        	
        	pstmt.close();
        	releaseConnection(con);
        	
    	} catch (SQLException e) {
            try { if (con != null) con.close(); } catch (SQLException e2) { /* ignore */ }
        	throw new MyDAOException(e);
		}
	}
	
	public void delete(int id) throws MyDAOException {
		Connection con = null;
    	try {
        	con = getConnection();

            Statement stmt = con.createStatement();
            stmt.executeUpdate("DELETE FROM " + tableName + " WHERE favoriteid=" + id);
            stmt.close();
            releaseConnection(con);
    	} catch (SQLException e) {
            try { if (con != null) con.close(); } catch (SQLException e2) { /* ignore */ }
        	throw new MyDAOException(e);
		}
	}

	public FavoriteBean[] getUserFavorite(UserBean user) throws MyDAOException {
		Connection con = null;
    	try {
        	con = getConnection();

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM " + tableName + " WHERE id = "+ user.getId());
            
            List<FavoriteBean> list = new ArrayList<FavoriteBean>();
            while (rs.next()) {
            	FavoriteBean bean = new FavoriteBean();
            	bean.setFavoriteId(rs.getInt("favoriteid"));
            	bean.setId(rs.getInt("id"));
            	bean.setURL(rs.getString("URL"));
            	bean.setComment(rs.getString("comment"));
            	bean.setCount(rs.getInt("count"));
            	list.add(bean);
            }
            stmt.close();
            releaseConnection(con);
            
            return list.toArray(new FavoriteBean[list.size()]);
    	} catch (SQLException e) {
            try { if (con != null) con.close(); } catch (SQLException e2) { /* ignore */ }
        	throw new MyDAOException(e);
		}
	}
	
	public FavoriteBean readURL(String url,int id) throws MyDAOException {
		Connection con = null;
        try {
        	con = getConnection();

        	PreparedStatement pstmt = con.prepareStatement("SELECT * FROM " + tableName + " WHERE URL = ? AND id = ?");
        	pstmt.setString(1,url);
        	pstmt.setInt(2,id);
        	ResultSet rs = pstmt.executeQuery();
        	
        	FavoriteBean favorite;
        	if (!rs.next()) {
        		favorite = null;
        	} else {
        		favorite = new FavoriteBean();
        		favorite.setFavoriteId(rs.getInt("favoriteid"));
        		favorite.setId(rs.getInt("id"));
        		favorite.setURL(rs.getString("URL"));
        		favorite.setComment(rs.getString("comment"));
        		favorite.setCount(rs.getInt("count"));
        	}
        	
        	rs.close();
        	pstmt.close();
        	releaseConnection(con);
            return favorite;
            
        } catch (Exception e) {
            try { if (con != null) con.close(); } catch (SQLException e3) { /* ignore */ }
        	throw new MyDAOException(e);
        }
	}
	
	public FavoriteBean read(int favoriteid) throws MyDAOException {
		Connection con = null;
        try {
        	con = getConnection();

        	PreparedStatement pstmt = con.prepareStatement("SELECT * FROM " + tableName + " WHERE favoriteid=?");
        	pstmt.setInt(1,favoriteid);
        	ResultSet rs = pstmt.executeQuery();
        	
        	FavoriteBean favorite;
        	if (!rs.next()) {
        		favorite = null;
        	} else {
        		favorite = new FavoriteBean();
        		favorite.setFavoriteId(rs.getInt("favoriteid"));
        		favorite.setId(rs.getInt("id"));
        		favorite.setURL(rs.getString("URL"));
        		favorite.setComment(rs.getString("comment"));
        		favorite.setCount(rs.getInt("count"));
        	}
        	
        	rs.close();
        	pstmt.close();
        	releaseConnection(con);
            return favorite;
            
        } catch (Exception e) {
            try { if (con != null) con.close(); } catch (SQLException e3) { /* ignore */ }
        	throw new MyDAOException(e);
        }
	}
	
	public int size() throws MyDAOException {
		Connection con = null;
    	try {
        	con = getConnection();

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT COUNT(favoriteid) FROM " + tableName);
            
            rs.next();
            int count = rs.getInt("COUNT(favoriteid)");

            stmt.close();
            releaseConnection(con);
            
            return count;

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

        } catch (Exception e) {
            try { if (con != null) con.close(); } catch (SQLException e2) { /* ignore */ }
        	throw new MyDAOException(e);
        }
    }

	private void createTable() throws MyDAOException {
    	Connection con = getConnection();
    	try {
            Statement stmt = con.createStatement();
            stmt.executeUpdate (
            		"CREATE TABLE " + tableName +
            		" (favoriteid INT NOT NULL AUTO_INCREMENT," +
            		" id INT," +
            		" URL VARCHAR(255)," +
            		" comment VARCHAR(255)," +
            		" count INT," +
            		" PRIMARY KEY(favoriteid))");
           
            stmt.close();
            releaseConnection(con);
        } catch (Exception e) {
            try { if (con != null) con.close(); } catch (SQLException e2) { /* ignore */ }
        	throw new MyDAOException(e);
        }
    }
}
