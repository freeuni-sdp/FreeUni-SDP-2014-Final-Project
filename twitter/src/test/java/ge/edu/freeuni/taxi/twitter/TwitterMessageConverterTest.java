package ge.edu.freeuni.taxi.twitter;

import ge.edu.freeuni.taxi.core.MessageType;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

/**
 * @author Sandro Dolidze
 */
public class TwitterMessageConverterTest {
    private TwitterMessageConverter converter = new TwitterMessageConverter();

    @Test(expected = RuntimeException.class)
    public void testParseMessageThrowsExceptionOnBadInput() {
        converter.parseMessageType("Game of Thrones");
    }

    @Test
    public void testParseMessageType() {
       assertEquals(MessageType.CLIENT_ORDERED, converter.parseMessageType("#freeunitaxi order taxi"));
    }

    @Test(expected = RuntimeException.class)
    public void testFormatMessageTypeThrowsExceptionOnNull() {
        converter.formatMessageType(null);
    }

    @Test
    public void testFormatMessageType() {
        assertTrue(converter.formatMessageType(MessageType.DRIVER_ARRIVED).contains("arrived"));
    }
}