import org.junit.Before;
import org.junit.Test;
import org.junit.After;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainClassTest {

    @Before
    public void setUp() {
        // 清空 adventurers 以确保每个测试独立
        MainClass.getAdventurers().clear();
    }

    @After
    public void tearDown() {
        // 测试后清空 adventurers
        MainClass.getAdventurers().clear();
    }

    @Test
    public void testAddAdventurer() {
        MainClass.addAdventurer(1, "John");
        Map<Integer, Adventurer> adventurers = MainClass.getAdventurers();
        assertEquals(1, adventurers.size());
        Adventurer adv = adventurers.get(1);
        assertNotNull(adv);
        assertEquals("John", adv.getName());
    }

    @Test
    public void testAddBottle() {
        MainClass.addAdventurer(1, "John");
        MainClass.addBottle(1, 101, "Healing Potion", 100, "HpBottle", 50);
        Adventurer adv = MainClass.getAdventurers().get(1);
        assertNotNull(adv);
        assertEquals(1, adv.getBottleNum());
        Bottle bottle = adv.getAllBottles().get(0);
        assertEquals("Healing Potion", bottle.getName());
        assertEquals(100, bottle.getCapacity());
        assertEquals("HpBottle", bottle.getBotType());
        assertEquals(50, bottle.getCombatEffectiveness());
    }

    @Test
    public void testAddEquipment() {
        MainClass.addAdventurer(1, "John");
        MainClass.addEquipment(1, 201, "Sword", 10, "Sword", 50);
        Adventurer adv = MainClass.getAdventurers().get(1);
        assertNotNull(adv);
        assertEquals(1, adv.getEquipmentNum());
        Equipment equipment = adv.getAllEquipments().get(0);
        assertEquals("Sword", equipment.getName());
        assertEquals(10, equipment.getDurability());
        assertEquals("Sword", equipment.getEquType());
        assertEquals(50, equipment.getCombatEffectiveness());
    }

    @Test
    public void testIncreaseDurability() {
        MainClass.addAdventurer(1, "John");
        MainClass.addEquipment(1, 201, "Sword", 10, "Sword", 50);
        MainClass.increaseDurability(1, 201);
        Adventurer adv = MainClass.getAdventurers().get(1);
        Equipment equipment = adv.getAllEquipments().get(0);
        assertEquals(11, equipment.getDurability());
    }

    @Test
    public void testAddFragment() {
        MainClass.addAdventurer(1, "John");
        MainClass.addFrag(1, 301, "Fire Fragment");
        Adventurer adv = MainClass.getAdventurers().get(1);
        assertEquals(1, adv.getFragments().size());
        Fragment fragment = adv.getFragments().get(0);
        assertEquals("Fire Fragment", fragment.getName());
        assertEquals(301, fragment.getId());
    }

    @Test
    public void testFight_AxeVictory() {
        // 设置攻击者
        MainClass.addAdventurer(1, "John");
        MainClass.addEquipment(1, 201, "Axe", 10, "Axe", 100);
        Adventurer attacker = MainClass.getAdventurers().get(1);
        attacker.setAtk(20);

        // 设置被攻击者
        MainClass.addAdventurer(2, "Doe");
        Adventurer defender = MainClass.getAdventurers().get(2);
        defender.setDef(15);
        defender.setHitPoint(500);

        // 将装备携带到背包
        attacker.carryItem(201);

        // 被攻击者 ID 列表
        ArrayList<Integer> opponentIds = new ArrayList<>();
        opponentIds.add(2);

        // 进行战斗
        MainClass.fight(1, "Axe", 1, opponentIds);

        // 验证被攻击者体力被减少到原来的十分之一
        assertEquals(50, defender.getHitPoint());

        // 验证装备耐久度减少
        Equipment axe = attacker.getAllEquipments().get(0);
        assertEquals(9, axe.getDurability());
    }

    @Test
    public void testFight_SwordVictory() {
        // 设置攻击者
        MainClass.addAdventurer(1, "John");
        MainClass.addEquipment(1, 202, "Sword", 10, "Sword", 80);
        Adventurer attacker = MainClass.getAdventurers().get(1);
        attacker.setAtk(15);

        // 设置被攻击者
        MainClass.addAdventurer(2, "Doe");
        Adventurer defender = MainClass.getAdventurers().get(2);
        defender.setDef(10);
        defender.setHitPoint(400);

        // 将装备携带到背包
        attacker.carryItem(202);

        // 被攻击者 ID 列表
        ArrayList<Integer> opponentIds = new ArrayList<>();
        opponentIds.add(2);

        // 进行战斗
        MainClass.fight(1, "Sword", 1, opponentIds);

        // 计算体力减少量: 80 + 15 - 10 = 85
        assertEquals(315, defender.getHitPoint());

        // 验证装备耐久度减少
        Equipment sword = attacker.getAllEquipments().get(0);
        assertEquals(9, sword.getDurability());
    }

    @Test
    public void testFight_BladeVictory() {
        // 设置攻击者
        MainClass.addAdventurer(1, "John");
        MainClass.addEquipment(1, 203, "Blade", 10, "Blade", 70);
        Adventurer attacker = MainClass.getAdventurers().get(1);
        attacker.setAtk(10);

        // 设置被攻击者
        MainClass.addAdventurer(2, "Doe");
        Adventurer defender = MainClass.getAdventurers().get(2);
        defender.setDef(20);
        defender.setHitPoint(500);

        // 将装备携带到背包
        attacker.carryItem(203);

        // 被攻击者 ID 列表
        ArrayList<Integer> opponentIds = new ArrayList<>();
        opponentIds.add(2);

        // 进行战斗
        MainClass.fight(1, "Blade", 1, opponentIds);

        // 计算体力减少量: 70 + 10 = 80
        assertEquals(420, defender.getHitPoint());

        // 验证装备耐久度减少
        Equipment blade = attacker.getAllEquipments().get(0);
        assertEquals(9, blade.getDurability());
    }

    @Test
    public void testFight_LossDueToHighDefense() {
        // 设置攻击者
        MainClass.addAdventurer(1, "John");
        MainClass.addEquipment(1, 204, "Axe", 10, "Axe", 30);
        Adventurer attacker = MainClass.getAdventurers().get(1);
        attacker.setAtk(5);

        // 设置被攻击者
        MainClass.addAdventurer(2, "Doe");
        Adventurer defender = MainClass.getAdventurers().get(2);
        defender.setDef(50);
        defender.setHitPoint(1000);

        // 将装备携带到背包
        attacker.carryItem(204);

        // 被攻击者 ID 列表
        ArrayList<Integer> opponentIds = new ArrayList<>();
        opponentIds.add(2);

        // 进行战斗
        MainClass.fight(1, "Axe", 1, opponentIds);

        // 验证被攻击者体力未减少
        assertEquals(1000, defender.getHitPoint());

        // 验证装备耐久度减少
        Equipment axe = attacker.getAllEquipments().get(0);
        assertEquals(10, axe.getDurability());
    }

    @Test
    public void testFight_EquipmentBreak() {
        // 设置攻击者
        MainClass.addAdventurer(1, "John");
        MainClass.addEquipment(1, 205, "Blade", 1, "Blade", 60);
        Adventurer attacker = MainClass.getAdventurers().get(1);
        attacker.setAtk(10);

        // 将装备携带到背包
        attacker.carryItem(205);

        // 设置被攻击者
        MainClass.addAdventurer(2, "Doe");
        Adventurer defender = MainClass.getAdventurers().get(2);
        defender.setDef(20);
        defender.setHitPoint(300);

        // 被攻击者 ID 列表
        ArrayList<Integer> opponentIds = new ArrayList<>();
        opponentIds.add(2);

        // 进行战斗
        MainClass.fight(1, "Blade", 1, opponentIds);

        // 计算体力减少量: 60 + 10 = 70
        assertEquals(230, defender.getHitPoint());

        // 验证装备耐久度减少到0并被移除
        Adventurer adv = MainClass.getAdventurers().get(1);
        assertEquals(0, adv.getEquipments().size());
        assertFalse(adv.getBackpack().containsKey(205));
    }

    @Test
    public void testFight_NoEquipment() {
        // 设置攻击者
        MainClass.addAdventurer(1, "John");
        Adventurer attacker = MainClass.getAdventurers().get(1);
        attacker.setAtk(10);

        // 设置被攻击者
        MainClass.addAdventurer(2, "Doe");
        Adventurer defender = MainClass.getAdventurers().get(2);
        defender.setDef(5);
        defender.setHitPoint(500);

        // 被攻击者 ID 列表
        ArrayList<Integer> opponentIds = new ArrayList<>();
        opponentIds.add(2);

        // 进行战斗，没有携带任何装备
        MainClass.fight(1, "Sword", 1, opponentIds);

        // 验证被攻击者体力未减少
        assertEquals(500, defender.getHitPoint());
    }

    @Test
    public void testFight_MultipleDefenders() {
        // 设置攻击者
        MainClass.addAdventurer(1, "John");
        MainClass.addEquipment(1, 206, "Sword", 10, "Sword", 50);
        Adventurer attacker = MainClass.getAdventurers().get(1);
        attacker.setAtk(20);

        // 设置多个被攻击者
        MainClass.addAdventurer(2, "Doe");
        Adventurer defender1 = MainClass.getAdventurers().get(2);
        defender1.setDef(10);
        defender1.setHitPoint(200);

        MainClass.addAdventurer(3, "Alice");
        Adventurer defender2 = MainClass.getAdventurers().get(3);
        defender2.setDef(15);
        defender2.setHitPoint(300);

        MainClass.addAdventurer(4, "Bob");
        Adventurer defender3 = MainClass.getAdventurers().get(4);
        defender3.setDef(5);
        defender3.setHitPoint(400);

        // 将装备携带到背包
        attacker.carryItem(206);

        // 被攻击者 ID 列表
        ArrayList<Integer> opponentIds = new ArrayList<>();
        opponentIds.add(2);
        opponentIds.add(3);
        opponentIds.add(4);

        // 进行战斗
        MainClass.fight(1, "Sword", 3, opponentIds);

        // 计算整体防御能力为15 (defender2)
        // 攻击力 = 20 + 50 = 70
        // 15 < 70，战斗成功
        // 根据装备类型 "Sword":
        // defender1 hitPoint = 200 - (50 + 20 - 10) = 200 - 60 = 140
        // defender2 hitPoint = 300 - (50 + 20 - 15) = 300 - 55 = 245
        // defender3 hitPoint = 400 - (50 + 20 - 5) = 400 - 65 = 335

        assertEquals(140, defender1.getHitPoint());
        assertEquals(245, defender2.getHitPoint());
        assertEquals(335, defender3.getHitPoint());

        // 验证装备耐久度减少
        Equipment sword = attacker.getAllEquipments().get(0);
        assertEquals(9, sword.getDurability());
    }

    @Test
    public void testFight_EquipmentNotInBackpack() {
        // 设置攻击者
        MainClass.addAdventurer(1, "John");
        MainClass.addEquipment(1, 207, "Sword", 10, "Sword", 50);
        Adventurer attacker = MainClass.getAdventurers().get(1);
        attacker.setAtk(20);

        // 设置被攻击者
        MainClass.addAdventurer(2, "Doe");
        Adventurer defender = MainClass.getAdventurers().get(2);
        defender.setDef(10);
        defender.setHitPoint(200);

        // 不将装备携带到背包
        // 被攻击者 ID 列表
        ArrayList<Integer> opponentIds = new ArrayList<>();
        opponentIds.add(2);

        // 进行战斗
        MainClass.fight(1, "Sword", 1, opponentIds);

        // 因为装备未在背包中，战斗失败
        assertEquals(200, defender.getHitPoint());

        // 验证装备耐久度未减少
        Equipment sword = attacker.getAllEquipments().get(0);
        assertEquals(10, sword.getDurability());
    }

    @Test
    public void testFight_EquipmentDurabilityZeroAfterFight() {
        // 设置攻击者
        MainClass.addAdventurer(1, "John");
        MainClass.addEquipment(1, 208, "Sword", 1, "Sword", 50);
        Adventurer attacker = MainClass.getAdventurers().get(1);
        attacker.setAtk(20);

        // 将装备携带到背包
        attacker.carryItem(208);

        // 设置被攻击者
        MainClass.addAdventurer(2, "Doe");
        Adventurer defender = MainClass.getAdventurers().get(2);
        defender.setDef(10);
        defender.setHitPoint(200);

        // 被攻击者 ID 列表
        ArrayList<Integer> opponentIds = new ArrayList<>();
        opponentIds.add(2);

        // 进行战斗
        MainClass.fight(1, "Sword", 1, opponentIds);

        // 计算体力减少量: 50 + 20 - 10 = 60
        assertEquals(140, defender.getHitPoint());

        // 验证装备耐久度减少到0并被移除
        Adventurer adv = MainClass.getAdventurers().get(1);
        assertEquals(0, adv.getEquipments().size());
        assertFalse(adv.getBackpack().containsKey(208));
    }

    @Test
    public void testGetAdventurers() {
        // 添加多个冒险者
        MainClass.addAdventurer(1, "John");
        MainClass.addAdventurer(2, "Doe");
        MainClass.addAdventurer(3, "Alice");

        Map<Integer, Adventurer> adventurers = MainClass.getAdventurers();
        assertEquals(3, adventurers.size());
        assertTrue(adventurers.containsKey(1));
        assertTrue(adventurers.containsKey(2));
        assertTrue(adventurers.containsKey(3));
    }
}
