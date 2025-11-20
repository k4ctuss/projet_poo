package core;

public abstract class Event implements Comparable<Event> {
    private long date;

    public Event(long date) { this.date = date; }

    public long getDate() { return date; }

    /** Action exécutée à la date prévue */
    public abstract void execute();

    @Override
    public int compareTo(Event other) {
        return Long.compare(this.date, other.date);
    }
}
