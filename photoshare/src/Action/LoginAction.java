package Action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import DAO.FavoriteDAO;
import DAO.MyDAOException;
import DAO.UserDAO;

import org.genericdao.RollbackException;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import Form.LoginForm;
import Beans.FavoriteBean;
import Beans.UserBean;
import DAO.Model;

public class LoginAction extends Action{
	
private FormBeanFactory<LoginForm> formBeanFactory = FormBeanFactory.getInstance(LoginForm.class);
	
	private UserDAO userDAO;
	private FavoriteDAO favoriteDAO;

	public LoginAction(Model model) {
		userDAO = model.getUserDAO();
		favoriteDAO = model.getFavoriteDAO();
	}

	public String getName() { return "login.do"; }


	@Override
	public String perform(HttpServletRequest request) {
		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);
		
		
		
		try{
		LoginForm form = formBeanFactory.create(request);
		request.setAttribute("form",form);
		request.setAttribute("userlist", userDAO.getUsers());
   		
    	if (!form.isPresent()) {
    		System.out.println("empty");
    		return "Login.jsp";
    	}
    	
//    	if (form.getButton().equals("Register")) {
//    		return "Register.jsp";
//   		}
    	errors.addAll(form.getValidationErrors());
    	System.out.println(errors);
    		if (errors.size() != 0) {
    			return "Login.jsp";
    		}
    	
        try {
            UserBean user;
            	
                user = userDAO.read(form.getEmailAddress());
		       	if (user == null) {
		       		errors.add("No such user");
		       		return "Login.jsp";
		       	}
		       	
		       	if (!form.getPassword().equals(user.getPassword())) {
		       		errors.add("Incorrect password");
		       		return "Login.jsp";
		       	}
       		
	    	
	       	HttpSession session = request.getSession();
	       	session.setAttribute("user",user);
	       	FavoriteBean[] items = favoriteDAO.getUserFavorite(user);
       		request.setAttribute("myfavorite", items);
       		
	       //	System.out.println(session.getAttribute("user"));
	       	return "manage.do";
       	} catch (MyDAOException e) {
       		errors.add(e.getMessage());
       		return "Login.jsp";
       	}
	}catch (FormBeanException e) {
    	errors.add(e.getMessage());
    	return "error.jsp";
    }catch (MyDAOException e){
    	errors.add(e.getMessage());
    	return "error.jsp";
    }
}
}
