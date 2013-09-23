package com.analyticobjects;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A class to abstract for the source of URLs.
 *
 * @author Joel Bondurant
 */
public class UrlSource {
    
    private static UrlSource singletonInstance;

    /**
     * Primitive way of storing the data in memory for version one.
     */
    private List<String> urlList;
    
    static synchronized UrlSource getInstance() {
        if (singletonInstance == null) {
            singletonInstance = new UrlSource();
        }
        return singletonInstance;
    }

    private UrlSource() {
        this.urlList = new LinkedList<>();
        try (
                InputStream urlStream = this.getClass().getResourceAsStream("URLSET.txt");
                BufferedReader urlReader = new BufferedReader(new InputStreamReader(urlStream));) {
            String url;
            while (true) {
                url = urlReader.readLine();
                if (url == null) {
                    break;
                }
                if (!url.isEmpty()) {
                    this.urlList.add(url);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(DictionarySource.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    List<String> getList() {
        return Collections.unmodifiableList(urlList);
    }
    
    List<List<String>> getListOfLists(int numberOfLists) {
        List<List<String>> lists = new ArrayList<>();
        int listSize = urlList.size() / numberOfLists + 1;
        for (int i = 0; i < urlList.size(); i += listSize) {
            lists.add(urlList.subList(i, i + Math.min(listSize, urlList.size() - i)));
        }
        return lists;
    }
    
    int size() {
        return urlList.size();
    }

}
