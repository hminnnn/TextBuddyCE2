package textbuddy;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class TextBuddyTest {

	@Test
	public void testIsAdded() {
		
		String[] arg = {"test.txt"};
		TextBuddy tb = new TextBuddy(arg);
		
		// add normal text
		tb.executeCommand("add this is a brown fox");
		assertTrue(tb.isAdded("this is a brown fox"));
		assertEquals("added to test.txt: \"this is a brown fox\"",
				tb.executeCommand("add this is a brown fox"));
		
		// add command wrong.
		tb.executeCommand("addd this cannot be added");
		assertFalse(tb.isAdded("addd this cannot be added"));
		assertEquals("Invalid command",
				tb.executeCommand("addd this cannot be added"));
		
		//add nothing
		tb.executeCommand("add");
		assertFalse("nothing was added", tb.isAdded("add"));
		assertEquals("Invalid command",
				tb.executeCommand("add"));
	}
	
	@Test
	public void testDelete() {
		
		String[] arg = {"test.txt"};
		TextBuddy tb = new TextBuddy(arg);
		
		tb.executeCommand("add 123");
		tb.executeCommand("add apples");
		tb.executeCommand("add cats");
		tb.executeCommand("delete 10");
		
		// Delete invalid integer.
		assertEquals("Invalid command", tb.executeCommand("delete 10"));
		
		// Delete valid 
		assertEquals("deleted from test.txt \"apples\"", tb.executeCommand("delete 2"));
		
		// Delete nothing
		assertEquals("Invalid command", tb.executeCommand("delete"));
		
	}
	
	@Test 
	public void testClear() {
		
		String[] arg = {"test.txt"};
		TextBuddy tb = new TextBuddy(arg);
		
		tb.executeCommand("add 123");
		tb.executeCommand("add apples");
		tb.executeCommand("add cats");
		tb.executeCommand("delete 10");
		
		// Clear command invalid
		assertEquals("Invalid command", tb.executeCommand("clear 2"));
		
		assertEquals("all content deleted from test.txt", tb.executeCommand("clear"));
		
	}
	
	// Sort1. Input not sorted
	@Test 
	public void testIsSorted1() {
		
		String[] arg = {"test.txt"};
		TextBuddy tb = new TextBuddy(arg);
		
		tb.executeCommand("clear");
		tb.executeCommand("add zzz");
		tb.executeCommand("add bbb");
		tb.executeCommand("add sss");
		tb.sort();
		
		assertEquals("test.txt is sorted", tb.sort());
		
	}
	
	// Sort2. Input sorted
	@Test 
	public void testIsSorted2() {
		
		String[] arg = {"test.txt"};
		TextBuddy tb = new TextBuddy(arg);
		ArrayList<String> expectedArray = new ArrayList<String>();
		ArrayList<String> actualOutput = tb.isSorted();
		
		tb.executeCommand("add 123");
		tb.executeCommand("add apples");
		tb.executeCommand("add cats");
		expectedArray.add("123");
		expectedArray.add("apples");
		expectedArray.add("cats");
		
		for(int i = 0; i < expectedArray.size(); i++) {
			assertEquals(expectedArray.get(i), actualOutput.get(i));
		}
		
	} 
	
	// Search1. Word present in one line
	@Test
	public void testSearch1() {
		
		String[] arg = {"test.txt"};
		TextBuddy tb = new TextBuddy(arg);
		
		ArrayList<String> expectedArray = new ArrayList<String>();
		ArrayList<String> actualOutput = tb.searched();

		tb.executeCommand("add hello there");
		tb.executeCommand("add huimin is here!");
		tb.executeCommand("add abcdefg");
		tb.executeCommand("add qwerty");
		tb.executeCommand("search huimin");
		
		expectedArray.add("huimin is here!");
		
		for(int i = 0; i < expectedArray.size(); i++) {
			assertEquals(expectedArray.get(i), actualOutput.get(i));
		}
	}

}