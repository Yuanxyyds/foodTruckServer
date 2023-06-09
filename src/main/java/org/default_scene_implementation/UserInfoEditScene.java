package org.default_scene_implementation;

import org.controllers.Scene;
import org.exceptions.IncorrectOldPasswordException;
import org.exceptions.UnauthorizedAccessException;
import org.exceptions.UnknownCommandException;
import org.singleton_pattern.Singleton;
import org.use_case.UserManager;

public class UserInfoEditScene extends Scene {
    private final static UserInfoEditScene editScene = new UserInfoEditScene();
    private String accessKey;
    private String username;


    private UserInfoEditScene() {
        super();
        this.fields.put("Old Password", "");
        this.fields.put("New Password", "");
        this.fields.put("Phone Number", "");
        this.fields.put("Nickname", "");
        String helpMessage = """


                All commands:
                help -> View all commands on this page
                old_P + [Space] + [old password] -> Enter your old password
                new_P + [Space] + [new password] -> Enter your new password
                new_PN + [Space] + [new phone number] -> Enter your new phone number
                change_nickname + [Space] + [new nickname] -> Enter your new nickname
                confirm -> change old password to new password and change old phone number tonew phone number
                back -> Discard changes and return to view user information page""";
        this.setHelpMessage(helpMessage);
    }

    public static Singleton getInstance() {
        return editScene;
    }

    /**
     * @param input from commandline interface.
     */
    @Override
    public void handleInputString(String input) {
        String[] text = input.split(" ");
        switch (text[0]) {
            case "old_P":
                this.fillInField("Old Password", text[1]);
                break;
            case "new_P":
                this.fillInField("New Password", text[1]);
                break;
            case "new_PN":
                this.fillInField("Phone Number", text[1]);
                break;
            case "change_nickname":
                this.fillInField("Nickname", text[1]);
                break;
            case "confirm":
                try {
                    if (!fields.get("New Password").equals("")) {
                        UserManager.setPassword(username, accessKey, fields.get("New Password"), fields.get("Old Password"));
                    }
                    String phoneNumber = fields.get("Phone Number");
                    if (phoneNumber.length() > 0) {
                        UserManager.setPhoneNumber(username, accessKey, phoneNumber);
                    }
                    String nickname = fields.get("Nickname");
                    if (nickname.length() > 0) {
                        UserManager.setNickname(username, accessKey, nickname);
                    }
                    UserInformationScene us = (UserInformationScene) UserInformationScene.getInstance();
                    switchScene(us);
                    us.updateUserInfo();
                } catch (UnauthorizedAccessException | IncorrectOldPasswordException e) {
                    this.state.append(e.getMessage()).append("\n");
                }
                break;
            case "help":
                this.state.append(this.getHelpMessage());
                break;
            case "back":
                switchScene((Scene) UserInformationScene.getInstance());
                break;
            default:
                this.state.append((new UnknownCommandException()).getMessage()).append("\n");
                break;
        }

    }

    /**
     * @return the output string to commandline interface.
     */
    @Override
    public String constructOutputString() {
        StringBuilder outputString = new StringBuilder();
        String enteredOldPassword = "*".repeat(this.fields.get("Old Password").length());
        String enteredNewPassword = "*".repeat(this.fields.get("New Password").length());
        String enteredNewNickname = this.fields.get("Nickname");
        String enteredNewPhoneNum = this.fields.get("Phone Number");
        outputString.append("Old Password: ").append(enteredOldPassword).append("\n");
        outputString.append("New Password: ").append(enteredNewPassword).append("\n");
        outputString.append("New Phone Number: ").append(enteredNewPhoneNum).append("\n");
        outputString.append("New Nickname: ").append(enteredNewNickname).append("\n");
        outputString.append(this.state);
        return outputString.toString();
    }

    public void setUserInfo(String username, String key) {
        this.username = username;
        this.accessKey = key;
    }
}
