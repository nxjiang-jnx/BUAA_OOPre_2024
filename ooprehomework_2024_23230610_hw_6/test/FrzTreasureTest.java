import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class FrzTreasureTest {

    private FrzTreasure frzTreasure;
    private Adventurer adventurer;
    private Adventurer hiredAdventurer;

    @Before
    public void setUp() {
        frzTreasure = new FrzTreasure();
        adventurer = new Adventurer(1, "Main Adventurer");
        adventurer.setAtk(100);

        // 雇佣一个冒险者
        hiredAdventurer = new Adventurer(2, "Hired Adventurer");
        hiredAdventurer.setAtk(50);
        adventurer.hireAdventurer(hiredAdventurer);
    }

    @Test
    public void testShowInfo() {
        frzTreasure.showInfo();
        // 没有具体断言，因为仅打印输出
    }

    @Test
    public void testUseBy() {
        frzTreasure.useBy(adventurer);

        assertEquals(150, adventurer.getAtk());
        assertEquals(100, hiredAdventurer.getAtk());
    }
}
