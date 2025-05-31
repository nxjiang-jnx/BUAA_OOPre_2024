import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class AxeTest {

    private Axe axe;

    @Before
    public void setUp() throws Exception {
        axe = new Axe(1, "Battle Axe", 80, 100, "Axe");
    }

    @Test
    public void testGetId() {
        assertEquals(1, axe.getId());
    }

    @Test
    public void testGetName() {
        assertEquals("Battle Axe", axe.getName());
    }

    @Test
    public void testGetDurability() {
        assertEquals(80, axe.getDurability());
    }

    @Test
    public void testIncreaseDurability() {
        axe.increaseDurability();
        assertEquals(81, axe.getDurability());
    }

    @Test
    public void testDecreaseDurability() {
        axe.decreaseDurability();
        assertEquals(79, axe.getDurability());
    }

    @Test
    public void testGetCombatEffectiveness() {
        assertEquals(100, axe.getCombatEffectiveness());
    }

    @Test
    public void testGetEquType() {
        assertEquals("Axe", axe.getEquType());
    }
}
