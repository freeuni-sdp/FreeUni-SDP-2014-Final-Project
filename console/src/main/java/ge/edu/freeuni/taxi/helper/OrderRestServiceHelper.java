package ge.edu.freeuni.taxi.helper;

import ge.edu.freeuni.taxi.Driver;
import ge.edu.freeuni.taxi.PassengerOrder;
import ge.edu.freeuni.taxi.manager.DriversManager;

import java.util.Date;

public class OrderRestServiceHelper {

    public static void modifyPassengerOrderAndAssignDriver(PassengerOrder passengerOrder) {
        Driver driver = DriversManager.getInstance().getDriver(passengerOrder.getDriver().getId());
        passengerOrder.setDriver(driver);
        passengerOrder.setActive(true);
        passengerOrder.setProcessed(true);
        passengerOrder.setCreateTime(new Date());
    }
}
