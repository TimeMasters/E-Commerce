package ihm;

import bd.BaseDeDonnees;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;


public class PanelConnexion extends JPanel implements ActionListener,KeyListener
{
    private JTextField pseudo;
    private JPasswordField passwd;
    private JButton connect;
    private JLabel pseudoLabel;
    private JLabel passwdLabel;
    private BaseDeDonnees baseDonnees;
    private FenetreMere fenetreMere;
    private JLabel msgConnexion;
    
    public PanelConnexion(BaseDeDonnees bd, FenetreMere fenetreParent)
    {
        baseDonnees = bd;
        fenetreMere = fenetreParent;
        
        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints contrainte = new GridBagConstraints();
        this.setLayout(layout);
        
        pseudoLabel = new JLabel("Pseudo :");
        contrainte.gridx=0; contrainte.gridy=0; contrainte.anchor = GridBagConstraints.EAST; contrainte.insets=new Insets(0, 0, 0, 10);
        this.add(pseudoLabel, contrainte);
        
        passwdLabel = new JLabel("Mot de passe :");
        contrainte.gridy=1;
        this.add(passwdLabel,contrainte);
        
        pseudo = new JTextField(10);
        pseudo.setToolTipText("Pseudo");
        contrainte.gridx=1; contrainte.gridy=0; contrainte.insets=new Insets(0, 0, 0, 0);
        this.add(pseudo,contrainte);
        
        passwd = new JPasswordField(10);
        passwd.setToolTipText("Mot de passe");
        contrainte.gridy=1;contrainte.insets=new Insets(10, 0, 0, 0);
        this.add(passwd,contrainte);
        
        connect = new JButton("Se connecter");
        contrainte.gridx=0; contrainte.gridy=2; contrainte.gridwidth=2;contrainte.anchor = GridBagConstraints.CENTER; contrainte.insets=new Insets(10, 10, 10, 10);
        connect.addActionListener(this);
        this.add(connect,contrainte);
        
        this.msgConnexion = new JLabel();
        contrainte.gridy = 3;
        this.add(msgConnexion,contrainte);
        
        passwd.addKeyListener(this);
        connect.addKeyListener(this);
        pseudo.addKeyListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent parEvt) 
    {
        if (parEvt.getSource() == this.connect)
        {
            if (baseDonnees.connexion_user(this.pseudo.getText(), this.passwd.getText()))
            {
                fenetreMere.setPanel(new PanelCatalogue(baseDonnees,this.pseudo.getText(),fenetreMere));
                updateUI();
            }
            else
            {
               JOptionPane.showMessageDialog(null,"Identifiants incorrects. Veuillez réessayer...");
               this.msgConnexion.setText("USER1 : user1/user1/t USER2 : user2/user2");
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent parEvt) 
    {
    }

    @Override
    public void keyPressed(KeyEvent e) 
    {
    }

    @Override
    public void keyReleased(KeyEvent e) 
    {
        if (e.getKeyCode() == KeyEvent.VK_ENTER)
        {
            if (baseDonnees.connexion_user(this.pseudo.getText(), this.passwd.getText()))
            {
                fenetreMere.setPanel(new PanelCatalogue(baseDonnees,this.pseudo.getText(),fenetreMere));
                updateUI();
            }
            else
            {
               JOptionPane.showMessageDialog(null,"Identifiants incorrects. Veuillez réessayer...");
               this.msgConnexion.setText("USER1 : user1/user1 ou USER2 : user2/user2");
            }
        }
    }
}
