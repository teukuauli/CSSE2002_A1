package builder.entities.tiles;

import builder.ui.SpriteGallery;
import builder.entities.resources.Ore;

public class OreVein extends Tile {
    private Ore ore;

    public OreVein(int x, int y) {
        super(x, y, SpriteGallery.field);
        this.ore = new Ore(x, y);  // Add coordinates
        placeOn(ore);
    }
    public Ore getOre(){
        return ore;
    }
}