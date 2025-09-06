package builder.world;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import builder.entities.tiles.Dirt;
import builder.entities.tiles.Grass;
import builder.entities.tiles.Water;
import builder.entities.tiles.Tile;
import builder.GameState;
import engine.EngineState;
import engine.renderer.Dimensions;
import engine.renderer.Renderable;

import java.util.List;

public class BeanWorldTest {
    private BeanWorld world;
    private MockDimensions dimensions;
    private MockEngineState engineState;
    private MockGameState gameState;

    @Before
    public void setUp() {
        world = new BeanWorld();
        dimensions = new MockDimensions();
        engineState = new MockEngineState();
        gameState = new MockGameState();
    }

    @Test
    public void testEmptyWorldAllTiles() {
        List<Tile> tiles = world.allTiles();
        assertNotNull(tiles);
        assertEquals(0, tiles.size());
    }

    @Test
    public void testPlaceSingleTile() {
        Tile dirt = new Dirt(0, 0);
        world.place(dirt);

        List<Tile> allTiles = world.allTiles();
        assertEquals(1, allTiles.size());
        assertTrue(allTiles.contains(dirt));
    }

    @Test
    public void testPlaceMultipleTiles() {
        Tile dirt = new Dirt(0, 0);
        Tile grass = new Grass(25, 25);
        Tile water = new Water(50, 50);

        world.place(dirt);
        world.place(grass);
        world.place(water);

        List<Tile> allTiles = world.allTiles();
        assertEquals(3, allTiles.size());
        assertTrue(allTiles.contains(dirt));
        assertTrue(allTiles.contains(grass));
        assertTrue(allTiles.contains(water));
    }

    @Test
    public void testAllTilesReturnsDefensiveCopy() {
        Tile dirt = new Dirt(0, 0);
        world.place(dirt);

        List<Tile> tiles1 = world.allTiles();
        List<Tile> tiles2 = world.allTiles();

        // Should be different list objects (defensive copy)
        assertNotSame(tiles1, tiles2);
        // But contain same elements
        assertEquals(tiles1, tiles2);

        // Modifying returned list shouldn't affect world
        tiles1.clear();
        assertEquals(1, world.allTiles().size());
    }

    @Test
    public void testTilesAtPositionExactMatch() {
        Tile dirt = new Dirt(0, 0);  // tile position (0,0)
        world.place(dirt);

        List<Tile> tilesFound = world.tilesAtPosition(0, 0, dimensions);
        assertEquals(1, tilesFound.size());
        assertEquals(dirt, tilesFound.get(0));
    }

    @Test
    public void testTilesAtPositionWithinSameTile() {
        Tile dirt = new Dirt(0, 0);  // tile position (0,0)
        world.place(dirt);

        // Any pixel within the tile boundary should find the tile
        List<Tile> tilesFound = world.tilesAtPosition(10, 15, dimensions);
        assertEquals(1, tilesFound.size());
        assertEquals(dirt, tilesFound.get(0));
    }

    @Test
    public void testTilesAtPositionNoMatch() {
        Tile dirt = new Dirt(0, 0);
        world.place(dirt);

        // Position that maps to different tile
        List<Tile> tilesFound = world.tilesAtPosition(100, 100, dimensions);
        assertEquals(0, tilesFound.size());
    }

    @Test
    public void testTilesAtPositionMultipleTilesSamePosition() {
        Tile dirt = new Dirt(0, 0);
        Tile grass = new Grass(0, 0);  // Same position as dirt

        world.place(dirt);
        world.place(grass);

        List<Tile> tilesFound = world.tilesAtPosition(0, 0, dimensions);
        assertEquals(2, tilesFound.size());
        assertTrue(tilesFound.contains(dirt));
        assertTrue(tilesFound.contains(grass));
    }

    @Test
    public void testTilesAtPositionDifferentPositions() {
        Tile dirt = new Dirt(0, 0);
        Tile grass = new Grass(25, 25);

        world.place(dirt);
        world.place(grass);

        List<Tile> tilesAtOrigin = world.tilesAtPosition(0, 0, dimensions);
        assertEquals(1, tilesAtOrigin.size());
        assertEquals(dirt, tilesAtOrigin.get(0));

        List<Tile> tilesAtOther = world.tilesAtPosition(25, 25, dimensions);
        assertEquals(1, tilesAtOther.size());
        assertEquals(grass, tilesAtOther.get(0));
    }

    @Test
    public void testRenderEmptyWorld() {
        List<Renderable> renderables = world.render();
        assertNotNull(renderables);
        assertEquals(0, renderables.size());
    }

    @Test
    public void testRenderWithTiles() {
        Tile dirt = new Dirt(0, 0);
        Tile grass = new Grass(25, 25);

        world.place(dirt);
        world.place(grass);

        List<Renderable> renderables = world.render();
        assertNotNull(renderables);
        // Each tile renders itself, so expect at least 2 renderables
        assertTrue(renderables.size() >= 2);
    }

    @Test
    public void testTickCallsTickOnAllTiles() {
        MockTile mockTile1 = new MockTile(0, 0);
        MockTile mockTile2 = new MockTile(25, 25);

        world.place(mockTile1);
        world.place(mockTile2);

        world.tick(engineState, gameState);

        assertTrue("First tile should have been ticked", mockTile1.wasTickCalled());
        assertTrue("Second tile should have been ticked", mockTile2.wasTickCalled());
    }

    @Test
    public void testTickWithNoTiles() {
        // Should not throw exception
        world.tick(engineState, gameState);
    }

    // Mock classes for testing
    private static class MockDimensions implements Dimensions {
        @Override
        public int pixelToTile(int pixel) {
            return pixel / 25; // Assuming tile size of 25
        }

        @Override
        public int tileToPixel(int tile) {
            return tile * 25;
        }

        @Override
        public int tileSize() {
            return 25;
        }

        @Override
        public int windowSize() {
            return 500;
        }
    }

    private static class MockEngineState implements EngineState {
        private MockDimensions dimensions = new MockDimensions();

        @Override
        public Dimensions getDimensions() {
            return dimensions;
        }

        @Override
        public int currentTick() {
            return 0; // Static value for testing
        }

        // Add minimal implementations for other required methods
        public engine.input.KeyState getKeys() {
            return null; // Not needed for these tests
        }

        public engine.input.MouseState getMouse() {
            return null; // Not needed for these tests
        }
    }

    private static class MockGameState implements GameState {
        private BeanWorld world;
        private builder.player.Player player;
        private builder.inventory.Inventory inventory;

        public MockGameState() {
            this.world = new BeanWorld();
            this.player = null; // Not needed for BeanWorld tests
            this.inventory = null; // Not needed for BeanWorld tests
        }

        @Override
        public BeanWorld getWorld() {
            return world;
        }

        @Override
        public builder.player.Player getPlayer() {
            return player;
        }

        @Override
        public builder.inventory.Inventory getInventory() {
            return inventory;
        }
    }

    private static class MockTile extends Tile {
        private boolean tickCalled = false;

        public MockTile(int x, int y) {
            super(x, y, builder.ui.SpriteGallery.grass); // Use actual sprite group
        }

        @Override
        public void tick(EngineState state) {
            super.tick(state);
            tickCalled = true;
        }

        public boolean wasTickCalled() {
            return tickCalled;
        }

        @Override
        public boolean canWalkThrough() {
            return true; // Default behavior for testing
        }
    }
}