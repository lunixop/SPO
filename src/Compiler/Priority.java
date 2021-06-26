package Compiler;

public class Priority {
	
	public int return_priority(String token, String lexema) {
		
		int i = 0;
		
		switch (token) {
		
		case("TOK_ASSIGN"):
			i = 2;
		break;
		
		case("TOK_MATH_OP"):
			
			switch(lexema) {
				
				case("+"):
					i = 7;
				break;
				
				case("-"):
					i = 7;
				break;
				
				case("*"):
					i = 8;
				break;
				
				case("/"):
					i = 8;
				break;
				
			}
		
		break;
		
		case("TOK_LOGICAL_OP"):
			
			switch(lexema) {
			
				case("&&"):
					i = 4;
				break;
				
				case("||"):
					i = 3;
				break;
				
				default:
					i = 7;
				break;
			
		}
			break;
		
		}
		
		
		return i;
	}
	
	
}
