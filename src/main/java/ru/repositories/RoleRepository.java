package ru.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.model.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {
}
