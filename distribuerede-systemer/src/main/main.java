package main;

import brugerautorisation.data.Diverse;
import brugerautorisation.data.Bruger;
import brugerautorisation.transport.soap.Brugeradmin;
import galgeleg.GalgeI;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;

/**
 *
 * @author j
 */

public class main {
    static Brugeradmin ba;
    static String guess;
	public static void main(String[] args) throws MalformedURLException {
//		URL url = new URL("http://localhost:9901/brugeradmin?wsdl");
		URL url = new URL("http://javabog.dk:9901/brugeradmin?wsdl");
		QName qname = new QName("http://soap.transport.brugerautorisation/", "BrugeradminImplService");
		Service service = Service.create(url, qname);
	 ba = service.getPort(Brugeradmin.class);
                
                //URL url2 = new URL("http://localhost:9943/galgeleg?wsdl");
                URL url2 = new URL("http://ubuntu4.saluton.dk:9913/galgeleg?wsdl");
                QName qname2 = new QName("http://galgeleg/", "GalgelogikService");
                Service service2 = Service.create(url2, qname2);
                 GalgeI game = service2.getPort(GalgeI.class);
        game.nulstil();
        System.out.println("Welcome to Hangman - Please login!");
        
        Scanner scanner = new Scanner(System.in);
        while(true){
            System.out.println("Enter username: ");
            String username = scanner.nextLine();
            System.out.println("Enter password: ");
            String password = scanner.nextLine();
            
            if(Login(username, password)){break;}
            else{System.out.println("Wrong credentials. Please try again..");}
            
        }
        System.out.println("You are now logged in, please enjoy the game :)" );
        System.out.println(game.logStatus());
        while (true) {
            
            guess = scanner.nextLine();
//                if (guess.length() != 1) {
//                    
//                }
            game.gætBogstav(guess);
            System.out.println(game.logStatus());
            
            if (game.erSpilletVundet()) {
                System.out.println("Congratulations you won! You guessed the word: " + game.getOrdet());
                System.out.println("Type Y to play again");
                if (guess.equalsIgnoreCase("Y")){
                    game.nulstil();
                    System.out.println(game.logStatus());
                    System.out.println("Guess a word!");
                }
            }
            else if (game.erSpilletTabt()) {
                System.out.println("You lost! The word was: " + game.getOrdet());
                System.out.println("Type Y to play again");
                if (guess.equalsIgnoreCase("Y")){
                    game.nulstil();
                    System.out.println(game.logStatus());
                    System.out.println("Guess a word!");
                }
            }
        }

	}
        public static Boolean Login(String usrname, String password){
               
                try{
                 
                    ba.hentBruger(usrname, password);
                    return true;
                }catch(Exception e){
                    
                    System.out.println("Wrong credentials");
                    return false;
                }
            
         
        }
}

