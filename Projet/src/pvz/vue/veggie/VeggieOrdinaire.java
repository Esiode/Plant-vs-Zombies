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
 * Objet visuel qui représente un Veggie Ordinaire
 *
 * <p>Cette classe contient toutes les informations nécessaires au bon
 * fonctionnement visuel des Veggies Ordinaires. Elle contient des valeurs 
 * prédéterminées de variables permettant de distinguer les Veggies Ordinaires des 
 * autres sortes de JVeggies.</p>
 *
 * @author Xavier Reid et Philippe Marcotte
 */
public class VeggieOrdinaire  extends JVeggie{
    
    
    public VeggieOrdinaire(float posiX, int noZombi, final PvZControleur controleur) {
        super(posiX, "VO", noZombi, controleur);
    }
    
}
