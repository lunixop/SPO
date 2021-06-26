package Compiler;

import java.util.LinkedList;

public class RPN {
	
	private LinkedList<String> lexems;
	private LinkedList<Token> tokens;
	private LinkedList<String> notation = new LinkedList<>();
	private LinkedList<String> operators = new LinkedList<>();
	private LinkedList<String> operators_tokens = new LinkedList<>();
	private LinkedList<String> notation_tokens = new LinkedList<>();
	private Priority check = new Priority();
	private int i = 0;
	public void add_list (LinkedList lexems, LinkedList tokens) {
		
		this.lexems = lexems;
		this.tokens = tokens;
		
	}
	
	public void create_rpn() {
		
		while (lexems.size() > i) {
			
			if ((tokens.get(i).toString() == "TOK_VAR") || (tokens.get(i).toString() == "TOK_NUMBER")) {
				
				notation.addLast(lexems.get(i));
				notation_tokens.addLast(tokens.get(i).toString());
				i++;
				
			}
			
			else {
				
				function(tokens.get(i).toString());
				
			}
			
		}
		
		
		
	}
	
	private void function (String token) {
		
		String lexem = lexems.get(i);
		
		if ((operators.size() == 0 || token == "TOK_L_BR") && (token != "TOK_SEMI") && (token != "TOK_L_S_BR") && (token != "TOK_R_S_BR")) {
			operators.addLast(lexem);
			operators_tokens.addLast(token);
			i++;
			
		}
		
		else {
			
			if (token == "TOK_R_BR") {
				
				while (operators_tokens.getLast() != "TOK_L_BR") {
					
					notation_tokens.addLast(operators_tokens.getLast());
					notation.addLast(operators.getLast());
					operators.removeLast();
					operators_tokens.removeLast();
					
					
				}
				
				
				operators.removeLast();
				operators_tokens.removeLast();
				i++;
			
				
			}
			
else {
				
				if ( (token == "TOK_SEMI") || (token == "TOK_L_S_BR") || (token == "TOK_R_S_BR") ) {
				
					while (operators.size() != 0) {
						
						notation_tokens.addLast(operators_tokens.getLast());
						notation.addLast(operators.getLast());
						operators.removeLast();
						operators_tokens.removeLast();
						
					}
					notation_tokens.addLast(token);
					notation.addLast(lexem);
					i++;
				}
				
				else {
					if (operators.size() != 0 ) {
						
						while (check.return_priority(operators_tokens.getLast().toString(), operators.getLast()) >= check.return_priority(token, lexem)) {

							
							notation_tokens.addLast(operators_tokens.getLast());
							notation.addLast(operators.getLast());
							operators.removeLast();
							operators_tokens.removeLast();
							
							if(operators.size() == 0) {
								
								break;
								
							}
							
						}
					}
					
					operators.addLast(lexem);
					operators_tokens.addLast(token);
					i++;
					
					
				}
				
			}
			
		}
		
	}
	
	public LinkedList return_rpn() {
		
		return notation;
		
	}
	
	public LinkedList reutrn_rpn_tok() {
		
		return notation_tokens;
		
	}
	
}
