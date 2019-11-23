package model;

import java.awt.*;

public class LinkWithCode extends WebLink {
    public Image getLinkImg() {
        return linkImg;
    }

    public void setLinkImg(Image linkImg) {
        this.linkImg = linkImg;
    }

    private Image linkImg;
    private String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public LinkWithCode(String webName, String webLink) {
        super(webName, webLink);
    }
}
