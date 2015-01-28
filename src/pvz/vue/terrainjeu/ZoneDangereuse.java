/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pvz.vue.terrainjeu;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.ArrayList;
import javax.swing.JComponent;

/**
 *
 * @author Xavier Reid et Philippe Marcotte
 */
/**
 * Objet visuel montrant qu'une case est dangereuse aux Veggies marchant dedans.
 * @author Xavier Reid et Philippe Marcotte
 */
public class ZoneDangereuse extends JComponent {

    private Image img1 = Toolkit.getDefaultToolkit().getImage("ZoneDangereuse0.gif");
    private Image img2 = Toolkit.getDefaultToolkit().getImage("ZoneDangereuse1.gif");
    private int indexImageActuelle = 0;
    private Image imageActuelle;
    
/**
 * Constructeur de ZoneDangereuse;
 */
    public ZoneDangereuse() {
        this.setSize(79, 124);
        imageActuelle = img1;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(imageActuelle, 0, 0, this);
    }

    /**
     * Change l'image actuelle de la zone dangereuse à l'autre image possible.
     */
    public void actualiser() {
        if (indexImageActuelle == 0) {
            indexImageActuelle = 1;
            imageActuelle=img1;
        } else {
            indexImageActuelle = 0;
            imageActuelle=img2;
        }
    }
}
