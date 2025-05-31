import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ShdTest {

    private Shd shd;
    private Adventurer strongAdventurer;
    private Adventurer weakAdventurer;

    @Before
    public void setUp() {
        shd = new Shd();

        // 构造强大的冒险者，战斗力超过1000
        strongAdventurer = new Adventurer(1, "Strong Adventurer");
        strongAdventurer.setAtk(800);
        strongAdventurer.setDef(500);

        // 构造弱小的冒险者，战斗力低于1000
        weakAdventurer = new Adventurer(2, "Weak Adventurer");
        weakAdventurer.setAtk(400);
        weakAdventurer.setDef(500);
    }

    @Test
    public void testFight_StrongAdventurer_ReturnsTrue() {
        assertTrue(shd.fight(strongAdventurer));
    }

    @Test
    public void testFight_WeakAdventurer_ReturnsFalse() {
        assertFalse(shd.fight(weakAdventurer));
    }

    @Test
    public void testGetType_ReturnsShd() {
        assertEquals("Shd", shd.getType());
    }
}
