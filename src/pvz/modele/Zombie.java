package pvz.modele;

/**
 * Classe qui sert de représentation statistique de leur homologue visuel,
 * JVeggie.
 *
 * <p>Elle contient les statistiques comme les points de vies des zombies, les
 * dommages, les identitées, les états, les powerUp, la ligne sur laquelle un
 * zombie est, le type d'un zombie et quelle plante piège retient un zombie.
 * Elle contient aussi les méthodes qui permettent de modifier l'état d'un
 * zombie, la plante qui le piège et les tests d'états.</p>
 *
 * @author Xavier Reid et Philippe Marcotte
 */
public class Zombie {

    /*
     * Integer représentant le nombre de points de vies
     */
    private int pV;
    /*
     * Integer représentant les dommages fait par tous les zombies.
     */
    private int dmg = 1;
    /*
     * String représentant l'identité d'un zombie ce qui permet au programme de les correspondre avec leur homologue visuel.
     */
    private String ID;
    /*
     * Integer représentant la ligne sur laquelle le zombie se situe.
     */
    private int noLane;
    /*
     * Variable de type EtatZombie qui représente l'état dans lequel un zombie est.
     */
    private Zombie.EtatZombie etat;
    /*
     * Boolean qui détermine si un zombie peut accélérer ou non.
     */
    private boolean peutSonicMode = true;
    /*
     * Boolean qui détermine si un zombie peut esquiver les projectiles.
     */
    private boolean peutLimboMode = true;
    /*
     * String qui représente lee type d'un zombie.
     */
    private String typeZombie;
    /*
     * Plante qui représente la plante qui retient un zombie piégé.
     */
    private Plante plantePiege;

    /**
     * Constructeur d'un zombie où l'initialise la ligne dans laquelle le zombie
     * se situe, l'identité de celui-ci, son état par défaut, le type de zombie,
     * ses points de vies et s'il peut se pencher ou s'il peut accélérer.
     *
     * @param typeZombie
     * @param ID
     * @param noLane
     */
    protected Zombie(String typeZombie, String ID, int noLane) {
        this.noLane = noLane;
        this.ID = ID;
        this.etat = Zombie.EtatZombie.debout;
        this.typeZombie = typeZombie;
        switch (typeZombie) {
            case "GV":
                this.pV = 1200;
                break;
            case "VO":
                this.pV = 600;
                break;
            case "LV":
                this.pV = 500;
                this.peutLimboMode = false;
                break;
            case "CV":
                this.pV = 900;
                this.peutSonicMode = false;
                break;
        }
    }
    
    /**
     * Fonction qui renvoie la plante piégeant le zombie concerné.
     *
     * @return Plante piège
     */
    public Plante getPlantePiege() {
        return plantePiege;
    }

    /**
     * Méthode qui permet de déterminer quelle plante piège le zombie concerné
     *
     * @param plantePiege Représente la plante qui retient un zombie piégé.
     */
    public void setPlantePiege(Plante plantePiege) {
        this.plantePiege = plantePiege;
    }

    /**
     * Fonction qui renvoie le type du zombie concerné.
     *
     * @return String qui représente le type de zombie.
     */
    public String getTypeZombie() {
        return typeZombie;
    }

    /**
     * Fonction qui renvoie si le zombie concerné peut accélérer ou non.
     *
     * @return Boolean qui est true si le zombie peut accélérer, et false s'il
     * ne peut pas.
     */
    public boolean isSonicModePossible() {
        return this.peutSonicMode;
    }

    /**
     * Fonction qui renvoie si le zombie peut se pencher ou non.
     *
     * @return Boolean qui est true si le zombie peut se pencher, et false s'il
     * ne peut pas.
     */
    public boolean isLimboModePossible() {
        return this.peutLimboMode;
    }

    /**
     * Fonction qui renvoie l'état du zombie concerné.
     *
     * @return Variable de type EtatZombie qui représente les différents états
     * dans lequel un zombie peut être.
     */
    public Zombie.EtatZombie getEtat() {
        return etat;
    }

    /**
     * Méthode qui permet de changer l'état du zombie concerné.
     *
     * @param etat Variable de type EtatZombie qui représente les différents
     * états dans lequel un zombie peut être.
     */
    public void setEtat(Zombie.EtatZombie etat) {
        this.etat = etat;
    }

    /**
     * Classe de type énumération contenant tous les états dans lequel un zombie
     * peut être.
     */
    public enum EtatZombie {

        /**
         * Le zombie est immobilisé par une plante piège.
         */
        piege,
        /**
         * Le zombie est en train de manger une plante.
         */
        mange,
        /**
         * Le zombie est dans son état normal où il ne fait qu'avancer.
         */
        debout,
        /**
         * Le zombie est penché et esquive les projectiles de Tire-Pois.
         */
        limboMode
    }

    /**
     * Fonction qui renvoie si le zombie concerné est piégé ou non.
     *
     * @return Boolean qui est false si le zombie n'est pas piégé, et true s'il
     * l'est.
     */
    public boolean isPiege() {
        boolean isPiege = false;
        if (this.getEtat() == Zombie.EtatZombie.piege) {
            isPiege = true;
        }
        return isPiege;
    }

    /**
     * Fonction qui renvoie si le zombie concerné est en train de manger une
     * plante ou non.
     *
     * @return Boolean qui est false s'il n'est pas en train de manger, et true
     * s'il l'est.
     */
    public boolean isMange() {
        boolean isMange = false;
        if (this.getEtat() == Zombie.EtatZombie.mange) {
            isMange = true;
        }
        return isMange;
    }

    /**
     * Fonction qui renvoie si le zombie concerné est en train d'esquiver les
     * projectiles des Tire-Pois ou non.
     *
     * @return Boolean qui est false s'il n'est pas train d'esquiver, et true
     * s'il l'est.
     */
    public boolean isLimboMode() {
        boolean isPenche = false;
        if (this.getEtat() == Zombie.EtatZombie.limboMode) {
            isPenche = true;
        }
        return isPenche;
    }

    /**
     * Fonction qui permet de réduire les points de vie du zombie concerné.
     *
     * @param dmg Integer représentant les dommages fait par tous les zombies.
     */
    protected void reduirePV(int dmg) {
        this.pV -= dmg;
    }

    /**
     * Fonction qui renvoie les points de vies actuels du zombie concerné.
     *
     * @return Integer représentant les points de vies.
     */
    public int getpV() {
        return pV;
    }

    /**
     * Fonction qui renvoie les dommage que peut faire un zombie à une plante.
     *
     * @return Integer représentant les dommages.
     */
    protected int getDmg() {
        return dmg;
    }

    /**
     * Fonction qui renvoie l'identité du zombie concerné.
     *
     * @return String qui représente l'identité.
     */
    protected String getID() {
        return ID;
    }
}
