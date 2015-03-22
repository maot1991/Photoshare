package Action;

//Mao Tang
//maot
//08-600
//HW9
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Action.Action;
import Beans.FavoriteBean;
import Beans.UserBean;
import DAO.FavoriteDAO;
import DAO.Model;
import DAO.MyDAOException;
import DAO.UserDAO;



public class MyFavorites extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	public void init() throws ServletException {
		
		Model model = new Model(getServletConfig());
		UserDAO userDAO = model.getUserDAO();
		FavoriteDAO favoriteDAO = model.getFavoriteDAO();
		
			try{
				if (userDAO.getUsers().length==0){
					UserBean u1,u2,u3;
					u1 = new UserBean();
					u1.setEmailAddress("carnegie@cmu.edu");
					u1.setPassword("cmu");
					u1.setLastName("Carnegie");
					u1.setFirstName("Mellon");
					u2 = new UserBean();
					u2.setEmailAddress("tang@xjtu.edu");
					u2.setPassword("xjtu");
					u2.setLastName("Tang");
					u2.setFirstName("Mao");
					u3 = new UserBean();
					u3.setEmailAddress("guo@tku.edu");
					u3.setPassword("tku");
					u3.setLastName("Guo");
					u3.setFirstName("Yuyan");
					userDAO.create(u1);
					userDAO.create(u2);
					userDAO.create(u3);
					FavoriteBean f1,f2,f3,f4;
					f1 = new FavoriteBean();
			   		f1.setId(userDAO.read(u1.getEmailAddress()).getId());
			   		f1.setURL("http://www.baidu.com");
			   		f1.setComment("search in cn");
			   		favoriteDAO.addToList(f1);
			   		f2 = new FavoriteBean();
			   		f2.setId(userDAO.read(u1.getEmailAddress()).getId());
			   		f2.setURL("http://www.sina.com");
			   		f2.setComment("News");
			   		favoriteDAO.addToList(f2);
			   		f3 = new FavoriteBean();
			   		f3.setId(userDAO.read(u1.getEmailAddress()).getId());
			   		f3.setURL("http://www.facebook.com");
			   		f3.setComment("social");
			   		favoriteDAO.addToList(f3);
			   		f4 = new FavoriteBean();
			   		f4.setId(userDAO.read(u1.getEmailAddress()).getId());
			   		f4.setURL("http://www.cmu.edu");
			   		f4.setComment("school");
			   		favoriteDAO.addToList(f4);
			    	f1.setId(userDAO.read(u2.getEmailAddress()).getId());
			    	favoriteDAO.addToList(f1);
			    	f2.setId(userDAO.read(u2.getEmailAddress()).getId());
			    	favoriteDAO.addToList(f2);
			    	f3.setId(userDAO.read(u2.getEmailAddress()).getId());
			    	favoriteDAO.addToList(f3);
			    	f4.setId(userDAO.read(u2.getEmailAddress()).getId());
			    	favoriteDAO.addToList(f4);
			    	f1.setId(userDAO.read(u3.getEmailAddress()).getId());
			    	favoriteDAO.addToList(f1);
			    	f2.setId(userDAO.read(u3.getEmailAddress()).getId());
			    	favoriteDAO.addToList(f2);
			    	f3.setId(userDAO.read(u3.getEmailAddress()).getId());
			    	favoriteDAO.addToList(f3);
			    	f4.setId(userDAO.read(u3.getEmailAddress()).getId());
			    	favoriteDAO.addToList(f4);
				}
			
			}catch (MyDAOException e){
				return;
			}
		
		Action.add(new LoginAction(model));
		Action.add(new RegisterAction(model));
		Action.add(new ManageAction(model));
		Action.add(new LogoutAction(model));
		Action.add(new ChangePwdAction(model));
		Action.add(new ListAction(model));
		Action.add(new RemoveAction(model));
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 String nextPage = performTheAction(request);
		 sendToNextPage(nextPage,request,response);
	}
    
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	doGet(request,response);
    }

    private String performTheAction(HttpServletRequest request) {
        HttpSession session     = request.getSession(true);
        String      servletPath = request.getServletPath();
        UserBean        user = (UserBean) session.getAttribute("user");
        String      action = getActionName(servletPath);
        System.out.println("now we are="+action);
        if(action.contains("list.do"))
        	return Action.perform(action, request);
        
        if (action.equals("register.do") || action.equals("login.do")) {
        	// Allow these actions without logging in
			return Action.perform(action,request);
        }
        
        if (user == null) {
        	System.out.println("no usre");
        	// If the user hasn't logged in, direct him to the login page
			return Action.perform("login.do",request);
        }

        //System.out.println("now we are="+action);
      	// Let the logged in user run his chosen action
		return Action.perform(action,request);
    }

    /*
     * If nextPage is null, send back 404
     * If nextPage ends with ".do", redirect to this page.
     * If nextPage ends with ".jsp", dispatch (forward) to the page (the view)
     *    This is the common case
     */
    private void sendToNextPage(String nextPage, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    	if (nextPage == null) {
    		response.sendError(HttpServletResponse.SC_NOT_FOUND,request.getServletPath());
    		return;
    	}
    	
    	if (nextPage.endsWith(".do")) {
			response.sendRedirect(nextPage);
			return;
    	}
    	
    	if (nextPage.endsWith(".jsp")) {
	   		RequestDispatcher d = request.getRequestDispatcher("WEB-INF/" + nextPage);
	   		d.forward(request,response);
	   		return;
    	}
    	
    	if (nextPage.contains("http")) {
    		System.out.println("I was clicked");
    		response.sendRedirect(nextPage);
	   		return;
    	}
    	
    	System.out.println("nextpage="+nextPage);
    	
    	throw new ServletException(Action.class.getName()+".sendToNextPage(\"" + nextPage + "\"): invalid extension.");
    }

	/*
	 * Returns the path component after the last slash removing any "extension"
	 * if present.
	 */
    private String getActionName(String path) {
    	// We're guaranteed that the path will start with a slash
        int slash = path.lastIndexOf('/');
        return path.substring(slash+1);
    }
}

    