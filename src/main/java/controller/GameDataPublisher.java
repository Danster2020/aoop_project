package controller;

import java.util.ArrayList;

import model.Block;
import model.Event;
import model.Observer;
import model.Publisher;

public class GameDataPublisher implements Publisher {

    private Game game;
    private ArrayList<Observer> observers;

    public GameDataPublisher(Game g) {
        this.game = g;
        observers = new ArrayList<>();
    }

    /**
     * @return Game
     */
    public Game getGame() {
        return game;
    }

    /**
     * @return Block[][]
     */
    public Block[][] getLvlGrid() {
        return game.getCurrLvl().getGrid();
    }

    /**
     * Represents the grid in a string format. Every symbol is decided by the type
     * of block.
     * 
     * @return String
     */
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

    /**
     * @param observer
     */
    @Override
    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    /**
     * @param observer
     */
    @Override
    public void unregisterObserver(Observer observer) {
        observers.remove(observer);
    }

    /**
     * @param event
     */
    @Override
    public void notifyObservers(Event event) {
        for (Observer observer : observers) {
            observer.update(event);
        }
    }
}
