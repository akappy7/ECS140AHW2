import java.util.ArrayList;

public class Symbol {
	public String ID;
	public int depth;
	public int decLine;
	public ArrayList assLines; //topKek 
	public ArrayList useLines;

	public Symbol(String ID, int depth, int decLine) {
		this.ID      = ID;
		this.depth   = depth;
		this.decLine = decLine;
		assLines     = new ArrayList();
		useLines     = new ArrayList();
	}
}