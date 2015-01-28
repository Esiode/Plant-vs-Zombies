/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pvz.vue.veggie;

import java.awt.Graphics;
import pvz.controleur.PvZControleur;

/**
 *
 * @author Xavier Reid et Philippe Marcotte
 */
/**
 * Objet visuel qui représente un Voirie
 *
 * <p>Cette classe contient toutes les informations nécessaires au bon
 * fonctionnement visuel des Voiries. Elle contient des valeurs 
 * prédéterminées de variables permettant de distinguer les Voiries des 
 * autres sortes de JVeggies.</p>
 *
 * @author Xavier Reid et Philippe Marcotte
 */
public class GrosVeggie extends JVeggie{
    public GrosVeggie(float posiX, int noZombi, final PvZControleur controleur){
        super(posiX, "GV", noZombi, controleur);
    }
}
