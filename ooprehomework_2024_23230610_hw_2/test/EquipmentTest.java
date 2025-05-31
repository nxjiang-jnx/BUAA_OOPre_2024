import static org.junit.Assert.*;
import org.junit.Test;

public class EquipmentTest {

    @Test
    public void getID() {
        //在0 - 2147483647之间进行测试
        for (int i = 0; i < 2147483647; i++) {
            Equipment equipment = new Equipment(i, "test", 0);
            assertEquals(i, equipment.getID());
        }
    }

    @Test
    public void getName() {
        //在0-40之间进行测试
        for (int i = 0; i < 40; i++) {
            Equipment equipment = new Equipment(0, "test", i);
            assertEquals("test", equipment.getName());
        }
    }

    @Test
    public void increaseDurability() {
        //在0-2147483647之间进行测试
        for (int i = 0; i < 2147483647; i++) {
            Equipment equipment = new Equipment(0, "test", i);
            equipment.increaseDurability();
            assertEquals(i + 1, equipment.getDurability());
        }
    }

    @Test
    public void getDurability() {
        //在0-2147483647之间进行测试
        for (int i = 0; i < 2147483647; i++) {
            Equipment equipment = new Equipment(0, "test", i);
            assertEquals(i, equipment.getDurability());
        }
    }
}