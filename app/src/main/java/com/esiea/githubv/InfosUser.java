package com.esiea.githubv;

public class InfosUser {
    private String login;
    private int id;
    private String avatar;
    private String url;
    private String description;
    private int nbRepos;
    private String dateCreation;
    private String dateUpdate;

    public InfosUser(String login, int id, String avatar, String url, String description, int nbRepos, String dateCreation, String dateUpdate) {
        this.login = login;
        this.id = id;
        this.avatar = avatar;
        this.url = url;
        this.description = description;
        this.nbRepos = nbRepos;
        this.dateCreation = dateCreation;
        this.dateUpdate = dateUpdate;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getNbRepos() {
        return nbRepos;
    }

    public void setNbRepos(int nbRepos) {
        this.nbRepos = nbRepos;
    }

    public String getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(String dateCreation) {
        this.dateCreation = dateCreation;
    }

    public String getDateUpdate() {
        return dateUpdate;
    }

    public void setDateUpdate(String dateUpdate) {
        this.dateUpdate = dateUpdate;
    }

}
