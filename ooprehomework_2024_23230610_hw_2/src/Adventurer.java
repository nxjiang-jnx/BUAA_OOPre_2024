/**
 * @author jhria
 * @since 2024/9/17 下午1:05
 */

import java.util.ArrayList;

public class Adventurer {
    //属性
    private int id; //冒险者ID
    private String name; //冒险者名字
    private ArrayList<Bottle> bottles;    //存放冒险者携带的所有药瓶的容器
    private ArrayList<Equipment> equipments;    //存放冒险者携带的所有装备的容器

    //构造方法
    public Adventurer(int id, String name) {
        this.id = id;
        this.name = name;
        this.bottles = new ArrayList<>();
        this.equipments = new ArrayList<>();
    }

    //方法
    //给冒险者增加一个药水瓶
    public void addBottle(Bottle bottle) {
        bottles.add(bottle);
    }

    //将已知ID的药瓶删除
    public Bottle removeBottle(int bottleId) {
        for (Bottle bottle : bottles) {
            if (bottle.getId() == bottleId) {
                bottles.remove(bottle);
                return bottle;
            }
        }
        return null;
    }

    //获取药瓶数量
    public int getBottleNum() {
        return bottles.size();
    }

    //给冒险者增加一个装备
    public void addEquipment(Equipment equipment) {
        equipments.add(equipment);
    }

    //给冒险者已知ID的装备提升一点耐久度
    public Equipment increaseEquipmentDurability(int equipmentId) {
        for (Equipment equipment : equipments) {
            if (equipment.getID() == equipmentId) {
                equipment.increaseDurability();
                return equipment;
            }
        }
        return null;
    }

    //将已知ID的装备删除
    public Equipment removeEquipment(int equipmentId) {
        for (Equipment equipment : equipments) {
            if (equipment.getID() == equipmentId) {
                equipments.remove(equipment);
                return equipment;
            }
        }
        return null;
    }

    //获取装备数量
    public int getEquipmentNum() {
        return equipments.size();
    }

}
