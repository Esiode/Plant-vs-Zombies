/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pvz.vue.terrainjeu;

import pvz.vue.veggie.JVeggie;
import pvz.vue.veggie.CoachVeggie;
import pvz.vue.veggie.VeggieOrdinaire;
import pvz.vue.veggie.GrosVeggie;
import pvz.vue.veggie.LimboVeggie;
import pvz.vue.projectiles.JProjectile;
import pvz.vue.projectiles.Pois;
import pvz.vue.plante.JPlante;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import pvz.controleur.PvZControleur;
import pvz.vue.plante.PlantePiege;

/**
 * Classe qui représente les 5 lignes du terrain.
 *
 * <p>C'est cette classe qui va gérer quand les projectiles qui sont situés dans
 * la ligne doivent se redessiner ainsi que les plantes et les zombies. Elle
 * crée les zombies au hasard quand le panneau terrain jeu lui dit d'en créer.
 * Elle gère les collisions entre un zombie et un projectile et entre un zombie
 * et une plante.</p>
 *
 * @author Xavier Reid et Philippe Marcotte
 */
public class Lane extends JComponent {

    /**
     * Integer représentant la dernière frame où la Lane a été actualisée.
     * Empêche de faire plus d'une actualisation par frame.
     */
    private long dateDerniereUpdate = 0;
    /**
     * Classse random qui sert à la création au hasard des zombies.
     */
    private Random rnd = new Random();
    /**
     * Déclaration du contrôleur pour établir la communication entre les deux.
     */
    private PvZControleur controleur;
    /**
     * Tableau de zone plantable représentant toutes les zones où l'on peut
     * placer une plante sur une ligne.
     */
    private JZonePlantable[] cases = new JZonePlantable[9];
    /**
     * ArrayList contenant tous les projectiles qui sont dans la ligne.
     */
    private ArrayList<JProjectile> listeProjectile = new ArrayList<>();
    /**
     * ArrayList contenant tous les veggie (homologue visuel des zombies) qui
     * sont dans la ligne.
     */
    private ArrayList<JVeggie> listeZombies = new ArrayList<>();
    /**
     * Integer représentant le numéro de la ligne concernée.
     */
    private int noLane;
    /**
     * Déclaration du panneau principal pour établir la communication entre les
     * deux.
     */
    private PnlTerrainJeu pnlTerrainJeu;

    /**
     * Méthode qui permet d'ajouter un projectile à la ligne lors de leur
     * création.
     *
     * @param projectile Objet visuel représentant ce qui fait des dommages aux
     * zombies.
     */
    public void addProjectile(JProjectile projectile) {
        this.listeProjectile.add(projectile);
        this.add(projectile);
    }

    /**
     * Fonction qui renvoie le numéro de la ligne concernée.
     *
     * @return Integer représentant le numéro de la ligne concernée.
     */
    public int getNoLane() {
        return noLane;
    }

    /**
     * Constructeur d'une ligne.
     *
     * @param controleur Permet d'initialiser le contrôleur et, ainsi, établir
     * la communication entre les deux.
     * @param pnlTerrainJeu Permet d'initialiser la fenêtre principale et,
     * ainsi, établir la communication entre les deux.
     * @param noLane Integer représentant le numéro de la ligne concernée.
     */
    protected Lane(final PvZControleur controleur, final PnlTerrainJeu pnlTerrainJeu, int noLane) {
        this.pnlTerrainJeu = pnlTerrainJeu;
        this.noLane = noLane;
        this.controleur = controleur;
        this.setSize(85 * 9, 130);
        for (int i = 0; i < 9; i++) {
            cases[i] = new JZonePlantable(controleur, pnlTerrainJeu, i, this);
            this.add(cases[i]);
            cases[i].setLocation(3 + i * 85, 3);
        }
    }

    /*
     * Méthode qui sert à acutaliser les dessins contenu par chaque zone plantable.
     */
    private void actualiserJZPs(boolean resteZombies) {
        for (int i = 0; i < cases.length; i++) {
            cases[i].actualiser(!listeZombies.isEmpty(), resteZombies);
        }
    }

    /*
     * Méthode qui sert à actualiser le déplacement de chaque projectile contenu dans la ligne.
     */
    private void deplacerProjectiles() {
        for (int i = 0; !(listeProjectile.isEmpty()) && i < listeProjectile.size(); i++) {
            JProjectile projectile = listeProjectile.get(i);
            if (projectile instanceof Pois) {
                ((Pois) projectile).actualiser();
            }
            if (projectile.getX() > 1016) {
                this.remove(projectile);
                listeProjectile.remove(i);
                i--;
            }
        }
    }

    /*
     * Méthode qui sert à tester s'il y a des zombies spéciaux dans la ligne et actualiser l'état des zombies affectés en conséquence.
     */
    private void actualiserBonusZombies() {
        boolean zombiesEncourages = false;
        boolean zombiesPenches = false;
        for (int i = 0; !(listeZombies.isEmpty()) && (!(zombiesEncourages && zombiesPenches)) && i < listeZombies.size(); i++) {
            if ((controleur.getZombie(listeZombies.get(i).getID()).getTypeZombie()).equalsIgnoreCase("CV")) {
                zombiesEncourages = true;
            } else if ((controleur.getZombie(listeZombies.get(i).getID()).getTypeZombie()).equalsIgnoreCase("LV")) {
                zombiesPenches = true;
            }
        }

        for (int i = 0; i < listeZombies.size(); i++) {
            JVeggie veggie = listeZombies.get(i);
            if (zombiesEncourages && controleur.getZombie(veggie.getID()).isSonicModePossible()) {
                veggie.setVitesse(-0.56666F);
            } else {
                veggie.setVitesse(-0.28333F);
            }
            if (!controleur.getZombie(veggie.getID()).isMange() && !controleur.getZombie(veggie.getID()).isPiege()) {
                if (zombiesPenches && controleur.getZombie(veggie.getID()).isLimboModePossible()) {
                    controleur.setEtat(controleur.getZombie(veggie.getID()), "limboMode");
                } else {
                    controleur.setEtat(controleur.getZombie(veggie.getID()), "debout");
                }
            }
        }
    }

    /*
     * Méthode qui sert à retirer un zombie du jeu lorsqu'il meurt sous les projectiles.
     */
    private boolean jeterZombie(JVeggie veggie) {
        boolean isJeter = false;
        if (controleur.getZombie(veggie.getID()).getpV() <= 0) {
            if (controleur.getZombie(veggie.getID()).isPiege()) {
                controleur.setEtat(controleur.getZombie(veggie.getID()).getPlantePiege(), "desactive");
            }
            controleur.getListeZombies().remove(controleur.getZombie(veggie.getID()));
            this.remove(veggie);
            listeZombies.remove(veggie);
            isJeter = true;
            pnlTerrainJeu.unMeurtreDePlus();
        } else if (veggie.getX() == 0) {
            String messageGameOver = "VOUS AVEZ PERDU!!!!!!";
            JOptionPane.showMessageDialog(null, messageGameOver);
            System.exit(0);
        }
        return isJeter;
    }

    /*
     * Méthode qui sert à retirer une plante du jeu lorsqu'elle meurt dévorée par un zombie.
     */
    private void jeterPlante(JPlante plante, int index) {
        if (controleur.getPlante(plante.getCoordYX()).getpV() <= 0) {
            controleur.getListePlantes().remove(controleur.getPlante(plante.getCoordYX()));
            cases[index].setVide(true);
            cases[index].remove(plante);
        }
    }

    /*
     * Méthode qui sert à actualiser le déplacement de tous les zombies contenu par la ligne.
     * Elle esrt aussi appeler la méthode qui va tester toutes les collisions dans la ligne et à vérifier si un zombie est piégé ou non.
     */
    private synchronized void actualiserZombies() {
        actualiserBonusZombies();
        for (int i = 0; !(listeZombies.isEmpty()) && i < listeZombies.size(); i++) {
            JVeggie veggie = listeZombies.get(i);
            if (!jeterZombie(veggie)) {
                if (controleur.getZombie(veggie.getID()).isPiege()) {
                    veggie.setVitesse(0);
                }
                testCollision(veggie);
                veggie.actualiser();
            }
        }
    }

    /*
     * Méthode général pour tester les collisions.
     */
    private void testCollision(JVeggie veggie) {
        testCollisionProjectile(veggie);
        testCollisionPlante(veggie);
    }

    /*
     * Méthode qui sert à tester les collisions entre les projectiles de type pois et les zombies. 
     * On vérifie si un zombie peut esquiver le pois, s'il doit subir des dommages, etc.
     */
    private void testCollisionProjectile(JVeggie veggie) {
        Rectangle zoneVeggie = new Rectangle(veggie.getX(), veggie.getY(), 40, veggie.getHeight());
        for (int i = 0; !listeProjectile.isEmpty() && i < listeProjectile.size(); i++) {
            JProjectile projectile = listeProjectile.get(i);
            Rectangle zoneProjectile = new Rectangle(projectile.getBounds());
            if (zoneVeggie.intersects(zoneProjectile) && !controleur.getZombie(veggie.getID()).isLimboMode()) {
                if (projectile instanceof Pois) {
                    if (!controleur.getZombie(veggie.getID()).isLimboMode()) {
                        controleur.playSound("poc.wav");
                        controleur.endommageZombie(veggie.getID(), projectile.getPlanteAssocie());
                    }
                }
                this.remove(projectile);
                listeProjectile.remove(i);
            }
        }
    }

    /*
     * Méthode qui teste les collisions entre les zombies et les plantes. Si un zombie entre en collision avec une plante, sauf les plante-pièges,
     * il doit commencer à la manger. 
     * Si il entre en collision avec une plante-piège il doit changer son état pour que la méthode actualiser zombie l'immobilise.
     */
    private void testCollisionPlante(JVeggie veggie) {
        Rectangle zoneVeggie = new Rectangle(veggie.getX(), veggie.getY(), 40, veggie.getHeight());
        for (int i = 0; i < cases.length; i++) {
            Rectangle zoneCase = new Rectangle(cases[i].getBounds());
            if (zoneVeggie.intersects(zoneCase) && cases[i].getX() <= veggie.getX()) {
                if (cases[i].getCaseDangereuse()) {
                    controleur.endommageZombie(veggie.getID(), "LanceMelon");
                }
                Rectangle zoneVulnerablePlante = new Rectangle(cases[i].getX() + 30, cases[i].getY() + 10, 20, 5);
                if (!cases[i].getVide() && zoneVeggie.intersects(zoneVulnerablePlante)) {
                    if (cases[i].getPlanteDansCase() instanceof PlantePiege) {
                        PlantePiege plantePiegeTouchee = (PlantePiege) cases[i].getPlanteDansCase();
                        if (controleur.getPlante(plantePiegeTouchee.getCoordYX()).isDesactive()) {
                            controleur.playSound("paf.wav");
                            controleur.getZombie(veggie.getID()).setPlantePiege(controleur.getPlante(plantePiegeTouchee.getCoordYX()));
                            controleur.setEtat(controleur.getPlante(plantePiegeTouchee.getCoordYX()), "active");
                            controleur.setEtat(controleur.getZombie(veggie.getID()), "piege");

                        } else if (!controleur.getZombie(veggie.getID()).isPiege()) {
                            controleur.setEtat(controleur.getZombie(veggie.getID()), "mange");
                            veggie.setVitesse(0);
                            controleur.endommagePlante(plantePiegeTouchee.getCoordYX(), veggie.getTypeZombi());
                            if (controleur.getPlante(plantePiegeTouchee.getCoordYX()).getpV() % 60 == 0) {
                                controleur.playSound("croc.wav");
                            }
                            jeterPlante(plantePiegeTouchee, i);
                        }
                    } else {
                        controleur.setEtat(controleur.getZombie(veggie.getID()), "mange");
                        veggie.setVitesse(0);
                        JPlante planteAttaquee = cases[i].getPlanteDansCase();
                        controleur.endommagePlante(planteAttaquee.getCoordYX(), veggie.getTypeZombi());
                        if (controleur.getPlante(planteAttaquee.getCoordYX()).getpV() % 60 == 0) {
                            controleur.playSound("croc.wav");
                        }
                        jeterPlante(planteAttaquee, i);
                    }
                }
                if (cases[i].getVide() && (controleur.getZombie(veggie.getID()).isPiege()
                        || controleur.getZombie(veggie.getID()).isMange())) {
                    controleur.setEtat(controleur.getZombie(veggie.getID()), "debout");
                    veggie.setVitesse(-0.28333F);
                }
            }
        }
    }

    /**
     * Méthode qui sert à appeler toutes les autres méthodes d'actualisation
     * dans la classe.
     *
     * @param resteZombies Boolean qui est true s'il reste des zombies sur le
     * terrain et false s'il n'en reste pas.
     */
    protected void actualiser(boolean resteZombies) {
        if (controleur.getTemps() == dateDerniereUpdate + 1) {
            actualiserJZPs(resteZombies);
            if (!listeProjectile.isEmpty()) {
                deplacerProjectiles();
            }
            if (!listeZombies.isEmpty()) {
                actualiserZombies();
            }
            dateDerniereUpdate = controleur.getTemps();
        } else {
            System.out.println("PROBLEM");
            System.out.println("Erreur de Thread!");
        }
    }

    /**
     * Fonction qui renvoie s'il y a bel et bien des zombies ou non dans la
     * ligne.
     *
     * @return Boolean qui représente si la liste contenant les zombies est vide
     * ou non.
     */
    public boolean ilYADesZombiesDansCetteLane() {
        return !listeZombies.isEmpty();
    }

    /**
     * Méthode qui sert à générer au hasard un des quatres types de zombies
     * possible.
     */
    protected void genererNouveauZombie(int noVague) {
        if (noVague <= 1) {
            VeggieOrdinaire veggie = new VeggieOrdinaire(this.getWidth(), controleur.getNumeroZombie(), controleur);
            ajouterZombie(veggie);
        } 
        else if (noVague <= 3) {
            switch (rnd.nextInt(2)) {
                case 0:
                    VeggieOrdinaire veggie = new VeggieOrdinaire(this.getWidth(), controleur.getNumeroZombie(), controleur);
                    ajouterZombie(veggie);
                    break;
                case 1:
                    LimboVeggie limboVeggie = new LimboVeggie((float) this.getWidth(), controleur.getNumeroZombie(), controleur);
                    ajouterZombie(limboVeggie);
                    break;
            }
        } 
        else if (noVague <= 5) {
            switch (rnd.nextInt(3)) {
                case 0:
                    VeggieOrdinaire veggie = new VeggieOrdinaire(this.getWidth(), controleur.getNumeroZombie(), controleur);
                    ajouterZombie(veggie);
                    break;
                case 1:
                    LimboVeggie limboVeggie = new LimboVeggie((float) this.getWidth(), controleur.getNumeroZombie(), controleur);
                    ajouterZombie(limboVeggie);
                    break;
                case 2:
                    CoachVeggie coachVeggie = new CoachVeggie((float) this.getWidth(), controleur.getNumeroZombie(), controleur);
                    ajouterZombie(coachVeggie);
                    break;
            }
        } else if (noVague <= 9) {
            switch (rnd.nextInt(4)) {
                case 0:
                    VeggieOrdinaire veggie = new VeggieOrdinaire(this.getWidth(), controleur.getNumeroZombie(), controleur);
                    ajouterZombie(veggie);
                    break;
                case 1:
                    LimboVeggie limboVeggie = new LimboVeggie((float) this.getWidth(), controleur.getNumeroZombie(), controleur);
                    ajouterZombie(limboVeggie);
                    break;
                case 2:
                    CoachVeggie coachVeggie = new CoachVeggie((float) this.getWidth(), controleur.getNumeroZombie(), controleur);
                    ajouterZombie(coachVeggie);
                    break;
                case 3:
                    GrosVeggie grosVeggie = new GrosVeggie((float) this.getWidth(), controleur.getNumeroZombie(), controleur);
                    ajouterZombie(grosVeggie);
                    break;
            }
        } else {
            switch (rnd.nextInt(4)+1) {
                case 0:
                    GrosVeggie grosVeggie = new GrosVeggie((float) this.getWidth(), controleur.getNumeroZombie(), controleur);
                    ajouterZombie(grosVeggie);
                    break;
                case 1:
                    LimboVeggie limboVeggie = new LimboVeggie((float) this.getWidth(), controleur.getNumeroZombie(), controleur);
                    ajouterZombie(limboVeggie);
                    break;
                case 2:
                    CoachVeggie coachVeggie = new CoachVeggie((float) this.getWidth(), controleur.getNumeroZombie(), controleur);
                    ajouterZombie(coachVeggie);
                    break;
                case 3:
                    GrosVeggie grosVeggie2 = new GrosVeggie((float) this.getWidth(), controleur.getNumeroZombie(), controleur);
                    ajouterZombie(grosVeggie2);
                    break;
            }
        }
    }

    /*
     * Méthode qui ajoute un zombie à la ligne lors de sa création.
     */
    private void ajouterZombie(JVeggie veggie) {
        this.listeZombies.add(veggie);
        this.add(veggie);
    }

    /**
     * Fonction qui renvoie la zone plante selon l'index entré en paramètre.
     *
     * @param index Integer qui représente le nuiméro de la case demandée.
     * @return La zone plantable correspondant à l'index.
     */
    public JZonePlantable getCase(int index) {
        return this.cases[index];
    }
}
