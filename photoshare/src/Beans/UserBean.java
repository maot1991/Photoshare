package Beans;
//Mao Tang
//maot
//08-600
//HW8

public class UserBean {
	private int id;
	private String emailAddress;
    private String firstName;
    private String lastName;
    private String password;

    public int getId()				   { return id; }
    public String getEmailAddress()    { return emailAddress; }
    public String getFirstName()	   { return firstName; }
    public String getLastName()	   	   { return lastName; }
    public String getPassword()        { return password; }
    

    public void setId(int i)				   { id = i; }
    public void setEmailAddress(String s)      { emailAddress = s; }
    public void setFirstName(String s)	       { firstName = s; }
    public void setLastName(String s)	   	   { lastName = s; }
    public void setPassword(String s)          { password = s; }
   
}
