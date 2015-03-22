<jsp:include page="template_top_left.jsp" />

<jsp:include page="error-list.jsp" />  
        <form action = login.do method="POST">
            <table>
                <tr>
                    <td style="font-size: x-large">Email Address:</td>
                    <td>
                        <input type="text" name="emailAddress"  value="${form.emailAddress}"
        
                        />
                    </td>
                </tr>
                <tr>
                    <td style="font-size: x-large">Password:</td>
                    <td><input type="password" name="password" /></td>
                </tr>
                <tr>
                    <td colspan="2" style="text-align: center;">
                        <input type="submit" name="action" value="Login" />
                    </td>
                </tr>
            </table>
        </form>
        <table>
        	    <tr>
        	        <td><span style="font-size: x-large"></span></td>
        	        <td><a href="register.do"><font color="#599989">No Accout? Register Now!</font></a><br/></td>
        	    </tr>
        </table>
	<jsp:include page="template_bottom.jsp" />
