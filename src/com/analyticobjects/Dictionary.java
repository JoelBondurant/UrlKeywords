package com.analyticobjects;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * A class to abstract for the keyword lookup. I'm thinking a bloom filter might
 * work much better here, but using error free hashing for now.
 *
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

    /**
     * A method to check if a string is in the dictionary.
     *
     * @param str Any string to check.
     * @return True if in dictionary, False otherwise. With error-free hashing.
     */
    public boolean isStringInDictionary(String str) {
        return this.words.contains(str);
    }

    /**
     * Singleton accessor.
     *
     * @return The single Dictionary object.
     */
    public static synchronized Dictionary getInstance() {
        if (singletonInstance == null) {
            singletonInstance = new Dictionary();
        }
        return singletonInstance;
    }

}
