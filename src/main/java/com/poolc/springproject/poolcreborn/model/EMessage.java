package com.poolc.springproject.poolcreborn.model;

public enum EMessage {
    USED_EMAIL("This message is already in use."),
    USED_USERNAME("This username is already in use."),
    ALREADY_SIGNED_UP("You already signed up for this activity."),
    SELF_SIGNUP_DENIED("You cannot sign up for your own activity."),
    UPDATE_ACTIVITY_ACCESS_DENIED("You can only update your own activity."),
    APPROVAL_ACCESS_DENIED("You can only approve requests for your own activity."),
    SUCCESSFUL_ROLE_ADD("You have successfully added roles."),
    SUCCESSFUL_UPDATE("You have successfully updated your activity."),
    SUCCESSFUL_SIGNUP_ACTIVITY("You have successfully signed up for this activity."),
    SUCCESSFUL_SIGNUP_REQUEST("You have successfully requested signup."),
    SUCCESSFUL_REQUEST_APPROVAL("You have successfully approved requests."),
    SUCCESSFUL_DELETE_USER("You have successfully deleted your account.");

    private final String message;
    EMessage(String message) {
        this.message = message;
    }
    public String getMessage() {
        return this.message;
    }
}
