package builder.entities.tiles;

import builder.GameState;
import builder.entities.Interactable;
import builder.entities.Usable;
import builder.ui.RenderableGroup;
import engine.EngineState;
import engine.art.sprites.SpriteGroup;
import engine.game.Entity;
import engine.game.HasTick;
import engine.renderer.Renderable;
import engine.art.ArtNotFoundException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class Tile extends Entity implements Interactable, Usable, RenderableGroup, HasTick {
    private SpriteGroup art;
    private List<Entity> stackedEntities;

    public Tile(int x, int y, SpriteGroup art) {
        super(x, y);
        this.art = art;
        this.stackedEntities = new ArrayList<>();
        setSprite(art.getSprite("default"));
    }

    public void setArt(SpriteGroup art) {
        this.art = art;
        updateSprite("default");
    }

    public void updateSprite(String artName) {
        setSprite(art.getSprite(artName));
    }

    @Override
    public void tick(EngineState engine) {
        Iterator<Entity> iterator = stackedEntities.iterator();
        while (iterator.hasNext()) {
            Entity entity = iterator.next();
            if (entity.isMarkedForRemoval()) {
                iterator.remove();
            }
        }

        for (Entity entity : stackedEntities) {
            if (entity instanceof HasTick) {
                ((HasTick) entity).tick(engine);
            }
        }
    }

    public List<Entity> getStackedEntities() {
        return new ArrayList<>(stackedEntities);
    }

    public void placeOn(Entity entity) {
        stackedEntities.add(entity);
    }

    @Override
    public void interact(EngineState state, GameState game) {
        for (Entity entity : stackedEntities) {
            if (entity instanceof Interactable) {
                ((Interactable) entity).interact(state, game);
            }
        }
    }

    @Override
    public void use(EngineState state, GameState game) {
        for (Entity entity : stackedEntities) {
            if (entity instanceof Usable) {
                ((Usable) entity).use(state, game);
            }
        }
    }

    public boolean canWalkThrough() {
        return true;
    }

    @Override
    public List<Renderable> render() {
        List<Renderable> renderables = new ArrayList<>();
        renderables.add(this);
        renderables.addAll(stackedEntities);
        return renderables;
    }
}
    public void updateSprite(String artName) throws engine.art.ArtNotFoundException {
    setSprite(art.getSprite(artName));
}
