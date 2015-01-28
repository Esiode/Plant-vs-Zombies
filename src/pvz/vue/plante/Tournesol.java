package pvz.vue.plante;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import pvz.controleur.PvZControleur;
import pvz.vue.terrainjeu.JZonePlantable;

/**
 * Objet visuel qui représente un tire-pois
 *
 * <p>Cette classe crée toutes les méthodes nécessaires au bon fonctionnement
 * visuel des tire-pois. Elle contient les méthodes qui lui permettent de
 * s'actualiser et de faire l'action de tirer</p>
 *
 * @author Xavier Reid et Philippe Marcotte
 */
public class Tournesol extends JPlante {

    /*
     * Contient l'image qu'elle doit afficher au moment de l'appel de la variable.
     */
    private Image imgActuelle;
    /*
     * Contient l'image qui doit être dessinée lorsqu'elle est activée.
     */
    private Image imgActif = Toolkit.getDefaultToolkit().getImage("Tournesol1.gif");
    /*
     * Contient l'image qui doit être dessinée lorsqu'elle n'est pas activée.
     */
    private Image imgInactif = Toolkit.getDefaultToolkit().getImage("Tournesol2.gif");
    /*
     * Integer qui représente le temps entre chaque tire de pois.
     */
    private int tempsRecharge;
    /*
     * Long qui représente le temps auquel la plante a été créée.
     */
    private long dateNaissance;
    /*
     * Boolean qui représente si la plante est prête à tirer ou non.
     */
    private boolean actif;

    /**
     * Constructeur d'un tournesol. Il instancie le tempsRecharge, la
     * dateNaissance, l'état actif à false par défaut, l'imageActuelle à image
     * inactif et un mouseListener pour lorsqu'un joueur appuie sur la plante
     * pour prendre l'argent.
     *
     * @param jzp Objet qui représente une zone où l'on peut planter une plante.
     * @param controleur Permet d'initialiser le contrôleur et, ainsi, établir
     * la communication entre les deux.
     */
    public Tournesol(final PvZControleur controleur, JZonePlantable jzp) {
        super(jzp, controleur, "Tournesol");
        this.tempsRecharge = controleur.getFPS() * 30;
        this.actif = false;
        this.imgActuelle = imgInactif;
        this.dateNaissance = controleur.getTemps();
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent me) {
                Tournesol tourneSource = ((Tournesol) me.getSource());
                if (tourneSource.actif) {
                    actif = false;
                    tourneSource.imgActuelle = imgInactif;
                    controleur.playSound("kaching.wav");
                    controleur.modCash(25);
                }
            }
        });
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(imgActuelle, 0, 0, this);
    }

    @Override
    public void actualiser(boolean zombieDansLane, boolean resteZombies) {
        if (getControleur().getTemps()!=dateNaissance&&((getControleur().getTemps() - dateNaissance) % tempsRecharge == 0)) {
            this.actif = true;
            this.imgActuelle = imgActif;
        }
        this.invalidate();
        this.repaint();
    }
}
