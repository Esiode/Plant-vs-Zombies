package pvz.modele;

/**
 * Classe qui sert de représentation statistique de leur homologue visuel,
 * JPlante.
 *
 * <p>Elle contient les statistiques comme les points de vies de chaque type de
 * plantes, les dommages et les différents états d'une plante piège. Elle
 * contient aussi les méthodes qui permettent de modifier les états et de tester
 * si une plante est dans un certain état ou non.</p>
 *
 * @author Xavier Reid et Philippe Marcotte
 */
public class Plante {

    /*
     * Integer représentant le nombre de points de vies d'une plante.
     */
    private int pV;
    /*
     * Integer représentant les dommages d'une plante. Seules les Tire-Pois et les Lance-Melons font des dommages.
     */
    private int dmg;
    /*
     * String qui permet de savoir quel type de
     * plante est créé puisque les statistiques différent d'un type à l'autre.
     */
    private String typePlante;
    /*
     * Tableau de 2 integer qui permet, comme le ID des zombies,
     * de savoir quelle plante dans le modèle correspond à quelle plante dans la
     * vue.
     */
    private int[] coordYX = new int[2];
    /*
     * Variable de type EtatPlante, elle représente dans quel état est une plante piégée
     */
    private Plante.EtatPlante etat;

    /**
     * Constructeur d'une plante où l'on instancie le nombre de points de vies
     * de toutes les plantes, leur type, leurs coordonnées, les dommages que
     * chacunes font et leur état lorsque applicable.
     *
     * @param typePlante String qui permet de savoir quel type de plante est
     * créé puisque les statistiques différent d'un type à l'autre.
     * @param coordYX Tableau de 2 integer qui permet, comme le ID des zombies,
     * de savoir quelle plante dans le modèle correspond à quelle plante dans la
     * vue.
     */
    protected Plante(String typePlante, int[] coordYX) {
        this.pV = 600;
        this.typePlante = typePlante;
        this.coordYX = coordYX;
        switch (typePlante) {
            case "TirePois":
                this.dmg = 60;
                this.etat = Plante.EtatPlante.pasApplicable;
                break;
            case "LanceCochon":
                this.dmg = 5;
                this.etat = Plante.EtatPlante.pasApplicable;
                break;
            case "Tournesol":
                this.dmg = 0;
                this.etat = Plante.EtatPlante.pasApplicable;
                break;
            case "PlantePiege":
                this.dmg = 0;
                this.pV = 300;
                this.etat = Plante.EtatPlante.desactive;
                break;
        }
    }

    /**
     * Fonction qui renvoie les points de vies actuels de la plante concernée.
     *
     * @return Integer représentant les points de vies.
     */
    public int getpV() {
        return pV;
    }

    /**
     * Fonction qui renvoie l'état actuel de la plante concernée.
     *
     * @return Variable de type EtatPlante représentant l'état d'une plante.
     */
    public Plante.EtatPlante getEtat() {
        return etat;
    }

    /**
     * Méthode qui permet de changer l'état de la plante concernée.
     *
     * @param etat Variable de type EtatPlante, elle représente dans quel état
     * est une plante piégée
     */
    public void setEtat(Plante.EtatPlante etat) {
        this.etat = etat;
    }

    /**
     * Fonction qui renvoie les coordonées de la plante concernée
     *
     * @return Tableau de integer contenant les coordonées.
     */
    protected int[] getCoordYX() {
        return coordYX;
    }

    /**
     * Fonction qui renvoie une String représentant les coordonnées de la plante
     * concernée.
     *
     * @return String représentant les coordonnées.
     */
    protected final String toStringCoordYX() {
        return coordYX[0] + " " + coordYX[1];
    }

    /**
     * Méthode qui sert à réduire les points de vies de la plante concernée.
     * Elle est utilisée dans la méthode endommagePlante du modèle.
     *
     * @param dmg Integer représentant les dommages d'une plante. Seules les
     * Tire-Pois et les Lance-Melons font des dommages.
     */
    protected void reduirePV(int dmg) {
        this.pV -= dmg;
    }

    /**
     * Fonction qui renvoie les dommages que les projectiles d'une plante peut
     * effectuer sur un zombie.
     *
     * @return Integer représentant les dommages d'un projectile.
     */
    protected int getDmg() {
        return dmg;
    }

    /**
     * Fonction qui renvoie le type de la plante concernée.
     *
     * @return String qui représente le type d'une plante.
     */
    protected String getType() {
        return typePlante;
    }

    /**
     * Classe de type énumération qui sert à représenter les états des plantes,
     * particulièrement ceux des plantes piège.
     */
    public enum EtatPlante {

        /**
         * Une plante piège à capturer un zombie.
         */
        active,
        /**
         * Une plante piège n'est pas en train de retenir un zombie.
         */
        desactive,
        /**
         * État des plantes autre que plante piège.
         */
        pasApplicable
    }

    /**
     * Fonction qui renvoie si oui ou non une plante est dans l'état désactivé.
     * True si elle est désactivée et false si elle est autre chose.
     *
     * @return Boolean qui représente si la plante est activée ou non.
     */
    public boolean isDesactive() {
        boolean isDesactive = false;
        if (this.getEtat() == Plante.EtatPlante.desactive) {
            isDesactive = true;
        }
        return isDesactive;
    }
}
