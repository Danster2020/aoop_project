package model;

import java.util.ArrayList;

import controller.Game;

public class GameData implements Subject {

    public Game game;
    private ArrayList<Observer> observers;

    public GameData(Game g) {
        this.game = g;
        observers = new ArrayList<>();
    }

    public Block[][] getLvlGrid() {
        return game.getCurrLvl().blockGrid;
    }

    @Override
    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void unregisterObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update();
        }
    }
}
