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
                    advID = Integer.parseInt(command.get(1));
                    int botID = Integer.parseInt(command.get(2));
                    String botName = command.get(3);
                    int capacity = Integer.parseInt(command.get(4));
                    Bottle bottle = new Bottle(botID, botName, capacity);
                    adventurers.get(advID).addBottle(bottle);
                    break;
                case 3:
                    //给ID冒险者增加一个装备
                    advID = Integer.parseInt(command.get(1));
                    int equID = Integer.parseInt(command.get(2));
                    String equName = command.get(3);
                    int durability = Integer.parseInt(command.get(4));
                    Equipment equipment = new Equipment(equID, equName, durability);
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
                    //删除ID冒险者的botID药水瓶
                    advID = Integer.parseInt(command.get(1));
                    botID = Integer.parseInt(command.get(2));
                    Bottle removedBottle = adventurers.get(advID).removeBottle(botID);
                    if (removedBottle != null) {
                        int bottleNum = adventurers.get(advID).getBottleNum();
                        System.out.println(bottleNum + " " + removedBottle.getName() + " " +
                                removedBottle.getCapacity());
                    }
                    break;
                case 6:
                    //删除ID冒险者的equID装备
                    advID = Integer.parseInt(command.get(1));
                    equID = Integer.parseInt(command.get(2));
                    Equipment removedEquipment = adventurers.get(advID).removeEquipment(equID);
                    if (removedEquipment != null) {
                        int equipmentNum = adventurers.get(advID).getEquipmentNum();
                        System.out.println(equipmentNum + " " + removedEquipment.getName() + " " +
                                removedEquipment.getDurability());
                    }
                    break;
                default:
                    break;
            }
        }
    }

}
