package model;

import java.util.HashSet;
import java.util.List;

public abstract class HackingGame {
    public static final int WIDTH = 1500;
    public static final int LENGTH = 1000;
    public static final int MAX_PASSWORD_LENGTH = 15;
    public static final String GAME_OVER = "GAME_OVER";
    public static final String HACK_SCREEN = "HACK_SCREEN";
    public static final String GAME_LOG_IN = "GAME_LOG_IN";
    public static final String GAME_IS_PLAYED = "GAME_IS_PLAYED";
    public static final String WIN = "WIN";
    public static final String DEFAULT_PASSWORD = "*******";
    public static final String DEFAULT_NOTES_NAME = "Notes";
    public static final String DEFAULT_BROWSER_NAME = "A.N.N";
    public static final String DEFAULT_NOTE_FILE_PATH = "./data/notepad.txt";
    public static final String DEFAULT_WEB_LINKS_PATH = "./data/weblinks.txt";
    public static final String DEFAULT_CODES_FILE_PATH = "./data/hacktyper.txt";
    public static final String DEFAULT_KEYS_FILE_PATH = "./data/keys.txt";
    public static final String LINK_IMG_PATH = "./data/img/key";
    public static final String GAME_TRULY_OVER = "GAME_TRULY_OVER";
    protected static final String DEFAULT_NOTE_IMAGE_PATH = "./data/img/notePadIcon.png";
    protected static final String DEFAULT_BROWSER_IMAGE_PATH = "./data/img/browserIcon.png";
    public static final String RAND_STRING_URL_BASE = "https://www.passwordrandom.com/query?command=guid&format=plain&count=";

    protected List<String> lines;

    public List<String> getLines() {
        return lines;
    }


    protected String state;

    public List<String> keys;

    public NotePadIcon getNotePadIcon() {
        return notePadIcon;
    }

    protected NotePadIcon notePadIcon;

    public NotePadPage getNotePadPage() {
        return notePadPage;
    }

    protected NotePadPage notePadPage;

    public BrowserIcon getBrowserIcon() {
        return browserIcon;
    }

    protected BrowserIcon browserIcon;

    public BrowserPage getBrowserPage() {
        return browserPage;
    }

    protected BrowserPage browserPage;

    public abstract void update(String gameState);

    public String getState() {
        return state;
    }



}
