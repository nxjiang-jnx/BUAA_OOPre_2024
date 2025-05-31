import static org.junit.Assert.*;
import org.junit.Test;

public class HpBottleTest {
    @Test
    public void testHpBottleConstructorAndMethods() {
        // 实例化 AtkBottle
        HpBottle hpBottle = new HpBottle(1, "Hp Potion", 50, 10, "HpBottle");

        // 测试对象是否成功创建
        assertNotNull(hpBottle);

        // 测试继承的方法
        assertEquals(1, hpBottle.getId());
        assertEquals("Hp Potion", hpBottle.getName());
        assertEquals(50, hpBottle.getCapacity());
        assertEquals(10, hpBottle.getCombatEffectiveness());
        assertEquals("HpBottle", hpBottle.getBotType());
        assertEquals("HpBottle", hpBottle.getType());
        assertFalse(hpBottle.isEmpty());

        // 测试使用瓶子的方法
        hpBottle.useBottle();
        assertTrue(hpBottle.isEmpty());
    }
}