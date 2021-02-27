package com.fenrir.ubot.utilities.imageUtil;

import com.fenrir.ubot.exception.ImageException;
import com.fenrir.ubot.utilities.Utilities;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class RedditScraper {

    private static RedditScraper scraper = null;

    private HashMap<String, ArrayList<ImageData>> images;
    private HashMap<String, Long> updatesCoolDown;

    private RedditScraper() {
        images = new HashMap<>();
        updatesCoolDown = new HashMap<>();
    }

    public static RedditScraper getInstance() {
        if(scraper == null) {
            scraper = new RedditScraper();
        }
        return scraper;
    }

    public void getMeme(String source) throws UnirestException, ImageException {
        if(!images.containsKey(source)
                || !updatesCoolDown.containsKey(source)
                || System.currentTimeMillis() - updatesCoolDown.get(source) > 600_000) {

            ArrayList<ImageData> imageData = update(source);
            images.put(source, imageData);
            updatesCoolDown.put(source, System.currentTimeMillis());
        }

        if (images.get(source) != null && images.get(source).size() > 0) {
            ImageData image = Utilities.randomElementFromList(images.get(source));
            System.out.println(image.getUrl());
        } else {
            throw new ImageException("No images found.");
        }
    }

    private ArrayList<ImageData> update(String source) throws UnirestException {

        JSONArray response = Unirest.get("https://domainfsdf.tiiny.{site}/")
                .routeParam("site", source)
                .asJson()
                .getBody()
                .getObject()
                .getJSONObject("data")
                .getJSONArray("children");

        ArrayList<ImageData> imageData = new ArrayList<>();

        for (int i = 0; i < response.length(); i++) {
            JSONObject data = response.getJSONObject(i).getJSONObject("data");
            imageData.add(new ImageData(data.getString("url"),
                    data.getString("author_fullname"),
                    data.getString("title"),
                    data.getString("subreddit"),
                    data.getBoolean("over_18")));
        }

        return imageData;

    }

}
