/**
 *
 */
package pt.unl.fct.di.novalincs.nohr.parsing;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.channels.IllegalSelectorException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * A basic {@link <a href="https://en.wikipedia.org/wiki/Lexical_analysis#Scanner">Scanner</a>} implemented on top of {@link Scanner}. The recognized
 * tokens are those that are specified by {@link TokenType}.
 *
 * @author Nuno Costa
 */
class NoHRScanner {

	private static final Pattern DOUBLE_SLASH = Pattern.compile("\\\\\\\\");

	private static final Pattern SLASH = Pattern.compile("(?<!\\\\)\\\\");

	/** The position of the scanner in the current line. */
	private int position;

	/** The current line. */
	private int line;

	/** The underlying {@link Scanner} */
	private final Scanner scanner;

	/** The length of the scanned {@link String}. */
	private final int length;

	private TokenType currentTokenType;

	/**
	 * Constructs a {@link NoHRScanner} for a given {@link File file}.
	 *
	 * @param file
	 *            the file that will be scanned.
	 * @throws FileNotFoundException
	 */
	NoHRScanner(File file) throws FileNotFoundException {
		scanner = new Scanner(file);
		length = 0;
		position = 0;
		line = 1;
	}

	/**
	 * Constructs a {@link NoHRScanner} for a given {@link String string}.
	 *
	 * @param str
	 *            the string that will be scanned.
	 */
	NoHRScanner(String str) {
		scanner = new Scanner(str);
		length = str.length();
		position = 0;
	}

	/**
	 * Returns true if this scanner has another token in its input. This method may block while waiting for input to scan.
	 *
	 * @return true if and only if this scanner has another token.
	 */
	boolean hasNext() {
		return scanner.hasNext();
	}

	/**
	 * Returns true if there is another line in the input of this scanner. This method may block while waiting for input.
	 *
	 * @return true if and only if this scanner has another line of input.
	 */
	boolean hasNextLine() {
		return scanner.hasNextLine();
	}

	/**
	 * The the length of the scanned string.
	 *
	 * @return the length of the scanned string.
	 */
	int length() {
		return length;
	}

	/**
	 * Returns the current line.
	 *
	 * @return the current line.
	 */
	int line() {
		return line;
	}

	/**
	 * Try to consume a token of a given {@link TokenType type}. If no token of the given type is found, maintains the current position. The value of
	 * the consumed token is obtained calling {@link #value()}.
	 *
	 * @param type
	 *            the type of the token to consume.
	 * @return true iff a token of the type {@code type} was found.
	 */
	boolean next(TokenType type) {
		try {
			scanner.skip(type.pattern());
			position = scanner.match().end();
			currentTokenType = type;
		} catch (final NoSuchElementException e) {
			return false;
		} catch (final IllegalSelectorException e) {
			return false;
		}
		return true;
	}

	/**
	 * Advances this scanner past the current line.
	 */
	void nextLine() {
		scanner.nextLine();
		line++;
	}

	/**
	 * Returns the current position of this {@link NoHRScanner}.
	 *
	 * @return he current position of this {@link NoHRScanner}.
	 */
	int position() {
		return position;
	}

	/**
	 * Returns the value of the last consumed token.
	 *
	 * @return the value of the last consumed token.
	 */
	String value() {
		String val = scanner.match().group();
		if (currentTokenType == TokenType.SYMBOL)
			if (val.startsWith("'") && val.endsWith("'"))
				val = val.substring(1, val.length() - 1);
			else {
				val = SLASH.matcher(val).replaceAll("");
				val = DOUBLE_SLASH.matcher(val).replaceAll("\\\\");
			}
		return val;
	}

}
