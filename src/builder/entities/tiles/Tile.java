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

/**
 * Base class for map tiles. A tile can stack entities on top of it and
 * forwards interactions/uses to stacked entities. Tiles are renderable
 * and may tick each frame.
 */
public abstract class Tile extends Entity
        implements Interactable,
                   Usable,
                   RenderableGroup,
                   HasTick {

    private SpriteGroup art;
    private List<Entity> stackedEntities;

    /**
     * Creates a tile at (x, y) with the provided sprite group.
     *
     * @param x   the x-coordinate
     * @param y   the y-coordinate
     * @param art the sprite group used to render this tile
     */
    public Tile(int x, int y, SpriteGroup art) {
        super(x, y);
        this.art = art;
        this.stackedEntities = new ArrayList<>();
        setSprite(art.getSprite("default"));
    }

    /**
     * Sets the sprite group and attempts to update to the default sprite.
     *
     * @param art the new sprite group
     */
    public void setArt(SpriteGroup art) {
        this.art = art;
        try {
            updateSprite("default");
        } catch (ArtNotFoundException e) {
            // Default sprite not found; keep current sprite.
        }
    }

    /**
     * Updates the current sprite using a name within the sprite group.
     *
     * @param artName the sprite name to display
     * @throws ArtNotFoundException if the named sprite is missing
     */
    public void updateSprite(String artName) throws ArtNotFoundException {
        setSprite(art.getSprite(artName));
    }

    /**
     * Ticks this tile and any stacked entities that also implement {@link HasTick}.
     *
     * @param engine the current engine state
     */
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

    /**
     * Returns a defensive copy of entities stacked on this tile.
     *
     * @return list of stacked entities
     */
    public List<Entity> getStackedEntities() {
        return new ArrayList<>(stackedEntities);
    }

    /**
     * Places an entity on top of this tile.
     *
     * @param entity the entity to stack
     */
    public void placeOn(Entity entity) {
        stackedEntities.add(entity);
    }

    /**
     * Forwards interaction to stacked entities that are {@link Interactable}.
     *
     * @param state the engine state
     * @param game  the game state
     */
    @Override
    public void interact(EngineState state, GameState game) {
        for (Entity entity : stackedEntities) {
            if (entity instanceof Interactable) {
                ((Interactable) entity).interact(state, game);
            }
        }
    }

    /**
     * Forwards use to stacked entities that are {@link Usable}.
     *
     * @param state the engine state
     * @param game  the game state
     */
    @Override
    public void use(EngineState state, GameState game) {
        for (Entity entity : stackedEntities) {
            if (entity instanceof Usable) {
                ((Usable) entity).use(state, game);
            }
        }
    }

    /**
     * Whether characters can walk through this tile.
     *
     * @return true if walkable; false otherwise
     */
    public boolean canWalkThrough() {
        return true;
    }

    /**
     * Returns this tile and all stacked entities as renderables.
     *
     * @return renderables in draw order
     */
    @Override
    public List<Renderable> render() {
        List<Renderable> renderables = new ArrayList<>();
        renderables.add(this);
        renderables.addAll(stackedEntities);
        return renderables;
    }
}
