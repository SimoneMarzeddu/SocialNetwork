package Social;

import Social.Exception.ViolatedInvariantException;

import java.util.*;

public class ROLMySocialNetwork extends MySocialNetwork implements ROLSocialNetwork {

    /*@REPINV:
    *           POSTREPORTS != NULL && (for each (MyPost)p, POSTREPORTS.CONTAINSKEY(p) ==> POSTREPORTS.GET(p).SIZE >= 1 && SOCIALPOSTS.CONTAINS(p))
    *           USERREPORTS != NULL && (for each (MyUser)u, USERREPORTS.CONTAINSKEY(u) ==> USERREPORTS.GET(u).SIZE >= 1 && USERS.CONTAINS(u)) */

    /*@ABSTFUNCT: α:
     *          α(HashMap<MyPost,TreeSet<String>> PostReports) -> Map in cui ogni Post che ha subito una segnalazione almeno una volta è associato all'insieme dei nomi degli utenti che hanno compiuto le segnalazioni nei suoi confronti
     *          α(HashMap<String,TreeSet<String>> UserReports) -> Map in cui ogni nome di utente che ha subito una segnalazione almeno una volta è associato all'insieme dei nomi degli utenti che hanno compiuto le segnalazioni nei suoi confronti */

        private final HashMap<MyPost,TreeSet<String>> PostReports;
        private final HashMap<String,TreeSet<String>> UserReports;

        /*
        * @REQUIRES : TRUE
        * @MODIFIES : USERS, SOCIALPOSTS, MICROBLOG, LIKESNUMBER, POSTREPORTS, USERREPORTS
        * @THROWS : none
        * @EFFECTS : A new social network has been created with the possibility of reporting offensive behavior */
        public ROLMySocialNetwork () {

            super();
            PostReports = new HashMap<>();
            UserReports = new HashMap<>();
        }

        public void report (MyUser user, MyPost post) throws IllegalArgumentException, NullPointerException {

            if(post == null) throw new NullPointerException("post cant be a null value");
            if(user == null) throw new NullPointerException("user cant be a null value");
            if(!super.getSocialPosts().contains(post)) throw new IllegalArgumentException("this post is not in socialnetwork");
            if(!super.getUsers().contains(user)) throw new IllegalArgumentException("this user is not registered in socialnetwork");

            TreeSet<String> ts;

            if(PostReports.containsKey(post)) {

                ts = PostReports.get(post);
                ts.add(user.getUsername());
                PostReports.replace(post,ts);
            }
            else {

                ts = new TreeSet<>();
                ts.add(user.getUsername());
                PostReports.put(post,ts);
            }

            String author = post.getAuthor();

            if(UserReports.containsKey(author)) {

                ts = UserReports.get(author);
                ts.add(user.getUsername());
                UserReports.replace(author,ts);
            }
            else {

                ts = new TreeSet<>();
                ts.add(user.getUsername());
                UserReports.put(author,ts);
            }
        }

        public HashMap<MyPost,TreeSet<String>> getPostReports () {

            return (HashMap<MyPost,TreeSet<String>>) PostReports.clone();
        }

        public HashMap<String,TreeSet<String>> getUserReports () {

        return (HashMap<String,TreeSet<String>>) UserReports.clone();
    }

        public boolean checkPostBan (MyPost post) throws IllegalArgumentException, NullPointerException {

            if (post == null) throw new NullPointerException("Parameter cant be null");
            if (!getSocialPosts().contains(post)) throw new IllegalArgumentException("Post must be published in the network");

            if (PostReports.containsKey(post)) return PostReports.get(post).size() >= 3;
            else return false;
        }

        public boolean checkUserBan (MyUser user) throws IllegalArgumentException, NullPointerException {

            if (user == null) throw new NullPointerException("Parameter cant be null");
            if (!getUsers().contains(user)) throw new IllegalArgumentException("User must be registered in the network");

            if (UserReports.containsKey(user.getUsername())) return UserReports.get(user.getUsername()).size() >= 9;
            else return false;
    }

        protected void checkRep() throws NullPointerException, ViolatedInvariantException {
            super.checkRep();

            if (PostReports == null) throw new NullPointerException("PostReports cant be null");
            if (UserReports == null) throw new NullPointerException("PostReports cant be null");

            for(MyPost p : PostReports.keySet()) {

                if (!super.getSocialPosts().contains(p)) throw new ViolatedInvariantException("All PostReports's posts must have been published in the network");
                if (PostReports.get(p).size()<1) throw new ViolatedInvariantException("All PostReports's posts must have been reported at least 1 time");
            }

            for(String u : UserReports.keySet()) {

                int c = 0;
                for (MyUser us : super.getUsers()) {if (us.getUsername().equals(u)) c++;}

                if (c == 0) throw new ViolatedInvariantException("All UserReports's usernames must be names of network's registered users");
                if (UserReports.get(u).size()<1) throw new ViolatedInvariantException("All UserReports's usernames must have been reported at least 1 time");
            }
        }


}
