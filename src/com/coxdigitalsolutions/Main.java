package com.coxdigitalsolutions;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * A main class to handle executable jar responsibilities.
 * 
 * @author Joel Bondurant
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        if (args.length > 0) {
            UrlKeywordUtility urlUtil = new UrlKeywordUtility();
            Set<String> urlKeywords = urlUtil.urlToKeywords(args[0]);
            System.out.println(urlKeywords);
        } else {
            makeKeywordCSV();
        }
    }
    
    private static void makeKeywordCSV() {
        UrlKeywordUtility urlUtil = new UrlKeywordUtility();
        UrlSource urls = new UrlSource();
        try (
                FileWriter writer = new FileWriter("urlKeywords.csv");
            ) {
            
            for (String url : urls.getUrlList()) {
               Set<String> urlKeywords = urlUtil.urlToKeywords(url);
               System.out.println(url);
               System.out.println(urlKeywords);
               System.out.println();
               writer.append("\"" + url + "\",");
               writer.append(urlKeywords.toString().replace(" ", "").replace("[", "").replace("]", ""));
               writer.append("\n");
            }
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    

    
}
