package Social;

import java.util.*;

public interface SocialNetwork {
        /* @OVERVIEW : SocialNetwork è un TDA mutabile costituito da un insieme di utenti registrati alla rete, una lista di post pubblicati dagli utenti della rete,
        *              una Map che associa ad ogni username di utente sulla rete un insieme di usernames di utenti seguiti dal primo e una Map che associa ad ogni post
        *              pubblicato sulla rete il numero di apprezzamenti ("mi piace"/"likes") ricevuti da questo */

    /*
     * @REQUIRES : (List) posts != NULL
     * @MODIFIES : none
     * @THROWS : NullPointerException
     * @EFFECTS : viene restituita una rete sociale (MicroBlog) derivata dalla lsta di post passata come parametro
     * @RETURNS : Map<String, Set<String>> */
    Map<String, Set<String>> guessFollowers (List<MyPost> posts);

    /*
     * @REQUIRES : none
     * @MODIFIES : none
     * @THROWS : none
     * @EFFECTS : viene restituita la lista dei maggiori Influencers la cui dimensione massima è 10
     * @RETURNS : List<String> */
    List<String> influencers ();

    /*
     * @REQUIRES : (Map) followers != NULL
     * @MODIFIES : none
     * @THROWS : NullPointerException
     * @EFFECTS : viene restituita la lista dei maggiori Influencers della rete sociale parametro, la dimensione massima di questa lista è uguale a 10
     * @RETURNS : List<String> */
    List<String> influencers (Map<String, Set<String>> followers);

    /*
     * @REQUIRES : TRUE
     * @MODIFIES : none
     * @THROWS : none
     * @EFFECTS : viene restituito l'insieme dei nomi degli utenti menzionati nei post del social
     * @RETURNS : Set<String> */
    Set<String> getMentionedUsers ();

    /*
     * @REQUIRES : (List)ps != NULL
     * @MODIFIES : none
     * @THROWS : NullPointerException
     * @EFFECTS : viene restituito l'insieme dei nomi degli utenti menzionati nei post passati come parametro
     * @RETURNS : Set<String> */
    Set<String> getMentionedUsers (List<MyPost> ps);

    /*
     * @REQUIRES : TRUE
     * @MODIFIES : none
     * @THROWS : none
     * @EFFECTS : viene restituito l'insieme dei nomi degli utenti autori dei post del social
     * @RETURNS : Set<String> */
    Set<String> getAuthorUsers ();

    /*
     * @REQUIRES : (List)ps != NULL
     * @MODIFIES : none
     * @THROWS : NullPointerException
     * @EFFECTS : viene restituito l'insieme dei nomi degli utenti autori dei post passati come parametro
     * @RETURNS : Set<String> */
    Set<String> getAuthorUsers (List<MyPost> ps);

    /*
     * @REQUIRES : (List)ps != NULL && (String)username != NULL && (String)username è il nome di un utente registrato nel social
     * @MODIFIES : none
     * @THROWS : NullPointerException, IllegalArgumentException
     * @EFFECTS : viene restituita la sottolista dei post appartenenti alla lista parametro pubblicati dall'utente il cui nome è indicato
     * @RETURNS : List<MyPost> */
    List<MyPost> writtenBy (List<MyPost> ps, String username);

    /*
     * @REQUIRES : TRUE
     * @MODIFIES : none
     * @THROWS : none
     * @EFFECTS : viene restituita la lista dei post appartenenti al social pubblicati dall'utente il cui nome è indicato nel parametro
     * @RETURNS : List<MyPost> */
    List<MyPost> writtenBy (String username);

    /*
     * @REQUIRES : (List)words != NULL
     * @MODIFIES : none
     * @THROWS : NullPointerException
     * @EFFECTS : viene restituita la lista dei post pubblicati nel socialnetwork contenenti almeno una tra le parole raccolte nella lista parametro
     * @RETURNS : List<MyPost> */
    List<MyPost> containing (List<String> words);

    /*
     * @REQUIRES : (for each x, x is a User registered in socialnetwork => x.USERNAME != user.USERNAME)
     * @MODIFIES : USERS,MICROBLOG
     * @THROWS : IllegalArgumentException
     * @EFFECTS : viene registrato al social l'utente passato come parametro */
    void addUser (MyUser user);

    /*
     * @REQUIRES : (MyUser)user is registered in socialnetwork && (MyPost)post is not published in socialnetwork && (MyUser)user.USERNAME == (MyPost)post.AUTHOR
     * @MODIFIES : USERS, SOCIALPOSTS
     * @THROWS : IllegalArgumentException
     * @EFFECTS : viene pubblicato nel social il post specificato che ha come autore l'utente passato come parametro */
    void addPost (MyUser user, MyPost post);

    /*
     * @REQUIRES : (MyUser)follower is registered in socialnetwork && (MyUser)followed is registered in socialnetwork
     * @MODIFIES : USERS, MICROBLOG
     * @THROWS : IllegalArgumentException
     * @EFFECTS : l'utente "followed" è ora seguito dall'utente "follower" */
    void follow (MyUser follower, MyUser followed);

    /*
     * @REQUIRES : (MyUser)follower is registered in socialnetwork && (MyUser)followed is registered in socialnetwork
     * @MODIFIES : USERS, MICROBLOG
     * @THROWS : IllegalArgumentException
     * @EFFECTS : l'utente "followed" è ora non più seguito dall'utente "follower" */
    void unfollow (MyUser follower, MyUser followed);

    /*
     * @REQUIRES : (MyUser)liker is registered in socialnetwork && (MyPost)liked is published in socialnetwork
     * @MODIFIES : USERS, LIKESNUMBER
     * @THROWS : IllegalArgumentException
     * @EFFECTS : l'utente liker ha ora dimostrato apprezzamento (aggiunto un like/ mi piace) verso il post liked */
    void like (MyUser liker, MyPost liked);

    /*
     * @REQUIRES : (MyUser)liker is registered in socialnetwork && (MyPost)liked is published in socialnetwork && (MyPost)liked is a post liked by (MyUser)liker
     * @MODIFIES : USERS, LIKESNUMBER
     * @THROWS : IllegalArgumentException
     * @EFFECTS : l'utente liker ha ora rimosso la dimostrazione di apprezzamento (rimosso un like/ mi piace) verso il post liked */
    void unlike (MyUser liker, MyPost liked);

    /*
     * @REQUIRES : TRUE
     * @MODIFIES : none
     * @THROWS : none
     * @EFFECTS : restituisce la lista dei post pubblicati nel socialnetwork
     * @RETURNS : LinkedList<MyPost> */
    LinkedList<MyPost> getSocialPosts ();

    /*
     * @REQUIRES : TRUE
     * @MODIFIES : none
     * @THROWS : none
     * @EFFECTS : restituisce l' insieme degli utenti registrati nel socialnetwork
     * @RETURNS : TreeSet<MyUser> */
    TreeSet<MyUser> getUsers ();

    /*
     * @REQUIRES : TRUE
     * @MODIFIES : none
     * @THROWS : none
     * @EFFECTS : restituisce la rete sociale (MicroBlog) relativa al socialnetwork
     * @RETURNS : HashMap<String, TreeSet<String>> */
    HashMap<String, TreeSet<String>> getMicroBlog ();

    /*
     * @REQUIRES : TRUE
     * @MODIFIES : none
     * @THROWS : none
     * @EFFECTS : restituisce una Map dai post che hanno ricevuto almeno un apprezzamento (like/mi piace) al numero di apprezzamenti (like/mi piace) relativi
     * @RETURNS : HashMap<MyPost, Integer> */
    HashMap<MyPost, Integer> getLikesNumber ();

    /*
     * @REQUIRES : (MyPost)post is published in socialnetwork
     * @MODIFIES : none
     * @THROWS : IllegalArgumentException
     * @EFFECTS : restituisce il numero di apprezzamenti (like/mi piace) ricevuti dal post parametro
     * @RETURNS : Integer */
    Integer getPostLikesNumber (MyPost post);









}
