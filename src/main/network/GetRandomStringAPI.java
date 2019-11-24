package network;

import model.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;


import java.util.ArrayList;
import java.util.List;


public class GetRandomStringAPI {
    private String urlBase;
    private URL url;
    private ConcreteHackingGame game;
    private int count;

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

    //MODIFIES: this
    //EFFECT: create a string of random website links
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


    //MODIFIES:this, ConcreteHackingGame
    //EFFECT: generate random links for browser page

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

        if (count < game.keys.size()) {
            game.keys = game.keys.subList(0, count);

        }
    }


    //MODIFIES:this, ConcreteHackingGame, BrowserPage
    //EFFECT: make a random web link

    private void makeWebLink(List<String> webNamesArray, int i, List<String> webLinksArray) {
        String webName = webNamesArray.get(i);
        double d = Math.random();
        game.webNames.add(webName);
        WebLink link;
        if (d < 0.3) {
            if (count < 4) {
                link = new LinkWithCode(webName, "http://" + webLinksArray.get(i) + ".ann");
                ((LinkWithCode) link).setLinkImg(game.getImage(game.LINK_IMG_PATH + count + ".png"));
                ((LinkWithCode) link).setKey(game.keys.get(count));
                count++;
            } else {
                link = new BrokenWebLink(webName, "http://" + webLinksArray.get(i) + ".ann");
            }
        } else {
            link = new LinkWithLinks(webName, "http://" + webLinksArray.get(i) + ".ann");

        }

        game.getBrowserPage().addWebLink(link);
    }


}
