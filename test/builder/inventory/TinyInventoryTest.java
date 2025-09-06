package builder.inventory;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import builder.inventory.items.Item;
import builder.inventory.items.Hoe;

public class TinyInventoryTest {

    private TinyInventory inventory;

    @Before
    public void setUp() {
        inventory = new TinyInventory(4);
    }

    @Test
    public void testGetCapacity() {
        assertEquals(4, inventory.getCapacity());
    }

    @Test
    public void testSetItem() {
        Hoe hoe = new Hoe();
        inventory.setItem(0, hoe);
        assertEquals(hoe, inventory.getItem(0));
    }

    @Test
    public void testSetActiveSlot() {
        inventory.setActiveSlot(2);
        assertEquals(2, inventory.getActiveSlot());
    }

    @Test
    public void testGetActiveSlot() {
        assertEquals(0, inventory.getActiveSlot());
    }

    @Test
    public void testAddCoins() {
        inventory.addCoins(10);
        assertEquals(10, inventory.getCoins());
    }

    @Test
    public void testAddFood() {
        inventory.addFood(5);
        assertEquals(5, inventory.getFood());
    }
}