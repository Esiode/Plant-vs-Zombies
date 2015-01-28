package pvz.vue.plante;

import javax.swing.JComponent;
import pvz.controleur.PvZControleur;
import pvz.vue.terrainjeu.JZonePlantable;

/**
 * Classe qui est l'aspect visuel d'une plante du modèle.
 *
 * <p>Elle régit les variables de l'homologue visuel d'une plante. On parle des
 * coordonnées qui permet de faire le lien avec les plantes du modèles et le
 * type de plante.</p>
 *
 * @author Xavier Reid et Philippe Marcotte
 */
public abstract class JPlante extends JComponent {
    /*
     * Tableau de 2 integer qui permet, comme le ID des zombies,
     * de savoir quelle plante dans le modèle correspond à quelle plante dans la
     * vue.
     */

    private int[] coordYX = new int[2];
    /*
     * Déclaration d'une variable de type PvZControleur qui permet au thread de communiquer avec le contrôleur.
     */
    private PvZControleur controleur;
    /*
     * String qui permet de savoir quel type de
     * plante est créé puisque les statistiques différent d'un type à l'autre.
     */
    private String typePlante;
    /*
     * Objet qui représente une zone où l'on peut planter une plante.
     */
    private JZonePlantable jzp;

    /**
     * Méthode qui renvoie la zone sur laquelle la plante concernée a été
     * placée.
     *
     * @return Objet qui représente une zone où l'on peut planter une plante.
     */
    protected JZonePlantable getJzp() {
        return jzp;
    }

    /**
     * Fonction qui renvoie les coordonnées de la plante concernée
     *
     * @return Tableau de 2 integer qui permet, comme le ID des zombies, de
     * savoir quelle plante dans le modèle correspond à quelle plante dans la
     * vue.
     */
    public int[] getCoordYX() {
        return coordYX;
    }

    /**
     * Constructeur d'une JPlante où l'on initialise la zone où elle est
     * plantée, ses coordonnées, le contrôleur, sa taille, le type de plante
     * créée et, finalement, où l'on appelle le contrôleur pour créer la plante
     * dans le modèle.
     *
     * @param jzp Objet qui représente une zone où l'on peut planter une plante.
     * @param controleur Permet d'initialiser le contrôleur et, ainsi, établir
     * la communication entre les deux.
     * @param typePlante String qui permet de savoir quel type de plante est
     * créé puisque les statistiques différent d'un type à l'autre.
     */
    public JPlante(JZonePlantable jzp, final PvZControleur controleur, String typePlante) {
        this.jzp = jzp;
        this.coordYX[0] = jzp.getLane().getNoLane();
        this.coordYX[1] = jzp.getNoCase();
        this.controleur = controleur;
        this.setSize(106, 155);
        this.typePlante = typePlante;
        controleur.creePlante(typePlante, coordYX);
    }

    /**
     * Méthode abstraite qui sera redéfinie dans les classes enfants.
     * Celle-ci sert à déterminer ce qu'il faut faire lorsqu'il vient le temps
     * de repeindre les images. Cela consiste souvent à changer les images
     * actuelles selon le nouvel état de la plante (activée ou désactivée). De
     * plus, cela active l'animation de la plante pour tirer ou piéger ou
     * montrer un sac d'argent.
     *
     * @param zombieDansLaLane Boolean qui détermine s'il y a des zombies dans
     * la ligne des plantes ou non.
     * @param resteZombies Boolean qui détermine s'il reste des zombies dans la
     * ligne ou non.
     */
    public abstract void actualiser(boolean zombieDansLaLane, boolean resteZombies);

    /**
     * fonction qui renvoie le contrôleur lorsqu'on l'appelle. Celle-ci sert aux
     * enfants de la classe lorsqu'ils ont besoin d'une méthode du contrôleur.
     *
     * @return PvZControleur qui fait le lien avec les plantes et le contrôleur.
     */
    public PvZControleur getControleur() {
        return this.controleur;
    }
}
