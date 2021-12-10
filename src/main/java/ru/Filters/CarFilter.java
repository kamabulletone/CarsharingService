package ru.Filters;

import org.springframework.lang.Nullable;

public class CarFilter {

    @Nullable
    private String carMark;

    @Nullable
    private String carModel;

    @Nullable
    public String getCarMark() {
        return carMark;
    }

    public void setCarMark(@Nullable String carMark) {
        this.carMark = carMark;
    }

    @Nullable
    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(@Nullable String carModel) {
        this.carModel = carModel;
    }
}
