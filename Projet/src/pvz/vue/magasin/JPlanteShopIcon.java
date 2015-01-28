package pvz.vue.magasin;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JComponent;
import javax.swing.JLabel;
import pvz.controleur.PvZControleur;
import pvz.vue.terrainjeu.PnlTerrainJeu;

/**
 * Classe qui représente les icônes des plantes dans le magasin.
 *
 * <p>Elle dessine les images qui servent à représenter les plantes dans le
 * magasin et à les animer lorsqu'on met la souris dessus.</p>
 *
 * @author Xavier Reid et Philippe Marcotte
 */
public class JPlanteShopIcon extends JComponent {

    /*
     * Integer représentant le prix à payer pour placer une plante.
     */
    private int prix;
    /*
     * String représentant le nom de plante que le joueur achète.
     */
    private String nom;
    /*
     * Boolean représentant si la plante est sélectionné ou non.
     */
    private boolean estSelectionne;
    /*
     * Boolean représentant le fait que la souris soit dessus l'icône d'une plante ou non.
     */
    private boolean sourisDessus = false;
    /*
     * Déclaration du panneau principal pour établir la communication entre les deux.
     */
    private PnlTerrainJeu pnlTerrainJeu;
    /*
     * Contient l'image d'une plante lorsqu'elle n'est plas sélectionnée.
     */
    private Image img1;
    /*
     * Contient l'image d'une plante que la classe va alterner avec l'img1 lorsque le joueur sélectionne une plante.
     */
    private Image img2;
    /*
     * Contient l'image qui doit être dessinée au moment de l'appel de la variable.
     */
    private Image imgActuelle;
    /*
     * Contient le texte qui décrit la plante sélectionnée.
     */
    private JLabel lblTexteSourisDessus;

    /**
     * Constructeur des icônes qui vont être placées dans le magasin. Il
     * initialise toutes les variable nécessaires pour gérer les achats des
     * plantes et les différents effets visuels lorsqu'on sélectionne une
     * plante.
     *
     * @param nom String représentant le nom de plante que le joueur achète.
     * @param prix Integer représentant le prix à payer pour placer une plante.
     * @param pnlTerrainJeu Permet d'initialiser la fenêtre principale et,
     * ainsi, établir la communication entre les deux.
     * @param texteSourisDessus String qui représente le texte que le JLabel
     * lblTexteSourisDessus doit afficher.
     */
    public JPlanteShopIcon(final String nom, final int prix, final PnlTerrainJeu pnlTerrainJeu, String texteSourisDessus) {
        lblTexteSourisDessus = new JLabel(texteSourisDessus);
        this.pnlTerrainJeu = pnlTerrainJeu;
        this.setSize(90, 110);
        this.nom = nom;
        this.prix = prix;
        this.pnlTerrainJeu = pnlTerrainJeu;
        img1 = Toolkit.getDefaultToolkit().getImage(nom + "1.gif");
        img2 = Toolkit.getDefaultToolkit().getImage(nom + "2.gif");
        imgActuelle = img1;
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent me) {
                JPlanteShopIcon jpsiSource = ((JPlanteShopIcon) me.getSource());
                jpsiSource.sourisDessus = true;
                pnlTerrainJeu.add(jpsiSource.getLblTexteSourisDessus());
            }

            @Override
            public void mouseExited(MouseEvent me) {
                JPlanteShopIcon jpsiSource = ((JPlanteShopIcon) me.getSource());
                jpsiSource.sourisDessus = false;
                pnlTerrainJeu.remove(jpsiSource.getLblTexteSourisDessus());
            }
        });
    }

    /**
     * Fonction qui renvoie le texte qui est contenu dans le JLabel.
     *
     * @return String représentant le texte qui doit être afficher lorsqu'une
     * plante est sélectionnée,
     */
    public JLabel getLblTexteSourisDessus() {
        return lblTexteSourisDessus;
    }

    /**
     * Fonction qui renvoie si oui ou non la plante est sélectionnée.
     *
     * @return Boolean qui true si la plante est sélectionnée et false si elle
     * ne l'est pas.
     */
    public boolean getEstSelectionne() {
        return estSelectionne;
    }

    /**
     * Méthode qui permet de changer le fait qu'une plante est sélectionnée ou
     * non.
     *
     * @param estSelectionne Boolean représentant si la plante est sélectionné
     * ou non.
     */
    public void setEstSelectionne(boolean estSelectionne) {
        this.estSelectionne = estSelectionne;
    }

    /**
     * Fonction qui renvoie le nom de l'icône de la plante sélectionnée.
     *
     * @return String qui représente le nom de la plante sélectionnée.
     */
    public String getNom() {
        return nom;
    }

    /**
     * Fonction qui renvoie le prix de la plante sélectionnée.
     *
     * @return Integer qui représente le prix de la plante achetée.
     */
    public int getPrix() {
        return prix;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(imgActuelle, 0, 0, this);
    }

    /**
     * Permet l'alternation entre les deux images de chaque plante et, ainsi,
     * crée une animation lorsqu'un plante est sélectionnée.
     */
    public void actualiser() {
        if (estSelectionne || sourisDessus) {
            if (imgActuelle == img1) {
                imgActuelle = img2;
            } else {
                imgActuelle = img1;
            }
        }
    }
}
