package builder;

import builder.inventory.Inventory;
import builder.player.Player;
import builder.world.World;

public class JavaBeanGameState implements GameState {
    private World world;
    private Player player;
    private Inventory inventory;

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