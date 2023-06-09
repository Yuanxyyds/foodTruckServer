package org.default_scene_implementation;

import org.controllers.Scene;
import org.exceptions.IncorrectArgumentException;
import org.exceptions.UnauthorizedAccessException;
import org.exceptions.UnknownCommandException;
import org.singleton_pattern.Singleton;
import org.use_case.FoodTruckManager;

class FoodTruckEditScene extends Scene {
    private static final FoodTruckEditScene ies = new FoodTruckEditScene();
    private String accessKey;
    private String username;

    private FoodTruckEditScene() {
        super();
        this.fields.put("New Truck Name", "");
        this.fields.put("New Location", "");
        this.fields.put("New Start Time", "");
        this.fields.put("New End Time", "");
        String helpMessage = """


                All commands:
                help -> View all commands on this page
                truck_name + [Space] + [new truck name] -> Enter new truck name
                location + [Space] + [new location] -> Enter truck new location
                start + [Space] + [1 or 2 (1 is AM, 2 is PM)] + [Space] + [1-12] -> Enter your new truck service start time
                end + [Space] + [1 or 2 (1 is AM, 2 is PM)] + [Space] + [1-12] -> Enter your new truck service end time
                confirm -> confirm changes
                change_menu -> proceed to edit menu
                back -> Discard changes and return to view user information page""";
        this.setHelpMessage(helpMessage);
    }

    public static Singleton getInstance() {
        return ies;
    }

    /**
     * @param input input form commandline interface.
     */
    @Override
    public void handleInputString(String input) {
        String[] text = input.split(" ");
        switch (text[0]) {
            case "back":
                switchScene((UserInformationScene) UserInformationScene.getInstance());
                break;
            case "help":
                this.state.append(this.getHelpMessage());
                break;
            case "truck_name":
                this.fillInField("New Truck Name", text[1]);
                break;
            case "start":
                try {
                    String newTime = generateTime(text[1], text[2]);
                    this.fillInField("New Start Time", newTime);
                } catch (IncorrectArgumentException e) {
                    this.state.append(e.getMessage());
                }
                break;
            case "end":
                try {
                    String newTime = generateTime(text[1], text[2]);
                    this.fillInField("New End Time", newTime);
                } catch (IncorrectArgumentException e) {
                    this.state.append(e.getMessage());
                }
                break;
            case "change_menu":
                switchScene((MenuEditScene) MenuEditScene.getInstance());
                break;
            case "location":
                this.fillInField("New Location", text[1]);
                break;
            case "confirm":
                try {
                    if (!fields.get("New Truck Name").equals("")) {
                        FoodTruckManager.setTruckName(fields.get("New Truck Name"), username, accessKey);
                    }
                    String location = fields.get("New Location");
                    if (location.length() > 0) {
                        FoodTruckManager.setAddress(location, username, accessKey);
                    }
                    String startTime = fields.get("New Start Time");
                    if (startTime.length() > 0) {
                        FoodTruckManager.setStartTime(startTime, username, accessKey);
                    }
                    String endTime = fields.get("New End Time");
                    if (endTime.length() > 0) {
                        FoodTruckManager.setEndTime(endTime, username, accessKey);
                    }
                    UserInformationScene us = (UserInformationScene) UserInformationScene.getInstance();
                    switchScene((UserInformationScene) UserInformationScene.getInstance());
                    us.updateUserInfo();
                } catch (UnauthorizedAccessException e) {
                    this.state.append(e.getMessage()).append("\n");
                }
                break;
            default:
                this.state.append((new UnknownCommandException()).getMessage()).append("\n");
                break;
        }
    }

    /**
     * @return the output string of this string.
     */
    @Override
    public String constructOutputString() {
        StringBuilder outputString = new StringBuilder();
        String location = this.fields.get("New Location");
        String truckName = this.fields.get("New Truck Name");
        String start = this.fields.get("New Start Time");
        String end = this.fields.get("New End Time");
        outputString.append("New Truck Name: ").append(truckName).append("\n");
        outputString.append("New Location: ").append(location).append("\n");
        outputString.append("New Start Time: ").append(start).append("\n");
        outputString.append("New End Time: ").append(end).append("\n");
        outputString.append(this.state);
        return outputString.toString();
    }

    /**
     * @param username username of the user
     * @param key      access key
     */
    public void setUserInfo(String username, String key) {
        this.username = username;
        this.accessKey = key;
    }

    /**
     * @param midday decide it's AM or PM
     * @param hour   hour of the day
     * @return the string of new time
     * @throws IncorrectArgumentException if the arguments are incorrect.
     */
    private String generateTime(String midday, String hour) throws IncorrectArgumentException {
        int a = Integer.parseInt(midday);
        int b = Integer.parseInt(hour);
        if (checkInRange(2, a) && checkInRange(12, b)) {
            if (a == 1) {
                return b + ":00" + "AM";
            } else {
                return b + ":00" + "PM";
            }
        }
        throw new IncorrectArgumentException();
    }

    /**
     * @param large an upper bound
     * @param check a number
     * @return true if the number is between 1 and the upper bound.
     */
    private boolean checkInRange(int large, int check) {
        return 1 <= check && large >= check;
    }

}
