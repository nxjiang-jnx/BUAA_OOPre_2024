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

    public static void main(String[] args) {
        input();

        //处理指令
        for (ArrayList<String> command : inputInfo) {
            int type = Integer.parseInt(command.get(0));

            switch (type) {
                case 1:
                    //加入冒险者
                    int advID = Integer.parseInt(command.get(1));
                    String advName = command.get(2);
                    Adventurer adv = new Adventurer(advID, advName);
                    adventurers.put(advID, adv);
                    break;
                case 2:
                    //给ID冒险者增加一个药水瓶
                    advID = Integer.parseInt(command.get(1));   //冒险者ID
                    int botID = Integer.parseInt(command.get(2));   //药水瓶ID
                    String botName = command.get(3);    //药水瓶名称
                    int capacity = Integer.parseInt(command.get(4));    //药水瓶容量
                    String botType = command.get(5);    //药水瓶类型
                    int botCombatEffectiveness = Integer.parseInt(command.get(6));    //药水瓶战斗力
                    Bottle bottle = new Bottle(botID, botName, capacity,
                            botCombatEffectiveness, botType);
                    adventurers.get(advID).addBottle(bottle);
                    break;
                case 3:
                    //给ID冒险者增加一个装备
                    advID = Integer.parseInt(command.get(1));
                    int equID = Integer.parseInt(command.get(2));
                    String equName = command.get(3);
                    int durability = Integer.parseInt(command.get(4));
                    int equCombatEffectiveness = Integer.parseInt(command.get(5));
                    Equipment equipment = new Equipment(equID, equName,
                            durability, equCombatEffectiveness);
                    adventurers.get(advID).addEquipment(equipment);
                    break;
                case 4:
                    //给ID冒险者的equID装备提升一点耐久度
                    advID = Integer.parseInt(command.get(1));
                    equID = Integer.parseInt(command.get(2));
                    Equipment eq = adventurers.get(advID).increaseEquipmentDurability(equID);
                    if (eq != null) {
                        System.out.println(eq.getName() + " " + eq.getDurability());
                    }
                    break;
                case 5:
                    //删除ID冒险者的某个药水瓶或装备
                    advID = Integer.parseInt(command.get(1));
                    int delId = Integer.parseInt(command.get(2));   //需要删除的药水瓶或装备ID
                    adventurers.get(advID).removeItem(delId);
                    break;
                case 6:
                    //ID冒险者携带他拥有的ID物品
                    advID = Integer.parseInt(command.get(1));
                    int itemID = Integer.parseInt(command.get(2));
                    adventurers.get(advID).carryItem(itemID);
                    break;
                case 7:
                    //ID冒险者尝试使用他拥有的id的药水瓶
                    advID = Integer.parseInt(command.get(1));
                    botID = Integer.parseInt(command.get(2));
                    adventurers.get(advID).useBottle(botID);
                    break;
                default:
                    break;
            }
        }
    }

}
