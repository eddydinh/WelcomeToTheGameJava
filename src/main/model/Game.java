package model;

import java.util.List;

public abstract class Game {
    public static final int WIDTH = 1500;
    public static final int LENGTH = 1000;
    public static final int MAX_PASSWORD_LENGTH = 15;
    public static final String GAME_OVER = "GAME_OVER";
    public static final String HACK_SCREEN = "HACK_SCREEN";
    public static final String GAME_LOG_IN = "GAME_LOG_IN";
    public static final String GAME_IS_PLAYED = "GAME_IS_PLAYED";
    public static final String DEFAULT_PASSWORD = "*******";
    public static final String DEFAULT_NOTES_NAME = "Notes";
    public static final String DEFAULT_BROWSER_NAME = "A.N.N";
    public static final String DEFAULT_NOTE_FILE_PATH = "src/notepad.txt";
    public static final String DEFAULT_WEB_LINKS_PATH = "src/weblinks.txt";
    public static final String DEFAULT_CODES_FILE_PATH = "src/hacktyper.txt";
    protected static final String DEFAULT_NOTE_IMAGE_PATH = "src/img/notePadIcon.png";
    protected static final String DEFAULT_BROWSER_IMAGE_PATH = "src/img/browserIcon.png";
    protected static final String RAND_STRING_URL_BASE = "https://www.passwordrandom.com/query?command=guid&format=plain&count=";

    public List<String> lines;

    public String getState() {
        return state;
    }

    protected String state;

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


}
