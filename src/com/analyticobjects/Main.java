package com.analyticobjects;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * A main class to handle executable jar responsibilities.
 * 
 * @author Joel Bondurant
 */
public class Main {
    
    private final Map<String, Set<String>> urlKeywordMap;
    private final UrlKeywordUtility urlUtil;
    private final UrlSource urlSource;
    
    Main() {
        this.urlKeywordMap = new ConcurrentHashMap<>(UrlSource.getInstance().size());
        this.urlUtil = new UrlKeywordUtility();
        this.urlSource = UrlSource.getInstance();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        if (args.length > 0) {
            UrlKeywordUtility urlUtil = new UrlKeywordUtility();
            Set<String> urlKeywords = urlUtil.urlToKeywords(args[0]);
            System.out.println(urlKeywords);
        } else {
            (new Main()).makeKeywordCSV();
        }
    }
    
    class UrlKeywordTask implements Callable<Object> {
        
        private List<String> urls;
        
        public UrlKeywordTask(List<String> urls) {
            this.urls = urls;
        }
        
        @Override
        public Object call() {
            for (String url : urls) {
                Set<String> urlKeywords = urlUtil.urlToKeywords(url);
                urlKeywordMap.put(url, urlKeywords);
            }
            return null;
        }
    }
    
    
    private void makeKeywordCSV() {
        // multi-threaded processing...
        int processorCount = Runtime.getRuntime().availableProcessors();
        ExecutorService executor = Executors.newFixedThreadPool(processorCount);     
        List<UrlKeywordTask> urlKeywordTasks = new ArrayList<>(processorCount);
        for (List<String> urls : UrlSource.getInstance().getListOfLists(processorCount)) {
            UrlKeywordTask urlKeywordTask = new UrlKeywordTask(urls);
            urlKeywordTasks.add(urlKeywordTask);
        }
        try {
            executor.invokeAll(urlKeywordTasks, 5, TimeUnit.MINUTES);
            List<Runnable> unfinished = executor.shutdownNow();
            if (!unfinished.isEmpty()) {
                throw new InterruptedException();
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } 
        // single-threaded write out...
        try (
                FileWriter writer = new FileWriter("urlKeywords.csv");
            ) {
            for (String url : UrlSource.getInstance().getList()) {
               Set<String> urlKeywords = urlKeywordMap.get(url);
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
