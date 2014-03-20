package galeshapley;

import java.util.*;

public class GaleShapley {
	private LinkedHashMap<String, Stack<String>> malePref;
	private HashMap<String, HashMap<String, Integer>> femalePref;
	private HashMap<String, String> malePerspective;
	private HashMap<String, String> femalePerspective; // TODO
	private String[] names = { "Ross", "Monica", "Chandler", "Phoebe", "Joey",
			"Rachel" };
	private int[][] preferences = { 
			{ 6, 4, 2 }, { 3, 5, 1 }, { 2, 6, 4 },
			{ 5, 1, 3 }, { 6, 4, 2 }, { 1, 5, 3 } };

	public void d (Object str){
		boolean debug = true;
		if (debug) {
			System.out.println("" + str);
		}
	}
	
	public GaleShapley() {
		setup(names, preferences);
		match();
		print();
	}

	public void setup(String[] names, int[][] preferences) {
		malePref = new LinkedHashMap<String, Stack<String>>();
		femalePref = new HashMap<String, HashMap<String, Integer>>();
		malePerspective = new HashMap<String, String>();
		femalePerspective = new HashMap<String, String>();
		
		Stack<String> mPrefStack = null;		
		for (int n = 0; n < names.length; n++) {
			if (n % 2 == 0) {
				// man
				d(names[n]);
				mPrefStack = new Stack<String>(); 
				for (int p = preferences[n].length-1; p >= 0; p--) { // kollonner
					String lowestRankedFemale = names[preferences[n][p]-1]; // Tjej sist i ranking nu.
					mPrefStack.push(lowestRankedFemale); //TODO s��tt ihop dessa tv�� rader.
				}
				malePref.put(names[n], mPrefStack);
				malePerspective.put(names[n], null);
			} else { 
				// woman 
				HashMap<String,Integer> fPrefMap = new HashMap<String, Integer>();
				for (int p = 0; p < preferences[0].length; p++) {
					String highestRankedMan = names[preferences[n][p]-1];
					Integer rankOfHighestRankedMan = p;	//Low number =high rank, sl��ng ihop // TODO
					fPrefMap.put(highestRankedMan, rankOfHighestRankedMan);					
				}
				femalePref.put(names[n], fPrefMap);
				femalePerspective.put(names[n],null);
			}
		}
	}

	public void match() {
		Iterator<String> itr = malePerspective.keySet().iterator();
		String nextMale;
		while (itr.hasNext())  {
			nextMale = itr.next();
			match(nextMale);
		} 
	}
	
	public void match(String nextMale) {
		d("nextmale: " + nextMale);
		String highestRankedFemale = malePref.get(nextMale).pop();
		d("hgih female: " + highestRankedFemale);
		if (femalePerspective.get(highestRankedFemale) == null) {
			d(highestRankedFemale + " är ledig");
			pair(highestRankedFemale,nextMale);
		} else {
			String currentMan = femalePerspective.get(highestRankedFemale);
			d(currentMan + " är tillfällig");
			if (femalePref.get(highestRankedFemale).get(nextMale) > femalePref.get(highestRankedFemale).get(currentMan)) {
				d(nextMale + " är bättre än " + currentMan);
				pair(highestRankedFemale,nextMale);
				match(currentMan);
			} else {
				d(nextMale + " är sämre än " + currentMan);
				match(nextMale);
			}
		}
	}

	private void pair(String highestRankedFemale, String nextMale) {
		femalePerspective.put(highestRankedFemale,nextMale);
		malePerspective.put(nextMale, highestRankedFemale);
	}

	public void print() {
		for (String key: malePerspective.keySet()) {
			System.out.printf("%s == %s\n", key, malePerspective.get(key));
		}
	}
}
