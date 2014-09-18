package textbuddy;

import static org.junit.Assert.*;

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

}