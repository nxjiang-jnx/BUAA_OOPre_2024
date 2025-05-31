public class Var implements Factor {
    private final String name;

    public Var(String name) {
        this.name = name;
    }

    public final String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public void print() {
        System.out.println("Var " + this);
    }
}
