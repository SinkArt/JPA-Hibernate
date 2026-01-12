package org.example.app.view;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class UserReadByIdView {

    // Отримуємо вхідні дані
    public Map<String, String> getData() {
        System.out.println("\nREAD BY ID FORM");
        Scanner scanner = new Scanner(System.in);
        Map<String, String> data = new HashMap<>();
        System.out.print("Input id: ");
        data.put("id", scanner.nextLine().trim());
        return data;
    }

    // Виведення результату роботи програми
    public void getOutput(String output) {
        System.out.println("\nDATA BY ID:\n" + output);
    }
}
