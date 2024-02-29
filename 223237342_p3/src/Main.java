import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main {	
	
	/**
	 * A method for recursively search for a String in an array of Strings
	 * @param word - The word of type String that we are looking for.
	 * @param words - The String array containing all the words
	 * @param lo - The lower bound for the search
	 * @param hi - The upper bound for the search
	 * @return the index of the found word in the words array
	 * (10 marks) ********************************************
	 */
	public static int recursiveBinarySearch(String word, String[] words, int lo, int hi){		
		//TODO: Complete
		
		if(lo > hi) {
			//then we've gone through the whole array without finding a match
			return -1;
		}
		
		
		//get the midPoint
		int midPoint = (lo + hi)/2;
		
		//get the word at the midPoint
		String midPointWord = words[midPoint];
		
		
		//compare the word at the midpoint with the word searched for
		int compareWords = word.compareTo(midPointWord);
		
		
		if(compareWords < 0) {
			//then the searched-for word is lexicogrpahically less 
			hi = midPoint - 1;
			recursiveBinarySearch(word, words, lo, hi);
		}
		else if(compareWords > 0) {
			//then the searched-for word is lexicogrpahically greater
			lo = midPoint + 1;
			recursiveBinarySearch(word, words, lo, hi);
		}
		
				
		//Base case: If both words are equal, return the index
		return midPoint;
	}
	
	
	
	/**
	 * @param word - The String which its characters will be shuffled around
	 * @return The shuffled String
	 * (5 marks) ********************************************
	 */
	public static String mixCharacterOrder(String word){	
		//TODO: Complete	
		String shuffledWord = "";
		List<Character> characterArr = new ArrayList<>();
		
		//?????
		//fill up the characterArr with the characters in the word
		for(char aChar : word.toCharArray()) {
			characterArr.add(aChar);
		}
		
		//shuffle the character array
		 Collections.shuffle(characterArr);
		 
		 //add each character of the shuffled array to the shuffeledString
		 for(Character aChar : characterArr) {
			 shuffledWord += aChar;
		 }
		
		 return shuffledWord;
	}
	
	
	/**
	 * The conundrum solver that uses the array dictionary, mixCharacterOrder 
	 * and recursive binary search.
	 * @param conundrum - The current conundrum that needs to be solved.
	 * @param words - The String array containing all the dictionary words
	 * @return A valid word that can be found in the conundrum
	 * (5 marks) ********************************************
	 */
	public static String solveConundrum1(String conundrum, String[] words){				
		//TODO: Complete
		int lastIndex = conundrum.length() - 1; 
		
		while(recursiveBinarySearch(conundrum, words, 0, lastIndex) == -1) {
			conundrum = mixCharacterOrder(conundrum);
		}
		return conundrum;		
		
	}
	
	
	/**
	 * The conundrum solver that uses the DList dictionary, mixCharacterOrder 
	 * and the DList search.
	 * @param conundrum - The current conundrum that needs to be solved.
	 * @param words - The String array containing all the dictionary words
	 * @return A valid word that can be found in the conundrum
	 */
	public static String solveConundrum2(String conundrum, DList<String> words){		
		while(words.search(conundrum) == null){
			conundrum = mixCharacterOrder(conundrum);
		}
		return conundrum;				
	}
	
	
	
	
	/**
	 * @param path - The location of the dictionary
	 * @return The String array of words  containing all the dictionary words
	 * (5 marks) ********************************************
	 */
	public static String[] loadPotentialDicitonary1(String path){
		//TODO: Complete
		ArrayList<String> tempWordList = new ArrayList<String>();
		File dictionaryFile = new File(path);
		
		try {
			Scanner s = new Scanner(dictionaryFile);
			
			while(s.hasNextLine()){
				tempWordList.add(s.nextLine());					
			}			
			s.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}	
		
		int size = tempWordList.size();
		
		String[] returnedString = new String[size];
		
		for(int i = 0; i < size; i++) {
			returnedString[i] = tempWordList.get(i);
		}
		
		System.out.println(size + " entries loaded");
		return returnedString;
		
	}	
	
	
	
	/**
	 * @param path - The location of the dictionary
	 * @return The String array of words  containing all the dictionary words
	 */
	public static DList<String> loadPotentialDictionary2(String path){		
		DList<String> wordsList = new DList<String>();
		File f = new File(path);		
		 
		try {
			Scanner s = new Scanner(f);
			
			while(s.hasNextLine()){
				wordsList.addLast(s.nextLine());					
			}			
			s.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}	
		System.out.println(wordsList.size()+ " entries loaded");
		return wordsList;
	}
	
	
	/*
	 * The main method
	 * //(10 marks for execution) ********************************************
	 */
	public static void main(String[] args) {
		final String PATH = "test.dict"; 
		String conundrum = "ionlitabo";
		
		System.out.println("Dictionary Load 1 begin");
		long startTime = System.currentTimeMillis();
		String[] words = loadPotentialDicitonary1(PATH);
		long endTime = System.currentTimeMillis();
		double totalTime = (endTime - startTime)/1000.0;
		System.out.println("Dictionary Load 1 completed in "+ totalTime+" seconds");
		
		System.out.println("Dictionary Load 2 begin");
		startTime = System.currentTimeMillis();
		DList<String> wordList = loadPotentialDictionary2(PATH);
		endTime = System.currentTimeMillis();
		totalTime = (endTime - startTime)/1000.0;
		System.out.println("Dictionary Load 2 completed in "+ totalTime+" seconds");
		
		System.out.println("Algorithm 1 Test begin");
		startTime = System.currentTimeMillis();
		//System.out.println("The found word is: "+solveConundrum1(conundrum, words));
		endTime = System.currentTimeMillis();
		totalTime = (endTime - startTime)/1000.0;
		System.out.println("Algorithm 1 Test completed in "+ totalTime+" seconds");
		
		System.out.println("Algorithm 2 Test begin");
		startTime = System.currentTimeMillis();
		System.out.println("The found word is: "+solveConundrum2(conundrum, wordList));
		endTime = System.currentTimeMillis();
		totalTime = (endTime - startTime)/1000.0;
		System.out.println("Algorithm 2 Test completed in "+ totalTime+" seconds");
	}

}
