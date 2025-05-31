public class Num implements Factor {
    private final int value;

    public Num(int value) {
        this.value = value;
    }

    public final int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public void print() {
        System.out.println("Num " + this);
    }
}
