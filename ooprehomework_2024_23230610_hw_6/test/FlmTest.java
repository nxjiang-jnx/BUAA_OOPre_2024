import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class FlmTest {

    private Flm flm;
    private Adventurer strongAdventurer;
    private Adventurer weakAdventurer;

    @Before
    public void setUp() {
        flm = new Flm();

        // 构造强大的冒险者，战斗力超过2000
        strongAdventurer = new Adventurer(1, "Strong Adventurer");
        strongAdventurer.setAtk(1800);
        strongAdventurer.setDef(500);

        // 构造弱小的冒险者，战斗力低于2000
        weakAdventurer = new Adventurer(2, "Weak Adventurer");
        weakAdventurer.setAtk(1000);
        weakAdventurer.setDef(500);
    }

    @Test
    public void testFight_StrongAdventurer_ReturnsTrue() {
        // 测试强大的冒险者战斗力大于2000
        assertTrue(flm.fight(strongAdventurer));
    }

    @Test
    public void testFight_WeakAdventurer_ReturnsFalse() {
        // 测试弱小的冒险者战斗力小于等于2000
        assertFalse(flm.fight(weakAdventurer));
    }

    @Test
    public void testGetType_ReturnsFlm() {
        // 测试返回类型是否为"Flm"
        assertEquals("Flm", flm.getType());
    }
}
