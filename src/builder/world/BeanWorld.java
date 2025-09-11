package builder.world;

import builder.Tickable;
import builder.ui.RenderableGroup;
import builder.entities.tiles.Tile;
import builder.GameState;
import engine.EngineState;
import engine.renderer.Renderable;
import engine.renderer.Dimensions;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple implementation of the {@link World} interface.
 * Stores and manages a collection of tiles, supports rendering,
 * ticking, and querying tiles at given positions.
 */
public class BeanWorld implements RenderableGroup, Tickable, World {
    private List<Tile> tiles;

    /**
     * Constructs an empty BeanWorld with no tiles.
     */
    public BeanWorld() {
        this.tiles = new ArrayList<>();
    }

    @Override
    public List<Tile> tilesAtPosition(int x, int y, Dimensions dimensions) {
        List<Tile> tilesAtPos = new ArrayList<>();
        int tileX = dimensions.pixelToTile(x);
        int tileY = dimensions.pixelToTile(y);

        for (Tile tile : tiles) {
            int tilePosX = dimensions.pixelToTile(tile.getX());
            int tilePosY = dimensions.pixelToTile(tile.getY());
            if (tilePosX == tileX && tilePosY == tileY) {
                tilesAtPos.add(tile);
            }
        }
        return tilesAtPos;
    }

    @Override
    public List<Tile> allTiles() {
        return new ArrayList<>(tiles);
    }

    @Override
    public void place(Tile tile) {
        tiles.add(tile);
    }

    @Override
    public void tick(EngineState state, GameState game) {
        for (Tile tile : tiles) {
            tile.tick(state);
        }
    }

    @Override
    public List<Renderable> render() {
        List<Renderable> renderables = new ArrayList<>();
        for (Tile tile : tiles) {
            renderables.addAll(tile.render());
        }
        return renderables;
    }
}
