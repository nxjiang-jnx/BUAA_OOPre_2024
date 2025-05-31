/**
 * @author jhria
 * @since 2024/9/17 下午1:05
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

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
    private ArrayList<Fragment> fragments = new ArrayList<>();  //定义冒险者拥有的碎片
    private HashMap<Adventurer, Integer> hiredAdventurers = new HashMap<>();//键是被雇佣的冒险者对象，值是他们的援助次数

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

    //删除冒险者的某个药水瓶或装备
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
                System.out.println(equipment.getEquType() + " " +
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

    //给冒险者设置体力
    public void setHitPoint(int hitPoint) {
        this.hitPoint = hitPoint;
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

    //setCombatEffectiveness
    public void setCombatEffectiveness(int combatEffectiveness) {
        this.combatEffectiveness = combatEffectiveness;
    }

    //获取冒险者拥有的所有药水瓶
    public ArrayList<Bottle> getBottles() {
        return bottles;
    }

    //获取冒险者拥有的所有装备
    public ArrayList<Equipment> getEquipments() {
        return equipments;
    }

    //获取冒险者拥有的所有碎片
    public ArrayList<Fragment> getFragments() {
        return fragments;
    }

    //getBackpack
    public HashMap<Integer, Integer> getBackpack() {
        return backpack;
    }

    //setDef
    public void setDef(int def) {
        this.def = def;
    }

    //setAtk
    public void setAtk(int atk) {
        this.atk = atk;
    }

    //获取冒险者所拥有的id装备的名字
    public String getEquipmentName(int equipmentId) {
        for (Equipment equipment : equipments) {
            if (equipment.getId() == equipmentId) {
                return equipment.getName();
            }
        }
        return null;
    }

    //获取冒险者所拥有的id药水瓶的名字
    public String getBottleName(int bottleId) {
        for (Bottle bottle : bottles) {
            if (bottle.getId() == bottleId) {
                return bottle.getName();
            }
        }
        return null;
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
                //冒险者最多只能携带 maxBottles 个名字相同的药水瓶
                int maxBottles = getCombatEffectiveness() / 5 + 1;
                //遍历背包，如果背包中名字等于itemId药水瓶名字的药水瓶个数小于maxBottles
                int count = 0;
                for (int findId : backpack.keySet()) {
                    if (this.getBottleName(findId) != null &&
                            this.getBottleName(findId).equals(bottle.getName())) {
                        count++;
                    }
                }
                if (count < maxBottles) {
                    backpack.put(itemId, 1);
                    return;
                }
            }
        }
        //遍历所有装备
        for (Equipment equipment : equipments) {
            if (equipment.getId() == itemId) {  //冒险者拥有该装备，可以添加至背包中
                //遍历背包，如果背包中装备的名字等于该equipment的名字（就是尝试携带的装备的名字）
                for (int findId : backpack.keySet()) {
                    if (this.getEquipmentName(findId) != null &&
                            this.getEquipmentName(findId).equals(equipment.getName())) {
                        //删除findId装备，添加equipment装备
                        backpack.remove(findId);
                        backpack.put(itemId, 1);
                        return;
                    }
                }
                backpack.put(itemId, 1);
                return;
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

    //给冒险者添加一个碎片
    public void addFragment(Fragment fragment) {
        fragments.add(fragment);
    }

    //冒险者尝试兑换一次福利
    public void exchangeWelfare(String name, int welfareId) {
        //如果冒险者拥有的名称均为name的碎片数量小于5个，则兑换失败
        int count = 0;
        for (Fragment fragment : fragments) {
            if (fragment.getName().equals(name)) {
                count++;
            }
        }
        if (count < 5) {
            System.out.println(count + ": Not enough fragments collected yet");
            return; //兑换失败
        }

        //否则，兑换成功，移除共5个名字为name的碎片
        count = 0;
        ArrayList<Fragment> fragmentsToRemove = new ArrayList<>();  //定义一个管理要删除的碎片的容器
        for (Fragment fragment : fragments) {
            if (fragment.getName().equals(name)) {
                count++;
                fragmentsToRemove.add(fragment);
                if (count == 5) {
                    break;
                }
            }
        }
        fragments.removeAll(fragmentsToRemove); // 删除所有符合条件的碎片

        //如果冒险者同时拥有 id 与当前welfare_id完全匹配的药水瓶
        for (Bottle bottle : bottles) {
            if (bottle.getId() == welfareId) {
                //该药水瓶为有效兑换对象。若此药水瓶当前为空，系统将立即为其注满
                if (bottle.isEmpty()) {
                    bottle.fillBottle();
                }
                //输出药水瓶的的name和药水瓶的容量
                System.out.println(bottle.getName() + " " + bottle.getCapacity());
                return;
            }
        }
        //如果冒险者同时拥有 id 与当前welfare_id完全相符的装备
        for (Equipment equipment : equipments) {
            if (equipment.getId() == welfareId) {
                //此装备为有效兑换对象，并即时提升其耐久度一点，即新耐久度=原耐久度+1
                equipment.increaseDurability();
                //输出装备的name和装备的新的耐久度
                System.out.println(equipment.getName() + " " + equipment.getDurability());
                return;
            }
        }
        //如果冒险者未拥有 id 与当前welfare_id相匹配的药水瓶或装备
        //冒险者得到一件全新的 HpBottle 体力恢复药水，其 ID 设定为welfare_id，名称与兑换所用碎片名称相同，初始容量为100，战斗力设定为0，且为充满状态
        HpBottle newBottle = new HpBottle(welfareId, name, 100, 0, "HpBottle");
        bottles.add(newBottle);
        System.out.println("Congratulations! HpBottle " + name + " acquired");
    }

    //搜索冒险者背包中携带的名字为equName的装备
    public Equipment searchBackpackEqu(String equName) {
        ArrayList<Equipment> findEqu = new ArrayList<>();

        //遍历equipments，将名字为name的装备全部添加到findEqu中
        for (Equipment equipment : equipments) {
            if (equipment.getName().equals(equName)) {
                findEqu.add(equipment);
            }
        }

        //遍历backpack，将背包中每一个物品的Id与findEqu中每一个装备的id比较
        for (int findId : backpack.keySet()) {
            for (Equipment equipment : findEqu) {
                if (findId == equipment.getId()) {
                    return equipment;
                }
            }
        }

        //若不存在该装备，返回null
        return null;
    }

    //从背包中移除equId装备
    public void removeBackpackEqu(int equId) {
        backpack.remove(equId);
    }

    //返回碎片个数
    public int getFragmentCount() {
        return fragments.size();
    }

    //雇佣一个冒险者
    public void hireAdventurer(Adventurer adventurer) {
        //如果尚未雇佣该冒险者，则加入集合，援助次数定义为0
        if (!hiredAdventurers.containsKey(adventurer)) {
            hiredAdventurers.put(adventurer, 0);
        }
    }

    //返回雇佣的所有冒险者
    public HashMap<Adventurer, Integer> getHiredAdventurers() {
        return hiredAdventurers;
    }

    //返回综合战力
    public int getComprehensiveCE() {
        //综合战力=战斗方自身、所携带的全部物品，以及雇佣的所有冒险者的战斗力总和
        int comprehensiveCE = 0;

        //冒险者自身
        comprehensiveCE += getCombatEffectiveness();

        //遍历背包，添加背包中所有物品的CombatEffectiveness
        for (int id : backpack.keySet()) {
            //背包中每一个物品的Id与findEqu中每一个装备的id比较
            for (Equipment equipment : equipments) {
                if (id == equipment.getId()) {
                    comprehensiveCE += equipment.getCombatEffectiveness();
                    break;
                }
            }
            //遍历药水瓶
            for (Bottle bottle : bottles) {
                if (id == bottle.getId()) {
                    comprehensiveCE += bottle.getCombatEffectiveness();
                    break;
                }
            }
        }

        //遍历雇佣冒险者，添加雇佣冒险者的战斗力
        for (Adventurer adventurer : hiredAdventurers.keySet()) {
            comprehensiveCE += adventurer.getCombatEffectiveness();
        }

        return comprehensiveCE;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Adventurer that = (Adventurer) obj;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}


