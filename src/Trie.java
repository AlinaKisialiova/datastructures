import java.util.HashMap;
import java.util.Map;

/*
A trie is a tree-like data structure whose nodes store the letters of an alphabet.

Insert and search costs O(key_length).
The memory requirements is O(ALPHABET_SIZE * key_length * N) where N is number of keys in Trie.

Strengths
Sometimes Space-Efficient.
If you're storing lots of words that start with similar patterns, tries may reduce the overall storage cost by storing shared prefixes once.

Efficient Prefix Queries.
Tries can quickly answer queries about words with shared prefixes.

Weaknesses
Usually Space-Inefficient.
Tries rarely save space when compared to storing strings in a set.

Not Standard.
Most languages don't come with a built-in trie implementation. You'll need to implement one yourself.

Example of trie:
Stored words: their, there, answer, any, bye.
                       root
                    /   \    \
                    t   a     b
                    |   |     |
                    h   n     y
                    |   |  \  |
                    e   s  y  e
                 /  |   |
                 i  r   w
                 |  |   |
                 r  e   e
                        |
                        r
 */
class Trie {

    public static void main(String[] args) {
        Trie trie = new Trie();
        trie.insert("apple");
        boolean found = trie.search("apple");
        assert found : "Solution is wrong";
        found = trie.search("app");
        assert !found : "Solution is wrong";

        found = trie.startsWith("app");
        assert found : "Solution is wrong";
        trie.insert("app");
        found = trie.search("app");
        assert found : "Solution is wrong";

        trie.deleteLogically("apple");
        found = trie.search("apple");
        assert !found : "Solution is wrong";
        trie.deletePhysically("app");
        found = trie.search("app");
        assert !found : "Solution is wrong";
    }


    private TrieNode root;

    public Trie() {
        root = new TrieNode();
    }

    public void insert(String word) {
        if (word == null) {
            return;
        }
        TrieNode current = root;
        for (Character l : word.toCharArray()) {
            current = current.getChildren()
                    .computeIfAbsent(l, ch -> new TrieNode());
        }
        current.setEndOfWord(true);
    }

    public void deletePhysically(String word) {
        deleteWordFromTrie(root, word, 0);
    }

    //O(n), where n represents the length of the key.
    // 1) Go to last word letter
    // 2) Remove end mark from last letter and check if there any children to indicate if it is allowed to delete it
    // 3) last letter will be deleted, if there are no children and it is without end mark
    // 4) Untwist recursion: if (child == condition from step 3) => delete parent
    private Boolean deleteWordFromTrie(TrieNode parent, String word, int letterIndexInWord) {
        //if last letter
        if (letterIndexInWord == word.length()) {
            // no mark that last letter is end of word => trie doesn't contain this word as word
            if (!parent.isEndOfWord) {
                return false;
            }
            //remove end word mark to allow deleting this node, if the node doesn't have children
            parent.setEndOfWord(false);
            return parent.getChildren().isEmpty();
        }

        char letter = word.charAt(letterIndexInWord);
        TrieNode child = parent.getChildren().get(letter);
        //a word doesn't exist in trie
        if (child == null) {
            return false;
        }

        //if child of current child doesn't have children and not the end of word
        boolean isAllowedToDeleteLetterNode = deleteWordFromTrie(child, word, letterIndexInWord + 1) && !child.getEndOfWord();
        if (isAllowedToDeleteLetterNode) {
            parent.getChildren().remove(letter);
            //if there are no children, parent can be deleted from its parent
            return parent.getChildren().isEmpty();
        }
        return false;
    }

    public void deleteLogically(String word) {
        deleteEndOfWordMark(root, word, 0);
    }

    private void deleteEndOfWordMark(TrieNode node, String word, int index) {
        if (word.length() == index) {
            node.setEndOfWord(false);
            return;
        }
        char letter = word.charAt(index);
        TrieNode nextNode = node.getChildren().get(letter);
        if (nextNode != null) {
            deleteEndOfWordMark(nextNode, word, ++index);
        }
    }

    /**
     * Returns if the word is in the trie.
     */
    public boolean search(String word) {
        if (word == null) {
            return false;
        }
        TrieNode lastMatchedCharacter = searchPrefix(word);
        return lastMatchedCharacter != null && lastMatchedCharacter.isEndOfWord;
    }

    /**
     * Returns if there is any word in the trie that starts with the given prefix.
     */
    public boolean startsWith(String prefix) {
        if (prefix == null) {
            return false;
        }
        return searchPrefix(prefix) != null;
    }

    TrieNode searchPrefix(String prefix) {
        TrieNode current = root;
        for (Character l : prefix.toCharArray()) {
            current = current.getChildren().get(l);
            if (current == null) {
                return null;
            }
        }
        return current;
    }

    class TrieNode {

        //for optimisation we can use just 'TrieNode[] children', where array index is ASCII code of letter number in latin alphabet stored in array(starts from 0).
        //Then to access to elements a char has to be converted to index for it in array: "ch -'a'";
        //For example: ch=m=109(ASCII code), 'a'=97; 109-97=12. M is 13th letter in general alphabet, but in array notation is 12.
        //http://www.asciitable.com/
        private Map<Character, TrieNode> children = new HashMap<>();

        private Boolean isEndOfWord = false;

        public Map<Character, TrieNode> getChildren() {
            return children;
        }

        public void setEndOfWord(boolean endOfWord) {
            isEndOfWord = endOfWord;
        }

        public Boolean getEndOfWord() {
            return isEndOfWord;
        }
    }
}
