package com.coxdigitalsolutions;


import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * A URL keyword utility class.
 * @author Joel Bondurant
 */
public class UrlKeywordUtility {
    
    private final int MIN_KEYWORD_LENGTH = 3;
    private Dictionary dictionary;
    
    public UrlKeywordUtility() {
        this.dictionary = Dictionary.getInstance();
    }
    
    public Set<String> urlString2Keywords(String urlString) {
        Set<String> allKeywords = new HashSet<>();
        urlString = urlString.replaceAll("http://", "");
        urlString = urlString.replaceAll(".com", "");
        urlString = urlString.replaceAll("www.", "");
        List<String> urlChunks = Arrays.asList(urlString.split("[^a-zA-Z]"));
        for (String urlChunk : urlChunks) {
            Set<String> keywordsFromString = reduceKeyWordSetV1(keywordsFromString(urlChunk));
            allKeywords.addAll(keywordsFromString);
        }
        return allKeywords;
    }
    
    private Set<String> keywordsFromString(String str) {
        Set<String> keywords = new HashSet<>();
        if (str.length() < MIN_KEYWORD_LENGTH) {
            return keywords; // enforce a minimum keyword length.
        }
        if (dictionary.isStringInDictionary(str)) {
            keywords.add(str); // recursive base case of sorts.
            return keywords;
        }
        for (int i = 1; i < str.length(); i++) {
            String substringA = str.substring(0, i);
            String substringB = str.substring(i, str.length());
            if (dictionary.isStringInDictionary(substringA)) {
                if (substringA.length() >= MIN_KEYWORD_LENGTH) {
                    keywords.add(substringA);
                }
                keywords.addAll(keywordsFromString(substringB));
            } else if (dictionary.isStringInDictionary(substringB)) {
                if (substringB.length() >= MIN_KEYWORD_LENGTH) {
                    keywords.add(substringB);
                }
                keywords.addAll(keywordsFromString(substringA));
            }
        }
        return keywords;
    }
    
    private Set<String> reduceKeyWordSetV1(Set<String> fullKeywordSet) {
        Set<String> reducedSet = new HashSet<>(fullKeywordSet);
        for (String keywordA : fullKeywordSet) {
            for (String keywordB : fullKeywordSet) {
                if (keywordA.equals(keywordB)) {
                    continue;
                }
                if (keywordA.contains(keywordB)) {
                    reducedSet.remove(keywordB);
                } else if (keywordB.contains(keywordA)) {
                    reducedSet.remove(keywordA);
                }
            }
        }
        return reducedSet;
    }


}
