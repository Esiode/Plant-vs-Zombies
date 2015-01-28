/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pvz.vue.plante;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import pvz.controleur.PvZControleur;
import pvz.vue.terrainjeu.JZonePlantable;
import pvz.vue.terrainjeu.PnlTerrainJeu;
import pvz.vue.projectiles.Pois;

/**
 * Objet visuel qui représente un tire-pois
 *
 * <p>Cette classe crée toutes les méthodes nécessaires au bon fonctionnement
 * visuel des tire-pois. Elle contient les méthodes qui lui permettent de
 * s'actualiser et de faire l'action de tirer</p>
 *
 * @author Xavier Reid et Philippe Marcotte
 */
public class TirePois extends JPlante {

    /*
     * Contient l'image qu'elle doit afficher au moment de l'appel de la variable.
     */
    private Image imgActuelle;
    /*
     * Contient l'image qui doit être dessinée lorsqu'elle est activée.
     */
    private Image imgInactive = Toolkit.getDefaultToolkit().getImage("PeaShooter2.gif");
    /*
     * Contient l'image qui doit être dessinée lorsqu'elle n'est pas activée.
     */
    private Image imgActive = Toolkit.getDefaultToolkit().getImage("PeaShooter1.gif");
    /*
     * Integer qui représente le temps entre chaque tire de pois.
     */
    private int tempsRecharge;
    /*
     * Long qui représente le temps auquel la plante a été créée.
     */
    private long dateNaissance;
    /*
     * Déclaration du panneau principal pour établir la communication entre les deux.
     */
    private PnlTerrainJeu pnlTerrainJeu;
    /*
     * Long qui représente le temps auquel la plante a tirer un cochon pour la dernière fois.
     */
    private long dateDernierTir;
    /*
     * Boolean qui représente si la plante est prête à tirer ou non.
     */
    private boolean charge;

    /**
     * Constructeur d'tire-pois visuel. Il instancie le tempsRecharge, la
     * dateDernierTir, l'état chargé à true par défaut et l'imageActuelle à
     * image active.
     *
     * @param jzp Objet qui représente une zone où l'on peut planter une plante.
     * @param controleur Permet d'initialiser le contrôleur et, ainsi, établir
     * la communication entre les deux.
     */
    public TirePois(final PvZControleur controleur, JZonePlantable jzp) {
        super(jzp, controleur, "TirePois");
        this.tempsRecharge = controleur.getFPS();
        this.dateDernierTir = controleur.getTemps();
        this.charge = true;
        this.imgActuelle = imgActive;
    }

    @Override
    public void actualiser(boolean ZombieDansLaLane, boolean resteZombies) {
        if ((dateDernierTir - super.getControleur().getTemps()) % (tempsRecharge / 2) == 0) {
            imgActuelle = imgActive;
        }
        if ((dateDernierTir - super.getControleur().getTemps()) % tempsRecharge == 0 && ZombieDansLaLane) {
            charge = true;
        }
        if (charge && ZombieDansLaLane) {
            tirerPois();
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(imgActuelle, 0, 0, this);
    }

    /**
     * Gère l'animation d'une plante tire-pois qui tire.
     */
    private void tirerPois() {
        super.getControleur().playSound("pew.wav");
        dateDernierTir = super.getControleur().getTemps();
        this.getJzp().getLane().addProjectile(new Pois((float) (this.getJzp().getX() + 59), this.getJzp().getY() + 23));
        charge = false;
        imgActuelle = imgInactive;
    }
}
