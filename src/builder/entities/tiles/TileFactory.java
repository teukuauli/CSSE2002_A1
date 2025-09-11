package builder.entities.tiles;

/**
 * Factory class for creating {@link Tile} instances based on symbol input.
 */
public class TileFactory {

    /**
     * Constructs a TileFactory
     */
    public TileFactory() {
    }

    /**
     * Creates a tile at the given coordinates from a character symbol.
     * d = Dirt, t = Tilled Dirt, w = Water, g = Grass, o = OreVein
     *
     * @param x      the x-coordinate (pixels)
     * @param y      the y-coordinate (pixels)
     * @param symbol the tile symbol
     * @return a new {@link Tile}
     */
    public static Tile fromSymbol(int x, int y, char symbol) {
        switch (symbol) {
            case 'd':
                return new Dirt(x, y);
            case 't': {
                Dirt tilledDirt = new Dirt(x, y);
                tilledDirt.till();
                return tilledDirt;
            }
            case 'w':
                return new Water(x, y);
            case 'g':
                return new Grass(x, y);
            case 'o':
                return new OreVein(x, y);
            default:
                throw new IllegalArgumentException("Unknown tile symbol: " + symbol);
        }
    }
}
