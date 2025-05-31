import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class HpBottleTest {

    private HpBottle hpBottle;

    @Before
    public void setUp() throws Exception {
        // 初始化一个体力恢复药水瓶子
        hpBottle = new HpBottle(3, "Health Potion", 200, 0, "Health Recovery");
    }

    @Test
    public void testGetId() {
        assertEquals(3, hpBottle.getId());
    }

    @Test
    public void testGetName() {
        assertEquals("Health Potion", hpBottle.getName());
    }

    @Test
    public void testGetCapacity() {
        assertEquals(200, hpBottle.getCapacity());
    }

    @Test
    public void testGetBotType() {
        assertEquals("Health Recovery", hpBottle.getBotType());
    }

    @Test
    public void testGetCombatEffectiveness() {
        assertEquals(0, hpBottle.getCombatEffectiveness()); // 体力恢复药水战斗力为 0
    }

    @Test
    public void testUseBottle() {
        hpBottle.useBottle();
        assertTrue(hpBottle.isEmpty());
    }

    @Test
    public void testFillBottle() {
        hpBottle.useBottle();
        hpBottle.fillBottle();
        assertFalse(hpBottle.isEmpty());
    }
}
