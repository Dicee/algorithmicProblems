import java.io.*;
import static java.lang.Integer.parseInt;

public class Main {
    public static void main (String[] args) throws IOException {
        File file = new File(args[0]);
        BufferedReader buffer = new BufferedReader(new FileReader(file));
        String line;
        while ((line = buffer.readLine()) != null) {
            String[] split = line.split("\\s+");
            int x  = parseInt(split[0]), y = parseInt(split[1]), max = parseInt(split[2]);
            int dx = x, dy = y;
            
            for (int i=1 ; i<=max ; i++) {
                String output;
                if (i == dx) {
                    if (i == dy) {
                        output = "FB";
                        dy    += y;
                    } else
                        output = "F";
                    dx += x;
                } else if (i == dy) {
                    output = "B";
                    dy     += y;
                } else 
                    output = String.valueOf(i);
                System.out.print(output + (i == max ? "\n" :  " "));
            }
        }
        buffer.close();
    }
}