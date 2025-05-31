import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class BladeTest {

    private Blade blade;

    @Before
    public void setUp() throws Exception {
        blade = new Blade(2, "Sharp Blade", 60, 90, "Blade");
    }

    @Test
    public void testGetId() {
        assertEquals(2, blade.getId());
    }

    @Test
    public void testGetName() {
        assertEquals("Sharp Blade", blade.getName());
    }

    @Test
    public void testGetDurability() {
        assertEquals(60, blade.getDurability());
    }

    @Test
    public void testIncreaseDurability() {
        blade.increaseDurability();
        assertEquals(61, blade.getDurability());
    }

    @Test
    public void testDecreaseDurability() {
        blade.decreaseDurability();
        assertEquals(59, blade.getDurability());
    }

    @Test
    public void testGetCombatEffectiveness() {
        assertEquals(90, blade.getCombatEffectiveness());
    }

    @Test
    public void testGetEquType() {
        assertEquals("Blade", blade.getEquType());
    }
}
