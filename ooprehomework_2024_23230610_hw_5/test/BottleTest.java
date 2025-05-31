import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class BottleTest {

    private Bottle bottle;

    @Before
    public void setUp() throws Exception {
        // 初始化一个瓶子对象，使用不同参数进行测试
        bottle = new Bottle(1, "Health Potion", 100, 50, "Healing");
    }

    @Test
    public void testGetId() {
        assertEquals(1, bottle.getId());
    }

    @Test
    public void testGetName() {
        assertEquals("Health Potion", bottle.getName());
    }

    @Test
    public void testGetCapacity() {
        assertEquals(100, bottle.getCapacity());
    }

    @Test
    public void testGetBotType() {
        assertEquals("Healing", bottle.getBotType());
    }

    @Test
    public void testGetCombatEffectiveness() {
        assertEquals(50, bottle.getCombatEffectiveness());
    }

    @Test
    public void testIsEmptyInitially() {
        // 初始状态下，瓶子应该未使用
        assertFalse(bottle.isEmpty());
    }

    @Test
    public void testUseBottle() {
        // 使用瓶子，标记为已使用
        bottle.useBottle();
        assertTrue(bottle.isEmpty());
    }

    @Test
    public void testFillBottle() {
        // 使用后再填满瓶子
        bottle.useBottle();
        assertTrue(bottle.isEmpty());

        bottle.fillBottle();
        assertFalse(bottle.isEmpty());
    }

    @Test
    public void testGetType() {
        // botType 和 getType() 方法一致
        assertEquals(bottle.getBotType(), bottle.getType());
    }
}
