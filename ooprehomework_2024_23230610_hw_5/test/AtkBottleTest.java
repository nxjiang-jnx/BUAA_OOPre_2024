import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class AtkBottleTest {

    private AtkBottle atkBottle;

    @Before
    public void setUp() throws Exception {
        // 初始化一个力量药水瓶子
        atkBottle = new AtkBottle(1, "Strength Potion", 150, 70, "Attack");
    }

    @Test
    public void testGetId() {
        assertEquals(1, atkBottle.getId());
    }

    @Test
    public void testGetName() {
        assertEquals("Strength Potion", atkBottle.getName());
    }

    @Test
    public void testGetCapacity() {
        assertEquals(150, atkBottle.getCapacity());
    }

    @Test
    public void testGetBotType() {
        assertEquals("Attack", atkBottle.getBotType());
    }

    @Test
    public void testGetCombatEffectiveness() {
        assertEquals(70, atkBottle.getCombatEffectiveness());
    }

    @Test
    public void testUseBottle() {
        atkBottle.useBottle();
        assertTrue(atkBottle.isEmpty());
    }

    @Test
    public void testFillBottle() {
        atkBottle.useBottle();
        atkBottle.fillBottle();
        assertFalse(atkBottle.isEmpty());
    }
}
