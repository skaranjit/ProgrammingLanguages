/* File: Front.java
Author: Suman Karanjit
Program: Assignment06
Date: 03/02/2021
*/
// This program is conversion of front.c to front.java from the course book.

//Importing io for file reading where the input for the program is : front.in
import java.io.*;

public class front
{
    // Global declarations
    // Variables
    private static int charClass;
    private static char[] lexeme;
    private static char nextChar;
    private static int lexLen;
    private static int token;
    private static int nextToken;
    private static File in_fp;
    private static FileInputStream fopen;
    
    //Character classes
    private static final int LETTER=0;
    private static final int DIGIT=1;
    private static final int UNKNOWN=99;
    
    //Token codes
    private static final int INT_LIT=10;
    private static final int IDENT=11;
    private static final int ASSIGN_OP=20;
    private static final int ADD_OP=21;
    private static final int SUB_OP=22;
    private static final int MULT_OP=23;
    private static final int DIV_OP=24;
    private static final int LEFT_PAREN=26;
    private static final int RIGHT_PAREN=26;
    private static final int EOF=-1;
    
    //Function declarations
    /*****************************************************/
/* lookup - a function to lookup operators and parentheses
 and return the token */
    public static int lookup(char ch) {
        switch (ch) {
            case '(':
                addChar();
                nextToken = LEFT_PAREN;
                break;
            case ')':
                addChar();
                nextToken = RIGHT_PAREN;
                break;
            case '+':
                addChar();
                nextToken = ADD_OP;
                break;
            case '-':
                addChar();
                nextToken = SUB_OP;
                break;
            case '*':
                addChar();
                nextToken = MULT_OP;
                break;
            case '/':
                addChar();
                nextToken = DIV_OP;
                break;
            default:
                addChar();
                nextToken = EOF;
                break;
        }
        return nextToken;
    }

    // addChar - a function to add nextChar to lexeme
    public static void addChar()
    {
        if(lexLen <=98)
        {
            lexeme[lexLen++] = nextChar;
            lexeme[lexLen] = 0;
        }
        else
        {
            System.out.println("Error - lexeme is too long \n");
        }
    }
    
    //getchar - a function to get the next Character of input and determine its Character class
    public static void getChar()
    {
        try
        {
            if(fopen.available()>0)
            {
                nextChar = (char)fopen.read();
                if(Character.isLetter(nextChar)){
                    charClass=LETTER;
                }
                else if(Character.isDigit(nextChar)){
                    charClass=DIGIT;
                }
                else{
                    charClass=UNKNOWN;
                }		
            }
            else
            {
                charClass=EOF;
            }
        }
	catch(IOException e)
	{
		e.printStackTrace();	
	}
    }
    
    // getNonBlank - a function to call getChar until it returns a non-whitespace character
    public static void getNonBlank()
    {
        while(Character.isSpaceChar(nextChar)){
            getChar();
        }
    }
    
    // lex - a simple lexical analyzer for arithmetic expressions
    
    public static int lex()
    {
        lexLen=0;
        getNonBlank();
        switch(charClass)
        {
// Parse identifiers
            case LETTER:
                addChar();
                getChar();
                while(charClass==LETTER || charClass==DIGIT)
                {
                    addChar();
                    getChar();
                }
                nextToken = IDENT;
                break;
// Parse integer literals
            case DIGIT:
                addChar();
                getChar();
                while(charClass==DIGIT)
                {
                    addChar();
                    getChar();
                }
                nextToken=INT_LIT;
                break;
// Parentheses and operators
            case UNKNOWN:
                lookup(nextChar);
                getChar();
                break;
// EOF
            case EOF:
                nextToken=EOF;
                lexeme[0]='E';
                lexeme[1]='O';
                lexeme[2]='F';
                lexeme[3]=0;
                break;
        }
// End of switch statement
	System.out.println("Next token is: "+nextToken+" Next lexeme is: "+new String(lexeme));
	// for(int i=0; i<lexLen+1; i++){
	// 	System.out.println(lexeme[i]);
	// }
	return nextToken;
    }


// main function
public static void main(String args[]){
	lexLen=0;
	in_fp = new File("front.in");
	
	if(!in_fp.exists()){
		System.out.println("File does not exist");
		return;
	}
	else{
		try{
			fopen=new FileInputStream(in_fp);
			char current;
			getChar();
			do
			{
                //initiate lexeme to new char everytime. 
                lexeme=new char[100];
                lex();
			}
			while (fopen.available()>0);
			{
                
                System.out.println("Next token is: -1, Next lexem is EOF\n");
			}
		}
		catch(IOException e)
		{
            e.printStackTrace();
		}
      }
	}
}



