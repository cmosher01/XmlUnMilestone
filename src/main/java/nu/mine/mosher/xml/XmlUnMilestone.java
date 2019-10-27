package nu.mine.mosher.xml;

import org.xml.sax.*;

import javax.xml.parsers.*;
import javax.xml.stream.XMLStreamException;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;

public class XmlUnMilestone {
    public static void main(final String... args) throws SAXException, IOException, XMLStreamException, ParserConfigurationException {
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

    public static String unMilestone(final String xmlIn, final TagName milestone) throws SAXException, XMLStreamException, IOException, ParserConfigurationException {
        final StringWriter xmlOut = new StringWriter();

        final SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        saxParserFactory.setNamespaceAware(true);
        final SAXParser saxParser = saxParserFactory.newSAXParser();

        final XMLReader xr = saxParser.getXMLReader();
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
