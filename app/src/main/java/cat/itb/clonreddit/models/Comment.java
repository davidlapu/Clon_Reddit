package cat.itb.clonreddit.models;

public class Comment {
    private String id, text;
    private User user;
    private long date;
    private int upVotes;


    public Comment(String text, User user, long date) {
        this.text = text;
        this.user = user;
        this.date = date;
        this.upVotes = 0;
    }

    public Comment(String text, User user) {
        this.text = text;
        this.user = user;
        this.date = System.currentTimeMillis();
        this.upVotes = 0;
    }

    public Comment() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public int getUpVotes() {
        return upVotes;
    }

    public void setUpVotes(int upVotes) {
        this.upVotes = upVotes;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
