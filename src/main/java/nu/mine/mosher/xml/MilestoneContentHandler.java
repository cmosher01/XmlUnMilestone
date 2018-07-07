package nu.mine.mosher.xml;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import java.io.Writer;
import java.util.Deque;
import java.util.LinkedList;

public class MilestoneContentHandler extends ContentHandlerToXmlStreamWriter implements ContentHandler {
    private final Deque<TagName> hierElements = new LinkedList<>();
    private final TagName milestone;

    public MilestoneContentHandler(final TagName milestone, final Writer out) throws XMLStreamException {
        super(XMLOutputFactory.newInstance().createXMLStreamWriter(out));
        this.milestone = milestone;
    }

    public void startElement (final String namespace, final String tag, final String legacyTag, final Attributes attributes) throws SAXException {
        final TagName tagName = new TagName(namespace, tag, legacyTag);

        if (this.hierElements.isEmpty()) {
            if (tagName.equals(this.milestone)) {
                this.hierElements.push(tagName);
            }
            super.startElement(tagName, attributes);
        } else {
            if (tagName.equals(this.milestone)) {
                breakMilestone(tagName, attributes);
            } else {
                this.hierElements.push(tagName);
                super.startElement(tagName, attributes);
            }
        }
    }

    private void breakMilestone(final TagName tagName, final Attributes attributes) throws SAXException {
        final Deque<TagName> temp = new LinkedList<>();

        TagName x = this.hierElements.pop();
        while (!x.equals(this.milestone)) {
            temp.push(x);
            super.endElement(x);
            x = this.hierElements.pop();
        }
        super.endElement(tagName);

        this.hierElements.push(tagName);
        super.startElement(tagName, attributes);
        while (!temp.isEmpty()) {
            final TagName y = temp.pop();
            this.hierElements.push(y);
            super.startElement(y, new AttributesImpl());
        }
    }

    public void endElement (final String namespace, final String tag, final String legacyTag) throws SAXException {
        final TagName tagName = new TagName(namespace, tag, legacyTag);

        if (tagName.equals(this.milestone)) {
            return;
        }

        if (!this.hierElements.isEmpty()) {
            this.hierElements.pop();
        }
        super.endElement(tagName);
    }
}
