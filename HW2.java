/*Jamison (Jami) Biddle
 * JKB115 
 * Homework 2: Demonstrating method and array practice
 */

//Imports JOptionPane for use in Hangman
import javax.swing.JOptionPane;

//Initializes HW2, the class in which all of these methods will go.
public class HW2 {

  
 //takes a string, and replaces the first 'replacements' 
 public static String replaceFirstK(String s, char a, char b, int replacements) {
    StringBuilder output = new StringBuilder();
    int count = 0;
    for (int i = 0; i < s.length(); i += 1) {
      if (s.charAt(i) == a && count < replacements){
        output.append(b);
        count = count + 1;
      }
      else output.append(s.charAt(i));
    }
    return output.toString();
  }
  
  //Method all chars: print the alphabet between the two input characters, inclusive.
 public static String allChars(char a, char b){
   //SB for the final output
    StringBuilder s = new StringBuilder();
    //stores the alphabet
    String alphabet = "abcdefghijklmnopqrstuvwxyz";
    //Says whether it should be appending the alphabet (it's seen char a, but not char b yet)
    Boolean placing = false;

    for (int i = 0; i < alphabet.length(); i++)
        if (alphabet.charAt(i) == a && a != b) {
            s.append(alphabet.charAt(i));
            placing = true;
        } else if (alphabet.charAt(i) == b) {
            s.append(alphabet.charAt(i));
            placing = false;
            //accounts for wrong-order inputs. Simply prints the two chars.
            if (s.toString().length() == 1)
              s.append(a);
            return s.toString();
        } else if (placing) s.append(alphabet.charAt(i));
        return s.toString();
 }
  
/*Outputs a new String, except for each char of a first string if it’s not present in the 
 * second string, it is ‘replaced’ with an underscore.
 */
  public static String showCharOfString(String a, String b) {
     StringBuilder s = new StringBuilder();
     for (int i = 0; i < a.length(); i++){
      for (int p = 0; p < b.length() && (s.toString().length() < i + 1); p++) {
        if (a.charAt(i) == b.charAt(p)){
           s.append(a.charAt(i));
    }
    }
       if (s.toString().length() <= i)
         s.append('_');
     }
    return s.toString();
    
  }
  
  /*Plays hangman! When a word is inputted, it prints the number of blank spaces, and the number
   * of wrong guesses. Player guesses using a JOption Pane. When a wrong guess is inputted, the 
   * number should go up. If a correct letter is guessed, every iteration of it should be placed 
   * in the correct position. The guesses counter should not go up in the event of a correct guess.
   */
  public static Boolean hangman(String word, int maxGuesses) {
    StringBuilder s = new StringBuilder();
    int wrongGuesses = 0;
    while (wrongGuesses < maxGuesses && (showCharOfString(word, s.toString())).equals(word) == false) {
      boolean correct = false;
      System.out.println(showCharOfString(word, s.toString()) + " " + wrongGuesses);
      String guess = JOptionPane.showInputDialog("Type your guess!");
      for (int i = 0; i < word.length() && correct == false; i++){
        if (guess.charAt(0) == word.charAt(i)){
        s.append(guess);
        correct = true;
      }
      }
      if (correct == false)
        wrongGuesses += 1;
      
    }

    if (showCharOfString(word, s.toString()).equals(word))
      return true;
    else return false;
    
  }

  // Checks in a 1D array to see if the string s is present, forwards or backwards.
  public static Boolean hiddenString(char[] c, String s){
    //checks the forwards direction;
    for (int i = 0; i < c.length; i++){
      if (c[i] == s.charAt(0)){
        //loop breaks if it reaches the end of the string, the array, or the word truns out to not be present at that point.
        for (int p = 0; p < s.length() && (i+p) < c.length && c[i+p] == s.charAt(p); p++)
          if (p == s.length() - 1)
          return true;
      }
      
      //checks to see if the char is the last char in s. If so, it checks the next char in c to see if 
      if ((c[i] == s.charAt(s.length() - 1)) == true){
        //loop breaks if q i greater than s.length(), or if the checks go beyond the array length, or if the char isn't the same as the string, meaning it's not found it.
        for (int q = 0; q < s.length() && (q + i) < c.length && c[i+q] == s.charAt(s.length() - 1 - q); q++){
          if (q == s.length() - 1) {
            return true;
          }
        }
      }
    }
    //If all else fails, it returns false.
    return false;
  }
  
  
  //Check in a 2D array whether a string is present with crossword rules (u, d, l, r, diagonals)
  public static Boolean hiddenString(char[][] carray, String s){
      
    for (int i = 0; i < carray.length; i = i + 1){
      //Checks along the rows
      if (hiddenString(carray[i], s) == true)
        return true;
    }
      
    //Loops thru the whole 2D crossword
    for (int i = 0; i < carray.length; i = i + 1){
      for (int j = 0; j < carray[i].length; j++){
          
        //Accounts for 1 letter string inputs
        if (carray[i][j] == s.charAt(0) && s.length() == 0)
          return true;
          
        /*if it comes across the first letter of s, it begins checking the surrounding letters, and then moves along that 
        row/column/diagonal if it finds the se cond letter in that direction */
        if (carray[i][j] == s.charAt(0)){
          //checks above 
          if (i > 0 && j < carray[i - 1].length  && (carray[i - 1][j] == s.charAt(1))){
            //goes through the upper chars until it either reaches the end of them or finds the word.
            System.out.println((carray[i].length - 1) > 0);
            for (int p = 1; p < s.length() && (carray[i].length - p) >= 0  && carray[i-p][j] == s.charAt(p); p++) {
              if (p == s.length() - 1){
                return true;
              }
            }
          }
        
            //checks below
            if ((i + 1) < carray[i].length && j < carray[i + 1].length && (carray[i + 1][j] == s.charAt(1))){
              //goes through the lower chars until it either reaches the end of them or finds the word.
              for (int p = 1; p < s.length() && p < carray[i].length && carray[i+p][j] == s.charAt(p); p++) {
                if (p == s.length() - 1){
                  return true;
                }
              }
            }
        
            //checks top left (i-1, j-1)
            if (i > 0 && j > 0 && (carray[i - 1][j - 1] == s.charAt(1))){
              for (int p = 1; p < s.length() && (carray[i].length - p) > 0 && carray[i-p][j-p] == s.charAt(p); p++) {
                if (p == s.length() - 1){
                  return true;
                }
              }
            }
        
            //checks top right (i-1, j + 1)
            if (i > 0 && (j + 1) < carray[i-1].length && (carray[i - 1][j + 1] == s.charAt(1))){
              for (int p = 1; p < s.length() && (carray[i].length - p) >= 0 && carray[i-p][j+p] == s.charAt(p); p++) {
                if (p == s.length() - 1){
                  return true;
                }
              }
            }
        
            //checks bottom right (i + 1, j + 1)
            if (i < (carray.length -1) && (j + 1) <  carray[i+1].length && (carray[i + 1][j + 1] == s.charAt(1))){
              for (int p = 1; p < s.length() && (carray[i].length - p) >= 0 && carray[i+p][j+p] == s.charAt(p); p++) {
                if (p == s.length() - 1){
                  return true;
                }
              }
            }
        
            //Checks bottom left (i + 1, j - 1)
            if (i < (carray.length -1) && j > 0 && (carray[i + 1][j - 1] == s.charAt(1))){
              for (int p = 1; p < s.length() && (carray[i].length - p) >= 0 && carray[i+p][j-p] == s.charAt(p); p++) {
                if (p == s.length() - 1){
                  return true;
                }
              }
            }  
          }
        }       
      }
      //if all else fails, it must not be present. thus:
      return false;
  }
  
}
      
