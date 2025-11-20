package core;

import java.util.PriorityQueue;

public class EventManager {
    private long currentDate;
    private PriorityQueue<Event> events;

    public EventManager() {
        this.currentDate = 0;
        this.events = new PriorityQueue<>();
    }

    public void addEvent(Event e) {
        events.add(e);
    }

    public void next() {
        currentDate++;
        while (!events.isEmpty() && events.peek().getDate() <= currentDate) {
            Event e = events.poll();
            e.execute();
        }
    }

    public void restart() {
        currentDate = 0;
        events.clear();
    }

    public boolean isFinished() {
        return events.isEmpty();
    }

    public long getCurrentDate() {
        return currentDate;
    }
}
