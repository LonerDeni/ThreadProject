package Synchronization.CarShowRoomAdvanced;

public abstract class ManufacturerCar {
    private String nameCar;
    private String nameModel;

    public ManufacturerCar(String nameCar, String nameModel) {
        this.nameCar = nameCar;
        this.nameModel = nameModel;
    }

    public String getNameModel() {
        return nameModel;
    }
    public String getNameCar() {
        return nameCar;
    }
}