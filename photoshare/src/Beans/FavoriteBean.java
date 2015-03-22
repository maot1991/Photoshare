package Beans;


public class FavoriteBean {
	int favoriteId;
	int id;
	String URL;
	String comment;
	int count;
	
	public int    getFavoriteId()        { return favoriteId; }
    public int    getId()                { return id;         }
    public String getURL()               { return URL;        }
    public String getComment()           { return comment;    }
    public int    getCount()             { return count;      }

    public void   setFavoriteId(int s)   { favoriteId = s;    }
    public void	  setId(int s)           { id = s;            }
    public void   setURL(String s)       { URL = s;           }
    public void   setComment(String s)   { comment = s;       }
    public void   setCount(int s)        { count = s;         }
	
	
}
