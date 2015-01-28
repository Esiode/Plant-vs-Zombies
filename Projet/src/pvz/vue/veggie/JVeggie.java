package pvz.vue.veggie;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.ArrayList;
import javax.swing.JComponent;
import pvz.controleur.PvZControleur;
import pvz.modele.Zombie;

public abstract class JVeggie extends JComponent {

    /**
     * Float qui représente la position exacte du JVeggie dans la Lane (ensuite arrondi lorsqu'on appelle JVeggie.setLocation()).
     */
    private float posiX;
    /**
     * Float qui représente la vitesse de déplacement du JVeggie.
     */
    private float vitesse;
    /**
     * String contenant le type de veggie que ce JVeggie représente afin de lui associer le bon zombie du modèle.
     */
    private String typeZombi;
    /**
     * Integer représentant  le numéro de zombie du JVeggie afin de lui associer le bon zombie du modèle.
     */
    private int noZombi;
    /**
     * Contrôleur associé à JVeggie.
     */
    private PvZControleur controleur;
    /**
     * ArrayList contenant les sprites associés à un certain type de JVeggie en train de marcher.
     */
    private ArrayList<Image> listeMarche = new ArrayList<>();
    /**
     * ArrayList contenant les sprites associés à un certain type de JVeggie en train de manger.
     */
    private ArrayList<Image> listeMange = new ArrayList<>();
    /**
     * ArrayList contenant les sprites associés à un certain type de JVeggie en train de marcher penché.
     */
    private ArrayList<Image> listePenche = new ArrayList<>();
    /**
     * Integer représentant l'index de l'image que le JVeggie doit afficher à partir d'un certain arrayList.
     */
    private int indexImgActuel;
    /**
     * Image que le JVeggie affiche au prochain repaint.
     */
    private Image imgActuelle;
    /**
     * String qui permet de savoir à quel zombie du modèle ce JVeggie est associé.
     */
    private String ID;

    /**
     * Fonction renvoyant le typeDeZombie associé à ce JVeggie.
     * @return le type de Veggie du JVeggie.
     */
    public String getTypeZombi() {
        return typeZombi;
    }
    /**
     * Méthode initialisant l'ID du zombie.
     * @param ID L'ID sauvegardée en mémoire de ce JVeggie.
     */
    public void setID(String ID) {
        this.ID = ID;
    }

    /**
     * Fonction renvoyant l'ID de ce JVeggie.
     * @return L'ID du JVeggie.
     */
    public String getID() {
        return ID;
    }

    /**
     * Méthode modifiant la vitesse de ce JVeggie.
     * @param vitesse Nouvelle vitesse du JVeggie.
     */
    public void setVitesse(float vitesse) {
        this.vitesse = vitesse;
    }
    
    /**
     * Constructeur de JVeggie.
     * @param posiX Float qui représente la vitesse de déplacement du JVeggie.
     * @param typeZombi String contenant le type de veggie que ce JVeggie représente afin de lui associer le bon zombie du modèle.
     * @param noZombi Integer représentant  le numéro de zombie du JVeggie afin de lui associer le bon zombie du modèle.
     * @param controleur Contrôleur.
     */
    public JVeggie(float posiX, String typeZombi, int noZombi, final PvZControleur controleur) {
        Image img;
        for (int i = 0; i < 4; i++) {
            img = Toolkit.getDefaultToolkit().getImage(typeZombi + (i + 1) + ".gif");
            listeMarche.add(img);
        }

        for (int i = 0; i < 4; i++) {
            img = Toolkit.getDefaultToolkit().getImage(typeZombi + "Mange"+ (i + 1) + ".gif");
            listeMange.add(img);
        }

        for (int i = 0; i < 4; i++) {
            img = Toolkit.getDefaultToolkit().getImage(typeZombi +"Penche"+ (i + 1) + ".gif");
            listePenche.add(img);
        }

        this.imgActuelle = listeMarche.get(0);
        this.posiX = posiX;
        this.setSize(110, 100);
        this.setLocation((int) posiX, 0);
        this.typeZombi = typeZombi;
        this.noZombi = noZombi;
        this.ID = typeZombi + noZombi;
        this.controleur = controleur;
        controleur.creeZombie(typeZombi, ID, noZombi);
    }


    /**
     * Méthode modifiant et déplaçant le JVeggie en fonction de son zombie du modèle associé.
     */
    public void actualiser() {
        if (this instanceof LimboVeggie && controleur.getTemps()%30==0) {
            controleur.playSound("untz.wav");
        }
        deplacer();
        if (controleur.getTemps() % 12 == 0) {
            if (indexImgActuel == 3) {
                indexImgActuel = 0;
            } else if (controleur.getZombie(ID).getEtat()!=Zombie.EtatZombie.piege) {
                indexImgActuel++;
            }
            switch (controleur.getZombie(ID).getEtat()) {
                case piege:
                    imgActuelle = listeMarche.get(indexImgActuel);
                    break;
                case debout:
                    imgActuelle = listeMarche.get(indexImgActuel);
                    break;
                case mange:
                    imgActuelle = listeMange.get(indexImgActuel);
                    break;
                case limboMode:
                    imgActuelle = listePenche.get(indexImgActuel);
                    break;
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(imgActuelle, 0, 0, this);
    }

    /**
     * Méthode qui change la position du JVeggie dans la Lane en fonction de sa vitesse.
     */
    public void deplacer() {
        posiX += vitesse;
        this.setLocation((int) posiX, this.getY());
    }
}