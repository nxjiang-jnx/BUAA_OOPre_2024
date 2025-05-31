/**
 * @author jhria
 * @since 2024/10/20 下午3:59
 */

class ShdTreasure implements Treasure {
    @Override
    public void showInfo() {
        System.out.println("Cloak of Shadows");
    }

    @Override
    public void useBy(Adventurer adv) {
        //冒险者及其雇佣的所有冒险者防御力加40
        adv.setDef(adv.getDef() + 40);
        //遍历hashMap
        for (Adventurer adventurer : adv.getHiredAdventurers().keySet()) {
            adventurer.setDef(adventurer.getDef() + 40);
        }
    }
}
