/**
 * @author jhria
 * @since 2024/10/20 下午4:04
 */

class FlmTreasure implements Treasure {
    @Override
    public void showInfo() {
        System.out.println("Flamebrand Sword");
    }

    @Override
    public void useBy(Adventurer adv) {
        //冒险者及其雇佣的所有冒险者每人增加攻击力40
        adv.setAtk(adv.getAtk() + 40);
        //遍历hashmap
        for (Adventurer adventurer : adv.getHiredAdventurers().keySet()) {
            adventurer.setAtk(adventurer.getAtk() + 40);
        }
    }
}
