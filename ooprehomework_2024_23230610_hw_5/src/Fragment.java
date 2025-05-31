/**
 * 碎片
 *
 * @author jhria
 * @since 2024/10/12 下午7:17
 */

public class Fragment {
    private int id; //碎片Id
    private String name; //碎片名称

    //构造方法
    public Fragment(int id, String name) {
        this.id = id;
        this.name = name;
    }

    //方法
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
