package core;

/**
 * Représente un événement dans un système de gestion d'événements
 * Chaque événement a une date d'exécution et une action associée
 * Les événements sont comparables en fonction de leur date pour permettre l'ordonnancement
 */
public abstract class Event implements Comparable<Event> {
    private long date;

    public Event(long date) { this.date = date; }

    public long getDate() { return date; }

    /** Action exécutée à la date prévue */
    public abstract void execute();

    /**
     * Compare deux événements en fonction de leur date
     * @param other l'autre événement à comparer
     * @return un entier négatif, zéro ou positif si cet événement est respectivement avant,
     *         à la même date ou après l'autre événement
     */
    @Override
    public int compareTo(Event other) {
        return Long.compare(this.date, other.date);
    }
}
