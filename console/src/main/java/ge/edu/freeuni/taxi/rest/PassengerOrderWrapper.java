package ge.edu.freeuni.taxi.rest;

import ge.edu.freeuni.taxi.Driver;
import ge.edu.freeuni.taxi.PassengerOrder;

import java.util.List;

public class PassengerOrderWrapper {
    private PassengerOrder order;
    private List<Driver> drivers;

    public PassengerOrder getOrder() {
        return order;
    }

    public void setOrder(PassengerOrder order) {
        this.order = order;
    }

    public List<Driver> getDrivers() {
        return drivers;
    }

    public void setDrivers(List<Driver> drivers) {
        this.drivers = drivers;
    }
}
