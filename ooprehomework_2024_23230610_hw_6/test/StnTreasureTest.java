import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class StnTreasureTest {

    private StnTreasure stnTreasure;
    private Adventurer adventurer;
    private Adventurer hiredAdventurer;

    @Before
    public void setUp() {
        stnTreasure = new StnTreasure();
        adventurer = new Adventurer(1, "Main Adventurer");
        adventurer.setDef(100);

        // 雇佣一个冒险者
        hiredAdventurer = new Adventurer(2, "Hired Adventurer");
        hiredAdventurer.setDef(50);
        adventurer.hireAdventurer(hiredAdventurer);
    }

    @Test
    public void testShowInfo() {
        stnTreasure.showInfo();
        // 没有具体断言，因为仅打印输出
    }

    @Test
    public void testUseBy() {
        stnTreasure.useBy(adventurer);

        assertEquals(140, adventurer.getDef());
        assertEquals(90, hiredAdventurer.getDef());
    }
}
