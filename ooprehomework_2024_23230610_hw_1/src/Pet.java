public class Pet {
    private String name;
    private int hunger;
    private int happiness;
    private int health;
    private int biscuitCount;
    private final int BISCUIT_HUNGER_REDUCTION = 10;
    private final int FRUIT_HUNGER_REDUCTION = 8;
    private final int MAX_BISCUIT_COUNT = 3;

    public Pet(String name, int hunger, int happiness, int health) {
        this.name = name;
        this.hunger = hunger;
        this.happiness = happiness;
        this.health = health;
        this.biscuitCount = 0;
    }

    public void eat(String foodName) {
        if (foodName.equals("biscuit")) {
            biscuitCount++;
            if (biscuitCount > MAX_BISCUIT_COUNT) {
                health -= 5;
            }
            hunger -= BISCUIT_HUNGER_REDUCTION;
        } else {
            hunger -= FRUIT_HUNGER_REDUCTION;
        }
        hunger = Math.max(hunger, 0);
    }

    public void play() {
        happiness += 5;
        if (happiness > 100) {
            happiness = 100;
        }
    }

    public void printStatus() {
        System.out.println(name + ":");
        System.out.println("Hunger: " + hunger);
        System.out.println("Happiness: " + happiness);
        System.out.println("Health: " + health);
    }
}
