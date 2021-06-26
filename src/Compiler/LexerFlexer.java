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
		
		
		try (Stream<String> st = Files.lines(Paths.get(filePathandName))) { //Записываем в класс StringBuilder данные из файла
            st.forEach(input::append);
        }
		
		catch (IOException ex) { //Если файл не является текстовым или Java не может его открыть, то вызывается ошибка
			System.out.println("Не могу прочитать файл" + filePathandName);
            System.exit(0);
            return;
        }
		

		deleteAllSpaces();
		//System.out.print(input);
		i = 0;
		CheckLexem(filePathandName);
		
		
	}
	
	private void deleteAllSpaces() { //Метод для удаления всех пробелов/табуляций/перевода на новую строчку, тобишь получаем единую строчку
		
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
			
			System.out.println("Файл пустой: " + filePathandName);
            System.exit(0);
			
		}
		
		while(input.length() > 0) {
		
			if(findNextToken()) {}
			
			 else {
	        	   
	        	   System.out.println("\nВстречен неизвестный символ: " + input.charAt(0));
	        	   System.exit(0);
	        	   
	           }
		}
		
		//System.out.println("\n" + lexema);
		//System.out.println("\n" + token);
		
	}
	
	private boolean findNextToken() {
        for (Token t : Token.values()) { //Присваиваем токену значение из перечеслений (enum) 
        	
        	 int end = t.endOfMatch(input.toString()); //Вызываем метод для возврата последнего номера в строчке, где определен был токен
            
            if (end != -1) { //Если номер был найдет
		          
            		token.addLast(t);  //Заносим в двусвязный список токен
                	lexema.addLast(input.substring(0, end)); //Подстрочку вносим в список
                	input.delete(0, end); //Удаляем эту подстрочку
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
