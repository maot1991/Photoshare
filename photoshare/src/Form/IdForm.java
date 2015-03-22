package Form;

import java.util.ArrayList;
import java.util.List;

import org.mybeans.form.FormBean;

public class IdForm extends FormBean {
	private String favoriteId;
	
	public String getId() { return favoriteId;    }
	
	public int getIdAsInt() {
		try {
			return Integer.parseInt(favoriteId);
		} catch (NumberFormatException e) {
			// call getValidationErrors() to detect this
			return -1;
		}
	}
	public void setFavoriteId(String favoriteId) { this.favoriteId = favoriteId; }

	public List<String> getValidationErrors() {
		List<String> errors = new ArrayList<String>();
		
		if (favoriteId == null || favoriteId.length() == 0) {
			errors.add("Id is required");
			return errors;
		}

		try {
			Integer.parseInt(favoriteId);
		} catch (NumberFormatException e) {
			errors.add("Id is not an integer");
		}
		
		return errors;
	}
}
