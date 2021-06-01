package globallogic;

import java.util.Set;

public class Word implements Comparable<Word> {

    static int total;

    private final Set<Character> set;
    private final int number;
    private int count;
    private String frequency;

    public Word(Set<Character> set, int number, int count) {
        this.set = set;
        this.number = number;
        this.count = count;
    }

    public Set<Character> getSet() {
        return set;
    }

    public int getCount() {
        return count;
    }

    public int getNumber() {
        return number;
    }

    public void setFrequency(String frequency){
        this.frequency = frequency;
    }

    public static void setTotal(int total) {
        Word.total = total;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public static int getTotal() {
        return total;
    }

    @Override
    public int compareTo(Word word) {
        if(this.getCount() > word.getCount())
            return 1;
        else if (this.getCount() < word.getCount())
            return -1;
        return 0;
    }

    @Override
    public String toString() {
        StringBuilder letters = new StringBuilder();
        letters.append("(");

        // For printing output in 'logic' order
        char[] possibleLetters = {'l', 'o', 'g', 'i', 'c'};

        boolean first = true;
        for(Character letter: possibleLetters) {
            if(set.contains(letter)) {
                if (first) {
                    letters.append(letter);
                    first = false;
                } else {
                    letters.append(", ").append(letter);
                }
            }
        }
        letters.append(")");

        return "{" + letters + ", " + number + "} = " + frequency + " (" + count + "/" + total + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Word))
            return false;
        Word word = (Word) obj;
        return this.set.equals(word.getSet()) && this.number == word.getNumber();
    }
}
