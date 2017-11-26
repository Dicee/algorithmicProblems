package stackoverflow.ABStarLanguage;

import java.util.Arrays;

/**
 * Answer to https://stackoverflow.com/questions/47491635/enumerate-all-words-of-a-language-in-a-sequence/47491774?noredirect=1#comment81939636_47491774
 * Less efficient than the specialized two characters alphabet solution that also met the OP's requirements but can be applied to any finite alphabet 
 * that can be stored in an array (i.e. must have less than 2^32 - 1 symbols and fit in memory).
 */
public class TwoCharactersAlphabet {
  public static void main(String[] args) {
      listWords(new char[]{ 'e', 'z', 'f' }, 3);
  }

  private static void listWords(char[] alphabet, int maxWordLength) {
      Arrays.sort(alphabet);
      int base = alphabet.length;

      int wordLength = 1;
      int current = 0;
      int max = (int) Math.pow(base, wordLength) - 1;

      while (wordLength <= maxWordLength) {
          System.out.println(makeWord(alphabet, current, wordLength));

          if (current < max) current++;
          else {
              wordLength++;
              current = 0;
              max = (int) Math.pow(base, wordLength) - 1;
          }
      }
  }

  private static String makeWord(char[] alphabet, int current, int wordLength) {
      int base = alphabet.length;

      StringBuilder sb = new StringBuilder();
      for (int i = 0; i < wordLength; i++) {
          sb.append(alphabet[current % base]);
          current /= base;
      }
      return sb.reverse().toString();
  }
}
