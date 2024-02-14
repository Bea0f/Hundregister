package bea.prog1;

import java.util.*;

/**
 * @author Beatrice Friberg befr6512
 */

public class Program {

    private final ArrayList<Dog> dogList = new ArrayList<>();
    private final ArrayList<Owner> ownerList = new ArrayList<>();
    private final ArrayList<Auction> auctionList = new ArrayList<>();
    private Scanner input = new Scanner(System.in);
    private int counter = 1;


    public void commands() {
        String command;
        showCommands();
        do {
            command = askForCommand();

            switch (command.toLowerCase()) {
                case "register new dog":
                    registerNewDog();
                    break;
                case "list dogs":
                    listDogs();
                    break;
                case "increase age":
                    increaseAge();
                    break;
                case "remove dog":
                    removeDog();
                    break;
                case "register new owner":
                    registerNewOwner();
                    break;
                case "give dog":
                    giveDog();
                    break;
                case "list owners":
                    listOwners();
                    break;
                case "remove owner":
                    removeOwner();
                    break;
                case "start auction":
                    startAuction();
                    break;
                case "make bid":
                    makeBid();
                    break;
                case "list bids":
                    listBids();
                    break;
                case "list auctions":
                    listAuctions();
                    break;
                case "close auction":
                    closeAuction();
                    break;
                case "exit":
                    input.close();
                    break;
                default: System.out.println("Error: The command \"" + command + "\" is not valid");
            }
        } while (!command.equals("exit"));

    }

    private void showCommands (){
        System.out.println("Welcome! \n");

        System.out.print("Choose a command: \n" +
                "* register new dog \n" +
                "* list dogs \n" +
                "* increase age \n" +
                "* remove dog \n" +
                "* register new owner \n" +
                "* give dog \n" +
                "* list owners \n" +
                "* remove owner \n" +
                "* start auction \n" +
                "* make bid \n" +
                "* list bids \n" +
                "* list auctions \n" +
                "* close auction \n" +
                "* exit \n");
    }

    private String askForCommand() {

        System.out.println("Command?> ");

        return input.nextLine();
    }

    private void registerNewDog() {

        //ask for name

        String name = askForDogName();

        //ask for breed
        System.out.print("Breed?> ");
        String breed = input.nextLine();

        //ask for age
        System.out.print("Age?> ");
        int age = input.nextInt();

        input.nextLine();

        //ask for weight
        System.out.print("Weight?> ");
        int weight = input.nextInt();

        input.nextLine();

        Dog newDog = new Dog(name, breed, age, weight);
        // add to dogList
        dogList.add(newDog);
        System.out.println(name + " added to the register");

    }

    private void listDogs() {
        if (dogList.isEmpty()) {
            System.out.println("Error: no dog in register.");
        } else {
            sortDogs();
            //Ask the user for smallest tail length to show
            System.out.println("Smallest tail length to display?> ");
            double minLength = input.nextDouble();
            input.nextLine();
            System.out.println("The following dogs has such a large tail:");
            for (Dog dog : dogList) {
                if (dog.getTailLength() >= minLength) {
                    if (dog.getOwner() != null) {
                        System.out.println("*" + dog.getName() + "(" + dog.getBreed() + ", "
                                + dog.getAge() + " years, "
                                + dog.getWeight() + " kilo, " + dog.getTailLength() + " cm tail, Owned by " + dog.getOwner() + ")");
                    } else {
                        System.out.println("*" + dog.getName() + "(" + dog.getBreed() + ", "
                                + dog.getAge() + " years, "
                                + dog.getWeight() + " kilo, " + dog.getTailLength() + " cm tail)");
                    }
                } else {
                    System.out.println("No dog has longer or same length tail.");
                }
            }
        }
    }

    private void increaseAge() {
        Dog name = dogToFind();

        if (name == null) {
            System.out.println("Error: no such dog");
        } else {
            name.newAge();
            System.out.println(name.getName() + " is now 1 year older");
        }
    }

    private void removeDog() {

        Dog toRemove = dogToFind();
        if (dogList.contains(toRemove)) {
            if (toRemove.getOwner() != null) {
                toRemove.getOwner().removeDogFromOwner(toRemove);
            }
            if (toRemove.getInAuction()) {
                Auction auction = findAuction(toRemove);
                auctionList.remove(auction);
            }
            dogList.remove(toRemove);
            System.out.println(toRemove.getName() + " is removed");

        } else {
            System.out.println("Error: no such dog");
        }

    }

    private void registerNewOwner() {

        String ownerName = askForOwnerName();

        Owner newOwner = new Owner(ownerName);
        // add to list
        ownerList.add(newOwner);
        System.out.println(ownerName + " added to the register");
    }

    private void giveDog() {

        Dog d = dogToFind();
        if (!dogList.contains(d)) {
            System.out.println("Error: no such dog");
        } else if (d.getOwner() != null) {
            System.out.println("Error: the dog already has an owner ");
        } else {
            Owner o = ownerToFind();
            if (!ownerList.contains(o)) {
                System.out.println("Error: no such owner");

            } else {
                o.addDogToOwner(d);
                if (d.getInAuction()) {
                    removeAuction(d);
                }
                System.out.println(o.getName() + " now owns " + d.getName());
            }
        }
    }

    private void listOwners() {
        if (ownerList.isEmpty()) {
            System.out.println("Error: no owners in register");
        } else {
            for (Owner o : ownerList) {
                o.printDogs();
            }
        }
    }

    private void removeOwner() {

        //Ask which owner to remove
        Owner toRemove = ownerToFind();

        //Check for the owner in list
        if (!ownerList.contains(toRemove)) {
            //If not; error
            System.out.println("Error: no such Owner");

        } else {
            //If there is dogs, remove them from dogList
            ArrayList<Dog> removeList = new ArrayList<>();
            for (Dog d : dogList) {
                if (d.getOwner() == toRemove) {
                    removeList.add(d);
                    toRemove.removeDogFromOwner(d);


                }
                Auction a = findAuction(d);
                if (a != null) {
                    a.removeBidsOwner(toRemove);
                }
            }

            dogList.removeAll(removeList);

            //Remove owner from list
            ownerList.remove(toRemove);
            System.out.println(toRemove.getName() + " is removed from the register");

        }
    }

    private void startAuction() {

        Dog d = dogToFind();

        if (!dogList.contains(d)) {
            System.out.println("Error:No such dog");
        } else if (d.getOwner() != null) {
            System.out.println("Error: this dog already has an owner");
        } else {
            if (!d.getInAuction()) {
                Auction a = new Auction(d, counter);
                counter++;

                auctionList.add(a);
                System.out.println(a.toString());
            } else {
                System.out.println("Error: the dog is already up for auction");
            }

        }

    }

    private void makeBid() {
        //Name?
        Owner o = ownerToFind();
        //Check the owner
        if (!ownerList.contains(o)) {
            //If not: error
            System.out.println("Error: no such owner");
        } else {
            //If: check for the dogs name
            Dog d = dogToFind();
            //Check the dog
            if (!dogList.contains(d)) {
                //If not: error
                System.out.println("Error: No such dog");


                // check if the dog is up for auction or not
            } else if (!d.getInAuction() || d.getOwner() != null) {
                //If not: error
                System.out.println("Error: The dog is not up for auction");
            } else {

                //If in auction, ask for bid
                // + print min bid
                System.out.print("Amount to bid?> (" +
                        "min " + d.getCurrentBid() +
                        " )");
                int b = input.nextInt();
                input.nextLine();

                //Check the bid
                checkBid(o, d, b);

            }
        }
    }

    private void listBids() {

        Dog d = dogToFind();

        if (findAuction(d) == null) {
            System.out.println("Error: this dog is not up for auction");
        } else {
            findAuction(d);
            if (d.getCurrentBid() < 0) {
                System.out.println("No bids registered yet for this auction");
            } else {
                System.out.println("Here are the bids for this auction");
                Auction a = findAuction(d);
                a.sortBids();
                for (int i = 0; i < a.getBidsSize(); i++) {
                    Owner o = a.getIndex(i).getOwner();
                    int j = a.getIndex(i).getBid();
                    System.out.println(o + " " + j + " kr");

                }

            }
        }

    }

    private void listAuctions() {
        if (auctionList.isEmpty()) {
            System.out.println("Error: no auctions in progress");
        } else {

            for (Auction auction : auctionList) {
                int a = auction.getAuctionId();
                String name = auction.getDog().getName();
                System.out.print("Auction #" + a + ": " + name + ". " + "Top bids: [");
                if (auction.noBids()) {
                    System.out.print("]");
                    System.out.println();
                } else {
                    auction.sortBids();
                    for (int j = 0; j < Math.min(auction.getBidsSize(), 3); j++) {
                        System.out.print(auction.getIndex(j).getOwner().getName()
                                + " " + auction.getIndex(j).getBid()
                                + " kr, ");
                    }
                    System.out.print("]");
                    System.out.println();
                }
            }
        }
    }

    private void closeAuction() {

        Dog d = dogToFind();
        if (!dogList.contains(d)) {
            System.out.println("Error: no such dog");

        } else if (findAuction(d) == null) {
            System.out.println("Error: This dog is not up for auction");
        } else {
            Auction a = findAuction(d);
            if (a.noBids()) {
                //Close the auction, but no winner
                System.out.println("The auction is closed. No bids where made for " + findAuction(d).getDog().getName());
                removeAuction(d);
            } else {
                Bid b = a.getIndex(0);
                Owner o = b.getOwner();
                int i = b.getBid();

                //Add the dog to a owner

                o.addDogToOwner(d);
                //Close Auction
                removeAuction(d);

                System.out.print("The auction is closed. The winning bid was " + i + " kr and was made by " + o.getName());
                System.out.println();
            }
        }
    }


    private void removeAuction(Dog d) {

        Auction a = findAuction(d);
        auctionList.remove(a);
    }

    private Auction findAuction(Dog d) {
        Auction auction = null;

        if( d == null){
            return null;
        }else{
            for (Auction value : auctionList) {
                if (value.getDog().getName().equals(d.getName())) {
                    auction = value;
                }
            }
        }
        return auction;
    }

    private void checkBid(Owner o, Dog d, int b) {

        while (d.getCurrentBid() > b) {
            System.out.println("Error: bid too low!");
            System.out.print("Amount to bid?> (" +
                    "min " + d.getCurrentBid() +
                    " )");
            b = input.nextInt();
            input.nextLine();

        }
        if (b >= d.getCurrentBid()) {
            d.setCurrentBid(b);
            Bid newBid = new Bid(o, d, b);

            Auction a = findAuction(d);
            a.addBids(newBid);
            System.out.println(newBid.toString());
        }


    }

    private Owner findOwner(String name) {
        Owner owner = null;
        for (Owner value : ownerList) {
            if (value.getName().equals(name)) {
                owner = value;
            }
        }
        return owner;
    }

    private Dog findDog(String name) {
        Dog dog = null;
        for (Dog value : dogList) {
            if (value.getName().equals(name)) {
                dog = value;
            }
        }
        return dog;
    }

    private void sortDogs() {
        //Bubbelsort, tar värden i en lista och genom iterationer jämför två värden åt gången,
        // och byter plats på dem enligt ett vilkor(i detta fall om värde två är mindre än värde ett).
        // Använda källor:
        // https://www.javatpoint.com/bubble-sort-in-java,
        // https://www.youtube.com/watch?v=6Gv8vg0kcHc
        // https://www.youtube.com/watch?v=F13_wsHDIG4
        // https://www.youtube.com/watch?v=Fs_elYHrTHU&t=610s
        // https://stackabuse.com/bubble-sort-in-java/
        boolean sorted = false;
        while (!sorted) {
            sorted = true;
            for (int i = 0; i < dogList.size(); i++) {
                for (int j = 0; j < dogList.size() - 1; j++) {
                    Dog one = dogList.get(j);
                    Dog two = dogList.get(j + 1);
                    if (one.getTailLength() > two.getTailLength()) {
                        setList(one, two, j);
                        sorted = false;

                    } else if (one.getTailLength() == two.getTailLength()) {
                        if (one.getName().compareTo(two.getName()) > 0) {
                            setList(one, two, j);
                        }
                    }
                }
            }
        }
    }

    private void setList(Dog one, Dog two, int i) {
        dogList.set(i, two);
        dogList.set(i + 1, one);

    }

    private String askForDogName() {
        String a;
        do {
            System.out.print("Name?> ");
            a = input.nextLine();

            if (a.isBlank()) {
                System.out.println("Error: the name can’t be empty");
            }
        } while (a.isBlank());

        return a.toLowerCase().strip();
    }

    private String askForOwnerName() {
        String a;
        do {
            System.out.print("Name?> ");
            a = input.nextLine();

            if (a.isBlank()) {
                System.out.println("Error: the name can’t be empty");
            }
        } while (a.isBlank());

        return a.toLowerCase().strip();
    }

    private Owner ownerToFind() {
        String a;
        do {
            System.out.println("Enter the name of the Owner?> ");
            a = input.nextLine();

            if (a.isBlank()) {
                System.out.println("Error: the name can’t be empty");
            }
        } while (a.isBlank());

        return findOwner(a.toLowerCase().strip());
    }

    private Dog dogToFind() {
        String a;
        do {
            System.out.println("Enter the name of the Dog?> ");
            a = input.nextLine();
            if (a.isBlank()) {
                System.out.println("Error: the name can’t be empty");
            }
        } while (a.isBlank());

        return findDog(a.toLowerCase().strip());
    }

}