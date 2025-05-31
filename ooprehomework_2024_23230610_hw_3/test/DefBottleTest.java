import static org.junit.Assert.*;
import org.junit.Test;

public class DefBottleTest {
    @Test
    public void testDefBottleConstructorAndMethods() {
        // 实例化 DefBottle
        DefBottle defBottle = new DefBottle(1, "Defense Potion", 50, 10, "DefBottle");

        // 测试对象是否成功创建
        assertNotNull(defBottle);

        // 测试继承的方法
        assertEquals(1, defBottle.getId());
        assertEquals("Defense Potion", defBottle.getName());
        assertEquals(50, defBottle.getCapacity());
        assertEquals(10, defBottle.getCombatEffectiveness());
        assertEquals("DefBottle", defBottle.getBotType());
        assertEquals("DefBottle", defBottle.getType());
        assertFalse(defBottle.isEmpty());

        // 测试使用瓶子的方法
        defBottle.useBottle();
        assertTrue(defBottle.isEmpty());
    }

}