package codeeval.easy.SelfDescriptingNumbers;

import java.io.*;
public class Main {
    public static void main (String[] args) throws IOException {
        File file = new File(args[0]);
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        while ((line = br.readLine()) != null) {
            int[] counters = new int[10];
            line = line.trim();
            
            int output = 1;
            if (line.length() > 9) 
                output = 0;
            else {
                for (int i=0 ; i<line.length() ; i++) 
                    counters[line.charAt(i) - '0']++;
                    
                for (int i=0 ; i<line.length() ; i++)
                    if (counters[i] != line.charAt(i) - '0') {
                        output = 0;
                        break;
                    }
            }
            System.out.println(output);
        }
        br.close();
    }
}