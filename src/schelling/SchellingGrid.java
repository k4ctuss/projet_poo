package schelling;

import core.Cell;
import core.MultiStateGrid;
import utils.ColorUtil;

import java.awt.*;
import java.util.*;

/**
 * Classe représentant la grille du modèle de Schelling
 * Elle hérite de la classe MultiStateGrid et implémente les règles spécifiques
 * du modèle de Schelling pour le déplacement des cellules en fonction de leur voisinage
 * Les règles de transition sont les suivantes:
 * - Si une cellule a plus de 'seuil' voisins d'un état différent, elle déménage dans une habitation vacante aléatoire
 */
public class SchellingGrid extends MultiStateGrid {

    private final Set<Cell> originVacantHabitations;
    private final Set<Cell> vacantHabitations;
    private final int seuil;

		/**
		 * Constructeur de la grille de Schelling
		 * @param nbCellWidth nombre de cellules en largeur de la grille
		 * @param nbCellHeight nombre de cellules en hauteur de la grille
		 * @param initialCell ensemble des cellules initialement occupées
		 * @param numberStates nombre d'états possibles pour les cellules (couleurs des familles)
		 * @param initialStateForCell map des cellules initiales avec leur état associé
		 * @param originVacantHabitations ensemble des cellules initialement vacantes
		 */
    public SchellingGrid(int nbCellWidth, int nbCellHeight, Set<Cell> initialCell, int numberStates, HashMap<Cell, Integer> initialStateForCell, Set<Cell> originVacantHabitations, int seuil) {
        super(nbCellWidth, nbCellHeight, initialCell, numberStates, initialStateForCell);
        this.seuil = seuil;
        this.originVacantHabitations = new HashSet<>(originVacantHabitations);
        this.vacantHabitations = new HashSet<>();
    }

		/**
		 * Crée une palette de couleurs pastel pour représenter les différents états des cellules
		 * @param numberStates nombre d'états possibles pour les cellules
		 * @return un tableau de couleurs représentant la palette
		 * @see utils.ColorUtil#pastelHSB(int)
		 */
    @Override
    protected Color[] createPalette(int numberStates) {
        return ColorUtil.pastelHSB(numberStates);
    }

		/**
		 * Change l'habitation d'une cellule en la déplaçant vers une habitation vacante aléatoire
		 * met à jour nextStateCell et nextAlive en conséquence
		 * @param c la cellule à déplacer
		 * @param currState l'état actuel de la cellule
		 * @throws IllegalStateException si aucune habitation n'est disponible
		 */
    private void changeHabitation(Cell c, int currState){
        if(vacantHabitations.isEmpty()){
            throw new IllegalStateException("Erreur : aucune habitation n'est disponible.");
        }
        int item = new Random().nextInt(vacantHabitations.size());
        int i = 0;
        Cell newHabitation = null;
        for(Cell vac : vacantHabitations){
            if(i == item){
                newHabitation = vac;
                break;
            }
            i++;
        }
        assert(newHabitation != null);
        nextStateCell.put(newHabitation,currState);
        nextAlive.add(newHabitation);
        newHabitation.setColor(palette[currState]);
        vacantHabitations.remove(newHabitation);
        vacantHabitations.add(c);
    }

		/**
		 * Passe à l'état suivant dans le modèle de Schelling
		 * Pour chaque cellule occupée, on compte le nombre de voisins d'un état différent
		 * Si ce nombre est supérieur au seuil, la cellule déménage dans une habitation vacante aléatoire
		 * Sinon, elle conserve son état actuel
		 */
    @Override
    public void nextStep(){
        for(Cell c : currAlive){
            int nbNeighborDiff = 0;
            int currState = currStateCell.get(c);
            for (Cell neighbor: getNeighbor(c)){
                if(currState != currStateCell.getOrDefault(neighbor, 0)){ // si la voisine n'a pas de famille son état est 0
                    nbNeighborDiff++;
                }
            }

            if(nbNeighborDiff>seuil){
                changeHabitation(c, currState);
            }else{
                nextStateCell.put(c,currState);
                nextAlive.add(c);
            }
        }

        applyNextState();
    }

    @Override
    public void restart(){
        super.restart();
        this.vacantHabitations.clear();
        this.vacantHabitations.addAll(originVacantHabitations);

    }
}
