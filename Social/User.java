package Social;

import java.util.*;

public interface User {
    /* @OVERVIEW : User è un TDA mutabile costituito da un username, una collezione di post pubblicati dall'utente, una collezione di usernames di utenti seguiti dall'utente,
                   una collezione di usernames di utenti che seguono l'utente, la cardinalità di questa e una collezione di post verso i quali l'utente ha dimostrato apprezzamento (messo "mi piace") */

    /*
     * @REQUIRES : TRUE
     * @MODIFIES : none
     * @THROWS : none
     * @EFFECTS : restituisce l'username dell'utente
     * @RETURNS : String */
    String getUsername ();

    /*
     * @REQUIRES : TRUE
     * @MODIFIES : none
     * @THROWS : none
     * @EFFECTS : restituisce l'insieme dei post pubblicati dall'utente
     * @RETURNS : TreeSet<MyPost> */
    TreeSet<MyPost> getUserPosts();

    /*
     * @REQUIRES : TRUE
     * @MODIFIES : none
     * @THROWS : none
     * @EFFECTS : restituisce l'insieme dei nomi degli utenti seguiti dall'utente
     * @RETURNS : TreeSet<String> */
    TreeSet<String> getUserFollowed ();

    /*
     * @REQUIRES : TRUE
     * @MODIFIES : none
     * @THROWS : none
     * @EFFECTS : restituisce l'insieme dei nomi degli utenti che seguono l'utente
     * @RETURNS : TreeSet<String> */
    TreeSet<String> getUserFollowers ();

    /*
     * @REQUIRES : TRUE
     * @MODIFIES : none
     * @THROWS : none
     * @EFFECTS : restituisce il numero degli utenti che seguono l'utente
     * @RETURNS : Integer */
    int getFollowersCounter ();

    /*
     * @REQUIRES : TRUE
     * @MODIFIES : none
     * @THROWS : none
     * @EFFECTS : restituisce l'insieme dei post verso i quali l'utente ha dimostarto apprezzamento (messo "mi piace")
     * @RETURNS : TreeSet<MyPost> */
    TreeSet<MyPost> getLikedPosts();

    /*
     * @REQUIRES : parametro != NULL
     * @MODIFIES : none
     * @THROWS : NullPointerException
     * @EFFECTS : restituisce un valore indicante una confronto tra i due utenti
     * @RETURNS : Integer */
    int compareTo (MyUser a);

    /*
     * @REQUIRES : parametro != NULL && parametro.IstanzaDi(User)
     * @MODIFIES : none
     * @THROWS : NullPointerException, IllegalArgumentException
     * @EFFECTS : restituisce un valore indicante la relazione di uguaglianza tra i due utenti
     * @RETURNS : Boolean */
    boolean equals (Object a);

    /*
     * @REQUIRES : TRUE
     * @MODIFIES : none
     * @THROWS : none
     * @EFFECTS : restituisce una stringa rappresentante questo User
     * @RETURNS : String */
    String toString ();
}
