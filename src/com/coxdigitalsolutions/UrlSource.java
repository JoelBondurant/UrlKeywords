package com.coxdigitalsolutions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A class to abstract for the source of URLs.
 * @author Joel Bondurant
 */
public class UrlSource {
    
    /**
     * Primitive way of storing the data in memory for version one.
     */
    private List<String> urlList;
    
    public UrlSource() {
        this.urlList = new ArrayList<>();
        try (
            InputStream urlStream = this.getClass().getResourceAsStream("URLSET.txt");
            BufferedReader urlReader = new BufferedReader(new InputStreamReader(urlStream));
            ) {
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
    
    public List<String> getUrlList() {
        return urlList;
    }
    
}
