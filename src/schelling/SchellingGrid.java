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
	 * Trouve une nouvelle habitation vacante aléatoire pour une cellule qui déménage
	 * @return la cellule vacante sélectionnée
	 * @throws IllegalStateException si aucune habitation n'est disponible
	 */
    private Cell selectNewHabitation(){
        if(vacantHabitations.isEmpty()){
            throw new IllegalStateException("Erreur : aucune habitation n'est disponible.");
        }
        int item = new Random().nextInt(vacantHabitations.size());
        int i = 0;
        for(Cell vac : vacantHabitations){
            if(i == item){
                return vac;
            }
            i++;
        }
        return null; // Ne devrait jamais arriver ici
    }		/**
		 * Passe à l'état suivant dans le modèle de Schelling
		 * Pour chaque cellule occupée, on compte le nombre de voisins d'un état différent
		 * Si ce nombre est supérieur au seuil, la cellule déménage dans une habitation vacante aléatoire
		 * Sinon, elle conserve son état actuel
		 */
    @Override
    public void step(){
        buildSnapshot();  // Crée le snapshot une fois au début
        
        // Listes des mouvements à effectuer après l'itération
        Set<Cell> cellsToMove = new HashSet<>();
        
        for(Iterator<Cell> it = currAlive.iterator(); it.hasNext(); ){
            Cell c = it.next();
            int nbNeighborDiff = 0;
            int currState = getStateSnapshot(c);
            for (Cell neighbor: getNeighbor(c)){
                if(currState != getStateSnapshot(neighbor)){  // si la voisine n'a pas de famille son état est 0
                    nbNeighborDiff++;
                }
            }

            if(nbNeighborDiff > seuil){
                cellsToMove.add(c);
                it.remove(); // on enlève la cellule de currAlive de façon sûre
            }
        }
        
        // Traiter les mouvements après la boucle car sinon on a un ConcurrentModificationException si on add dans currAlive
        for(Cell c : cellsToMove){
            int currState = getStateSnapshot(c);
            Cell newHabitation = selectNewHabitation();
            newHabitation.setState(currState);
            currAlive.add(newHabitation);
            vacantHabitations.remove(newHabitation);
            vacantHabitations.add(c);
            c.setState(0); // morte
        }

    }

    @Override
    public void restart(){
        super.restart();
        this.vacantHabitations.clear();
        this.vacantHabitations.addAll(originVacantHabitations);
    }
}
