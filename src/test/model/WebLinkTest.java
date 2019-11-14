package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class WebLinkTest {

    @Test
    void testToString() {
        WebLink webLink = new WebLink("testWeb", "http://test1.ann");
        assertEquals(webLink.getWebLink(), webLink.toString());
    }

    @Test
    void testSetLink() {
        WebLink webLink = new WebLink("testWeb", "http://test1.ann");
        assertEquals(webLink.getWebLink(), webLink.toString());
        webLink.setLinkLength(10);
        webLink.setLinkWidth(10);
        webLink.setLinkX(10);
        webLink.setLinkY(10);

        assertEquals(10, webLink.getLinkX());
        assertEquals(10, webLink.getLinkY());
        assertEquals(10, webLink.getLinkWidth());
        assertEquals(10, webLink.getLinkLength());

        double mouseX = 10 + 1;
        double mouseY = 10 - 5;
        assertTrue(webLink.isMouseOverLink(mouseX, mouseY));
        mouseX = 10 + 20;
        mouseY = 10 + 20;
        assertFalse(webLink.isMouseOverLink(mouseX, mouseY));
    }

    @Test
    void testBrokenLink(){
        WebLink brokenLink = new BrokenWebLink("test","test");
        assertEquals("test", brokenLink.getWebName());
        assertEquals("test", brokenLink.getWebLink());
    }

    @Test
    void testAddLink(){
        WebLink webLink1 = new WebLink("testWeb", "http://test1.ann");
        WebLink webLink2 = new WebLink("testWeb", "http://test1.ann");
        WebLink webLink3 = new WebLink("testWeb0", "http://test0.ann");

        LinkWithLinks linkWithLinks = new LinkWithLinks("testWebLinks", "http://test2.ann");
        linkWithLinks.addLinks(webLink1);
        assertEquals(1, linkWithLinks.getLinks().size());
        linkWithLinks.addLinks(webLink2);
        assertEquals(1, linkWithLinks.getLinks().size());
        linkWithLinks.addLinks(webLink3);
        assertEquals(2, linkWithLinks.getLinks().size());

    }


    @Test
    void testEqualsOverridden() {
        WebLink webLink1 = new WebLink("testWeb", "http://test1.ann");
        WebLink webLink2 = new WebLink("testWeb", "http://test1.ann");
        WebLink webLink3 = null;
        Object obj = new Object();
        Page browserPage = new BrowserPage(1, 2, 3, 4, "testPageName");
        assertEquals(webLink1, webLink2);
        assertEquals(webLink1, webLink1);
        assertEquals(webLink1.hashCode(), webLink2.hashCode());
        assertEquals(webLink1.hashCode(), webLink1.hashCode());
        assertFalse(webLink1 == obj);
        assertFalse(webLink1.equals(browserPage));
        assertFalse(webLink1.hashCode() == obj.hashCode());
        assertFalse(webLink1.hashCode() == browserPage.hashCode());


    }


}
