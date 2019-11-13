package model;

import java.util.Objects;

public class WebLink {
    private String webName;
    private String webLink;
    private int linkX;
    private int linkY;
    private int linkLength;

    public void setLinkWidth(int linkWidth) {
        this.linkWidth = linkWidth;
    }

    public int getLinkWidth() {
        return linkWidth;
    }

    private int linkWidth;

    public boolean hasLine = false;


    public void setLinkX(int linkX) {
        this.linkX = linkX;
    }

    public void setLinkY(int linkY) {
        this.linkY = linkY;
    }

    public void setLinkLength(int linkLength) {
        this.linkLength = linkLength;
    }

    public int getLinkX() {
        return linkX;
    }

    public int getLinkY() {
        return linkY;
    }

    public int getLinkLength() {
        return linkLength;
    }

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

    public boolean isMouseOverLink(double mouseX, double mouseY) {


        return new MousePosDetector().detectMousePos(mouseX, mouseY,
                getLinkX(),
                getLinkX() + getLinkWidth(),
                getLinkY() - getLinkLength(),
                getLinkY());


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


