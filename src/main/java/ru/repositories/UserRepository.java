package ru.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.model.Order;
import ru.model.User;

public interface UserRepository extends JpaRepository<User, String> {
}
