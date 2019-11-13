package network;

import jdk.nashorn.internal.runtime.regexp.joni.Regex;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class GetRandomStringAPI {
    String urlBase;
    URL url;

    public GetRandomStringAPI(String urlBase, int count) {
        this.urlBase = urlBase + count;
        try {
            url = new URL(this.urlBase);
        } catch (MalformedURLException e) {
            System.out.println(e.getMessage());
        }
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


}
