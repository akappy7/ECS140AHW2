//tester
import java.io.Console;

public class Tester {
	public static void main(String args[]) {
		SymbolTable t = new SymbolTable();
		Console console = System.console();
		int i = 0;
		while(true) {
			String input = console.readLine();
			String name;
			if(input.contentEquals("begBlock"))
				t.begBlock();
			else if(input.contentEquals("endBlock"))
				t.endBlock();
			else if(input.contentEquals("assignSym")) {
				name = console.readLine("name: ");
				System.out.println( t.assignSym(name, i).success );
			} else if(input.contentEquals("decSym")) {
				name = console.readLine("name: ");
				System.out.println( t.decSym(name, i).success );
			} else if(input.contentEquals("useSym")) {
				name = console.readLine("name: ");
				System.out.println( t.useSym(name, i).success );
			}else if(input.contentEquals("printSym")) {
				name = console.readLine("name: ");
				t.printSym(name);
			} else if(input.contentEquals("printH")) {
				t.printHistory();
			} else {
				System.out.println("nope" + input);
			}
			i++;
		}
	}
}