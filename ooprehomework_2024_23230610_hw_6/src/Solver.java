import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Solver {
    public Solver(Scanner scanner) {
        this.scanner = scanner;
    }

    private Scanner scanner;
    // 解析后的输入将会存进该容器中, 类似于C语言的二维数组
    private static ArrayList<ArrayList<String>> inputInfo = new ArrayList<>();
    // 存储冒险者
    private static Map<Integer, Adventurer> adventurers = new HashMap<>();

    //加入冒险者
    public static void addAdventurer(int advID, String advName) {
        Adventurer adv = new Adventurer(advID, advName);
        adventurers.put(advID, adv);
    }

    //给ID冒险者增加一个药水瓶
    public static void addBottle(int advID, int botID, String botName,
                                 int capacity, String botType, int botCombatEffectiveness) {
        Bottle bottle = new Bottle(botID, botName, capacity, botCombatEffectiveness, botType);
        adventurers.get(advID).addBottle(bottle);
    }

    //给ID冒险者增加一个装备
    public static void addEquipment(int advID, int equID, String equName,
                                    int durability, String equType, int equCombatEffectiveness) {
        Equipment equipment = new Equipment(equID, equName,
                durability, equCombatEffectiveness, equType);
        adventurers.get(advID).addEquipment(equipment);
    }

    //给ID冒险者的equID装备提升一点耐久度
    public static void increaseDurability(int advID, int equID) {
        Equipment eq = adventurers.get(advID).increaseEquipmentDurability(equID);
        if (eq != null) {
            System.out.println(eq.getName() + " " + eq.getDurability());
        }
    }

    //ID冒险者添加一个一个 id 、名字分别为{frag_id}、{name}的碎片
    public static void addFrag(int advID, int fragID, String fragName) {
        Fragment fragment = new Fragment(fragID, fragName);
        adventurers.get(advID).addFragment(fragment);
    }

    //ID 为 {adv_id} 的冒险者尝试使用名字为 {name}的装备与k个冒险者进行一次战斗类型为{attack_type}的战斗战斗
    // （k个冒险者的 ID 分别为 adv_id_1、adv_id_2、…、adv_id_k）
    public static void fight(int advID, String equName2, String type,
                             int k, ArrayList<Integer> advIds) {
        if (type.equals("normal")) {
            normalFight(advID, equName2, k, advIds);
        } else {
            chainFight(advID, equName2, k, advIds);
        }
    }

    // 处理普通战斗类型
    public static void normalFight(int advID, String equName2, int k, ArrayList<Integer> advIds) {
        // 记录所有受攻击者的初始体力
        ArrayList<Integer> hitPoints = recordDefendersHitPoints(k, advIds);

        // 计算受攻击方的整体防御能力
        int totalDefense = calculateTotalDefense(k, advIds);

        // 检查攻击者是否携带指定装备
        Equipment eq2 = adventurers.get(advID).searchBackpackEqu(equName2);
        if (eq2 == null) {
            System.out.println("Adventurer " + advID + " defeated");
            return;
        }

        // 计算攻击方的整体攻击力
        int totalAttack = calculateTotalAttack(advID, eq2);

        // 判断战斗结果
        if (totalDefense >= totalAttack) {
            System.out.println("Adventurer " + advID + " defeated");
            return;
        } else {
            // 战斗成功，处理伤害
            applyDamageToDefendersNormal(k, advID, eq2, advIds);
        }

        // 处理援助机制
        handleAidMechanism(k, advIds, hitPoints);

        // 输出受攻击者的状态
        outputDefendersStatus(k, advIds);

        // 更新攻击者的装备
        updateAttackerEquipment(advID, eq2);
    }

    // 处理连环递归攻击类型
    public static void chainFight(int advID, String equName2, int k, ArrayList<Integer> advIds) {
        // 收集所有受攻击者的ID
        LinkedHashSet<Integer> allDefenderIds = collectAllDefenders(k, advIds);

        // 计算受攻击方的整体防御能力
        int totalDefense = calculateTotalDefenseChain(allDefenderIds);

        // 检查攻击者是否携带指定装备
        Equipment eq2 = adventurers.get(advID).searchBackpackEqu(equName2);
        if (eq2 == null) {
            System.out.println("Adventurer " + advID + " defeated");
            return;
        }

        // 计算攻击方的整体攻击力
        int totalAttack = calculateTotalAttack(advID, eq2);

        // 判断战斗结果
        if (totalDefense >= totalAttack) {
            System.out.println("Adventurer " + advID + " defeated");
            return;
        } else {
            // 战斗成功，处理伤害
            int totalLostHitPoint = applyDamageToDefendersChain(advID, eq2, allDefenderIds);

            // 更新攻击者的装备
            updateAttackerEquipment(advID, eq2);

            // 输出本次战斗中所有冒险者失去的体力值的总和
            System.out.println(totalLostHitPoint);
        }
    }

    // 记录所有受攻击者的初始体力
    public static ArrayList<Integer> recordDefendersHitPoints(int k, ArrayList<Integer> advIds) {
        ArrayList<Integer> hitPoints = new ArrayList<>();
        for (int i = 0; i < k; i++) {
            hitPoints.add(adventurers.get(advIds.get(i)).getHitPoint());
        }
        return hitPoints;
    }

    // 计算受攻击方的整体防御能力（普通战斗）
    public static int calculateTotalDefense(int k, ArrayList<Integer> advIds) {
        int totalDefense = 0;
        for (int i = 0; i < k; i++) {
            int def = adventurers.get(advIds.get(i)).getDef();
            if (def > totalDefense) {
                totalDefense = def;
            }
        }
        return totalDefense;
    }

    // 计算攻击方的整体攻击力
    public static int calculateTotalAttack(int advID, Equipment eq2) {
        return adventurers.get(advID).getAtk() + eq2.getCombatEffectiveness();
    }

    // 对受攻击者应用伤害（普通战斗）
    public static void applyDamageToDefendersNormal(int k, int advID,
                                                    Equipment eq2, ArrayList<Integer> advIds) {
        String equType = eq2.getEquType();
        for (int i = 0; i < k; i++) {
            Adventurer defender = adventurers.get(advIds.get(i));
            int newHitPoint = calculateNewHitPoint(defender, advID, eq2, equType);
            defender.setHitPoint(newHitPoint);
        }
    }

    // 计算新的体力值（根据装备类型）
    public static int calculateNewHitPoint(Adventurer defender,
                                           int advID, Equipment eq2, String equType) {
        int newHitPoint = defender.getHitPoint();
        if (equType.equals("Axe")) {
            newHitPoint = defender.getHitPoint() / 10;
        } else if (equType.equals("Sword")) {
            int hitPointDecrease = eq2.getCombatEffectiveness() +
                    adventurers.get(advID).getAtk() - defender.getDef();
            newHitPoint = defender.getHitPoint() - hitPointDecrease;
        } else if (equType.equals("Blade")) {
            int hitPointDecrease = eq2.getCombatEffectiveness() + adventurers.get(advID).getAtk();
            newHitPoint = defender.getHitPoint() - hitPointDecrease;
        }
        return newHitPoint;
    }

    // 处理援助机制
    public static void handleAidMechanism(int k, ArrayList<Integer>
            advIds, ArrayList<Integer> hitPoints) {
        for (int i = 0; i < k; i++) {
            Adventurer defender = adventurers.get(advIds.get(i));
            int initialHitPoint = hitPoints.get(i);
            if (defender.getHitPoint() <= initialHitPoint / 2) {
                // 处理单个受攻击者的援助机制
                HashMap<Adventurer, Integer> hiredAdventurers = defender.getHiredAdventurers();
                List<Adventurer> adventurersToUnhire = new ArrayList<>();
                for (Map.Entry<Adventurer, Integer> entry : hiredAdventurers.entrySet()) {
                    Adventurer hiredAdventurer = entry.getKey();
                    int aidTimes = entry.getValue();

                    // 转移装备
                    transferEquipment(hiredAdventurer, defender);

                    // 更新援助次数
                    aidTimes += 1;
                    hiredAdventurers.put(hiredAdventurer, aidTimes);

                    // 检查是否需要解除雇佣关系
                    if (aidTimes > 3) {
                        adventurersToUnhire.add(hiredAdventurer);
                    }
                }

                // 解除雇佣关系
                for (Adventurer adventurerToUnhire : adventurersToUnhire) {
                    defender.getHiredAdventurers().remove(adventurerToUnhire);
                }
            }
        }
    }

    // 转移装备
    public static void transferEquipment(Adventurer hiredAdventurer, Adventurer defender) {
        HashMap<Integer, Integer> hiredBackpack = hiredAdventurer.getBackpack();
        List<Integer> equipmentIdsToRemoveFromBackpack = new ArrayList<>();
        List<Equipment> equipmentsToRemove = new ArrayList<>();
        List<Equipment> equipmentsToAddToDefender = new ArrayList<>();

        for (Integer itemId : hiredBackpack.keySet()) {
            Equipment equipment = getEquipmentById(hiredAdventurer, itemId);
            if (equipment != null) {
                equipmentIdsToRemoveFromBackpack.add(itemId);
                equipmentsToRemove.add(equipment);
                equipmentsToAddToDefender.add(equipment);
            }
        }

        // 从被雇佣冒险者处移除装备
        for (Integer itemId : equipmentIdsToRemoveFromBackpack) {
            hiredAdventurer.getBackpack().remove(itemId);
        }
        hiredAdventurer.getEquipments().removeAll(equipmentsToRemove);

        // 将装备添加到受攻击者的装备列表中
        defender.getEquipments().addAll(equipmentsToAddToDefender);
    }

    // 根据ID获取装备
    public static Equipment getEquipmentById(Adventurer adventurer, int itemId) {
        for (Equipment eq : adventurer.getEquipments()) {
            if (eq.getId() == itemId) {
                return eq;
            }
        }
        return null;
    }

    // 输出受攻击者的状态
    public static void outputDefendersStatus(int k, ArrayList<Integer> advIds) {
        for (int i = 0; i < k; i++) {
            Adventurer defender = adventurers.get(advIds.get(i));
            System.out.println(defender.getName() + " " + defender.getHitPoint());
        }
    }

    // 更新攻击者的装备
    public static void updateAttackerEquipment(int advID, Equipment eq2) {
        eq2.decreaseDurability();
        if (eq2.getDurability() <= 0) {
            adventurers.get(advID).removeBackpackEqu(eq2.getId());
            adventurers.get(advID).removeEquipment(eq2.getId());
        }
    }

    // 收集所有受攻击者的ID（连环递归攻击）
    public static LinkedHashSet<Integer> collectAllDefenders(int k, ArrayList<Integer> advIds) {
        LinkedHashSet<Integer> allDefenderIds = new LinkedHashSet<>();
        for (int i = 0; i < k; i++) {
            int defenderId = advIds.get(i);
            collectDefendersRecursive(defenderId, allDefenderIds, 1);
        }
        allDefenderIds = new LinkedHashSet<>(allDefenderIds);   //去重，保证每个数字仅出现1次
        return allDefenderIds;
    }

    // 递归收集受攻击者的ID
    public static void collectDefendersRecursive(int defenderId,
                                                 LinkedHashSet<Integer> allDefenderIds, int depth) {
        if (depth > 5) {
            return;
        }
        allDefenderIds.add(defenderId);
        Adventurer defender = adventurers.get(defenderId);
        HashMap<Adventurer, Integer> hiredAdventurers = defender.getHiredAdventurers();
        for (Adventurer hiredAdventurer : hiredAdventurers.keySet()) {
            collectDefendersRecursive(hiredAdventurer.getId(), allDefenderIds, depth + 1);
        }
    }

    // 计算受攻击方的整体防御能力（连环递归攻击）
    public static int calculateTotalDefenseChain(LinkedHashSet<Integer> allDefenderIds) {
        int totalDefense = 0;
        for (int defenderId : allDefenderIds) {
            int def = adventurers.get(defenderId).getDef();
            if (def > totalDefense) {
                totalDefense = def;
            }
        }
        return totalDefense;
    }

    // 对受攻击者应用伤害（连环递归攻击）
    public static int applyDamageToDefendersChain(
            int advID, Equipment eq2, LinkedHashSet<Integer> allDefenderIds) {
        int totalLostHitPoint = 0;
        String equType = eq2.getEquType();
        for (int defenderId : allDefenderIds) {
            Adventurer defender = adventurers.get(defenderId);
            int lostHitPoint = calculateLostHitPoint(defender, advID, eq2, equType);
            totalLostHitPoint += lostHitPoint;
            defender.setHitPoint(defender.getHitPoint() - lostHitPoint);
        }
        return totalLostHitPoint;
    }

    // 计算失去的体力值（根据装备类型）
    public static int calculateLostHitPoint(Adventurer defender,
                                            int advID, Equipment eq2, String equType) {
        int lostHitPoint = 0;
        if (equType.equals("Axe")) {
            lostHitPoint = defender.getHitPoint() - defender.getHitPoint() / 10;
        } else if (equType.equals("Sword")) {
            lostHitPoint = eq2.getCombatEffectiveness() +
                    adventurers.get(advID).getAtk() - defender.getDef();
        } else if (equType.equals("Blade")) {
            lostHitPoint = eq2.getCombatEffectiveness() + adventurers.get(advID).getAtk();
        }
        return lostHitPoint;
    }

    // 定义 collectDefenders 方法，递归收集所有受攻击者
    public static void collectDefenders(int defenderId,
                                        LinkedHashSet<Integer> allDefenderIds, int depth) {
        // 如果递归深度超过5，停止递归
        if (depth > 5) {
            return;
        }
        // 如果该冒险者已在集合中，避免重复，返回
        if (allDefenderIds.contains(defenderId)) {
            return;
        }
        // 将当前冒险者加入集合
        allDefenderIds.add(defenderId);

        Adventurer defender = adventurers.get(defenderId);

        // 获取被当前冒险者雇佣的所有冒险者
        HashMap<Adventurer, Integer> hiredAdventurers = defender.getHiredAdventurers();

        // 对每个被雇佣的冒险者，递归调用 collectDefenders
        for (Adventurer hiredAdventurer : hiredAdventurers.keySet()) {
            collectDefenders(hiredAdventurer.getId(), allDefenderIds, depth + 1);
        }
    }

    //返回冒险者集合
    public static Map<Integer, Adventurer> getAdventurers() {
        return adventurers;
    }

    //ID 为 {adv_id_1}的冒险者雇佣 ID 为 {adv_id_2}的冒险者
    public static void hire(int advID1, int advID2) {
        adventurers.get(advID1).hireAdventurer(adventurers.get(advID2));
    }

    //”秘境探险工厂“副本
    public static void challenge(int advID) {
        Adventurer adv = adventurers.get(advID);
        Guard shd = new Shd();
        if (shd.fight(adv)) {
            Treasure treasure = TreasureFactory.createTreasure(shd);
            treasure.showInfo(); // 显示获得的宝物信息
            treasure.useBy(adv);
        }
        Guard flm = new Flm();
        if (flm.fight(adv)) {
            Treasure treasure = TreasureFactory.createTreasure(flm);
            treasure.showInfo(); // 显示获得的宝物信息
            treasure.useBy(adv);
        }
        Guard stn = new Stn();
        if (stn.fight(adv)) {
            Treasure treasure = TreasureFactory.createTreasure(stn);
            treasure.showInfo(); // 显示获得的宝物信息
            treasure.useBy(adv);
        }
        Guard wnd = new Wnd();
        if (wnd.fight(adv)) {
            Treasure treasure = TreasureFactory.createTreasure(wnd);
            treasure.showInfo(); // 显示获得的宝物信息
            treasure.useBy(adv);
        }
        Guard frz = new Frz();
        if (frz.fight(adv)) {
            Treasure treasure = TreasureFactory.createTreasure(frz);
            treasure.showInfo(); // 显示获得的宝物信息
            treasure.useBy(adv);
        }
    }

    public void input() {
        //题目中已有的代码
        int n = Integer.parseInt(scanner.nextLine().trim()); // 读取行数
        for (int i = 0; i < n; ++i) {
            String nextLine = scanner.nextLine(); // 读取本行指令
            String[] strings = nextLine.trim().split(" +"); // 按空格对行进行分割
            inputInfo.add(new ArrayList<>(Arrays.asList(strings))); // 将指令分割后的各个部分存进容器中
        }
    }

    public void solve() {
        input();

        //处理指令
        for (ArrayList<String> command : inputInfo) {
            int type = Integer.parseInt(command.get(0));
            int advID = Integer.parseInt(command.get(1));

            switch (type) {
                case 1:
                    //加入冒险者
                    addAdventurer(advID, command.get(2));
                    break;
                case 2:
                    //给ID冒险者增加一个药水瓶
                    addBottle(advID, Integer.parseInt(command.get(2)),
                            command.get(3), Integer.parseInt(command.get(4)),
                            command.get(5), Integer.parseInt(command.get(6)));
                    break;
                case 3:
                    //给ID冒险者增加一个装备
                    addEquipment(advID, Integer.parseInt(command.get(2)),
                            command.get(3), Integer.parseInt(command.get(4)),
                            command.get(5), Integer.parseInt(command.get(6)));
                    break;
                case 4:
                    //给ID冒险者的equID装备提升一点耐久度
                    increaseDurability(advID, Integer.parseInt(command.get(2)));
                    break;
                case 5:
                    //删除ID冒险者的某个药水瓶或装备
                    adventurers.get(advID).removeItem(Integer.parseInt(command.get(2)));
                    break;
                case 6:
                    //ID冒险者携带他拥有的ID物品
                    adventurers.get(advID).carryItem(Integer.parseInt(command.get(2)));
                    break;
                case 7:
                    //ID冒险者尝试使用他拥有的id的药水瓶
                    adventurers.get(advID).useBottle(Integer.parseInt(command.get(2)));
                    break;
                case 8:
                    //ID冒险者添加一个一个 id 、名字分别为{frag_id}、{name}的碎片
                    addFrag(advID, Integer.parseInt(command.get(2)), command.get(3));
                    break;
                case 9:
                    //ID 为{adv_id}的冒险者尝试使用其拥有的名字为name的碎片兑换一次福利，福利标识码为welfare_id
                    adventurers.get(advID).exchangeWelfare(command.get(2),
                            Integer.parseInt(command.get(3)));
                    break;
                case 10:
                    //ID 为 {adv_id} 的冒险者尝试使用名字为 {name}的装备与k个冒险者进行一次战斗类型为{attack_type}的战斗战斗
                    // （k个冒险者的 ID 分别为 adv_id_1、adv_id_2、…、adv_id_k）
                    int k = Integer.parseInt(command.get(4));
                    ArrayList<Integer> advIds = new ArrayList<>();
                    for (int i = 0; i < k; i++) {
                        advIds.add(Integer.parseInt(command.get(5 + i)));
                    }
                    fight(advID, command.get(2), command.get(3), k, advIds);
                    break;
                case 11:
                    //ID 为 {adv_id_1}的冒险者雇佣 ID 为 {adv_id_2}的冒险者
                    hire(advID, Integer.parseInt(command.get(2)));
                    break;
                case 12:
                    //ID 为 {adv_id}的冒险者挑战”秘境探险工厂“副本
                    challenge(advID);
                    break;
                default:  break;
            }
        }
    }
}