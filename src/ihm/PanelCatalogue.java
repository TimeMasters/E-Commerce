package ihm;

import bd.BaseDeDonnees;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


public class PanelCatalogue extends JPanel implements ActionListener
{
    private JLabel user_connexion;
    private String pseudo;
    private BaseDeDonnees baseDonnees;
    private JPanel marquePanel;
    private JPanel produitPanel;
    private JPanel descriptionPanel;
    private PanelPanier panierPanel;
    private JButton ajoutPanier;
    private JButton retirerPanier;
    private JButton validerPanier;
    private JButton logout_user;
    private JButton viderPanier;
    private FenetreMere fenetreMere;
    private JLabel[] labelPanel;
    private String idProduitCourant;
    private ArrayList<BoutonID> boutonsMarques;
    private ArrayList<BoutonID> boutonsProduits;
    
    private ArrayList <String> actionCommand = new ArrayList<>();
    private ArrayList <String> actionCommandMarque = new ArrayList<>();
    private ArrayList <String> actionCommandProduit = new ArrayList<>();
    
    
    public PanelCatalogue(BaseDeDonnees bd,String username,FenetreMere fenetreParent)
    {
        fenetreMere = fenetreParent;
        baseDonnees=bd;
        pseudo = username;
        
        this.validerPanier = new JButton();
        
        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints contrainte = new GridBagConstraints();
        this.setLayout(layout);
        
        this.user_connexion= new JLabel("Connecté en tant que "+pseudo);
        contrainte.gridx = 1; contrainte.gridy=0; contrainte.anchor = GridBagConstraints.WEST; contrainte.gridwidth=2; contrainte.insets = new Insets(10, 10, 10, 10);
        this.add(user_connexion,contrainte);
        
        this.logout_user = new JButton("Déconnexion");
        contrainte.gridx=4; contrainte.gridwidth=0; contrainte.gridwidth = 1;
        logout_user.addActionListener(this);
        this.add(logout_user, contrainte);
        
        this.viderPanier = new JButton("Vider le panier");
        contrainte.gridy = 1;
        viderPanier.addActionListener(this);
        this.add(viderPanier,contrainte);
        
        this.marquePanel = new JPanel();
        marquePanel.setLayout(new GridBagLayout());
        this.marquePanel.setPreferredSize(new Dimension(125,200));
        marquePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        contrainte.gridx=1; contrainte.gridy=3; contrainte.anchor = GridBagConstraints.CENTER;
        this.add(marquePanel,contrainte);
        
        this.produitPanel = new JPanel();
        produitPanel.setLayout(new GridBagLayout());
        produitPanel.setPreferredSize(new Dimension(250,200));
        produitPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        contrainte.gridx= 2;
        this.add(produitPanel, contrainte);
        
        this.descriptionPanel = new JPanel();
        descriptionPanel.setLayout(new GridBagLayout());
        descriptionPanel.setPreferredSize(new Dimension(225,250));
        descriptionPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        contrainte.gridx = 3;
        this.add(descriptionPanel, contrainte);
        
        contrainte.gridx=4;
        this.panierPanel = new PanelPanier(baseDonnees,pseudo,this);
        panierPanel.setPreferredSize(new Dimension(250, 200));
        this.add(panierPanel,contrainte);
        
        this.retirerPanier = new JButton("Retirer du panier");
        retirerPanier.setEnabled(false);
        contrainte.gridx = 2; contrainte.gridy=4;
        retirerPanier.addActionListener(this);
        this.add(retirerPanier,contrainte);
        
        this.ajoutPanier = new JButton("Ajouter au panier");
        ajoutPanier.setEnabled(false);
        contrainte.gridx=3;
        ajoutPanier.addActionListener(this);
        this.add(ajoutPanier, contrainte);
        
        contrainte.gridx=4;
        validerPanier.addActionListener(this);
        this.add(validerPanier, contrainte);
        
        labelPanel = new JLabel[3];
        contrainte = new GridBagConstraints(); // réinitialistation des contraintes
        contrainte.gridx = 0; contrainte.gridy= 0;contrainte.insets = new Insets(3, 3, 3, 3);
        
        labelPanel[0] = new JLabel("Marques");
        marquePanel.add(labelPanel[0], contrainte);
        boutonsMarques = new ArrayList<>();
        try{
        boutonsMarques = this.renvoieButtonsMarque();
        }
        catch(SQLException e){
            System.err.println(e.getMessage());
        }
        if (!boutonsMarques.isEmpty()) //Si boutonsMarque n'est pas vide
        {
            Iterator <BoutonID> iterateur = boutonsMarques.iterator();
            while (iterateur.hasNext())
            {
                contrainte.gridy+=1;
                BoutonID boutonCourant = iterateur.next();
                boutonCourant.setActionCommand(boutonCourant.getIDBouton());
                boutonCourant.addActionListener(this);
                actionCommandMarque.add(boutonCourant.getIDBouton());
                marquePanel.add(boutonCourant, contrainte);
            }
        }
        
        contrainte.gridy=0;
        labelPanel[1] = new JLabel("Produits");
        produitPanel.add(labelPanel[1], contrainte);
        labelPanel[2] = new JLabel("Description");
        descriptionPanel.add(labelPanel[2], contrainte);
    }

    @Override
    public void actionPerformed(ActionEvent parEvt) 
    {
        String command = ((JButton)parEvt.getSource()).getActionCommand();
        actionCommand.clear();
        actionCommand.addAll(actionCommandMarque);
        actionCommand.addAll(actionCommandProduit);
        if (!actionCommand.contains(command))
        {
            if (parEvt.getSource() == logout_user)
            {
                pseudo = null;
                fenetreMere.setPanel(new PanelConnexion(baseDonnees,fenetreMere));
                updateUI();
            }
            else if ((parEvt.getSource() == ajoutPanier) && (idProduitCourant != null))
            {
                
                try{
                panierPanel.createProduitPanier(idProduitCourant);
                }catch(SQLException e)
                {
                    System.err.println(e.getMessage());
                }
            }
            else if (parEvt.getSource() == validerPanier || parEvt.getSource() == viderPanier)
            {
                try{
                    panierPanel.viderPanier();
                    panierPanel.calculPrix();
                }catch(SQLException e){
                    System.err.println(e.getMessage());
                }
                updateUI();
                if (parEvt.getSource() == validerPanier)
                    JOptionPane.showMessageDialog(null,"Merci pour votre achat !");
            }
            else if (parEvt.getSource() == retirerPanier)
            {
                try{
                    panierPanel.suppressionElmtPanier(idProduitCourant);
                }catch(SQLException e){
                    System.err.println(e.getMessage());
                }
            }
        }
        else
        {
            if (actionCommandMarque.contains(command))
            {
                idProduitCourant=null;
                try{
                    boutonsProduits = renvoieNomProduit(parEvt.getActionCommand());
                }catch(SQLException e)
                {
                    System.err.println(e.getMessage());
                }
                afficheBoutonsProduit(boutonsProduits);
                
                retirerPanier.setEnabled(false);
                ajoutPanier.setEnabled(false);
                descriptionPanel.removeAll();
                descriptionPanel.add(labelPanel[2]);
                updateUI();
            }
            else if(actionCommandProduit.contains(command))
            {
                String[] intitulesLabel = {"Nom : ","Origine : ","Type : ","Couleur : ","Poids : ","Prix : "};
                JLabel[] labelDescr = new JLabel[6];
                String[] descrLabel = new String[6];
                descriptionPanel.removeAll();
                GridBagConstraints contrainte = new GridBagConstraints();
                contrainte.insets = new Insets(10, 10, 10, 10); contrainte.anchor = GridBagConstraints.CENTER;
                descriptionPanel.add(new JLabel("Description"),contrainte);
                contrainte.gridy = 1; contrainte.anchor = GridBagConstraints.WEST;
                try{
                    descrLabel = returnDescriptionProduit(parEvt.getActionCommand());
                }catch(SQLException e)
                {
                    System.err.println(e.getMessage());
                }
                for (int i = 0; i<6;i++)
                {
                    if (i<5)
                        labelDescr[i] = new JLabel(intitulesLabel[i] + descrLabel[i]);
                    else
                        labelDescr[i] = new JLabel(intitulesLabel[i] + descrLabel[i]+"€"); 
                    descriptionPanel.add(labelDescr[i], contrainte);
                    contrainte.gridy++;
                }
                idProduitCourant=command;
                try{
                if (panierPanel.estDansPanier(idProduitCourant))
                    retirerPanier.setEnabled(true);
                else
                    retirerPanier.setEnabled(false);
                }catch(SQLException e){
                    System.err.println(e.getMessage());
                }
                ajoutPanier.setEnabled(true);
                updateUI();
            }
        }
    }
    
    private ArrayList<BoutonID> renvoieButtonsMarque() throws SQLException
    {
        ArrayList<BoutonID> boutonsRetour = new ArrayList<>();
        ResultSet rset;
        
        rset = baseDonnees.getStatement().executeQuery("Select * from MARQUE");
        while(rset.next())
        {
            boutonsRetour.add(new BoutonID(rset.getString(1),rset.getString(1)));
        }
        return boutonsRetour;
    }
    
    private ArrayList<BoutonID> renvoieNomProduit(String nomMarque) throws SQLException
    {
        ArrayList<BoutonID> boutonsRetour = new ArrayList<>();
        ResultSet rset;
        
        rset = baseDonnees.getStatement().executeQuery("Select * from PRODUIT where nomMarque='"+nomMarque+"'");
        while(rset.next())
        {
            boutonsRetour.add(new BoutonID(rset.getString(2),rset.getString(1)));
        }
        return boutonsRetour;
    }
    
    private void afficheBoutonsProduit(ArrayList<BoutonID> parBoutonsProduits)
    {
        GridBagConstraints contrainte = new GridBagConstraints();
        if (!parBoutonsProduits.isEmpty()) //Si c'est pas vide
        {
            Iterator <BoutonID> iterateur = boutonsProduits.iterator();
            actionCommandProduit.clear();
            contrainte.gridy = 0; contrainte.insets = new Insets(3,3,3,3);
            produitPanel.removeAll();
            produitPanel.add(this.labelPanel[1], contrainte);
            contrainte.gridy = 1;
            while (iterateur.hasNext())
            {
                contrainte.gridy+=1;
                BoutonID boutonCourant = iterateur.next();
                boutonCourant.setActionCommand(boutonCourant.getIDBouton());
                boutonCourant.addActionListener(this);
                actionCommandProduit.add(boutonCourant.getIDBouton());
                produitPanel.add(boutonCourant, contrainte);
            }  
            updateUI();
        }
    }
    
    private String[] returnDescriptionProduit(String idProduit) throws SQLException
    {
        String[] stringRetour = new String[6];
        ResultSet rset;
        
        rset = baseDonnees.getStatement().executeQuery("SELECT nomProduit,nationalite,type,couleur,poids,prix FROM PRODUIT,MARQUE WHERE PRODUIT.nomMarque=MARQUE.nomMarque and idProduit ='"+idProduit+"'");
        for (int i = 0; i<6;i++)
            stringRetour[i] = rset.getString(i+1);
        
        return stringRetour;
    }

    public JButton getValiderButton()
    {
        return this.validerPanier;
    }
    
    public JButton getViderPanierButton()
    {
        return this.viderPanier;
    }
    
    public void retirerPanierBoutonSetEnable(boolean bool)
    {
        retirerPanier.setEnabled(bool);
    }
}
