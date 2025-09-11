package builder.entities.resources;

import builder.GameState;
import builder.entities.Interactable;
import builder.ui.SpriteGallery;
import engine.EngineState;
import engine.game.Entity;
import engine.game.HasTick;
import engine.timing.RepeatingTimer;
import engine.timing.TimerDuration;
import engine.timing.TickTimer;

/**
 * Represents a cabbage crop that grows over time and can be collected
 * when fully grown. Provides food and coins to the player's inventory
 * upon harvest.
 */
public class Cabbage extends Entity implements Interactable, HasTick {
    public static final int COST = 2;

    private TickTimer growthTimer;
    private int growthStage = 0;
    private final String[] stages = {"default", "budding", "growing", "grown", "collectable"};

    /**
     * Constructs a cabbage entity at the given position.
     *
     * @param x the x-coordinate of the cabbage
     * @param y the y-coordinate of the cabbage
     */
    public Cabbage(int x, int y) {
        super(x, y);
        setSprite(SpriteGallery.cabbage.getSprite("default"));
        this.growthTimer = new RepeatingTimer(TimerDuration.SHORT);
    }

    @Override
    public void tick(EngineState state) {
        growthTimer.tick();
        if (growthTimer.isFinished() && growthStage < stages.length - 1) {
            growthStage++;
            setSprite(SpriteGallery.cabbage.getSprite(stages[growthStage]));
        }
    }

    @Override
    public void interact(EngineState state, GameState game) {
        if (growthStage == stages.length - 1) { 
            game.getInventory().addFood(COST);
            game.getInventory().addCoins(3);
            markForRemoval();
        }
    }
}
