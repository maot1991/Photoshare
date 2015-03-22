package Form;
//Mao Tang
//maot
//08-600
//HW8

import java.util.ArrayList;

import org.mybeans.form.FormBean;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

public class LoginForm extends FormBean {
    private String emailAddress;
    private String password;
    private String button;
	
//    public LoginForm() {
//    	emailAddress = "sss";
//    	password = null;
//    	button   = null;
//    }
//    
//    public LoginForm(HttpServletRequest request) {
//    	emailAddress = request.getParameter("userName");
//    	password = request.getParameter("password");
//    	button   = request.getParameter("action");
//    }
    
    
    public void setEmailAddress(String s) { emailAddress = trimAndConvert(s,"<>\"");  }
	public void setPassword(String s) {	password = s.trim();                  }
	
    
    public String getEmailAddress()  { return emailAddress; }
    public String getPassword()  { return password; }
    public String getButton()    { return button;   }
    
//    public boolean isPresent()   { 
//    	if (button == null)
//    		return false;
//    	else if (button.equals("Login")||button.equals("Register"))
//    		return true;
//    	return false;
//    }

    public List<String> getValidationErrors() {
        List<String> errors = new ArrayList<String>();

        if (emailAddress == null || emailAddress.length() == 0) errors.add("Email address is required");
        if (password == null || password.length() == 0) errors.add("Password is required");
      //  if (button == null) errors.add("Button is required");

        if (errors.size() > 0) return errors;

      //  if (!button.equals("Login") && !button.equals("Register")) errors.add("Invalid button");
        if (emailAddress.indexOf('@')==-1) errors.add("Please input valid email address");
		
        return errors;
    }
}
