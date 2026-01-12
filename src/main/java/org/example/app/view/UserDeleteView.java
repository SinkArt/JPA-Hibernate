package org.example.app.view;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class UserDeleteView {

    // Отримуємо вхідні дані
    public Map<String, String> getData() {
        System.out.println("\nDELETE FORM");
        Map<String, String> map = new HashMap<>();
        Scanner scanner = new Scanner(System.in);
        System.out.print("Input id: ");
        map.put("id", scanner.nextLine().trim());
        return map;
    }

    // Виведення результату роботи програми
    public void getOutput(String output) {
        System.out.println(output);
    }
}
