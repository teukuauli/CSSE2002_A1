package builder.entities.tiles;

import builder.ui.SpriteGallery;

public class Water extends Tile {
    public Water(int x, int y) {
        super(x, y, SpriteGallery.water);
    }

    @Override
    public boolean canWalkThrough() {
        return false;
    }
}