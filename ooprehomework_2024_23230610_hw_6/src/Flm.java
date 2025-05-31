/**
 * 烈焰
 *
 * @author jhria
 * @since 2024/10/20 下午3:53
 */

class Flm implements Guard {
    @Override
    public boolean fight(Adventurer adv) {
        return adv.getComprehensiveCE() > 2000;
    }

    @Override
    public String getType() {
        return "Flm";
    }
}
