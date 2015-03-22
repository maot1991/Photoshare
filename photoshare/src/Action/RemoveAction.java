package Action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import DAO.Model;
import DAO.FavoriteDAO;
import DAO.MyDAOException;
import DAO.UserDAO;

import org.genericdao.RollbackException;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import Beans.FavoriteBean;
import Beans.UserBean;

import Form.IdForm;

/*
 * Removes a photo.  Given an "id" parameter.
 * Checks to see that id is valid number for a photo and that
 * the logged user is the owner.
 * 
 * Sets up the "userList" and "photoList" request attributes
 * and if successful, forwards back to to "manage.jsp".
 */
public class RemoveAction extends Action {
	private FormBeanFactory<IdForm> formBeanFactory = FormBeanFactory.getInstance(IdForm.class);

	private FavoriteDAO favoriteDAO;
	private UserDAO  userDAO;

    public RemoveAction(Model model) {
    	favoriteDAO = model.getFavoriteDAO();
    	userDAO  = model.getUserDAO();
	}

    public String getName() { return "delete.do"; }

    public String perform(HttpServletRequest request) {
        List<String> errors = new ArrayList<String>();
        request.setAttribute("errors",errors);
        
		try {
            // Set up user list for nav bar
			request.setAttribute("userList",userDAO.getUsers());

	    	//IdForm form = formBeanFactory.create(request);
	    	IdForm form = formBeanFactory.create(request);
	    	IdForm form1 = formBeanFactory.create(request);
	    	UserBean user = (UserBean) request.getSession().getAttribute("user");

			int id = form.getIdAsInt();
			String strid = form1.getId();
			System.out.println("deleteidstr="+strid);
			System.out.println("deleteid="+id);
			if(strid!=null)
				favoriteDAO.delete(Integer.parseInt(strid));

    		// Be sure to get the photoList after the delete
        	
        	FavoriteBean[] items = favoriteDAO.getUserFavorite(user);
        	request.setAttribute("myfavorite", items);
	      
	        request.setAttribute("userlist", userDAO.getUsers());
	        System.out.println("flenght="+items.length);
	        return "Manage.jsp";
		}  catch (FormBeanException e) {
    		errors.add(e.getMessage());
    		return "error.jsp";
    	} catch (MyDAOException e){
    		errors.add(e.getMessage());
    		return "Manage.jsp";
    	}
    }
}
