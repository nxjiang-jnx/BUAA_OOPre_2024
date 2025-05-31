import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class TreasureFactoryTest {

    private Guard shdGuard;
    private Guard flmGuard;
    private Guard stnGuard;
    private Guard wndGuard;
    private Guard frzGuard;

    @Before
    public void setUp() {
        // 手动创建不同类型的 Guard
        shdGuard = new ShdGuard();
        flmGuard = new FlmGuard();
        stnGuard = new StnGuard();
        wndGuard = new WndGuard();
        frzGuard = new FrzGuard();
    }

    @Test
    public void testCreateShdTreasure() {
        Treasure treasure = TreasureFactory.createTreasure(shdGuard);
        assertTrue(treasure instanceof ShdTreasure);
    }

    @Test
    public void testCreateFlmTreasure() {
        Treasure treasure = TreasureFactory.createTreasure(flmGuard);
        assertTrue(treasure instanceof FlmTreasure);
    }

    @Test
    public void testCreateStnTreasure() {
        Treasure treasure = TreasureFactory.createTreasure(stnGuard);
        assertTrue(treasure instanceof StnTreasure);
    }

    @Test
    public void testCreateWndTreasure() {
        Treasure treasure = TreasureFactory.createTreasure(wndGuard);
        assertTrue(treasure instanceof WndTreasure);
    }

    @Test
    public void testCreateFrzTreasure() {
        Treasure treasure = TreasureFactory.createTreasure(frzGuard);
        assertTrue(treasure instanceof FrzTreasure);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateTreasureWithUnknownGuard() {
        Guard unknownGuard = new UnknownGuard();
        TreasureFactory.createTreasure(unknownGuard);
    }

    // 手动创建不同类型的 Guard 实现类
    class ShdGuard implements Guard {
        @Override
        public boolean fight(Adventurer adv) {
            return false;
        }

        @Override
        public String getType() {
            return "Shd";
        }
    }

    class FlmGuard implements Guard {
        @Override
        public boolean fight(Adventurer adv) {
            return false;
        }

        @Override
        public String getType() {
            return "Flm";
        }
    }

    class StnGuard implements Guard {
        @Override
        public boolean fight(Adventurer adv) {
            return false;
        }

        @Override
        public String getType() {
            return "Stn";
        }
    }

    class WndGuard implements Guard {
        @Override
        public boolean fight(Adventurer adv) {
            return false;
        }

        @Override
        public String getType() {
            return "Wnd";
        }
    }

    class FrzGuard implements Guard {
        @Override
        public boolean fight(Adventurer adv) {
            return false;
        }

        @Override
        public String getType() {
            return "Frz";
        }
    }

    class UnknownGuard implements Guard {
        @Override
        public boolean fight(Adventurer adv) {
            return false;
        }

        @Override
        public String getType() {
            return "Unknown";
        }
    }
}
