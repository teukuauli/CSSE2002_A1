package builder.entities.resources;

import builder.GameState;
import builder.entities.Usable;
import builder.inventory.items.Jackhammer;
import builder.ui.SpriteGallery;
import engine.EngineState;
import engine.game.Entity;
import engine.game.HasTick;

/**
 * Represents an ore resource in the game that can be mined with a jackhammer.
 * The ore depletes over time as it is mined and provides coins to the player.
 */
public class Ore extends Entity implements Usable, HasTick {
    private int value = 10;
    private final int originalValue = 10;
    private int tickCounter = 0;

    /**
     * Constructs an ore entity at the given position.
     *
     * @param x the x-coordinate of the ore
     * @param y the y-coordinate of the ore
     */
    public Ore(int x, int y) {
        super(x, y);
        setSprite(SpriteGallery.rock.getSprite("default"));
    }

    @Override
    public void tick(EngineState state) {
        tickCounter++;

        double percentage = (double) value / originalValue;
        if (percentage > 0.9) {
            setSprite(SpriteGallery.rock.getSprite("default"));
        } else if (percentage > 0.1) {
            setSprite(SpriteGallery.rock.getSprite("damaged"));
        } else {
            setSprite(SpriteGallery.rock.getSprite("depleted"));
        }
    }

    @Override
    public void use(EngineState state, GameState game) {
        if (game.getInventory().getHolding() instanceof Jackhammer &&
                tickCounter % 5 == 0 &&
                value > 0) {

            int damage = game.getPlayer().getDamage();
            int coinsToAdd = Math.min(damage, value);
            value -= coinsToAdd;
            game.getInventory().addCoins(coinsToAdd);

            if (value <= 0) {
                markForRemoval();
            }
        }
    }
}
