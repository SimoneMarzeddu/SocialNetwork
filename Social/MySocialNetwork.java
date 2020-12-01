package Social;

import Social.Exception.ViolatedInvariantException;

import java.util.*;

public class MySocialNetwork implements SocialNetwork{

    /*@REPINV:
     *           USERS != NULL && (for each (MyUser) x, USERS.CONTAINS(x) ==> MICROBLOG.CONTAINSKEY(x.USERNAME) && (exist and is unique w, USERS.CONTAINS(w) && w.USERNAME == x.USERNAME))
     *           SOCIALPOSTS != NULL && (for each (MyPost) p, SOCIALPOSTS.CONTAINS(p) ==> (exist and is unique u, USERS.CONTAINS(u) && u.USERNAME == p.AUTHOR)) && (SOCIALPOSTS == U(for each  i, USERS.CONTAINS(i)) i.USERPOSTS)
     *           MICROBLOG != NULL && (for each k, MICROBLOG.CONTAINSKEY(k) ==> MICROBLOG.GET(k) != NULL)
     *           LIKESNUMBER != NULL && (for each (MyPost) y, LIKESNUMBER.CONTAINSKEY(y) ==> SOCIALPOSTS.CONTAINS(y))
     */

    /*@ABSTFUNCT: α:
     *          α(TreeSet<MyUser> Users) -> Insieme degli utenti registrati nel social
     *          α(LinkedList<MyPost> SocialPosts) -> post pubblicati nel social dagli utenti registrati
     *          α(HashMap<String, TreeSet<String>> MicroBlog) -> MicroBlog del social dove ogni chiave è il nome di un utente a cui sono associati i nomi degli utenti seguiti
     *          α(HashMap<MyPost, Integer> LikesNumber) ->  Map in cui ogni chiave è un post pubblicato nel social e cui è associato il numero degli apprezzamenti ("mi piace") ricevuti dal post  */

    private final TreeSet<MyUser> Users;
    private final LinkedList<MyPost> SocialPosts;
    private final HashMap<String, TreeSet<String>> MicroBlog;
    private final HashMap<MyPost, Integer> LikesNumber;

    /*
     * @REQUIRES : TRUE
     * @MODIFIES : USERS, SOCIALPOSTS, MICROBLOG, LIKESNUMBER
     * @THROWS : none
     * @EFFECTS : A new socialnetwork has been created */

        public MySocialNetwork () {

            Users = new TreeSet<>();
            SocialPosts = new LinkedList<>();
            MicroBlog = new HashMap<>();
            LikesNumber = new HashMap<>();

        }

        public Map<String, Set<String>> guessFollowers (List<MyPost> posts) throws NullPointerException {

            if (posts == null) throw new NullPointerException("Post's list cant be a null value");

            ListIterator<MyPost> iterino = posts.listIterator();
            Iterator<MyUser> iterello;
            HashMap<String, Set<String>> mappina = new HashMap<>();

            while (iterino.hasNext()) {

                MyPost temppost = iterino.next();
                iterello = Users.descendingIterator();

                while(iterello.hasNext()) {

                    MyUser tempuser = iterello.next();

                    if(temppost.getAuthor().equals(tempuser.getUsername())) {

                        mappina.put(temppost.getAuthor(),tempuser.getUserFollowed());
                        break;
                    }
                }
            }

            return mappina;

        }

        public List<String> influencers (Map<String, Set<String>> followers) throws NullPointerException {

            if (followers == null) throw new NullPointerException("Parameter Map cant be a null value");

            Map<String,Integer> guessFame = new TreeMap<>();
            LinkedList<String> influencers = new LinkedList<>();
            Set <String> allfollowed = new TreeSet<>();

            for (MyUser user : Users) {
                allfollowed.add(user.getUsername());
            }
            for (String key: allfollowed) {

                guessFame.put(key,0);

            }

            Collection<Set<String>> coll = followers.values();

            for (Set<String> itset : coll) {

                Iterator<String> iterello = itset.iterator();

                while (iterello.hasNext()) {

                    String temp = iterello.next();
                    guessFame.replace(temp, guessFame.get(temp) + 1);

                }

        }  /* guessFame viene aggiornata con la cardinalità di followers per utente mappato */

        TreeMap<Integer,LinkedList<String>> revguessFame = new TreeMap<>();
        TreeSet<Integer> tsvalues = new TreeSet<>(guessFame.values());

        for (Integer keyn: tsvalues) {

            LinkedList<String> mappedusers = new LinkedList<>();

            for (String keyu : allfollowed) {

                if (guessFame.get(keyu).equals(keyn)) mappedusers.add(keyu);
            }

            revguessFame.put(keyn, mappedusers);
        }        /* revguessFame viene popolata come se fosse la map "inversa" di guessFame */

        Iterator<Integer> iterino = tsvalues.descendingIterator();

        while (iterino.hasNext()) {

            influencers.addAll(revguessFame.get(iterino.next()));

        }

        return influencers.subList(0,Math.min(influencers.size(),9));

    }

        public List<String> influencers () {

            return influencers((Map)MicroBlog);
        }

        public Set<String> getMentionedUsers () {

            return this.getMentionedUsers(this.getSocialPosts());
        }

        public Set<String> getMentionedUsers (List<MyPost> ps) throws NullPointerException {

            if (ps == null) throw new NullPointerException("Parameter list cant be a null value");

            TreeSet<String> MentionedUsers = new TreeSet<>();

            for(MyPost post : ps) {

                MentionedUsers.addAll(post.getMentions());
            }

            Iterator<String> iterino = MentionedUsers.iterator();
            Iterator<MyUser> iterello;
            TreeSet<String> trueMentionedUsers = (TreeSet<String>) MentionedUsers.clone();

            while(iterino.hasNext()) {

                String tempm = iterino.next();
                iterello = Users.descendingIterator();

                while(iterello.hasNext()) {

                    MyUser tempu = iterello.next();

                    if(tempu.getUsername().equals(tempm)) break;
                    if(!tempu.getUsername().equals(tempm) && !iterello.hasNext()) trueMentionedUsers.remove(tempm);
                }
            } /* eliminazione menzioni di utenti non presenti */

            return trueMentionedUsers;
        }

        public Set<String> getAuthorUsers () {

            return this.getAuthorUsers(this.getSocialPosts());
        }

        public Set<String> getAuthorUsers (List<MyPost> ps) throws NullPointerException {

            if (ps == null) throw new NullPointerException("Parameter list cant be a null value");

            Set<String> authors = new TreeSet<>();

            for (MyPost post : ps) {

                authors.add(post.getAuthor());
            }

            return authors;
        }

        public List<MyPost> writtenBy (String username) {

            return this.writtenBy(SocialPosts,username);
        }

        public List<MyPost> writtenBy (List<MyPost> ps, String username) throws NullPointerException, IllegalArgumentException {

            if(username.equals(null)) throw new NullPointerException("username cant be null");
            if(ps == null) throw new NullPointerException("Parameter list cant be null");
            if(!this.checkuser(username)) throw new IllegalArgumentException("user not found");

            LinkedList<MyPost> posts = new LinkedList<>();

            for (MyPost p : ps) {

                if(p.getAuthor().equals(username)) posts.add(p);

            }
            return posts;

        }

        public List<MyPost> containing (List<String> words) throws NullPointerException {

            if (words == null) throw new NullPointerException("Parameter list cant be null");

            LinkedList<MyPost> SocialPostsclone = this.getSocialPosts();

            for (MyPost post : SocialPosts) {

                Iterator<String> iterino = words.iterator();

                while (iterino.hasNext()) {

                    String temp = iterino.next();
                    if(post.getText().contains(temp)) break;
                    if(!iterino.hasNext()) SocialPostsclone.remove(post);
                }
            }

            return SocialPostsclone;

        }

        public void addUser (MyUser user) throws IllegalArgumentException{

            String username = user.getUsername();

            if(this.checkuser(username)) throw new IllegalArgumentException("This username has already been taken");


            Users.add(user);
            this.MicroBlog.put(username, user.getUserFollowed());
        }

        public void addPost (MyUser user, MyPost post) throws IllegalArgumentException{

            if(!Users.contains(user)) throw new IllegalArgumentException("User not found");
            if(SocialPosts.contains(post)) throw new IllegalArgumentException("This post is already in Socialnetwork");
            if(user.getUsername()!=post.getAuthor()) throw new IllegalArgumentException("This post is already in Socialnetwork");

            SocialPosts.add(post);
            user.addPost(post);

        }

        public void follow (MyUser follower, MyUser followed) throws IllegalArgumentException{

            if(!Users.contains(follower)) throw new IllegalArgumentException("Follower user is not registered in the network");
            if(!Users.contains(followed)) throw new IllegalArgumentException("Followed user is not registered in the network");

            follower.follow(followed);
            MicroBlog.replace(follower.getUsername(), follower.getUserFollowed());
        }

        public void unfollow (MyUser follower, MyUser followed) throws IllegalArgumentException {

        if(!Users.contains(follower)) throw new IllegalArgumentException("Follower user is not registered in the network");
        if(!Users.contains(followed)) throw new IllegalArgumentException("Followed user is not registered in the network");

        follower.unfollow(followed);
        MicroBlog.replace(follower.getUsername(), follower.getUserFollowed());
    }

        public void like (MyUser liker, MyPost liked)  throws IllegalArgumentException {

            if(!Users.contains(liker)) throw new IllegalArgumentException("user not found");
            if(!SocialPosts.contains(liked)) throw new IllegalArgumentException("post not found");

            if(LikesNumber.containsKey(liked) && !liker.getLikedPosts().contains(liked)) LikesNumber.replace(liked,LikesNumber.get(liked)+1);
            else LikesNumber.put(liked,1);

            liker.like(liked);
        }

        public void unlike (MyUser liker, MyPost liked) throws IllegalArgumentException {

            if(!Users.contains(liker)) throw new IllegalArgumentException("user not found");
            if(!SocialPosts.contains(liked)) throw new IllegalArgumentException("post not found");
            if(!liker.getLikedPosts().contains(liked)) throw new IllegalArgumentException("this post is not a user's liked post");

        if(LikesNumber.containsKey(liked) && liker.getLikedPosts().contains(liked)) LikesNumber.replace(liked,LikesNumber.get(liked)-1);


        liker.unlike(liked);
    }

        private boolean checkuser (String username) {

            if(Users.isEmpty()) return false;

            Iterator<MyUser> iterino = Users.descendingIterator();

            while(iterino.hasNext()) {

                if (iterino.next().getUsername().equals(username)) break;
                if (!iterino.hasNext()) return false;
            }

            return true;
        }

        public LinkedList<MyPost> getSocialPosts () {

            return (LinkedList<MyPost>) SocialPosts.clone();
        }

        public TreeSet<MyUser> getUsers () {

        return (TreeSet<MyUser>) Users.clone();
    }

        public HashMap<String, TreeSet<String>> getMicroBlog () {

            return (HashMap<String, TreeSet<String>>) MicroBlog.clone();
        }

        public HashMap<MyPost, Integer> getLikesNumber () {

        return (HashMap<MyPost, Integer>) LikesNumber.clone();
    }

        public Integer getPostLikesNumber (MyPost post) throws IllegalArgumentException {

            if(!SocialPosts.contains(post)) throw new IllegalArgumentException("Parameter post must be puplished in socialnetwork");
            if(LikesNumber.containsKey(post)) return LikesNumber.get(post);

            else return 0;
    }

        protected void checkRep () throws NullPointerException, ViolatedInvariantException {

            {
                if (Users == null) throw new NullPointerException("User's list cant be a null value");

                int usercount;

                for (MyUser usera : Users) {

                    usercount = 0;
                    if (!MicroBlog.containsKey(usera))
                        throw new ViolatedInvariantException(" All User's must be mapped in social's MicroBlog");

                    for (MyUser userb : Users) {

                        if (userb.getUsername().equals(usera.getUsername())) usercount++;
                        if (usercount > 1) throw new ViolatedInvariantException("Two Users cant have the same username");

                    }
                }
            } // Users Invariant

            {
                if (SocialPosts == null) throw new NullPointerException("SocialPosts cant be a null value");

                int usercount;
                boolean write = true;
                LinkedList<MyPost> list = new LinkedList<>();
                for (MyPost post : SocialPosts) {

                    usercount = 0;
                    for (MyUser user : Users) {

                        if (user.getUsername().equals(post.getAuthor())) usercount++;
                        if (usercount > 1) throw new ViolatedInvariantException("Two Users cant have the same username");
                        if (write == true) list.addAll(user.getUserPosts());
                    }
                    if (write == true) write = false;

                    if(usercount == 0) throw new ViolatedInvariantException("A post must be published by a socialnetwork's registered user");
                }

                if (!(SocialPosts.containsAll(list) && list.containsAll(SocialPosts))) throw new ViolatedInvariantException("SocialPosts must be an union of all differents users's UserPosts list");

            } // SocialPosts Invariant

            {
                if (MicroBlog == null) throw new NullPointerException("MicroBlog cant be a null value");

                for (String key : MicroBlog.keySet()) {

                    if (MicroBlog.get(key) == null) throw new ViolatedInvariantException("All MicroBlog's mapped values cant be a null value");
                }

            } // MicroBlog Invariant

            {
                if (LikesNumber == null) throw new NullPointerException("LikesNumber cant be a null value");

                    if(!SocialPosts.containsAll(LikesNumber.keySet())) throw new ViolatedInvariantException("All LikesNumber's posts must be published in the network");

            } // LikesNumber Invariant
        }

}
