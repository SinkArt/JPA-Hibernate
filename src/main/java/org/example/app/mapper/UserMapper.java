package org.example.app.mapper;

import org.example.app.entity.User;
import org.example.app.entity.User;

import java.util.Map;

public class UserMapper {

    public User mapContactData(Map<String, String> data) {
        User contact = new User();
        if (data.containsKey("id"))
            contact.setId(Long.parseLong(data.get("id")));
        if (data.containsKey("first_name"))
            contact.setFirstName(data.get("first_name"));
        if (data.containsKey("last_name"))
            contact.setLastName(data.get("last_name"));
        if (data.containsKey("phone"))
            contact.setPhone(data.get("phone"));
        if (data.containsKey("email"))
            contact.setEmail(data.get("email"));
        return contact;
    }
}
