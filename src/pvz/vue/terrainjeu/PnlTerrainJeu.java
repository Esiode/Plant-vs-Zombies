package pvz.vue.terrainjeu;

import java.awt.Color;
import pvz.vue.magasin.CashMoney;
import pvz.vue.magasin.JPlanteShopIcon;
import pvz.vue.plante.JPlante;
import pvz.vue.plante.Tournesol;
import pvz.vue.plante.TirePois;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import pvz.controleur.PvZControleur;
import pvz.vue.plante.LanceCochon;
import pvz.vue.plante.PlantePiege;
import pvz.vue.projectiles.BouletCochon;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

/**
 * Classe qui contient tous les aspects visuels du jeu.
 *
 * <p>Elle contient le fond jusqu'aux lignes qui affichent les zombies et les
 * projectiles et tout ce quil y a entre. C'est là qu'on calcule le rythme
 * auquel on crée les zombies. On gère les évènements du magasin.</p>
 *
 * @author Xavier Reid et Philippe Marcotte
 */
public class PnlTerrainJeu extends JPanel {

    /**
     * Integer qui représente le nombre de zombie a créé pour cette vague.
     */
    private int zombiDuRound;
    /**
     * Integer qui représente le nombre de zombie créé au moment de l'appel.
     */
    private int zombisApparusCeRound = 0;
    /**
     * Long qui représente le temps où la nouvelle vague doit commencer.
     */
    private long tempsOuProchainZombieAttaque;
    /**
     * JLabel qui contient l'image de fond pour le magasin.
     */
    private JLabel imgBackgroundShop = new JLabel(new ImageIcon("ShopBackground.gif"));
    /**
     * JLabel qui contient l'image de fond du terrain de jeu.
     */
    private JLabel imgBackground = new JLabel(new ImageIcon("TerrainJeu(1016x784).gif"));
    /**
     * Objet visuel pour représenter la main tenant un sac d'argent.
     */
    private CashMoney cashMoney;
    /**
     * Déclaration du contrôleur pour établir la communication entre les deux.
     */
    private PvZControleur controleur;
    /**
     * Classe random qui sert à choisir au hasard la ligne sur laquelle créé un
     * zombie.
     */
    private Random rnd = new Random();
    /**
     * JLabel qui représente visuelle le montant d'argentq ue le joueur a en sa
     * possession.
     */
    private JLabel lblCash = new JLabel();
    /**
     * Tableau d'objet icône de magasin pour représente les 4 plantes
     * différentes.
     */
    private JPlanteShopIcon[] plantesAchetables = new JPlanteShopIcon[4];
    /**
     * Tableau de ligne pour représenter les 5 lignes du jeu.
     */
    private Lane[] ensembleDeLanes = new Lane[5];
    /**
     * Integer qui représente le nombre de zombie tué par le joueur.
     */
    private int grosseurHecatombe = 0;
    /**
     * Panneau qui représente la trajectoire des boulet-cochons.
     */
    private TrajectoireBouletCochon trajectoireBouletCochon;
    /**
     * JLabel qui affiche la grosseur de l'hécatombe pour voir le nombre de
     * zombie tué.
     */
    private JLabel lblGrosseurHecatombe = new JLabel();
    /**
     * Integer qui représente le numéro de la vague actuelle.
     */
    private int noVague = 1;
    /**
     * Jlabel qui affiche le noVague pour voir la vague à laquelle on est rendu.
     */
    private JLabel lblVagueActuelle = new JLabel();
    /**
     * Objet visuel qui représente toutes les zombies où il pourrait y avoir une
     * potentielle explosion.
     */
    private JZoneExplosions jZoneExplosions = new JZoneExplosions();

    /**
     * Méthode qui fait augmenter le nombre de zombie tué.
     */
    public void unMeurtreDePlus() {
        grosseurHecatombe++;
    }

    /**
     * Constructeur du panneau de terrain de jeu.
     *
     * @param controleur Permet d'initialiser le contrôleur et, ainsi, établir
     * la communication entre les deux.
     */
    public PnlTerrainJeu(final PvZControleur controleur) {
        this.controleur = controleur;
        controleur.playSound("Catgroove.wav");
        this.tempsOuProchainZombieAttaque = controleur.getFPS() * 30;
        this.zombiDuRound = controleur.getNbZombies(noVague);
        this.setLayout(null);
        this.setSize(1016, 784);
        this.setPreferredSize(new Dimension(1016, 784));
        this.cashMoney = new CashMoney(controleur, this);
        this.add(cashMoney);
        lblCash.setText(controleur.getCash() + "$");
        lblCash.setFont(new Font("Ravie", 100, 15));
        lblCash.setSize(200, 200);
        this.add(lblCash);
        lblCash.setLocation(20, 10);

        this.trajectoireBouletCochon = new TrajectoireBouletCochon(this.getSize());
        this.add(trajectoireBouletCochon);
        this.trajectoireBouletCochon.setLocation(0, 0);
        this.add(lblGrosseurHecatombe);
        lblGrosseurHecatombe.setForeground(new Color(49, 168, 87));
        lblGrosseurHecatombe.setFont(new Font("Ravie", 100, 12));
        lblGrosseurHecatombe.setSize(290, 70);
        lblGrosseurHecatombe.setLocation(600, 10);
        this.add(lblVagueActuelle);
        lblVagueActuelle.setForeground(new Color(49, 168, 87));
        lblVagueActuelle.setFont(new Font("Ravie", 100, 12));
        lblVagueActuelle.setSize(230, 70);
        lblVagueActuelle.setLocation(660, 60);
        for (int i = 0; i < 5; i++) {
            ensembleDeLanes[i] = new Lane(controleur, this, i);
            this.add(ensembleDeLanes[i]);
            ensembleDeLanes[i].setLocation(230, 99 + i * 130);
        }

        this.add(jZoneExplosions);
        jZoneExplosions.setLocation(230, 99);

        plantesAchetables[0] = new JPlanteShopIcon("Tournesol", 50, this, "<html> Cout: 50 <br> Donne 25 $CAN aux 30 secondes. Plutot rentable...");
        plantesAchetables[1] = new JPlanteShopIcon("PlantePiege", 75, this, "");
        plantesAchetables[2] = new JPlanteShopIcon("Peashooter", 100, this, "");
        plantesAchetables[3] = new JPlanteShopIcon("LanceCochon", 150, this, "");
        for (int i = 0; i < plantesAchetables.length; i++) {
            plantesAchetables[i].addMouseListener(new PnlTerrainJeu.GestionnaireEvenements());
            this.add(plantesAchetables[i]);
            plantesAchetables[i].setLocation(150 + i * 120, 10);
        }

        this.add(imgBackgroundShop);
        imgBackgroundShop.setSize(900, 130);
        imgBackgroundShop.setLocation(0, 0);
        this.add(imgBackground);
        imgBackground.setLocation(0, 0);
        imgBackground.setSize(1000, 784);
    }

    /**
     * Méthode qui actualise toutes les composantes visuelles du panneau et
     * appel les méthodes qui vont mettre à jour toutes les images
     * individuellements
     */
    public void miseAJourPnlTerrainJeu() {
        lblCash.setText(controleur.getCash() + "$");
        lblGrosseurHecatombe.setText("Nombre de zombies tués : " + grosseurHecatombe);

        cashMoney.actualiser();
        trajectoireBouletCochon.actualiser();
        Boolean resteDesZombies = false;
        for (int i = 0; !resteDesZombies && i < ensembleDeLanes.length; i++) {
            if (ensembleDeLanes[i].ilYADesZombiesDansCetteLane()) {
                resteDesZombies = true;
            }
        }
        for (int i = 0; i < ensembleDeLanes.length; i++) {
            ensembleDeLanes[i].actualiser(resteDesZombies);
        }
        actualiserJpsiSelectionne();
        verifierSiZombiesAttaquent();

        if (controleur.getTemps() % 12 == 0) {
            for (int i = 0; i < plantesAchetables.length; i++) {
                plantesAchetables[i].actualiser();
            }
        }
        if (controleur.getTemps()%((5*60+10)*60)==0) {
            controleur.playSound("Catgroove.wav");
        }
        this.invalidate();
        this.repaint();
    }

    /**
     * Méthode qui teste quelle plante doit être créée dans la zone plantable
     * sélectionnée.
     *
     * @param jzp Objet qui représente une case où l'on peut planter une plante.
     */
    public void clicZonePlantable(JZonePlantable jzp) {
        if (jzp.getVide()) {
            for (int i = 0; i < plantesAchetables.length; i++) {
                if (plantesAchetables[i].getEstSelectionne()) {
                    controleur.modCash(-(plantesAchetables[i].getPrix()));

                    JPlante nouvPlant = null;
                    switch (plantesAchetables[i].getNom()) {
                        case "Tournesol":
                            nouvPlant = (JPlante) new Tournesol(controleur, jzp);
                            jzp.planterPlante(nouvPlant);
                            break;
                        case "Peashooter":
                            nouvPlant = (JPlante) new TirePois(controleur, jzp);
                            jzp.planterPlante(nouvPlant);
                            break;
                        case "LanceCochon":
                            nouvPlant = (JPlante) new LanceCochon(controleur, jzp, this);
                            jzp.planterPlante(nouvPlant);
                            break;
                        case "PlantePiege":
                            nouvPlant = (JPlante) new PlantePiege(controleur, jzp);
                            jzp.planterPlante(nouvPlant);
                            break;
                    }
                }
            }
        }
    }

    /**
     * Méthode qui actualise toutes les icônes du magasin. Elle teste si le
     * joueur a bel et bien le montant d'argent nécessaire pour acheter la
     * plante sélectionnée.
     */
    public void actualiserJpsiSelectionne() {
        for (int i = 0; i < plantesAchetables.length; i++) {
            if (plantesAchetables[i].getEstSelectionne() && plantesAchetables[i].getPrix() > controleur.getCash()) {
                unselectAllPlantShopIcons();
            }
        }
    }

    /**
     * Méthode qui déselectionne toutes les plantes pour qu'il n'y est pas de
     * conflit de plusieurs plantes sélectionnées en même temps.
     */
    public void unselectAllPlantShopIcons() {
        for (int i = 0; i < plantesAchetables.length; i++) {
            plantesAchetables[i].setEstSelectionne(false);
        }
    }

    /**
     * Méthode qui change l'état de l'icône du magasin pour qu'elle soit
     * considérée sélectionnée.
     *
     * @param jpsiSource Objet visuel qui représente l'icône du magasin
     * sélectionné.
     */
    public void clicJPlantShopIcon(JPlanteShopIcon jpsiSource) {
        unselectAllPlantShopIcons();
        if (jpsiSource.getPrix() <= controleur.getCash()) {
            jpsiSource.setEstSelectionne(true);
        }
    }

    /**
     * Méthode qui teste si le temps est venu de commcner une nouvelle vague.
     * Elle décide au hasard quelle ligne va devoir créer des zombies et à
     * quelle rythme.
     */
    public void verifierSiZombiesAttaquent() {
        if (controleur.getTemps() >= tempsOuProchainZombieAttaque) {
            if (zombisApparusCeRound < zombiDuRound / 2) {
                int noLane = rnd.nextInt(5);
                ensembleDeLanes[noLane].genererNouveauZombie(noVague);
                zombisApparusCeRound++;
                if (zombisApparusCeRound == 1) {
                    lblVagueActuelle.setText("Vague : " + noVague);
                }
                tempsOuProchainZombieAttaque += controleur.getFPS() * 5;
            } else {
                if (zombisApparusCeRound < zombiDuRound) {
                    int noLane = rnd.nextInt(5);
                    ensembleDeLanes[noLane].genererNouveauZombie(noVague);
                    zombisApparusCeRound++;
                    tempsOuProchainZombieAttaque += controleur.getFPS() * 0.5;
                }
                if (zombisApparusCeRound == zombiDuRound) {
                    zombisApparusCeRound = 0;
                    noVague++;
                    zombiDuRound = controleur.getNbZombies(noVague);
                    tempsOuProchainZombieAttaque = controleur.getTemps() + controleur.getFPS() * 30;
                }
            }
        }
    }

    /**
     * Méthode qui sert à calculer la trajectoire des boulet-cochons.
     *
     * @param jzpOrigine Zone plantable qui représente le point de départ du
     * boulet-cochon.
     */
    public void lancerBouletCochon(JZonePlantable jzpOrigine) {
        int noLane = rnd.nextInt(5);
        int noCase = rnd.nextInt(9);
        BouletCochon bouletCochon = new BouletCochon(jzpOrigine, ensembleDeLanes[noLane].getCase(noCase), trajectoireBouletCochon);
        trajectoireBouletCochon.addCochon(bouletCochon);
    }

    /**
     * Fonction qui renvoie la zone de toutes les explosions pour accèder à ses
     * méthodes.
     *
     * @return JComponent de la zone de toutes les explosions possible.
     */
    public JZoneExplosions getZoneExplosions() {
        return this.jZoneExplosions;
    }


    /**
     * Classe qui sert à tester si un icône d'un magasin a été cliqué. Elle
     * appelle la méthode qui va changé l'état de l'icône à sélectionné.
     */
    class GestionnaireEvenements extends MouseAdapter {

        @Override
        public void mouseClicked(MouseEvent e) {
            JPlanteShopIcon jpsiSource = (JPlanteShopIcon) e.getSource();
            clicJPlantShopIcon(jpsiSource);
        }
    }
}