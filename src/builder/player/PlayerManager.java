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

public class PlayerManager implements Tickable, RenderableGroup {
    private ChickenFarmer player;

    public PlayerManager(int x, int y) {
        this.player = new ChickenFarmer(x, y);
    }

    @Override
    public void tick(EngineState state, GameState game) {
        player.tick(state);

        // Try getting KeyState this way
        KeyState keyState = state.getKeys();

        // Handle movement with collision detection
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
                    player.getX(), player.getY() + state.getDimensions().tileSize(), state.getDimensions());

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
            return true; // No world to check against
        }

        List<Tile> tiles = game.getWorld().tilesAtPosition(newX, newY, state.getDimensions());
        for (Tile tile : tiles) {
            if (!tile.canWalkThrough()) {
                return false; // Can't walk through this tile
            }
        }
        return true;
    }

    public Player getPlayer() {
        return player;
    }

    @Override
    public List<Renderable> render() {
        List<Renderable> renderables = new ArrayList<>();
        renderables.add(player);
        return renderables;
    }
}