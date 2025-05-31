import org.junit.Test;

import static org.junit.Assert.*;

public class BottleTest {
    @Test
    public void testConstructorAndGetters() {
        int id = 1;
        String name = "Healing Potion";
        int capacity = 100;
        int combatEffectiveness = 50;
        String botType = "Health";

        Bottle bottle = new Bottle(id, name, capacity, combatEffectiveness, botType);

        // 测试属性是否正确初始化
        assertEquals(id, bottle.getId());
        assertEquals(name, bottle.getName());
        assertEquals(capacity, bottle.getCapacity());
        assertEquals(combatEffectiveness, bottle.getCombatEffectiveness());
        assertEquals(botType, bottle.getBotType());
        assertEquals(botType, bottle.getType()); // 验证 getType 方法
        assertFalse(bottle.isEmpty()); // 初始状态应为未使用
    }

    @Test
    public void testUseBottle() {
        Bottle bottle = new Bottle(2, "Mana Potion", 150, 30, "Magic");

        // 使用前应为未使用状态
        assertFalse(bottle.isEmpty());

        // 使用瓶子
        bottle.useBottle();

        // 使用后应为已使用状态
        assertTrue(bottle.isEmpty());

        // 确认容量未改变
        assertEquals(150, bottle.getCapacity());
    }
}