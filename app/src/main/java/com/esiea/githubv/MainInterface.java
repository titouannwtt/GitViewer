package com.esiea.githubv;

import java.util.List;

public interface MainInterface {
    void showUserList(List<User> userList);
    void showRepoList(List<Repo> repos);
    void showLoader();
    void showError();
    void makeApiCall();
}
