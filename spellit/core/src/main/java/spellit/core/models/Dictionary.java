package spellit.core.models;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import spellit.core.utils.TrieTree;

/**
 * The Class Dictionary. Loads words from a file and stores them in a Trie structure for faster
 * lookup.
 *
 * @see TrieTree
 */
public class Dictionary {

  private TrieTree root;

  /**
   * Instantiates a new dictionary.
   */
  Dictionary() {
    root = new TrieTree();
    try (BufferedReader br = new BufferedReader(
        new InputStreamReader(this.getClass().getResourceAsStream("dictionary.csv"), "UTF-8"))) {
      String line = br.readLine();
      while ((line = br.readLine()) != null) {
        String word = line.split(";")[0].toLowerCase();
        root.insert(word);
      }
      br.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Lookup a word in the dictionary.
   *
   * @param word the word to lookup
   * @return true, if successful
   */
  public boolean lookup(String word) {
    return root.search(word);
  }
}
