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
import Form.UserForm;

/*
 * Looks up the photos for a given "user".
 * 
 * If successful:
 *   (1) Sets the "userList" request attribute in order to display
 *       the list of users on the navbar.
 *   (2) Sets the "photoList" request attribute in order to display
 *       the list of given user's photos for selection.
 *   (3) Forwards to list.jsp.
 */
public class ListAction extends Action {
	private FormBeanFactory<UserForm> formBeanFactory = FormBeanFactory.getInstance(UserForm.class);

	private FavoriteDAO favoriteDAO;
	private UserDAO  userDAO;

    public ListAction(Model model) {
    	favoriteDAO = model.getFavoriteDAO();
    	userDAO  = model.getUserDAO();
	}

    public String getName() { return "list.do"; }

    public String perform(HttpServletRequest request)  {
    	//System.out.println("123123123123123123123123");
        // Set up the request attributes (the errors list and the form bean so
        // we can just return to the jsp with the form if the request isn't correct)
        List<String> errors = new ArrayList<String>();
        request.setAttribute("errors",errors);
        String action = request.getParameter("action");
       // System.out.println("action======"+action);
        
		try {
			request.setAttribute("userlist", userDAO.getUsers());
			
			UserForm form = formBeanFactory.create(request);
			String email = form.getUserEmail();
			String idstr = form.getUserId();
			String url = form.getUserUrl();
			int id=0;
			if (idstr!=null && idstr.length()!=0)
				id = Integer.parseInt(idstr);
			
//			System.out.println("url="+url);
//			System.out.println("email="+email);
//			System.out.println("idididid="+id);
//			
			if("click".equals(action)){
	        	String uid = request.getParameter("id");
	        	int favoriteid = Integer.parseInt(uid);
	        	try{
	        		favoriteDAO.changeCount(favoriteDAO.read(favoriteid));
	        		//System.out.println("you are here0.333 id="+id);
	        		return url;
	        	}catch (MyDAOException e){
	        		return "error.jsp";
	        	}
	        }
	        
		//	System.out.println("you are here333");
			
			
			
//			if (email == null || email.length() == 0) {
//				errors.add("User must be specified");
//				return "error.jsp";
//			}
			  
			
			
			
		       
		//	System.out.println("you are here4");
	        // Set up photo list
        	UserBean user; 
        //	System.out.println("you are here 44id="+id);
        	if (email==null || email.length()==0 && id!=0)
				user = userDAO.readFromId(id);
        	else
        		user = userDAO.read(email);
        	if (user == null) {
    			errors.add("Invalid User: "+email);
    			return "error.jsp";
    		}
        	request.setAttribute("userVisit",user);
        //	System.out.println("you are here5");
        	FavoriteBean[] favoriteList = favoriteDAO.getUserFavorite(user);
	        request.setAttribute("favoriteList",favoriteList);
	        return "list.jsp";
        } catch (FormBeanException e) {
        	errors.add(e.getMessage());
        	return "error.jsp";
        }catch (MyDAOException e){
        	errors.add(e.getMessage());
        	return "error.jsp";
        }
    }
}
