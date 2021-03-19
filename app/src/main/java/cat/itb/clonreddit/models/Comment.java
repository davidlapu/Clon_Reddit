package cat.itb.clonreddit.models;

import java.util.Date;

public class Comment {
    private String id, text;
    private User user;
    private Date date;
    private int upVotes;


    public Comment(String text, User user) {
        this.text = text;
        this.user = user;
        this.date = new Date();
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
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
