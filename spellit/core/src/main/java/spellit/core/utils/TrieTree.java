package spellit.core.utils;

public class TrieTree {

	private TrieNode root;
	// Alphabet size (# of symbols)
	static final int ALPHABET_SIZE = 31;

	// trie node
	static class TrieNode {
		TrieNode[] children = new TrieNode[ALPHABET_SIZE];

		// isEndOfWord is true if the node represents
		// end of a word
		boolean isEndOfWord;

		TrieNode() {
			isEndOfWord = false;
			for (int i = 0; i < ALPHABET_SIZE; i++)
				children[i] = null;
		}
	};

	public TrieTree() {
		root = new TrieNode();
	}

	// If not present, inserts key into trie
	// If the key is prefix of trie node,
	// just marks leaf node
	public void insert(String key) {
		int level;
		int length = key.length();
		int index;

		TrieNode pCrawl = root;
		for (level = 0; level < length; level++) {

			index = translateIndex(key.charAt(level));

			if (pCrawl.children[index] == null)
				pCrawl.children[index] = new TrieNode();

			pCrawl = pCrawl.children[index];
		}

		// mark last node as leaf
		pCrawl.isEndOfWord = true;
	}

	// Returns true if key presents in trie, else false
	public boolean search(String key) {
		int level;
		int length = key.length();
		int index;
		TrieNode pCrawl = root;

		for (level = 0; level < length; level++) {
			index = translateIndex(key.toLowerCase().charAt(level));

			if (pCrawl.children[index] == null)
				return false;

			pCrawl = pCrawl.children[index];
		}

		return (pCrawl != null && pCrawl.isEndOfWord);
	}

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
