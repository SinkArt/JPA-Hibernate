package org.example.app.view;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class UserUpdateView {
    // Отримуємо вхідні дані
    public Map<String, String> getData() {
        Scanner scanner = new Scanner(System.in);
        Map<String, String> data = new HashMap<>();
        System.out.print("Enter contact's ID: ");
        data.put("id", scanner.nextLine().trim());
        System.out.print("Enter first name: ");
        data.put("first_name", scanner.nextLine().trim());
        System.out.print("Enter last name: ");
        data.put("last_name", scanner.nextLine().trim());
        System.out.print("Enter phone in format xxx xxx-xxxx: ");
        data.put("phone", scanner.nextLine().trim());
        System.out.print("Enter email in format example@mail.com: ");
        data.put("email", scanner.nextLine().trim());
        return data;
    }

    public void getOutput(String output) {
        System.out.println(output);
    }
}
