package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LinkWithCodeTest {
    LinkWithCode linkWithCode;
    @BeforeEach
    void runBefore(){
        linkWithCode = new LinkWithCode("testName1", "testLink1");
    }

}