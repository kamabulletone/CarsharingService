package ru.model;



import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(name = "clients")
//@JsonIgnoreProperties(ignoreUnknown = true,
//        value = {"hibernateLazyInitializer", "handler", "created", "FullName"})
/**
 * Класс,реализующий сущность клиента
 */
public class Client implements UserDetails {

    /**
     * Идентификатр клиента
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "client_id") //,updatable = false, nullable = false)
    private int clientID;

    /**
     * Полное имя клиента
     */
    @Column(name = "full_name")
    private String fullName;

    /**
     * Ссылка на скан паспорта клиента
     */
    @Column(name = "passport", unique = true)
    private String passport; //PassportScanURL

    /**
     * Ссылка на фато клиента
     */
    @Column(name = "face_photo")
    private String facePhoto; //FacePhotoURL

    /**
     * Ссылка на вод. удостоверение клиента
     */
    @Column(name = "driver_license")
    private String driverLicense; //driverLicenseScanURL

    /**
     * Мобильный телефон клиента
     */
    @Column(name = "phone_num")
    private String phoneNumber;

    /**
     * Электронная почта клиента
     */
    @Column(name = "email", unique = true)
    private String email;

    /**
     * Зашифрованный пароль
     */
    @Column(name = "password")
    private String password;

    /**
     * Набор ролей клиента(юзера)
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JsonManagedReference
    private Set<Role> roles;

    /**
     * Метод работы с коллекциями привилегий.
     * @return список ролей клиента
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    /**
     * Метод получения пароля клиента
     * @return пароль
     */
    @Override
    public String getPassword() {
        return password;
    }

    /**
     * Метод получения имени пользователя.
     * @return Вернуть почту клиента.
     */
    @Override
    public String getUsername() {
        return email;
    }

    /**
     * Метод проверки активности аккаунта.
     * @return Вернуть положительный ответ.
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Метод проверки незаблокированности аккаунта.
     * @return Вернуть положительный ответ.
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Метод проверки учетных данных аккаунта.
     * @return Вернуть положительный ответ.
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Метод проверки доступности аккаунта.
     * @return Вернуть положительный ответ.
     */
    @Override
    public boolean isEnabled() {
        return true;
    }


}
