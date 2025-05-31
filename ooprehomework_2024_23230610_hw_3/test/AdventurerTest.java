import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;

public class AdventurerTest {
    @Test
    public void testGetBottleNum() {
        Adventurer adventurer = new Adventurer(1, "TestUser");
        assertEquals(0, adventurer.getBottleNum());

        Bottle bottle = new Bottle(100, "TestBottle", 50, 10, "HpBottle");
        adventurer.addBottle(bottle);
        assertEquals(1, adventurer.getBottleNum());
    }

    @Test
    public void testGetAllBottles() {
        Adventurer adventurer = new Adventurer(1, "TestUser");
        ArrayList<Bottle> bottles = adventurer.getAllBottles();
        assertTrue(bottles.isEmpty());

        // 创建一个类型为 HpBottle 的瓶子
        Bottle bottle = new Bottle(100, "TestBottle", 50, 10, "HpBottle");
        adventurer.addBottle(bottle);

        // 获取所有瓶子
        bottles = adventurer.getAllBottles();
        assertEquals(1, bottles.size());

        // 获取实际添加的瓶子对象
        Bottle addedBottle = bottles.get(0);

        // 验证添加的瓶子的属性
        assertEquals(100, addedBottle.getId());
        assertEquals("TestBottle", addedBottle.getName());
        assertEquals(50, addedBottle.getCapacity());
        assertEquals(10, addedBottle.getCombatEffectiveness());
        assertEquals("HpBottle", addedBottle.getBotType());

        // 验证添加的瓶子是 HpBottle 的实例
        assertTrue(addedBottle instanceof HpBottle);
    }

    @Test
    public void testRemoveExistingBottle() {
        Adventurer adventurer = new Adventurer(1, "TestUser");
        Bottle bottle = new Bottle(100, "TestBottle", 50, 10, "HpBottle");
        adventurer.addBottle(bottle);
        adventurer.removeItem(100);
        assertEquals(0, adventurer.getBottleNum());
    }

    @Test
    public void testRemoveNonExistingItem() {
        Adventurer adventurer = new Adventurer(1, "TestUser");
        adventurer.removeItem(999);
        // 验证没有异常抛出，物品数量不变
        assertEquals(0, adventurer.getBottleNum());
        assertEquals(0, adventurer.getEquipmentNum());
    }

    @Test
    public void testUseBottleSuccessfully() {
        Adventurer adventurer = new Adventurer(1, "TestUser");

        // 创建一个类型为 HpBottle 的瓶子
        Bottle bottle = new Bottle(100, "Health Potion", 100, 0, "HpBottle");
        adventurer.addBottle(bottle);

        // 冒险者携带该瓶子
        adventurer.carryItem(100);

        // 在使用前，获取实际的 HpBottle 实例
        Bottle hpBottle = null;
        for (Bottle b : adventurer.getAllBottles()) {
            if (b.getId() == 100) {
                hpBottle = b;
                break;
            }
        }

        assertNotNull(hpBottle);
        assertFalse(hpBottle.isEmpty());

        // 使用瓶子
        adventurer.useBottle(100);

        // 验证体力增加
        assertEquals(600, adventurer.getHitPoint()); // 500 + 100

        // 验证瓶子已被标记为已使用
        assertTrue(hpBottle.isEmpty());

    }

    @Test
    public void testUseBottleNotCarried() {
        Adventurer adventurer = new Adventurer(1, "TestUser");
        Bottle hpBottle = new HpBottle(100, "Health Potion", 100, 0, "HpBottle");
        adventurer.addBottle(hpBottle);
        adventurer.useBottle(100);
        assertEquals(500, adventurer.getHitPoint()); // 体力不变
    }

    @Test
    public void testUseBottleAlreadyEmpty() {
        Adventurer adventurer = new Adventurer(1, "TestUser");
        Bottle hpBottle = new HpBottle(100, "Health Potion", 100, 0, "HpBottle");
        adventurer.addBottle(hpBottle);
        adventurer.carryItem(100);
        // 第一次使用
        adventurer.useBottle(100);
        // 第二次使用
        adventurer.useBottle(100);
        // 验证瓶子已被移除
        assertEquals(0, adventurer.getBottleNum());
    }

    @Test
    public void testIncreaseEquipmentDurabilityWhenEmpty() {
        Adventurer adventurer = new Adventurer(1, "TestUser");
        Equipment result = adventurer.increaseEquipmentDurability(100);
        assertNull(result);
    }


    @Test
    public void testConstructorAndBasicAttributes() {
        Adventurer adventurer = new Adventurer(1, "Hero");
        assertEquals(1, adventurer.getId());
        assertEquals("Hero", adventurer.getName());
        assertEquals(500, adventurer.getHitPoint());
        assertEquals(1, adventurer.getAtk());
        assertEquals(0, adventurer.getDef());
        // 假设您有一个 getCombatEffectiveness() 方法
        assertEquals(1, adventurer.getCombatEffectiveness());
    }

    @Test
    public void testAddEquipmentAndGetEquipmentNum() {
        Adventurer adventurer = new Adventurer(1, "Warrior");
        assertEquals(0, adventurer.getEquipmentNum());

        Equipment sword = new Equipment(200, "Sword", 100, 20);
        adventurer.addEquipment(sword);
        assertEquals(1, adventurer.getEquipmentNum());

        Equipment shield = new Equipment(201, "Shield", 150, 15);
        adventurer.addEquipment(shield);
        assertEquals(2, adventurer.getEquipmentNum());
    }

    @Test
    public void testGetAllEquipments() {
        Adventurer adventurer = new Adventurer(1, "Archer");
        ArrayList<Equipment> equipments = adventurer.getAllEquipments();
        assertTrue(equipments.isEmpty());

        Equipment bow = new Equipment(300, "Bow", 80, 25);
        adventurer.addEquipment(bow);
        equipments = adventurer.getAllEquipments();
        assertEquals(1, equipments.size());
        assertEquals(bow, equipments.get(0));
    }

    @Test
    public void testRemoveEquipment() {
        Adventurer adventurer = new Adventurer(1, "Mage");
        Equipment staff = new Equipment(400, "Staff", 60, 30);
        adventurer.addEquipment(staff);
        assertEquals(1, adventurer.getEquipmentNum());

        Equipment removedEquipment = adventurer.removeEquipment(400);
        assertNotNull(removedEquipment);
        assertEquals(0, adventurer.getEquipmentNum());
        assertEquals(staff, removedEquipment);
    }

    @Test
    public void testRemoveNonExistingEquipment() {
        Adventurer adventurer = new Adventurer(1, "Mage");
        Equipment removedEquipment = adventurer.removeEquipment(999);
        assertNull(removedEquipment);
    }

    @Test
    public void testIncreaseEquipmentDurability() {
        Adventurer adventurer = new Adventurer(1, "Knight");
        Equipment armor = new Equipment(500, "Armor", 100, 50);
        adventurer.addEquipment(armor);

        Equipment updatedEquipment = adventurer.increaseEquipmentDurability(500);
        assertNotNull(updatedEquipment);
        assertEquals(101, updatedEquipment.getDurability());
    }

    @Test
    public void testIncreaseDurabilityNonExistingEquipment() {
        Adventurer adventurer = new Adventurer(1, "Knight");
        Equipment result = adventurer.increaseEquipmentDurability(999);
        assertNull(result);
    }

    @Test
    public void testCarryItem() {
        Adventurer adventurer = new Adventurer(1, "Rogue");
        Bottle bottle = new Bottle(600, "Stealth Potion", 30, 5, "DefBottle");
        Equipment dagger = new Equipment(601, "Dagger", 50, 15);

        adventurer.addBottle(bottle);
        adventurer.addEquipment(dagger);

        adventurer.carryItem(600);
        adventurer.carryItem(601);

        assertEquals(2, adventurer.getBackpackSize());
    }

    @Test
    public void testCarryItemAlreadyCarried() {
        Adventurer adventurer = new Adventurer(1, "Rogue");
        Bottle bottle = new Bottle(600, "Stealth Potion", 30, 5, "DefBottle");
        adventurer.addBottle(bottle);

        adventurer.carryItem(600);
        int initialBackpackSize = adventurer.getBackpackSize();
        adventurer.carryItem(600);
        assertEquals(initialBackpackSize, adventurer.getBackpackSize());
    }

    @Test
    public void testUseAtkBottle() {
        Adventurer adventurer = new Adventurer(1, "Warrior");
        Bottle bottle = new Bottle(700, "Attack Potion", 50, 10, "AtkBottle");
        adventurer.addBottle(bottle);
        adventurer.carryItem(700);

        int initialAtk = adventurer.getAtk();

        adventurer.useBottle(700);

        // 计算预期的攻击力增加
        int expectedIncrease = bottle.getCombatEffectiveness() + bottle.getCapacity() / 100;
        assertEquals(initialAtk + expectedIncrease, adventurer.getAtk());
    }

    @Test
    public void testUseDefBottle() {
        Adventurer adventurer = new Adventurer(1, "Guardian");
        Bottle bottle = new Bottle(800, "Defense Potion", 75, 5, "DefBottle");
        adventurer.addBottle(bottle);
        adventurer.carryItem(800);

        int initialDef = adventurer.getDef();

        adventurer.useBottle(800);

        // 计算预期的防御力增加
        int expectedIncrease = bottle.getCombatEffectiveness() + bottle.getCapacity() / 100;
        assertEquals(initialDef + expectedIncrease, adventurer.getDef());
    }

    @Test
    public void testUseNonExistingBottle() {
        Adventurer adventurer = new Adventurer(1, "TestUser");
        adventurer.useBottle(999); // 使用不存在的瓶子
        // 验证体力、攻击力、防御力均未发生变化
        assertEquals(500, adventurer.getHitPoint());
        assertEquals(1, adventurer.getAtk());
        assertEquals(0, adventurer.getDef());
    }

    @Test
    public void testUseBottleNotOwned() {
        Adventurer adventurer = new Adventurer(1, "TestUser");
        Bottle bottle = new Bottle(100, "Health Potion", 100, 0, "HpBottle");
        // 未添加到冒险者的瓶子列表中

        adventurer.carryItem(100); // 试图携带未拥有的瓶子
        adventurer.useBottle(100); // 试图使用未拥有的瓶子

        // 验证体力、攻击力、防御力均未发生变化
        assertEquals(500, adventurer.getHitPoint());
        assertEquals(1, adventurer.getAtk());
        assertEquals(0, adventurer.getDef());
    }

    @Test
    public void testReuseEmptyBottle() {
        Adventurer adventurer = new Adventurer(1, "TestUser");
        Bottle bottle = new Bottle(100, "Health Potion", 100, 0, "HpBottle");
        adventurer.addBottle(bottle);
        adventurer.carryItem(100);

        // 第一次使用
        adventurer.useBottle(100);
        assertEquals(600, adventurer.getHitPoint());

        // 第二次使用
        adventurer.useBottle(100);
        // 瓶子应已被移除，体力不应再次增加
        assertEquals(600, adventurer.getHitPoint());
        assertEquals(0, adventurer.getBottleNum());
    }

    @Test
    public void testRemoveItemWithMultipleItems() {
        Adventurer adventurer = new Adventurer(1, "TestUser");
        Bottle bottle1 = new Bottle(100, "Potion1", 50, 10, "HpBottle");
        Bottle bottle2 = new Bottle(101, "Potion2", 50, 10, "AtkBottle");
        Equipment equipment = new Equipment(200, "Sword", 100, 20);

        adventurer.addBottle(bottle1);
        adventurer.addBottle(bottle2);
        adventurer.addEquipment(equipment);

        // 移除一个瓶子
        adventurer.removeItem(100);
        assertEquals(1, adventurer.getBottleNum());
        assertEquals(1, adventurer.getEquipmentNum());

        // 移除装备
        adventurer.removeItem(200);
        assertEquals(1, adventurer.getBottleNum());
        assertEquals(0, adventurer.getEquipmentNum());
    }

}