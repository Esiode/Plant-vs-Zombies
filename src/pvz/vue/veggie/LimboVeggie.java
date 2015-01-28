/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pvz.vue.veggie;

import java.awt.Color;
import java.awt.Graphics;
import pvz.controleur.PvZControleur;

/**
 *
 * @author Xavier Reid et Philippe Marcotte
 */
/**
 * Objet visuel qui représente un GhettoBlaster Veggie
 *
 * <p>Cette classe contient toutes les informations nécessaires au bon
 * fonctionnement visuel des GhettoBlaster Veggies. Elle contient des valeurs 
 * prédéterminées de variables permettant de distinguer les Limbo Veggies des 
 * autres sortes de JVeggies.</p>
 *
 * @author Xavier Reid et Philippe Marcotte
 */
public class LimboVeggie extends JVeggie{

    public LimboVeggie(float posiX, int noZombi, final PvZControleur controleur){
        super(posiX, "LV", noZombi, controleur);
    }
}
