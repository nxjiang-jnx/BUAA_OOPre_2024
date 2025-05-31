import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class FrzTest {

    private Frz frz;
    private Adventurer strongAdventurer;
    private Adventurer weakAdventurer;

    @Before
    public void setUp() {
        frz = new Frz();

        // 构造强大的冒险者，战斗力超过5000
        strongAdventurer = new Adventurer(1, "Strong Adventurer");
        strongAdventurer.setAtk(3000);
        strongAdventurer.setDef(2500);

        // 构造弱小的冒险者，战斗力低于5000
        weakAdventurer = new Adventurer(2, "Weak Adventurer");
        weakAdventurer.setAtk(2000);
        weakAdventurer.setDef(2000);
    }

    @Test
    public void testFight_StrongAdventurer_ReturnsTrue() {
        assertTrue(frz.fight(strongAdventurer));
    }

    @Test
    public void testFight_WeakAdventurer_ReturnsFalse() {
        assertFalse(frz.fight(weakAdventurer));
    }

    @Test
    public void testGetType_ReturnsFrz() {
        assertEquals("Frz", frz.getType());
    }
}
