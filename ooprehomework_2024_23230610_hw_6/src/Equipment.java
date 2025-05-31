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
    private String equType; //装备类型,分为Axe,Sword,Blade三种类型

    //构造方法
    public Equipment(int id, String name, int durability, int combatEffectiveness, String equType) {
        this.id = id;
        this.name = name;
        this.durability = durability;
        this.combatEffectiveness = combatEffectiveness;
        this.equType = equType;
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

    //降低装备耐久度，耐久度-1
    public void decreaseDurability() {
        this.durability -= 1;
    }

    //获取装备耐久度
    public int getDurability() {
        return durability;
    }

    //获取装备战斗力
    public int getCombatEffectiveness() {
        return combatEffectiveness;
    }

    //获取装备类型
    public String getEquType() {
        return equType;
    }
}
