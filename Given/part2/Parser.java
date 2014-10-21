/* *** This file is given as part of the programming assignment. *** */

public class Parser {


    // tok is global to all these parsing methods;
    // scan just calls the scanner's scan method and saves the result in tok.
    private Token tok; // the current token
    //private boolean flag = true;
    private void scan() {
        tok = scanner.scan();
    }

    private Scan scanner;
    Parser(Scan scanner) {
        this.scanner = scanner;
        scan();
        program();
	//if(tok.kind == TK.NUM)
	//{
	  //  parse_error("HI arman");
	//}
	//System.out.println("last one: "+ tok.string);
	if( tok.kind != TK.EOF )
            parse_error("junk after logical end of program");
    }

    private void program() {
        block();
    }

    private void block() {
  	//check to see if declaration
  	boolean flag = true;
	if(is(TK.VAR))
	{
		declarations();
		//System.out.println("Works for decleration");
	}
	//must be statemnet list
	while(true){
		//System.out.println("LOOOOOPIN: "+ tok.string);
		
		flag = statement_list(flag);
		if(is(TK.EOF))
		{
			break;
		}
		if(flag == false)
		{
			break;	
		}
	}
    }

    private boolean statement_list(boolean flag)
    {
	if(is(TK.ID))
	{
		//System.out.println("assignmen: " +tok.string);
		assignment();
		return true;
	}
	if(is(TK.PRINT))
	{
		mustbe(TK.PRINT);
		expression();
		return true;
	}
	if(is(TK.IF))
	{
//		System.out.println("HI");
		
		mustbe(TK.IF);
		guarded_commands();
		//System.out.println("MUST BE FI: " + tok.string);
		mustbe(TK.FI);
		//System.out.println("Whoa there");
		return true;
	}
	if(is(TK.DO))
	{
	
		scan();
		guarded_commands();

		mustbe(TK.OD);
		return true;
	}
	if(is(TK.FA))
	{
		scan();
		mustbe(TK.ID);
		mustbe(TK.ASSIGN);
		expression();
		mustbe(TK.TO);
		expression();
		if(is(TK.ST))
		{
			scan();
			expression();
		}
		mustbe(TK.ARROW);
                block();
		mustbe(TK.AF);
		return true;
	}
	
//	System.out.println("ONLY at the end");
	return false;

    }
    private void guarded_commands(){
  		guarded_command();	
		//System.out.println("NEXT: " + tok.string);
		while(true)
		{
			if(is(TK.BOX)){
				scan();
				guarded_command();
			}	
			else
			{
				break;
			}
		}
		if(is(TK.ELSE))
		{
			scan();
			mustbe(TK.ARROW);
               		block();
	
		}
	//	System.out.println("NOTHING");	
	}

    private void guarded_command(){
		if(is(TK.ELSE))
		{
			parse_error("factor");
		}
		expression();
		
	//	System.out.println("Next: " + tok.string);
		mustbe(TK.ARROW);
		block();		

	}
    //assigning part of statement
    private void assignment(){
	//System.out.println("ID" + tok.kind);
	mustbe(TK.ID);
	//System.out.println("Next; " + tok.string);
	mustbe(TK.ASSIGN);
//	System.out.println("Assigned to : "+ tok.string);
	expression();
	

	}
    private void expression(){
	
	//simple
	simple();
	//System.out.println("Start of Expression: " + tok.string);
	//check to see if more than one simple	
	if(is(TK.EQ) || is(TK.NE) || is(TK.LT) || is(TK.GT) || is(TK.LE) || is(TK.GE)){

	//	System.out.println("CHar :" + tok.string);
		scan();
	//	System.out.println("CHar2 :" + tok.string);
		simple();	
	}
	//System.out.println("expression evaluated");
	 //System.out.println("Next one; "+ tok.string);
	}//expression()

    private void simple(){
	boolean flag = true;
	term();	
	
	while(flag)
	{
		if(is(TK.PLUS) || is(TK.MINUS))
		{
			//System.out.println("PLUS or MINUS: " + tok.string);
			scan();
			term();
		}	
		else{
			return;
		}
		//scan();	
	}//while
	}//simple()
    private void term(){
	boolean flag = true;
	factor();

	while(flag)
	{
		//checks for more terms
		if(is(TK.TIMES) || is(TK.DIVIDE))
		{ 
			//System.out.println("Times/divide: " + tok.string);
 			scan();
			factor();	
		}
		else 
		{
			return;
		}
	//	scan();
	}//while()
	}//term()
    private void factor(){
        if(is(TK.LPAREN))
	{
		//System.out.println("left");
		scan();
		expression();
		//scan();
		//System.out.println("right: " + tok.string);

		mustbe(TK.RPAREN);	
	 	//System.out.println("right: " + tok.string);	
	}
	//id or number
	else if(is( TK.ID) || is(TK.NUM))
	{
		//System.out.println("factor works for id or number: " + tok.string);
		scan();

	}
	else
	{
		//parse_error("must be ID or NUM");
	}
	}//facotr()
    private void declarations() {
        mustbe(TK.VAR);
        while( is(TK.ID) ) {
            scan();
        }
	//scan();
        mustbe(TK.RAV);
    }

    // you'll need to add a bunch of methods here

    // is current token what we want?
    private boolean is(TK tk) {
        return tk == tok.kind;
    }

    // ensure current token is tk and skip over it.
    private void mustbe(TK tk) {
        if( ! is(tk) ) {
            System.err.println( "mustbe: want " + tk + ", got " +
                                    tok);
            parse_error( "missing token (mustbe)" );
        }
        scan();
    }

    private void parse_error(String msg) {
        System.err.println( "can't parse: line "
                            + tok.lineNumber + " " + msg );
        System.exit(1);
    }
}
