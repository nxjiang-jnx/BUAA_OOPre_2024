/**
 * 风灵
 *
 * @author jhria
 * @since 2024/10/20 下午3:56
 */

class Wnd implements Guard {
    @Override
    public boolean fight(Adventurer adv) {
        return adv.getComprehensiveCE() > 4000;
    }

    @Override
    public String getType() {
        return "Wnd";
    }
}
