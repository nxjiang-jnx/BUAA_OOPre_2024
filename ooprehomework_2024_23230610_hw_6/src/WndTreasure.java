/**
 * @author jhria
 * @since 2024/10/20 下午4:07
 */

class WndTreasure implements Treasure {
    @Override
    public void showInfo() {
        System.out.println("Windrunner Boots");
    }

    @Override
    public void useBy(Adventurer adv) {
        //冒险者及其雇佣的所有冒险者防御力加30
        adv.setDef(adv.getDef() + 30);
        //遍历hashMap
        for (Adventurer adventurer : adv.getHiredAdventurers().keySet()) {
            adventurer.setDef(adventurer.getDef() + 30);
        }
    }
}
