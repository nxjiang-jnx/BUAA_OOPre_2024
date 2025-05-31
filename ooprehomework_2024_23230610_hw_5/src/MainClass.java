/**
 * @author jhria
 * @since 2024/9/17 下午1:07
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class MainClass {
    // 解析后的输入将会存进该容器中, 类似于C语言的二维数组
    private static ArrayList<ArrayList<String>> inputInfo = new ArrayList<>();

    // 存储冒险者
    private static Map<Integer, Adventurer> adventurers = new HashMap<>();

    public static void input() {
        //题目中已有的代码
        Scanner scanner = new Scanner(System.in);
        int n = Integer.parseInt(scanner.nextLine().trim()); // 读取行数
        for (int i = 0; i < n; ++i) {
            String nextLine = scanner.nextLine(); // 读取本行指令
            String[] strings = nextLine.trim().split(" +"); // 按空格对行进行分割
            inputInfo.add(new ArrayList<>(Arrays.asList(strings))); // 将指令分割后的各个部分存进容器中
        }
    }

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

    //ID 为 {adv_id} 的冒险者尝试使用名字为 {name}的装备与k个冒险者进行一次战斗
    // （k个冒险者的 ID 分别为 adv_id_1、adv_id_2、…、adv_id_k）
    public static void fight(int advID, String equName2, int k, ArrayList<Integer> advIds) {
        //定义受攻击方的整体防御能力：遍历advIds，找到受攻击方所有个体防御力中的最大值
        int totalDefense = 0;
        for (int i = 0; i < k; i++) {
            if (adventurers.get(advIds.get(i)).getDef() > totalDefense) {
                totalDefense = adventurers.get(advIds.get(i)).getDef();
            }
        }

        //遍历advID冒险者背包，若没有名字为equName2的装备，则战斗失败
        Equipment eq2 = adventurers.get(advID).searchBackpackEqu(equName2);
        if (eq2 == null) {
            System.out.println("Adventurer " + advID + " defeated");
            return;
        }

        //定义攻击方的整体攻击力：advID冒险者的攻击力和他的所携带的装备的攻击力之和
        int totalAttack = adventurers.get(advID).getAtk() +
                eq2.getCombatEffectiveness();

        //如果totalDefence >= totalAttack，战斗失败
        if (totalDefense >= totalAttack) {
            System.out.println("Adventurer " + advID + " defeated");
            return;
        } else {
            //战斗成功，判断装备的类型是Axe、Sword还是Blade
            if (eq2.getEquType().equals("Axe")) {
                //所有受攻击者新体力hitPoint =  受攻击者原体力 / 10
                for (int i = 0; i < k; i++) {
                    adventurers.get(advIds.get(i)).setHitPoint(adventurers.
                            get(advIds.get(i)).getHitPoint() / 10);
                }
            } else if (eq2.getEquType().equals("Sword")) {
                //受攻击者损失生命值 hitPoint_decrease = 装备的战斗力 + 攻击者的 atk - 受攻击者的 def
                for (int i = 0; i < k; i++) {
                    int hitPointDecrease = eq2.getCombatEffectiveness() +
                            adventurers.get(advID).getAtk() -
                            adventurers.get(advIds.get(i)).getDef();
                    adventurers.get(advIds.get(i)).
                            setHitPoint(adventurers.get(advIds.get(i)).
                                    getHitPoint() - hitPointDecrease);
                }
            } else {
                //Blade, 受攻击者损失生命值为 hitPoint_decrease = 装备的CE + 攻击者的 atk
                for (int i = 0; i < k; i++) {
                    int hitPointDecrease = eq2.getCombatEffectiveness()
                            + adventurers.get(advID).getAtk();
                    adventurers.get(advIds.get(i)).
                            setHitPoint(adventurers.get(advIds.get(i)).
                                    getHitPoint() - hitPointDecrease);
                }
            }
        }

        //输出k行，第m行的格式为{ID 为 adv_id_m 的冒险者的name} {ID 为 adv_id_m 的冒险者受到攻击后的体力值}
        for (int i = 0; i < k; i++) {
            System.out.println(adventurers.get(advIds.get(i)).getName()
                    + " " + adventurers.get(advIds.get(i)).getHitPoint());
        }

        //装备的新耐久度=装备的原耐久度−1
        eq2.decreaseDurability();
        //如果装备的耐久度小于等于0，则从冒险者的backpack和拥有的装备中均移除
        if (eq2.getDurability() <= 0) {
            adventurers.get(advID).removeBackpackEqu(eq2.getId());
            adventurers.get(advID).removeEquipment(eq2.getId());
        }
    }

    //返回冒险者集合
    public static Map<Integer, Adventurer> getAdventurers() {
        return adventurers;
    }

    public static void main(String[] args) {
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
                    //ID 为 {adv_id} 的冒险者尝试使用名字为 {name}的装备与k个冒险者进行一次战斗
                    // （k个冒险者的 ID 分别为 adv_id_1、adv_id_2、…、adv_id_k）
                    int k = Integer.parseInt(command.get(3));
                    ArrayList<Integer> advIds = new ArrayList<>();
                    for (int i = 0; i < k; i++) {
                        advIds.add(Integer.parseInt(command.get(4 + i)));
                    }
                    fight(advID, command.get(2), k, advIds);
                    break;
                default:
                    break;
            }
        }
    }
}
