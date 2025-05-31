import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class StnTest {

    private Stn stn;
    private Adventurer strongAdventurer;
    private Adventurer weakAdventurer;

    @Before
    public void setUp() {
        stn = new Stn();

        // 构造强大的冒险者，战斗力超过3000
        strongAdventurer = new Adventurer(1, "Strong Adventurer");
        strongAdventurer.setAtk(2000);
        strongAdventurer.setDef(1500);

        // 构造弱小的冒险者，战斗力低于3000
        weakAdventurer = new Adventurer(2, "Weak Adventurer");
        weakAdventurer.setAtk(1000);
        weakAdventurer.setDef(1500);
    }

    @Test
    public void testFight_StrongAdventurer_ReturnsTrue() {
        assertTrue(stn.fight(strongAdventurer));
    }

    @Test
    public void testFight_WeakAdventurer_ReturnsFalse() {
        assertFalse(stn.fight(weakAdventurer));
    }

    @Test
    public void testGetType_ReturnsStn() {
        assertEquals("Stn", stn.getType());
    }
}
