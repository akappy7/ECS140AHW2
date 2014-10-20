//begBlock() : begin a new block
//	put a new hash table onto the stack

//endBlock()
//	pop a hash table from the stack

//useSym(String id, int lineNumber)
//	Called when a symbol is simply used in an expression

//assignSym(String id, int lineNumber)
//	called when a value is assigned to a symbol

//dec(String id, int lineNumber)
//	declare the variable

//Should be a Stack of Hash Tables of Symbols
//Symbol should contain an ID, declaration depth, declaration line, list of assignment line numbers
//	, list of use line numbers


//Notes
//Nesting depth needs to be maintained
// Block 1 is depth 0
//line number of declaration, assignment, and use needs to be maintained

//Not sure if variable hiding is allowed

public class SymbolTable {
	
}