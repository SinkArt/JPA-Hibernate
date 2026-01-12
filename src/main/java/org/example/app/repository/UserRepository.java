package org.example.app.repository;

import jakarta.persistence.criteria.*;
import org.example.app.entity.User;
import org.example.app.config.HibernateConfig;
import org.example.app.utils.Message;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

public class UserRepository implements BaseRepository<User> {

    @Override
    public String create(User contact) {
        Transaction transaction = null;
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            // Транзакція стартує
            transaction = session.beginTransaction();
            // Зберігаємо сутність у БД
            session.persist(contact);
            // Транзакція виконується
            transaction.commit();
            // Повернення повідомлення при безпомилковому
            // виконанні транзакції
            return Message.DATA_INSERT_MSG.getMessage();
        } catch (Exception e) {
            if (transaction != null) {
                // Відкочення поточної транзакції ресурсу
                transaction.rollback();
            }
            // Повернення повідомлення про помилку роботи з БД
            return e.getMessage();
        }
    }

    @Override
    public Optional<List<User>> read() {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            Transaction transaction;
            // Транзакція стартує
            transaction = session.beginTransaction();
            // Отримуємо данні з БД (розгорнутий варіант коду)
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<User> cq = cb.createQuery(User.class);
            Root<User> root = cq.from(User.class);
            cq.select(root);
            Query<User> query = session.createQuery(cq);
            List<User> list = query.getResultList();
            // Отримуємо данні з БД (скорочений варіант коду)
//            CriteriaBuilder cb = session.getCriteriaBuilder();
//            CriteriaQuery<Contact> cq = cb.createQuery(Contact.class);
//            cq.from(Contact.class);
//            List<Contact> list = session.createQuery(cq).getResultList();
            // Транзакція виконується
            transaction.commit();
            // Повернення результату транзакції
            // Повертаємо Optional-контейнер з колецією даних
            return Optional.of(list);
        } catch (Exception e) {
            // Якщо помилка повертаємо порожній Optional-контейнер
            return Optional.empty();
        }
    }

    @Override
    public String update(User user) {
        // Спершу перевіряємо наявність такого id в БД.
        // Якщо ні, повертаємо повідомлення про відсутність таких даних,
        // інакше оновлюємо відповідний об'єкт в БД
        if (!isEntityWithSuchIdExists(user)) {
            return Message.DATA_ABSENT_MSG.getMessage();
        } else {
            Transaction transaction = null;
            try (Session session = HibernateConfig.getSessionFactory().openSession()) {
                // Транзакція стартує
                transaction = session.beginTransaction();
                // Оновлення об'єкту
                CriteriaBuilder cb = session.getCriteriaBuilder();
                CriteriaUpdate<User> cu = cb.createCriteriaUpdate(User.class);
                Root<User> root = cu.from(User.class);
                cu.set("firstName", user.getFirstName());
                cu.set("lastName", user.getLastName());
                cu.set("phone", user.getPhone());
                cu.set("email", user.getEmail());
                cu.where(cb.equal(root.get("id"), user.getId()));
                session.createMutationQuery(cu).executeUpdate();
                // Транзакція виконується
                transaction.commit();
                // Повернення повідомлення при безпомилковому
                // виконанні транзакції
                return Message.DATA_UPDATE_MSG.getMessage();
            } catch (Exception e) {
                if (transaction != null) {
                    // Відкочення поточної транзакції ресурсу
                    transaction.rollback();
                }
                // Повернення повідомлення про помилку роботи з БД
                return e.getMessage();
            }
        }
    }

    @Override
    public String delete(Long id) {
        // Спершу перевіряємо наявність об'єкта в БД за таким id.
        // Якщо ні, повертаємо повідомлення про відсутність таких даних,
        // інакше видаляємо відповідний об'єкт із БД
        if (readById(id).isEmpty()) {
            return Message.DATA_ABSENT_MSG.getMessage();
        } else {
            Transaction transaction = null;
            try (Session session = HibernateConfig.getSessionFactory().openSession()) {
                // Транзакція стартує
                transaction = session.beginTransaction();
                // Видалення об'єкту за певним id
                CriteriaBuilder cb = session.getCriteriaBuilder();
                CriteriaDelete<User> cd = cb.createCriteriaDelete(User.class);
                Root<User> root = cd.from(User.class);
                cd.where(cb.equal(root.get("id"), id));
                session.createMutationQuery(cd).executeUpdate();
                // Транзакція виконується
                transaction.commit();
                // Повернення повідомлення при безпомилковому
                // виконанні транзакції
                return Message.DATA_DELETE_MSG.getMessage();
            } catch (Exception e) {
                // Відкочення поточної транзакції ресурсу
                if (transaction != null) {
                    transaction.rollback();
                }
                // Повернення повідомлення про помилку роботи з БД
                return e.getMessage();
            }
        }
    }

    @Override
    public Optional<User> readById(Long id) {
        Transaction transaction = null;
        Optional<User> optional;
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            // Транзакція стартує
            transaction = session.beginTransaction();
            // Отримання об'єкту з БД за певним id
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<User> cq = cb.createQuery(User.class);
            Root<User> root = cq.from(User.class);
            cq.select(root).where(cb.equal(root.get("id"), id));
            Query<User> query = session.createQuery(cq);
            optional = query.uniqueResultOptional();
            // Транзакція виконується
            transaction.commit();
            // Повернення результату HQL-запиту
            // Повертаємо Optional-контейнер з колецією даних
            return optional;
        } catch (Exception e) {
            if (transaction != null) {
                // Відкочення поточної транзакції ресурсу
                transaction.rollback();
            }
            // Якщо помилка повертаємо порожній Optional-контейнер
            return Optional.empty();
        }
    }

    // Перевірка наявності об'єкту/сутності за певним id у БД
    private boolean isEntityWithSuchIdExists(User contact) {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            contact = session.get(User.class, contact.getId());
            if (contact != null) {
                CriteriaBuilder cb = session.getCriteriaBuilder();
                CriteriaQuery<User> cq = cb.createQuery(User.class);
                cq.from(User.class);
                session.createQuery(cq).setMaxResults(1);
            }
            return contact != null;
        }
    }

}
