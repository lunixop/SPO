package Compiler;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum Token {
	

	TOK_SEMI(";"),
	TOK_LOGICAL_OP("(>=|>|<=|<|==|!=|&&|\\|\\|)"),
	TOK_ASSIGN("="),
	TOK_PRINT("print"),
	TOK_NUMBER("[0-9]+(\\,[0-9]+)?"),
	TOK_KEY_IF("if"),
	TOK_KEY_ELSE("else"),
	TOK_KEY_WHILE("while"),
	TOK_L_S_BR("\\{"),
	TOK_R_S_BR("\\}"),
	TOK_L_BR("\\("),
	TOK_R_BR("\\)"),
	TOK_MATH_OP("(\\*|/|\\+|-)"),
	TOK_VAR("[a-zA-Z_][a-zA-Z_0-9]*");
	

	
	
	
	
	
	private final Pattern pattern;
	
	Token(String regex) {
        pattern = Pattern.compile("^" + regex);
    }
	
	 int endOfMatch(String s) {
	        Matcher m = pattern.matcher(s); //Передаем Matcher входной input

	        if (m.find()) { //Ищем совпадение подстроки внутри РВ
	            return m.end(); //Возвращаем последнее совпашее значение 
	        }
	        return -1; //Иначе -1
	    }
}
