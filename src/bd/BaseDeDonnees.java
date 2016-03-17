package bd;
import java.io.File;
import static java.lang.System.exit;
import java.sql.*;
import java.util.ArrayList;

public class BaseDeDonnees
{
    private static Connection conn; 
    private Statement stmt;
    
    public BaseDeDonnees() throws SQLException,ClassNotFoundException {
        // Chargement du driver JDBC Oracle
        // Utiliser une des deux m�thodes suivante. Ici, ,la 2�me est utilis�e

        //Class.forName ("org.sqlite.JDBC");
        //DriverManager.registerDriver("org.sqlite.JDBC");

        // Connexion
        conn = DriverManager.getConnection ("jdbc:sqlite:database.db"); 
        stmt = conn.createStatement ();
        
        //conn = DriverManager.getConnection ("jdbc:sqlite:database.db");   
    }
    
    public void creation_tables ()throws SQLException,ClassNotFoundException{
        // Chargement du driver JDBC Oracle
        // Utiliser une des deux m�thodes suivante. Ici, ,la 2�me est utilis�e

        Class.forName ("org.sqlite.JDBC");
        //DriverManager.registerDriver("org.sqlite.JDBC");

        // Connexion

        ResultSet rset;
        
        try{        
            //CREATION DES TABLES 
            stmt.executeUpdate("CREATE TABLE UTILISATEUR (pseudo varchar(20),mdp varchar(20),PRIMARY KEY(pseudo))");
            stmt.executeUpdate("CREATE TABLE MARQUE (nomMarque varchar(20),nationalite varchar(20),PRIMARY KEY(nomMarque))");
            stmt.executeUpdate("CREATE TABLE PRODUIT (idProduit varchar(9),nomProduit varchar(20),nomMarque varchar(20),type varchar(20),couleur varchar(20),poids number(9),prix number(4,2),PRIMARY KEY(idProduit),foreign key(nomMarque) references MARQUE(nomMarque))");
            stmt.executeUpdate("CREATE TABLE PANIER (ID INTEGER PRIMARY KEY,idProduit varchar(9),pseudo varchar(20),prix number(4,2),foreign key(pseudo) references UTILISATEUR(pseudo), foreign key(idProduit) references PRODUIT(idProduit))");
            //stmt.executeUpdate("CREATE TABLE RECHERCHE (idProduit number(9),nomMarque varchar(20),pseudo varchar(20),PRIMARY KEY(idProduit),foreign key(idProduit)references PRODUIT(idProduit),foreign key(nomMarque) references MARQUE(nomMarque),foreign key(pseudo) references UTILISATEUR(pseudo))");
            
            //INSERTION VALEURS TABLES
            //UTILISATEUR
            stmt.executeUpdate("INSERT INTO UTILISATEUR VALUES ('user1','user1')");
            stmt.executeUpdate("INSERT INTO UTILISATEUR VALUES ('user2','user2')");
            
            //MARQUES
            stmt.executeUpdate("INSERT INTO MARQUE VALUES ('Fender','America')");
            stmt.executeUpdate("INSERT INTO MARQUE VALUES ('Gibson','America')");
            stmt.executeUpdate("INSERT INTO MARQUE VALUES ('Yamaha','Japan')");
            stmt.executeUpdate("INSERT INTO MARQUE VALUES ('Vigier','France')");
            stmt.executeUpdate("INSERT INTO MARQUE VALUES ('Charvel','Mexico')");
            
            //PRODUIT
            
            //FENDER
            stmt.executeUpdate("INSERT INTO PRODUIT VALUES ('fend01','Postmodern journeyman','Fender','Stratocaster','Midnight Blue','3kg','3544')");
            stmt.executeUpdate("INSERT INTO PRODUIT VALUES ('fend02','Strato vintage','Fender','Stratocaster','Sunburst','3.2kg','3400')");
            stmt.executeUpdate("INSERT INTO PRODUIT VALUES ('fend03','Custom Shop','Fender','Telecaster','Midnight Blue','3.4kg','5200')");
            stmt.executeUpdate("INSERT INTO PRODUIT VALUES ('fend04','American Special','Fender','Stratocaster','Sandblasted','2.9kg','3150')");

            //GIBSON
            stmt.executeUpdate("INSERT INTO PRODUIT VALUES ('gibs01','Les Paul Studio','Gibson','LP','White Pearl','4.1kg','1200')");
            stmt.executeUpdate("INSERT INTO PRODUIT VALUES ('gibs02','Les Paul Traditional','Gibson','LP','Sunburst','4.2kg','3300')");
            stmt.executeUpdate("INSERT INTO PRODUIT VALUES ('gibs03','Les Paul Melody Maker','Gibson','LP','Midnight Purple','4.6kg','2900')");
            stmt.executeUpdate("INSERT INTO PRODUIT VALUES ('gibs04','Les Paul Standard','Gibson','LP','Stripped Yellow','4kg','2695')");
            
            //YAMAHA
            stmt.executeUpdate("INSERT INTO PRODUIT VALUES ('yam01','Pacifica 112','Yamaha','Stratocaster','Black','4.1kg','400')");
            stmt.executeUpdate("INSERT INTO PRODUIT VALUES ('yam02','Pacifica 612','Yamaha','Stratocaster','Red','4.2kg','1000')");
            stmt.executeUpdate("INSERT INTO PRODUIT VALUES ('yam03','Pacifica 912','Yamaha','Stratocaster','Dark Blue','4.6kg','1500')");
            stmt.executeUpdate("INSERT INTO PRODUIT VALUES ('yam04','Pacfica GPA','Yamaha','Stratocaster','Green Leaf','4kg','2695')");
            
            //VIGIER
            stmt.executeUpdate("INSERT INTO PRODUIT VALUES ('vig01','Vigier Excalibur','Vigier','Stratocaster','Amber','2.9kg','3500')");
            stmt.executeUpdate("INSERT INTO PRODUIT VALUES ('vig02','Vigier Exess Roger','Vigier','Stratocaster','Sunburst','2.2kg','3300')");
            stmt.executeUpdate("INSERT INTO PRODUIT VALUES ('vig03','Vigier Expert Retro','Vigier','Stratocaster','Stonewashed Blue','3.6kg','2860')");
            stmt.executeUpdate("INSERT INTO PRODUIT VALUES ('vig04','Vigier G.V Wood','Vigier','Stratocaster','White Alder ','2.4kg','3156')");
          
            //CHARVEL
            stmt.executeUpdate("INSERT INTO PRODUIT VALUES ('char01','San Dimas','Charvel','Superstratocaster','Red Alert','2.5kg','800')");
            stmt.executeUpdate("INSERT INTO PRODUIT VALUES ('char02','So Cal HH1 ','Charvel','Superstratocaster','Sunburst Blue','2.2kg','950')");
            stmt.executeUpdate("INSERT INTO PRODUIT VALUES ('char03','Super Stock','Charvel','Superstratocaster','Black Pearl','3.6kg','674')");
            stmt.executeUpdate("INSERT INTO PRODUIT VALUES ('char04','Pro Mod','Charvel','Superstratocaster','Viper Green','2.4kg','723')");
            
            
            
            /* rset = stmt.executeQuery("SELECT * from UTILISATEUR");
            while (rset.next ())
                   System.out.println (rset.getString(1)+" "+rset.getString(2));*/

        }
        catch(SQLException e){
            System.err.println(e.getMessage());
            exit(1);
        }
    }
    
    public boolean connexion_user(String pseudo, String mdp)
    {
        ResultSet rset;
        String[] idenditification = new String[2];
        try{
            rset = stmt.executeQuery("SELECT * FROM UTILISATEUR WHERE PSEUDO='"+pseudo+"' AND MDP='"+mdp+"'");
            while (rset.next())
            {
                idenditification[0] = rset.getString(1);
                idenditification[1] = rset.getString(2);
            }
        }
        catch(SQLException e){
            System.err.println(e.getMessage());
            exit(2);
        }
        if (((idenditification[0]!=null) && (idenditification[0].equals(pseudo)) && (idenditification[1]!=null) && (idenditification[1].equals(mdp))))
        {
            return true;
        }
        else
        {
            System.err.println("Erreur lors de la connexion. Veuillez réessayer");
            return false;
        }
    }
    
    public Statement getStatement()
    {
        return stmt;
    }
}
