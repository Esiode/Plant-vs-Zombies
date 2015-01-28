package pvz.vue.terrainjeu;

import javax.swing.JComponent;

/**
 * Classe qui contient l'objet visuel qui couvre touttes les cases et qui permet
 * de placer les ZoneDangereuse aux endroits où un boulet-cochon explose.
 *
 * @author Xavier Reid et Philippe Marcotte
 */
public class JZoneExplosions extends JComponent {

    /**
     * Constructeur de la zone d'explosion. Il détermine la taille de l'objet
     * visuel.
     */
    public JZoneExplosions() {
        this.setSize(765, 650);
    }

    /**
     * Méthode qui permet d'ajouter une zone dangereuse à l'endroit où un
     * boulet-cochon a explosé.
     *
     * @param noLane Integer qui représente la ligne de l'explosion
     * @param noCase Integer qui représente la case de l'explosion
     * @param zoneDangereuse ZoneDangereuse qui représente l'explosion en tant
     * que tel.
     */
    public void ajouterExplosion(int noLane, int noCase, ZoneDangereuse zoneDangereuse) {
        this.add(zoneDangereuse);
        zoneDangereuse.setLocation(85 * noCase, 130 * noLane);
    }

    /**
     * Permet d'enlever l'effet d'explosion lorsque celui-ci a expiré.
     *
     * @param zoneDangereuse ZoneDangereuse qui représente l'explosion en tant
     * que tel.
     */
    public void enleverExplosion(ZoneDangereuse zoneDangereuse) {
        this.remove(zoneDangereuse);
    }
}
