package cat.itb.clonreddit.models;

public class SubReddit {
    private String id, title, imgUrl;
    private int imageId;

    public SubReddit() {
    }

    public SubReddit(String id, String title, String imgUrl) {
        this.id = id;
        this.title = title;
        this.imgUrl = imgUrl;;
    }


    /*+++++ CONSTRUCTOR LOCAL +++++++*/
    public SubReddit(String title,int imageId) {
        this.title = title;
        this.imageId = imageId;
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

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }
}
