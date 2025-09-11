package builder.player;

import builder.GameState;
import builder.Tickable;
import builder.ui.RenderableGroup;
import builder.entities.tiles.*;
import engine.EngineState;
import engine.game.Direction;
import engine.renderer.Renderable;
import engine.input.KeyState;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages the player character and handles input, movement,
 * collision detection, and interactions with tiles.
 */
public class PlayerManager implements Tickable, RenderableGroup {
    private ChickenFarmer player;

    /**
     * Creates a PlayerManager and spawns a ChickenFarmer at the given coordinates.
     *
     * @param x the initial x-coordinate of the player
     * @param y the initial y-coordinate of the player
     */
    public PlayerManager(int x, int y) {
        this.player = new ChickenFarmer(x, y);
    }

    /**
     * Updates the player each tick, processes input, movement,
     * collisions, and interactions with tiles.
     *
     * @param state the current engine state
     * @param game  the current game state
     */
    @Override
    public void tick(EngineState state, GameState game) {
        player.tick(state);

        KeyState keyState = state.getKeys();
        if (keyState.isDown('w')) {
            int newY = player.getY() - 1;
            if (canMoveTo(player.getX(), newY, state, game)) {
                player.move(Direction.NORTH, 1);
            }
        } else if (keyState.isDown('s')) {
            int newY = player.getY() + 1;
            if (canMoveTo(player.getX(), newY, state, game)) {
                player.move(Direction.SOUTH, 1);
            }
        } else if (keyState.isDown('a')) {
            int newX = player.getX() - 1;
            if (canMoveTo(newX, player.getY(), state, game)) {
                player.move(Direction.WEST, 1);
            }
        } else if (keyState.isDown('d')) {
            int newX = player.getX() + 1;
            if (canMoveTo(newX, player.getY(), state, game)) {
                player.move(Direction.EAST, 1);
            }
        }

        if (game.getWorld() != null) {
            List<Tile> tiles = game.getWorld().tilesAtPosition(
                    player.getX(),
                    player.getY() + state.getDimensions().tileSize(),
                    state.getDimensions()
            );

            for (Tile tile : tiles) {
                tile.interact(state, game);

                if (state.getMouse().isLeftPressed()) {
                    tile.use(state, game);
                }
            }
        }
    }

    private boolean canMoveTo(int newX, int newY, EngineState state, GameState game) {
        if (game.getWorld() == null) {
            return true; 
        }

        List<Tile> tiles = game.getWorld().tilesAtPosition(newX, newY, state.getDimensions());
        for (Tile tile : tiles) {
            if (!tile.canWalkThrough()) {
                return false; 
            }
        }
        return true;
    }

    /**
     * Returns the player entity controlled by this manager.
     *
     * @return the player
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Returns the renderables associated with the player.
     *
     * @return list of renderables for drawing
     */
    @Override
    public List<Renderable> render() {
        List<Renderable> renderables = new ArrayList<>();
        renderables.add(player);
        return renderables;
    }
}
