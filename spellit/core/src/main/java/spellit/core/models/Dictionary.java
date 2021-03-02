package spellit.core.models;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import spellit.core.utils.TrieTree;

public class Dictionary {

	private TrieTree root;

	Dictionary() {
		root = new TrieTree();
		try (InputStream in = this.getClass().getResourceAsStream("dictionary.csv")) {

			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String line = br.readLine();
			while ((line = br.readLine()) != null) {
				String word = line.split(";")[0].toLowerCase();
				root.insert(word);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean lookup(String word) {
		return root.search(word);
	}
}
