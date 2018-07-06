package nu.mine.mosher.xml;

import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class XmlUnMilestone {
    public static void main(final String... args) throws SAXException, IOException, XMLStreamException {
        final TagName milestone;
        if (args.length == 2) {
            milestone = new TagName("", "", args[1]);
        } else if (args.length == 3) {
            milestone = new TagName(args[2], args[1], "");
        } else {
            System.err.println("usage: java XmlUnMilestone input.xml milestone [namespace]");
            System.err.println("For example:");
            System.err.println("java XmlUnMilestone input.xml pb http://www.tei-c.org/ns/1.0");
            System.err.flush();
            return;
        }



        final String xmlIn = String.join("\n", Files.readAllLines(Paths.get(args[0]), StandardCharsets.UTF_8));

        final String result = unMilestone(xmlIn, milestone);

        System.out.print(result);
        System.out.flush();
        System.err.flush();
    }

    public static String unMilestone(final String xmlIn, final TagName milestone) throws SAXException, XMLStreamException, IOException {
        final StringWriter xmlOut = new StringWriter();

        final XMLReader xr = XMLReaderFactory.createXMLReader();
        xr.setContentHandler(new MilestoneContentHandler(milestone, xmlOut));
        xr.setErrorHandler(createErrorThrower());
        xr.parse(new InputSource(new StringReader(xmlIn)));

        return xmlOut.toString();
    }



    private static ErrorHandler createErrorThrower() {
        return new ErrorHandler() {
            @Override
            public void warning(final SAXParseException exception) throws SAXException {
                throw exception;
            }

            @Override
            public void error(final SAXParseException exception) throws SAXException {
                throw exception;
            }

            @Override
            public void fatalError(final SAXParseException exception) throws SAXException {
                throw exception;
            }
        };
    }
}
