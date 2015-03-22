package Action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import DAO.Model;
import DAO.MyDAOException;
import DAO.UserDAO;


/*
 * Logs out by setting the "user" session attribute to null.
 * (Actions don't be much simpler than this.)
 */
public class LogoutAction extends Action {
	private UserDAO userDAO;
	public LogoutAction(Model model) {
		userDAO = model.getUserDAO();
		}

	public String getName() { return "logout.do"; }

	public String perform(HttpServletRequest request) {
		try{
			request.setAttribute("userlist", userDAO.getUsers());
		}catch (MyDAOException e){
			return "error.jsp";
		}
        HttpSession session = request.getSession(false);
        session.setAttribute("user",null);
        
		request.setAttribute("message","You are now logged out");
        return "Login.jsp";
    }
}
