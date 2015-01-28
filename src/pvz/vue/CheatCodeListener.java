package pvz.vue;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import pvz.controleur.PvZControleur;

/**
 * Classe qui sert à capter lorsqu'un joueur fait une combinaison de touches de
 * clavier pour entrer un code de triche,
 *
 * <p>Grâce à son KeyListener, cette classe va enregistrer les touches que le
 * joueur entre et les comparer aux codes de triche prédéfinis. Si la
 * combinaison de touches correspond à un des codes, alors un effet quelconque
 * s'ensuit.</p>
 *
 * @author Xavier Reid et Philippe Marcotte
 */
public class CheatCodeListener implements KeyListener {

    /*
     * ArrayList contenant les 11 derniers charatères entré au clavier. 
     * Cela sert lorsqu'on veut savoir si le joueur a entré le Konami Code.
     */
    private ArrayList<Integer> last11KeyPressed;
    /*
     * ArrayList de charactères représentant le Konami Code.
     */
    private ArrayList<Integer> konamiCode;
    /*
     * ArrayList de charactères qui sert à comparé les touches entrées par le joueur avec les deux autres codes de triche : Galarneau et Monsante.
     */
    private ArrayList<Character> passeEntree = new ArrayList<>();
    /*
     * String représentant le code de triche galarneau.
     */
    private String tricheArgent = "galarneau";
    /*
     * String représentant le code de triche Montsante.
     */
    private String modeDifficile = "Monsante";
    /*
     * Déclaration d'une variable de type PvZControleur qui permet au thread de communiquer avec le contrôleur.
     */
    private PvZControleur controleur;

    /**
     * Constructeur de la classe qui test les charactères entrés. Il initialise
     * le contrôleur et établit la communication entre les deux. Il initialise
     * aussi ce qu'est le Konami Code. Finalement, il instancie la liste des 11
     * derniers charactères entrés.
     *
     * @param controleur Permet d'initialiser le contrôleur et, ainsi, établir
     * la communication entre les deux.
     */
    public CheatCodeListener(final PvZControleur controleur) {
        this.controleur = controleur;
        konamiCode = new ArrayList<Integer>();
        konamiCode.add(KeyEvent.VK_UP);
        konamiCode.add(KeyEvent.VK_UP);
        konamiCode.add(KeyEvent.VK_DOWN);
        konamiCode.add(KeyEvent.VK_DOWN);
        konamiCode.add(KeyEvent.VK_LEFT);
        konamiCode.add(KeyEvent.VK_RIGHT);
        konamiCode.add(KeyEvent.VK_LEFT);
        konamiCode.add(KeyEvent.VK_RIGHT);
        konamiCode.add(KeyEvent.VK_B);
        konamiCode.add(KeyEvent.VK_A);
        konamiCode.add(KeyEvent.VK_ENTER);
        this.last11KeyPressed = new ArrayList<Integer>();
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if (!(tricheArgent.indexOf(e.getKeyChar()) == -1) || !(modeDifficile.indexOf(e.getKeyChar()) == -1)) {
            passeEntree.add(e.getKeyChar());
        }
        if (passeEntree.size() > 9) {
            passeEntree.remove(0);
        }
        String passeString = "";
        for (int i = 0; i < passeEntree.size(); i++) {
            passeString += passeEntree.get(i);
        }
        if (passeString.equals(tricheArgent)) {
            controleur.modCash(100);
        } else if (passeString.contains(modeDifficile)) {
            controleur.activerModeDifficile();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (last11KeyPressed.size() == 11) {
            last11KeyPressed.remove(0);
        }
        last11KeyPressed.add(e.getKeyCode());
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            boolean matches = true;
            for (int i = 0; i < konamiCode.size() && matches; i++) {
                if (last11KeyPressed.get(i) != konamiCode.get(i)) {
                    matches = false;
                }
            }
            if (matches) {
                FntKonami fntKonami = new FntKonami(controleur);
            }
        }
    }
}
