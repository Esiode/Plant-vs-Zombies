package pvz.vue.projectiles;

import java.awt.Color;
import java.awt.Graphics;

/**
 * Classe qui représente tous les objets visuels de type projectiles plus
 * précisément les pois.
 *
 * <p>Elle contient les méthodes pour le bon fionctionement visuel des pois.
 * Elle détermine comment ceux-ci se déplacent et leur apparence.</p>
 *
 * @author Xavier Reid et Philippe Marcotte
 */
public class Pois extends JProjectile {

    /*
     * Float qui représente la vitesse de déplacement d'un pois.
     */
    private float vitesse = 2.16f;
    /*
     * Float qui représente sa position sur la longueur actuelle lors de l'appel de la variable.
     */
    private float posX;

    /**
     * Constructeur des pois. Il instancie sa position sur la longueur initiale,
     * sa taille et sa position initiale.
     *
     * @param posX Float qui représente sa position sir la longueur.
     * @param posY Integer qui représente sa position sur l'hauteur.
     */
    public Pois(float posX, int posY) {
        super("TirePois");
        this.posX = posX;
        this.setSize(20, 20);
        this.setLocation((int) posX, posY);
    }

    @Override
    public void paintComponent(Graphics g) {
        g.setColor(Color.green);
        g.fillOval(0, 0, 20, 20);
    }

    @Override
    public void deplacement() {
        posX += vitesse;
        this.setLocation((int) posX, this.getY());
    }

    @Override
    public void actualiser() {
        deplacement();
        this.invalidate();
        this.repaint();
    }
}