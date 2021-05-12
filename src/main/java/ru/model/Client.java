package ru.model;



import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@ToString
@Getter
@Table(name = "clients", schema = "carsharing")
//@JsonIgnoreProperties(ignoreUnknown = true,
//        value = {"hibernateLazyInitializer", "handler", "created", "FullName"})
public class Client implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "client_id") //,updatable = false, nullable = false)
    private int clientID;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "passport", unique = true)
    private String passport; //PassportScanURL

    @Column(name = "face_photo")
    private String facePhoto; //FacePhotoURL

    @Column(name = "driver_license")
    private String driverLicense; //driverLicenseScanURL

    @Column(name = "phone_num")
    private String phoneNumber;


    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "password")
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
//    @OneToOne(fetch = FetchType.LAZY, mappedBy = "client", cascade = CascadeType.ALL)
//    @JsonBackReference
//    private Order order;



//    @Fetch(FetchMode.JOIN)
//    @OneToMany(mappedBy = "item", fetch = FetchType.LAZY)
//    private List<Order> orders;



}
