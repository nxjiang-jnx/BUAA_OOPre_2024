public class Player {
    private Pet pet;
    private int exp;
    private final int FEED_EXP = 5;
    private final int PLAY_EXP = 7;

    public Player() {
        exp = 0;
    }

    public void feedPet(String foodName) {
        pet.eat(foodName);
        exp += FEED_EXP;
    }

    public void playWithPet() {
        pet.play();
        exp += PLAY_EXP;
    }

    public void checkPetStatus() {
        pet.printStatus();
    }

    public void printStatus() {
        System.out.println("Final Player Exp: "+ exp);
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }
}