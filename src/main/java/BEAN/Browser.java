package BEAN;

import java.io.*;

public class Browser {

	private boolean browsing;
	private WebVisit currentVisit;

	/**
	  * Main method for the application. Create a new Browser object and set it to run.
	  */
	public static void main(String[] args) {
		Browser b = new Browser();
		b.run();
	}

	/**
	  * Constructor for the Browser. Welcome the user and initialize the instance variables.
	  */
	public Browser() {
		browsing = true;
		currentVisit = null;
		System.out.println("Welcome to FCS Browser v0.01");
	}

	/**
	  * The primary method for the web browser: while the user is browsing the web, continually prompt, read, and execute commands.
	  */
	public void run() {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		while (browsing) {
			try {
				promptUser();
				String cmd = br.readLine();
				interpretAndExecute(cmd);
			} catch (IOException e) {
				System.out.println("Oops! There was an error reading your command.");
			}
		}
	}

	/**
	  * Prompt the user with a message to the command line.
	  */
	private void promptUser() {
		System.out.println("\nBrowser ready.\n(Commands: visit *web url*; history; back; quit; forward ;)");
	}

	/**
	  * Given a string, determine which command (if any) is being issued and execute it
	  *  hint: splitting at " " will be useful
	  *  hint: notice that comparing Strings requires use of the .equals() method. Let's talk about it.
	  * @param cmd The complete string entered by the user
	  */
	private void interpretAndExecute(String cmd) {
		String[] commands = cmd.split(" ");
		if (cmd.equals("back")) {
			goBack();
		} else if (cmd.equals("history")) {
			viewHistory();
		} else if (commands[0].equals("visit")) {
			visitPage(commands[1]);
		} else if (cmd.equals("quit")) {
			quit();
		} else if (cmd.equals("forward")){
			goForward();

		} else {
			System.out.println("Command not recognized");
		}
	}

	/**
	  * Create a new WebVisit object according to the URL passed in, move it to the top of the stack.
	  * @param url The URL of the new webpage the user would like to visit
	  */
	private void visitPage(String url) {
		System.out.println("Now going to visit "+url);
		WebVisit wv = new WebVisit(url, currentVisit, null);
		if (currentVisit != null) {
			currentVisit.setNextNode(wv);
		}
		currentVisit = wv;
		System.out.println(currentVisit);
	}
	/**
	  * If there is a previous visit in the browser's history, pop the current one off the top of the stack
	  *  and print out the browser's new page (if it exists). If there is no page to go back to, alert the user and do nothing.
	  */
	private void goBack() {
		if (currentVisit != null) {
			System.out.println("Going back...");
			currentVisit = currentVisit.getPreviousNode();
			if (currentVisit != null) {
				System.out.println(currentVisit);
			}
		} else {
			System.out.println("No web visits in browser history");
		}
	}

	private void goForward() {
		if (currentVisit != null) {
			System.out.print("Going forward...");
			currentVisit = currentVisit.getNextNode();
			if (currentVisit != null) {
				System.out.println(currentVisit);
			} else {
				System.out.println("No next page to show.");
			}
		}
	}

	/**
	  * View a list of all WebVisits in the browser's history; list the total number of visits.
	  */
	private void viewHistory() {
		System.out.println("Showing browser history...");
		WebVisit tmp = currentVisit;
		int counter = 0;
		while (tmp != null) {
			System.out.println("\t~"+tmp);
			tmp = tmp.getPreviousNode();
			counter++;
		}
		System.out.println("\t\t"+counter+" total");
	}
	/**
	  * Say goodbye; quit the program.
	  */
	private void quit() {
		System.out.println("Quitting now...");
		browsing = false;
	}
}