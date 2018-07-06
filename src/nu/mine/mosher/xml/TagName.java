package nu.mine.mosher.xml;

public class TagName {
    private final String namespace;
    private final String tag;
    private final String legacyTag;

    public TagName(final String namespace, final String tag, final String legacyTag) {
        this.namespace = namespace;
        this.tag = tag;
        this.legacyTag = legacyTag;
    }

    public String namespace() {
        return this.namespace;
    }

    public String tag() {
        return this.tag;
    }

    public String legacyTag() {
        return this.legacyTag;
    }

    @Override
    public boolean equals(final Object object) {
        if (!(object instanceof TagName)) {
            return false;
        }

        final TagName that = (TagName)object;

        if (this.namespace.isEmpty() && that.namespace.isEmpty()) {
            return this.legacyTag.equals(that.legacyTag);
        }

        return this.namespace.equals(that.namespace) && this.tag.equals(that.tag);
    }

    @Override
    public String toString() {
        if (this.namespace.isEmpty()) {
            if (this.legacyTag.isEmpty()) {
                return this.tag;
            }
            return this.legacyTag;
        }
        return this.tag;
    }
}
