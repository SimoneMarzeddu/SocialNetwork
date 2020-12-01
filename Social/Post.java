package Social;

import java.util.*;

public interface Post {
    /* @OVERVIEW : Post è un TDA immutabile definito da un ID univoco, un autore, un testo di lunghezza massima uguale
    *              a 140 caratteri, un timestamp rappresentante data e ora di pubblicazione del post e un insieme di nomi di utenti menzionati */

    /*
     * @REQUIRES : TRUE
     * @MODIFIES : none
     * @THROWS : none
     * @EFFECTS : restituisce l'ID del post
     * @RETURNS : String */
    String getID ();

    /*
     * @REQUIRES : TRUE
     * @MODIFIES : none
     * @THROWS : none
     * @EFFECTS : restituisce l'username dell'autore del post
     * @RETURNS : String */
    String getAuthor ();

    /*
     * @REQUIRES : TRUE
     * @MODIFIES : none
     * @THROWS : none
     * @EFFECTS : restituisce il testo del post
     * @RETURNS : String */
    String getText();

    /*
     * @REQUIRES : TRUE
     * @MODIFIES : none
     * @THROWS : none
     * @EFFECTS : restituisce la data e l'ora di pubblicazione del post
     * @RETURNS : String */
    String getTimestamp ();

    /*
     * @REQUIRES : TRUE
     * @MODIFIES : none
     * @THROWS : none
     * @EFFECTS : restituisce un insieme dei nomi di utenti menzionati nel post
     * @RETURNS : TreeSet<String> */
    TreeSet<String> getMentions();

    /*
     * @REQUIRES : TRUE
     * @MODIFIES : none
     * @THROWS : none
     * @EFFECTS : restituisce una stringa rappresentante la specifica istanza di Post
     * @RETURNS : String */
    @Override
    String toString ();

    /*
     * @REQUIRES : parametro != NULL && parametro.IstanzaDi(Post)
     * @MODIFIES : none
     * @THROWS : IllegalArgumentException, NullPointerException
     * @EFFECTS : restituisce un booleano rappresentante il valore della relazione di uguaglianza tra i due post
     * @RETURNS : Boolean */
    @Override
    boolean equals (Object a);

    /*
     * @REQUIRES : PARAMETER != NULL
     * @MODIFIES : none
     * @THROWS : NullPointerException
     * @EFFECTS : restituisce un valore indicante una confronto tra i due post
     * @RETURNS : Integer */
    int compareTo (MyPost a);
}
