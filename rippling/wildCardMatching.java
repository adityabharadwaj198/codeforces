package rippling;

import java.util.*;

public class wildCardMatching {
    
    public class TrieNode {
        Map<Character, TrieNode> branches;
        boolean isEnd;

        public TrieNode () {
            this.branches = new HashMap<>();
            this.isEnd = false;
        }
    }
    public class Trie {
        TrieNode root; 

        public Trie() {
            this.root = new TrieNode();
        }

        public void addStringToTrie(String input) {
            TrieNode cur = root; 
            iterateOver(input, cur);
        }

        public boolean doesStringExistInTrie(String input) {
            TrieNode cur = root;
            int index = 0;
            while (cur.isEnd == false && index < input.length()) {
                if (cur.branches.get(input.charAt(index)) != null) {
                    cur = cur.branches.get(input.charAt(index));
                } else {
                    return false;
                }
                index++;
            }
            return cur.isEnd;
        }
        
        public boolean doesPrefixExistInTrie(String input, TrieNode cur, int index) {
            if (index >= input.length()) {
                return cur.isEnd;
            }
            while (cur.isEnd == false && index < input.length()) {
                if (input.charAt(index) == '?') {
                    for (Character c: cur.branches.keySet()) {
                        if (doesPrefixExistInTrie(input, cur.branches.get(c), index+1)) {
                            return true;
                        }
                    }
                }
                if (input.charAt(index) == '*') {
                    for (Character c: cur.branches.keySet()) {
                        if (doesPrefixExistInTrie(input, cur.branches.get(c), index)) {
                            return true;
                        }
                    }
                    return false;
                }
                else if (cur.branches.get(input.charAt(index)) != null) {
                    cur = cur.branches.get(input.charAt(index));
                } else {
                    return false;
                }
                index++;
            }
            return cur.isEnd;
        }

        // public void recurse(String input, int index, TrieNode cur) {
        //     if (cur.branches.containsKey(input.charAt(index))) {
        //         cur = cur.branches.get(input.charAt(index));
        //     } else {
        //         TrieNode t = new TrieNode();
        //         cur.branches.put(input.charAt(index), t);
        //         cur = cur.branches.get(input.charAt(index));
        //     }
        //     recurse(input, index+1, cur);
        // }

        public void iterateOver(String input, TrieNode cur) {
            int i = 0;
            while (i < input.length()) {
                if (cur.branches.containsKey(input.charAt(i))) {
                    cur = cur.branches.get(input.charAt(i));
                } else {
                    cur.branches.put(input.charAt(i), new TrieNode());
                    cur = cur.branches.get(input.charAt(i));
                }
                i++;
            }
            cur.isEnd = true;
        }
    }

    public static void main (String[] args) {
        wildCardMatching wcMatching = new wildCardMatching();
        Trie t = wcMatching.new Trie();
        t.addStringToTrie("bhardu_boi");

        System.out.println(t.doesPrefixExistInTrie("?hardu_boi", t.root, 0));
        System.out.println(t.doesPrefixExistInTrie("*", t.root, 0));
        t.addStringToTrie("cb");
        System.out.println(t.doesPrefixExistInTrie("?a", t.root, 0));
    }
}
