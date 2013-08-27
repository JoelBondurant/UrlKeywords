package com.coxdigitalsolutions;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * A class to abstract for the keyword lookup.
 * @author Joel Bondurant
 */
public class Dictionary {
    
    private static Dictionary singletonInstance = null;

    private final Set<String> words;
    
    private Dictionary() {
        this.words = new LinkedHashSet<>();
        DictionarySource ds = new DictionarySource();
        for (String dictionaryWord : ds.getDictionaryWords()) {
            this.words.add(dictionaryWord);
        }
    }
            
    public boolean isStringInDictionary(String str) {
        return this.words.contains(str);
    }
    
    public static synchronized Dictionary getInstance() {
        if (singletonInstance == null) {
            singletonInstance = new Dictionary();
        }
        return singletonInstance;
    }
    
}
