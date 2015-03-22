package Action;

import Beans.FavoriteBean;
import Beans.UserBean;
import DAO.MyDAOException;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import Form.FavoriteForm;
import DAO.FavoriteDAO;
import DAO.UserDAO;
import DAO.Model;

public class ManageAction extends Action{
	
//	private FormBeanFactory<FavoriteForm> formBeanFactory = FormBeanFactory.getInstance(FavoriteForm.class);
	private FavoriteDAO favoriteDAO;
	private UserDAO  userDAO;

	public ManageAction(Model model) {
    	favoriteDAO = model.getFavoriteDAO();
    	userDAO  = model.getUserDAO();
	}

	public String getName() { return "manage.do"; }

	@Override
	public String perform(HttpServletRequest request) {
		  HttpSession session = request.getSession(true);
		  List<String> errors = new ArrayList<String>();
		  FavoriteForm form = new FavoriteForm(request);
	      request.setAttribute("errors",errors);
	       try {
	    	   request.setAttribute("userlist", userDAO.getUsers());
	       }catch (MyDAOException e){
	    	   errors.add(e.getMessage());
	        	return "Manage.jsp";
	       }
	       UserBean user;
       	   user = (UserBean)session.getAttribute("user");
       	   try{
       		   FavoriteBean[] items = favoriteDAO.getUserFavorite(user);
       		   request.setAttribute("myfavorite", items);
       	   }catch (MyDAOException e){	
       		   return "Manage.jsp";
       	   }
       	   System.out.println(form.isPresent());
       	   if (!form.isPresent()){
       		   return "Manage.jsp";
       	   }
       	   errors.addAll(form.getValidationErrors());
       	   
       	   if (errors.size() > 0 && !"click".equals(request.getParameter("action"))) {
       		   return "Manage.jsp";
    	   }
	       try {
	       		FavoriteBean favorite;
	       		favorite = favoriteDAO.readURL(form.getURL(),userDAO.read(user.getEmailAddress()).getId());
		       	
	       		if (favorite != null && "Add Favorite".equals(form.getButton())) {
		       		errors.add(" This page already EXIST!!!!");
		    		return "Manage.jsp";
		       	}
	       		
	       		if("click".equals(request.getParameter("action"))){
		        	String uid = request.getParameter("id");
		        	int favoriteid = Integer.parseInt(uid);
		        	//System.out.println("id="+favoriteid);
		        	try{
		        		favoriteDAO.changeCount(favoriteDAO.read(favoriteid));
		        		return request.getParameter("URL");
		        	}catch (MyDAOException e){
		        		return "error.jsp";
		        	}
		        }
	       		
	       		FavoriteBean bean = new FavoriteBean();
	       		bean.setId(userDAO.read(user.getEmailAddress()).getId());
	       		bean.setURL(form.getURL());
	       		bean.setComment(form.getComment());
	       		request.setAttribute("favorite", bean);
	        	favoriteDAO.addToList(bean);
	        	FavoriteBean[] items = favoriteDAO.getUserFavorite(user);
	        	request.setAttribute("myfavorite", items);
		    	return "Manage.jsp";
	        } catch (MyDAOException e) {
	        	errors.add(e.getMessage());
	        	return "Manage.jsp";
	        }
		   
		  
		   
		   
		   
		   
		      
	       
//	        if (action.equals("Add Favorite")) {
//	        //	processAdd(request,response,session);
//	        	return "Manage.jsp";
//	        }
//	        else if (action.equals("Logout")){
//	        //	session.setAttribute("user", null);
//	        	return "Login.jsp";
//	        }
//	        
//	        else if(action.equals("click")){
//	        	String uid = request.getParameter("id");
//	        	int id = Integer.parseInt(uid);
//	        	try{
//	        		favoriteDAO.changeCount(favoriteDAO.read(id));
//	        		return "Manage.jsp";
//	        	}catch (MyDAOException e){
//	        		return "Manage.jsp";
//	        	}
//	        }else  
//	        	return "Manage.jsp";
//	        }
//	       
	
	}
	}

