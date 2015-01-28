package pvz.vue.terrainjeu;

import pvz.vue.plante.JPlante;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JComponent;
import pvz.controleur.PvZControleur;

/**
 * Classe qui représente une case où l'on peut planter une plante.
 *
 * <p>Elle détermine tout ce qui a trait à la délimitation de la zone où on peut
 * placer une plante. Elle pêrmet de gérer chaque plante individuellement.</p>
 *
 * @author Xavier Reid et Philippe Marcotte
 */
public class JZonePlantable extends JComponent {

    /**
     * Boolean qui représente si la case contient une plante ou non.
     */
    private boolean caseVide = true;
    /**
     * Boolean qui représente si un boulet-cochon vient d'exploser sur la case ou non.
     */
    private boolean caseDangereuse = false;
    /**
     * Représente une zone où un boulet-cochon vient d'exploser
     */
    private ZoneDangereuse zoneDangereuse = new ZoneDangereuse();
    /**
     * Représente le moment où une zoneDangereuse devrait expirer.
     */
    private long dateOuDevientInnofensif = 0;
    /**
     * Déclaration du contrôleur pour établir la communication entre les deux.
     */
    private PvZControleur controleur;
    /**
     * Objet plante qui représente la plante qui est dans la case.
     */
    private JPlante planteDansCase;
    /**
     * Déclaration du panneau principal pour établir la communication entre les deux.
     */
    private PnlTerrainJeu pnlTerrainJeu;
    /**
     * Integer qui représente le numéro de la case dans la ligne.
     */
    private int noCase;
    /**
     * Integer qui représente la ligne où la case se situe.
     */
    private Lane lane;

    /**
     * Constructeur d'une zone plante.
     *
     * @param controleur Permet d'initialiser le contrôleur et, ainsi, établir
     * la communication entre les deux.
     * @param pnlTerrainJeu Permet d'initialiser la fenêtre principale et,
     * ainsi, établir la communication entre les deux.
     * @param noCase Integer qui représente le numéro de la case dans la ligne.
     * @param lane Integer qui représente la ligne où la case se situe.
     */
    public JZonePlantable(final PvZControleur controleur, final PnlTerrainJeu pnlTerrainJeu, int noCase, Lane lane) {
        this.controleur = controleur;
        this.pnlTerrainJeu = pnlTerrainJeu;
        this.noCase = noCase;
        this.lane = lane;
        this.setSize(79, 124);
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                pnlTerrainJeu.clicZonePlantable((JZonePlantable) e.getSource());
            }
        });
    }

    /**
     * Fonction qui renvoie la case de la zone plantable concernée.
     *
     * @return Integer qui représente le numéro de la case dans la ligne.
     */
    public int getNoCase() {
        return noCase;
    }

    /**
     * Fonction qui renvoie la ligne de la zone plantable concernéée.
     *
     * @return Integer qui représente la ligne où la case se situe.
     */
    public Lane getLane() {
        return lane;
    }

    /**
     * Fonction qui renvoie l'objet plante qui se situe dansl a zone plantable
     * concernée.
     *
     * @return Objet plante qui représente la plante qui est dans la case.
     */
    public JPlante getPlanteDansCase() {
        return planteDansCase;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

    /**
     * Fonction qui renvoie si la zone plantable est occupée par une plante ou
     * non.
     *
     * @return Boolean qui représente si la case contient une plante ou non.
     */
    public boolean getVide() {
        return this.caseVide;
    }

    /**
     * Méthode qui permet de modifier si la case est occupée par une plante ou
     * non
     *
     * @param caseVide Boolean qui représente si la case contient une plante ou
     * non.
     */
    public void setVide(boolean caseVide) {
        this.caseVide = caseVide;
    }

    /**
     * Méthode qui permet de déterminer la plante qui est dans la case et de la
     * placer correctement à l'intérieur, ainsi que de changer l'état de la zone
     * plantable pour dire qu'elle est occupée.
     *
     * @param plantePlantee Objet plante qui représente la plante qui est dans
     * la case.
     */
    public void planterPlante(JPlante plantePlantee) {
        this.planteDansCase = plantePlantee;
        this.add(plantePlantee);
        plantePlantee.setLocation(0, 0);
        this.caseVide = false;
    }

    /**
     * Méthode qui permet de redessiner individuellement chaque plante de chaque
     * zone plantable. C'est aussi cette méthode qui va enlever une zone
     * d'explosion lorsqu'elle est expirée.
     *
     * @param zombieDansLaLane Boolean qui est true s'il y a des zombies dans la
     * ligne et false s'il y en n'a pas.
     * @param resteZombies Boolean qui est true s'il reste des zombies sur tout
     * le terrain et false s'il n'en reste plus.
     */
    public void actualiser(boolean zombieDansLaLane, boolean resteZombies) {
        if (!(caseVide)) {
            planteDansCase.actualiser(zombieDansLaLane, resteZombies);
        }
        if (caseDangereuse && controleur.getTemps() >= dateOuDevientInnofensif) {
            caseDangereuse = false;
            pnlTerrainJeu.getZoneExplosions().enleverExplosion(zoneDangereuse);
        }
        if (controleur.getTemps() % 6 == 0) {
            zoneDangereuse.actualiser();
        }
    }

    /**
     * Méthode qui applique la zone d'explosion sur la zone plantable concernée
     * lorsqu'un boulet-cochon explose. Elle détermine aussi à quel moment la
     * zone dangereuse doit expirer.
     */
    public void splat() {
        controleur.playSound("boom.wav");
        if (!caseDangereuse) {
            caseDangereuse = true;
            pnlTerrainJeu.getZoneExplosions().ajouterExplosion(this.getLane().getNoLane(), this.getNoCase(), zoneDangereuse);
            dateOuDevientInnofensif = controleur.getTemps() + 10 * controleur.getFPS();
        }
        else {
            dateOuDevientInnofensif = controleur.getTemps() + 10 * controleur.getFPS();
        }
    }

    /**
     * Fonction qui renvoie si la case contient une explosion ou non.
     *
     * @return Boolean qui représente si un boulet-cochon vient d'exploser sur la case ou non.
     */
    public boolean getCaseDangereuse() {
        return caseDangereuse;
    }
}