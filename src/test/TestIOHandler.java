package test;

import java.util.Scanner;

// helper class: takes care of indentation while printing and reads int values from the console
public class TestIOHandler {
	// number of tabs to be inserted before current line
	private static int F = 0;

	private static Scanner in = new Scanner(System.in);

	public static void increaseIndentation() {
		F++;
	}

	public static void decreaseIndentation() {
		F--;
	}

	// return F tabulators
	public static String getIndentation() {
		String tabs = "";

		for (int ii = 0; ii < F; ++ii)
			tabs += "\t";

		return tabs;
	};

	// prints str indented
	public static void print(String str) {
		System.out.print(getIndentation() + str);
	}

	// prints str indented in a new line
	public static void println(String str) {
		System.out.println(getIndentation() + str);
	}

	// reads a number from the standard input
	public static int readNumber() {
		int returnValue = 0;
		boolean invalidInput = true;
		while (invalidInput) {
			try {
				returnValue = Integer.parseInt(in.next());
				invalidInput = false;
			} catch (Exception ex) {
				print("Invalid input! Please try again! ");
			}
		}

		return returnValue;
	}

	public static int readNumber(int a, int b) {
		int returnValue = 0;
		boolean invalidInput = true;
		while (invalidInput) {
			try {
				returnValue = Integer.parseInt(in.next());
				invalidInput = false;

				if ((returnValue < a) || (returnValue > b))
					invalidInput = true;
			} catch (Exception ex) {
				invalidInput = true;
			} finally {
				if (invalidInput)
					print("Invalid input! Please try again! ");
			}
		}

		return returnValue;
	}

	public static void releaseInput() {
		in.close();
	}
};
