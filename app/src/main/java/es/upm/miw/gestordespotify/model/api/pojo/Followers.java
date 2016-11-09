package es.upm.miw.gestordespotify.model.api.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Generated("org.jsonschema2pojo")
public class Followers implements Parcelable{

    @SerializedName("href")
    @Expose
    private String href;
    @SerializedName("total")
    @Expose
    private Integer total;

    /**
     *
     * @return
     * The href
     */
    public String getHref() {
        return href;
    }

    /**
     *
     * @param href
     * The href
     */
    public void setHref(String href) {
        this.href = href;
    }

    /**
     *
     * @return
     * The total
     */
    public Integer getTotal() {
        return total;
    }

    /**
     *
     * @param total
     * The total
     */
    public void setTotal(Integer total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(href);
        parcel.writeInt(total);
    }

    protected Followers(Parcel in){
        this.href = in.readString();
        this.total = in.readInt();
    }

    public static final Parcelable.Creator<Followers> CREATOR = new Parcelable.Creator<Followers>(){
        @Override
        public Followers createFromParcel(Parcel parcel) {
            return new Followers(parcel);
        }

        @Override
        public Followers[] newArray(int i) {
            return new Followers[i];
        }
    };
}