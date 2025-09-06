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

public class Cabbage extends Entity implements Interactable, HasTick {
    public static final int COST = 2;

    private TickTimer growthTimer;
    private int growthStage = 0;
    private final String[] stages = {"default", "budding", "growing", "grown", "collectable"};

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
        if (growthStage == stages.length - 1) { // Fully grown (collectable)
            game.getInventory().addFood(COST);
            game.getInventory().addCoins(3);
            markForRemoval();
        }
    }
}