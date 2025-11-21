package boids;

import core.EventManager;
import core.StepEvent;
import gui.GUISimulator;
import gui.Oval;
import gui.Simulable;

import java.util.*;

/**
 * Simulateur de boids gérant plusieurs groupes avec des fréquences d'update différentes
 * Utilise un EventManager pour orchestrer les mises à jour des différents groupes
 */
public class BoidSimulator implements Simulable {

    private final GUISimulator gui;
    private final EventManager eventManager;
    private final Set<BoidGroup> groups;
    private final Map<BoidGroup, Long> groupFrequencies;  // Fréquence d'update pour chaque groupe

    /**
     * Constructeur du simulateur de boids
     * @param gui l'interface graphique

     */
    public BoidSimulator(GUISimulator gui) {
        this.gui = gui;
        this.groups = new HashSet<>();
        this.eventManager = new EventManager();
        this.groupFrequencies = new HashMap<>();
        this.gui.setSimulable(this);
    }

    /**
     * Ajoute un groupe de boids avec une fréquence d'update spécifique
     * @param group le groupe à ajouter
     * @param frequency fréquence d'update (intervalle de temps)
     */
    public void addBoidGroup(BoidGroup group, long frequency) {
        groups.add(group);
        groupFrequencies.put(group, frequency);
        // Créer l'événement initial du groupe
        eventManager.addEvent(new StepEvent(0, group, eventManager, frequency));
    }

    /**
     * Configure les interactions entre deux groupes
     * @param group1 premier groupe
     * @param groupName1 nom de l'interaction pour group1
     * @param group2 deuxième groupe
     */
    public void setupInteraction(BoidGroup group1, String groupName1, BoidGroup group2) {
        group1.addInteractionGroup(groupName1, group2);
    }

    /**
     * Passe au pas de temps suivant
     * Délègue au gestionnaire d'événements
     */
    @Override
    public void next(){
        eventManager.next();
        draw();
    }

    /**
     * Réinitialise la simulation
     * Remet tous les groupes à leur état initial
     */
    @Override
    public void restart(){
        eventManager.restart();
        for(BoidGroup group : groups) {
            group.restart();
            // Recréer l'événement initial du groupe
            long freq = groupFrequencies.getOrDefault(group, 1L);
            eventManager.addEvent(new StepEvent(0, group, eventManager, freq));
        }
        draw();
    }

    /**
     * Affiche tous les boids de tous les groupes
     * Chaque groupe a sa propre couleur de configuration
     */
    public void draw() {
        gui.reset();
        for(BoidGroup group : groups) {
            for(Boid boid : group.getBoids()) {
                gui.addGraphicalElement(new Oval(
                    (int)boid.getX(), (int)boid.getY(), boid.getColor(), boid.getColor(), 6
                ));
            }
        }
    }

    public long getDelay(BoidGroup group){
        return this.groupFrequencies.getOrDefault(group, 1L);
    }
}
