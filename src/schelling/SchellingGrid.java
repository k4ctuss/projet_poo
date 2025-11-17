package schelling;

import core.Cell;
import core.Grid;
import java.util.*;

/**
 * Classe représentant la grille du modèle de Schelling
 * Elle hérite de la classe Grid et implémente les règles spécifiques
 * du modèle de Schelling pour le déplacement des cellules en fonction de leur voisinage
 * Les règles de transition sont les suivantes:
 * - Si une cellule a plus de 'seuil' voisins d'un état différent, elle déménage dans une habitation vacante aléatoire
 */
public class SchellingGrid extends Grid {

    private final Set<Cell> originVacantHabitations;
    private final Set<Cell> vacantHabitations;
    private final int seuil;

		/**
		 * Constructeur de la grille de Schelling
		 * @param nbCellWidth nombre de cellules en largeur de la grille
		 * @param nbCellHeight nombre de cellules en hauteur de la grille
		 * @param initialCell ensemble des cellules initialement occupées
		 * @param numberStates nombre d'états possibles pour les cellules (couleurs des familles)
		 * @param originVacantHabitations ensemble des cellules initialement vacantes
		 * @param seuil seuil de dissatisfaction pour le déplacement
		 */
    public SchellingGrid(int nbCellWidth, int nbCellHeight, Set<Cell> initialCell, int numberStates, Set<Cell> originVacantHabitations, int seuil) {
        super(nbCellWidth, nbCellHeight, initialCell, numberStates);
        this.seuil = seuil;
        this.originVacantHabitations = new HashSet<>(originVacantHabitations);
        this.vacantHabitations = new HashSet<>(originVacantHabitations);
    }

		/**
		 * Change l'habitation d'une cellule en la déplaçant vers une habitation vacante aléatoire
		 * met à jour l'état de la nouvelle cellule en conséquence
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
        newHabitation.setState(currState);
        nextAlive.add(newHabitation);
        vacantHabitations.remove(newHabitation);
        vacantHabitations.add(c);
        c.setState(0); // morte
    }

		/**
		 * Passe à l'état suivant dans le modèle de Schelling
		 * Pour chaque cellule occupée, on compte le nombre de voisins d'un état différent
		 * Si ce nombre est supérieur au seuil, la cellule déménage dans une habitation vacante aléatoire
		 * Sinon, elle conserve son état actuel
		 */
    @Override
    public void nextStep(){
        updateSnapshot();  // Remplit snapshotState avec l'état courant
        
        for(Cell c : currAlive){
            int nbNeighborDiff = 0;
            int currState = getStateSnapshot(c);
            for (Cell neighbor: getNeighbor(c)){
                if(currState != getStateSnapshot(neighbor)){  // si la voisine n'a pas de famille son état est 0
                    nbNeighborDiff++;
                }
            }

            if(nbNeighborDiff > seuil){
                changeHabitation(c, currState);
            } else {
                nextAlive.add(c);
            }
        }

        // Basculement des états
        this.currAlive.clear();
        this.currAlive.addAll(nextAlive);
        this.nextAlive.clear();
    }

    @Override
    public void restart(){
        super.restart();
        this.vacantHabitations.clear();
        this.vacantHabitations.addAll(originVacantHabitations);
    }
}
