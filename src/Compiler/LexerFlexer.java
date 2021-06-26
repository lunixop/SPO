package Compiler;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.stream.Stream;





public class LexerFlexer {
	private Integer i = 0;
	//private String lexema;
	private StringBuilder input = new StringBuilder();
	private Token tok;
	private LinkedList<String> lexema = new LinkedList<>();
    private LinkedList<Token> token = new LinkedList<>();
	
	public LexerFlexer(String filePathandName) {
		
		
		try (Stream<String> st = Files.lines(Paths.get(filePathandName))) { //���������� � ����� StringBuilder ������ �� �����
            st.forEach(input::append);
        }
		
		catch (IOException ex) { //���� ���� �� �������� ��������� ��� Java �� ����� ��� �������, �� ���������� ������
			System.out.println("�� ���� ��������� ����" + filePathandName);
            System.exit(0);
            return;
        }
		

		deleteAllSpaces();
		//System.out.print(input);
		i = 0;
		CheckLexem(filePathandName);
		
		
	}
	
	private void deleteAllSpaces() { //����� ��� �������� ���� ��������/���������/�������� �� ����� �������, ������ �������� ������ �������
		
		while (input.length() > i) {
			
			switch(input.charAt(i)) {

			case('\t'):
				input.deleteCharAt(i);
				break;
			
			case('\n'):
				input.deleteCharAt(i);
				break;
				
			case(' '):
				input.deleteCharAt(i);
				break;
				
				default:
					i++;
					break;
				
			
			}
			
			
		}
		
	}
	
	private void CheckLexem(String filePathandName) {
		
		if(input.length() == 0) {
			
			System.out.println("���� ������: " + filePathandName);
            System.exit(0);
			
		}
		
		while(input.length() > 0) {
		
			if(findNextToken()) {}
			
			 else {
	        	   
	        	   System.out.println("\n�������� ����������� ������: " + input.charAt(0));
	        	   System.exit(0);
	        	   
	           }
		}
		
		//System.out.println("\n" + lexema);
		//System.out.println("\n" + token);
		
	}
	
	private boolean findNextToken() {
        for (Token t : Token.values()) { //����������� ������ �������� �� ������������ (enum) 
        	
        	 int end = t.endOfMatch(input.toString()); //�������� ����� ��� �������� ���������� ������ � �������, ��� ��������� ��� �����
            
            if (end != -1) { //���� ����� ��� ������
		          
            		token.addLast(t);  //������� � ���������� ������ �����
                	lexema.addLast(input.substring(0, end)); //���������� ������ � ������
                	input.delete(0, end); //������� ��� ����������
                	return true;
            	
            }
        }

        return false;
    }
	
	public LinkedList<String> return_lexema() {
		
		return lexema;
		
	}
	
public LinkedList<Token> return_token() {
		
		return token;
		
	}
	
	
}
