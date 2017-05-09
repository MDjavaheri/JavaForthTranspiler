import java.util.HashMap;

public class JavaForthTranspilerListener extends JavaForthBaseListener {
    private StringBuilder output = new StringBuilder();
    private HashMap<String, Boolean> initializedVars = new HashMap<>();
    
   	@Override public void enterExpression(JavaForthParser.ExpressionContext ctx) {

   	}

	@Override public void exitExpression(JavaForthParser.ExpressionContext ctx) {
		String operator;
		if (ctx.getChildCount() > 1) {
			operator = ctx.getChild(1).getText();
			switch(operator) {
			case "==":
				operator = "=";
				break;
			
			case "&&":
				operator = "and";
				break;
				
			case "||":
				operator = "or";
				break;
			
			case "+=":
				operator = "+!";
				break;
				
			case "-=":
				operator = "-!";
				break;
				
			case "*=":
				operator = "*!";
				break;
			case "/=":
				operator = "/!";
				break;
				
			case "++":
				operator = "+!";
				break;
			}
			output.append(operator + " ");
		}
	}

    
    @Override public void enterVariableDeclarator(JavaForthParser.VariableDeclaratorContext ctx) { 
    	//add to hashmap with false, when initialized set to true, when accessed from elsewhere, check hashamp to see if true or not
    	String out = ctx.variableDeclaratorId().getText();
    	if (ctx.variableInitializer() == null){
    		initializedVars.put(out, false);
    		output.append(out);
    	}
    	else if (((JavaForthParser.VariableDeclaratorsContext) ctx.getParent()).primitiveType() == null) {
//    		throw UninitializedVariableException;
    	}
    	else {
    		initializedVars.put(out, true);
    		output.append(out + " " + ctx.variableInitializer().getText() + " " + out + " !");
    	}
    }

	@Override
	public void enterPrimitiveType(JavaForthParser.PrimitiveTypeContext ctx) {
		output.append("variable ");
	}
	
	@Override public void enterLiteral(JavaForthParser.LiteralContext ctx) {
		if (ctx.BooleanLiteral() != null) {
			int bool = (ctx.BooleanLiteral().getText().equals("true")) ? -1 : 0;
			output.append(bool + " ");			
		}
		else {
			output.append(ctx.IntegerLiteral().getText() + " ");
		}
	}
	
	@Override public void exitPrimary(JavaForthParser.PrimaryContext ctx) {
		if (ctx.Identifier() != null) {
			output.append(ctx.Identifier().getText() + " ");
		}
	}

	@Override public void enterPrint(JavaForthParser.PrintContext ctx) {
		output.append("print ");
	}
    
	@Override public void exitLang(JavaForthParser.LangContext ctx) {
		output.append("\n");
	}

	@Override
	public void exitCompilationUnit(JavaForthParser.CompilationUnitContext ctx) {
		System.out.print(output.toString());
	}

}