package ru.model;



import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(name = "clients", schema = "carsharing")
@JsonIgnoreProperties(ignoreUnknown = true,
        value = {"hibernateLazyInitializer", "handler", "created", "FullName"})
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "client_id",updatable = false, nullable = false)
    public int clientID;

    @Column(name = "full_name")
    public String FullName;

    @Column(name = "passport")
    public String passport; //PassportScanURL

    @Column(name = "face_photo")
    public String facePhoto; //FacePhotoURL

    @Column(name = "driver_license")
    public String driverLicense; //driverLicenseScanURL

    @Column(name = "phone_num")
    public String phoneNumber;

    @Column(name = "email")
    public String email;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "client")
    @JsonBackReference
    private Order order;



//    @Fetch(FetchMode.JOIN)
//    @OneToMany(mappedBy = "item", fetch = FetchType.LAZY)
//    private List<Order> orders;



}
