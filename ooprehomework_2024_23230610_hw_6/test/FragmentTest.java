import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class FragmentTest {

    private Fragment fragment;

    @Before
    public void setUp() throws Exception {
        // 初始化 Fragment 实例
        fragment = new Fragment(1, "Mystic Fragment");
    }

    @Test
    public void testGetId() {
        // 测试获取碎片ID的方法
        assertEquals(1, fragment.getId());
    }

    @Test
    public void testGetName() {
        // 测试获取碎片名称的方法
        assertEquals("Mystic Fragment", fragment.getName());
    }
}
