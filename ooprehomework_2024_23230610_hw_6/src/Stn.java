/**
 * 石卫
 *
 * @author jhria
 * @since 2024/10/20 下午3:54
 */

class Stn implements Guard {
    @Override
    public boolean fight(Adventurer adv) {
        return adv.getComprehensiveCE() > 3000;
    }

    @Override
    public String getType() {
        return "Stn";
    }
}
