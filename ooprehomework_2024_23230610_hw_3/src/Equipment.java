/**
 * @author jhria
 * @since 2024/9/17 下午1:01
 */

public class Equipment {
    //属性
    private int id; //装备Id
    private String name; //装备名称
    private int durability; //装备耐久度
    private int combatEffectiveness; //装备的战斗力

    //构造方法
    public Equipment(int id, String name, int durability, int combatEffectiveness) {
        this.id = id;
        this.name = name;
        this.durability = durability;
        this.combatEffectiveness = combatEffectiveness;
    }

    //方法
    //获取装备ID
    public int getId() {
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

    //获取装备战斗力
    public int getCombatEffectiveness() {
        return combatEffectiveness;
    }
}
