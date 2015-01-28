package pvz.vue.projectiles;

import java.awt.Graphics;
import javax.swing.JComponent;

/**
 * Classe qui représente tous les objets visuels de type projectiles.
 *
 * <p>Elle contient les méthodes pour le bon fionctionement visuel des
 * projectiles. Elle s'occupe de régir ce qu'est un projectile visuellement.</p>
 *
 * @author Xavier Reid et Philippe Marcotte
 */
public abstract class JProjectile extends JComponent {

    /*
     * String qui représente la plante associée au projectile.
     */
    private String planteAssocie;

    /**
     * Fonction qui renvoie la plante associée au projectile concerné.
     *
     * @return String qui représente la plante associée au projectile.
     */
    public String getPlanteAssocie() {
        return planteAssocie;
    }

    /**
     * Méthode abstraite qui sera redéfinie dans les classes enfants. Celle-ci
     * sert à déterminer ce qu'il faut faire lorsqu'il vient le temps de
     * repeindre les images.
     */
    public abstract void actualiser();

    @Override
    public abstract void paintComponent(Graphics g);

    /**
     * Méthode abstraite qui sera redéfinie dans les classes enfants. Elle sert
     * à déplacer dans la fenêtre les projectiles d'un certain nombre de pixel à
     * chauqe fois qu'elle est appelée.
     */
    public abstract void deplacement();

    /**
     * Constructeur d'un JProjectile. Il instancie la plante associée.
     *
     * @param planteAssocie String qui représente la plante associée au projectile.
     */
    public JProjectile(String planteAssocie) {
        this.planteAssocie = planteAssocie;
    }
}
