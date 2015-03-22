package Action;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import DAO.UserDAO;
import DAO.MyDAOException;

import org.genericdao.DuplicateKeyException;
import org.genericdao.RollbackException;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import Beans.UserBean;
import DAO.Model;
import Form.FavoriteForm;
import Form.LoginForm;
import Form.RegisterForm;


public class RegisterAction extends Action {
	private FormBeanFactory<RegisterForm> formBeanFactory = FormBeanFactory.getInstance(RegisterForm.class);

	private UserDAO userDAO;
	
	public RegisterAction(Model model) {
		userDAO = model.getUserDAO();
	}

	public String getName() { return "register.do"; }

    public String perform(HttpServletRequest request) {
    	List<String> errors = new ArrayList<String>();
    	request.setAttribute("errors",errors);

    	//user = userDAO.read(form.getUserName());
    	try{
    		RegisterForm form = formBeanFactory.create(request);
    		request.setAttribute("form",form);
    		request.setAttribute("userlist", userDAO.getUsers());
    		
    		System.out.println("address="+form.getEmailAddress());
    		
        	if (!form.isPresent()) {
        		System.out.println("emeeee");
        		return "Register.jsp";
        	}
       
        	errors.addAll(form.getValidationErrors());
        	if (errors.size() != 0) {
        		return "Register.jsp";
        	}
	       
    	
    	/*
    	 * use const.equals(var) prevent it from error when null.equals(const) will cause error;
    	 */
    
    	try {
            UserBean user;
            	
                user = userDAO.read(form.getEmailAddress());
		       	if (user == null) {
		       		user = new UserBean();
	       			user.setEmailAddress(form.getEmailAddress());
	       			user.setPassword(form.getPassword());
	       			user.setLastName(form.getLastName());
	       			user.setFirstName(form.getFirstName());
	       			userDAO.create(user);
	       			//user.setId(i);
	       			user.setId(userDAO.read(user.getEmailAddress()).getId());
	       			HttpSession session = request.getSession();
	    	       	session.setAttribute("user",user);
		    		return "Manage.jsp";
		       	}
		       	
		       	else {
		       		errors.add("User exists");
		    		return "Register.jsp";
		       	}
       		} catch (MyDAOException e) {
       			errors.add(e.getMessage());
       			return "Register.jsp";
       		}
    	}catch (FormBeanException e) {
        	errors.add(e.getMessage());
        	return "register.jsp";
        }catch (MyDAOException e){
        	errors.add(e.getMessage());
        	return "register.jsp";
        }
    }

}