package ru.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.model.Role;

/**
 * Интерфейс реализующий работу с репозиторием ролей.
 */
public interface RoleRepository extends JpaRepository<Role, Integer> {
}
