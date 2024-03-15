package com.main.dietidealsclient.Controller;

import com.main.dietidealsclient.Model.Notifica;
import com.main.dietidealsclient.Requesters.NotificheRequester;
import com.main.dietidealsclient.Utility.LoggedUser;

import java.util.List;

public class NotificheController {

    NotificheRequester notificheRequester;

    public NotificheController() {
        notificheRequester = new NotificheRequester();
    }

    public List<Notifica> getAll() {
        try {
            return notificheRequester.getAll(LoggedUser.getInstance().getLoggedUser().getEmail());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
