package pvz.modele;

import java.util.ArrayList;

/**
 * Classe qui sert à gérer et calculer les mécaniques du jeu.
 *
 * <p>Il eneleve les points de vies des zombies ou plantes et réduire le montant
 * d'argent du joueur selon si c'est un achat ou un gain. Il sert aussi à
 * changer les états des zombies selon si un coach veggie, un limbo veggie est
 * dans la même la ligne, ou s'il est piégé par une plante. Finalement, il
 * calcule la quantit/ de zombies à créer dans une nouvelle vague</p>
 *
 * @author Xavier Reid et Philippe Marcotte
 */
public class Modele {

    private int noZombie = 0;
    /*
     * Integer qui représente le montant total d'argent que le joueur a en sa possession.
     */
    private int cash = 425;
    /*
     * ArrayList représentant tous les zombies vivant dans le jeu. Ceux-ci sont une représentation "statistique" des JVeggie
     * qui ne sont que l'aspect visuel des zombies.
     */
    private ArrayList<Zombie> listeZombie = new ArrayList<>();
    /*
     * ArrayList représentant toutes les plantes vivantes dans le jeu. Ceux-ci sont une représentation "statistique" des JPlante 
     * qui ne sont que l'aspect visuel des plantes.
     */
    private ArrayList<Plante> listePlante = new ArrayList<>();
    /*
     * Integer représentant le nombre de zombie à créer à la vague actuelle.
     */
    private int nbZombieCetteVague = 5;
    /*
     * Float représentant de combien le nombre de zombie, à créer à la prochaine vague, doit augmenter.
     */
    private float multiplicateurZombie = 1.2F;
    /*
     * Tableau de 2 integer qui permet, comme le ID des zombies,
     * de savoir quelle plante dans le modèle correspond à quelle plante dans la
     * vue.
     */
    private int[] coordYX = new int[2];
    /*
     * Objet représentant statistiquement ce que le joueur peut planter pour se défendre contre les hordes de zombies. 
     * Dans ce cas-ci, il sert uniquement a obtenir les dommages de la plante Tire Pois.
     */
    private Plante tirePois = new Plante("TirePois", coordYX);
    /*
     * Objet représentant statistiquement ce que le joueur peut planter pour se défendre contre les hordes de zombies.
     * Dans ce cas-ci, il sert uniquement a obtenir les dommages de la plante Lance Melon.
     */
    private Plante lanceMelon = new Plante("LanceMelon", coordYX);

    /**
     * Fonction renvoyant toute la liste contenant les plantes du modèle pour
     * permettre une quelconque utilisation dans la vue.
     *
     * @return ArrayList contenant les plantes.
     */
    public ArrayList<Plante> getListePlantes() {
        return listePlante;
    }

    /**
     * Fonction renvoyant un zombie précis, selon l'identité entrée en
     * paramètre, pour permettre une quelconque utilisation dans la vue.
     *
     * @return ArrayList contenant les zombies.
     */
    public ArrayList<Zombie> getListeZombie() {
        return listeZombie;
    }

    /**
     * Fonction renvoyant un zombie précis, selon l'identité entrée en
     * paramètre, pour permettre une quelconque utilisation dans la vue.
     *
     * @param ID String qui permet de savoir quel zombie dans le modèle
     * correspond à quel zombie dans la vue.
     * @return Le zombie correspondant à l'identité en paramètre.
     */
    public Zombie getZombie(String ID) {
        Zombie zombieSelection = null;
        for (Zombie zombie : listeZombie) {
            if (ID.equalsIgnoreCase(zombie.getID())) {
                zombieSelection = zombie;
            }
        }
        return zombieSelection;
    }

    /**
     * Fonction renvoyant une plante précise, selon les coordonnées entrées en
     * paramètre, pour permettre une quelconque utilisation dans la vue.
     *
     * @param coordYX Tableau de 2 integer qui permet, comme le ID des zombies,
     * de savoir quelle plante dans le modèle correspond à quelle plante dans la
     * vue.
     * @return La plante correspondant aux coordonnées en paramètre.
     */
    public Plante getPlante(int[] coordYX) {
        Plante planteSelection = null;
        for (Plante plante : listePlante) {
            if (coordYX == plante.getCoordYX()) {
                planteSelection = plante;
            }
        }
        return planteSelection;
    }

    /**
     * Méthode qui permet d'appliquer une transaction lorsqu'un joueur achète
     * une plante ou lorsqu'il appuie sur un sac d'argent. On entre en paramètre
     * le montant à ajouter ou à retirer.
     *
     * @param transaction Integer représentant le montant d'argent à ajouter ou
     * à retirer au total qui est contenu dans le modèle.
     */
    public void modCash(int transaction) {
        if (cash + transaction >= 0) {
            cash += transaction;
        }
    }

    /**
     * Fonction qui permet à la vue de savoir le montant d'argent que le joueur
     * a en sa possession au moment de l'appel.
     *
     * @return Integer représentant le montant d'argent total.
     */
    public int getCash() {
        return this.cash;
    }

    /**
     * Méthode qui permet de synchroniser la création de zombie dans la vue et
     * le modèle. Elle est utiliser dans le contrôleur pour que la vue puisse
     * l'appeler ensuite.
     *
     * @param typeZombie String qui permet au modèle de savoir quel type de
     * zombie créé puisque les statistiques différent d'un type à un autre.
     * @param ID String qui permet de savoir quel zombie dans le modèle
     * correspond à quel zombie dans la vue.
     * @param noLane Integer qui permet de savoir dans quelle ligne le zombie
     * créé se situe.
     */
    public void creeZombie(String typeZombie, String ID, int noLane) {
        listeZombie.add(new Zombie(typeZombie, ID, noLane));
    }

    /**
     * Méthode qui permet de synchroniser la création de plantes dans la vue et
     * le modèle. Elle est utiliser dans le contrôleur pour que la vue puisse
     * l'appeler ensuite.
     *
     * @param typePlante String qui permet au modèle de savoir quel type de
     * plante créé puisque les statistiques différent d'un type à l'autre.
     * @param coordYX Tableau de 2 integer qui permet, comme le ID des zombies,
     * de savoir quelle plante dans le modèle correspond à quelle plante dans la
     * vue.
     */
    public void creePlante(String typePlante, int[] coordYX) {
        listePlante.add(new Plante(typePlante, coordYX));
    }

    /**
     * Méthode, qui est appeler dans le contrôleur, qui permet au modèle
     * d'enlever les points de vies aux zombies lorsque lors homologue JVeggie
     * entre en collision avec un projectile.
     *
     * @param ID String qui permet de savoir quel zombie dans le modèle
     * correspond à quel zombie dans la vue.
     * @param typePlante String qui permet au modèle de savoir quel type de
     * plante est créé puisque les statistiques différent d'un type à l'autre.
     * Dans ce cas-ci, elle permet de savoir combien de dommage le projectile
     * fait au zombie.
     */
    public void endommageZombie(String ID, String typePlante) {
        Zombie zombie = getZombie(ID);
        if (typePlante.equalsIgnoreCase(tirePois.getType())) {
            zombie.reduirePV(tirePois.getDmg());
        } else if (typePlante.equalsIgnoreCase(lanceMelon.getType())) {
            zombie.reduirePV(lanceMelon.getDmg());
        }
    }

    /**
     * Méthode, qui est appeler dans le contrôleur, qui permet au modèle
     * d'enlever les points de vies aux plantes lorsque lors homologue JPlante
     * se fait dévorer par un zombie.
     *
     * @param coordYX Tableau de 2 integer qui permet, comme le ID des zombies,
     * de savoir quelle plante dans le modèle correspond à quelle plante dans la
     * vue.
     * @param typeZombie String qui permet au modèle de savoir quel type de
     * zombie il s'agit puisque les statistiques différent d'un type à un autre.
     * Dans ce cas-ci, elle sert à savoir combien de dommage le zombie fait à la
     * plante.
     */
    public synchronized void endommagePlante(int[] coordYX, String typeZombie) {
        for (Plante plante : listePlante) {
            if (coordYX == plante.getCoordYX()) {
                boolean degatsInfliges = false;
                for (int i = 0; !degatsInfliges && i < listeZombie.size(); i++) {
                    Zombie zombie = listeZombie.get(i);
                    if (typeZombie.equalsIgnoreCase(zombie.getTypeZombie())) {
                        plante.reduirePV(zombie.getDmg());
                        degatsInfliges = true;
                    }
                }
            }
        }
    }

    /**
     * Ffonction renvoyant le nombre de zombies qui vont être créés dans la
     * prochaine vague.
     *
     * @param noVague Integer qui permet de différencier la première vague des
     * suivantes.
     * @return Integer représentant le nombre de zombies à créer dans la
     * prochaine vague.
     */
    public int nbZombieNouvelleVague(int noVague) {
        if (noVague >= 2) {
            nbZombieCetteVague *= multiplicateurZombie;
        }
        return nbZombieCetteVague;
    }

    /*
     * Méthode qui permet de modifier le mutliplicateur de zombies qui régit de combien grossisse chaque nouvelle vague de zombie.
     */
    public void setMultiplicateurZombie(float multiplicateurZombi) {
        this.multiplicateurZombie = multiplicateurZombi;
    }

    /**
     * * Renvoie un numéro encore inutilisé pour créer un zombie.
     *
     * @return Un numéro de zombie qui n'a pas encore été assigné.
     */
    public int getNombreDeZombiesApparus() {
        noZombie++;
        return this.noZombie;
    }
}