import java.util.Scanner;

/**
 *  Command line encryption tool
 *  For CSCI 717
 *  @author Matt
 */
public class EncryptionMachine {

    public static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz"; //valid chars to encrypt
    public static final int SHIFT = 3; //how many positions to shift each char

    /**
     * Main method creates a Scanner for user input,
     * then runs the CaeserCipher
     * @param args to start heated arguments
     */
    public static void main(String args[]) {

        Scanner scan = new Scanner(System.in); //used to read user input

        runCaesarCipher(scan);

        //take out da trash
        scan.close();

    }

    /**
     * Asks user for an encryption key, then lets user
     * input words to be encrypted.
     * @param scan Used for user input
     */
    private static void runCaesarCipher(Scanner scan) {

        printBorder();

        System.out.println("Thank you for choosing Caesar Salad Cipher"+"\n");

        //get encryption key from user
        System.out.print("Enter secret key: ");
        String key = scan.nextLine();
        try {
            System.out.println("Your encrypted key is: "+encryptWord(key));
        }
        catch(IllegalArgumentException ex) { //handle exceptions from encryptWord
            handleError(ex.getMessage());
            return;
        }

        //get word count from user
        System.out.print("\n"+"Enter the number of words to be encrypted: ");
        int wordCount = -1;
        try {
            wordCount = Integer.parseInt(scan.nextLine());
        }
        catch(NumberFormatException ex) { //if input isn't an integer
            handleError("That was not a number...");
            return;
        }
        if(wordCount<1) { //word count must be 1 or more
            handleError("Word count must be positive");
            return;
        }

        //allow user to enter each word one-by-one
        for(int i=0; i<wordCount; i++) {

            System.out.print("\n"+"Enter a word: ");
            String word = scan.nextLine();
            try {
                System.out.println("\""+word+"\" has been encrypted to: "+encryptWord(word));
            }
            catch(IllegalArgumentException ex) { //handle exceptions from encryptWord
                handleError(ex.getMessage());
                return;
            }

        }

        System.out.println("\n"+"All words have been encrypted.");

        printBorder();

    }

    /**
     * Print error message to user and closing border.
     * CaesarCipher should be halted after this call.
     * @param message Error message to print
     */
    private static void handleError(String message) {
        System.out.println("\n"+message+"\n"+"Stopping Caesar Cipher Program.");
        printBorder();
    }

    /**
     * Prints a border to make the console look super cool
     */
    private static void printBorder() {

        System.out.println();

        for(int i=0; i<24; i++)
            System.out.print("~-");

        System.out.println("~\n");

    }


    /**
     * Encrypts a word using {@link EncryptionMachine#shiftChar()}
     * @param word Word to be encrypted
     * @return The encrypted word
     * @throws IllegalArgumentException If input is null or an empty String
     */
    private static String encryptWord(String word) throws IllegalArgumentException {

        if(word != null && word.length()>0) { //make sure input is valid

            String result = ""; //encrypted chars will be appended to this String

            //shift each char one-by-one
            for(int i=0; i<word.length(); i++)
                result += shiftChar(word.charAt(i));

            return result;
        }
        else
            throw new IllegalArgumentException("Word cannot be empty");

    }

    /**
     * Encrypts a single char by shifting it {@link EncryptionMachine#SHIFT}
     * @param c Char to be encrypted
     * @return The encrypted char
     * @throws IllegalArgumentException If c is not in {@link EncryptionMachine#ALPHABET}
     */
    private static char shiftChar(char c) throws IllegalArgumentException {

        int pos = ALPHABET.indexOf(c); //get position of char in ALPHABET
        if(pos != -1) { //make sure char is in ALPHABET

            //shift the char position, wrapping around if necessary
            pos += SHIFT;
            while(pos>=ALPHABET.length())
                pos -= ALPHABET.length();
            while(pos<0)
                pos += ALPHABET.length();

            return ALPHABET.charAt(pos);

        }
        else if(c == ' ')
            throw new IllegalArgumentException("Words cannot contain spaces");
        else
            throw new IllegalArgumentException("Invalid character: "+c);

    }

}

