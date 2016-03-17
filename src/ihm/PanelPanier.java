package ihm;

import bd.BaseDeDonnees;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author jpgoodwin
 */
public class PanelPanier extends JPanel
{
    private BaseDeDonnees baseDonnees;
    private GridBagConstraints contrainte;
    private String pseudo;
    private JLabel labelTitre = new JLabel("Panier");
    private HashMap<String,JLabel[]> quantiteMap;
    private PanelCatalogue panelPere;
    
    public PanelPanier(BaseDeDonnees bd,String username,PanelCatalogue panelParent)
    {
        baseDonnees = bd;
        pseudo = username;
        panelPere = panelParent;
        quantiteMap = new HashMap<>();

        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        contrainte = new GridBagConstraints();
        contrainte.gridwidth = 2; contrainte.gridy = 0;
        this.add(labelTitre,contrainte);
        contrainte.gridwidth=1; contrainte.gridy=1; contrainte.gridx = 0; contrainte.insets = new Insets(3,3,3,3);
        try{
            lecturePanier();
            calculPrix();
        }catch(SQLException e){
            System.err.println(e.getMessage());
        }
    }
    
    public void createProduitPanier(String idProduit) throws SQLException
    {
        ResultSet rset;
        String nomProduitAjout = "";
        String prixProduitAjout = "";
        JLabel[] labelPanier = new JLabel[2];
        
        rset = baseDonnees.getStatement().executeQuery("Select nomProduit,prix from Produit where idProduit='"+idProduit+"'");
        while (rset.next())
        {
            nomProduitAjout = rset.getString(1);
            prixProduitAjout = rset.getString(2);
        }
        
        if (!quantiteMap.containsKey(nomProduitAjout))//Si il existe pas
        {
            baseDonnees.getStatement().executeUpdate("Insert into Panier Select NULL,idproduit,pseudo,prix from produit,utilisateur where pseudo ='"+pseudo+"' and idproduit='"+idProduit+"'");
            contrainte.gridy+=1;
            
            rset = baseDonnees.getStatement().executeQuery("Select count(ID) from Panier where idProduit='"+idProduit+"' and pseudo='"+pseudo+"'");
            labelPanier[0] = new JLabel(nomProduitAjout +" - "+prixProduitAjout+"€");
            labelPanier[1] = new JLabel(rset.getString(1));
            quantiteMap.put(nomProduitAjout, labelPanier);
            
            this.add(quantiteMap.get(nomProduitAjout)[0],contrainte);
            
            contrainte.gridx=1;
            this.add(quantiteMap.get(nomProduitAjout)[1], contrainte);
            contrainte.gridx=0;
            
            panelPere.retirerPanierBoutonSetEnable(true);
            
            updateUI();

            
        }
        else
        {
            quantiteMap.get(nomProduitAjout)[1].setText(Integer.toString(Integer.parseInt(quantiteMap.get(nomProduitAjout)[1].getText())+1));
            baseDonnees.getStatement().executeUpdate("Insert into Panier Select NULL,idproduit,pseudo,prix from produit,utilisateur where pseudo ='"+pseudo+"' and idproduit='"+idProduit+"'");
        }
        calculPrix();
    }
    
    private void lecturePanier() throws SQLException
    {
        ResultSet rset;
        ArrayList<String> idProduitsAjoutes = new ArrayList<>();
        String nomProduitAjoute;
        String prixProduitAjoute;
        
        //On récuềre les id des produits du panier de l'utilisateur
        rset = baseDonnees.getStatement().executeQuery("Select distinct idproduit from panier where pseudo='"+pseudo+"'");
        while (rset.next()) //Tant qu'il reste un id
            idProduitsAjoutes.add(rset.getString(1)); //on ajoute l'id dans une arrayList
        
        Iterator <String> iterateur = idProduitsAjoutes.iterator(); //on met un iterateyr sur l'arraylist
        while (iterateur.hasNext()) //tant que l'arraylist est pas vide
        {
            JLabel[] labelPanier= new JLabel[2];
            
            String idProduit = iterateur.next();//on passe à l'id suivant
            rset = baseDonnees.getStatement().executeQuery("Select nomProduit,prix from Produit where idProduit='"+idProduit+"'"); //on récupère les noms des produits et le prix du produit à l'id stock"e en cours
            nomProduitAjoute = rset.getString(1); //on stocke le nom du produit
            prixProduitAjoute = rset.getString(2); //on stocke le prix du produit
            
            labelPanier[0] = new JLabel(rset.getString(1) + " - "+ rset.getString(2)+"€"); //on écrit un JLabel avec le nom et le prix
            rset = baseDonnees.getStatement().executeQuery("Select count(ID) from panier where idProduit='"+idProduit+"' and pseudo='"+pseudo+"'"); //on récupère la quantité du panier
            labelPanier[1] = new JLabel(rset.getString(1)); //on stocke la quantité dans un JLabel
            quantiteMap.put(nomProduitAjoute, labelPanier); //on place les JLabels dans la HashMap
            
            contrainte.gridy +=1;
            add(quantiteMap.get(nomProduitAjoute)[0],contrainte);
            contrainte.gridx=1;
            this.add(quantiteMap.get(nomProduitAjoute)[1], contrainte);
            contrainte.gridx=0;
        }
        updateUI();
    }
    
    public void calculPrix() throws SQLException
    {
        ResultSet rset;
        rset = baseDonnees.getStatement().executeQuery("select sum(prix) from panier where pseudo='"+pseudo+"'");
        if (rset.getString(1) != null)
        {
            panelPere.getValiderButton().setText("Acheter : "+rset.getString(1)+"€");
            panelPere.getValiderButton().setEnabled(true);
            panelPere.getViderPanierButton().setEnabled(true);
        }
        else
        {
            panelPere.getValiderButton().setText("Acheter");
            panelPere.getValiderButton().setEnabled(false);
            panelPere.getViderPanierButton().setEnabled(false);
        }
    }
    
    public void suppressionElmtPanier(String idProduit) throws SQLException
    {
        ResultSet rset; 
        rset = baseDonnees.getStatement().executeQuery("Select nomproduit from produit where idproduit='"+idProduit+"'");
        String NomProduit = rset.getString(1);
        if (Integer.parseInt(quantiteMap.get(NomProduit)[1].getText())==1) // Si il reste un et seulement un produit de ce type
        {
            this.remove(quantiteMap.get(NomProduit)[0]);
            this.remove(quantiteMap.get(NomProduit)[1]);
            quantiteMap.remove(NomProduit);
            panelPere.retirerPanierBoutonSetEnable(false);
        }
        else
        {
            quantiteMap.get(NomProduit)[1].setText(Integer.toString(Integer.parseInt(quantiteMap.get(NomProduit)[1].getText())-1));
        }
        baseDonnees.getStatement().executeUpdate("Delete from panier where pseudo='"+pseudo+"' and idproduit='"+idProduit+"' and id=(select max(id) from panier where pseudo='"+pseudo+"' and idproduit='"+idProduit+"')");
        updateUI();
    }
    
    public boolean estDansPanier(String idProduit) throws SQLException
    {
        ResultSet rset;
        rset = baseDonnees.getStatement().executeQuery("Select nomProduit from produit where idproduit='"+idProduit+"'");
        return quantiteMap.containsKey(rset.getString(1));
    }
    
    public void viderPanier() throws SQLException
    {
        baseDonnees.getStatement().executeUpdate("Delete from panier");
        this.removeAll();
        quantiteMap.clear();
        contrainte = new GridBagConstraints();
        contrainte.gridwidth = 2; contrainte.gridy = 0;
        this.add(labelTitre,contrainte);
        contrainte.gridwidth=1; contrainte.gridy=1; contrainte.gridx = 0; contrainte.insets = new Insets(3,3,3,3);
    }
}