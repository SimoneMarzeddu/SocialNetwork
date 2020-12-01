package Social;

import java.util.HashMap;
import java.util.TreeSet;

public interface ROLSocialNetwork {
    /* @OVERVIEW : ROLSocialNetwork è un'estensione gerarchica del TDA SocialNetwork, in aggiunta alla superclasse è costituita da una Map che associa ad ogni post segnalato l'
    *              insieme degli usernames degli utenti segnalatori e una Map che associa ad ogni nome di utente segnalato l'insieme degli usernames appartenenti agli utenti segnalatori */

    /*
     * @REQUIRES : (MyPost)post != NULL && (MyUser)user != NULL && (MyPost)post is published in the network && (MyUser)user is registered in the network
     * @MODIFIES : POSTEREPORTS, USERREPORTS
     * @THROWS : NullPointerException, IllegalArgumentException
     * @EFFECTS : l'utente "user" compie con successo una segnalazione del post "post" */
    void report (MyUser user, MyPost post);

    /*
     * @REQUIRES : (MyPost)post != NULL &&  (MyPost)post is published in the network
     * @MODIFIES : none
     * @THROWS : NullPointerException, IllegalArgumentException
     * @EFFECTS : restituisce true se il post è "rimovibile", false altrimenti
     * @RETURNS : Boolean*/
    boolean checkPostBan (MyPost post);

    /*
     * @REQUIRES : (MyUser)user != NULL && (MyUser)user is registered in the network
     * @MODIFIES : none
     * @THROWS : NullPointerException, IllegalArgumentException
     * @EFFECTS : restituisce true se l'utente è "rimovibile", false altrimenti
     * @RETURNS : Boolean*/
    boolean checkUserBan (MyUser user);

    /*
     * @REQUIRES : TRUE
     * @MODIFIES : none
     * @THROWS : none
     * @EFFECTS : restituisce la map che per ogni post segnalato associa l'insieme dei nomi degli utenti che hanno compiuto la segnalazione verso il post
     * @RETURNS : HashMap<MyPost, TreeSet<String>> */
    HashMap<MyPost, TreeSet<String>> getPostReports ();

    /*
     * @REQUIRES : TRUE
     * @MODIFIES : none
     * @THROWS : none
     * @EFFECTS : restituisce la map che per ogni utente segnalato associa l'insieme dei nomi degli utenti che hanno compiuto la segnalazione verso il primo
     * @RETURNS : HashMap<String,TreeSet<String>> */
    HashMap<String,TreeSet<String>> getUserReports ();
}
