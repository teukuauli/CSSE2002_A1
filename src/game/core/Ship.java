package game.core;

import game.ui.ObjectGraphic;

/**
 * Represents the player's ship in the game.
 * The ship can move in four directions, take damage, and fire bullets.
 */
public class Ship extends Controllable {
    private int health;
    private int score;

    /**
     * Constructs a Ship with the specified position and health.
     * Also initialises score to be 0.
     *
     */
    public Ship(int x, int y, int health) {
        super(x, y);
        this.health = health;
        this.score = 0;
    }

    /**
     * Constructs a Ship with default position and health.
     * By default, a ship should be at position x = 5 and y = 10, with 100 points of health.
     */
    public Ship() {
        super(5, 10);
        this.health = 100;
        this.score = 0;
    }

    /**
     * Returns a new ObjectGraphic with the appropriate text representation and image path.
     *
     * @return the appropriate new ObjectGraphic.
     */
    @Override
    public ObjectGraphic render() {
        return new ObjectGraphic("🚀", "assets/ship.png");
    }

    /**
     * Reduces the ship's health by the specified damage amount.
     * A ship's health can never fall below 0.
     *
     */
    public void takeDamage(int damage) {
        this.health = Math.max(0, this.health - damage);
    }

    /**
     * Heals the ship by the specified amount.
     * A ship's health can never rise above 100.
     *
     */
    public void heal(int amount) {
        this.health = Math.min(100, this.health + amount);
    }

    /**
     * Adds points to the ship's score.
     *
     */
    public void addScore(int points) {
        this.score += points;
    }

    /**
     * Returns the current health of the ship.
     *
     */
    public int getHealth() {
        return this.health;
    }

    /**
     * Returns the current score of the ship.
     *
     * @return the current score.
     */
    public int getScore() {
        return this.score;
    }

    /**
     * As Ships have no tick-dependent behaviour, this method should be left blank.
     *
     * @param tick the given game tick.
     */
    @Override
    public void tick(int tick) {

    }
}