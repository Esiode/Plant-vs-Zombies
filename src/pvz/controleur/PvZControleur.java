package pvz.controleur;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import pvz.modele.Modele;
import pvz.modele.Plante;
import pvz.modele.Plante.EtatPlante;
import pvz.modele.Zombie;
import pvz.modele.Zombie.EtatZombie;
import pvz.vue.FntPrincipale;
import pvz.vue.MasterThread;
import pvz.vue.terrainjeu.PnlTerrainJeu;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

/**
 * Le contrôleur régit la communication entre la fenêtre principale et le
 * modèle. Il initialise la dite fenêtre principale et le dit modèle.
 *
 * <p>Le contrôleur contient les méthodes qui vont permettre de synchroniser la
 * cération des plantes, lorsqu'un joueur en plante une, et la création des
 * zombies lorsque ceux-ci commencent à attaquer. De plus, il permet, grâce à de
 * multiple fonction, la communication avec la vue (fenêtre principale) et le
 * modèle. Pour exemple, lorsqu'un zombie se fait attaquer par un projectile en
 * premier dans la vue, c'est par une méthode du contrôleur que le modèle va
 * pouvoir connaître quel zombie précisément s'est fait attaquer. La vue se sert
 * du controleur pour connaître les différents états des plantes et des zombies
 * qui sont contenus dans le modèle. Finalement, il permet au modèle de
 * connaître la vague où le jeu est rendu et, ainsi, calculer la grosseur de la
 * prochaine vague de zombies proportionnellement.</p>
 *
 * @author Xavier Reid et Philippe Marcotte
 */
public class PvZControleur {

    /*
     * Déclaration de thread pour ensuite l'initialiser pour faire fonctionner les animations et utiliser ses méthodes pour avoir le temps
     * et contrôler le nombre d'image afficher par seconde.
     * 
     */
    private MasterThread masterThread;
    /*
     * Déclaration de la fenêtre principale pour initialiser tout l'aspect visuel du jeu (les sprites, le magasin, le fond, etc).
     */
    private FntPrincipale fntPrincipale;
    /*
     * Déclaration du modèle pour ensuite l'initialiser et, ainsi, utiliser ses méthodes.
     */
    private Modele modele;
    /*
     * Permet de contrôler à quelle vitesse la vue rafraîchie les images.
     */
    private int FPS = 60;

    /**
     * Constructeur du contrôleur où l'on initialise le modèle, la fenêtre
     * principal et le thread en passant le contrôleur aux deux derniers pour
     * ainsi utiliser ses méthodes et la fenêtre principale au thread pour que
     * celui mette à jour la vue.
     */
    public PvZControleur() {
        this.modele = new Modele();
        this.fntPrincipale = new FntPrincipale(this);
        this.masterThread = new MasterThread(this, fntPrincipale);
    }

    /**
     * Méthode qui permet de synchroniser la création de zombie dans la vue et
     * le modèle. Elle réfère à la méthode qui crée les zombies dans le modèle.
     *
     * @param typeZombie String qui permet au modèle de savoir quel type de
     * zombie créé puisque les statistiques différent d'un type à un autre.
     * @param ID String qui permet de savoir quel zombie dans le modèle
     * correspond à quel zombie dans la vue.
     * @param noLane Integer qui permet de savoir dans quelle ligne le zombie
     * créé se situe.
     */
    public void creeZombie(String typeZombie, String ID, int noLane) {
        modele.creeZombie(typeZombie, ID, noLane);
    }

    /**
     * Méthode qui permet de synchroniser la création de plantes dans la vue et
     * le modèle. Elle réfère à la méthode qui crée les plantes dans le modèle.
     *
     * @param typePlante String qui permet au modèle de savoir quel type de
     * plante créé puisque les statistiques différent d'un type à l'autre.
     * @param coordYX Tableau de 2 integer qui permet, comme le ID des zombies,
     * de savoir quelle plante dans le modèle correspond à quelle plante dans la
     * vue.
     */
    public synchronized void creePlante(String typePlante, int[] coordYX) {
        modele.creePlante(typePlante, coordYX);
    }

    /**
     * Méthode qui permet à la vue de dire au modèle qu'un zombie a subi des
     * dommages dû à une collision avec un projectile.
     *
     * @param ID String qui permet de savoir quel zombie dans le modèle
     * correspond à quel zombie dans la vue.
     * @param typePlante String qui permet au modèle de savoir quel type de
     * plante créé puisque les statistiques différent d'un type à l'autre. Dans
     * ce cas-ci, elle permet de savoir combien de dommage le projectile fait au
     * zombie.
     */
    public void endommageZombie(String ID, String typePlante) {
        modele.endommageZombie(ID, typePlante);
    }

    /**
     * Méthode qui permet à la vue de dire au modèle qu'une plante a subi des
     * dommages dû au fait qu'elle se fait manger par un zombie.
     *
     * @param coordYX Tableau de 2 integer qui permet, comme le ID des zombies,
     * de savoir quelle plante dans le modèle correspond à quelle plante dans la
     * vue.
     * @param typeZombie String qui permet au modèle de savoir quel type de
     * zombie créé puisque les statistiques différent d'un type à un autre. Dans
     * ce cas-ci, elle sert à savoir combien de dommage le zombie fait à la
     * plante.
     */
    public void endommagePlante(int[] coordYX, String typeZombie) {
        modele.endommagePlante(coordYX, typeZombie);
    }

    /**
     * Fonction qui appelle la fonction du modèle renvoyant toute la liste
     * contenant les zombies du modèle pour permettre une quelconque utilisation
     * dans la vue.
     *
     * @return ArrayList contenant les zombies du modèle
     */
    public ArrayList getListeZombies() {
        return modele.getListeZombie();
    }

    /**
     * Fonction qui appelle la fonction du modèle renvoyant toute la liste
     * contenant les plantes du modèle pour permettre une quelconque utilisation
     * dans la vue.
     *
     * @return ArrayList contenant les plantes du modèle
     */
    public ArrayList getListePlantes() {
        return modele.getListePlantes();
    }

    /**
     * Fonction qui appelle la fonction du modèle renvoyant une plante précise,
     * selon les coordonnées entrées en paramètre, pour permettre une quelconque
     * utilisation dans la vue.
     *
     * @param coordYX Tableau de 2 integer qui permet, comme le ID des zombies,
     * de savoir quelle plante dans le modèle correspond à quelle plante dans la
     * vue.
     * @return La plante correspondant aux coordonnées en paramètre.
     */
    public Plante getPlante(int[] coordYX) {
        return modele.getPlante(coordYX);
    }

    /**
     * Fonction qui appelle la fonction du modèle renvoyant un zombie précis,
     * selon l'identité entrée en paramètre, pour permettre une quelconque
     * utilisation dans la vue.
     *
     * @param ID String qui permet de savoir quel zombie dans le modèle
     * correspond à quel zombie dans la vue.
     * @return Le zombie correspondant à l'identité en paramètre.
     */
    public Zombie getZombie(String ID) {
        return modele.getZombie(ID);
    }

    /**
     * Fonction permettant à la vue de recevoir la fréquence à laquelle elle
     * doit redessiner les images.
     *
     * @return Integer représensant la fréquence de mise à jour.
     */
    public int getFPS() {
        return this.FPS;
    }

    /**
     * Méthode qui appelle la méthode du modèle qui permet d'appliquer une
     * transaction lorsqu'un joueur achète une plante ou lorsqu'il appuie sur un
     * sac d'argent. On entre en paramètre le montant à ajouter ou à retirer.
     *
     * @param transaction Integer représentant le montant d'argent à ajouter ou
     * à retirer au total qui est contenu dans le modèle.
     */
    public void modCash(int transaction) {
        modele.modCash(transaction);
    }

    /**
     * Fonction qui appelle la fonction du modèle qui permet à la vue de savoir
     * le montant d'argent que le joueur a en sa possession au moment de
     * l'appel.
     *
     * @return Integer représentant le montant d'argent total.
     */
    public int getCash() {
        return modele.getCash();
    }

    /**
     * Fonction qui renvoie le temps qui s'est écoulé depuis l'initialisation du
     * thread. Cette fonction est utilisé plusieurs fois dans la fenêtre
     * principale.
     *
     * @return Long représentant le temps qui s'est écoulé de puis le début du
     * programme.
     */
    public long getTemps() {
        return masterThread.getCurrentFrameNumber();
    }

    /**
     * Méthode qui sert à changer l'état d'une plante dans le modèle depuis la
     * vue. On entre en paramètre la plante pour laquelle on veut changer
     * l'état, et on écrit l'état qu'elle doit avoir après modification.
     * Ensuite, la méthode va appeler celle qui va changer l'état dans le modèle
     * selon la String entrée en paramètre. Cette méthode n'est pertinente que
     * pour les plantes pièges qui peuvent être désactivées ou activées.
     *
     * @param plante Représente la plante dont l'on veut modifier l'état
     * @param etatPlante Représente l'état dans lequel la plante doit être.
     */
    public void setEtat(Plante plante, String etatPlante) {
        EtatPlante etat = null;
        switch (etatPlante) {
            case "active":
                etat = EtatPlante.active;
                break;
            case "desactive":
                etat = EtatPlante.desactive;
                break;
        }
        plante.setEtat(etat);
    }

    /**
     * Méthode qui sert à changer l'état d'un zombie dans le modèle depuis la
     * vue. On entre en paramètre le zombie pour lequel on veut changer l'état ,
     * et on écrit l'état qu'il doit avoir après modification. Ensuite, la
     * méthode va appeler celle qui va changer l'état dans le modèle selon la
     * String entrée en paramètre.
     *
     * @param zombie Représente le zombie dont l'on veut modifier l'état.
     * @param etatZombie Représente l'état dans lequel le zombie doit être.
     */
    public void setEtat(Zombie zombie, String etatZombie) {
        EtatZombie etat = zombie.getEtat();
        switch (etatZombie) {
            case "piege":
                etat = EtatZombie.piege;
                break;
            case "mange":
                etat = EtatZombie.mange;
                break;
            case "debout":
                etat = EtatZombie.debout;
                break;
            case "limboMode":
                etat = EtatZombie.limboMode;
                break;
        }
        zombie.setEtat(etat);
    }

    /**
     * Fonction qui appelle la fonction du modèle renvoyant le nombre de zombies
     * qui vont être créés dans la prochaine vague.
     *
     * @param noVague Integer qui permet de différencier la première vague des
     * suivantes.
     * @return Integer représentant le nombre de zombies à créer dans la
     * prochaine vague.
     */
    public int getNbZombies(int noVague) {
        return modele.nbZombieNouvelleVague(noVague);
    }

    /**
     * Méthode qui sert à changer le multiplicateur régissant de combien
     * grossisse chaque nouvelle vague de zombies.
     */
    public void activerModeDifficile() {
        modele.setMultiplicateurZombie(1.5F);
    }
    
    public int getNumeroZombie(){
        return modele.getNombreDeZombiesApparus();
    }
    
    /**
     * Joue un son.
     * @param soundName String où se situe le son à jouer.
     */
    //    Ce code à été trouvé à http://www.javaworld.com/javaworld/javatips/jw-javatip24.html
    public void playSound(String soundName) {
        // Open an input stream  to the audio file.
        InputStream in;
        try {
            in = new FileInputStream(soundName);
            AudioStream as;
            try {
                as = new AudioStream(in);
                AudioPlayer.player.start(as);
            } catch (IOException ex) {
                Logger.getLogger(PnlTerrainJeu.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PnlTerrainJeu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
