package cat.itb.clonreddit.models;

public class SubReddit {
    private String id, title, imgUrl;

    public SubReddit() {
    }

    public SubReddit(String title, String imgUrl) {
        this.title = title;
        this.imgUrl = imgUrl;;
    }


    /*+++++ CONSTRUCTOR LOCAL +++++++*/



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
}
