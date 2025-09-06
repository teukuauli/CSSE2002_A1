package builder.entities.tiles;

import builder.GameState;
import builder.inventory.items.Hoe;
import builder.ui.SpriteGallery;
import engine.EngineState;

public class Grass extends Tile {
    public Grass(int x, int y) {
        super(x, y, SpriteGallery.grass);
    }

    @Override
    public void use(EngineState state, GameState game) {
        super.use(state, game);

        if (game.getInventory().getHolding() instanceof Hoe && !isMarkedForRemoval()) {
            markForRemoval();
            game.getWorld().place(new Dirt(getX(), getY()));
        }
    }
}