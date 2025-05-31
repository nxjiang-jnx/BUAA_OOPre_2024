import org.junit.Test;

import static org.junit.Assert.*;

public class EquipmentTest {
    @Test
    public void testConstructorAndGetters() {
        int id = 1;
        String name = "Sword of Destiny";
        int durability = 100;
        int combatEffectiveness = 80;

        Equipment equipment = new Equipment(id, name, durability, combatEffectiveness);

        // 测试属性是否正确初始化
        assertEquals(id, equipment.getId());
        assertEquals(name, equipment.getName());
        assertEquals(durability, equipment.getDurability());

        // 注意：原类中没有提供 getCombatEffectiveness 方法
        // 如果添加了该方法，则可以进行以下测试
        assertEquals(combatEffectiveness, equipment.getCombatEffectiveness());
    }

    @Test
    public void testIncreaseDurability() {
        Equipment equipment = new Equipment(2, "Shield of Valor", 50, 60);

        int initialDurability = equipment.getDurability();

        // 提升耐久度
        equipment.increaseDurability();

        // 验证耐久度是否增加了1
        assertEquals(initialDurability + 1, equipment.getDurability());
    }
}