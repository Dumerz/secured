package io.dumerz.secured.jwt;

public class UsernameAndPasswordAuthenticationRequest {
    private String usermane;
    private String password;

    public UsernameAndPasswordAuthenticationRequest(String usermane, String password) {
        this.usermane = usermane;
        this.password = password;
    }

    public String getUsermane() {
        return usermane;
    }

    public void setUsermane(String usermane) {
        this.usermane = usermane;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}