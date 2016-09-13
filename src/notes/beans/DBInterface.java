package notes.beans;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBInterface {
    private final static String   DEFAULT_URL      = "jdbc:mysql://mysql-eseo.alwaysdata.net/eseo_projet_java";
    private final static String   DEFAULT_USER     = "eseo";
    private final static String   DEFAULT_PASSWORD = "eseo";
    private final static String[] NOM_UE           = { "Modélisation et persistance des données",
                                                           "Modélisation et spécification des systèmes",
                                                           "Systèmes informatiques",
                                                           "Réseaux",
                                                           "Electronique Hautes Fréquences et Hyperfréquences",
                                                           "Transmissions RF",
                                                           "Traitement numérique du signal et de l'image",
                                                           "Automatique", "Microcontrôleurs",
                                                           "Communication", "Séminaire entreprise", "Anglais",
                                                           "Stage Découverte de l'Entreprise" };
    private Connection            connection;
    private PreparedStatement             preparedStatement;
    private String                noms[];
    private float                 moyennes[];
    private int                   classements[];
    private int                   index;
    private Eleve                 eleves[];
    private EleveComplet          complet[];
    private String                admin;

    public DBInterface() {
        try {
            Class.forName( "com.mysql.jdbc.Driver" );
        } catch ( ClassNotFoundException e ) {
            e.printStackTrace();
        }
    }

    public void ajouterEtudiant( String nom, int id, float moyenne ) {
        try {
            connection = DriverManager.getConnection( DEFAULT_URL, DEFAULT_USER, DEFAULT_PASSWORD );
        } catch ( SQLException e1 ) {
            e1.printStackTrace();
        }

        PreparedStatement preparedStatement = null;
        String sql = "INSERT INTO `Classement` (`ID`, `Nom`, `ID_ESEO`, `Moyenne`) VALUES(NULL,?,?,?);";
        String sql_update = "UPDATE  `eseo_projet_java`.`Classement` SET  `Moyenne` =  ? WHERE  `Classement`.`Nom` = ?";
        try {
            preparedStatement = connection.prepareStatement( sql );
        } catch ( SQLException e2 ) {
            e2.printStackTrace();
        }

        try {
            preparedStatement.setString( 1, nom );
        } catch ( SQLException e ) {
            e.printStackTrace();
        }
        try {
            preparedStatement.setInt( 2, id );
        } catch ( SQLException e1 ) {
            e1.printStackTrace();
        }
        try {
            preparedStatement.setFloat( 3, moyenne );
        } catch ( SQLException e ) {
            e.printStackTrace();
        }

        try {
            preparedStatement.executeUpdate();
        } catch ( com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException ex ) {
            try {
                preparedStatement = connection.prepareStatement( sql_update );
            } catch ( SQLException e2 ) {
                e2.printStackTrace();
            }
            try {
                preparedStatement.setFloat( 1, moyenne );
            } catch ( SQLException e ) {
                e.printStackTrace();
            }
            try {
                preparedStatement.setString( 2, nom );
            } catch ( SQLException e1 ) {
                e1.printStackTrace();
            }
            try {
                preparedStatement.executeUpdate();
            } catch ( SQLException e ) {
                e.printStackTrace();

            }
        } catch ( SQLException e ) {
            e.printStackTrace();
        }
    }

    public void recupererEtudiant( int id ) {
        complet = new EleveComplet[13];
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            try {
                connection = DriverManager.getConnection( DEFAULT_URL, DEFAULT_USER, DEFAULT_PASSWORD );
            } catch ( SQLException e1 ) {
                e1.printStackTrace();
            }
            String requete = "SELECT *  FROM Notes WHERE ID_ESEO=?";
            try {
                preparedStatement = connection.prepareStatement( requete );
            } catch ( SQLException e2 ) {
                e2.printStackTrace();
            }
            preparedStatement.setInt( 1, id );
            resultSet = preparedStatement.executeQuery();
            if ( resultSet.next() ) {
                int i = 0;
                int j = 4;
                for ( String tmp : NOM_UE ) {
                    complet[i] = new EleveComplet();
                    complet[i].setUE( tmp );
                    complet[i].setCoef( resultSet.getFloat( j ) );
                    complet[i].setNote( resultSet.getFloat( j + 1 ) );
                    i++;
                    j += 2;
                }
            }
        } catch ( SQLException e ) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch ( Exception e ) {
                e.printStackTrace();
            }
        }

    }

    public int recupererClassement() {
        noms = new String[300];
        moyennes = new float[300];
        classements = new int[300];
        index = 0;
        try {
            connection = DriverManager.getConnection( DEFAULT_URL, DEFAULT_USER, DEFAULT_PASSWORD );
        } catch ( SQLException e1 ) {
            e1.printStackTrace();
        }
        PreparedStatement preparedStatement = null;
        String sql = "SELECT    Nom, Moyenne,@curRank := @curRank + 1 AS Classement FROM Classement c, (SELECT @curRank := 0) r ORDER BY  Moyenne DESC LIMIT 0,300;";
        try {
            preparedStatement = connection.prepareStatement( sql );
        } catch ( SQLException e2 ) {
            e2.printStackTrace();
        }
        ResultSet rs = null;
        try {
            rs = preparedStatement.executeQuery();
        } catch ( SQLException e3 ) {
            e3.printStackTrace();
        }

        try {
            while ( rs.next() ) {
                noms[index] = rs.getString( "Nom" );
                moyennes[index] = Float.parseFloat( rs.getString( "Moyenne" ) );
                classements[index] = Integer.parseInt( rs.getString( "Classement" ) );
                index++;
            }
            eleves = new Eleve[index];
            for ( int i = 0; i < index; i++ ) {
                eleves[i] = new Eleve( noms[i], moyennes[i], classements[i] );
            }
        } catch ( SQLException e ) {
            e.printStackTrace();
        }
        return index;
    }

    public Utilisateur chercherUtilisateur( String login ) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Utilisateur utilisateur = null;
        try {
            try {
                connection = DriverManager.getConnection( DEFAULT_URL, DEFAULT_USER, DEFAULT_PASSWORD );
            } catch ( SQLException e1 ) {
                e1.printStackTrace();
            }
            String requete = "SELECT *  FROM Utilisateurs WHERE login = ?";
            try {
                preparedStatement = connection.prepareStatement( requete );
            } catch ( SQLException e2 ) {
                e2.printStackTrace();
            }
            preparedStatement.setString( 1, login );
            resultSet = preparedStatement.executeQuery();
            if ( resultSet.next() ) {
                utilisateur = new Utilisateur();
                utilisateur.setId( resultSet.getLong( "ID" ) );
                utilisateur.setLogin( resultSet.getString( "login" ) );
                utilisateur.setMotDePasse( resultSet.getString( "motDePasse" ) );
                utilisateur.setDateInscription( resultSet.getTimestamp( "dateInscription" ) );
            }
        } catch ( SQLException e ) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch ( Exception e ) {
                e.printStackTrace();
            }
        }
        return utilisateur;
    }

    public void ajouterUtilisateur( String login, String motDePasse ) {
        PreparedStatement preparedStatement = null;
        try {
            try {
                connection = DriverManager.getConnection( DEFAULT_URL, DEFAULT_USER, DEFAULT_PASSWORD );
            } catch ( SQLException e1 ) {
                e1.printStackTrace();
            }
            String requete = "INSERT INTO Utilisateurs (ID ,login ,motDePasse ,dateInscription)VALUES (NULL ,  ?,  ?,  NOW());";
            try {
                preparedStatement = connection.prepareStatement( requete );
            } catch ( SQLException e2 ) {
                e2.printStackTrace();
            }
            preparedStatement.setString( 1, login );
            preparedStatement.setString( 2, motDePasse );
            preparedStatement.executeUpdate();
        } catch ( SQLException e ) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch ( SQLException e ) {
                e.printStackTrace();
            }
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection( Connection connection ) {
        this.connection = connection;
    }

    public PreparedStatement getPreparedStatement() {
        return preparedStatement;
    }

    public void setPreparedStatement( PreparedStatement preparedStatement ) {
        this.preparedStatement = preparedStatement;
    }

    public String[] getNoms() {
        return noms;
    }

    public void setNoms( String[] noms ) {
        this.noms = noms;
    }

    public float[] getMoyennes() {
        return moyennes;
    }

    public void setMoyennes( float[] moyennes ) {
        this.moyennes = moyennes;
    }

    public int[] getClassements() {
        return classements;
    }

    public void setClassements( int[] classements ) {
        this.classements = classements;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex( int index ) {
        this.index = index;
    }

    public Eleve[] getEleves() {
        return eleves;
    }

    public void setEleves( Eleve[] eleves ) {
        this.eleves = eleves;
    }

    public EleveComplet[] getComplet() {
        return complet;
    }

    public void setComplet( EleveComplet[] complet ) {
        this.complet = complet;
    }

    public String getAdmin() {
        try {
            connection = DriverManager.getConnection( DEFAULT_URL, DEFAULT_USER, DEFAULT_PASSWORD );
        } catch ( SQLException e1 ) {
            e1.printStackTrace();
        }
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM Utilisateurs WHERE login=\"admin\";";
        try {
            preparedStatement = connection.prepareStatement( sql );
        } catch ( SQLException e2 ) {
            e2.printStackTrace();
        }
        try {
            rs = preparedStatement.executeQuery();
        } catch ( SQLException e3 ) {
            e3.printStackTrace();
        }
        try {
            if ( rs.next() ) {
                setAdmin( rs.getString( "motDePasse" ) );
            }
        } catch ( SQLException e ) {
            e.printStackTrace();
        }
        return admin;
    }

    public void setAdmin( String admin ) {
        this.admin = admin;
    }

}
