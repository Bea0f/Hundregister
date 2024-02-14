package bea.prog1;

/**
 * @author Beatrice Friberg befr6512
 */

public class Bid {

    private final Owner owner;
    private final Dog dog;
    private final int bid;


    public Bid(Owner owner, Dog dog, int bid) {
        this.owner = owner;
        this.dog = dog;
        this.bid = bid;

    }

    public Owner getOwner() {
        return owner;
    }

    public Dog getDog() {
        return dog;
    }

    public int getBid() {
        return bid;
    }

    @Override
    public String toString() {
        return "Bid made";

    }
}
