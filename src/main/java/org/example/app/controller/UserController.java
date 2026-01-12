package org.example.app.controller;

import org.example.app.service.UserService;
import org.example.app.utils.AppStarter;
import org.example.app.view.*;

public class UserController {

    UserService service = new UserService();

    public void createContact() {
        UserCreateView createView = new UserCreateView();
        // ВИПРАВЛЕНО: викликаємо createUser замість createContact
        createView.getOutput(service.createUser(createView.getData()));
        AppStarter.startApp();
    }

    public void readContacts() {
        UserReadView readView = new UserReadView();
        // ВИПРАВЛЕНО: викликаємо readUsers замість readContacts
        readView.getOutput(service.readUsers());
        AppStarter.startApp();
    }

    public void updateContact() {
        UserUpdateView updateView = new UserUpdateView();
        // ВИПРАВЛЕНО: викликаємо updateUser
        updateView.getOutput(service.updateUser(updateView.getData()));
        AppStarter.startApp();
    }

    public void deleteContact() {
        UserDeleteView deleteView = new UserDeleteView();
        // ВИПРАВЛЕНО: викликаємо deleteUser
        deleteView.getOutput(service.deleteUser(deleteView.getData()));
        AppStarter.startApp();
    }

    public void readContactById() {
        UserReadByIdView readByIdView = new UserReadByIdView();
        // ВИПРАВЛЕНО: викликаємо readUserById
        readByIdView.getOutput(service.readUserById(readByIdView.getData()));
        AppStarter.startApp();
    }
}