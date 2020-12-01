package Social;

import Social.Exception.ViolatedInvariantException;

import java.util.*;

public class MyPost implements Post, Comparable<MyPost>{

    /*@REPINV:
    *           ID != NULL && ID != EMPTY &&
    *           AUTHOR != NULL && AUTHOR != EMPTY &&
    *           TIMESTAMP != NULL && TIMESTAMP != EMPTY &&
    *           TEXT != NULL && TEXT'S SIZE <= 140
    *           MENTIONS != NULL */

    /*@ABSTFUNCT: α:
    *          α(String ID) -> identificatore univoco del post
    *          α(String Author) -> autore del post
    *          α(String Text) -> testo del post
    *          α(String Timestamp) ->  data e orario di pubblicazione del post
    *          α(TreeSet<String> Mentions) -> insieme degli utenti menzionati nel post */

    private final String ID;
    private final String Author;
    private final String Text;
    private final String Timestamp;
    private final TreeSet<String> Mentions;

    /*
    * @REQUIRES : AUTHOR != NULL && AUTHOR != EMPTY && TEXT != NULL && TEXT.LENGTH <= 140
    * @MODIFIES : this.ID, this.Author, this.Text, this.Timestamp, this.Mentions
    * @THROWS : NullPointerException, IllegalArgumentException
    * @EFFECTS : Viene creato un nuovo post */

    public MyPost (String Author, String Text) throws NullPointerException, IllegalArgumentException {

        if (Text == null) throw new NullPointerException("Text cant be a null value");
        if (Author == null) throw new NullPointerException("author cant be a null value");
        if (Author.isEmpty()) throw new IllegalArgumentException("Author cant be empty");
        if (Text.length() > 140) throw new IllegalArgumentException("Text's max lenght is 140");

        Timestamp =  Calendar.getInstance().getTime().toString();
        this.Author = Author;
        this.Text = Text;
        this.Mentions = new TreeSet<>();
        ID = this.Author + Calendar.getInstance().getTimeInMillis();

    }

    /*
     * @REQUIRES : AUTHOR != NULL && AUTHOR != EMPTY && TEXT != NULL && TEXT.LENGTH <= 140 && MENTIONS != NULL
     * @MODIFIES : this.ID, this.Author, this.Text, this.Timestamp, this.Mentions
     * @THROWS : NullPointerException, IllegalArgumentException
     * @EFFECTS : Viene creato un nuovo post */

    public MyPost (String Author, String Text, TreeSet<String> mentions) throws NullPointerException, IllegalArgumentException {

        if (Text == null) throw new NullPointerException("Text cant be a null value");
        if (Author == null) throw new NullPointerException("author cant be a null value");
        if (mentions == null) throw new NullPointerException("mentions cant be a null value");
        if (Author.isEmpty()) throw new IllegalArgumentException("Author cant be empty");
        if (Text.length() > 140) throw new IllegalArgumentException("Text's max lenght is 140");

        Timestamp =  Calendar.getInstance().getTime().toString();
        this.Author = Author;
        this.Text = Text;
        this.Mentions = mentions;
        ID = this.Author + Calendar.getInstance().getTimeInMillis();

    }

    public String getAuthor() {
        return Author;
    }

    public String getID () {
        return ID;
    }

    public String getText() {
        return Text;
    }

    public TreeSet<String> getMentions() {

        return (TreeSet<String>) Mentions.clone();
    }

    public String getTimestamp() {
        return Timestamp;
    }

    @Override
    public boolean equals (Object a) throws IllegalArgumentException, NullPointerException{

        if (a == null) throw new NullPointerException("parameter cant be null");
        if (!(a instanceof MyPost)) throw new IllegalArgumentException("parameter is not an instance of MyPost");

        return this.toString().equals(a.toString());
    }

    @Override
    public String toString () {

        return ID + Text ;
    }

    public int compareTo (MyPost a) throws NullPointerException {

        return this.toString().compareTo(a.toString());
    }

    private void checkRep () throws ViolatedInvariantException, NullPointerException {

        if (Author == null) throw new NullPointerException("Author is not initialized");
        if (Author.isEmpty()) throw new ViolatedInvariantException("Author is empty");
        if (Text == null) throw new NullPointerException("Text is not initialized");
        if (Text.length() > 140) throw new ViolatedInvariantException("Text.length > 140");
        if (Mentions == null) throw new NullPointerException("Mentions are not initialized");
    }
}
