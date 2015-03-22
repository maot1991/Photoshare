<jsp:include page="template_top_left.jsp" />
	    
<jsp:include page="error-list.jsp" />
	 <p>
        <form action = "register.do" method="POST">
            <table>
                <tr>
                    <td style="font-size: x-large">Email Address:</td>
                    <td>
                        <input type="text" name="emailAddress" value="${form.emailAddress}"

                        />
                    </td>
                </tr>
                <tr>
                    <td style="font-size: x-large">Password:</td>
                    <td><input type="password" name="password" /></td>
                </tr>
                <tr>
                    <td style="font-size: x-large">Last Name:</td>
                    <td>
                        <input type="text" name="lastName"  value="${form.lastName}"
                    /></td> 
                </tr>
                <tr>
                    <td style="font-size: x-large">First Name:</td>
                    <td>
                        <input type="text" name="firstName" value="${form.firstName}"
               
                    /></td>
                </tr>
                <tr>
                    <td colspan="2" style="text-align: center;">
                        <input type="submit" name="action" value="Register Now" />
                    </td>
                </tr>
            </table>
        </form>
      </p>
       <table>
        	    <tr>
        	        <td><span style="font-size: x-large"></span></td>
        	        <td><a href="login.do"><font color="#599989">Returning User?</font></a><br/></td>
        	    </tr>
        </table>
 
	<jsp:include page="template_bottom.jsp" />