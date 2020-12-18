package sessionTimeout;

import java.util.Hashtable;
import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import beans.LoginBean;
import beans.LogoutBean;

public class SessionCounterListener implements HttpSessionListener {
	 private static int totalActiveSessions;
	 
	  public static int getTotalActiveSession(){
		return totalActiveSessions;
	  }
	 
	  public void sessionCreated(HttpSessionEvent arg0) {
		totalActiveSessions++;
		System.out.println("sessionCreated - add one session into counter");
	  }
	 
	  @Override
	  public void sessionDestroyed(HttpSessionEvent arg0) {
		totalActiveSessions--;
		HttpSession session = arg0.getSession();
		
		System.out.println("session.getLastAccessedTime()"+session.getLastAccessedTime());
		System.out.println("session.getId()"+session.getId());
		System.out.println("session.getCreationTime()"+session.getCreationTime());
//		System.out.println("session.getCreationTime()"+session.getCreationTime());
		System.out.println("sessionDestroyed - deduct one session from counter");
		prepareLogoutInfoAndLogoutActiveUser(session);
	  }
	  public void prepareLogoutInfoAndLogoutActiveUser(HttpSession httpSession) {
		    Hashtable user = (Hashtable) httpSession.getAttribute("User");
		    System.out.println("Unlocked examination for user: "+user);
		   LogoutBean lob = new LogoutBean<>();
		   try {
			   if(user!=null)
			lob.getRedirect(String.valueOf(user.get("username")), user,false);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		    
		}
}
