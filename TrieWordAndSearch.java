import java.util.HashMap;
import java.util.Map;

public class TrieWordAndSearch {

    class TrieNode {
        Map<Character, TrieNode> tmap;
        boolean isEnd;
        public TrieNode() {
            this.tmap = new HashMap<>();
            this.isEnd = false;
        }
    }

    TrieNode root;

    public TrieWordAndSearch() {
        this.root = new TrieNode();
    }
    
    public void addWord(String word) {
        TrieNode cur = this.root; 
        for (int i=0; i<word.length(); i++) {
            if (!cur.tmap.containsKey(word.charAt(i))) {
                cur.tmap.put(word.charAt(i), new TrieNode());
            }
            cur = cur.tmap.get(word.charAt(i));
        }
        cur.isEnd = true;
    }
    
    public boolean search(String word) {
        TrieNode cur = this.root;
        return search(word, cur, 0);
    }

    public boolean search(String word, TrieNode root, int index) {
        TrieNode cur = root;
        boolean isfound = false;
        if(cur.isEnd) {
            return cur.isEnd;
        }
        for (int i=index; i<word.length(); i++) {
            if (word.charAt(i) == '.') {
                for (Character c: cur.tmap.keySet()) {
                    isfound = search(word, root.tmap.get(c), index+1);
                    if (isfound == true) {
                        return true;
                    }
                }
            } else {
                if (cur.tmap.get(word.charAt(i)) != null) {
                    cur = cur.tmap.get(word.charAt(i));
                } else {
                    return false;
                }
            }
        }
        return isfound;
    }

    public static void main (String args[]) {
        TrieWordAndSearch obj = new TrieWordAndSearch();
        // [[],["bad"],["dad"],["mad"],["pad"],["bad"],[".ad"],["b.."]]
//        ["WordDictionary","addWord","addWord","addWord","search","search","search","search"]

        obj.addWord("bad");
        obj.addWord("dad");
        obj.addWord("mad");
        System.out.println(obj.search("pad"));
        System.out.println(obj.search("bad"));
        System.out.println(obj.search(".ad"));
        System.out.println(obj.search("b.."));
    }
}

/**
 * Your WordDictionary object will be instantiated and called as such:
 * WordDictionary obj = new WordDictionary();
 * obj.addWord(word);
 * boolean param_2 = obj.search(word);
 */
