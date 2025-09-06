package builder.entities.tiles;

public class TileFactory {

    public TileFactory() {
        // Constructor not needed according to hint
    }

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