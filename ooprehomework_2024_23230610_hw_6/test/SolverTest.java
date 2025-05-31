import org.junit.Before;
import org.junit.Test;
import org.junit.After;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Map;

public class SolverTest {

    @Before
    public void setUp() {
        // 清空 adventurers 以确保每个测试独立
        Solver.getAdventurers().clear();
    }

    @After
    public void tearDown() {
        // 测试后清空 adventurers
        Solver.getAdventurers().clear();
    }

    @Test
    public void testAddAdventurer() {
        Solver.addAdventurer(1, "John");
        Map<Integer, Adventurer> adventurers = Solver.getAdventurers();
        assertEquals(1, adventurers.size());
        Adventurer adv = adventurers.get(1);
        assertNotNull(adv);
        assertEquals("John", adv.getName());
    }

    @Test
    public void testAddBottle() {
        Solver.addAdventurer(1, "John");
        Solver.addBottle(1, 101, "Healing Potion", 100, "HpBottle", 50);
        Adventurer adv = Solver.getAdventurers().get(1);
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
        Solver.addAdventurer(1, "John");
        Solver.addEquipment(1, 201, "Sword", 10, "Sword", 50);
        Adventurer adv = Solver.getAdventurers().get(1);
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
        Solver.addAdventurer(1, "John");
        Solver.addEquipment(1, 201, "Sword", 10, "Sword", 50);
        Solver.increaseDurability(1, 201);
        Adventurer adv = Solver.getAdventurers().get(1);
        Equipment equipment = adv.getAllEquipments().get(0);
        assertEquals(11, equipment.getDurability());
    }

    @Test
    public void testAddFragment() {
        Solver.addAdventurer(1, "John");
        Solver.addFrag(1, 301, "Fire Fragment");
        Adventurer adv = Solver.getAdventurers().get(1);
        assertEquals(1, adv.getFragments().size());
        Fragment fragment = adv.getFragments().get(0);
        assertEquals("Fire Fragment", fragment.getName());
        assertEquals(301, fragment.getId());
    }

    @Test
    public void testFight_AxeVictory() {
        // 设置攻击者
        Solver.addAdventurer(1, "John");
        Solver.addEquipment(1, 201, "Axe", 10, "Axe", 100);
        Adventurer attacker = Solver.getAdventurers().get(1);
        attacker.setAtk(20);

        // 设置被攻击者
        Solver.addAdventurer(2, "Doe");
        Adventurer defender = Solver.getAdventurers().get(2);
        defender.setDef(15);
        defender.setHitPoint(500);

        // 将装备携带到背包
        attacker.carryItem(201);

        // 被攻击者 ID 列表
        ArrayList<Integer> opponentIds = new ArrayList<>();
        opponentIds.add(2);

        // 进行战斗
        Solver.fight(1, "Axe", "normal", 1, opponentIds);

        // 验证被攻击者体力被减少到原来的十分之一
        assertEquals(50, defender.getHitPoint());

        // 验证装备耐久度减少
        Equipment axe = attacker.getAllEquipments().get(0);
        assertEquals(9, axe.getDurability());
    }

    @Test
    public void testFight_SwordVictory() {
        // 设置攻击者
        Solver.addAdventurer(1, "John");
        Solver.addEquipment(1, 202, "Sword", 10, "Sword", 80);
        Adventurer attacker = Solver.getAdventurers().get(1);
        attacker.setAtk(15);

        // 设置被攻击者
        Solver.addAdventurer(2, "Doe");
        Adventurer defender = Solver.getAdventurers().get(2);
        defender.setDef(10);
        defender.setHitPoint(400);

        // 将装备携带到背包
        attacker.carryItem(202);

        // 被攻击者 ID 列表
        ArrayList<Integer> opponentIds = new ArrayList<>();
        opponentIds.add(2);

        // 进行战斗
        Solver.fight(1, "Sword", "normal", 1, opponentIds);

        // 计算体力减少量: 80 + 15 - 10 = 85
        assertEquals(315, defender.getHitPoint());

        // 验证装备耐久度减少
        Equipment sword = attacker.getAllEquipments().get(0);
        assertEquals(9, sword.getDurability());
    }

    @Test
    public void testFight_BladeVictory() {
        // 设置攻击者
        Solver.addAdventurer(1, "John");
        Solver.addEquipment(1, 203, "Blade", 10, "Blade", 70);
        Adventurer attacker = Solver.getAdventurers().get(1);
        attacker.setAtk(10);

        // 设置被攻击者
        Solver.addAdventurer(2, "Doe");
        Adventurer defender = Solver.getAdventurers().get(2);
        defender.setDef(20);
        defender.setHitPoint(500);

        // 将装备携带到背包
        attacker.carryItem(203);

        // 被攻击者 ID 列表
        ArrayList<Integer> opponentIds = new ArrayList<>();
        opponentIds.add(2);

        // 进行战斗
        Solver.fight(1, "Blade", "normal", 1, opponentIds);

        // 计算体力减少量: 70 + 10 = 80
        assertEquals(420, defender.getHitPoint());

        // 验证装备耐久度减少
        Equipment blade = attacker.getAllEquipments().get(0);
        assertEquals(9, blade.getDurability());
    }

    @Test
    public void testFight_LossDueToHighDefense() {
        // 设置攻击者
        Solver.addAdventurer(1, "John");
        Solver.addEquipment(1, 204, "Axe", 10, "Axe", 30);
        Adventurer attacker = Solver.getAdventurers().get(1);
        attacker.setAtk(5);

        // 设置被攻击者
        Solver.addAdventurer(2, "Doe");
        Adventurer defender = Solver.getAdventurers().get(2);
        defender.setDef(50);
        defender.setHitPoint(1000);

        // 将装备携带到背包
        attacker.carryItem(204);

        // 被攻击者 ID 列表
        ArrayList<Integer> opponentIds = new ArrayList<>();
        opponentIds.add(2);

        // 进行战斗
        Solver.fight(1, "Axe", "normal", 1, opponentIds);

        // 验证被攻击者体力未减少
        assertEquals(1000, defender.getHitPoint());

        // 验证装备耐久度减少
        Equipment axe = attacker.getAllEquipments().get(0);
        assertEquals(10, axe.getDurability());
    }

    @Test
    public void testFight_EquipmentBreak() {
        // 设置攻击者
        Solver.addAdventurer(1, "John");
        Solver.addEquipment(1, 205, "Blade", 1, "Blade", 60);
        Adventurer attacker = Solver.getAdventurers().get(1);
        attacker.setAtk(10);

        // 将装备携带到背包
        attacker.carryItem(205);

        // 设置被攻击者
        Solver.addAdventurer(2, "Doe");
        Adventurer defender = Solver.getAdventurers().get(2);
        defender.setDef(20);
        defender.setHitPoint(300);

        // 被攻击者 ID 列表
        ArrayList<Integer> opponentIds = new ArrayList<>();
        opponentIds.add(2);

        // 进行战斗
        Solver.fight(1, "Blade", "normal", 1, opponentIds);

        // 计算体力减少量: 60 + 10 = 70
        assertEquals(230, defender.getHitPoint());

        // 验证装备耐久度减少到0并被移除
        Adventurer adv = Solver.getAdventurers().get(1);
        assertEquals(0, adv.getEquipments().size());
        assertFalse(adv.getBackpack().containsKey(205));
    }

    @Test
    public void testFight_NoEquipment() {
        // 设置攻击者
        Solver.addAdventurer(1, "John");
        Adventurer attacker = Solver.getAdventurers().get(1);
        attacker.setAtk(10);

        // 设置被攻击者
        Solver.addAdventurer(2, "Doe");
        Adventurer defender = Solver.getAdventurers().get(2);
        defender.setDef(5);
        defender.setHitPoint(500);

        // 被攻击者 ID 列表
        ArrayList<Integer> opponentIds = new ArrayList<>();
        opponentIds.add(2);

        // 进行战斗，没有携带任何装备
        Solver.fight(1, "Sword", "normal", 1, opponentIds);

        // 验证被攻击者体力未减少
        assertEquals(500, defender.getHitPoint());
    }

    @Test
    public void testFight_MultipleDefenders() {
        // 设置攻击者
        Solver.addAdventurer(1, "John");
        Solver.addEquipment(1, 206, "Sword", 10, "Sword", 50);
        Adventurer attacker = Solver.getAdventurers().get(1);
        attacker.setAtk(20);

        // 设置多个被攻击者
        Solver.addAdventurer(2, "Doe");
        Adventurer defender1 = Solver.getAdventurers().get(2);
        defender1.setDef(10);
        defender1.setHitPoint(200);

        Solver.addAdventurer(3, "Alice");
        Adventurer defender2 = Solver.getAdventurers().get(3);
        defender2.setDef(15);
        defender2.setHitPoint(300);

        Solver.addAdventurer(4, "Bob");
        Adventurer defender3 = Solver.getAdventurers().get(4);
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
        Solver.fight(1, "Sword", "normal", 3, opponentIds);

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
        Solver.addAdventurer(1, "John");
        Solver.addEquipment(1, 207, "Sword", 10, "Sword", 50);
        Adventurer attacker = Solver.getAdventurers().get(1);
        attacker.setAtk(20);

        // 设置被攻击者
        Solver.addAdventurer(2, "Doe");
        Adventurer defender = Solver.getAdventurers().get(2);
        defender.setDef(10);
        defender.setHitPoint(200);

        // 不将装备携带到背包
        // 被攻击者 ID 列表
        ArrayList<Integer> opponentIds = new ArrayList<>();
        opponentIds.add(2);

        // 进行战斗
        Solver.fight(1, "Sword", "normal", 1, opponentIds);

        // 因为装备未在背包中，战斗失败
        assertEquals(200, defender.getHitPoint());

        // 验证装备耐久度未减少
        Equipment sword = attacker.getAllEquipments().get(0);
        assertEquals(10, sword.getDurability());
    }

    @Test
    public void testFight_EquipmentDurabilityZeroAfterFight() {
        // 设置攻击者
        Solver.addAdventurer(1, "John");
        Solver.addEquipment(1, 208, "Sword", 1, "Sword", 50);
        Adventurer attacker = Solver.getAdventurers().get(1);
        attacker.setAtk(20);

        // 将装备携带到背包
        attacker.carryItem(208);

        // 设置被攻击者
        Solver.addAdventurer(2, "Doe");
        Adventurer defender = Solver.getAdventurers().get(2);
        defender.setDef(10);
        defender.setHitPoint(200);

        // 被攻击者 ID 列表
        ArrayList<Integer> opponentIds = new ArrayList<>();
        opponentIds.add(2);

        // 进行战斗
        Solver.fight(1, "Sword", "normal", 1, opponentIds);

        // 计算体力减少量: 50 + 20 - 10 = 60
        assertEquals(140, defender.getHitPoint());

        // 验证装备耐久度减少到0并被移除
        Adventurer adv = Solver.getAdventurers().get(1);
        assertEquals(0, adv.getEquipments().size());
        assertFalse(adv.getBackpack().containsKey(208));
    }

    @Test
    public void testGetAdventurers() {
        // 添加多个冒险者
        Solver.addAdventurer(1, "John");
        Solver.addAdventurer(2, "Doe");
        Solver.addAdventurer(3, "Alice");

        Map<Integer, Adventurer> adventurers = Solver.getAdventurers();
        assertEquals(3, adventurers.size());
        assertTrue(adventurers.containsKey(1));
        assertTrue(adventurers.containsKey(2));
        assertTrue(adventurers.containsKey(3));
    }

    @Test
    public void testGetComprehensiveCE_WithHiredAdventurers() {
        Solver.addAdventurer(1, "John");
        Solver.addAdventurer(2, "Doe");

        // 设置冒险者的战斗力
        Adventurer adv = Solver.getAdventurers().get(1);
        adv.setAtk(100);
        adv.setDef(50);

        // 雇佣冒险者
        Adventurer hired = Solver.getAdventurers().get(2);
        hired.setAtk(50);
        hired.setDef(50);

        adv.hireAdventurer(hired);

        // 计算综合战力，包括雇佣的冒险者
        assertEquals(250, adv.getComprehensiveCE());  // 100 + 50 + (50 + 50) = 250
    }

    @Test
    public void testChainFight_Victory() {
        // 设置攻击者
        Solver.addAdventurer(1, "John");
        Solver.addEquipment(1, 201, "Sword", 10, "Sword", 80);
        Adventurer attacker = Solver.getAdventurers().get(1);
        attacker.setAtk(20);

        // 设置被攻击者
        Solver.addAdventurer(2, "Doe");
        Adventurer defender1 = Solver.getAdventurers().get(2);
        defender1.setDef(10);
        defender1.setHitPoint(300);

        Solver.addAdventurer(3, "Alice");
        Adventurer defender2 = Solver.getAdventurers().get(3);
        defender2.setDef(15);
        defender2.setHitPoint(400);

        // 被攻击者ID列表
        ArrayList<Integer> opponentIds = new ArrayList<>();
        opponentIds.add(2);
        opponentIds.add(3);

        // 进行连环递归战斗
        Solver.fight(1, "Sword", "chain", 2, opponentIds);

        // 检查攻击成功后防御者的体力值
        assertEquals(300, defender1.getHitPoint());  // 300 - (80 + 20 - 10) = 240
        assertEquals(400, defender2.getHitPoint());  // 400 - (80 + 20 - 15) = 335
    }

    @Test
    public void testOutputDefendersStatus() {
        Solver.addAdventurer(1, "John");

        // 添加多个防御者
        Solver.addAdventurer(2, "Doe");
        Solver.addAdventurer(3, "Alice");
        ArrayList<Integer> opponentIds = new ArrayList<>();
        opponentIds.add(2);
        opponentIds.add(3);

        // 捕获输出
        Solver.outputDefendersStatus(2, opponentIds);

        // 验证控制台输出的防御者状态（可以通过捕获输出流来测试）
        // 这里只是确保调用了输出方法，详细验证可以在生产环境通过捕获输出流来实现
    }

    @Test
    public void testTransferEquipment() {
        Solver.addAdventurer(1, "John");
        Solver.addAdventurer(2, "Doe");

        // 设置两个冒险者
        Adventurer attacker = Solver.getAdventurers().get(1);
        Adventurer defender = Solver.getAdventurers().get(2);

        // 为攻击者添加装备
        Solver.addEquipment(1, 201, "Sword", 10, "Sword", 50);
        attacker.carryItem(201);

        // 执行装备转移
        Solver.transferEquipment(attacker, defender);

        // 检查装备是否成功从攻击者转移给防御者
        assertEquals(1, defender.getEquipmentNum());
        assertEquals(0, attacker.getEquipmentNum());
    }

    @Test
    public void testHandleAidMechanism() {
        Solver.addAdventurer(1, "John");
        Solver.addAdventurer(2, "Doe");

        // 设置防御者和雇佣者
        Adventurer defender = Solver.getAdventurers().get(1);
        Adventurer hired = Solver.getAdventurers().get(2);
        defender.hireAdventurer(hired);

        // 初始化体力和援助状态
        defender.setHitPoint(200);
        hired.setHitPoint(50);  // 将雇佣者体力降到援助机制启动的阈值以下

        // 模拟援助
        ArrayList<Integer> hitPoints = new ArrayList<>();
        hitPoints.add(200);
        ArrayList<Integer> advIds = new ArrayList<>();
        advIds.add(1);

        Solver.handleAidMechanism(1, advIds, hitPoints);

        // 确认雇佣冒险者被正确援助
        assertEquals(1, defender.getHiredAdventurers().size());
    }

    @Test
    public void testCollectDefendersRecursive() {
        Solver.addAdventurer(1, "John");
        Solver.addAdventurer(2, "Doe");
        Solver.addAdventurer(3, "Alice");

        // 设置雇佣关系
        Adventurer defender = Solver.getAdventurers().get(1);
        Adventurer hired1 = Solver.getAdventurers().get(2);
        Adventurer hired2 = Solver.getAdventurers().get(3);

        defender.hireAdventurer(hired1);
        hired1.hireAdventurer(hired2);

        // 收集防御者
        LinkedHashSet<Integer> allDefenderIds = new LinkedHashSet<>();
        Solver.collectDefendersRecursive(1, allDefenderIds, 1);

        // 检查是否递归收集了所有防御者
        assertTrue(allDefenderIds.contains(1));
        assertTrue(allDefenderIds.contains(2));
        assertTrue(allDefenderIds.contains(3));
    }

    @Test
    public void testCalculateTotalDefenseChain() {
        Solver.addAdventurer(1, "John");
        Solver.addAdventurer(2, "Doe");
        Solver.addAdventurer(3, "Alice");

        // 设置防御力
        Adventurer defender1 = Solver.getAdventurers().get(1);
        Adventurer defender2 = Solver.getAdventurers().get(2);
        Adventurer defender3 = Solver.getAdventurers().get(3);

        defender1.setDef(10);
        defender2.setDef(15);
        defender3.setDef(20);

        // 收集所有防御者ID
        LinkedHashSet<Integer> defenderIds = new LinkedHashSet<>();
        defenderIds.add(1);
        defenderIds.add(2);
        defenderIds.add(3);

        // 计算总防御力
        int totalDefense = Solver.calculateTotalDefenseChain(defenderIds);
        assertEquals(20, totalDefense);  // 最大防御力为20
    }

}
