package builder.world;

import builder.entities.tiles.Tile;
import builder.entities.tiles.TileFactory;
import engine.renderer.Dimensions;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Utilities for constructing {@link BeanWorld} instances from strings, files, or tile lists.
 */
public class WorldBuilder {

    /**
     * Creates a WorldBuilder. (Helper class with static builders.)
     */
    public WorldBuilder() {
    }

    /**
     * Parses a textual world representation into tiles.
     * Each line corresponds to rows; each character is a tile symbol.
     *
     * @param dimensions dimensions providing tile/window sizes
     * @param text       the raw world text (lines of symbols)
     * @return a list of created tiles
     * @throws WorldLoadException if the input dimensions/content are invalid
     */
    public static List<Tile> fromString(
            Dimensions dimensions,
            String text
    ) throws WorldLoadException {
        String[] lines = text.split("\n");
        int expectedLines = dimensions.windowSize() / dimensions.tileSize();
        int expectedCharsPerLine = expectedLines;

        if (lines.length != expectedLines) {
            throw new WorldLoadException(
                    "Expected " + expectedLines + " lines, got " + lines.length
            );
        }

        List<Tile> tiles = new ArrayList<>();

        for (int y = 0; y < lines.length; y++) {
            String line = lines[y];
            if (line.length() != expectedCharsPerLine) {
                throw new WorldLoadException(
                        "Line " + y + " has " + line.length()
                                + " characters, expected " + expectedCharsPerLine
                );
            }

            for (int x = 0; x < line.length(); x++) {
                char symbol = line.charAt(x);
                int pixelX = x * dimensions.tileSize();
                int pixelY = y * dimensions.tileSize();

                try {
                    Tile tile = TileFactory.fromSymbol(pixelX, pixelY, symbol);
                    tiles.add(tile);
                } catch (Exception e) {
                    throw new WorldLoadException(
                            "Invalid symbol '" + symbol + "' at position ("
                                    + x + ", " + y + ")"
                    );
                }
            }
        }

        return tiles;
    }

    /**
     * Loads a world from a file path, parsing its textual content into tiles.
     *
     * @param dimensions dimensions providing tile/window sizes
     * @param filepath   path to the world text file
     * @return a {@link BeanWorld} containing the parsed tiles
     * @throws IOException         if file reading fails
     * @throws WorldLoadException  if the file content is invalid
     */
    public static BeanWorld fromFile(
            Dimensions dimensions,
            String filepath
    ) throws IOException, WorldLoadException {
        String content = new String(Files.readAllBytes(Paths.get(filepath)));
        List<Tile> tiles = fromString(dimensions, content);
        return fromTiles(tiles);
    }

    /**
     * Creates an empty {@link BeanWorld}.
     *
     * @return an empty world instance
     */
    public static BeanWorld empty() {
        return new BeanWorld();
    }

    /**
     * Builds a {@link BeanWorld} from a list of tiles.
     *
     * @param tiles tiles to place into the world
     * @return a world containing the provided tiles
     */
    public static BeanWorld fromTiles(List<Tile> tiles) {
        BeanWorld world = new BeanWorld();
        for (Tile tile : tiles) {
            world.place(tile);
        }
        return world;
    }
}
