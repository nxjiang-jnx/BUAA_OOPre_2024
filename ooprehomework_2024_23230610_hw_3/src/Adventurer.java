/**
 * @author jhria
 * @since 2024/9/17 下午1:05
 */

import java.util.ArrayList;
import java.util.HashMap;

public class Adventurer {
    //属性
    private int id; //冒险者ID
    private String name; //冒险者名字
    private int hitPoint; //冒险者体力
    private int atk; //冒险者攻击力
    private int def; //冒险者防御力
    private int combatEffectiveness = atk + def;  //冒险者的战斗力为其攻击力与防御力之和
    private ArrayList<Bottle> bottles;    //存放冒险者拥有的所有药瓶的容器
    private ArrayList<Equipment> equipments;    //存放冒险者拥有的所有装备的容器
    private HashMap<Integer, Integer> backpack = new HashMap<>();   //定义冒险者的背包，可以存放药水瓶或装备的id，表示携带

    //构造方法
    public Adventurer(int id, String name) {
        this.id = id;
        this.name = name;
        this.hitPoint = 500;
        this.atk = 1;
        this.def = 0;
        this.bottles = new ArrayList<>();
        this.equipments = new ArrayList<>();
    }

    //方法

    //给冒险者增加一个药水瓶
    public void addBottle(Bottle bottle) {
        //根据药水瓶botType，分入继承的子类中
        if (bottle.getBotType().equals("HpBottle")) {
            Bottle hpBottle = new HpBottle(bottle.getId(), bottle.getName(), bottle.getCapacity(),
                    bottle.getCombatEffectiveness(), bottle.getType());
            bottles.add(hpBottle);
        } else if (bottle.getBotType().equals("AtkBottle")) {
            Bottle atkBottle = new AtkBottle(bottle.getId(), bottle.getName(), bottle.getCapacity(),
                    bottle.getCombatEffectiveness(), bottle.getType());
            bottles.add(atkBottle);
        } else if (bottle.getBotType().equals("DefBottle")) {
            Bottle defBottle = new DefBottle(bottle.getId(), bottle.getName(), bottle.getCapacity(),
                    bottle.getCombatEffectiveness(), bottle.getType());
            bottles.add(defBottle);
        }
    }

    ////删除冒险者的某个药水瓶或装备
    public void removeItem(int itemId) {
        //搜素冒险者所有物品的id来判断删除的是药水瓶还是装备
        for (Bottle bottle : bottles) {
            if (bottle.getId() == itemId) {
                //输出瓶子类型、名称、容量
                System.out.println(bottle.getType() + " " +
                        bottle.getName() + " " + bottle.getCapacity());
                bottles.remove(bottle);
                return;
            }
        }
        for (Equipment equipment : equipments) {
            if (equipment.getId() == itemId) {
                //输出装备类型、名称、耐久度
                System.out.println("Equipment " +
                        equipment.getName() + " " + equipment.getDurability());
                equipments.remove(equipment);
                return;
            }
        }
    }

    //获取药瓶数量
    public int getBottleNum() {
        return bottles.size();
    }

    //获取所有药水瓶
    public ArrayList<Bottle> getAllBottles() {
        return bottles;
    }

    //给冒险者增加一个装备
    public void addEquipment(Equipment equipment) {
        equipments.add(equipment);
    }

    //给冒险者已知ID的装备提升一点耐久度
    public Equipment increaseEquipmentDurability(int equipmentId) {
        for (Equipment equipment : equipments) {
            if (equipment.getId() == equipmentId) {
                equipment.increaseDurability();
                return equipment;
            }
        }
        return null;
    }

    //将已知ID的装备删除
    public Equipment removeEquipment(int equipmentId) {
        for (Equipment equipment : equipments) {
            if (equipment.getId() == equipmentId) {
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

    //获取所有装备
    public ArrayList<Equipment> getAllEquipments() {
        return equipments;
    }

    //获取冒险者体力
    public int getHitPoint() {
        return hitPoint;
    }

    //获取冒险者Id
    public int getId() {
        return id;
    }

    //获取冒险者名称
    public String getName() {
        return name;
    }

    //获取冒险者攻击力
    public int getAtk() {
        return atk;
    }

    //获取冒险者防御力
    public int getDef() {
        return def;
    }

    //获取冒险者战斗力
    public int getCombatEffectiveness() {
        return atk + def;
    }

    //在背包中携带冒险者已拥有的某个物品（物品指药水瓶或装备）
    public void carryItem(int itemId) {
        //遍历背包中的id，若该物品已经携带，则退出
        for (int findId : backpack.keySet()) {
            if (findId == itemId) {
                return;
            }
        }
        //遍历所有药水
        for (Bottle bottle : bottles) {
            if (bottle.getId() == itemId) {
                backpack.put(itemId, 1);
            }
        }
        //遍历所有装备
        for (Equipment equipment : equipments) {
            if (equipment.getId() == itemId) {
                backpack.put(itemId, 1);
            }
        }
    }

    //获取背包大小
    public int getBackpackSize() {
        return backpack.size();
    }

    //使用拥有的id药水瓶
    public void useBottle(int bottleId) {
        //遍历背包，未携带则使用失败
        if (!backpack.containsKey(bottleId)) {
            //遍历药水瓶，获取该药水瓶名称
            for (Bottle bottle : bottles) {
                if (bottle.getId() == bottleId) {
                    System.out.println(name + " fail to use " + bottle.getName());
                    return;
                }
            }
        }
        //遍历药水瓶，使用药瓶
        Bottle bottleToRemove = null;
        for (Bottle bottle : bottles) {
            if (bottle.getId() == bottleId) {
                //以下情况均视为使用成功
                if (bottle.isEmpty()) {
                    //如果该药水瓶已空，则从背包和药水瓶集合中丢弃
                    backpack.remove(bottleId);
                    bottleToRemove = bottle; // 记录需要删除的瓶子，防止ConcurrentModificationException错误
                } else {
                    //若冒险者药水类型为HpBottle，则冒险者增加了数值为 capacity 的体力
                    if (bottle instanceof HpBottle) {
                        hitPoint += bottle.getCapacity();
                    }
                    //若冒险者药水类型为AtkBottle，则冒险者增加了数值为 药水瓶的combatEffectiveness + capacity / 100 的攻击力
                    if (bottle instanceof AtkBottle) {
                        atk += bottle.getCombatEffectiveness() + bottle.getCapacity() / 100;
                    }
                    //若冒险者药水类型为DefBottle，则冒险者增加了数值为 药水瓶的combatEffectiveness + capacity / 100 的防御力
                    if (bottle instanceof DefBottle) {
                        def += bottle.getCombatEffectiveness() + bottle.getCapacity() / 100;
                    }
                    //使用该药水瓶，且药水瓶容量清空
                    bottle.useBottle();
                }
                System.out.println(name + " " + hitPoint + " " + atk + " " + def);
            }
        }
        if (bottleToRemove != null) {
            bottles.remove(bottleToRemove); // 在遍历结束后删除
        }
    }
}
