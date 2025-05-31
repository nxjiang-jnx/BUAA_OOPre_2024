/**
 * @author jhria
 * @since 2024/10/20 下午4:08
 */

class FrzTreasure implements Treasure {
    @Override
    public void showInfo() {
        System.out.println("Frostbite Staff");
    }

    @Override
    public void useBy(Adventurer adv) {
        //冒险者及其雇佣的所有冒险者每人增加攻击力50
        adv.setAtk(adv.getAtk() + 50);
        //遍历hashmap
        for (Adventurer adventurer : adv.getHiredAdventurers().keySet()) {
            adventurer.setAtk(adventurer.getAtk() + 50);
        }
    }
}
