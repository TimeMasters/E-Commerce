package ihm;

import javax.swing.JButton;


public class BoutonID extends JButton
{
    private String idBouton;
    
    //Construteur de base
    public BoutonID(String nomBouton)
    {
        super(nomBouton);
    }
    
    //Constructeur avec bouton
    public BoutonID(String nomBouton, String parIDBouton)
    {
        super(nomBouton);
        idBouton = parIDBouton;
    }
    
    public String getIDBouton()
    {
        return idBouton;
    }
    
    public void setIDBouton(String parIDBouton)
    {
        idBouton = parIDBouton;
    }
}
