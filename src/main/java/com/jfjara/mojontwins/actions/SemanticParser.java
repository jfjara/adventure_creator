package com.jfjara.mojontwins.actions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SemanticParser {

    public static String extractVerb(String text) {
        String[] words = text.split(" ");
        return words[0].trim();
    }

    public static List<String> extractObjects(String text) {
        String[] words = text.split(" ");
        List<String> list = new ArrayList<>();
        for (int i = 1; i < words.length; i++) {
            if (!isPreposition(words[i])
                    && !isArticle(words[i])
                    && !isPronoumDemostrative(words[i])
                    && !words[i].trim().equals("")) {
                list.add(words[i]);
            }
        }
        return list;
    }

    private static boolean isPreposition(String word) {
        List<String> prepositions = Arrays.asList("a", "ante", "bajo", "con", "contra", "de", "desde", "durante", "en",
                "entre", "hacia", "hasta", "mediante", "para", "por", "según", "sin", "sobre", "tras");
        return prepositions.stream().filter(p -> p.toUpperCase().equals(word.toUpperCase())).findFirst().isPresent();
    }

    private static boolean isArticle(String word) {
        List<String> articles = Arrays.asList("la", "las", "lo", "los", "el", "un", "una", "unos", "unas");
        return articles.stream().filter(p -> p.toUpperCase().equals(word.toUpperCase())).findFirst().isPresent();
    }

    private static boolean isPronoumDemostrative(String word) {
        List<String> pronoums = Arrays.asList("Esa", "ese", "eso", "esas", "esos", "esta", "este", "esto",
                "estos", "estas", "aquel", "aquello", "aquella", "aquellas", "aquellos");
        return pronoums.stream().filter(p -> p.toUpperCase().equals(word.toUpperCase())).findFirst().isPresent();
    }

}
