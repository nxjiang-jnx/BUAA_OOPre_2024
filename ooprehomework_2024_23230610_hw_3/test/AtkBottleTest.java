import static org.junit.Assert.*;
import org.junit.Test;

public class AtkBottleTest {
    @Test
    public void testAtkBottleConstructorAndMethods() {
        // 实例化 AtkBottle
        AtkBottle atkBottle = new AtkBottle(1, "Attack Potion", 50, 10, "AtkBottle");

        // 测试对象是否成功创建
        assertNotNull(atkBottle);

        // 测试继承的方法
        assertEquals(1, atkBottle.getId());
        assertEquals("Attack Potion", atkBottle.getName());
        assertEquals(50, atkBottle.getCapacity());
        assertEquals(10, atkBottle.getCombatEffectiveness());
        assertEquals("AtkBottle", atkBottle.getBotType());
        assertEquals("AtkBottle", atkBottle.getType());
        assertFalse(atkBottle.isEmpty());

        // 测试使用瓶子的方法
        atkBottle.useBottle();
        assertTrue(atkBottle.isEmpty());
    }
}