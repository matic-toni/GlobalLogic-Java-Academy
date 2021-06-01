package globallogic;

import java.io.*;
import java.util.*;

public class JavaAcademy {
    public static void main(String[] args) throws IOException {
	System.out.println("Enter your input: ");

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String input = reader.readLine();

        FileWriter fileWriter = new FileWriter("./output.txt");
        fileWriter.write("[Input]:\n" + input + "\n\n");

        String[] words = input.split(" ");
        List<Word> wordList = new LinkedList<>();
        int allLettersCnt = 0;
        int logicLettersCntTotal = 0;
        for(String word : words) {
            // Deleting all special characters and making every word lowercase.
            // If two non-special letters are connected with a special one, they become part of the same new word.
            // Example: 'wo/r.d' becomes 'word'.
            // It is not clearly written in task what to do in this situation so I assumed this should happen.
            // If it meant in task that I had to split that word into two, this regex should be connected with the space above in the split function.
            word = word.replaceAll("[!\"#$%&'()*+,-./:;\\\\<=>?@\\[\\]^_`{|}~]", "");
            word = word.toLowerCase();

            int logicCurrentWordCnt = 0;
            Set<Character> charSet = new HashSet<>();

            for(int i = 0; i < word.length(); i++) {
                allLettersCnt++;

                // Finding some of 'logic' letters in word
                if (Character.toString(word.charAt(i)).matches("[logic]")) {
                    logicCurrentWordCnt++;
                    logicLettersCntTotal++;
                    charSet.add(word.charAt(i));
                }
            }

            Word word_obj = new Word(charSet, word.length(), logicCurrentWordCnt);

            // If this element already exists in the list, just increase its count
            if(wordList.contains(word_obj)) {
                int count = wordList.get(wordList.indexOf(word_obj)).getCount();
                wordList.get(wordList.indexOf(word_obj)).setCount(count + logicCurrentWordCnt);
                continue;
            }

            // If this word has some of the 'logic' letters, add it to list
            if(logicCurrentWordCnt > 0)
                wordList.add(word_obj);
        }

        // Sorting list by frequencies
        Collections.sort(wordList);

        // Setting static variable, which is used in toString() method
        Word.setTotal(logicLettersCntTotal);

        fileWriter.write("[Output]:\n");

        // Printing frequencies for every element in the list
        for(Word word : wordList) {
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
