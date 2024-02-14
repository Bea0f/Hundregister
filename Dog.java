package bea.prog1;

/**
 * @author Beatrice Friberg befr6512
 */

public class Dog {

    private static final double DACHSHUND_TAIL_LENGTH = 3.7;
    private final String name;
    private final String breed;
    private int age;
    private final int weight;
    private double tailLength;
    private Owner owner;
    private boolean inAuction;
    private int currentBid;


    public Dog(String name, String breed, int age, int weight) {
        this.name = name;
        this.breed = breed;
        this.age = age;
        this.weight = weight;
        if (breed.equalsIgnoreCase("dachshund") || breed.equalsIgnoreCase("Tax")) {
            this.tailLength = DACHSHUND_TAIL_LENGTH;
        } else {
            this.tailLength = (double) age * (double) weight / 10;
        }


    }

    public String getName() {
        return name;
    }

    public String getBreed() {
        return breed;
    }

    public int getWeight() {
        return weight;
    }

    public int getAge() {
        return age;
    }

    public double getTailLength() {
        return tailLength;
    }

    public void newAge() {
        age++;
        if (breed.equalsIgnoreCase("dachshund") || breed.equalsIgnoreCase("Tax")) {
            this.tailLength = DACHSHUND_TAIL_LENGTH;
        } else {
            this.tailLength = (double) age * (double) weight / 10;
        }
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner o) {
        this.owner = o;
        o.addDogToOwner(this);

        if (this.owner == null) {
            System.out.println("Error: the dog already has an owner ");
        }
    }

    public int getCurrentBid() {
        return currentBid + 1;
    }

    public void setCurrentBid(int currentBid) {
        this.currentBid = currentBid;
    }

    public boolean getInAuction() {
        return inAuction;

    }

    public void setInAuction(boolean inAuction) {
        this.inAuction = inAuction;
    }

    public String toString() {
        return "Name: " + name + "\nBreed: " + breed + "\nAge: " + age + "\nWeight: " + weight +
                "\nTail length: " + tailLength;
    }

}

