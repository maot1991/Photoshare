package Form;

import java.util.ArrayList;
import java.util.List;

import org.mybeans.form.FormBean;

public class UserForm extends FormBean {
	private String userId = "";
	private String userEmail = "";
	private String userUrl = "";
	
	public String getUserId()  { return userId; }
	public String getUserEmail()  { return userEmail; }
	public String getUserUrl() {return userUrl;}
	
	public void setUserId(String s)  { userId = s; }
	public void setUserEmail(String s)  { userEmail = trimAndConvert(s,"<>>\"]"); }
	public void setUserUrl(String s) {userUrl = s;}
	
	
	public List<String> getValidationErrors() {
		List<String> errors = new ArrayList<String>();

		if ((userId == null || userId.length()==0)&&(userEmail == null || userEmail.length() == 0)) {
			errors.add("User Name is required");
		}
		
	
		
		return errors;
	}
}
