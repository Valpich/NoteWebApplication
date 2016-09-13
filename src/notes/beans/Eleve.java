package notes.beans;

public class Eleve {
    private String nom;
    private float moyenne;
    private int classement;
    
    public Eleve(String nom, float moyenne, int classement){
        setNom(nom);
        setMoyenne(moyenne);
        setClassement(classement);
    }
    
    public String getNom() {
        return nom;
    }
    public void setNom( String nom ) {
        this.nom = nom;
    }
    public float getMoyenne() {
        return moyenne;
    }
    public void setMoyenne( float moyenne ) {
        this.moyenne = moyenne;
    }
    public int getClassement() {
        return classement;
    }
    public void setClassement( int classement ) {
        this.classement = classement;
    }
    
}
