//begBlock() : begin a new block
//	put a new hash table onto the stack

//endBlock()
//	pop a hash table from the stack

//useSym(String id, int lineNumber)
//	Called when a symbol is simply used in an expression

//assignSym(String id, int lineNumber)
//	called when a value is assigned to a symbol

//decSym(String id, int lineNumber)
//	declare the variable

//Should be a Stack of Hash Tables of Symbols
//Symbol should contain an ID, declaration depth, declaration line, list of assignment line numbers
//	, list of use line numbers


//Notes
//Nesting depth needs to be maintained
// Block 1 is depth 0
//line number of declaration, assignment, and use needs to be maintained

//Not sure if variable hiding is allowed
import java.util.Stack;
import java.util.ArrayList;
import java.util.Iterator;


public class SymbolTable {
	private Stack<ArrayList<Symbol>> stack;
	private int depth;
	private ArrayList<Symbol> current;

	public SymbolTable() {
		depth = -1; //every block increments, first block needs to be at depth 0
		stack = new Stack<ArrayList<Symbol>>();
	}

	public TableResponse assignSym(String ID, int line) {
		if(findSymbols(ID) != null)
			return(new TableResponse(true, ""));
		else
			return new TableResponse(false, "not declared");
	}

	public void begBlock() {
		depth++;
		stack.push(new ArrayList<Symbol>());
		current = stack.peek();
	}
//if theres a symbol with same name at current depth fail, otherwise true
	public TableResponse decSym(String ID, int line) {
		for(Symbol s : current) {
			if(s.depth == depth)
				return(new TableResponse(false, "redeclared"));
		}
		Symbol s = new Symbol(ID, depth, line);
		current.add(s);
		return(new TableResponse(true, ""));
	}

	public void endBlock() {
		depth--;
		stack.pop();
		if(depth >= 0)
			current = stack.peek();
	}

	public TableResponse useSym(String ID, int line) {
		return null;
	}

	private Symbol[] findSymbols(String ID) { //returns an array of all symbols with given ID
		ArrayList<Symbol> retArList = new ArrayList<Symbol>();

		for(ArrayList<Symbol> list : stack) {
			Iterator<Symbol> symbol = list.iterator();
			while(symbol.hasNext()) {
				Symbol s = symbol.next();
				if(s.ID.contentEquals(ID)) {
					retArList.add(s);
				}
			}
		}
		if(retArList.size() == 0)
			return null;
		else {
			Symbol retAr[] = new Symbol[retArList.size()];
			return(retArList.toArray(retAr));
		}
	}
}