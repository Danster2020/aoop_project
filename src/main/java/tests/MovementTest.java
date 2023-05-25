package tests;

import org.junit.*;
import org.junit.Test;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;

import controller.Game;
import controller.Game.Direction;

import static org.junit.Assert.assertEquals;

public class MovementTest {

    Game game = new Game();

    @Before // before each test
    public void setUp() {
        game.loadLevel("TestLevel1", false);
    }

    @After // after each test
    public void tearDown() {
        // mpq = new MaxPQ<Integer>();
    }

    @Test
    public void moveUp() {
        game.getPlayer().move(Direction.UP);
        assertEquals("Player in wrong position", true, game.getCurrLvl().getBlock(2, 2).hasPlayer());
        assertEquals("Player was not removed from old position", false, game.getCurrLvl().getBlock(2, 3).hasPlayer());
    }

    @Test
    public void moveDown() {
        game.getPlayer().move(Direction.DOWN);
        assertEquals("Player in wrong position", true, game.getCurrLvl().getBlock(2, 4).hasPlayer());
        assertEquals("Player was not removed from old position", false, game.getCurrLvl().getBlock(2, 3).hasPlayer());
    }

    @Test
    public void moveLeft() {
        game.getPlayer().move(Direction.LEFT);
        assertEquals("Player in wrong position", true, game.getCurrLvl().getBlock(1, 3).hasPlayer());
        assertEquals("Player was not removed from old position", false, game.getCurrLvl().getBlock(2, 3).hasPlayer());
    }

    @Test
    public void moveRight() {
        game.getPlayer().move(Direction.RIGHT);
        assertEquals("Player in wrong position", true, game.getCurrLvl().getBlock(3, 3).hasPlayer());
        assertEquals("Player was not removed from old position", false, game.getCurrLvl().getBlock(2, 3).hasPlayer());
    }

    @Test
    public void playerCollisionWall() {
        game.getPlayer().move(Direction.LEFT);
        game.getPlayer().move(Direction.LEFT);

        // player should be infront of wall
        assertEquals("Player didnt collide with wall", true, game.getCurrLvl().getBlock(1, 3).hasPlayer());

        // player should not be inside wall
        assertEquals("Player inside wall", false, game.getCurrLvl().getBlock(0, 3).hasPlayer());
    }

    @Test
    public void playerCollisionOneBox() {
        game.getPlayer().move(Direction.RIGHT);
        game.getPlayer().move(Direction.RIGHT);

        // player should have pushed box
        assertEquals("Player was blocked by box", true, game.getCurrLvl().getBlock(4, 3).hasPlayer());

        // box should have moved
        assertEquals("Box didn't move", true, game.getCurrLvl().getBlock(5, 3).hasBox());
    }

    @Test
    public void playerCollisionTwoBoxes() {
        game.getPlayer().move(Direction.RIGHT);
        game.getPlayer().move(Direction.RIGHT);
        game.getPlayer().move(Direction.DOWN);

        // player should have pushed box
        assertEquals("Player was blocked by box", true, game.getCurrLvl().getBlock(4, 3).hasPlayer());

        // box1 should not have moved
        assertEquals("Box1 moved", true, game.getCurrLvl().getBlock(4, 4).hasBox());

        // box2 should not have moved
        assertEquals("Box2 moved", true, game.getCurrLvl().getBlock(4, 5).hasBox());
    }

    @Test
    public void boxPlacedOnTarget() {
        game.getPlayer().move(Direction.RIGHT);
        game.getPlayer().move(Direction.RIGHT);

        // player should have pushed box
        assertEquals("Player was blocked by box", true, game.getCurrLvl().getBlock(4, 3).hasPlayer());

        // box should have moved
        assertEquals("Box didn't move", true, game.getCurrLvl().getBlock(5, 3).hasBox());

        // box should have converted to targetBox
        assertEquals("Block is not a target box", true,
                game.getCurrLvl().getBlock(5, 3).hasTargetBox());
    }

    @Test
    public void levelComplete() throws InterruptedException {
        Robot robot = null;
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }

        game.getPlayer().move(Direction.RIGHT);
        game.getPlayer().move(Direction.RIGHT);
        robot.keyPress(KeyEvent.VK_UP); // simulates a real key press

        Thread.sleep(1000);
        assertEquals("Level is not completed", true, game.isLevelComplete());

        // Should Progress to next level
        robot.keyPress(KeyEvent.VK_ENTER);
        Thread.sleep(1000);
        assertEquals("Did not progress to next level", "level2", game.getCurrLvl().getName());
    }

}
