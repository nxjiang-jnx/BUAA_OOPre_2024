import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class WndTest {

    private Wnd wnd;
    private Adventurer strongAdventurer;
    private Adventurer weakAdventurer;

    @Before
    public void setUp() {
        wnd = new Wnd();

        // 构造强大的冒险者，战斗力超过4000
        strongAdventurer = new Adventurer(1, "Strong Adventurer");
        strongAdventurer.setAtk(2500);
        strongAdventurer.setDef(2000);

        // 构造弱小的冒险者，战斗力低于4000
        weakAdventurer = new Adventurer(2, "Weak Adventurer");
        weakAdventurer.setAtk(1500);
        weakAdventurer.setDef(2000);
    }

    @Test
    public void testFight_StrongAdventurer_ReturnsTrue() {
        assertTrue(wnd.fight(strongAdventurer));
    }

    @Test
    public void testFight_WeakAdventurer_ReturnsFalse() {
        assertFalse(wnd.fight(weakAdventurer));
    }

    @Test
    public void testGetType_ReturnsWnd() {
        assertEquals("Wnd", wnd.getType());
    }
}
