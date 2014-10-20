public class Symbol {
	public String ID;
	public int depth;
	public int decLine;
	public ArrayList<int> assLines; //topKek 
	public ArrayList<int> useLines;

	public Symbol(String ID, int depth, int decLine) {
		this.ID      = ID;
		this.depth   = depth;
		this.decLine = decLine;
		assLines     = new ArrayList<int>();
		useLines     = new ArrayList<int>();
	}
}