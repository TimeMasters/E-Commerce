package ihm;

import bd.BaseDeDonnees;
import java.awt.Color;
import java.awt.Insets;
import java.io.File;
import static java.lang.System.exit;
import java.sql.SQLException;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JPanel;


public class FenetreMere extends JFrame
{
    private BaseDeDonnees baseDonnees;
    
    public FenetreMere ()
    {
        super ("Projet JDBC");
        boolean bdExiste = new File("database.db").exists();
        try{
            baseDonnees = new BaseDeDonnees();
            if (bdExiste == false)
                baseDonnees.creation_tables();
        }
        catch (SQLException | ClassNotFoundException e){
            System.err.println(e.getMessage());
            exit(1);
        }
        setContentPane (new PanelConnexion(baseDonnees,this)); //JFrame reçoit le panneau conteneur
        this.setBackground(new Color(0, 51, 102));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1000,600); setVisible(true); setLocation(200,200);

    }
    
    public void setPanel(JPanel panel)
    {
        this.getContentPane().removeAll();
        this.getContentPane().add(panel);
        revalidate();
        this.repaint();
    }
    
    public Insets getInsets ()
    {
        return new Insets (40,15,15,15); //affiche des bordures (top,left,bottom,right)
    }	//getInsets ()
        
    public static void main(String [] args)
    {
        new FenetreMere();
    }
}