package Form;
//Mao Tang
//maot
//08-600
//HW8

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.mybeans.form.FormBean;
public class RegisterForm extends FormBean{ 
	private String emailAddress;
	private String password;
	private String lastName;
	private String firstName;
	private String button;
	private String back;
	
    public void setEmailAddress(String s) { emailAddress = trimAndConvert(s,"<>\"");  }
	public void setPassword(String s) {	password = s.trim();                  }
	public void setLastName(String s)  { lastName = s.trim(); }
	public void setFirstName(String s)    { firstName = s.trim(); }

	
	public String getEmailAddress()  { return emailAddress; }
	public String getPassword()  { return password; }
	public String getButton()    { return button;   }
	public String getFirstName() { return firstName;}
	public String getLastName()  { return lastName; }
	public String getBack()		 { return back;		}
	


public List<String> getValidationErrors() {
    List<String> errors = new ArrayList<String>();

    if (emailAddress == null || emailAddress.length() == 0) errors.add("User Name is required");
    if (password == null || password.length() == 0) errors.add("Password is required");
    if (lastName == null || lastName.length() == 0) errors.add("Lastname is required");
    if (firstName == null || firstName.length() == 0) errors.add(" Firstname is required");
    if (errors.size() > 0) return errors;
    if (emailAddress.indexOf('@')==-1) errors.add("Plese input valid email address include @");
   
	
    return errors;
}
}


