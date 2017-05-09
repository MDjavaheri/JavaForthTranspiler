import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * 
 */

/**
 * @author morde
 *
 */
public class AcceptanceTests {
	JavaForthRunner transpiler;
	BufferedReader br;
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		transpiler = new JavaForthRunner();
		try {
			 br = Files.newBufferedReader(Paths.get("output.txt"));
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		br.close();
	}

	/**
	 * Helper method to DRY up transpiler testing
	 * @param input
	 * @param expected
	 */
	public void assertTranspiles(String input, String expectedOutput) {
		try {
			JavaForthRunner.main(input, true);
			String readInput = br.readLine().split("Java:")[1].trim();
			String output = br.readLine().split("Forth:")[1].trim();
			assertEquals(input + expectedOutput, readInput + output);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * @throws Exception 
	 * 
	 */
	@Test
	public void testIntLiteral() throws Exception {
		assertTranspiles("int x = 10;", "variable x 10 x !");
	}

	/**
	 * 
	 */
	@Test
	public void testIntVar() {
		assertTranspiles("int x;", "variable x");		
	}
	
	/**
	 * 
	 */
	@Test
	public void testExpression1() {
		assertTranspiles("(5 + 10) - (4 * ((2 + 6) - (10 / 5)));", "5 10 + 4 2 6 + 10 5 / - * -");		
	}
	
	/**
	 * 
	 */
	@Test
	public void testExpression2() {
		assertTranspiles("100 - 20 / 5;", "100 20 5 / -");				
	}
	
	/**
	 * 
	 */
	@Test
	public void testExpression3() {
		assertTranspiles("100 - (4 * 20) / 5 - (100 % 25);", "100 4 20 * 5 / - 100 % 25 -");		
	}
	/**
	 * 
	 */
	@Test
	public void testSyntacticSugar() {
		assertTranspiles("int x = 10;\nx++;", ""); //TODO how to do this?	
	}

	/**
	 * 
	 */
	@Test
	public void testBooleanTrue() {
		assertTranspiles("boolean hey = true;", "variable hey 1 hey !");		
	}
	/**
	 * 
	 */
	@Test
	public void testBooleanFalse() {
		assertTranspiles("boolean bye = false;", "variable bye 0 bye !");		
	}
	
	/**
	 * Declares a variable without initializing, throws error
	 */
	@Test
	public void testVarDeclarWithoutInit() {
		assertTranspiles("a = 10;;", "");						
	}
	
	/**
	 * 
	 */
	@Test
	public void testPrint() {
		assertTranspiles("print int x = 20;", "print variable x 20 x !");								
	}
	
	
	/**
	 * 
	 */
	@Test
	public void testTriple() {
		assertTranspiles("int x = (20 > 3) ? 2 : 4;", "");										
	}
}