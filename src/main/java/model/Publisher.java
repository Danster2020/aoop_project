package model;

public interface Publisher {
    void registerObserver(Observer observer);

    void unregisterObserver(Observer observer);

    void notifyObservers(Event event);
}
