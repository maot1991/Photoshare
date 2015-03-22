<jsp:include page="template_top_left.jsp" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page import="Beans.FavoriteBean" %>


<p>
	<table>
		<tr>
			<td>Favorite Pages for ${userVisit.firstName} ${userVisit.lastName}</td>
		</tr>
		
		<c:forEach var="favor" items="${favoriteList}">
			<tr>
				<td><a href="list.do?action=click&userUrl=${favor.URL }&userId=${favor.id}&id=${favor.favoriteId}">${favor.URL}</a></td>
				<td>${favor.count} times</td>
			</tr>
		</c:forEach>
	</table>
</p>

<jsp:include page="template_bottom.jsp" />
<%-- <a href="?action=click&userId=${favor.getId()}&id=${favor.getFavoriteId}">${favor.getURL()} --%>