package Compiler;

import java.util.LinkedList;

public class Input {
	
	private static LinkedList<String> lexema;
	private static LinkedList<Token> token;
	private static LinkedList<String> notation;
	private static LinkedList<String> notation_tokens;
	//private int i = 0;
	
	final static String dir = System.getProperty("user.dir"); //Записываем в dir директорию запуска проекта
	
	
	public static void main(String args[]) {
		
		LexerFlexer lexer = new LexerFlexer(dir + "\\Test.txt");
		
		lexema = lexer.return_lexema();
		token = lexer.return_token();
		
		lexema_out();
		
		Parser_Vbl_4e_Prikalblvaetec parser = new Parser_Vbl_4e_Prikalblvaetec();
		parser.add_list(token);
		parser.lang();
		
		RPN rpn = new RPN();
		rpn.add_list(lexema, token);
		rpn.create_rpn();
		notation = rpn.return_rpn();
		//System.out.println(notation);
		poliz_out();
		notation_tokens = rpn.reutrn_rpn_tok();
		Stack stack = new Stack();
		stack.add_notation(notation);
		stack.add_notation_tok(notation_tokens);
		stack.stack_handler();
		
		
	
		
	}//Конец main
	
	private static void lexema_out() {
		
		int i = 0;
		while (i < lexema.size()) {
			
			System.out.println(lexema.get(i)+": " + token.get(i));
			i++;
			
			
		}
		
	}
	
	private static void poliz_out() {
		int i = 0;
		System.out.print("\n Poliz:");
		while (i < notation.size()) {
			
			System.out.print(notation.get(i) + " ");
			i++;
			
			
		}
		
		System.out.print("\n");
	}
	
	
	
	
	
}//Конец класса 
