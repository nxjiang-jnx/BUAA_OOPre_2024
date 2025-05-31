/**
 * 体力恢复药水。若冒险者使用体力恢复药水，则冒险者增加了数值为 capacity 的体力。
 *
 * @author jhria
 * @since 2024/9/21 下午7:54
 */

public class HpBottle extends Bottle {
    public HpBottle(int id, String name, int capacity, int combatEffectiveness, String botType) {
        super(id, name, capacity, combatEffectiveness, botType);
    }
}
