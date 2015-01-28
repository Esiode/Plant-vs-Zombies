package pvz.vue.plante;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import pvz.controleur.PvZControleur;
import pvz.vue.terrainjeu.JZonePlantable;
import pvz.vue.terrainjeu.PnlTerrainJeu;

/**
 * Objet visuel qui représente une plante lance-cochon
 *
 * <p>Cette classe crée toutes les méthodes nécessaires au bon
 * fonctionnement visuel des lance-cochons. Elle contient les méthodes qui lui
 * permettent de s'actualiser et de faire l'action de tirer</p>
 *
 * @author Xavier Reid et Philippe Marcotte
 */
public class LanceCochon extends JPlante {

    /*
     * Boolean qui représente si lap lante est en train de tirer ou non. False si elle ne l'est pas et true si elle l'est.
     */
    private final boolean actif;
    /*
     * Contient l'image qu'elle doit afficher au moment de l'appel de la variable.
     */
    private Image imgActuelle;
    /*
     * Contient l'image qui doit être dessinée lorsqu'elle n'est pas acitvée.
     */
    private Image imgInactive = Toolkit.getDefaultToolkit().getImage("LanceCochon1.gif");
    /*
     * Contineut l'image qui doit être dessinée lorsqu'elle est activée.
     */
    private Image imgActive = Toolkit.getDefaultToolkit().getImage("LanceCochon2.gif");
    /*
     * Integer qui représente le temps entre chaque tire de cochon.
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
     * Constructeur du lance-cochon qui initialise le boolean chargé par défaut
     * à true, la communication avec le panneau terrain jeu, la date du dernier
     * tir, le temps pour recharger, le temps de création, l'état actif true par
     * défaut, ainsi que l'image active.
     *
     * @param jzp Objet qui représente une zone où l'on peut planter une plante.
     * @param controleur Permet d'initialiser le contrôleur et, ainsi, établir
     * la communication entre les deux.
     * @param pnlTerrainJeu Permet d'initialiser la fenêtre principale et,
     * ainsi, établir la communication entre les deux.
     */
    public LanceCochon(final PvZControleur controleur, JZonePlantable jzp, PnlTerrainJeu pnlTerrainJeu) {
        super(jzp, controleur, "LanceCochon");
        this.pnlTerrainJeu = pnlTerrainJeu;
        this.charge = true;
        this.dateDernierTir = super.getControleur().getTemps();
        this.tempsRecharge = super.getControleur().getFPS() * 5;
        this.dateNaissance = super.getControleur().getTemps();
        this.actif = true;
        this.imgActuelle = imgActive;
    }

    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(imgActuelle, 0, 0, this);
    }

    @Override
    public void actualiser(boolean zombieDansLane, boolean resteZombies) {
        if ((dateDernierTir - super.getControleur().getTemps()) % tempsRecharge == 0) {
            charge = true;
            imgActuelle = imgActive;
        }
        if (charge && resteZombies) {
            tirerBouletCochon();
        }
    }

    /**
     * Méthode qui sert à faire l'animation de tirer les cochons du
     * lance-cochon.
     */
    private void tirerBouletCochon() {
        super.getControleur().playSound("pop.wav");
        dateDernierTir = super.getControleur().getTemps();
        pnlTerrainJeu.lancerBouletCochon(super.getJzp());
        charge = false;
        imgActuelle = imgInactive;
    }
}
