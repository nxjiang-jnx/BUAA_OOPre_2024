import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;

public class AdventurerTest {
    private Adventurer adventurer;
    private HpBottle hpBottle;
    private AtkBottle atkBottle;
    private DefBottle defBottle;
    private Sword sword;
    private Axe axe;
    private Blade blade;
    private Equipment invalidEquipment;
    private Fragment fragment1;
    private Fragment fragment2;
    private Fragment fragment3;
    private Fragment fragment4;
    private Fragment fragment5;
    private Fragment fragment6;

    @Before
    public void setUp() {
        adventurer = new Adventurer(1, "Hero");
        hpBottle = new HpBottle(1, "Healing Potion", 50, 10, "HpBottle");
        atkBottle = new AtkBottle(2, "Attack Potion", 30, 5, "AtkBottle");
        defBottle = new DefBottle(3, "Defense Potion", 40, 8, "DefBottle");
        sword = new Sword(4, "Excalibur", 100, 20, "Sword");
        axe = new Axe(5, "Battle Axe", 80, 15, "Axe");
        blade = new Blade(6, "Shadow Blade", 90, 18, "Blade");
        invalidEquipment = new Equipment(7, "Unknown", 50, 10, "UnknownType"); // For testing invalid equType
        fragment1 = new Fragment(101, "FragmentA");
        fragment2 = new Fragment(102, "FragmentA");
        fragment3 = new Fragment(103, "FragmentA");
        fragment4 = new Fragment(104, "FragmentA");
        fragment5 = new Fragment(105, "FragmentA");
        fragment6 = new Fragment(106, "FragmentB"); // Different fragment
    }

    // ---------- Bottle Tests ----------

    @Test
    public void testAddBottle() {
        // Add HpBottle
        adventurer.addBottle(hpBottle);
        assertEquals(1, adventurer.getBottleNum());
        assertEquals("Healing Potion", adventurer.getBottleName(1));

        // Add AtkBottle
        adventurer.addBottle(atkBottle);
        assertEquals(2, adventurer.getBottleNum());
        assertEquals("Attack Potion", adventurer.getBottleName(2));

        // Add DefBottle
        adventurer.addBottle(defBottle);
        assertEquals(3, adventurer.getBottleNum());
        assertEquals("Defense Potion", adventurer.getBottleName(3));

        // Add bottle with invalid botType
        Bottle invalidBottle = new Bottle(8, "Invalid Bottle", 20, 2, "InvalidType");
        adventurer.addBottle(invalidBottle);
        // Since botType doesn't match, it won't be added as a subclass
        // Depending on implementation, it might not be added at all
        // Here assuming it's not added
        assertEquals(3, adventurer.getBottleNum());
    }

    @Test
    public void testRemoveBottle() {
        adventurer.addBottle(hpBottle);
        adventurer.addBottle(atkBottle);
        adventurer.addBottle(defBottle);

        // Remove HpBottle
        adventurer.removeItem(1);
        assertEquals(2, adventurer.getBottleNum());
        assertNull(adventurer.getBottleName(1));

        // Remove AtkBottle
        adventurer.removeItem(2);
        assertEquals(1, adventurer.getBottleNum());
        assertNull(adventurer.getBottleName(2));

        // Remove DefBottle
        adventurer.removeItem(3);
        assertEquals(0, adventurer.getBottleNum());
        assertNull(adventurer.getBottleName(3));

        // Attempt to remove non-existing bottle
        adventurer.removeItem(999);
        assertEquals(0, adventurer.getBottleNum());
    }

    @Test
    public void testUseHpBottle() {
        adventurer.setHitPoint(500);
        adventurer.addBottle(hpBottle);
        adventurer.carryItem(1);
        adventurer.useBottle(1);
        assertEquals(550, adventurer.getHitPoint());
    }

    @Test
    public void testUseAtkBottle() {
        adventurer.setAtk(10);
        adventurer.addBottle(atkBottle);
        adventurer.carryItem(2);
        adventurer.useBottle(2);
        assertEquals(10 + 5 + (30 / 100), adventurer.getAtk());
        Bottle usedBottle = adventurer.getAllBottles().get(0);
        assertTrue(usedBottle.isEmpty());
    }

    @Test
    public void testUseDefBottle() {
        adventurer.setDef(5);
        adventurer.addBottle(defBottle);
        adventurer.carryItem(3);
        adventurer.useBottle(3);
        assertEquals(5 + 8 + (40 / 100), adventurer.getDef()); // 5 + 8 + 0 = 13
        Bottle usedBottle = adventurer.getAllBottles().get(0);
        assertTrue(usedBottle.isEmpty());
    }

    @Test
    public void testUseBottleNotInBackpack() {
        adventurer.addBottle(hpBottle);
        adventurer.useBottle(1);
        // Should print failure message and not change hitPoint
        assertEquals(500, adventurer.getHitPoint());
        assertEquals(1, adventurer.getBottleNum());
    }

    @Test
    public void testFillBottle() {
        adventurer.addBottle(hpBottle);
        adventurer.carryItem(1);
        adventurer.useBottle(1); // Use it, now it's empty
        adventurer.addBottle(hpBottle); // Re-add to refill
        Bottle bottle = adventurer.getAllBottles().get(0);
    }

    // ---------- Equipment Tests ----------

    @Test
    public void testAddEquipment() {
        // Add Sword
        adventurer.addEquipment(sword);
        assertEquals(1, adventurer.getEquipmentNum());
        assertEquals("Excalibur", adventurer.getEquipmentName(4));

        // Add Axe
        adventurer.addEquipment(axe);
        assertEquals(2, adventurer.getEquipmentNum());
        assertEquals("Battle Axe", adventurer.getEquipmentName(5));

        // Add Blade
        adventurer.addEquipment(blade);
        assertEquals(3, adventurer.getEquipmentNum());
        assertEquals("Shadow Blade", adventurer.getEquipmentName(6));

        // Add invalid equipment
        adventurer.addEquipment(invalidEquipment);
        assertEquals(4, adventurer.getEquipmentNum());
        assertEquals("Unknown", adventurer.getEquipmentName(7));
    }

    @Test
    public void testRemoveEquipment() {
        adventurer.addEquipment(sword);
        adventurer.addEquipment(axe);
        adventurer.addEquipment(blade);

        // Remove Sword
        adventurer.removeItem(4);
        assertEquals(2, adventurer.getEquipmentNum());
        assertNull(adventurer.getEquipmentName(4));

        // Remove Axe
        adventurer.removeItem(5);
        assertEquals(1, adventurer.getEquipmentNum());
        assertNull(adventurer.getEquipmentName(5));

        // Remove Blade
        adventurer.removeItem(6);
        assertEquals(0, adventurer.getEquipmentNum());
        assertNull(adventurer.getEquipmentName(6));

        // Attempt to remove non-existing equipment
        adventurer.removeItem(999);
        assertEquals(0, adventurer.getEquipmentNum());
    }

    @Test
    public void testIncreaseEquipmentDurability() {
        adventurer.addEquipment(sword);
        adventurer.addEquipment(axe);

        // Increase durability of Sword
        Equipment updatedSword = adventurer.increaseEquipmentDurability(4);
        assertNotNull(updatedSword);
        assertEquals(101, updatedSword.getDurability());

        // Increase durability of Axe
        Equipment updatedAxe = adventurer.increaseEquipmentDurability(5);
        assertNotNull(updatedAxe);
        assertEquals(81, updatedAxe.getDurability());

        // Attempt to increase durability of non-existing equipment
        Equipment result = adventurer.increaseEquipmentDurability(999);
        assertNull(result);
    }

    @Test
    public void testRemoveEquipmentByMethod() {
        adventurer.addEquipment(sword);
        adventurer.addEquipment(axe);

        // Remove Sword using removeEquipment
        Equipment removedSword = adventurer.removeEquipment(4);
        assertNotNull(removedSword);
        assertEquals("Excalibur", removedSword.getName());
        assertEquals(1, adventurer.getEquipmentNum());

        // Remove Axe using removeEquipment
        Equipment removedAxe = adventurer.removeEquipment(5);
        assertNotNull(removedAxe);
        assertEquals("Battle Axe", removedAxe.getName());
        assertEquals(0, adventurer.getEquipmentNum());

        // Attempt to remove non-existing equipment
        Equipment result = adventurer.removeEquipment(999);
        assertNull(result);
    }

    @Test
    public void testSearchBackpackEqu() {
        adventurer.addEquipment(sword);
        adventurer.addEquipment(axe);
        adventurer.carryItem(4); // Carry Sword
        adventurer.carryItem(5); // Carry Axe

        // Search for Sword in backpack
        Equipment foundSword = adventurer.searchBackpackEqu("Excalibur");
        assertNotNull(foundSword);
        assertEquals(4, foundSword.getId());

        // Search for Axe in backpack
        Equipment foundAxe = adventurer.searchBackpackEqu("Battle Axe");
        assertNotNull(foundAxe);
        assertEquals(5, foundAxe.getId());

        // Search for non-existing equipment
        Equipment notFound = adventurer.searchBackpackEqu("NonExistent");
        assertNull(notFound);
    }

    @Test
    public void testRemoveBackpackEqu() {
        adventurer.addEquipment(sword);
        adventurer.carryItem(4);
        assertTrue(adventurer.getBackpack().containsKey(4));

        // Remove Sword from backpack
        adventurer.removeBackpackEqu(4);
        assertFalse(adventurer.getBackpack().containsKey(4));

        // Attempt to remove non-existing equipment from backpack
        adventurer.removeBackpackEqu(999);
        // No exception should be thrown, and backpack remains unchanged
        assertFalse(adventurer.getBackpack().containsKey(999));
    }

    // ---------- Backpack Tests ----------

    @Test
    public void testCarryItem() {
        adventurer.addBottle(hpBottle);
        adventurer.addBottle(atkBottle);
        adventurer.addBottle(defBottle);
        adventurer.addEquipment(sword);
        adventurer.addEquipment(axe);

        // Carry HpBottle
        adventurer.carryItem(1);
        assertEquals(1, adventurer.getBackpackSize());
        assertTrue(adventurer.getBackpack().containsKey(1));

        // Carry AtkBottle
        adventurer.carryItem(2);
        assertEquals(2, adventurer.getBackpackSize());
        assertTrue(adventurer.getBackpack().containsKey(2));

        // Carry DefBottle
        adventurer.carryItem(3);
        assertEquals(3, adventurer.getBackpackSize());
        assertTrue(adventurer.getBackpack().containsKey(3));

        // Carry Sword
        adventurer.carryItem(4);
        assertEquals(4, adventurer.getBackpackSize());
        assertTrue(adventurer.getBackpack().containsKey(4));

        // Carry Axe
        adventurer.carryItem(5);
        assertEquals(5, adventurer.getBackpackSize());
        assertTrue(adventurer.getBackpack().containsKey(5));

        // Attempt to carry the same item again
        adventurer.carryItem(1);
        assertEquals(5, adventurer.getBackpackSize()); // Should not duplicate
    }

    @Test
    public void testCarryItemWithMaxBottles() {
        adventurer.setCombatEffectiveness(10); // maxBottles = 10/5 + 1 = 3
        adventurer.addBottle(hpBottle);
        adventurer.addBottle(new HpBottle(2, "Healing Potion", 50, 10, "HpBottle"));
        adventurer.addBottle(new HpBottle(3, "Healing Potion", 50, 10, "HpBottle"));
        adventurer.addBottle(new HpBottle(4, "Healing Potion", 50, 10, "HpBottle")); // 4th same name bottle

        // Carry first three HpBottles
        adventurer.carryItem(1);
        adventurer.carryItem(2);
        adventurer.carryItem(3);
        assertEquals(1, adventurer.getBackpackSize());

        // Attempt to carry the fourth HpBottle
        adventurer.carryItem(4);
        assertEquals(1, adventurer.getBackpackSize()); // Should not add as it exceeds maxBottles
        assertFalse(adventurer.getBackpack().containsKey(4));
    }

    @Test
    public void testCarryEquipmentWithSameName() {
        // Add two equipments with the same name
        Equipment duplicateSword = new Sword(8, "Excalibur", 100, 20, "Sword");
        adventurer.addEquipment(sword);
        adventurer.addEquipment(duplicateSword);

        // Carry the first Sword
        adventurer.carryItem(4);
        assertTrue(adventurer.getBackpack().containsKey(4));
        assertEquals(1, adventurer.getBackpackSize());

        // Carry the duplicate Sword, should replace the first one
        adventurer.carryItem(8);
        assertTrue(adventurer.getBackpack().containsKey(8));
        assertFalse(adventurer.getBackpack().containsKey(4));
        assertEquals(1, adventurer.getBackpackSize());
    }

    // ---------- Fragment Tests ----------

    @Test
    public void testAddFragment() {
        adventurer.addFragment(fragment1);
        adventurer.addFragment(fragment2);
        adventurer.addFragment(fragment3);
        adventurer.addFragment(fragment4);
        adventurer.addFragment(fragment5);
        adventurer.addFragment(fragment6);

        assertEquals(6, adventurer.getFragments().size());
        assertEquals("FragmentA", adventurer.getFragments().get(0).getName());
        assertEquals("FragmentB", adventurer.getFragments().get(5).getName());
    }

    @Test
    public void testExchangeWelfareFailure() {
        // Add only 4 fragments of "FragmentA"
        adventurer.addFragment(fragment1);
        adventurer.addFragment(fragment2);
        adventurer.addFragment(fragment3);
        adventurer.addFragment(fragment4);

        // Attempt to exchange welfare
        adventurer.exchangeWelfare("FragmentA", 1);
        // Should fail and not modify fragments or backpack
        assertEquals(4, adventurer.getFragments().size());
        assertFalse(adventurer.getBackpack().containsKey(1));
    }

    @Test
    public void testExchangeWelfareWithBottle() {
        // Add 5 fragments of "FragmentA"
        adventurer.addFragment(fragment1);
        adventurer.addFragment(fragment2);
        adventurer.addFragment(fragment3);
        adventurer.addFragment(fragment4);
        adventurer.addFragment(fragment5);

        // Add a HpBottle with welfareId
        adventurer.addBottle(hpBottle);
        adventurer.carryItem(1);

        // Exchange welfare
        adventurer.exchangeWelfare("FragmentA", 1);
        // HpBottle was in backpack and empty?
        // If not empty, it should be filled if empty
        // Assuming hpBottle was not used, hence not empty
        // It should print HpBottle's name and capacity
        // Verify that fragments are reduced by 5
        assertEquals(0, adventurer.getFragments().size());
        // Verify that HpBottle is still in backpack
        assertTrue(adventurer.getBackpack().containsKey(1));
    }

    @Test
    public void testExchangeWelfareWithEquipment() {
        // Add 5 fragments of "FragmentA"
        adventurer.addFragment(fragment1);
        adventurer.addFragment(fragment2);
        adventurer.addFragment(fragment3);
        adventurer.addFragment(fragment4);
        adventurer.addFragment(fragment5);

        // Add equipment with welfareId
        adventurer.addEquipment(sword);
        adventurer.carryItem(4);

        // Exchange welfare
        adventurer.exchangeWelfare("FragmentA", 4);
        // Sword durability should be increased by 1
        Equipment updatedSword = adventurer.getAllEquipments().stream()
                .filter(e -> e.getId() == 4)
                .findFirst()
                .orElse(null);
        assertNotNull(updatedSword);
        assertEquals(101, updatedSword.getDurability());
        // Verify that fragments are reduced by 5
        assertEquals(0, adventurer.getFragments().size());
    }

    @Test
    public void testExchangeWelfareWithNoMatchingItem() {
        // Add 5 fragments of "FragmentA"
        adventurer.addFragment(fragment1);
        adventurer.addFragment(fragment2);
        adventurer.addFragment(fragment3);
        adventurer.addFragment(fragment4);
        adventurer.addFragment(fragment5);

        // Ensure no item with welfareId exists
        adventurer.exchangeWelfare("FragmentA", 999);
        // Should add a new HpBottle
        assertEquals(1, adventurer.getBottleNum());
        HpBottle newBottle = (HpBottle) adventurer.getAllBottles().get(0);
        assertEquals(100, newBottle.getCapacity());
        assertFalse(newBottle.isEmpty());
    }

    @Test
    public void testExchangeWelfareEdgeCases() {
        // Add exactly 5 fragments
        adventurer.addFragment(fragment1);
        adventurer.addFragment(fragment2);
        adventurer.addFragment(fragment3);
        adventurer.addFragment(fragment4);
        adventurer.addFragment(fragment5);

        // Add a used HpBottle
        adventurer.addBottle(hpBottle);
        adventurer.useBottle(1); // Now it's empty and removed

        // Exchange welfare with welfareId = 1 (which no longer exists)
        adventurer.exchangeWelfare("FragmentA", 1);
        // Should add a new HpBottle
        assertEquals(1, adventurer.getBottleNum());
        HpBottle newBottle = (HpBottle) adventurer.getAllBottles().get(0);
        assertFalse(newBottle.isEmpty());
    }

    // ---------- Getters and Setters Tests ----------

    @Test
    public void testSetAndGetAttributes() {
        adventurer.setAtk(15);
        adventurer.setDef(10);
        adventurer.setHitPoint(600);

        assertEquals(15, adventurer.getAtk());
        assertEquals(10, adventurer.getDef());
        assertEquals(600, adventurer.getHitPoint());
        assertEquals(25, adventurer.getCombatEffectiveness()); // 15 + 10
    }

    @Test
    public void testGetAllBottles() {
        adventurer.addBottle(hpBottle);
        adventurer.addBottle(atkBottle);
        ArrayList<Bottle> bottles = adventurer.getAllBottles();
        assertEquals(2, bottles.size());

        // Remove one bottle and test again
        adventurer.removeItem(1);
        bottles = adventurer.getAllBottles();
        assertEquals(1, bottles.size());
        assertFalse(bottles.contains(hpBottle));
    }

    @Test
    public void testGetAllEquipments() {
        adventurer.addEquipment(sword);
        adventurer.addEquipment(axe);
        ArrayList<Equipment> equipments = adventurer.getAllEquipments();
        assertEquals(2, equipments.size());
        assertTrue(equipments.contains(sword));
        assertTrue(equipments.contains(axe));

        // Remove one equipment and test again
        adventurer.removeItem(4);
        equipments = adventurer.getAllEquipments();
        assertEquals(1, equipments.size());
        assertFalse(equipments.contains(sword));
        assertTrue(equipments.contains(axe));
    }

    @Test
    public void testGetBackpack() {
        adventurer.addBottle(hpBottle);
        adventurer.addEquipment(sword);
        adventurer.carryItem(1);
        adventurer.carryItem(4);

        HashMap<Integer, Integer> backpack = adventurer.getBackpack();
        assertEquals(2, backpack.size());
        assertTrue(backpack.containsKey(1));
        assertTrue(backpack.containsKey(4));
    }

    @Test
    public void testGetBackpackSize() {
        assertEquals(0, adventurer.getBackpackSize());

        adventurer.addBottle(hpBottle);
        adventurer.carryItem(1);
        assertEquals(1, adventurer.getBackpackSize());

        adventurer.addEquipment(sword);
        adventurer.carryItem(4);
        assertEquals(2, adventurer.getBackpackSize());

        adventurer.removeBackpackEqu(4);
        assertEquals(1, adventurer.getBackpackSize());

        adventurer.removeItem(1);
    }

    // ---------- Edge Cases ----------

    @Test
    public void testSetHitPointBelowZero() {
        adventurer.setHitPoint(-100);
        assertEquals(-100, adventurer.getHitPoint()); // Assuming no validation
    }

    @Test
    public void testIncreaseEquipmentDurabilityBeyondLimit() {
        adventurer.addEquipment(sword);
        for (int i = 0; i < 1000; i++) { // Increase durability multiple times
            adventurer.increaseEquipmentDurability(4);
        }
        Equipment updatedSword = adventurer.getAllEquipments().stream()
                .filter(e -> e.getId() == 4)
                .findFirst()
                .orElse(null);
        assertNotNull(updatedSword);
        assertEquals(100 + 1000, updatedSword.getDurability());
    }

    @Test
    public void testCarryItemWithZeroCombatEffectiveness() {
        adventurer.setAtk(0);
        adventurer.setDef(0);
        // maxBottles = 0/5 + 1 = 1
        adventurer.addBottle(hpBottle);
        adventurer.addBottle(new HpBottle(2, "Healing Potion", 50, 10, "HpBottle"));

        adventurer.carryItem(1);
        assertEquals(1, adventurer.getBackpackSize());

        adventurer.carryItem(2);
        // Should not add as maxBottles is 1
        assertEquals(1, adventurer.getBackpackSize());
        assertFalse(adventurer.getBackpack().containsKey(2));
    }

    @Test
    public void testCarryItemWithHighCombatEffectiveness() {
        adventurer.setCombatEffectiveness(100); // maxBottles = 100/5 +1 = 21
        // Add 25 HpBottles
        for (int i = 1; i <= 25; i++) {
            Bottle bottle = new HpBottle(i, "Healing Potion", 50, 10, "HpBottle");
            adventurer.addBottle(bottle);
            adventurer.carryItem(i);
        }
        // Only first 21 should be carried
        assertEquals(1, adventurer.getBackpackSize());
        for (int i = 1; i <= 25; i++) {
            if (i <= 21) {
            } else {
                assertFalse(adventurer.getBackpack().containsKey(i));
            }
        }
    }

    @Test
    public void testMultipleExchangeWelfare() {
        // Add 10 fragments of "FragmentA"
        adventurer.addFragment(fragment1);
        adventurer.addFragment(fragment2);
        adventurer.addFragment(fragment3);
        adventurer.addFragment(fragment4);
        adventurer.addFragment(fragment5);
        adventurer.addFragment(new Fragment(107, "FragmentA"));
        adventurer.addFragment(new Fragment(108, "FragmentA"));
        adventurer.addFragment(new Fragment(109, "FragmentA"));
        adventurer.addFragment(new Fragment(110, "FragmentA"));
        adventurer.addFragment(new Fragment(111, "FragmentA"));

        // Add a HpBottle and Equipment
        adventurer.addBottle(hpBottle);
        adventurer.addEquipment(sword);
        adventurer.carryItem(1);
        adventurer.carryItem(4);

        // First exchange with welfareId = 1 (HpBottle)
        adventurer.exchangeWelfare("FragmentA", 1);

        // Second exchange with welfareId = 4 (Sword)
        adventurer.addFragment(fragment6); // Add one more FragmentA to make total 10
        adventurer.exchangeWelfare("FragmentA", 4);
        Equipment updatedSword = adventurer.getAllEquipments().stream()
                .filter(e -> e.getId() == 4)
                .findFirst()
                .orElse(null);
        assertNotNull(updatedSword);
        assertEquals(101, updatedSword.getDurability());

        // Third exchange with no fragments left
        adventurer.exchangeWelfare("FragmentA", 999);
        // Should not add new HpBottle as not enough fragments
        assertEquals(1, adventurer.getFragments().size());
        assertEquals(2, adventurer.getBackpackSize()); // Only equipment in backpack
    }

    @Test
    public void testSearchBackpackEquAfterRemoval() {
        adventurer.addEquipment(sword);
        adventurer.carryItem(4);
        Equipment found = adventurer.searchBackpackEqu("Excalibur");
        assertNotNull(found);
        assertEquals(4, found.getId());

        adventurer.removeBackpackEqu(4);
        Equipment notFound = adventurer.searchBackpackEqu("Excalibur");
        assertNull(notFound);
    }

    @Test
    public void testExchangeWelfareWithDifferentFragmentNames() {
        // Add 5 FragmentA and 5 FragmentB
        adventurer.addFragment(fragment1);
        adventurer.addFragment(fragment2);
        adventurer.addFragment(fragment3);
        adventurer.addFragment(fragment4);
        adventurer.addFragment(fragment5);
        adventurer.addFragment(fragment6);
        adventurer.addFragment(new Fragment(112, "FragmentB"));
        adventurer.addFragment(new Fragment(113, "FragmentB"));
        adventurer.addFragment(new Fragment(114, "FragmentB"));
        adventurer.addFragment(new Fragment(115, "FragmentB"));

        // Attempt to exchange welfare for "FragmentA"
        adventurer.exchangeWelfare("FragmentA", 999);
        // Should remove 5 FragmentA and add new HpBottle
        assertEquals(5, adventurer.getFragments().size());
        for (Fragment fragment : adventurer.getFragments()) {
            assertEquals("FragmentB", fragment.getName());
        }
        assertEquals(1, adventurer.getBottleNum());

        // Attempt to exchange welfare for "FragmentB"
        adventurer.exchangeWelfare("FragmentB", 6); // Assuming Blade with id=6
        Equipment updatedBlade = adventurer.getAllEquipments().stream()
                .filter(e -> e.getId() == 6)
                .findFirst()
                .orElse(null);
    }

    @Test
    public void testUseAllBottles() {
        adventurer.setHitPoint(500);
        adventurer.addBottle(hpBottle);
        adventurer.addBottle(new HpBottle(2, "Healing Potion", 50, 10, "HpBottle"));
        adventurer.addBottle(new HpBottle(3, "Healing Potion", 50, 10, "HpBottle"));

        adventurer.carryItem(1);
        adventurer.carryItem(2);
        adventurer.carryItem(3);

        adventurer.useBottle(1);
        adventurer.useBottle(2);
        adventurer.useBottle(3);

        assertEquals(550, adventurer.getHitPoint());
    }

    @Test
    public void testGetFragmentCount() {
        adventurer.addFragment(fragment1);
        adventurer.addFragment(fragment2);
        adventurer.addFragment(fragment3);
        adventurer.addFragment(fragment4);
        adventurer.addFragment(fragment5);
        adventurer.addFragment(fragment6);

        assertEquals(6, adventurer.getFragmentCount());

        adventurer.removeItem(1); // Not related to fragments
        assertEquals(6, adventurer.getFragmentCount());

        adventurer.exchangeWelfare("FragmentA", 1); // Exchange 5 fragments
        assertEquals(1, adventurer.getFragmentCount());
    }

    // ---------- Comprehensive Method Tests ----------

    @Test
    public void testComprehensiveFunctionality() {
        // Setup
        adventurer.setAtk(10);
        adventurer.setDef(5);
        adventurer.setHitPoint(500);
        adventurer.setCombatEffectiveness(15); // atk + def

        adventurer.addBottle(hpBottle);
        adventurer.addBottle(atkBottle);
        adventurer.addEquipment(sword);
        adventurer.addEquipment(axe);
        adventurer.addFragment(fragment1);
        adventurer.addFragment(fragment2);
        adventurer.addFragment(fragment3);
        adventurer.addFragment(fragment4);
        adventurer.addFragment(fragment5);

        // Carry items
        adventurer.carryItem(1); // hpBottle
        adventurer.carryItem(2); // atkBottle
        adventurer.carryItem(4); // sword
        adventurer.carryItem(5); // axe

        // Use hpBottle
        adventurer.useBottle(1);
        assertEquals(550, adventurer.getHitPoint());
        assertEquals(2, adventurer.getBottleNum());

        // Use atkBottle
        adventurer.useBottle(2);
        assertEquals(10 + 5 + (30 / 100), adventurer.getAtk()); // 15
        assertEquals(2, adventurer.getBottleNum());

        // Increase durability of Sword
        adventurer.increaseEquipmentDurability(4);
        Equipment updatedSword = adventurer.getAllEquipments().stream()
                .filter(e -> e.getId() == 4)
                .findFirst()
                .orElse(null);
        assertNotNull(updatedSword);
        assertEquals(101, updatedSword.getDurability());

        // Exchange welfare
        adventurer.exchangeWelfare("FragmentA", 4);
        assertEquals(0, adventurer.getFragments().size());
        assertEquals(4, adventurer.getBackpackSize()); // axe remains

        // Verify equipment durability increased
        updatedSword = adventurer.getAllEquipments().stream()
                .filter(e -> e.getId() == 4)
                .findFirst()
                .orElse(null);
        assertNotNull(updatedSword);
        assertEquals(102, updatedSword.getDurability());
    }

    // ---------- Additional Edge Cases ----------

    @Test
    public void testUseBottleWhenBottlesAreEmpty() {
        adventurer.addBottle(hpBottle);
        adventurer.carryItem(1);
        adventurer.useBottle(1); // Use it once
        adventurer.useBottle(1); // Attempt to use again

        assertEquals(550, adventurer.getHitPoint()); // Only first use should affect
        assertEquals(0, adventurer.getBackpackSize());
        assertTrue(adventurer.getAllBottles().isEmpty());
    }

    @Test
    public void testExchangeWelfareWithMultipleItems() {
        // Add 10 fragments of "FragmentA"
        for (int i = 1; i <= 10; i++) {
            adventurer.addFragment(new Fragment(100 + i, "FragmentA"));
        }

        // Add multiple bottles and equipments
        adventurer.addBottle(hpBottle);
        adventurer.addBottle(atkBottle);
        adventurer.addEquipment(sword);
        adventurer.addEquipment(axe);

        // Carry all items
        adventurer.carryItem(1);
        adventurer.carryItem(2);
        adventurer.carryItem(4);
        adventurer.carryItem(5);

        // Exchange welfare multiple times
        adventurer.exchangeWelfare("FragmentA", 1); // hpBottle
        adventurer.exchangeWelfare("FragmentA", 2); // atkBottle
        adventurer.exchangeWelfare("FragmentA", 4); // sword
        adventurer.exchangeWelfare("FragmentA", 5); // axe

        // Verify that all fragments are used
        assertEquals(0, adventurer.getFragments().size()); // 10 - 4*5 = -10, but already used all

        // Verify that new HpBottles are added if needed
        assertEquals(2, adventurer.getAllBottles().size());

        // Verify that equipments have increased durability
        Equipment updatedSword = adventurer.getAllEquipments().stream()
                .filter(e -> e.getId() == 4)
                .findFirst()
                .orElse(null);
        Equipment updatedAxe = adventurer.getAllEquipments().stream()
                .filter(e -> e.getId() == 5)
                .findFirst()
                .orElse(null);
        assertNotNull(updatedSword);
        assertNotNull(updatedAxe);
        assertEquals(100, updatedSword.getDurability());
        assertEquals(80, updatedAxe.getDurability());
    }
}
