package pvz.vue.veggie;

import java.awt.Color;
import java.awt.Graphics;
import pvz.controleur.PvZControleur;

/**
 *
 * @author Xavier Reid et Philippe Marcotte
 */
/**
 * Objet visuel qui représente un Pom-Pom Veggie
 *
 * <p>Cette classe contient toutes les informations nécessaires au bon
 * fonctionnement visuel des Pom-Pom Veggies. Elle contient des valeurs 
 * prédéterminées de variables permettant de distinguer les Pom-Pom Veggies des 
 * autres sortes de JVeggies.</p>
 *
 * @author Xavier Reid et Philippe Marcotte
 */
public class CoachVeggie extends JVeggie{
    /**
     * Constructeur de CoachVeggie
     * @param posiX
     * @param noZombi
     * @param controleur 
     */
    public CoachVeggie(float posiX, int noZombi, final PvZControleur controleur){
        super(posiX, "CV", noZombi, controleur);
    }
}
