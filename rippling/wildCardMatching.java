package rippling;

import java.util.*;

public class wildCardMatching {
    
    public class TrieNode { // create trie 
        Map<Character, TrieNode> branches; // Map keeping track of character -> trieNode 
        boolean isEnd; // boolean var signifying if it's the end. 

        public TrieNode () {
            this.branches = new HashMap<>(); 
            this.isEnd = false;
        }
    }
    public class Trie { 
        TrieNode root;  // root level TrieNode. This will kind of contain all the first characters to TrieNode mappings of the strings that 
        // you input to the Trie.

        public Trie() { 
            this.root = new TrieNode(); // constructor calss Tri
        }

        public void addStringToTrie(String input) { 
            TrieNode cur = root; 
            int i = 0; 
            while (i < input.length()) { // #1. iterate over the input string 
                if (cur.branches.containsKey(input.charAt(i))) { // #2a. If the current trieNode contains mapping from current character of string to a trieNode ->>>>> 
                    cur = cur.branches.get(input.charAt(i)); // then go to that trieNode 
                } else {// #2B. If the current trieNode doesn't contain any such mapping, then create that mapping, make sure to create a new TrieNode here 
                    cur.branches.put(input.charAt(i), new TrieNode());
                    cur = cur.branches.get(input.charAt(i)); // then go to that trieNode
                }
                i++; // increment i so that you can check the next character. 
            }
            cur.isEnd = true; // on the last trieNode where cur will have reached automatically, just set isEnd = true, because it's the end of some string. 
        }

        public boolean doesStringExistInTrie(String input) {
            TrieNode cur = root;
            int index = 0;
            while (cur.isEnd == false && index < input.length()) { //1.  iterate over the string. Only iterate till cur.isEnd = false because if cur.isEnd is true, then 
                // something like "bhardu" is your input string and "bha" is in your trie.
                if (cur.branches.get(input.charAt(index)) != null) { // 2.a advance to the next node. 
                    cur = cur.branches.get(input.charAt(index));
                } else { //2.b if the node at which we are currently on doesn't contain the mapping for the character that we are on -> it means that we don't have this specific input string in the trie, so just return false from here. 
                    return false;
                }
                index++; // increment index -> enables while loop to iterate over the input string. 
            }
            return cur.isEnd; // Return cur.isEnd. If the complete word was in Trie, this will be true, If situation is Like "bha" is input & "bhardu" is in trie, then it will be false.
        }
        
        public boolean doesPrefixExistInTrie(String input, TrieNode cur, int index) {
            if (index >= input.length()) {
                return cur.isEnd;
            }
            while (cur.isEnd == false && index < input.length()) { //1.  iterate over the string. Only iterate till cur.isEnd = false because if cur.isEnd is true, then 
                // something like "bhardu" is your input string and "bha" is in your trie.

                if (input.charAt(index) == '?') { // if it's ? then skip one character. In this case you will need to do something like backtracking
                    for (Character c: cur.branches.keySet()) { // for every possibility (i.e every characterk key on this trieNode ) ->>>>>
                        if (doesPrefixExistInTrie(input, cur.branches.get(c), index+1)) { //try to check if the prefix exists in trie.
                            return true;
                        }
                    }
                }
                if (input.charAt(index) == '*') { // if it's *, then 
                    for (Character c: cur.branches.keySet()) { // for every every character key on this trieNode, don't advance the iterator on the string
                    // but advance the iterator using the trieNode Map. So the iterator for string will still keep pointing to index
                    // but the iterator / cur trieNode for trieNode will put to the possible trieNodes that are mapped. 
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

    }

    public static void main (String[] args) {
        wildCardMatching wcMatching = new wildCardMatching();
        Trie t = wcMatching.new Trie();
        t.addStringToTrie("bhardu_boi");

        System.out.println(t.doesPrefixExistInTrie("?hardu_boi", t.root, 0));
        System.out.println(t.doesPrefixExistInTrie("*", t.root, 0));
        t.addStringToTrie("cb");
        System.out.println(t.doesPrefixExistInTrie("?a", t.root, 0));
        System.out.println(t.doesPrefixExistInTrie("*boi", t.root, 0));
    }
}
