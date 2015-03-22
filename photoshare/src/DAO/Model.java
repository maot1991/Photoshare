package DAO;


import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;

public class Model {
	private FavoriteDAO favoriteDAO;
	private UserDAO  userDAO;

	public Model(ServletConfig config) throws ServletException {
		try {
			String jdbcDriver = config.getInitParameter("jdbcDriver");
			String jdbcURL    = config.getInitParameter("jdbcURL");
			
			ConnectionPool pool = new ConnectionPool(jdbcDriver, jdbcURL);
			
			userDAO  = new UserDAO(jdbcDriver, jdbcURL,"maot_u10");
			favoriteDAO = new FavoriteDAO(jdbcDriver, jdbcURL,"maot_f10");
		} catch (Exception e) {
			throw new ServletException(e);
		}
	}
	
	public FavoriteDAO getFavoriteDAO() { return favoriteDAO; }
	public UserDAO  getUserDAO()  { return userDAO;  }
}
