package cat.itb.clonreddit.models;

import android.os.Parcel;
import android.os.Parcelable;

public class SubReddit implements Parcelable {
    private String id, title, imgUrl;

    public SubReddit() {
    }

    public SubReddit(String title, String imgUrl) {
        this.title = title;
        this.imgUrl = imgUrl;;
    }


    /*+++++ CONSTRUCTOR LOCAL +++++++*/


    protected SubReddit(Parcel in) {
        id = in.readString();
        title = in.readString();
        imgUrl = in.readString();
    }

    public static final Creator<SubReddit> CREATOR = new Creator<SubReddit>() {
        @Override
        public SubReddit createFromParcel(Parcel in) {
            return new SubReddit(in);
        }

        @Override
        public SubReddit[] newArray(int size) {
            return new SubReddit[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(title);
        dest.writeString(imgUrl);
    }
}
