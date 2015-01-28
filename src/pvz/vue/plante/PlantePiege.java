/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
// +work
package pvz.vue.plante;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import pvz.controleur.PvZControleur;
import pvz.vue.terrainjeu.JZonePlantable;
import pvz.vue.veggie.JVeggie;

/**
 * Objet visuel qui représente une plante-piège
 *
 * <p>Cette classe crée toutes les méthodes nécessaires au bon fonctionnement
 * visuel des plantes-pièges. Elle contient les méthodes qui lui permettent de
 * s'actualiser et de faire l'action de piéger</p>
 *
 * @author Xavier Reid et Philippe Marcotte
 */
public class PlantePiege extends JPlante {

    /*
     * Contient l'image que le programme doit afficher pour une plante-piège au moment de l'appel de la variable.
     */
    private Image imgActuelle;
    /*
     * Contient l'image représentant une plante-piège désactivée.
     */
    private Image imgInactive = Toolkit.getDefaultToolkit().getImage("PlantePiege2.gif");
    /*
     * Contient l'image représentant une plante-piège activée.
     */
    private Image imgActive = Toolkit.getDefaultToolkit().getImage("PlantePiege1.gif");

    /**
     * Constructeur d'une plante-piège visuelle. Elle instancie l'imageActuelle
     * par défaut d'une plante-piège qui est à l'état désactivé.
     * 
     * @param jzp Objet qui représente une zone où l'on peut planter une plante.
     * @param controleur Permet d'initialiser le contrôleur et, ainsi, établir
     * la communication entre les deux.
     */
    public PlantePiege(final PvZControleur controleur, JZonePlantable jzp) {
        super(jzp, controleur, "PlantePiege");
        imgActuelle = imgInactive;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(imgActuelle, 0, 0, this);
    }

    @Override
    public synchronized void actualiser(boolean zombieDansLaLane, boolean resteZombies) {
        if (!getControleur().getPlante(this.getCoordYX()).isDesactive()) {
            imgActuelle = imgActive;
        } else {
            imgActuelle = imgInactive;
        }
    }
}