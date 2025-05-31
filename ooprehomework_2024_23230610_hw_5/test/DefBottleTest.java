import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class DefBottleTest {

    private DefBottle defBottle;

    @Before
    public void setUp() throws Exception {
        // 初始化一个敏捷药水瓶子
        defBottle = new DefBottle(2, "Agility Potion", 120, 40, "DefBottle");
    }

    @Test
    public void testGetId() {
        assertEquals(2, defBottle.getId());
    }

    @Test
    public void testGetName() {
        assertEquals("Agility Potion", defBottle.getName());
    }

    @Test
    public void testGetCapacity() {
        assertEquals(120, defBottle.getCapacity());
    }

    @Test
    public void testGetBotType() {
        assertEquals("DefBottle", defBottle.getBotType());
    }

    @Test
    public void testGetCombatEffectiveness() {
        assertEquals(40, defBottle.getCombatEffectiveness());
    }

    @Test
    public void testUseBottle() {
        defBottle.useBottle();
        assertTrue(defBottle.isEmpty());
    }

    @Test
    public void testFillBottle() {
        defBottle.useBottle();
        defBottle.fillBottle();
        assertFalse(defBottle.isEmpty());
    }
}
