/**
 * @author jhria
 * @since 2024/9/17 下午12:33
 */

public class Bottle {
    //属性0
    private int id;     //瓶子ID
    private String name;//瓶子名字
    private int capacity;//瓶子容量
    private int combatEffectiveness; //瓶子的战斗力
    private String botType;  //瓶子种类
    private boolean isUsed; //瓶子是否被用过

    //构造方法
    public Bottle(int id, String name, int capacity, int combatEffectiveness, String botType) {
        this.id = id;
        this.name = name;
        this.capacity = capacity;
        this.combatEffectiveness = combatEffectiveness;
        this.botType = botType;
        this.isUsed = false;    //初始化时，瓶子默认尚未用过
    }

    //方法
    //获取ID
    public int getId() {
        return id;
    }

    //获取名字
    public String getName() {
        return name;
    }0

    //获取容量
    public int getCapacity() {
        return capacity;
    }

    //获取类型
    public String getBotType() {
        return botType;
    }

    //获取种类
    public String getType() {
        return botType;
    }

    //获取战斗力
    public int getCombatEffectiveness() {
        return combatEffectiveness;
    }

    //判断药水瓶是否为空
    public boolean isEmpty() {
        return isUsed;
    }

    //使用药水，标记为已使用且清空，注意！但容量仍然不变，只是液体空了
    public void useBottle() {
        isUsed = true;
    }
}
