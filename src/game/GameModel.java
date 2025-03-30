package game;

import game.core.*;
import game.utility.Logger;
import game.core.SpaceObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Represents the game information and state. Stores and manipulates the game state.
 */
public class GameModel {
    public static final int GAME_HEIGHT = 20;
    public static final int GAME_WIDTH = 10;
    public static final int START_SPAWN_RATE = 2; // spawn rate (percentage chance per tick)
    public static final int SPAWN_RATE_INCREASE = 5; // Increase spawn rate by 5% per level
    public static final int START_LEVEL = 1; // Starting level value
    public static final int SCORE_THRESHOLD = 100; // Score threshold for leveling
    public static final int ASTEROID_DAMAGE = 10; // The amount of damage an asteroid deals
    public static final int ENEMY_DAMAGE = 20; // The amount of damage an enemy deals
    public static final double ENEMY_SPAWN_RATE = 0.5; // Percentage of asteroid spawn chance
    public static final double POWER_UP_SPAWN_RATE = 0.25; // Percentage of asteroid spawn chance

    private final Random random = new Random(); // ONLY USED IN this.spawnObjects()
    private final Logger logger;
    private final List<SpaceObject> spaceObjects;
    private int level;
    private int spawnRate;

    /**
     * Models a game, storing and modifying data relevant to the game.
     * Logger argument should be a method reference to a .log method such as the UI.log method.
     * Example: Model gameModel = new GameModel(ui::log)
     * - Instantiates an empty list for storing all SpaceObjects the model needs to track.
     * - Instantiates the game level with the starting level value.
     * - Instantiates the game spawn rate with the starting spawn rate.
     * - Instantiates a new ship.
     * - Stores reference to the given logger.
     *
     * @param logger a functional interface for passing information between classes.
     */
    public GameModel(Logger logger) {
        this.logger = logger;
        this.spaceObjects = new ArrayList<>();
        this.level = START_LEVEL;
        this.spawnRate = START_SPAWN_RATE;

        Ship ship = new Ship();
        this.spaceObjects.add(ship);
    }

    /**
     * Returns the ship instance in the game.
     *
     * @return the current ship instance.
     */
    public Ship getShip() {
        for (SpaceObject obj : spaceObjects) {
            if (obj instanceof Ship) {
                return (Ship) obj;
            }
        }
        return null;
    }

    /**
     * Returns a list of all SpaceObjects in the game.
     *
     * @return a list of all spaceObjects.
     */
    public List<SpaceObject> getSpaceObjects() {
        return spaceObjects;
    }

    /**
     * Returns the current level.
     *
     * @return the current level.
     */
    public int getLevel() {
        return level;
    }

    /**
     * Adds a SpaceObject to the game.
     * Objects are considered part of the game only when they are tracked by the model.
     *
     */
    public void addObject(SpaceObject object) {
        spaceObjects.add(object);
        logger.log("Added object: " + object.getClass().getSimpleName());
    }

    /**
     * Updates the game state by moving all objects and then removing off-screen objects.
     * Objects should be moved by calling .tick(tick) on each object.
     * Objects are considered off-screen if they are at y-coordinate > GAME_HEIGHT.
     *
     */
    public void updateGame(int tick) {
        // Create a list to store objects that need to be removed
        List<SpaceObject> objectsToRemove = new ArrayList<>();

        // Update all objects by calling tick
        for (SpaceObject object : spaceObjects) {
            object.tick(tick);

            // Check if the object is off-screen (y > GAME_HEIGHT)
            if (object.getY() > GAME_HEIGHT) {
                objectsToRemove.add(object);
            }
        }

        // Remove off-screen objects
        spaceObjects.removeAll(objectsToRemove);
    }

    /**
     * Spawns new objects (asteroids, enemies, and power-ups) at random positions.
     * Uses this.random to make EXACTLY 6 calls to random.nextInt() and 1 random.nextBoolean.
     */
    public void spawnObjects() {
        Ship ship = getShip();

        // 1. Check if an asteroid should spawn
        if (random.nextInt(100) < spawnRate) {
            // 2. If spawning an asteroid, spawn at x-coordinate = random.nextInt(GAME_WIDTH)
            int x = random.nextInt(GAME_WIDTH);

            // Check if ship is at the spawn location
            if (ship == null || ship.getX() != x || ship.getY() != 0) {
                addObject(new Asteroid(x, 0));
            }
        }

        // 3. Check if an enemy should spawn
        if (random.nextInt(100) < spawnRate * ENEMY_SPAWN_RATE) {
            // 4. If spawning an enemy, spawn at x-coordinate = random.nextInt(GAME_WIDTH)
            int x = random.nextInt(GAME_WIDTH);

            // Check if ship is at the spawn location
            if (ship == null || ship.getX() != x || ship.getY() != 0) {
                addObject(new Enemy(x, 0));
            }
        }

        // 5. Check if a power-up should spawn
        if (random.nextInt(100) < spawnRate * POWER_UP_SPAWN_RATE) {
            // 6. If spawning a power-up, spawn at x-coordinate = random.nextInt(GAME_WIDTH)
            int x = random.nextInt(GAME_WIDTH);

            // 7. If spawning a power-up, spawn a ShieldPowerUp if random.nextBoolean(), else a HealthPowerUp
            boolean isShield = random.nextBoolean();

            // Check if ship is at the spawn location
            if (ship == null || ship.getX() != x || ship.getY() != 0) {
                if (isShield) {
                    addObject(new ShieldPowerUp(x, 0));
                } else {
                    addObject(new HealthPowerUp(x, 0));
                }
            }
        }
    }

    /**
     * If level progression requirements are satisfied, levels up the game by increasing
     * the spawn rate and level number.
     */
    public void levelUp() {
        Ship ship = getShip();

        // Check if ship exists and score meets the threshold
        if (ship != null && ship.getScore() >= level * SCORE_THRESHOLD) {
            // Increase level and spawn rate
            level++;
            spawnRate += SPAWN_RATE_INCREASE;

            // Log the level up message
            logger.log("Level Up! Welcome to Level " + level +
                    ". Spawn rate increased to " + spawnRate + "%.");
        }
    }

    /**
     * Fires a bullet from the ship's current position.
     * Creates a new bullet at the coordinates the ship occupies.
     * Logs "Bullet fired!"
     */
    public void fireBullet() {
        Ship ship = getShip();

        // If a ship exists, create a bullet just above it
        if (ship != null) {
            Bullet bullet = new Bullet(ship.getX(), ship.getY() - 1);
            spaceObjects.add(bullet);
            logger.log("Core.Bullet fired!");
        } else {
            logger.log("Cannot fire bullet: No ship found in the game.");
        }
    }

    /**
     * Detects and handles collisions between spaceObjects (Ship and Bullet collisions).
     * Objects are considered to be colliding if they share x and y coordinates.
     */
    public void checkCollisions() {
        Ship ship = getShip();

        if (ship != null) {
            List<SpaceObject> objectsToRemove = new ArrayList<>();

            // Check for collisions with the ship
            for (SpaceObject obj : spaceObjects) {
                if (obj == ship) {
                    continue;
                }

                // Check if the object is at the same position as the ship
                if (obj.getX() == ship.getX() && obj.getY() == ship.getY()) {
                    if (obj instanceof PowerUp) {
                        PowerUp powerUp = (PowerUp) obj;
                        powerUp.applyEffect(ship);
                        String powerUpType = obj.getClass().getSimpleName();
                        logger.log("Power-up collected: " + powerUpType);
                        objectsToRemove.add(obj);
                    } else if (obj instanceof Asteroid) {
                        // Reduce ship health by asteroid damage
                        ship.takeDamage(ASTEROID_DAMAGE);
                        logger.log("Hit by asteroid! Health reduced by " + ASTEROID_DAMAGE + ".");
                        objectsToRemove.add(obj);
                    } else if (obj instanceof Enemy) {
                        // Reduce ship health by enemy damage
                        ship.takeDamage(ENEMY_DAMAGE);
                        logger.log("Hit by enemy! Health reduced by " + ENEMY_DAMAGE + ".");
                        objectsToRemove.add(obj);
                    }
                }
            }

            spaceObjects.removeAll(objectsToRemove);
        }

        // Check bullet collisions with enemies
        List<SpaceObject> bulletsToRemove = new ArrayList<>();
        List<SpaceObject> enemiesToRemove = new ArrayList<>();

        // Find all bullets and check for collisions with enemies
        for (SpaceObject obj1 : spaceObjects) {
            if (obj1 instanceof Bullet) {
                for (SpaceObject obj2 : spaceObjects) {
                    if (obj2 instanceof Enemy) {
                        if (obj1.getX() == obj2.getX() && obj1.getY() == obj2.getY()) {
                            bulletsToRemove.add(obj1);
                            enemiesToRemove.add(obj2);
                        }
                    }
                }
            }
        }

        // Remove bullets and enemies that collided
        spaceObjects.removeAll(bulletsToRemove);
        spaceObjects.removeAll(enemiesToRemove);
    }

    /**
     * Sets the seed of the Random instance created in the constructor using .setSeed().
     *
     * This method should NEVER be called.
     *
     * @param seed to be set for the Random instance
     * @provided
     */
    public void setRandomSeed(int seed) {
        this.random.setSeed(seed);
    }
}