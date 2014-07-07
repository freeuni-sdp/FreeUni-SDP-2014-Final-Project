package ge.edu.freeuni.taxi.dispatcher;

import ge.edu.freeuni.taxi.Driver;
import ge.edu.freeuni.taxi.Location;
import ge.edu.freeuni.taxi.core.Message;
import ge.edu.freeuni.taxi.manager.DriversManager;

/**
 * Created by giorgi kochakidze on 7/7/14.
 *
 * this class is registered on MessageDispatcher
 * and when Driver will update her/his location
 * via twitter or another API which MessageProcessor
 * is processing, this class will trigger DriverManager to
 * update driver location
 */
public class DriverLocationUpdateListener implements  IncomingMessageListener{


    @Override
    public void onIncomingMessage(Message message) {
        Driver driver = DriversManager.getInstance().getDriver(message.getSender());
        Location location = new Location();
        location.setLatitude(message.getLocation().getLatitude());
        location.setLongitude(message.getLocation().getLongitude());
        DriversManager.getInstance().updateDriverLocation(driver.getId(), location);
    }
}
