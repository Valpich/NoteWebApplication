package notes.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import notes.beans.DBInterface;
import notes.beans.Data;
import notes.beans.EleveComplet;
import notes.beans.ParsingTools;
import notes.beans.Utilisateur;

@WebServlet( "/AfficherEleve" )
public class AfficherEleve extends HttpServlet {
    private static final long    serialVersionUID = 1L;
    private static final String  VUE              = "/WEB-INF/noteEleve.jsp";
    private static final String  VUE_CON          = "/notes/Menu";
    private static final String  VUE_AUTRE          = "/notes/AfficherEleve";
    public final static String[] NOM_UE           = { "Modélisation et persistance des données",
                                                          "Modélisation et spécification des systèmes",
                                                          "Systèmes informatiques",
                                                          "Réseaux",
                                                          "Electronique Hautes Fréquences et Hyperfréquences",
                                                          "Transmissions RF",
                                                          "Traitement numérique du signal et de l'image",
                                                          "Automatique", "Microcontrôleurs",
                                                          "Communication", "Séminaire entreprise", "Anglais",
                                                          "Stage Découverte de l'Entreprise" };
    private DBInterface          db;

    public AfficherEleve() {
        super();
        db = new DBInterface();
    }

    protected void doGet( HttpServletRequest request, HttpServletResponse response )
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Utilisateur utilisateur = (Utilisateur) session.getAttribute( "utilisateur" );
        session.setAttribute( "listeEleve", ParsingTools.creerMap().keySet() );
        if ( utilisateur != null ) {
            this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
        } else {
            response.sendRedirect( VUE_CON );
        }
    }

    protected void doPost( HttpServletRequest request, HttpServletResponse response )
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Utilisateur utilisateur = (Utilisateur) session.getAttribute( "utilisateur" );
        String valeurChoix = getValeurChamp( request, "listeEleve" );
        if ( utilisateur != null && !valeurChoix.equals( "choixEleve" ) ) {
            int id = new Data().getMap().get( valeurChoix );
            db.recupererEtudiant( id );
            session.setAttribute( "eleveComplet", db.getComplet() );
            float moyenne = 0;
            float sommeCoeff = 0;
            for ( EleveComplet eleve : db.getComplet() ) {
                if ( eleve.getNote() != -1 && eleve.getCoef() != -1 ) {
                    moyenne += eleve.getNote()*eleve.getCoef();
                    sommeCoeff += eleve.getCoef();
                }
            }
            moyenne /= sommeCoeff;
            session.setAttribute( "sommeCoeff", sommeCoeff );
            session.setAttribute( "moyenne", moyenne );
            session.setAttribute( "nomEleve", valeurChoix );
            this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
        } else {
            if(utilisateur == null)response.sendRedirect( VUE_CON );
            else response.sendRedirect( VUE_AUTRE );
        }
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
