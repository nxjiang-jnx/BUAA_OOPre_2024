/**
 * @author jhria
 * @since 2024/9/17 下午1:01
 */

public class Equipment {
    //属性
    private int id; //装备Id
    private String name; //装备名称
    private int durability; //装备耐久度

    //构造方法
    public Equipment(int id, String name, int durability) {
        this.id = id;
        this.name = name;
        this.durability = durability;
    }

    //方法
    //获取装备ID
    public int getID() {
        return id;
    }

    //获取装备名称
    public String getName() {
        return name;
    }

    //提升装备耐久度，耐久度+1
    public void increaseDurability() {
        this.durability += 1;
    }

    //获取装备耐久度
    public int getDurability() {
        return durability;
    }

}
