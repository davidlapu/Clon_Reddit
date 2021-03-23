package cat.itb.clonreddit.models;

import java.io.Serializable;

//TODO Poner parcelable
public class Post implements Serializable {
    private String id;
    private User user;
    private String subRedditID ,time, title, imgUrl, text = null;
    private int numAwards, upVotes, commentsNum;

    public Post() {
    }

    public Post(String subRedditId, User user, String time, String title, String imgUrl, int numAwards, int upVotes, int commentsNum) {
        this.user = user;
        this.time = time;
        this.title = title;
        this.imgUrl = imgUrl;
        this.numAwards = numAwards;
        this.upVotes = upVotes;
        this.commentsNum = commentsNum;
        this.subRedditID = subRedditId;
    }

    public Post(String subRedditId, User user, String time, String title, int numAwards, String text, int upVotes, int commentsNum) {
        this.user = user;
        this.time = time;
        this.title = title;
        this.text = text;
        this.numAwards = numAwards;
        this.upVotes = upVotes;
        this.commentsNum = commentsNum;
        this.subRedditID = subRedditId;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public int getNumAwards() {
        return numAwards;
    }

    public void setNumAwards(int numAwards) {
        this.numAwards = numAwards;
    }

    public int getUpVotes() {
        return upVotes;
    }

    public void setUpVotes(int upVotes) {
        this.upVotes = upVotes;
    }

    public int getCommentsNum() {
        return commentsNum;
    }

    public void setCommentsNum(int commentsNum) {
        this.commentsNum = commentsNum;
    }

    public String getSubRedditID() {
        return subRedditID;
    }

    public void setSubRedditID(String subRedditID) {
        this.subRedditID = subRedditID;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
