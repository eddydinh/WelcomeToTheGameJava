package model;

import java.util.ArrayList;
import java.util.List;

public class LinkWithLinks extends WebLink {
    public LinkWithLinks(String webName, String webLink) {
        super(webName, webLink);
    }

    public List<WebLink> getLinks() {
        return links;
    }

    private List<WebLink> links = new ArrayList<>();


    //MODIFY: links
    //EFFECT: add new link to the list of links under this link
    public void addLinks(WebLink l) {
        if (!links.contains(l)) {
            links.add(l);
        }
    }
}
