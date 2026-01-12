package org.example.app.service;

import org.example.app.entity.User;
import org.example.app.exceptions.UserException;
import org.example.app.mapper.UserMapper;
import org.example.app.repository.UserRepository;
import org.example.app.utils.AppValidator;
import org.example.app.utils.Message;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

public class UserService {

    UserRepository repository = new UserRepository();

    // 1. Створення користувача
    public String createUser(Map<String, String> data) {
        Map<String, String> errors = AppValidator.validateContactData(data);
        if (!errors.isEmpty()) {
            return new UserException("Check inputs", errors).getErrors(errors);
        }
        return repository.create(new UserMapper().mapContactData(data));
    }

    // 2. Читання всіх користувачів
    public String readUsers() {
        Optional<List<User>> optional = repository.read();
        if (optional.isPresent()) {
            List<User> list = optional.get();
            if (!list.isEmpty()) {
                AtomicInteger count = new AtomicInteger(0);
                StringBuilder stringBuilder = new StringBuilder();
                list.forEach((user) ->
                        stringBuilder.append(count.incrementAndGet())
                                .append(") ")
                                .append(user.toString())
                                .append("\n")
                );
                return "\nUSERS:\n" + stringBuilder;
            } else return Message.DATA_ABSENT_MSG.getMessage();
        } else return Message.DATA_ABSENT_MSG.getMessage();
    }

    // 3. Оновлення користувача (ЦЬОГО МЕТОДУ НЕ ВИСТАЧАЛО)
    public String updateUser(Map<String, String> data) {
        Map<String, String> errors = AppValidator.validateContactData(data);
        if (!errors.isEmpty()) {
            return new UserException("Check inputs", errors).getErrors(errors);
        }
        return repository.update(new UserMapper().mapContactData(data));
    }

    // 4. Видалення користувача (ЦЬОГО МЕТОДУ ТАКОЖ НЕ ВИСТАЧАЛО)
    public String deleteUser(Map<String, String> data) {
        // Використовуємо мапер, щоб отримати ID з Map
        Long id = new UserMapper().mapContactData(data).getId();
        return repository.delete(id);
    }

    // 5. Пошук за ID (ДЛЯ UserController.readContactById)
    public String readUserById(Map<String, String> data) {
        try {
            Long id = Long.parseLong(data.get("id"));
            Optional<User> optional = repository.readById(id);
            if (optional.isPresent()) {
                return "\nUSER: " + optional.get() + "\n";
            } else return Message.DATA_ABSENT_MSG.getMessage();
        } catch (NumberFormatException e) {
            return "Invalid ID format";
        }
    }
}