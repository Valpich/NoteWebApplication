package notes.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import notes.beans.DBInterface;
import notes.beans.Utilisateur;

@WebServlet( "/AfficherClassement" )
public class AfficherClassement extends HttpServlet {
    private static final long   serialVersionUID = 1L;
    private static final String VUE              = "/WEB-INF/classement.jsp";
    private static final String SESSION_ELEVES   = "eleves";
    private static final String VUE_CON          = "/notes/Menu";
    private DBInterface         db;

    public AfficherClassement() {
        super();
        db = new DBInterface();
    }

    protected void doGet( HttpServletRequest request, HttpServletResponse response )
            throws ServletException, IOException {
        DBInterface db = new DBInterface();
        int test = db.recupererClassement();
        HttpSession session = request.getSession();
        Utilisateur utilisateur = (Utilisateur) session.getAttribute( "utilisateur" );
        if ( utilisateur != null ) {
            if ( test != 0 ) {
                session.setAttribute( SESSION_ELEVES, db.getEleves() );
            }
            this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
        } else {
            response.sendRedirect( VUE_CON );
        }
    }

}
