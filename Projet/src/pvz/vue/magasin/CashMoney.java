/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pvz.vue.magasin;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import pvz.controleur.PvZControleur;
import pvz.vue.terrainjeu.PnlTerrainJeu;

/**
 * Classe qui représente l'objet visuel qu'est la main tenant un sac d'argent.
 *
 * <p>Elle dessine l'image que représente cet objet et permet de l'actualiser et
 * tester lorsqu'elle doit changer, disparaître ou réapparaisse.</p>
 *
 * @author Xavier Reid et Philippe Marcotte
 */
public class CashMoney extends JComponent {

    /*
     * Boolean qui représente si l'objet devrait être visible sur l'écran ou non.
     */
    private boolean isActivated;
    /*
     * Déclaration du contrôleur pour établir la communication entre les deux.
     */
    private PvZControleur controleur;
    /*
     * Integer qui représente l'emplacement auquel l'objet doit être pour ne pas être visible sur l'écran.
     */
    private int limiteInvisible = -100;
    /*
     * Integer qui représente l'emplacement auquel l'objet doit être pour être visible sur l'écran.
     */
    private int limiteVisible = 0;
    /*
     * Contient l'image de l'objet lorsqu'il tient un sac d'argent.
     */
    private ImageIcon imgActif = new ImageIcon("CashMoney(100x100).gif");
    /*
     * Contient l'image de l'objet lorsqu'il n'a plus de sac d'argent.
     */
    private ImageIcon imgInactif = new ImageIcon("CashMoney2(100x100).gif");
    /*
     * Va contenir l'image que l'objet doit afficher au moment de l'appel de la variable.
     */
    private ImageIcon currentImg;
    /*
     * Random qui sert à déterminer les prochains emplacements de l'objet visuel.
     */
    private Random rnd = new Random();
    /*
     * Integer qui représente le prochain emplecement de l'objet.
     */
    private int rndInt = rnd.nextInt(554) + 130;
    /*
     * Déclaration du panneau principal pour établir la communication entre les deux.
     */
    private PnlTerrainJeu pnlTerrainJeu;

    /**
     * Constructeur de l'objet visuel qui initialise le lien entre le panneau
     * principal, le contrôleur, l'état par défaut de l'objet, ainsi que son
     * image, la taille en pixel de l'objet et le mouseListener qui va faire
     * changer l'objet quand le joueur clique dessus.
     *
     * @param controleur Permet d'initialiser le contrôleur et, ainsi, établir
     * la communication entre les deux.
     * @param pnlTerrainJeu Permet d'initialiser la fenêtre principale et,
     * ainsi, établir la communication entre les deux.
     */
    public CashMoney(final PvZControleur controleur, final PnlTerrainJeu pnlTerrainJeu) {
        this.pnlTerrainJeu = pnlTerrainJeu;
        this.controleur = controleur;
        this.isActivated = true;
        currentImg = imgActif;
        this.setSize(100, 100);
        this.setLocation(-100, rndInt);
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (isActivated) {
                    controleur.playSound("kaching.wav");
                    controleur.modCash(25);
                    ((CashMoney) e.getSource()).isActivated = false;
                    currentImg = imgInactif;
                }
            }
        });

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(currentImg.getImage(), 0, 0, this);
    }

    /**
     * Méthode qui permet de repeindre selon les changements effectuer avant. Si
     * l'image est activé alors celle-ci utilise un certain .gif qui est
     * différent de lorsqu'elle n'est pas activée.
     */
    public void actualiser() {
        /*
         * Integer qui représente le temps que l'objet doit attendre avant de réapparaître.
         */
        int intervalleRespawn = controleur.getFPS() * 30;
        /*
         * Integer qui représente le temps que l'objet reste visible dans la fenêtre avant de disparaître et attendre le moment de retour.
         */
        int tempsExistence = controleur.getFPS() * 10;
        if (isActivated && controleur.getTemps() % tempsExistence == 0) {
            isActivated = false;
        }
        if (controleur.getTemps() % intervalleRespawn == 0) {
            rndInt = rnd.nextInt(554) + 130;
            this.isActivated = true;
            currentImg = imgActif;
        }

        int posiX = this.getX();
        int posiY = rndInt;
        if (isActivated) {
            posiX += 3;
            if (posiX > limiteVisible) {
                posiX = limiteVisible;
            }
        } else if (!isActivated) {
            posiX -= 3;
            if (posiX < limiteInvisible) {
                posiX = limiteInvisible;
            }
        }
        this.setLocation(posiX, posiY);
    }
}
