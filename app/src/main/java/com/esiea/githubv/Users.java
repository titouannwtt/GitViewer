package com.esiea.githubv;

public class Users {
    private String login;
    private int id;
    private String avatar;

    public Users(String login, int id, String avatar) {
        this.login = login;
        this.id = id;
        this.avatar = avatar;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
