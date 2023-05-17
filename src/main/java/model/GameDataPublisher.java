package model;

import java.util.ArrayList;

import controller.Game;

public class GameDataPublisher implements Publisher {

    public Game game;
    private ArrayList<Observer> observers;

    public GameDataPublisher(Game g) {
        this.game = g;
        observers = new ArrayList<>();
    }

    public Block[][] getLvlGrid() {
        return game.getCurrLvl().blockGrid;
    }

    public String lvlGridToString() {
        String outputString = "[\n";

        for (Block[] row : getLvlGrid()) {
            for (Block block : row) {
                String string = "";
                if (block.hasPlayer()) {
                    string = "P";
                } else if (block.hasTargetBox()) {
                    string = "✓";
                } else if (block.hasBox()) {
                    string = "B";
                } else if (block.isTarget()) {
                    string = "•";
                } else if (block.isWall()) {
                    string = "W";
                } else if (block.isTile()) {
                    string = "-";
                } else {
                    string = "null";
                }
                outputString += string + " ";
            }
            outputString += "\n";
        }
        outputString += "]";

        return outputString;
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
