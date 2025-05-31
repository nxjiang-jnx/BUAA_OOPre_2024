/**
 * 暗影
 *
 * @author jhria
 * @since 2024/10/20 下午3:43
 */

class Shd implements Guard {
    @Override
    public boolean fight(Adventurer adv) {
        return adv.getComprehensiveCE() > 1000;
    }

    @Override
    public String getType() {
        return "Shd";
    }
}
