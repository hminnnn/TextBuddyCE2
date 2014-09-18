package textbuddy;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Scanner;


/**
 * TextBuddy CE2
 * File is saved after every command. 
 * @author huimin
 *
 */
public class TextBuddy {
	
	private ArrayList<String> textFile = new ArrayList<String>();
	private String restOfText;
	private String fileName;
	private static Scanner sc = new Scanner(System.in);
	private ArrayList<String> searchedText = new ArrayList<String>();
	
	private static final String MESSAGE_INVALID_COMMAND = "Invalid command";
	private static final String MESSAGE_NO_FILE_NAME = "File name not specified.";
	private static final String WELCOME_MESSAGE = "Welcome to TextBuddy. %1$s is ready for use";
	private static final String MESSAGE_FILE_ADDED = "added to %1$s: \"%2$s\"";
	private static final String MESSAGE_FILE_CLEARED = "all content deleted from %1$s";
	private static final String MESSAGE_FILE_EMPTY_DISPLAY = "%1$s is empty";
	private static final String MESSAGE_FILE_SORTED = "%1$s is sorted";
	
	public TextBuddy(String[] Args) {
		fileName = Args[0];
	}
	
	enum COMMAND_TYPE {
		ADD, DELETE, CLEAR, DISPLAY, EXIT, INVALID, SORT, SEARCH
	};
	
	public static void main(String[] arg) {
		String userInput;
		TextBuddy tb = new TextBuddy(arg);
		tb.checkFileValid();
		tb.welcomeMsg();
		String toDisplay;
		
		while (true) {
			System.out.print("command: ");
			userInput = sc.nextLine();
			toDisplay = tb.executeCommand(userInput);
			System.out.println(toDisplay);
			System.out.println(" ");
			tb.saveFile();
		}
	}

	/**
	 * This operation checks if the file name is valid.  
	 */
	public void checkFileValid() {
		if (fileName.isEmpty()) {
			System.out.println(String.format(MESSAGE_NO_FILE_NAME));
		}
	}
	
	public void welcomeMsg() {
		System.out.println(String.format(WELCOME_MESSAGE, fileName));
	}
	
	/**
	 * This operation determines which of the supported command types the user wants to perform
	 */
	public String executeCommand(String userInput) {
		
		String userCommand = splitText(userInput);
		COMMAND_TYPE commandType = determineCommandType(userCommand);

		switch (commandType) {
			case ADD :
				return add();
	
			case DELETE :
				return delete();
	
			case CLEAR :
				return clear();

			case DISPLAY :			
				return display();

			case EXIT :
				sc.close();
				System.exit(0);
				break;

			case INVALID :
				return String.format(MESSAGE_INVALID_COMMAND);
				
			case SORT :
				return sort();

			case SEARCH :
				return search();

			default :
				throw new Error(MESSAGE_INVALID_COMMAND);
		}
		return MESSAGE_INVALID_COMMAND;

	}
	
	/**
	 * This operation splits the string into the user's command and the rest of the text.
	 * @param userInput
	 * @return userCommand = the first word.
	 */
	public String splitText(String userInput) {
		String[] textArr = userInput.split(" ", 2);
		if (textArr.length > 1 ) {
			restOfText = textArr[1];
		} else {
			restOfText = "";
		}
		return textArr[0];
	}
	
	/**
	 * This operation determines which of the supported command types the user wants to perform
	 * @param userInput is the first word of the user command
	 */
	public COMMAND_TYPE determineCommandType(String userInput) {
	
		if (userInput == null) {
			throw new Error(MESSAGE_INVALID_COMMAND);
		}
		
		if (userInput.equalsIgnoreCase("add")) {
			return COMMAND_TYPE.ADD;
		} else if (userInput.equalsIgnoreCase("delete")) {
			return COMMAND_TYPE.DELETE;
		} else if (userInput.equalsIgnoreCase("display")) {
		 	return COMMAND_TYPE.DISPLAY;
		} else if (userInput.equalsIgnoreCase("clear")) {
			return COMMAND_TYPE.CLEAR;
		} else if (userInput.equalsIgnoreCase("exit")) {
			return COMMAND_TYPE.EXIT;
		} else if (userInput.equalsIgnoreCase("sort")) {
			return COMMAND_TYPE.SORT;
		} else if (userInput.equalsIgnoreCase("search")) {
			return COMMAND_TYPE.SEARCH;
		} else {
			return COMMAND_TYPE.INVALID;
		}
	}

	/**
	 * This operation displays all the text in the textFile
	 * @return all the text in the textFile array
	 */
	public String display() {
		if (!textFile.isEmpty()) {
			int size = 1;
			for (int i = 0; i < textFile.size()-1; i++) {
				System.out.println(size +". " + textFile.get(i));
				size++;
			}
			// prints the last line in textFile as toDisplay will be printed last. 
			return size + ". " + textFile.get(textFile.size()-1);
		} else {
			return String.format(MESSAGE_FILE_EMPTY_DISPLAY, fileName);
		}
	}

	/**
	 * This operation clears the textFile
	 * @return status of operation
	 */
	public String clear() {
		if (!restOfText.isEmpty()) {
			return String.format(MESSAGE_INVALID_COMMAND);
		}
		textFile.clear();
		return String.format(MESSAGE_FILE_CLEARED, fileName);
	}

	/**
	 * This operation deletes the specific line
	 * @return status of operation
	 */
	public String delete() {
		if (restOfText.isEmpty()) {
			 return MESSAGE_INVALID_COMMAND;
		}
		
		int indexToDelete = Integer.parseInt(restOfText.substring(0));
		
		// Integer to delete is invalid
		if ((indexToDelete > textFile.size()) || (indexToDelete < 1)) {
			 return MESSAGE_INVALID_COMMAND;
		} else {
			String deletedLine = textFile.get(indexToDelete-1);
			textFile.remove(indexToDelete-1);
			return "deleted from " + fileName + " \"" + deletedLine + "\"";
		}
	}

	/**
	 * This operation adds the input line to the textFile
	 * @return status of operation
	 */
	public String add() {
		if (restOfText.isEmpty()) {
			return String.format(MESSAGE_INVALID_COMMAND);
		}
		textFile.add(restOfText);	
		isAdded(restOfText);
		return String.format(MESSAGE_FILE_ADDED, fileName, restOfText);
	}
	
	/**
	 * This operation is for TextBuddyTest to test if the text is added.
	 */
	public boolean isAdded(String text) {
		if (textFile.get(textFile.size()-1).equals(text)) {
			return true;
		}
		return false;
	}
	
	/**
	 * This operation sorts the lines of text in textFile
	 * @return status of operation
	 */
	public String sort() {
		Collections.sort(textFile);
		return String.format(MESSAGE_FILE_SORTED, fileName);
	}
	
	/**
	 * This operation is for TextBuddyTest to test if the textFile arraylist is sorted.
	 */
	public ArrayList<String> isSorted() {
		return textFile;
	}

	/**
	 * This operation searches the textFile for the word to see if it is present.
	 * Ignores case and punctuation. 
	 * Only one word - the first word is searched. Rest of the words are ignored.
	 * @return lines that contain the word searched by the user
	 */
	public String search() {
		searchedText.clear();
		if (textFile.isEmpty() ) {
			return String.format(MESSAGE_FILE_EMPTY_DISPLAY, fileName);
		} else {
			boolean isPresent = false;
			String[] word;
			String textToCheck;
			String wordToSearch = restOfText.replaceAll("[\\W]", " ");
			String[] searchWord = wordToSearch.split(" ");
			
			for (int i = 0; i < textFile.size(); i++) {
				textToCheck = textFile.get(i).replaceAll("[\\W]",  " ");
				word = textToCheck.split(" ");
				for (int j = 0; j < word.length; j++ ) {
					if (word[j].equalsIgnoreCase(searchWord[0])) {
						isPresent = true;
						break;
					} else {
						isPresent = false;
					}
				}
				if (isPresent) {
					searched();
					int num = i+1;
					System.out.println(num +". " + textFile.get(i));
					searchedText.add(textFile.get(i));
				}
			}
		}
		return restOfText + " -  is present in the lines above:" ;
	}

	/**
	 * This operation is for TextBuddyTest to test if the word has been searched.
	 */
	public ArrayList<String> searched() {
		return searchedText;
		
	}
	
	/**
	 * This operation saves the file. If file already exists, it will be overwritten. 
	 */
	public void saveFile() {
		try {
			BufferedWriter fileWrite = new BufferedWriter(new FileWriter(fileName));
			Iterator<String> textFileItr = textFile.iterator();
			while (textFileItr.hasNext()) {
				fileWrite.write(textFileItr.next().toString());
				fileWrite.newLine();
			}	
			fileWrite.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}