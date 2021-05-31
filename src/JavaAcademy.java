import java.io.*;
import java.util.*;

public class JavaAcademy {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String input = reader.readLine();

        FileWriter fileWriter = new FileWriter("./output.txt");
        fileWriter.write("[Input]:\n" + input + "\n\n");

        String[] words = input.split(" ");
        List<Word> word_list = new LinkedList<>();
        int allLettersCnt = 0;
        int logicLettersCntTotal = 0;
        for(String word : words) {
            // Deleting all special characters and making every word lowercase.
            // If two non-special letters are connected with a special one, they become part of the same new word.
            // Example: 'wo/r.d' becomes 'word'.
            // It is not clearly written in task what to do in this situation so I assumed this should happen.
            // If it meant that I had to split that word into two, this regex should be connected with the space above in the split function.
            word = word.replaceAll("[!\"#$%&'()*+,-./:;\\\\<=>?@\\[\\]^_`{|}~]", "");
            word = word.toLowerCase();

            int logicCurrentWordCnt = 0;
            Set<Character> charSet = new HashSet<>();

            for(int i = 0; i < word.length(); i++) {
                allLettersCnt++;

                if (Character.toString(word.charAt(i)).matches("[logic]")) {
                    logicCurrentWordCnt++;
                    logicLettersCntTotal++;
                    charSet.add(word.charAt(i));
                }
            }

            Word word_obj = new Word(charSet, word.length(), logicCurrentWordCnt);

            // If this element already exists in the list, just increase its count
            if(word_list.contains(word_obj)) {
                word_list.get(word_list.indexOf(word_obj)).count += logicCurrentWordCnt;
                continue;
            }

            // If this word has some of the 'logic' letters, add it to list
            if(logicCurrentWordCnt > 0)
                word_list.add(new Word(charSet, word.length(), logicCurrentWordCnt));
        }

        // Sorting list by frequencies
        Collections.sort(word_list);

        // Setting static variable, which is used in toString() method
        Word.setTotal(logicLettersCntTotal);

        fileWriter.write("[Output]:\n");

        // Printing frequencies for every element in the list
        for(Word word : word_list) {
            word.setFrequency(String.format(Locale.US, "%.2f", + (double) word.getCount() / Word.getTotal()));
            System.out.println(word.toString());

            // Writing to file for persistence
            fileWriter.write(word.toString() + "\n");
        }

        // Printing total frequency
        String total_frequency = String.format(Locale.US, "%.2f", + (double) Word.getTotal() / allLettersCnt);
        System.out.println("TOTAL Frequency: " + total_frequency + " (" + Word.getTotal() + "/" + allLettersCnt + ")");
        fileWriter.write("TOTAL Frequency: " + total_frequency + " (" + Word.getTotal() + "/" + allLettersCnt + ")\n");

        fileWriter.close();

    }
}
