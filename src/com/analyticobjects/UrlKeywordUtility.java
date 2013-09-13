package com.analyticobjects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * A URL keyword utility class.
 *
 * @author Joel Bondurant
 */
public class UrlKeywordUtility {

    private final int MIN_KEYWORD_LENGTH = 3; // Thinking a and th aren't good keywords.
    private final Dictionary dictionary;

    /**
     * Constructs an instance with dictionary loaded.
     */
    public UrlKeywordUtility() {
        this.dictionary = Dictionary.getInstance();
    }

    /**
     * Decomposes a url string into a set of keywords based on dictionary
     * lookups.
     *
     * @param urlString Any url string. Note, for speed, the URL is not
     * validated.
     * @return Set of keywords for the supplied url string.
     */
    public Set<String> urlToKeywords(String urlString) {
        Set<String> allKeywords = new HashSet<>();
        urlString = urlString.replaceAll("http://", ""); // getting rid of boilerplate text
        urlString = urlString.replaceAll(".com", "");
        urlString = urlString.replaceAll("www.", "");
        urlString = urlString.replaceAll("index.html", "");
        urlString = urlString.toLowerCase(); // for faster case insensitive matching.
        List<String> urlChunks = Arrays.asList(urlString.split("[^a-zA-Z]"));
        for (String urlChunk : urlChunks) {
            if (urlChunk.length() < MIN_KEYWORD_LENGTH) {
                continue;
            }
            Set<String> keywordsFromString = reduceKeywordSet(urlChunk, keywordsFromUrlString(urlChunk));
            allKeywords.addAll(keywordsFromString);
        }
        return allKeywords;
    }

    /**
     * Decomposes a url substring into a set of keywords based on dictionary
     * lookups.
     *
     * @param urlChunk A portion of the url string to decompose.
     * @return A set of keywords of the string based on dictionary lookups.
     */
    private Set<String> keywordsFromUrlString(String urlChunk) {
        Set<String> keywords = new HashSet<>();
        if (urlChunk.length() < MIN_KEYWORD_LENGTH) {
            return keywords; // enforce a minimum keyword length.
        }
        if (dictionary.isStringInDictionary(urlChunk)) {
            keywords.add(urlChunk); // recursive base case of sorts.
            return keywords;
        }
        for (int i = 1; i < urlChunk.length(); i++) {
            String substringA = urlChunk.substring(0, i);
            String substringB = urlChunk.substring(i, urlChunk.length());
            if (dictionary.isStringInDictionary(substringA)) {
                if (substringA.length() >= MIN_KEYWORD_LENGTH) {
                    keywords.add(substringA);
                }
                keywords.addAll(keywordsFromUrlString(substringB));
            } else if (dictionary.isStringInDictionary(substringB)) {
                if (substringB.length() >= MIN_KEYWORD_LENGTH) {
                    keywords.add(substringB);
                }
                keywords.addAll(keywordsFromUrlString(substringA));
            }
        }
        return keywords;
    }

    /**
     * Reduces the full set of keywords to a minmal covering.
     *
     * @param urlChunk The url substring from where the full keywords were
     * derived.
     * @param fullKeywordSet Full keyword set to reduce.
     * @return A minimal set of keywords in the urlChunk.
     */
    private Set<String> reduceKeywordSet(String urlChunk, Set<String> fullKeywordSet) {
        List<String> keywordsOrdered = new ArrayList<>(fullKeywordSet);
        Collections.sort(keywordsOrdered, new KeywordComparator());
        for (String keyword : keywordsOrdered) { // give preference to longer keywords.
            if (!fullKeywordSet.contains(keyword)) {
                continue;
            }
            String urlChunkBitten = urlChunk.replaceAll(keyword, "");
            for (String keyword2 : keywordsOrdered) {
                if (keyword.equals(keyword2)) {
                    continue;
                }
                if (!urlChunkBitten.contains(keyword2)) {
                    fullKeywordSet.remove(keyword2);
                }
            }
        }
        return fullKeywordSet;
    }

    /**
     * Sort keywords by length then alphanumerically. For helping to give
     * priority to longer keywords.
     */
    public class KeywordComparator implements Comparator<String> {

        @Override
        public int compare(String s1, String s2) {
            int len1 = s1.length();
            int len2 = s2.length();
            if (len1 == len2) {
                return s1.compareTo(s2);
            }
            return len2 - len1;
        }
    }

}
