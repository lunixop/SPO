package Compiler;

import java.util.LinkedList;

/*

Необходимая грамматика языка:

lang ->  expr+
expr -> assign_expr | if_expr | while_expr | print_expr 
assign_expr ->  TOK_VAR TOK_ASSIGN value_expr() TOK_SEMI
if_expr -> if_head body (TOK_KEY_ELSE body)?
while_expr -> while_head body
value_expr -> (value_brakets | value) (math_op value_expr)?
if_head -> TOK_KEY_IF logic_expr 
body -> TOK_L_S_BR expr+ TOK_R_S_BR
while_head -> TOK_KEY_WHILE logic_expr
value -> TOK_VAR | TOK_NUMBER
logic_expr -> TOK_L_BR value (logic_op value)+ TOK_R_BR
value_brakets ->TOK_L_BR value_expr TOK_R_BR
math_op -> TOK_MATH_OP
logic_op -> TOK_LOGIC_OP
print_expr -> TOK_PRINT TOK_L_BR value TOK_R_BR




*/



public class Parser_Vbl_4e_Prikalblvaetec {
	
	LinkedList <Token> tokens;
	
	public void add_list(LinkedList list) {
		
		tokens = list;
		
	}
	
	private void Check_error() {
		
		System.out.println("Синтаксическая ошибка: " + tokens.get(i) + " после " + tokens.get(i-1));
		System.exit(0);
		
	}
	
	int i = 0;
	
	public void lang() {
		
		while(i < tokens.size()) {
			
			expr();
			
		}
		
	}
	
	private void expr() {
		if(tokens.get(i).toString().equals("TOK_VAR")) {assign_expr();}
		else if (tokens.get(i).toString().equals("TOK_KEY_IF")) { if_expr();}
		else if (tokens.get(i).toString().equals("TOK_KEY_WHILE")) while_expr();
		else if (tokens.get(i).toString().equals("TOK_PRINT")) print_expr();
		else { Check_error();}
		
	}
	
	private void assign_expr() {
		//System.out.println(i);
		i++;
		if(tokens.get(i).toString().equals("TOK_ASSIGN")) {i ++; value_expr();}
		else { Check_error(); }
		if(tokens.get(i).toString().equals("TOK_SEMI")) {i ++; }
		else { Check_error(); }
		
	}
	
	private void if_expr() {
		
		i++;
		if_head();
		body();
		if(tokens.size() > i) {
		if(tokens.get(i).toString().equals("TOK_KEY_ELSE")) { i++;  body();}
		else return;
		}
		else {return;}
	}
	
	private void while_expr() {
		
		i++;
		while_head();
		body();
		
	}
	
	private void print_expr() {
		i++;
		if(tokens.get(i).toString().equals("TOK_L_BR")) {i++; value();}
		else Check_error();
		if(tokens.get(i).toString().equals("TOK_R_BR")) i++;
		else Check_error();
		if(tokens.get(i).toString().equals("TOK_SEMI")) i++;
		else Check_error();
	}
	
	private void value_expr() {
		
		if(tokens.get(i).toString().equals("TOK_L_BR")) {value_brakets();}
		else value();
		if(tokens.get(i).toString().equals("TOK_MATH_OP")) {math_op(); value_expr();}
		else return;
		
		
	}
	
	private void if_head() {
		
		logic_expr();
		
	}
	
	private void body() {
		
		if(tokens.get(i).toString().equals("TOK_L_S_BR")) {i++; 
		
		while(!(tokens.get(i).toString().equals("TOK_R_S_BR"))) {
			
			expr();
			
			if(tokens.size() == i) {
				
				System.out.println("Конец обработки файла, нарушение синтаксиса");
				System.exit(0);
				
			}
			
		}
		i++;

		}
		
		else Check_error();
		
	}
	
	private void while_head() {
		
		logic_expr();
		
	}
	
	private void value() {
		
		if(tokens.get(i).toString().equals("TOK_VAR")) i++;
		else if(tokens.get(i).toString().equals("TOK_NUMBER")) i++;
		else Check_error();
		
	}
	
	private void value_brakets() {
		
		if(tokens.get(i).toString().equals("TOK_L_BR")) i++;
		else Check_error();
		//System.out.println(tokens.get(i));
		value_expr();
		if(tokens.get(i).toString().equals("TOK_R_BR")) i++;
		else Check_error();
		
	}
	
	private void math_op() {
		
		if(tokens.get(i).toString().equals("TOK_MATH_OP")) {i++;}
		else {Check_error();}
		
	}
	
	private void logic_expr() {
		if(tokens.get(i).toString().equals("TOK_L_BR")) i++;
		else Check_error();
		value();
		while (!tokens.get(i).toString().equals("TOK_R_BR")) {
			
			logic_op();
			value();
			
		}
		i++;
		//System.out.println("1 " + tokens.get(i));
	}
	
	private void logic_op() {
		
		if(tokens.get(i).toString().equals("TOK_LOGICAL_OP")) i++;
		else Check_error();
		
	}
}
