import java.io.File;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
@SuppressWarnings("deprecation")
public class JavaForthRunner {
	private static String defaultFile = "input.txt";
	private static String targetString;
	private static Path targetPath;
	public static void main(String[] args) throws Exception {
		targetString = (args.length == 0) ? defaultFile : args[0];

		if (args.length > 1 && args[1].trim().toLowerCase().equals("true")) {
			System.setOut(new PrintStream(new File("output.txt")));				
		}
		
		targetPath = Paths.get(targetString);
		Files.lines(targetPath).forEach(line -> {
			transpile(line);
		});
	}
	
	/**
	 * Transpiles a single line of code
	 * @param line a single line of code to transpile
	 * @throws Exception
	 */
	public static void main(String line) throws Exception {
		transpile(line);
	}

	/**
	 * Transpiles a single line of code and writes to file
	 * @param line a single line of code to transpile
	 * @param toFile true if should write to file, false if to console
	 * @throws Exception
	 */
	public static void main(String line, boolean toFile) throws Exception {
		if (toFile) {
			System.setOut(new PrintStream(new File("output.txt")));				
		}
		transpile(line);
	}

	/**
	 * Runs the line through ANTLR
	 * @param line
	 */
	private static void transpile(String line) {
		System.out.println("Java: " + line);
		System.out.print("Forth: ");		
		
		ANTLRInputStream input = new ANTLRInputStream(line);
		JavaForthLexer lexer = new JavaForthLexer(input);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		JavaForthParser parser = new JavaForthParser(tokens);
		ParseTree tree = parser.compilationUnit();
		
		JavaForthTranspilerListener extractor = new JavaForthTranspilerListener();
		ParseTreeWalker.DEFAULT.walk(extractor, tree);
	}
}