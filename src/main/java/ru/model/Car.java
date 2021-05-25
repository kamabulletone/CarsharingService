package ru.model;



import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Parent;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Validated
@Table(name = "cars")

public class Car{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "car_id", updatable = false, nullable = false)
    private int carId;


    @Column(name = "car_registr_num", unique = true, updatable = false)
//    @Pattern(regexp = "^[АВЕКМНОРСТУХ][0-9]{3}[АВЕКМНОРСТУХ]{2}[0-9]{2}",message = "Буквенные символы могут быть только - [А,В,Е,К,М,Н,О,Р,С,Т,У,Х],\n Общий вид: У199ВА78")
//    @NotBlank(message = "Введите регистрационный номер")
    private String carRegistrationNumber;

//    @NotBlank(message = "Введите регистрационный номер")
    @Column(name = "car_mark")
    private String carMark;

//    @NotBlank(message = "Введите регистрационный номер")
    @Column(name = "car_model")
    private String carModel;

//    @NotBlank(message = "Введите регистрационный номер")
    @Column(name = "car_status")
    private String carStatus;

}
