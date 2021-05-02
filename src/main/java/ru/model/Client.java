package ru.model;



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
@Table(name = "Clients", schema = "carsharing")
public class Client {

    @Id
    @Column(name = "client_id")
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
    private Order order;



//    @Fetch(FetchMode.JOIN)
//    @OneToMany(mappedBy = "item", fetch = FetchType.LAZY)
//    private List<Order> orders;



}
