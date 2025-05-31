/**
 * @author jhria
 * @since 2024/9/17 下午12:33
 */

public class Bottle {
    //属性
    private int id;     //瓶子ID
    private String name;//瓶子名字
    private int capacity;//瓶子容量

    //构造方法
    public Bottle(int id, String name, int capacity) {
        this.id = id;
        this.name = name;
        this.capacity = capacity;
    }

    //方法
    //获取ID
    public int getId() {
        return id;
    }

    //获取名字
    public String getName() {
        return name;
    }

    //获取容量
    public int getCapacity() {
        return capacity;
    }

}
