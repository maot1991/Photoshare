package Action;


import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import DAO.Model;
import DAO.UserDAO;
import DAO.MyDAOException;

import org.genericdao.RollbackException;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import Beans.UserBean;
import Form.ChangePwdForm;

public class ChangePwdAction extends Action {
	private FormBeanFactory<ChangePwdForm> formBeanFactory = FormBeanFactory.getInstance(ChangePwdForm.class);
	
	private UserDAO userDAO;

	public ChangePwdAction(Model model) {
		userDAO = model.getUserDAO();
	}

	public String getName() { return "change-pwd.do"; }
    
    public String perform(HttpServletRequest request) {
    	// Set up error list
        List<String> errors = new ArrayList<String>();
        request.setAttribute("errors",errors);
        try{
        	request.setAttribute("userlist", userDAO.getUsers());
        }catch (MyDAOException e){
        	errors.add(e.toString());
        	return "error.jsp";
        }

        try {
            // Set up user list for nav bar
			//request.setAttribute("userList",userDAO.getUsers());
	        
	        // Load the form parameters into a form bean
	        ChangePwdForm form = formBeanFactory.create(request);
	        
	        // If no params were passed, return with no errors so that the form will be
	        // presented (we assume for the first time).
	        if (!form.isPresent()) {
	            return "change-pwd.jsp";
	        }
	
	        // Check for any validation errors
	        errors.addAll(form.getValidationErrors());
	        if (errors.size() != 0) {
	            return "change-pwd.jsp";
	        }
	
			UserBean user = (UserBean) request.getSession().getAttribute("user");
			System.out.println("email="+user.getEmailAddress()+"     "+form.getNewPassword());
			// Change the password
        	userDAO.changePwd(user.getEmailAddress(),form.getNewPassword());
	
			request.setAttribute("message","Password changed for "+user.getEmailAddress());
	        return "Login.jsp";
        } catch (FormBeanException e) {
        	errors.add(e.toString());
        	return "error.jsp";
        } catch (MyDAOException e) {
        	errors.add(e.toString());
        	return "error.jsp";
		}
    }
}
