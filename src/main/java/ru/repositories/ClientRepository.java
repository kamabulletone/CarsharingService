package ru.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.model.Client;

public interface ClientRepository extends JpaRepository<Client, Integer> {
}
