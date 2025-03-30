package game;

import game.core.*;
import game.ui.UI;
import game.exceptions.BoundaryExceededException;
import game.utility.Direction;


/**
 * The Controller handling the game flow and interactions.
 *
 * Holds references to the UI and the Model, so it can pass information and references back and forth as necessary.
 * Manages changes to the game, which are stored in the Model, and displayed by the UI.
 */
public class GameController {
    private long startTime;
    private UI ui;
    private GameModel model;

    /**
     * Initializes the game controller with the given UI and Model.
     * Stores the ui, model and start time.
     * The start time System.currentTimeMillis() should be stored as a long.
     *
     * @param ui the UI used to draw the Game
     * @param model the model used to maintain game information
     * @provided
     */
    public GameController(UI ui, GameModel model) {
        this.ui = ui;
        this.model = model;
        this.startTime = System.currentTimeMillis(); // Start the timer
    }

    /**
     * Initializes the game controller with the given UI and a new GameModel (taking ui::log as the logger).
     * This constructor should call the other constructor using the "this()" keyword.
     *
     * @param ui the UI used to draw the Game
     * @provided
     */
    public GameController(UI ui) {
        this(ui, new GameModel(ui::log));
    }

    /**
     * Returns the current game model.
     *
     * @return the current game model.
     */
    public GameModel getModel() {
        return model;
    }

    /**
     * Starts the main game loop.
     *
     * Passes onTick and handlePlayerInput to ui.onStep and ui.onKey respectively.
     * @provided
     */
    public void startGame() {
        ui.onStep(this::onTick);
        // Uncomment in stage 2
        ui.onKey(this::handlePlayerInput); // Pass Callback to UI
    }

    /**
     * Uses the provided tick to call and advance the following:
     *      - A call to renderGame() to draw the current state of the game.
     *      - A call to model.updateGame(tick) to advance the game by the given tick.
     *      - A call to model.checkCollisions() to handle game interactions.
     *      - A call to model.spawnObjects() to handle object creation.
     *      - A call to model.levelUp() to check and handle leveling.
     *
     * @param tick the provided tick
     * @provided
     */
    public void onTick(int tick) {
        renderGame(); // Update Visual
        model.updateGame(tick); // Update GameObjects
        model.checkCollisions(); // Check for Collisions
        model.spawnObjects(); // Handles new spawns
        model.levelUp(); // Level up when score threshold is met
    }

    /**
     * Renders the current state of the game using the UI.
     */
    /**
     * Renders the current game state, including score, health, and ship position.
     * - Uses ui.setStat() to update the "Score", "Health" and "Level" appropriately with information from the model.
     * - Uses ui.setStat() to update "Time Survived" with (System.currentTimeMillis() - startTime) / 1000 + " seconds"
     * - Renders all SpaceObjects (including the Ship) using a single call to ui.render().
     */
    public void renderGame() {
        // Render all space objects
        ui.render(model.getSpaceObjects());

        // Update statistics display
        Ship ship = model.getShip();
        if (ship != null) {
            ui.setStat("Score", Integer.toString(ship.getScore()));
            ui.setStat("Health", Integer.toString(ship.getHealth()));
            ui.setStat("Level", Integer.toString(model.getLevel()));
        }

        // Calculate and display time survived
        long timeElapsed = (System.currentTimeMillis() - startTime) / 1000;
        ui.setStat("Time Survived", timeElapsed + " seconds");
    }

    /**
     * Handles player input and performs actions such as moving the ship or firing bullets.
     * Uppercase and lowercase inputs should be treated identically:
     * - For movement keys "W", "A", "S" and "D" the ship should be moved up, left, down, or right respectively
     *   The following should also be logged: "Ship moved to (x, y)"
     * - For input "F" the fireBullet() method of the model should be called.
     * - For input "P" the pauseGame() method should be called.
     * - For all other inputs, the following should be logged: "Invalid input. Use W, A, S, D, F, or P."
     *
     */
    public void handlePlayerInput(String input) {
        // Convert input to lowercase for case-insensitive comparison
        String lowerInput = input.toLowerCase();

        // Get the ship from the model
        Ship ship = model.getShip();

        // If no ship is found, we can't handle movement commands
        if (ship == null && (lowerInput.equals("w")
                || lowerInput.equals("a")
                || lowerInput.equals("s")
                || lowerInput.equals("d"))) {
            ui.log("No ship found in the game.");
            return;
        }

        try {
            switch (lowerInput) {
                case "w":
                    ship.move(Direction.UP);
                    ui.log("Core.Ship moved to (" + ship.getX() + ", " + ship.getY() + ")");
                    break;
                case "a":
                    ship.move(Direction.LEFT);
                    ui.log("Core.Ship moved to (" + ship.getX() + ", " + ship.getY() + ")");
                    break;
                case "s":
                    ship.move(Direction.DOWN);
                    ui.log("Core.Ship moved to (" + ship.getX() + ", " + ship.getY() + ")");
                    break;
                case "d":
                    ship.move(Direction.RIGHT);
                    ui.log("Core.Ship moved to (" + ship.getX() + ", " + ship.getY() + ")");
                    break;
                case "f":
                    model.fireBullet();
                    break;
                case "p":
                    pauseGame();
                    break;
                default:
                    ui.log("Invalid input. Use W, A, S, D, F, or P.");
                    break;
            }
        } catch (BoundaryExceededException e) {
            // Log the boundary exception message
            ui.log(e.getMessage());
        }
    }

    /**
     * Calls ui.pause() to pause the game until the method is called again.
     * Logs "Game paused." after calling ui.pause().
     */
    public void pauseGame() {
        ui.pause();
        ui.log("Game paused.");
    }
}