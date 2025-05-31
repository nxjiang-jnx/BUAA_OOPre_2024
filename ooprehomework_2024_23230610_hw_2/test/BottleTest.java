import org.junit.Test;

import static org.junit.Assert.*;

public class BottleTest {

    @Test
    public void getId() {
        //对0-2147483647进行测试
        for (int i = 0; i < 2147483647; i++) {
            Bottle bottle = new Bottle(i, "test", 100);
            assertEquals(i, bottle.getId());
        }
    }

    @Test
    public void getName() {
        ////在0-40之间进行测试
        for (int i = 0; i < 40; i++) {
            Bottle bottle = new Bottle(0, "test" + i, 100);
            assertEquals("test" + i, bottle.getName());
        }
    }

    @Test
    public void getCapacity() {
        //对0-2147483647进行测试
        for (int i = 0; i < 2147483647; i++) {
            Bottle bottle = new Bottle(0, "test", i);
            assertEquals(i, bottle.getCapacity());
        }
    }
}