package bea.prog1;

import java.util.ArrayList;

/**
 * @author Beatrice Friberg befr6512
 */

public class Auction {

    private final int auctionId;
    private final Dog dog;
    private final ArrayList<Bid> bidsList = new ArrayList<>();


    public Auction(Dog dog, int auctionId) {
        this.dog = dog;
        this.auctionId = auctionId;
        dog.setInAuction(true);


    }

    public Dog getDog() {
        return dog;
    }

    public int getAuctionId() {
        return auctionId;
    }

    public int getBidsSize() {
        return bidsList.size();
    }

    public Bid getIndex(int i) {
        return bidsList.get(i);
    }

    public boolean noBids() {
        return bidsList.isEmpty();
    }

    public void addBids(Bid b) {
        bidsList.add(b);
        sortBids();
    }

    public void sortBids() {
        Bid one;
        Bid two;

        for (int i = 0; i < bidsList.size() - 1; i++) {
            for (int j = 0; j < bidsList.size() - 1; j++) {
                one = bidsList.get(j);
                two = bidsList.get(j + 1);

                if (one.getBid() < two.getBid()) {
                    bidsList.set(j, two);
                    bidsList.set(j + 1, one);
                    if (one.getOwner().getName().equals(two.getOwner().getName())) {
                        bidsList.remove(one);
                    }

                }

            }

        }

    }

    public void removeBidsOwner(Owner o) {
        ArrayList<Bid> remove = new ArrayList<>();


        for (Bid b : bidsList) {
            if (b.getOwner() == o) {
                remove.add(b);
            }
        }
        bidsList.removeAll(remove);

        if (!bidsList.isEmpty()) {

            int i = 0;
            sortBids();
            Bid b = bidsList.get(i);
            Dog d = b.getDog();
            d.setCurrentBid(b.getBid());

        }else{
            Dog d = getDog();
            d.setCurrentBid(0);
        }
    }

    @Override
    public String toString() {
        return dog.getName() + " has been put up for auction in auction #" + auctionId;
    }
}
