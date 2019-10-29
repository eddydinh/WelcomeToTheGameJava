package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class WebLinkTest {

    @Test
    void testToString() {
        WebLink webLink = new WebLink("testWeb", "http://test1.ann");
        assertEquals(webLink.getWebLink(), webLink.toString());
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
