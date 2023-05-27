package org.observer_pattern;

public interface Observable {
    void notifyObservers();

    void addObserver(Observer o);
}
