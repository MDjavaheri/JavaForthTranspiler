import java.util.HashMap;

public class JavaForthTranspilerListener extends JavaForthBaseListener {
    private StringBuilder output = new StringBuilder();
    private HashMap<String, Boolean> initializedVars = new HashMap<>();
    
   	@Override public void enterExpression(JavaForthParser.ExpressionContext ctx) {
        System.out.println(ctx.toString());
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
	
	@Override public void enterPrint(JavaForthParser.PrintContext ctx) {
		output.append("print ");
	}
    
	@Override public void exitLang(JavaForthParser.LangContext ctx) {
		output.append("\n");
	}

	@Override
	public void exitCompilationUnit(JavaForthParser.CompilationUnitContext ctx) {
		System.out.println(output.toString());
	}

}