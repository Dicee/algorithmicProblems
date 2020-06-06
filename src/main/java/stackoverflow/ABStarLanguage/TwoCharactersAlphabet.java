package stackoverflow.ABStarLanguage;

/**
 * Answer to https://stackoverflow.com/questions/47491635/enumerate-all-words-of-a-language-in-a-sequence/47491774?noredirect=1#comment81939636_47491774
 */
 public class TwoCharactersAlphabet {
    private static final int MAX_LENGTH = 3;

    public static void main(String[] args) {
        int wordLength = 1;
        int current = 0;
        int max = (1 << wordLength) - 1;

        while (wordLength <= MAX_LENGTH) {
            System.out.println(makeWord(current, wordLength));

            if (current < max) current++;
            else {
                wordLength++;
                current = 0;
                max = (1 << wordLength) - 1;
            }
        }
    }

    private static String makeWord(int current, int wordLength) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < wordLength; i++) {
            sb.append((char) ((current % 2) + 'a'));
            current >>= 1;
        }
        return sb.reverse().toString();
    }
}
 
