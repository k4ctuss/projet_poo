package schelling;


import core.Cell;
import gui.GUISimulator;

import java.awt.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * Class de test pour le simulateur Schelling.
 * Modification facile du seuil K
 * Initialisation d'une configuration valide :
 *   * Grille 60x40, taille de cellule = 10
 *   * Couleurs (états) = 3 groupes (1..3), 0 signifie vacant et n'est pas initialisé
 *   * Places vacantes ~15 % de la grille (requis par les spécifications pour permettre les déplacements)
 *   * Familles placées aléatoirement parmi les cellules non vacantes, réparties équitablement entre les couleurs
 */
public class TestSchellingSimulator {
    public static void main(String[] args) {
        // Parameters
        final int width = 60;     // number of cells horizontally
        final int height = 40;    // number of cells vertically
        final int cellSize = 10;  // pixel size per cell
        final int numberStates = 10; // number of family colors (>=2). 0 is reserved for vacant (not initialized)
        final double vacancyRate = 0.15; // fraction of grid initially vacant (spec asks for enough vacancies)
        final int thresholdK = 5; // families move if they have > K neighbors of a different color


        GUISimulator window = new GUISimulator(width * cellSize, height * cellSize, Color.WHITE);

        // Initial configuration respecting the spec:
        Set<Cell> initialCells = new HashSet<>();
        HashMap<Cell, Integer> initialStateForCell = new HashMap<>();
        Set<Cell> originVacantHabitation = new HashSet<>();

        // Build a random but well-formed configuration:
        Random rng = new Random(42); // fixed seed for reproducibility; change or remove for variability
        int total = width * height;
        int targetVacant = (int) Math.round(total * vacancyRate);

        // Mark vacancies
        while (originVacantHabitation.size() < targetVacant) {
            int x = rng.nextInt(width);
            int y = rng.nextInt(height);
            originVacantHabitation.add(new Cell(x, y));
        }

        // Fill occupants with balanced colors
        int[] perColorTargets = new int[numberStates]; // index 1..numberStates
        int remainingSlots = total - originVacantHabitation.size();
        int perColor = remainingSlots / numberStates;
        int remainder = remainingSlots % numberStates;
        for (int c = 1; c < numberStates; c++) {
            perColorTargets[c] = perColor + ((c <= remainder) ? 1 : 0);
        }

        // Assign states to non-vacant positions
        int[] perColorAssigned = new int[numberStates];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Cell cell = new Cell(x, y);
                if (originVacantHabitation.contains(cell)) {
                    continue; // leave vacant
                }
                // Pick a color with remaining quota
                int chosen = chooseNextColor(perColorTargets, perColorAssigned, rng, numberStates);
                // Initialize occupied sets/maps (spec: do not initialize state 0)
                initialCells.add(cell);
                initialStateForCell.put(cell, chosen);
                perColorAssigned[chosen]++;
            }
        }

        // Launch simulator
        SchellingSimulator simulator = new SchellingSimulator(
                cellSize, window, width, height,
                initialCells,
                numberStates,
                initialStateForCell,
                originVacantHabitation,
                thresholdK
        );

    }

    private static int chooseNextColor(int[] targets, int[] assigned, Random rng, int numberStates) {
        // Build list of available colors
        int[] available = new int[numberStates];
        int count = 0;
        for (int c = 1; c < numberStates; c++) {
            if (assigned[c] < targets[c]) {
                available[count++] = c;
            }
        }
        if (count == 0) {
            // Fallback (should not happen): pick a random color
            return rng.nextInt(numberStates);
        }
        return available[rng.nextInt(count)];
    }

}
