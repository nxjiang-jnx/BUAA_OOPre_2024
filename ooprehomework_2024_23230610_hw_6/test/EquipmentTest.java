import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class EquipmentTest {

    private Equipment equipment;

    @Before
    public void setUp() throws Exception {
        // 初始化一个装备实例
        equipment = new Equipment(1, "Warrior Sword", 100, 75, "Sword");
    }

    @Test
    public void testGetId() {
        assertEquals(1, equipment.getId());
    }

    @Test
    public void testGetName() {
        assertEquals("Warrior Sword", equipment.getName());
    }

    @Test
    public void testGetDurability() {
        assertEquals(100, equipment.getDurability());
    }

    @Test
    public void testIncreaseDurability() {
        equipment.increaseDurability();
        assertEquals(101, equipment.getDurability());
    }

    @Test
    public void testDecreaseDurability() {
        equipment.decreaseDurability();
        assertEquals(99, equipment.getDurability());
    }

    @Test
    public void testGetCombatEffectiveness() {
        assertEquals(75, equipment.getCombatEffectiveness());
    }

    @Test
    public void testGetEquType() {
        assertEquals("Sword", equipment.getEquType());
    }
}
