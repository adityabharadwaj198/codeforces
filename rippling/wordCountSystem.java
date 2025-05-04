package rippling;
import java.util.*;

public class wordCountSystem {

    public class Pair {
        String word;
        int freq;

        public Pair(String w, int f) {
            this.word = w;
            this.freq = f;
        }
    }
    
    public int wordCount(String document) {
        // assuming that the words inside hte document are seperated by single " "
        return document.split(" ").length;
    }

    public List<String> commonWords(String document, int k) {
        String[] words = document.split(" ");
        Map<String, Integer> freq = new HashMap<>();
        for (int i=0; i<words.length; i++) {
            freq.put(words[i], freq.getOrDefault(words[i], 0) + 1);
        }
        PriorityQueue<Pair> mypq = new PriorityQueue<>((Pair p1, Pair p2) -> {
            return p1.freq - p2.freq;
        });
        
        for (String s: freq.keySet()) {
            if (mypq.size() >= k) {
                if (freq.get(s) > mypq.peek().freq) {
                    mypq.poll();
                    mypq.offer(new Pair(s, freq.get(s)));
                }
            } else {
                mypq.offer(new Pair(s, freq.get(s)));
            }
        }

        List<String> kCommon = new ArrayList<>();
        while (!mypq.isEmpty()) {
            kCommon.add(mypq.poll().word);
        }
        return kCommon;
    }

    public static void main(String[] args) {
        String s = "hello I am under the water please come and save me hello under the water bitch I'm poseidon";
        wordCountSystem wcs = new wordCountSystem();
        System.out.println(wcs.commonWords(s,5 ));
    }
    
    
}
