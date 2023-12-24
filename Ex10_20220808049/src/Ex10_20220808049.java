import java.util.*;

public class Ex10_20220808049 {
    public static void main(String[] args) {

    }
}
class User {
    private int id;
    private String username;
    private String email;
    private Set<User> followers;
    private Set<User> following;
    private Set<Post> likedPosts;
    private Map<User, Queue<Message>> messages;

    public User(String username, String email) {
        this.username = username;
        this.email = email;
        this.followers= new HashSet<>();
        this.following= new HashSet<>();
        this.likedPosts= new HashSet<>();
        this.messages= new HashMap<>();
        this.id= this.hashCode();
    }


    public String getUsername() {return username;}
    public void setUsername(String username) {this.username = username;}

    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}

    public Set<User> getFollowers() {return followers;}

    public Set<User> getFollowing() {return following;}

    public Set<Post> getLikedPosts() {return likedPosts;}

    public void message(User recipient , String content){

        Queue<Message> messageThis = messages.getOrDefault(recipient,new LinkedList<>());

        Queue<Message> messageRecipient = recipient.messages.getOrDefault(recipient,new LinkedList<>());

        Message message = new Message(this,content);

        messageThis.add(message);
        messageRecipient.add(message);

        messages.put(recipient,messageThis);
        recipient.messages.put(this,messageRecipient);



        read(recipient);
    }

    public void read(User user){
        for (Message messages:messages.get(user)) {
            System.out.println(messages.read(user));
        }
    }

    public void follow(User user){
        if (following.contains(user)){
            following.remove(user);
            user.getFollowers().remove(this);
        }else{
            following.add(user);
            user.getFollowers().add(this);
        }
    }

    public void like(Post post){
        post.likedBy(this);
    }

    public Post post(String content){
        return new Post(content);

    }

    public Comment comment(User user, String content){
        Comment comment = new Comment(content);
        comment.commentBy(user,comment);
        return comment;
    }


    @Override
    public boolean equals(Object o){
        if (o.getClass() == this.getClass()){
            User user = (User) o;
            return this.id == user.id;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(email) ;
    }
}

class Message{
    private boolean seen;
    private Date dateSent;
    private String content;
    private User sender;

    public Message(User sender, String content) {
        this.content = content;
        this.sender = sender;
        this.seen=false;
        dateSent= new Date();
    }

    public String read(User reader){
        if (!reader.equals(sender)){
            this.seen=true;
            System.out.println("Sent at: " + dateSent);
            return content;
        }
        return null;
    }

    public boolean hasRead(){
        return seen;
    }


}
class Post{
    private Date datePosted;
    private String content;
    private Set<User> likes;
    private Map<User,ArrayList<Comment>> comments;

    public Post(String content) {
        this.content = content;
        this.datePosted= new Date();
        this.likes= new HashSet<>();
        this.comments = new HashMap<>();
    }
    public boolean likedBy(User user){
        if(user.getLikedPosts().contains(this)){
            user.getLikedPosts().remove(this);
            likes.remove(user);
            return false;
        }else{
            user.getLikedPosts().add(this);
            likes.add(user);
            return true;
        }
    }

    public boolean commentBy(User user , Comment comment){
        ArrayList<Comment> comments1 = comments.getOrDefault(user,new ArrayList<>());
        comments1.add(comment);
        comments.put(user,comments1);
        return true;
    }

    public String getContent(){
        System.out.println("Posted at: " + datePosted);
        return content;
    }

    public Comment getComment(User user ,  int index){
        if (comments.containsKey(user)){
            if (comments.get(user).size() < index)
                return comments.get(user).get(index);
        }
        return null;
    }

    public int getCommentCount(){
        int size =0;
        for (List<Comment> value: comments.values())
            size += value.size();
        return size;
    }

    public int getCommentCountByUser(User user){
        if (comments.containsKey(user))
            return comments.get(user).size();
        else
            return 0;
    }


}
class Comment extends Post{

    public Comment(String content) {
        super(content);
    }
}
class SocialNetwork{
    private static Map<User,ArrayList<Post>> postByUsers = new HashMap<>();

    public static User register(String username, String email){
        User user = new User(username, email);
        if (postByUsers.containsKey(user)){
            return null;
        }else{
            postByUsers.put(user,new ArrayList<>());
            return user;
        }
    }

    public static Post post(User user, String content){
        if (postByUsers.containsKey(user)){
            Post post = new Post(content);
            postByUsers.get(user).add(post);
            return post;
        }else {
            return null;
        }
    }



    public static User getUser(String email){
        int hash  = Objects.hash(email);

        for (User user  : postByUsers.keySet()){
            if (user.hashCode() == hash)
                return user;
        }
        return null;
    }

    public static Set<Post> getFeed(User user){
        Set<Post> posts = new HashSet<>();
        for (User followed : user.getFollowing()){
            posts.addAll(postByUsers.getOrDefault(followed,new ArrayList<>()));
        }
        return posts;
    }

    public static Map<User,String> search(String keywords){
        Map<User,String> userStringMap = new HashMap<>();
        for(User user : postByUsers.keySet()){
            if(user.getUsername().contains(keywords))
                userStringMap.put(user, user.getUsername());
        }
        return userStringMap;
    }

    public static <K, V> Map<V, Set<K>> reverseMap(Map<K, V> map){
        Map<V,Set<K>> reversed = new HashMap<>();
        for (Map.Entry<K,V> entry: map.entrySet()){
            K key = entry.getKey();;
            V value = entry.getValue();;

            Set<K> keys = reversed.getOrDefault(value,new HashSet<>());
            keys.add(key);
            reversed.put(value,keys);
        }
        return reversed;
    }


}
