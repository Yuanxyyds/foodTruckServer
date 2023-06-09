package org.default_scene_implementation;

import org.controllers.Scene;
import org.exceptions.*;
import org.singleton_pattern.Singleton;
import org.use_case.FoodTruckManager;
import org.use_case.UserManager;

class UserInformationScene extends Scene {
    private final static UserInformationScene us = new UserInformationScene();
    private String username;
    private String nickname;
    private String phoneNum;
    private String truckName;
    private double accBalance;
    private String accessKey;
    private String truckActive;


    private UserInformationScene() {
        super();
        this.username = "";
        this.accessKey = "";
        this.nickname = "";
        this.phoneNum = "";
        this.truckName = "";
        this.truckActive = "";
        this.accBalance = 0;
        this.setHelpMessage("""


                All commands:
                help -> View all commands on this page
                view_market -> View all food trucks
                change_user_info -> Change user information
                change_truck_info -> Change user's food truck information
                add_money + [Space] + [amount of money] -> add money to balance
                withdraw_money + [Space] + [amount of money] -> withdraw money from balance
                view_orders -> View all orders
                change_truck_status -> Change Truck Status.""");
    }

    public static Singleton getInstance() {
        return us;
    }

    /**
     * @param input Input from commandline interface.
     */
    public void handleInputString(String input) {
        String[] text = input.split(" ");
        switch (text[0]) {
            case "view_market":
                this.viewMarket();
                break;
            case "help":
                this.state.append(this.getHelpMessage());
                break;
            case "exit":
                Scene.exit = true;
                break;
            case "sign_out":
                try {
                    UserManager.logOut(username, accessKey);
                    switchScene((LoginScene) LoginScene.getInstance());
                } catch (UnauthorizedAccessException e) {
                    state.append(e.getMessage()).append("\n");
                }
                break;
            case "change_user_info":
                changeUserInfo();
                break;
            case "change_truck_info":
                changeTruckInfo();
                break;
            case "add_money":
                try {
                    double money = Double.parseDouble(text[1]);
                    addFund(money);
                } catch (NumberFormatException | IncorrectArgumentException e) {
                    this.state.append((new IncorrectArgumentException()).getMessage());
                } catch (UnauthorizedAccessException e) {
                    this.state.append(e.getMessage()).append("\n");
                }
                break;
            case "withdraw_money":
                try {
                    double money = Double.parseDouble(text[1]);
                    withdrawFund(money);
                } catch (NumberFormatException | IncorrectArgumentException e) {
                    this.state.append((new IncorrectArgumentException()).getMessage());
                } catch (InsufficientBalanceException | UnauthorizedAccessException e) {
                    this.state.append(e.getMessage()).append("\n");
                }
                break;
            case "view_orders":
                viewOrders();
                break;
            case "change_truck_status":
                try {
                    FoodTruckManager.changeTruckStatus(username, accessKey);
                    updateUserInfo();
                } catch (UnauthorizedAccessException | UnknownFoodTruckException e) {
                    this.state.append(e.getMessage());
                }
                break;
            default:
                this.state.append((new UnknownCommandException()).getMessage()).append("\n");
                break;
        }
    }

    /**
     * @return Output String to the commandline interface.
     */
    @Override
    public String constructOutputString() {
        updateUserInfo();
        return "------------------------------User Information----------------------------------\n" +
                "Username: " + username + "\n" +
                "Nickname: " + nickname + "\n" +
                "Phone Number: " + phoneNum + "\n" +
                "Truck Name: " + truckName + "\n" +
                "Truck Status: " + truckActive + "\n" +
                "Account Balance: " + accBalance + "\n" +
                this.state;
    }

    /**
     * Update the user information
     */
    public void updateUserInfo() {
        try {
            this.truckName = UserManager.getTruckName(username, accessKey);
            this.nickname = UserManager.getNickname(username, accessKey);
            this.accBalance = UserManager.getBalance(username, accessKey);
            this.phoneNum = UserManager.getPhoneNumber(username, accessKey);
            boolean flag = FoodTruckManager.isActive(username, accessKey);
            if (flag) {
                this.truckActive = "Activated";
            } else {
                this.truckActive = "Deactivated";
            }

        } catch (UnauthorizedAccessException e) {
            this.state.append(e.getMessage());
        }
    }

    // the rest are helper methods for inputHandler.
    public void viewMarket() {
        MarketScene scene = (MarketScene) MarketScene.getInstance();
        this.switchScene(scene);
        this.state.setLength(0);
    }

    public void changeUserInfo() {
        UserInfoEditScene scene = (UserInfoEditScene) UserInfoEditScene.getInstance();
        this.switchScene(scene);
    }

    public void viewOrders() {
        OrderListScene scene = (OrderListScene) OrderListScene.getInstance();
        this.switchScene(scene);
    }

    public void changeTruckInfo() {
        FoodTruckEditScene scene = (FoodTruckEditScene) FoodTruckEditScene.getInstance();
        this.switchScene(scene);
    }

    public void addFund(double fund) throws IncorrectArgumentException, UnauthorizedAccessException {
        UserManager.addMoney(username, accessKey, fund);
        updateUserInfo();
    }

    public void withdrawFund(double fund) throws IncorrectArgumentException,
            InsufficientBalanceException, UnauthorizedAccessException {
        UserManager.withdrawMoney(username, accessKey, fund);
        updateUserInfo();
    }

    public void setUserInfo(String username, String key) {
        this.username = username;
        this.accessKey = key;
        this.updateUserInfo();
    }

}
