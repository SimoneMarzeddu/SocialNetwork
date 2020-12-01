package Social;

import Social.Exception.ViolatedInvariantException;

import java.util.*;

public class MyUser  implements User, Comparable<MyUser>  {

    /*@REPINV:
    *           USERNAME != NULL && USERNAME != EMPTY &&
    *           USERPOSTS != NULL &&
    *           LIKEDPOSTS != NULL &&
    *           USERFOLLOWED != NULL &&
    *           USERFOLLOWERS != NULL &&
    *           FOLLOWERSCOUNTER >= 0 && FOLLOWERSCOUNTER == USERFOLLOWERS.SIZE
    */

    /*@ABSTFUNCT: α:
     *          α(String Username) -> nome univoco dell'utente
     *          α(TreeSet<MyPost> UserPosts) -> post pubblicati dall'utente
     *          α(TreeSet<MyPost> LikedPosts) -> post verso cui l'utente ha dimostarto apprezzamento (aggiunto un "mi piace")
     *          α(TreeSet<String> UserFollowed) ->  insieme dei nomi degli utenti seguiti dall'utente
     *          α(TreeSet<String> UserFollowers) ->  insieme dei nomi degli utenti che seguono l'utente
     *          α(Integer FollowersCounter) -> numero di utenti che seguono l'utente */

    private final String Username;
    private final TreeSet<MyPost> UserPosts;
    private final TreeSet<MyPost> LikedPosts;
    private final TreeSet<String> UserFollowed;
    private final TreeSet<String> UserFollowers;
    private int FollowersCounter;

    /*
     * @REQUIRES : USERNAME != NULL && USERNAME != EMPTY
     * @MODIFIES : USERNAME, USERPOSTS, LIKEDPOSTS, USERFOLLOWED, USERFOLLOWERS, FOLLOWERSCOUNTER
     * @THROWS : NullPointerException, IllegalArgumentException
     * @EFFECTS : A new user has been created */

    public MyUser (String Username) throws NullPointerException, IllegalArgumentException {

        if (Username == null) throw new NullPointerException("Username cant be a null value");
        if (Username.isEmpty() ) throw new IllegalArgumentException("Username cant be empty");

        this.Username = Username;
        UserPosts = new TreeSet<>();
        UserFollowed = new TreeSet<>();
        UserFollowers = new TreeSet<>();
        LikedPosts = new TreeSet<>();
        FollowersCounter = 0;

    }

    void addPost (MyPost post) throws IllegalArgumentException, NullPointerException {

        if(!post.getAuthor().equals(Username)) throw new IllegalArgumentException("This post is not a User's post");

        UserPosts.add(post);
    }

    void follow (MyUser followed) throws IllegalArgumentException, NullPointerException {

        if(followed == null) throw new NullPointerException("User cant be a null value");
        if(Username.equals(followed.getUsername())) throw new IllegalArgumentException("An User cant follow itself");

        UserFollowed.add(followed.getUsername());
        followed.addfollower(this.Username);

    }

    void unfollow (MyUser followed) throws IllegalArgumentException, NullPointerException {

        if(followed == null) throw new NullPointerException("User cant be a null value");
        if(Username.equals(followed.getUsername())) throw new IllegalArgumentException("An User cant unfollow itself");
        if(!UserFollowed.contains(followed.getUsername())) throw new IllegalArgumentException("This user has never been followed");

        UserFollowed.remove(followed.getUsername());
        followed.removefollower(this.Username);

    }

    private void addfollower (String follower) {

        if(UserFollowers.add(follower)) FollowersCounter++;
    }

    private void removefollower (String follower) {

        if(UserFollowers.remove(follower)) FollowersCounter--;
    }

    void like (MyPost post) {

        this.LikedPosts.add(post);
    }

    void unlike (MyPost post) throws IllegalArgumentException{

        if(!LikedPosts.contains(post)) throw new IllegalArgumentException("This post has never been liked");

            LikedPosts.remove(post);
    }

    public int compareTo (MyUser a) throws NullPointerException {

        if(a == null) throw new NullPointerException("parameter cant be null");

        return toString().compareTo(a.toString());
    }

    @Override
    public boolean equals (Object a) throws NullPointerException, IllegalArgumentException {

        if (a == null) throw new NullPointerException("parameter cant be null");
        if (!(a instanceof MyUser)) throw new IllegalArgumentException("parameter is not an instance of MyUser");

        return toString().equals(a.toString());
    }

    @Override
    public String toString () {

        return Username+UserPosts+UserFollowed+UserFollowers;
    }

    public String getUsername () {

        return Username;
    }

    public TreeSet<MyPost> getUserPosts() {

        return (TreeSet<MyPost>)UserPosts.clone();
    }

    public TreeSet<MyPost> getLikedPosts() {

        return (TreeSet<MyPost>)LikedPosts.clone();
    }

    public TreeSet<String> getUserFollowed () {

        return (TreeSet<String>)UserFollowed.clone();
    }

    public TreeSet<String> getUserFollowers () {

        return (TreeSet<String>)UserFollowers.clone();
    }

    public int getFollowersCounter () {

        return FollowersCounter;
    }

    private void checkRep () throws NullPointerException, ViolatedInvariantException {

        if(Username == null) throw new NullPointerException("Username cant be a null value");
        if(Username.isEmpty()) throw new ViolatedInvariantException("Username cant be empty");
        if(UserPosts == null) throw new NullPointerException("UserPosts cant be a null value");
        if(UserFollowed == null) throw new NullPointerException("UserFollowed cant be a null value");
        if(UserFollowers == null) throw new NullPointerException("UserFollowers cant be a null value");
        if(LikedPosts == null) throw new NullPointerException("LikedPosts cant be a null value");
        if(FollowersCounter < 0) throw new ViolatedInvariantException("FollowersCounter cant be a negative value");
        if(FollowersCounter!=UserFollowers.size()) throw new ViolatedInvariantException("FollowersCounter must be equals to user's follower's list's size");

    }


}
