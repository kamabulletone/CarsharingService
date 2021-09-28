package ru.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Set;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "roles")
/**
 * Класс, реализующий сущность роли
 */
public class Role implements GrantedAuthority {
    /**
     * Идентификатор роли
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * Название роли
     */
    @Column(name = "name")
    private String name;

    /**
     * Список клиентом связанных с данной ролью
     */
    @ManyToMany(mappedBy = "roles")
    @JsonBackReference
    private Set<Client> clients;

    /**
     * Конструктор роли
     * @param id идектификатор роли
     * @param name название
     */
    public Role(int id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Метод получения имения роли
     * @return имя роли
     */
    @Override
    public String getAuthority() {
        return getName();
    }
}
