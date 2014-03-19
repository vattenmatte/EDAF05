package galeshapley;

import java.util.*;

public class GaleShapley {
	private HashMap<String, Stack<String>> malePref;
	private HashMap<String, HashMap<String, Integer>> femalePref;
	private HashMap<String, String> malePerspective;
	private HashMap<String, String> femalePerspective; // TODO
	private String[] names = { "Ross", "Monica", "Chandler", "Phoebe", "Joey",
			"Rachel" };
	private int[][] preferences = { 
			{ 6, 4, 2 }, { 3, 5, 1 }, { 2, 6, 4 },
			{ 5, 1, 3 }, { 6, 4, 2 }, { 1, 5, 3 } };

	public GaleShapley() {
		setup(names, preferences);
		match();
		print();
	}

	public void setup(String[] names, int[][] preferences) {
		malePref = new HashMap<String, Stack<String>>();
		femalePref = new HashMap<String, HashMap<String, Integer>>();
		malePerspective = new HashMap<String, String>();
		femalePerspective = new HashMap<String, String>();
		
		Stack<String> mPrefStack = null;		
		for (int n = 0; n <= names.length; n++) {
			if (n % 2 == 0) {
				mPrefStack = new Stack<String>(); // man
				for (int p = preferences[0].length; p >= 0; p--) { // kollonner
					String lowestRankedFemale = names[preferences[n][p]]; // Tjej sist i ranking nu.
					mPrefStack.push(lowestRankedFemale); //TODO sätt ihop dessa två rader.
				}
				malePref.put(names[n], mPrefStack);
				malePerspective.put(names[n], null);
			} else { // woman 
				HashMap<String,Integer> fPrefMap = new HashMap<String, Integer>();
				for (int p = 0; p <= preferences[0].length; p ++) {
					String highestRankedMan = names[preferences[n][p]];
					Integer rankOfHighestRankedMan = p;	//Low number =high rank, släng ihop // TODO
					fPrefMap.put(highestRankedMan, rankOfHighestRankedMan);					
				}
				femalePref.put(names[n], fPrefMap);
				femalePerspective.put(names[n],null);
			}
		}
	}

	public void match() {
		Iterator<String> itr = malePerspective.keySet().iterator();
		String nextMale = itr.next();
		String nextFemale = malePerspective.get(nextMale);
		String highestRankedF = null;
		while(nextFemale == null) {
			highestRankedF = malePref.get(nextMale).pop();
			if (femalePerspective.get(highestRankedF) == null) {
				femalePerspective.put(highestRankedF,nextMale);
				malePerspective.put(nextMale, highestRankedF);
				malePref.get(nextMale).pop();
				//ta bort killar ibland
			} else if (femalePref.get(highestRankedF).get(nextMale) < femalePref.get(highestRankedF).get(femalePerspective.get(highestRankedF))) {
				femalePerspective.put(highestRankedF,nextMale);
				malePerspective.put(nextMale, highestRankedF);
				malePref.get(nextMale).pop();
				//samma här
			} else {
				malePref.get(nextMale).pop();
				//w rejects m
			}
			
		}
	}

	public void print() {
	}// TODO
}
