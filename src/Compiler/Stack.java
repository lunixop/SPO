package Compiler;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class Stack {
	
	private LinkedList<String> notation = new LinkedList();
	
	private LinkedList<String> notation_tokens = new LinkedList();
	
	private LinkedList<String> stack = new LinkedList();
	
	private LinkedList<String> stack_tokens = new LinkedList();
	
	int i = 0;
	
	double result_op;
	Map<String,String> TableOfVar = new HashMap<String,String>();
	//Map<String,String> TableOfVar_tokens = new HashMap<String,String>();
	
	public void add_notation (LinkedList notation) {
		
		this.notation = notation;
		
	}
	
	public void add_notation_tok (LinkedList notation_tokens) {
		
		this.notation_tokens = notation_tokens;
		
	}
	
	public void stack_handler() {
		
		while(notation.size() > i) {
			
			stack_machine();
			
		}
		
	}
	
	private void stack_machine () {
		
		switch (notation_tokens.get(i)) {
		
		case("TOK_ASSIGN"):
			assign_op();
		break;
		
		case("TOK_PRINT"):
			print_op();
		break;
		
		case("TOK_SEMI"):
			i++;
		break;
		
		case("TOK_KEY_IF"):
			if_op();
		break;
		
		case ("TOK_KEY_ELSE"):
			i++;
		break;
		
		case ("TOK_KEY_WHILE"):
			while_op();
		break;
		
		case("TOK_L_S_BR"):
			i++;
		break;
		
		case("TOK_R_S_BR"):
			i++;
		break;
		
		case("TOK_MATH_OP"):
			math_op();
		break;
		
		case("TOK_LOGICAL_OP"):
			logic_op();
		break;
		
		default: 
			stack.addLast(notation.get(i));
			stack_tokens.addLast(notation_tokens.get(i));
			i++;
			break;
		
		
		}
		
	}
	
	private void assign_op() {
		String value = stack.getLast();
		stack.removeLast();
		String name = stack.getLast();
		stack.removeLast();
		stack_tokens.removeLast();
		stack_tokens.removeLast();
		TableOfVar.put(name, value);
		i++;
		
	}
	
	private void print_op () {
		
		switch(stack_tokens.getLast()) {
		
		case("TOK_VAR"):
			System.out.println(TableOfVar.get(stack.getLast()));
		break;
		
		default:
			System.out.println(stack.getLast());
			break;
		}
		
		i++;
		stack.removeLast();
		stack_tokens.removeLast();
		
	}
	
	private void if_op () {
		
		String bool_op = stack.getLast();
		i ++;
		stack.removeLast();
		stack_tokens.removeLast();
		
		if (bool_op == "true") {
			
			while (!notation.get(i).equals("}")) {
				
				stack_machine();
				
			}
			i ++; 
			if (notation.size() == i) {System.exit(0);}
			if(notation.get(i).equals("else")) {
				
				while (!notation.get(i).equals("}")) {
					
					shift_i();
					
				}
				i ++;
			}
			
		}
		
		else {
			
			while (!notation.get(i).equals("}")) {
				
				shift_i();
				
			}
			i ++; 
			
			
			if(notation.get(i).equals("else")) {
				while (!notation.get(i).equals("}")) {
					
					stack_machine();
					
				}
				i++;
			}
		}
		
	}
	
	private void while_op () {
		int k = i;
		while ((!notation.get(i).equals("}")) && (!notation.get(k).equals(";")) && (!notation.get(k).equals("{"))) {
			//System.out.println(notation_tokens.get(k));
			//System.out.println("5");
			k--;
			
		}
		
		k ++;
		String bool_op = stack.getLast();
		stack.removeLast();
		stack_tokens.removeLast();
		i++;
		
		if (bool_op == "true") {
			
			while(!notation.get(i).equals("}")) {
				
				stack_machine();
				
			}
			
			i = k;
			
		}
		
		else {
			
			while(!notation.get(i).equals("}")) {
				
				shift_i();
				
			}
			i ++;
			
		}
		
	}
	
	private void math_op () {
		//.i..out.println(stack);
		String lexemaSecOp = stack.getLast();
		stack.removeLast();
		String lexemaFirstOp = stack.getLast();
		stack.removeLast();
		String tokenSecOp = stack_tokens.getLast();
		stack_tokens.removeLast();
		String tokenFirstOp = stack_tokens.getLast();
		stack_tokens.removeLast();
		double operand2;
		double operand1;
		double result;
		operand2 = get_op(lexemaSecOp, tokenSecOp);
		operand1 = get_op(lexemaFirstOp, tokenFirstOp);
		//System.out.println(lexemaSecOp);
		switch(notation.get(i)) {
		
		case("+"):
			result = operand1 + operand2;
			lexemaSecOp = Double.toString(result);
			stack.addLast(lexemaSecOp);
			stack_tokens.addLast("TOK_NUMBER");
			
			break;
			
		case("-"):
			result = operand1 - operand2;
			lexemaSecOp = Double.toString(result);
			stack.addLast(lexemaSecOp);
			stack_tokens.addLast("TOK_NUMBER");
		
		break;
		
		case("*"):
			result = operand1 * operand2;
			lexemaSecOp = Double.toString(result);
			stack.addLast(lexemaSecOp);
			stack_tokens.addLast("TOK_NUMBER");
		
		break;
		
		case("/"):
			result = operand1 / operand2;
			lexemaSecOp = Double.toString(result);
			stack.addLast(lexemaSecOp);
			stack_tokens.addLast("TOK_NUMBER");
		
		break;
		
		}
		
		i++;
	}
	
	private void logic_op () {
		
		String lexemaSecOp = stack.getLast();
		stack.removeLast();
		String lexemaFirstOp = stack.getLast();
		stack.removeLast();
		String tokenSecOp = stack_tokens.getLast();
		stack_tokens.removeLast();
		String tokenFirstOp = stack_tokens.getLast();
		stack_tokens.removeLast();
		double operand2;
		double operand1;
		boolean result;
		operand2 = get_op(lexemaSecOp, tokenSecOp);
		operand1 = get_op(lexemaFirstOp, tokenFirstOp);
		switch(notation.get(i)) {
		
		case(">"):
			result = operand1 > operand2;
			lexemaSecOp = Boolean.toString(result);
			stack.addLast(lexemaSecOp);
			stack_tokens.addLast("Bool");
			
			break;
			
		case(">="):
			result = operand1 >= operand2;
			lexemaSecOp = Boolean.toString(result);
			stack.addLast(lexemaSecOp);
			stack_tokens.addLast("Bool");
		
		break;
		
		case("<"):
			result = operand1 < operand2;
			lexemaSecOp = Boolean.toString(result);
			stack.addLast(lexemaSecOp);
			stack_tokens.addLast("Bool");
		
		break;
		
		case("<="):
			result = operand1 >= operand2;
			lexemaSecOp = Boolean.toString(result);
			stack.addLast(lexemaSecOp);
			stack_tokens.addLast("Bool");
		
		break;
		
		case("=="):
			result = operand1 == operand2;
			lexemaSecOp = Boolean.toString(result);
			stack.addLast(lexemaSecOp);
			stack_tokens.addLast("Bool");
		
		break;
		
		case("&&"):
			
			boolean OP1;
			boolean OP2;
			OP1 = Boolean.parseBoolean(lexemaFirstOp);
			OP2 = Boolean.parseBoolean(lexemaSecOp);
			result = OP1 && OP2;
			lexemaSecOp = Boolean.toString(result);
			stack.addLast(lexemaSecOp);
			stack_tokens.addLast("Bool");
			
		break;
		
		case("||"):
			
			boolean OP1L;
			boolean OP2F;
			OP1L = Boolean.parseBoolean(lexemaFirstOp);
			OP2F = Boolean.parseBoolean(lexemaSecOp);
			result = OP1L && OP2F;
			lexemaSecOp = Boolean.toString(result);
			stack.addLast(lexemaSecOp);
			stack_tokens.addLast("Bool");
			
		break;
		}
		
		i++;
		
	}
	
	private void shift_i() {
		
		if (notation.get(i) == "else" || notation.get(i) == "while" || notation.get(i) == "if") {
			
			while (notation.get(i) != "}") {
				shift_i();
			}
			i ++;
			return;
		}
	
	i ++; return;
	
	}
		
	
	
	private double get_op(String lexema, String token) {
		
		String Op;
		double op = 0;
		switch(token) {
		
		case ("TOK_NUMBER"):
			op = parse_double(lexema);
		break;
		
		case("TOK_VAR"):
			Op = TableOfVar.get(lexema);
			//.out.println(stack);
			op = parse_double(Op);
			break;
		
		}
		
		return op;
		
	}
	
	private double parse_double(String lexema) {
		
		return Double.parseDouble(lexema);
		
	}
	
	
	
}
