package com.coxdigitalsolutions;

import java.util.Set;




/**
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
            Set<String> urlKeywords = urlUtil.urlString2Keywords(args[0]);
            System.out.println(urlKeywords);
        } else {
            makeKeywordCSV();
        }
    }
    
    private static void makeKeywordCSV() {
        UrlKeywordUtility urlUtil = new UrlKeywordUtility();
        UrlSource urls = new UrlSource();
        for (String url : urls.getUrlList()) {
           Set<String> urlKeywords = urlUtil.urlString2Keywords(url);
           System.out.println(url);
           System.out.println(urlKeywords);
           System.out.println();
        }
    }
    

    
}
