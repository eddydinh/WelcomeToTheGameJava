package network;

import model.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


public class GetRandomStringAPI {
    String urlBase;
    URL url;
    ConcreteHackingGame game;

    public GetRandomStringAPI(String urlBase, int count) {
        this.urlBase = urlBase + count;
        try {
            url = new URL(this.urlBase);
        } catch (MalformedURLException e) {
            System.out.println(e.getMessage());
        }
        game = ConcreteHackingGame.getInstance();
    }

    public GetRandomStringAPI() {
        game = ConcreteHackingGame.getInstance();
        generateWebLinks();
    }

    public List<String> getRandomStringArray() {


        try (BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()))) {


            String line;

            List<String> strings = new ArrayList<String>();

            while ((line = br.readLine()) != null) {

                strings.add(line);
            }

            return strings;

        } catch (IOException e) {
            return null;
        }
    }


    private void generateWebLinks() {

        List<String> webNamesArray = null;
        try {
            webNamesArray = game.readToLines(game.DEFAULT_WEB_LINKS_PATH);
        } catch (IOException e) {
            System.out.println("Can't get file names from network");
        }

        GetRandomStringAPI randomStringAPI = new GetRandomStringAPI(game.RAND_STRING_URL_BASE,
                webNamesArray.size());
        List<String> webLinksArray = randomStringAPI.getRandomStringArray();
        for (int i = 0; i < webNamesArray.size(); i++) {

            makeWebLink(webNamesArray, i, webLinksArray);
        }


    }


    private void makeWebLink(List<String> webNamesArray, int i, List<String> webLinksArray) {
        String webName = webNamesArray.get(i);
        double d = Math.random();
        game.webNames.add(webName);
        WebLink link = null;
        if (d < 0.5) {
            link = new BrokenWebLink(webName, "http://" + webLinksArray.get(i) + ".ann");
        } else {
            link = new LinkWithLinks(webName, "http://" + webLinksArray.get(i) + ".ann");

        }
        game.getBrowserPage().addWebLink(link);
    }


}
