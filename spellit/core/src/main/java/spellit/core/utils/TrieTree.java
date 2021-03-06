package spellit.core.utils;

/**
 * The Class TrieTree is used for storing data in a Trie Structure. A Trie is a data structure used
 * for locating specific keys within a set of keys. These keys are in this case individual
 * characters of the alphabet which are linked to other characters in the alphabet in order to form
 * words. In such way, the lookup and insert time for any word is O(m) - where m is the length of
 * the word.
 */
public class TrieTree {

  private TrieNode root;
  static final int ALPHABET_SIZE = 31;

  /**
   * The Class TrieNode. Represents a key/character in the Trie.
   */
  static class TrieNode {
    TrieNode[] children = new TrieNode[ALPHABET_SIZE];

    boolean isEndOfWord;

    /**
     * Instantiates a new trie node.
     */
    TrieNode() {
      isEndOfWord = false;
      for (int i = 0; i < ALPHABET_SIZE; i++) {
        children[i] = null;
      }

    }
  }

  /**
   * Instantiates a new trie tree.
   */
  public TrieTree() {
    root = new TrieNode();
  }

  /**
   * Inserts a word into the Trie. If any character isn't present, a new node for that character
   * will be inserted.
   *
   * @param key the the word to insert
   */
  public void insert(String key) {
    int level;
    int length = key.length();
    int index;

    TrieNode treeCrawl = root;
    for (level = 0; level < length; level++) {

      index = translateIndex(key.charAt(level));

      if (treeCrawl.children[index] == null) {
        treeCrawl.children[index] = new TrieNode();
      }

      treeCrawl = treeCrawl.children[index];
    }

    // mark last node as leaf
    treeCrawl.isEndOfWord = true;
  }

  /**
   * Searches for a word in the Trie.
   *
   * @param key the word to search for
   * @return true, if successful
   */
  public boolean search(String key) {
    int level;
    int length = key.length();
    int index;
    TrieNode treeCrawl = root;

    for (level = 0; level < length; level++) {
      index = translateIndex(key.toLowerCase().charAt(level));

      if (treeCrawl.children[index] == null) {
        return false;
      }

      treeCrawl = treeCrawl.children[index];
    }

    return (treeCrawl != null && treeCrawl.isEndOfWord);
  }

  /**
   * Translates the index of characters in order to use non-US characters such as 'æøå', and '-'.
   *
   * @param c the character to translate
   * @return the index of the character
   */
  private int translateIndex(char c) {
    switch (c) {
      case ('æ'):
        return 27;
      case ('ø'):
        return 28;
      case ('å'):
        return 29;
      case ('-'):
        return 30;
      default:
        return c - 'a';
    }
  }
}
