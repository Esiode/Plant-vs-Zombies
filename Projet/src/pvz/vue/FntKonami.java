package pvz.vue;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import pvz.controleur.PvZControleur;

/**
 * Classe qui contient la fenêtre qui s'ouvre lorsque le Konami Code est activé
 *
 * <p>Cette classe va faire apparaître une fenêtre contenant une image disant
 * que le joueur a gagné plein d'argents. Si celui-ci appuie sur le bouton
 * "Claim the prize" il recevra 999 999$ d'argent du jeu.</p>
 *
 * @author Xavier Reid et Philippe Marcotte
 */
public class FntKonami extends JFrame {

    /*
     * Déclaration d'une variable de type PvZControleur qui permet au thread de communiquer avec le contrôleur.
     * Dans ce cas-ci le contrôleur permet à la fenêtre d'aller dire au modèle d'augmenter l'argent du joueur.
     */
    private PvZControleur pvZcontroleur;

    /**
     * Consutructeur de la fenêtre expliquant que le joueur a gagné un prix. On
     * ne peut la faire afficher qu'en faisant le Konami code lorsque le jeu est
     * ouvert.
     *
     * @param controleur Permet d'initialiser le contrôleur et, ainsi, établir
     * la communication entre les deux.
     */
    public FntKonami(final PvZControleur controleur) {
        this.setTitle("Congratulations!");
        this.pvZcontroleur = controleur;
        JPanel pnl = new JPanel();
        pnl.setSize(431, 305);
        pnl.setPreferredSize(new Dimension(431, 305));
        pnl.setLayout(null);
        ImageIcon img = new ImageIcon("Cheat.gif");
        JLabel spam = new JLabel(img);
        spam.setSize(431, 305);
        spam.setLocation(0, 0);

        JButton boutonClaim = new JButton("Click here to claim prize!!!");
        boutonClaim.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = (JFrame) SwingUtilities.getRoot((JButton) e.getSource());
                frame.setVisible(false);
                pvZcontroleur.modCash(999999);
            }
        });
        boutonClaim.setSize(new Dimension(300, 25));
        pnl.add(boutonClaim);
        pnl.add(spam);



        boutonClaim.setLocation(pnl.getWidth() / 2 - boutonClaim.getWidth() / 2, 275);
        this.add(pnl);
        this.setVisible(true);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.pack();
        this.setResizable(false);
        controleur.playSound("error.wav");
    }
}
