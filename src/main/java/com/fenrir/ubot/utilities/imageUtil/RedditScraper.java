package com.fenrir.ubot.utilities.imageUtil;

import com.fenrir.ubot.exception.ImageException;
import com.fenrir.ubot.utilities.Utilities;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class RedditScraper {

    private static RedditScraper scraper = null;

    private HashMap<String, ArrayList<ImageData>> images;
    private HashMap<String, Long> updatesCoolDown;

    private final String[] availableFormats;

    private RedditScraper() {
        images = new HashMap<>();
        updatesCoolDown = new HashMap<>();
        availableFormats = new String[]{".jpg", ".jpeg", ".png", ".webp", ".gif"};
    }

    public static RedditScraper getInstance() {
        if(scraper == null) {
            scraper = new RedditScraper();
        }
        return scraper;
    }

    public ImageData getMeme(String source) throws UnirestException, ImageException, JSONException {
        source = source.toLowerCase().trim();   //Subreddit names are not case-sensitive
        ImageData image;

        if(!images.containsKey(source)
                || !updatesCoolDown.containsKey(source)
                || System.currentTimeMillis() - updatesCoolDown.get(source) > 600_000) {

            ArrayList<ImageData> imageData = update(source);
            images.put(source, imageData);
            updatesCoolDown.put(source, System.currentTimeMillis());
        }

        if (images.get(source) != null && images.get(source).size() > 0) {
            image = Utilities.randomElementFromList(images.get(source));
        } else {
            throw new ImageException("No images found.");
        }

        return image;
    }

    private ArrayList<ImageData> update(String source) throws UnirestException, JSONException {

        JSONArray imageArray = Unirest.get("https://www.reddit.com/r/{subreddit}/top/.json?sort=top&t=day&limit=100")
                .header("User-Agent", "Mozilla/5.0")
                .routeParam("subreddit", source)
                .asJson()
                .getBody()
                .getObject()
                .getJSONObject("data")
                .getJSONArray("children");

        ArrayList<ImageData> imageData = new ArrayList<>();

        for (int i = 0; i < imageArray.length(); i++) {
            JSONObject data = imageArray.getJSONObject(i).getJSONObject("data");

            if(checkFormat(data.getString("url"))) {
                imageData.add(new ImageData(data.getString("url"),
                        data.getString("author"),
                        data.getString("title"),
                        data.getString("subreddit"),
                        data.getBoolean("over_18")));
            }
        }
        return imageData;
    }

    private boolean checkFormat(String string) {
        return Arrays.stream(availableFormats)
                .anyMatch(string::endsWith);
    }

}
