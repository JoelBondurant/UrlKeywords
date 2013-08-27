package com.coxdigitalsolutions;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Joel Bondurant
 */
public class UrlKeywordUtilityTest {
    
    private final UrlKeywordUtility urlUtil;
    
    public UrlKeywordUtilityTest() {
        urlUtil = new UrlKeywordUtility();
    }

    /**
     * Test of urlToKeywords method, of class UrlKeywordUtility.
     */
    @Test
    public void testUrlToKeywords() {
        System.out.println("Testing: UrlKeywordUtilityTest.urlToKeywords\n");
        String urlString1 = "http://www.wfmz.com/lifestyle/travel/Investigators-get-voice-recorder-from-JetBlue-flight/-/133130/9758668/-/e0so4d/-/index.html";
        String urlString2 = "http://stumbleupon.com";
        String urlString3 = "http://fantastic.com";
        Set<String> expResult1 = new HashSet<>(Arrays.asList("lifestyle","travel","investigators","get","voice","recorder","from","jetblue","flight"));
        Set<String> expResult2 = new HashSet<>(Arrays.asList("stumble","upon"));
        Set<String> expResult3 = new HashSet<>(Arrays.asList("fantastic"));
        Set<String> result1 = urlUtil.urlToKeywords(urlString1);
        Set<String> result2 = urlUtil.urlToKeywords(urlString2);
        Set<String> result3 = urlUtil.urlToKeywords(urlString3);
        System.out.println("Test URL 1: " + "\n" + urlString1 + "\n");
        System.out.println("Expected result: " + "\n" + expResult1.toString() + "\n");
        System.out.println("Actual result: " + "\n" + result1.toString() + "\n");
        System.out.println("Test URL 2: " + "\n" + urlString2 + "\n");
        System.out.println("Expected result: " + "\n" + expResult2.toString() + "\n");
        System.out.println("Actual result: " + "\n" + result2.toString() + "\n");
        System.out.println("Test URL 3: " + "\n" + urlString3 + "\n");
        System.out.println("Expected result: " + "\n" + expResult3.toString() + "\n");
        System.out.println("Actual result: " + "\n" + result3.toString() + "\n");
        assertEquals(expResult1, result1);
        assertEquals(expResult2, result2);
        assertEquals(expResult3, result3);
    }
    
}
