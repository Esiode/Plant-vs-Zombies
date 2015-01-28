package pvz.vue;

import pvz.vue.terrainjeu.PnlTerrainJeu;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import pvz.controleur.PvZControleur;

/**
 * Classe qui initialise tout l'aspect visuel du jeu.
 *
 * <p>Elle crée le JPanel qui contient les méthodes pour faire fonctionner la
 * vue du jeu. Elle fait le lien en le contrôleur et le reste de la vue.</p>
 *
 * @author Xavier Reid et Philippe Marcotte
 */
public class FntPrincipale extends JFrame {

    /*
     * Déclaration du panneau du terrain pour ensuite l'instancier dans le constructeur.
     */
    private PnlTerrainJeu pnlTerrainJeu;
    /*
     * Déclaration du contrôleur que l'on va ensuite instancier dans le constructeur.
     */
    private PvZControleur controleur;

    /**
     * Constructeur de la classe où l'on initialise l'accès au contrôleur pour
     * le reste de la vue, le panneau qui contient l'aspect visuel du jeu et le
     * keyListener pour les codes de triche.
     *
     * @param controleur Variable de type PvZContrôleur qui permet d'avoir accès
     * au contrôleur dans le reste le vue.
     */
    public FntPrincipale(final PvZControleur controleur) {
        this.controleur = controleur;
        pnlTerrainJeu = new PnlTerrainJeu(controleur);
        this.add(pnlTerrainJeu);
        pnlTerrainJeu.setLocation(0, 0);
        this.setTitle("Plantes vs Zombies");
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.addKeyListener(new CheatCodeListener(controleur));
        this.pack();
    }

    /**
     * Méthode qui appelle la mise à jour du panneau principal.
     */
    protected void miseAJourFenetre() {
        pnlTerrainJeu.miseAJourPnlTerrainJeu();
    }
}
