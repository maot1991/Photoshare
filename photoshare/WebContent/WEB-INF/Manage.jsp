<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
  <%@page import="Beans.UserBean"%>
 <%@page import="Beans.FavoriteBean"%>
  <%@page import="DAO.FavoriteDAO"%>
   <%@page import="DAO.Model"%>
 <jsp:include page="template_top_left.jsp" />
 
 <jsp:include page="error-list.jsp" />  
        <form action="manage.do" method="POST">
            <table>
                <tr><td colspan="3"><hr/></td></tr>
                <tr>
                    <td style="font-size: large">URL:</td>
                    <td colspan="2"><input type="text" size="40" name="urlll" /></td>
                </tr>
                <tr>
                    <td style="font-size: large">Comment:</td>
                    <td colspan="2"><input type="text" size="40" name="comment"/></td>
                </tr>
                <tr>
                    <td></td>
                    <td><input type="submit" name="action" value="Add Favorite"/></td>
                </tr>
                <tr><td colspan="3"><hr/></td></tr>
            </table>
        </form>




        <p style="font-size: x-large">My favorite websites items.</p>
        
		
<p>
		<table>
			<c:forEach var="favor" items="${myfavorite}">
				<tr>
					<td>
			            <form action="delete.do" method="POST">
                			<input type="hidden" name="favoriteId" value="${favor.favoriteId}" />
                			<input type="submit" name="button" value="x" />
           				</form>
        			</td>
					<td>
						<span style="font-size: large">
							<a href="manage.do?action=click&URL=${favor.URL }&userId=${favor.id}&id=${favor.favoriteId}">${favor.URL}</a>
						</span>
					</td>
				</tr>
				<tr>
					<td></td>
					<td>
						<span style="font-size: medium">
							${ favor.comment}
						</span>
					</td>
				</tr>
				<tr>
					<td></td>
					<td>
						<span style="font-size: medium">
							${ favor.count}
						</span>
					</td>
				</tr>
		</c:forEach>
		
		</table>
		</p>
        
        
        
        

        
     
 
	<jsp:include page="template_bottom.jsp" />