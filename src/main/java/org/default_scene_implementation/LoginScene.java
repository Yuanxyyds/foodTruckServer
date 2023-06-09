package org.default_scene_implementation;


import org.controllers.Scene;
import org.exceptions.IncorrectCredentialsException;
import org.exceptions.UnauthorizedAccessException;
import org.exceptions.UnknownCommandException;
import org.singleton_pattern.Singleton;
import org.use_case.UserManager;

class LoginScene extends Scene {
    private final static LoginScene ls = new LoginScene();

    private LoginScene() {
        super();
        this.fields.put("username", "");
        this.fields.put("password", "");
        this.setHelpMessage("""


                All commands:
                help -> View all commands on this page
                U + [Space] + [your username] -> Enter your username
                P + [Space] + [your password] -> Enter your password
                confirm -> Login with the information you entered
                """);
    }

    public static Singleton getInstance() {
        return ls;
    }

    /**
     * @param input input from interface.
     */
    public void handleInputString(String input) {
        String[] text = input.split(" ");
        switch (text[0]) {
            case "U":
                this.fillInField("username", text[1]);
                break;
            case "P":
                this.fillInField("password", text[1]);
                break;
            case "confirm":
                try {
                    this.userLogin();
                } catch (IncorrectCredentialsException | UnauthorizedAccessException e) {
                    this.state.append(e.getMessage()).append("\n");
                }
                break;
            case "register":
                this.switchScene((Scene) RegisterScene.getInstance());
                break;
            case "help":
                this.state.append(this.getHelpMessage());
                break;
            case "exit":
                Scene.exit = true;
                break;
            default:
                this.state.append((new UnknownCommandException()).getMessage()).append("\n");
                break;
        }
    }

    /**
     * @return output string to interface.
     */
    public String constructOutputString() {
        StringBuilder outputString = new StringBuilder();
        String enteredPassword = "*".repeat(this.fields.get("password").length());
        String enteredUsername = this.fields.get("username");
        outputString.append("Username: ").append(enteredUsername).append("\n");
        outputString.append("Password: ").append(enteredPassword).append("\n");
        outputString.append(this.state);
        return outputString.toString();
    }

    /**
     * @throws IncorrectCredentialsException if the password and username don't match
     * @throws UnauthorizedAccessException   if the user has no access to this functionality.
     */
    public void userLogin() throws IncorrectCredentialsException, UnauthorizedAccessException { // attempt to login
        String username = this.fields.get("username");
        String password = this.fields.get("password");
        String key = UserManager.login(username, password);
        UserInformationScene s = (UserInformationScene) UserInformationScene.getInstance();
        s.setUserInfo(username, key); //set all scenes
        this.switchScene(s);
        // Scene setup
        UserInfoEditScene infoEditScene = (UserInfoEditScene) UserInfoEditScene.getInstance();
        infoEditScene.setUserInfo(username, key);
        FoodTruckEditScene truckEditScene = (FoodTruckEditScene) FoodTruckEditScene.getInstance();
        truckEditScene.setUserInfo(username, key);
        FoodTruckScene ftc = (FoodTruckScene) FoodTruckScene.getInstance();
        ftc.setUserInfo(username, key);
        MenuEditScene menuEditScene = (MenuEditScene) MenuEditScene.getInstance();
        menuEditScene.setUserInfo(username);
        OrderScene os = (OrderScene) OrderScene.getInstance();
        os.setUserInfo(username, key);
        OrderListScene ols = (OrderListScene) OrderListScene.getInstance();
        ols.setOrderListInfo(username, key);
    }
}
