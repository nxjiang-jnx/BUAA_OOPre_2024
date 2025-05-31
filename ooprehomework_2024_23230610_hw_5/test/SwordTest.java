import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class SwordTest {

    private Sword sword;

    @Before
    public void setUp() throws Exception {
        sword = new Sword(3, "Knight's Sword", 70, 95, "Sword");
    }

    @Test
    public void testGetId() {
        assertEquals(3, sword.getId());
    }

    @Test
    public void testGetName() {
        assertEquals("Knight's Sword", sword.getName());
    }

    @Test
    public void testGetDurability() {
        assertEquals(70, sword.getDurability());
    }

    @Test
    public void testIncreaseDurability() {
        sword.increaseDurability();
        assertEquals(71, sword.getDurability());
    }

    @Test
    public void testDecreaseDurability() {
        sword.decreaseDurability();
        assertEquals(69, sword.getDurability());
    }

    @Test
    public void testGetCombatEffectiveness() {
        assertEquals(95, sword.getCombatEffectiveness());
    }

    @Test
    public void testGetEquType() {
        assertEquals("Sword", sword.getEquType());
    }
}
