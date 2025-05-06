package rippling;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.TreeSet;
/*
 * You are given a list of equivalent string pairs synonyms where synonyms[i] = [si, ti] indicates that si and ti are equivalent strings. You are also given a sentence text.

Return all possible synonymous sentences sorted lexicographically.

 

Example 1:

Input: synonyms = [["happy","joy"],["sad","sorrow"],["joy","cheerful"]], text = "I am happy today but was sad yesterday"
Output: ["I am cheerful today but was sad yesterday","I am cheerful today but was sorrow yesterday","I am happy today but was sad yesterday","I am happy today but was sorrow yesterday","I am joy today but was sad yesterday","I am joy today but was sorrow yesterday"]
Example 2:

Input: synonyms = [["happy","joy"],["cheerful","glad"]], text = "I am happy today but was sad yesterday"
Output: ["I am happy today but was sad yesterday","I am joy today but was sad yesterday"]

 */

public class SynonymousSentences { 
    class Solution {
    public List<String> generateSentences(List<List<String>> synonyms, String text) {
        Map<String, List<String>> graph = new HashMap<>();
        for (List<String> pair : synonyms) { // step 1. graph build 
            String w1 = pair.get(0), w2 = pair.get(1);
            connect(graph, w1, w2);
            connect(graph, w2, w1);
        }
        // BFS
        Set<String> ans = new TreeSet<>(); // 
        Queue<String> q = new LinkedList<>(); // step 2 bfs 
        q.add(text); 
        while (!q.isEmpty()) {
            String out = q.remove();
            ans.add(out); // Add to result
            String[] words = out.split("\\s"); // split the sentence
            for (int i = 0; i < words.length; i++) { // for every word which is in the graph, do BFS. 
                if (graph.get(words[i]) == null) continue;
                for (String synonym : graph.get(words[i])) { // Replace words[i] with its synonym
                    words[i] = synonym;
                    String newText = String.join(" ", words); // this is you exploring a certain word's neighbour
                    if (!ans.contains(newText)) q.add(newText); // this is you putting that result into the queue for further BFS from a certain string
                }
            }
        }
        return new ArrayList<>(ans);
    }
    void connect(Map<String, List<String>> graph, String v1, String v2) {
        graph.putIfAbsent(v1, new LinkedList<>());
        graph.get(v1).add(v2);
    }
}
}
