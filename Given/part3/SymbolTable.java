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



//Dearest Arman,

//Within the parser just have a member variable SymbolTable.
//Whenever you declare a variable use decSym(variablesID, lineNumber)
//assign use assignSym(variablesID, lineNumber)
//use a variable use useSym(variablesID, lineNumber)

//These functions return a TableResponse object with members 'success' and 
// 'response'. success lets you know if the operation worked and response 
// contains a really basic error message if the operation failed.

//when you begin a new block call begBlock() and when you end a
// block use endBlock()
//	These functions don't return anything since they shouldn't fail

//I tested everything and it seemed to work alright but idk if it'll hold with an actual program
//Don't worry about texting me if you don't understand any of the code,
// its kinda messy so i don't blame you

import java.util.Stack;
import java.util.ArrayList;
import java.util.Iterator;


public class SymbolTable {
	private Stack<ArrayList<Symbol>> stack;
	private int depth;
	private ArrayList<Symbol> current;
	public ArrayList<ArrayList<Symbol>> history;

	public SymbolTable() {
		depth = -1; //every block increments, first block needs to be at depth 0
		stack = new Stack<ArrayList<Symbol>>();
		history = new ArrayList<ArrayList<Symbol>>();
	}

	public TableResponse assignSym(String ID, int line) {
		ArrayList<Symbol> symbols = findSymbols(ID);
		if(symbols.size() != 0) {
			symbols.get(symbols.size() - 1).assLines.add(line);
			return(new TableResponse(true, ""));
		}
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
			if(s.depth == depth && s.ID.contentEquals(ID))
				return(new TableResponse(false, "redeclared"));
		}
		Symbol s = new Symbol(ID, depth, line);
		current.add(s);
		return(new TableResponse(true, ""));
	}

	public void endBlock() {
		depth--;
		history.add(0, stack.pop()); //add discarded to history, will preserve order
		if(depth >= 0)
			current = stack.peek();
		else
			current = null;//this may catch errors down the road
	}

	//operating on assumption can use uninitialized variables and same scoping as C
	//IE hiding
	public TableResponse useSym(String ID, int line) {
		ArrayList<Symbol> symbols = findSymbols(ID);
		if(symbols.size() != 0) {
			symbols.get(symbols.size() - 1).useLines.add(line);
			return new TableResponse(true, "");
		} else
			return new TableResponse(false,"not declared");
	}

	public void printSym(String ID) {
		for(Symbol s : findSymbols(ID) ) {
			System.out.println( String.valueOf(s.depth) );
		}
	}

	public void printHistory() {
		for(ArrayList<Symbol> list : history) {
			for(Symbol s : list) {
				System.out.println("ID: " + s.ID);
				System.out.println("	declared on: " + String.valueOf(s.decLine));
				System.out.println("	at depth: " + String.valueOf(s.depth));
				System.out.print("	used at: ");
				for(Integer i : s.useLines)
					System.out.print(String.valueOf(i) + ' ');
				System.out.println(' ');
				System.out.print("	assigned at: ");
				for(Integer i : s.assLines)
					System.out.print(String.valueOf(i) + ' ');
				System.out.println(' ');
			}
		}
	}

	//the last Symbols in the return array should be the most recently pushed (deepest)
	private ArrayList<Symbol> findSymbols(String ID) { //returns an array of all symbols with given ID
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
		return(retArList);
	}
}