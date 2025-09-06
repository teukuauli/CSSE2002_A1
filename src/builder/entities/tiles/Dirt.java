package builder.entities.tiles;

import builder.GameState;
import builder.entities.resources.Cabbage;
import builder.inventory.items.Bucket;
import builder.inventory.items.Hoe;
import builder.ui.SpriteGallery;
import engine.EngineState;

public class Dirt extends Tile {
    private boolean tilled = false;

    public Dirt(int x, int y) {
        super(x, y, SpriteGallery.field);
    }

    public boolean isTilled() {
        return tilled;
    }

    public void till() {
        this.tilled = true;
        setArt(SpriteGallery.tilled);
    }

    @Override
    public void use(EngineState state, GameState game) {
        super.use(state, game);

        if (game.getInventory().getHolding() instanceof Hoe) {
            till();
        } else if (game.getInventory().getHolding() instanceof Bucket &&
                getStackedEntities().isEmpty() &&
                tilled &&
                game.getInventory().getCoins() >= Cabbage.COST) {

            game.getInventory().addCoins(-Cabbage.COST);
            placeOn(new Cabbage(getX(), getY()));
        }
    }
}