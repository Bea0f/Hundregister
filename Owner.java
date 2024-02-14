package bea.prog1;

/**
 * @author Beatrice Friberg befr6512
 */

public class Owner {


    private final String name;
    private Dog[] ownerDogs = new Dog[0];


    public Owner(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    private boolean ownerOfDog(Dog d) {
        Owner o = d.getOwner();
        return this == o;
    }

    public void addDogToOwner(Dog d) {
        if (ownerOfDog(d)) {
            Dog[] listTwo = new Dog[ownerDogs.length + 1];
            System.arraycopy(ownerDogs, 0, listTwo, 0, ownerDogs.length);
            listTwo[listTwo.length - 1] = d;
            ownerDogs = new Dog[listTwo.length];
            System.arraycopy(listTwo, 0, ownerDogs, 0, listTwo.length);
        }
        if (d.getOwner() == null) {
            d.setOwner(this);
        }
    }

    public void printDogs() {

        if (ownerDogs.length != 0) {
            System.out.print(getName() + " [");
            for (Dog ownerDog : ownerDogs) {
                System.out.print(ownerDog.getName() + ", ");
            }
            System.out.print("]\n");
        } else {
            System.out.println(getName() + " []");

        }

    }

    public void removeDogFromOwner(Dog d) {

        Dog[] remove = new Dog[ownerDogs.length - 1];

        for (int i = 0, k = 0; i < ownerDogs.length; i++) {
            if (ownerDogs[i] != d) {
                remove[k++] = ownerDogs[i];
            }
        }

        ownerDogs = new Dog[remove.length];
        System.arraycopy(remove, 0, ownerDogs, 0, remove.length);

    }

    public String toString() {
        return name;
    }

}
