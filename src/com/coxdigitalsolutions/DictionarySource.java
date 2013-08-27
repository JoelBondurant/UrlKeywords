package com.coxdigitalsolutions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A class to abstract for the source of URLs.
 * @author Joel Bondurant
 */
public class DictionarySource {
    
    /**
     * Primitive way of storing the data in memory for version one.
     */
    private List<String> dictionaryWords;
    
    public DictionarySource() {
        this.dictionaryWords = new ArrayList<>();
        try (
            InputStream dictStream = this.getClass().getResourceAsStream("american-english.txt");
            BufferedReader dictReader = new BufferedReader(new InputStreamReader(dictStream));
            ) {
                String word;
                while (true) {
                    word = dictReader.readLine();
                    if (word == null) {
                        break;
                    }
                    if (!word.isEmpty()) {
                        this.dictionaryWords.add(word.toLowerCase()); // making the dictionary all lower case for faster comparisons.
                    }
                }
        } catch (IOException ex) {
            Logger.getLogger(DictionarySource.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Collection<String> getDictionaryWords() {
        return dictionaryWords;
    }
    
}

