package pvz.vue.terrainjeu;

import java.awt.Dimension;
import java.util.ArrayList;
import javax.swing.JComponent;
import pvz.vue.projectiles.BouletCochon;

/**
 * JComponent qui contient la tracjectoire des boulet-cochons.
 *
 * <p>Cette classe sert à contenir les représentations visuelles des
 * trajectoires des boulets-cochons.</p>
 *
 * @author Xavier Reid et Philippe Marcotte
 */
public class TrajectoireBouletCochon extends JComponent {

    /**
     * ArrayList qui contient tous les boulet-cochons actif dans les airs.
     */
    private ArrayList<BouletCochon> listeBouletCochons = new ArrayList<>();

    /**
     * Constructeur du JComponent.
     *
     * @param pnlSize Sert à définir les dimensions du JComponent.
     */
    public TrajectoireBouletCochon(Dimension pnlSize) {
        this.setSize(pnlSize);
        this.setLayout(null);
    }

    /**
     * Méthode qui sert à ajouter un boulet-cochon au JComponent et définie sa
     * position à l'intérieur de celui-ci.
     *
     * @param bouletCochon Objet qui représente le projectile d'un lance-cochon.
     */
    public void addCochon(BouletCochon bouletCochon) {
        this.add(bouletCochon);
        bouletCochon.setLocation(bouletCochon.getPosiX(), bouletCochon.getPosiY());
        listeBouletCochons.add(bouletCochon);
    }

    /**
     * Méthode qui sert à retirer les boulet-cochons lorsqu'ils sont arrivés à
     * leur cible.
     *
     * @param bouletMelon Objet qui représente le projectile d'un lance-cochon.
     */
    public void enleverMelon(BouletCochon bouletMelon) {
        this.remove(bouletMelon);
        listeBouletCochons.remove(bouletMelon);
    }

    /**
     * Méthode qui sert à actualiser le déplacement d'un boulet-cochon.
     */
    public void actualiser() {
        for (int i = 0; i < listeBouletCochons.size(); i++) {
            listeBouletCochons.get(i).actualiser();
        }
    }
}
