package notes.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jasypt.util.password.ConfigurablePasswordEncryptor;

import notes.beans.DBInterface;
import notes.beans.PasswordStorage;
import notes.beans.PasswordStorage.CannotPerformOperationException;
import notes.beans.PasswordStorage.InvalidHashException;
import notes.beans.Utilisateur;

@WebServlet( "/CreerUtilisateur" )
public class CreerUtilisateur extends HttpServlet {
    private static final long   serialVersionUID = 1L;
    private static final String VUE              = "/WEB-INF/inscription.jsp";
    private static final String VUE_PRINCIPALE   = "/notes/Menu";

    private DBInterface         db;
    private boolean                     erreurMDP        = false;
    private boolean                     erreurLogin      = false;
    private boolean                     erreurADMIN      = false;

    public CreerUtilisateur() {
        super();
        db = new DBInterface();
    }

    protected void doGet( HttpServletRequest request, HttpServletResponse response )
            throws ServletException, IOException {

        this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
    }

    protected void doPost( HttpServletRequest request, HttpServletResponse response )
            throws ServletException, IOException {

        Utilisateur tmp = inscrireUtilisateur( request );
        if ( erreurMDP == true ) {
            request.setAttribute( "erreurMDP", "Mot de passe non conforme !" );
            if ( erreurLogin == true ) {
                request.setAttribute( "erreurLogin", "Login non conforme !" );
                if ( erreurADMIN == true ) {
                    request.setAttribute( "erreurADMIN", "Mot de passe admin faux !" );
                }
                this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
            }
        } else {
            if ( erreurLogin == true ) {
                request.setAttribute( "erreurLogin", "Login non conforme !" );
                if ( erreurADMIN == true ) {
                    request.setAttribute( "erreurADMIN", "Mot de passe admin faux !" );
                }
                this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
            } else {
                if ( erreurADMIN == true ) {
                    request.setAttribute( "erreurADMIN", "Mot de passe admin faux !" );
                    this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
                } else {
                    request.setAttribute( "utilisateur", tmp );
                    response.sendRedirect( VUE_PRINCIPALE );
                }
            }
        }
    }

    public Utilisateur inscrireUtilisateur( HttpServletRequest request ) {
        String login = getValeurChamp( request, "login" );
        String motDePasse = getValeurChamp( request, "motDePasse" );
        String confirmation = getValeurChamp( request, "confirmation" );
        String admin = getValeurChamp( request, "admin" );
        Utilisateur utilisateur = new Utilisateur();
        erreurMDP = traiterMotsDePasse( motDePasse, confirmation, utilisateur );
        erreurLogin = traiterLogin( login, utilisateur );
        erreurADMIN = traiterAdmin( admin );
        if ( erreurMDP == false && erreurLogin == false && erreurADMIN == false ) {
            try {
                String hashMDP = PasswordStorage.createHash( utilisateur.getMotDePasse() );
                Boolean test = false;
                try {
                    test = PasswordStorage.verifyPassword( utilisateur.getMotDePasse(), hashMDP );
                } catch ( InvalidHashException e ) {
                    e.printStackTrace();
                }
                if ( test == true )
                    db.ajouterUtilisateur( utilisateur.getLogin(), hashMDP );
                else
                    erreurMDP = true;
            } catch ( CannotPerformOperationException e ) {
                erreurMDP = true;
            }
        }
        return utilisateur;
    }

    public boolean traiterAdmin( String admin ) {
        try {
            if ( PasswordStorage.verifyPassword( admin, db.chercherUtilisateur( "admin" ).getMotDePasse() ) == true )
                return false;
        } catch ( CannotPerformOperationException e ) {
            e.printStackTrace();
        } catch ( InvalidHashException e ) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean traiterLogin( String login, Utilisateur utilisateur ) {
        if ( login == null )
            return true;
        if ( login.length() < 5 )
            return true;
        if ( db.chercherUtilisateur( login ) != null )
            return true;
        utilisateur.setLogin( login );
        return false;
    }

    public boolean validationMotsDePasse( String motDePasse, String confirmation ) {
        if ( motDePasse == null )
            return true;
        if ( !motDePasse.equals( confirmation ) ) {
            return true;
        } else {
            if ( motDePasse.length() < 5 )
                return true;
            else
                return false;
        }
    }

    private boolean traiterMotsDePasse( String motDePasse, String confirmation, Utilisateur utilisateur ) {
        if ( !validationMotsDePasse( motDePasse, confirmation ) == false )
            return true;
        utilisateur.setMotDePasse( motDePasse );
        return false;
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
