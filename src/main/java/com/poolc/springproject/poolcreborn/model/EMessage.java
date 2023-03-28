package com.poolc.springproject.poolcreborn.model;

public enum EMessage {
    USED_EMAIL("이미 사용중인 이메일입니다."),
    USED_USERNAME("이미 사용중인 아이디입니다."),

    ALREADY_SIGNED_UP("이미 신청한 활동입니다."),
    SELF_SIGNUP_DENIED("본인의 활동은 신청할 수 없습니다."),
    UPDATE_ACTIVITY_ACCESS_DENIED("본인이 개설한 활동만 수정할 수 있습니다."),
    APPROVAL_ACCESS_DENIED("본인이 개설한 활동만 신청 요청자를 승인할 수 있습니다."),

    SUCCESSFUL_ROLE_ADD("성공적으로 권한을 부여했습니다."),
    SUCCESSFUL_UPDATE("성공적으로 수정했습니다."),
    SUCCESSFUL_SIGNUP("성공적으로 신청되었습니다."),
    SUCCESSFUL_SIGNUP_REQUEST("성공적으로 신청을 요청했습니다."),
    SUCCESSFUL_REQUEST_APPROVAL("성공적으로 요청을 승인했습니다."),
    SUCCESSFUL_DELETE_USER("성공적으로 탈퇴했습니다.");

    private final String message;
    EMessage(String message) {
        this.message = message;
    }
    public String getMessage() {
        return this.message;
    }
}
