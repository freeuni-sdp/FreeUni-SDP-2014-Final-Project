package ge.edu.freeuni.taxi.core;

/**
 * @author Sandro Dolidze
 *
 * This enum does not contain all possible message types.
 * I'm open for change and addition requests :)
 */
public enum MessageType {
    CLIENT_ORDERED,
    CLIENT_CANCELED,
    DRIVER_ARRIVED,
    DRIVER_DELIVERED_CLIENT,
    DRIVER_LOCATION_UPDATED,
    DRIVER_ASSIGNED_TO_ORDER,
    DRIVERS_GET_NOTIFIED_ABOUT_ORDER
}