/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pvz.vue;

import pvz.controleur.PvZControleur;

/**
 *
 * @author Xavier Reid et Philippe Marcotte
 */
public class MasterThread extends Thread {

    /*
     * Integer qui représente le temps que le thread doit attendre avant d'appeler les autres méthodes dans la fonction run().
     */
    private int sleepTime;
    /*
     * Long qui représente l'image à laquelle le thread est rendu.
     */
    private long currentFrameNumber;
    /*
     * Déclaration d'une variable de type PvZControleur qui permet au thread de communiquer avec le contrôleur.
     */
    private PvZControleur controleur;
    /*
     * Déclaration de la fenêtre principal pour pouvoir communiquer avec celle-ci.
     */
    private FntPrincipale fntPrincipale;

    /**
     * Constructeur du thread où l'initialise la fenêtre principal, le
     * contrôleur, le sleeptime et le compteur de l'image actuel. On appelle
     * aussi la méthode start() pour que le thread démarre.
     *
     * @param controleur Permet d'initialiser le contrôleur et, ainsi, établir
     * la communication entre les deux.
     * @param fntPrincipale Permet d'initialiser la fenêtre principale et,
     * ainsi, établir la communication entre les deux.
     */
    public MasterThread(final PvZControleur controleur, final FntPrincipale fntPrincipale) {
        this.fntPrincipale = fntPrincipale;
        this.controleur = controleur;
        this.sleepTime = 1000 / controleur.getFPS();
        this.currentFrameNumber = 0;
        this.start();
    }

    @Override
    public void run() {
        while (true) {
            try {
                this.sleep(sleepTime);
                this.currentFrameNumber++;
                fntPrincipale.miseAJourFenetre();

            } catch (InterruptedException ex) {
                System.out.println("InterruptedException error: ");
                ex.printStackTrace();
            }

        }
    }

    /**
     * Fonction qui renvoie le compteur de l'image actuelle au moment où on
     * l'appel.
     *
     * @return Long qui représente le compteur de l'image actuelle.
     */
    public long getCurrentFrameNumber() {
        return this.currentFrameNumber;
    }
}
