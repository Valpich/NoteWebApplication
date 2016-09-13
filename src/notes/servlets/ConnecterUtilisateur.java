package notes.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.jasypt.util.password.ConfigurablePasswordEncryptor;

import notes.beans.DBInterface;
import notes.beans.PasswordStorage;
import notes.beans.PasswordStorage.CannotPerformOperationException;
import notes.beans.PasswordStorage.InvalidHashException;
import notes.beans.Utilisateur;

@WebServlet( "/ConnecterUtilisateur" )
public class ConnecterUtilisateur extends HttpServlet {
    private static final long   serialVersionUID = 1L;
    private static final String VUE              = "/WEB-INF/connexion.jsp";
    private static final String VUE_PRINCIPALE   = "/notes/Menu";
    private DBInterface         db;
    boolean                     erreurMDP        = false;
    boolean                     erreurLogin      = false;
    String                      resultat;

    public ConnecterUtilisateur() {
        db = new DBInterface();
    }
    
    protected void doGet( HttpServletRequest request, HttpServletResponse response )
            throws ServletException, IOException {

        this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
    }

    protected void doPost( HttpServletRequest request, HttpServletResponse response )
            throws ServletException, IOException {
        Utilisateur tmp = authentifierUtilisateur( request );
        if ( erreurMDP == true ) {
            request.setAttribute( "erreurMDP", "Mot de passe incorrect !" );
            this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
        } else if ( erreurLogin == true ) {
            request.setAttribute( "erreurLogin", "Login non présent !" );
            this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
        } else {
            HttpSession session = request.getSession();
            session.setAttribute( "utilisateur", tmp );
            response.sendRedirect( VUE_PRINCIPALE );
        }
    }

    private Utilisateur authentifierUtilisateur( HttpServletRequest request ) {
        Utilisateur tmp = null;
        Boolean test=null;
        tmp = db.chercherUtilisateur( getValeurChamp( request, "login" ) );
        if ( tmp != null ) {
            String motDePasse = getValeurChamp( request, "motDePasse" );
            try {
                test  = PasswordStorage.verifyPassword( motDePasse, tmp.getMotDePasse() );
            } catch ( CannotPerformOperationException | InvalidHashException e ) {
                e.printStackTrace();
            }
            if ( test == true ) {
                return tmp;
            } else {
                erreurMDP = true;
                return null;
            }
        }
        erreurLogin = true;
        return tmp;
    }

    private static String getValeurChamp( HttpServletRequest request, String nomChamp ) {
        String valeur = request.getParameter( nomChamp );
        if ( valeur == null || valeur.trim().length() == 0 ) {
            return null;
        } else {
            return valeur.trim();
        }
    }

}
