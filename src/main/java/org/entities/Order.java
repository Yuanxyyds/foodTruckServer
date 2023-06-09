package org.entities;


import org.exceptions.IncorrectArgumentException;

import java.io.Serializable;
import java.time.LocalDateTime; // Import the LocalDateTime class
import java.time.format.DateTimeFormatter; // Import the DateTimeFormatter class

/**
 * An order.
 */

public class Order implements Serializable {
    private final String summary;
    private final String buyerName; // name of the customer who ordered the food
    private final String seller;
    private final String buyer;
    private final String buyerNumber; // contact number of the customer who ordered the food
    private final String sellerName; // Nickname of the seller who owns the food truck
    private final String sellerNumber; // contact number of the seller who owns the food truck
    private final String truckName; // name of the truck
    private double rating;// customer can rate their order from 0 ~ 10. (if the customer didn't rate, rating
    // for the order will be a default -0.1)
    private String status; // the status can only be "in progress" or "order completed"
    private final LocalDateTime time;

    /**
     * Construct a new order object
     *
     * @param buyerNick      name of the customer who ordered the food
     * @param customerNumber contact number of the customer who ordered the food
     * @param sellerName     name of the seller who owns the food truck
     * @param sellerNumber   contact number of the seller who owns the food truck
     */
    public Order(String summary, String buyer, String buyerNick,
                 String customerNumber, String seller, String sellerName, String sellerNumber, String truckName) {
        this.summary = summary;
        this.buyerName = buyerNick;
        this.buyerNumber = customerNumber;
        this.sellerName = sellerName;
        this.sellerNumber = sellerNumber;
        this.buyer = buyer;
        this.seller = seller;
        this.rating = 0;
        this.status = "in progress";
        this.truckName = truckName;
        this.time = LocalDateTime.now();
    }

    /**
     * change the current status of this order to the next status.
     * E.g. when current status is "order completed", call changeOrderStatus again
     * will not change the status and "Change Failed" will be returned.
     */
    public void changeOrderStatus() {
        if (this.status.equals("in progress")) {
            this.status = "order completed";
        }
    }


    /**
     * Update the customers rating for his/her order. Method returns true when given a reasonable rating, return false
     * otherwise
     *
     * @param rating should be a double < 10 & > 0
     */
    public void rateOrder(double rating) throws IncorrectArgumentException {
        if (0 <= rating & rating <= 10) {
            this.rating = rating;
        } else {
            throw new IncorrectArgumentException();
        }
    }


    /**
     * A string representaton of order object (kinda like a receipt)
     *
     * @return A string
     */
    public String toString() {
        String s;
        if (this.status.equals("in progress")) {
            s = "TBD";
        } else {
            s = rating + "";
        }
        return "Order Time: " + this.getFormattedTime() + "\n" +
                "Buyer Name: " + buyerName + "\n" +
                "Buyer Number: " + buyerNumber + "\n" +
                "Seller Name: " + this.getSellerName() + "\n" +
                "Seller Number: " + this.getSellerNumber() + "\n" +
                this.summary + "\n" +
                "Status: " + this.status + "\n" +
                "Rating: " + s + "\n";
    }

    /**
     * @return this Order's description.
     */
    public String getDescription() {
        return "Date: " + this.getFormattedTime() + "\n" +
                "TruckName: " + this.truckName + "\n";
    }

    /**
     * A string represents the order time.
     *
     * @return A string
     */
    public String getFormattedTime() {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return this.getTime().format(format);
    }

    // the rests are getters of this class.
    public LocalDateTime getTime() {
        return this.time;
    }

    public String getBuyer() {
        return this.buyer;
    }

    public String getSeller() {
        return seller;
    }

    public String getSellerName() {
        return this.sellerName;
    }


    public String getSellerNumber() {
        return this.sellerNumber;
    }
}
