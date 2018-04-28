package pagerank;

public class Word {
    private String word;
    private int num;

    public Word() {
    }

    public Word(String word, int num) {
        this.word = word;
        this.num = num;
    }

    public String getword() {
        return word;
    }

    public void setword(String word) {
        this.word = word;
    }

    public int getnum() {
        return num;
    }

    public void setnum(int num) {
        this.num = num;
    }
}
