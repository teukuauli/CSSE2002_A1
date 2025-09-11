package builder;

import builder.inventory.Inventory;
import builder.player.Player;
import builder.world.World;

/**
 * Default implementation of {@link GameState}.
 * Stores the current world, player, and inventory references.
 */
public class JavaBeanGameState implements GameState {
    private World world;
    private Player player;
    private Inventory inventory;

    /**
     * Constructs a new game state with the given world, player, and inventory.
     *
     * @param world     the game world
     * @param player    the player character
     * @param inventory the player's inventory
     */
    public JavaBeanGameState(World world, Player player, Inventory inventory) {
        this.world = world;
        this.player = player;
        this.inventory = inventory;
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }

    @Override
    public Player getPlayer() {
        return player;
    }

    @Override
    public World getWorld() {
        return world;
    }
}
