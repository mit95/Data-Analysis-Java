/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab_8.analytics;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import lab_8.entities.Comment;
import lab_8.entities.Post;
import lab_8.entities.User;

/**
 *
 * @author harshalneelkamal
 */
public class AnalysisHelper {

    public void userWithMostLikes() {
        Map<Integer, Integer> userLikeCount = new HashMap<>();
        Map<Integer, User> users = DataStore.getInstance().getUsers();
        for (User user : users.values()) {
            for (Comment c : user.getComments()) {
                int likes = 0;
                if (userLikeCount.containsKey(user.getId())) {
                    likes = userLikeCount.get(user.getId());
                }
                likes += c.getLikes();
                userLikeCount.put(user.getId(), likes);
            }
        }
        int max = 0;
        int maxId = 0;
        for (int id : userLikeCount.keySet()) {
            if (userLikeCount.get(id) > max) {
                max = userLikeCount.get(id);
                maxId = id;
            }
        }
        System.out.println("User with most likes is:" + max + "\n" + users.get(maxId));
    }

    public void getFiveMostLikedComment() {
        Map<Integer, Comment> comments = DataStore.getInstance().getComments();

        List<Comment> commentList = new ArrayList<>(comments.values());

        Collections.sort(commentList, new Comparator<Comment>() {
            @Override
            public int compare(Comment c1, Comment c2) {
                return c2.getLikes() - c1.getLikes();
            }
        });

        System.out.println("5 most liked comments:");
        for (int i = 0; i < 5; i++) {
            System.out.println(commentList.get(i));
        }
    }

    public void postWithMostComments() {

        Map<Integer, Integer> postCommentCount = new HashMap<Integer, Integer>();
        Map<Integer, Comment> comments = DataStore.getInstance().getComments();

        for (Comment c : comments.values()) {
            int noofcomments = 0;
            if (postCommentCount.containsKey(c.getPostId())) {
                noofcomments = postCommentCount.get(c.getPostId());
            }
            noofcomments++;
            postCommentCount.put(c.getPostId(), noofcomments);

        }
        

        int max = 0;
        int maxId = 0;
        for (int id : postCommentCount.keySet()) {
            if (postCommentCount.get(id) > max) {
                max = postCommentCount.get(id);
                maxId = id;
            }
        }
        System.out.println("Post with most comments is: " + maxId + " Total number of comments: " + max);
    }

    public void postWithMostLikedComments() {
        Map<Integer, Integer> postLikeCount = new HashMap<Integer, Integer>();
        Map<Integer, Post> posts = DataStore.getInstance().getPosts();
        for (Post post : posts.values()) {
            for (Comment c : post.getComments()) {
                int likes = 0;
                if (postLikeCount.containsKey(post.getPostId())) {
                    likes = postLikeCount.get(post.getPostId());
                }
                likes += c.getLikes();
                postLikeCount.put(post.getPostId(), likes);
            }
        }
        int max = 0;
        int maxId = 0;
        for (int id : postLikeCount.keySet()) {
            if (postLikeCount.get(id) > max) {
                max = postLikeCount.get(id);
                maxId = id;
            }
        }
        System.out.println("Post with most liked Comment is: " + maxId + " Total number of likes: " + max);
    }

    public void avgNumOfLikes(){
        Map<Integer,Integer>userLikeCount = new HashMap<Integer,Integer>();
        Map<Integer,User> users = DataStore.getInstance().getUsers();
 
        int commentCount = 0;
        int likes = 0;
        double average_total = 0;
        for(User user:users.values()){                     
           commentCount += user.getComments().size();
           for(Comment c:user.getComments()){
              if(userLikeCount.containsKey(user.getId())){
                  likes = userLikeCount.get(user.getId());
              }
              likes += c.getLikes();  
           }                       
       } 
       average_total = likes/commentCount;
       System.out.println("Average Number of Likes per Comment = "+average_total);
        
    }
    
    public void getFiveMostInactiveUsersPost(){
        Map<Integer,Post>posts = DataStore.getInstance().getPosts();
        Map<Integer,Integer>userCountPost = new HashMap<>();//to store distinct user & post id
        Map<Integer,Integer>userOccurences = new HashMap<>(); //to store user id and its occurences
        
        for(Post post:posts.values()){           
            userCountPost.put(post.getPostId(),post.getUserId()); //storing distinct user id and post id
        }
        
        for(int id:userCountPost.keySet()){ //a set of postid's are being traversed      
            int count = Collections.frequency(userCountPost.values(), userCountPost.get(id)); //counting occurences of userid
            userOccurences.put(userCountPost.get(id), count); //adding user id & its occurences in a hashmap
        }
        
        List<Map.Entry<Integer,Integer>> postList =new ArrayList<>(userOccurences.entrySet()); //dumping hashmap values in a list for sorting

        Collections.sort(postList,new Comparator<Map.Entry<Integer, Integer>>(){
            @Override
            public int compare(Map.Entry<Integer, Integer> o1,  
                               Map.Entry<Integer, Integer> o2) 
            { 
                    return o1.getValue()-o2.getValue(); //sorting the list based on userid occurences in descending order
            } 
        });
       
        for(int i=0; i<5;i++){
            System.out.println("Top 5 most Inactive Users by post (UserId = Num of Post)"+postList.get(i)); 
            //printing the userid & occurences of 5 inactive users
        }
        
    }
    
    public void getFiveMostInactiveUsersComments(){
        Map<Integer,Comment>comments = DataStore.getInstance().getComments();
        Map<Integer,Integer>extractMap = new HashMap<>(); //to extract user id & comment id
        Map<Integer,Integer>userCountComment = new HashMap<>(); //to store user id and its comment occurences
        
        for (Comment comment:comments.values()){
            extractMap.put(comment.getId(), comment.getUserId()); //adding comment id and corresponding user id
        }
        for(int id:extractMap.keySet()){ 
            int count = Collections.frequency(extractMap.values(),extractMap.get(id));//counting occurences of user id
            userCountComment.put(extractMap.get(id), count);//adding user id & occurences to hashmap
        }
   
        List<Map.Entry<Integer,Integer>> commentList = new ArrayList<>(userCountComment.entrySet()); //dumping values in arrayList
        
        Collections.sort(commentList,new Comparator<Map.Entry<Integer,Integer>>(){
            public int compare(Map.Entry<Integer,Integer> o1,
                               Map.Entry<Integer,Integer> o2){
            
                return o1.getValue() - o2.getValue();
            }      
        });
        for(int i=0;i<5;i++){
            System.out.println("Top 5 most Inactive Users by comment(UserId = Num of Comment)"+commentList.get(i));
        } 
    }
	
    public void getInactiveUsersOnPostsandComments(){
        System.out.println("Inactive user based on postsand comments-");
        Map<Integer, User> users = DataStore.getInstance().getUsers();

        List<User> userList = new ArrayList<>(users.values());
    Collections.sort(userList, new Comparator<User>() {
        @Override
        public int compare(User o1, User o2) {
            return (o1.getPost().size()+ o1.getComments().size()) - ( o2.getPost().size() + o2.getComments().size() );
        }
    });
     for(int i = 0; i<5;i++){
        System.out.println("UserID= "+userList.get(i).getId() + " Number of Posts=  " + userList.get(i).getPost().size() + " Number of Comments=  " + userList.get(i).getComments().size());
    }
    }
public void getProactiveUsersOnPostsandComments(){
    System.out.println("Proactive user based on posts and comments-");
    Map<Integer, User> users = DataStore.getInstance().getUsers();

    List<User> userList = new ArrayList<>(users.values());
    Collections.sort(userList, new Comparator<User>() {
        @Override
        public int compare(User o1, User o2) {
            return (o2.getPost().size()+ o2.getComments().size()) - ( o1.getPost().size() + o1.getComments().size());
        }
    });
    for(int i = 0; i<5;i++){
        System.out.println("UserID= "+userList.get(i).getId() + " Number of Posts=  " + userList.get(i).getPost().size() + " Number of Comments=  " + userList.get(i).getComments().size());
    }
} 
	
	}

