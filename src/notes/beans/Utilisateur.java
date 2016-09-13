package notes.beans;

import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;

public class Utilisateur {

    private Long      id;
    private String    login;
    private String    motDePasse;
    private Timestamp dateInscription;

    public Long getId() {
        return id;
    }

    public void setId( Long id ) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin( String login ) {
        this.login = login;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse( String motDePasse ) {
        this.motDePasse = motDePasse;
    }

    public Timestamp getDateInscription() {
        return dateInscription;
    }

    public void setDateInscription( Timestamp dateInscription ) {
        this.dateInscription = dateInscription;
    }

}