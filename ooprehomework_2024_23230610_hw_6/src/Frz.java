/**
 * 冰霜
 *
 * @author jhria
 * @since 2024/10/20 下午3:57
 */

class Frz implements Guard {
    @Override
    public boolean fight(Adventurer adv) {
        return adv.getComprehensiveCE() > 5000;
    }

    @Override
    public String getType() {
        return "Frz";
    }
}
