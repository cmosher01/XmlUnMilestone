package nu.mine.mosher.xml;

import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class XmlUnMilestoneTest {
    @Test
    void nominal() throws Throwable {
        final String input = "<?xml version=\"1.0\" ?><x>AB<ms/>CD<ms/>EF</x>";
        final String tag = "ms";
        final String expected = "<?xml version=\"1.0\" ?><x>AB<ms>CD</ms><ms>EF</ms></x>";

        final String actual = XmlUnMilestone.unMilestone(input, new TagName("", "", tag));
        assertEquals(expected, actual);
    }
}
