package Form;
//Mao Tang
//maot
//08-600
//HW8

import java.util.ArrayList;
import org.mybeans.form.FormBean;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

public class FavoriteForm {
	private String urlll;
	private String comment;
	private String button;
	private String click;
	
	public FavoriteForm(){
		
	}
	
	public FavoriteForm(HttpServletRequest request) {
		urlll = request.getParameter("urlll");
		comment = request.getParameter("comment");
		button = request.getParameter("action");
		click = request.getParameter("action");
	}
	
	public void setURL(String s)    { urlll = s.trim(); }
	public void setComment(String s){ comment = s.trim();}
	
	public String getURL()    { return urlll; }
	public String getComment(){ return comment; }
	public String getButton() { return button;  }
	public String getClick()  { return click;  }
	
	public boolean isPresent(){
		
		if (!"Add Favorite".equals(button) && !"click".equals(button))
			return false;
		return true;
	}
	
	public List<String> getValidationErrors() {
		List<String> errors = new ArrayList<String>();

		if (urlll == null || urlll.length() == 0) {
			errors.add("URL is required");
		}

		if (comment == null || comment.length() == 0){
			errors.add("Comment is required");
		}
		if (errors.size()>0)
			return errors;
		if (!urlll.contains("http") && "Add Favorite".equals(button))
			errors.add("Please add 'http' to your url");
		return errors;
	}

}