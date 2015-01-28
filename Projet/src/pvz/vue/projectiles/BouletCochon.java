/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pvz.vue.projectiles;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import pvz.vue.terrainjeu.TrajectoireBouletCochon;
import pvz.vue.terrainjeu.JZonePlantable;

/**
 * Classe qui représente tous les objets visuels de type projectiles plus
 * précisément les boulets-cochons.
 *
 * <p>Elle contient les méthodes pour le bon fionctionement visuel des
 * boulets-cochons. Elle détermine comment ceux-ci se déplacent et leur
 * apparence.</p>
 *
 * @author Xavier Reid et Philippe Marcotte
 */
public class BouletCochon extends JProjectile {

    /*
     * Integer qui représente la vitesse à laquelle le projectile se déplace.
     */
    private int vitesse = -4;
    /*
     * Objet qui représente la zone où le projectile doit arriver.
     */
    private JZonePlantable jzpCible;
    /*
     * Integer représentant la position sur la longueur du point de départ du projectile.
     */
    private int posiX;
    /*
     * Integer représentant la position sur la longueur du point d'arriver du projectile.
     */
    private int posiXCible;
    /*
     * Integer représentant la position sur la hauteur du point de départ du projectile.
     */
    private int posiY;
    /*
     * Integer représentant la position sur la hauteur du point d'arrriver du projectile.
     */
    private int posiYCible;
    /*
     * Boolean qui détermine si le projectile est en train de monter vers le haut ou non.
     */
    private boolean monte = true;
    /*
     * Panneau qui sert uniquement à la trajectoire des boulet-cochons
     */
    private TrajectoireBouletCochon trajectoireBouletCochon;
    /*
     * Contient l'image qu'elle doit afficher au moment de l'appel de la variable.
     */
    private Image imgActuelle;
    /*
     * Contient l'image qui doit être dessinée lorsque le projectile est en train de monter.
     */
    private Image imgMontante = Toolkit.getDefaultToolkit().getImage("Cochon.gif");
    /*
     * Contient l'image qui doit être dessinée lorsque le projectile est en train de descendre.
     */
    private Image imgDescendante = Toolkit.getDefaultToolkit().getImage("Cochon2.gif");

    /**
     * Constructeur du projectile boulet-cochon. Il instancie la taille du
     * projectile, sa zoneplantable d'arriver, la panneau trajectoir des
     * boulet-cochons, sa position d'origine et celle d'arrivée, et puis,
     * finalement, son image actuelle par défaut étant montante.
     *
     * @param jzpOrigine Objet qui représente la zone d'où le projectile part.
     * @param jzpCible Objet qui représente la zone où le projectile doit
     * arriver.
     * @param trajectoireBouletCochon Panneau qui sert uniquement à la
     * trajectoire des boulet-cochons
     */
    public BouletCochon(JZonePlantable jzpOrigine, JZonePlantable jzpCible, TrajectoireBouletCochon trajectoireBouletCochon) {
        super("LanceMelon");
        this.setSize(25, 26);
        this.trajectoireBouletCochon = trajectoireBouletCochon;
        this.jzpCible = jzpCible;
        this.posiX = jzpOrigine.getLane().getX() + jzpOrigine.getX() + jzpOrigine.getWidth() / 2;
        this.posiY = jzpOrigine.getLane().getY();
        this.posiXCible = jzpCible.getX() + jzpCible.getLane().getX();
        this.posiYCible = jzpCible.getY() + jzpCible.getLane().getY();
        this.imgActuelle = imgMontante;
    }

    /**
     * Fonction qui renvoie la position sur la longueur d'origine du
     * boulet-cochon.
     *
     * @return Integer représentant la position sur la longueur du point de
     * départ du projectile.
     */
    public int getPosiX() {
        return posiX;
    }

    /**
     * Fonction qui renvoie la position sur l'hauteur d'origine du
     * boulet-cochon.
     *
     * @return Integer représentant la position sur la hauteur du point de
     * départ du projectile.
     */
    public int getPosiY() {
        return posiY;
    }

    @Override
    public void actualiser() {
        if (!monte && this.posiX == posiXCible && this.getY() >= posiYCible) {
            
            jzpCible.splat();
            trajectoireBouletCochon.enleverMelon(this);
        } else if (monte && this.posiY <= -this.getHeight()) {
            monte = false;
            imgActuelle = imgDescendante;
            posiX = jzpCible.getX() + jzpCible.getLane().getX();
            this.setLocation(posiX, posiY);
            vitesse = -vitesse;
        }
        deplacement();
        this.invalidate();
        this.repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(imgActuelle, 0, 0, this);
    }

    @Override
    public void deplacement() {
        posiY += vitesse;
        this.setLocation(posiX, posiY);
    }
}
