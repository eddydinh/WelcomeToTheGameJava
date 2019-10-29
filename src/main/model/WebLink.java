package model;

import java.util.Objects;

public class WebLink {
    private String webName;
    private String webLink;

    public WebLink(String webName, String webLink) {
        setWebName(webName);
        setWebLink(webLink);
    }

    public String getWebName() {
        return webName;
    }

    public void setWebName(String webName) {
        this.webName = webName;
    }

    public String getWebLink() {
        return webLink;
    }

    public void setWebLink(String webLink) {
        this.webLink = webLink;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        WebLink webLink1 = (WebLink) o;
        return getWebName().equals(webLink1.getWebName())
                && getWebLink().equals(webLink1.getWebLink());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getWebName(), getWebLink());
    }

    @Override
    public String toString() {
        return getWebLink();
    }


}


