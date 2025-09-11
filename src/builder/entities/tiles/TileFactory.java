package builder.entities.tiles;

/**
 * Factory class for creating {@link Tile} instances based on symbol input.
 */
public class TileFactory {

    /**
     * Constructs a TileFactory. (Not normally needed since all methods are static.)
     */
    public TileFactory() {
        // no-op
    }

    /**
     * Creates a tile at the given coordinates from a character symbol.
     *
     * @param x      the x-coordinate
     * @param y      the y-coordinate
     * @param symbol the tile type symbol: d=Dir t=Tilled Dirt w=Water g=Grass o=OreVein
     * @return a new {@link Tile} matching the symbol
     * @throws IllegalArgumentException if the symbol is unknown
     */
    public static Tile fromSymbol(int x, int y, char symbol) {
        switch (symbol) {
            case 'd':
                return new Dirt(x, y);
            case 't':
                Dirt tilledDirt = new Dirt(x, y);
                tilledDirt.till();
                return tilledDirt;
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
