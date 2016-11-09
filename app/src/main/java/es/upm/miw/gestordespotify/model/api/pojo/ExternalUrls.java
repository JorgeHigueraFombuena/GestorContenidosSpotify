package es.upm.miw.gestordespotify.model.api.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Generated("org.jsonschema2pojo")
public class ExternalUrls implements Parcelable {

    @SerializedName("spotify")
    @Expose
    private String spotify;

    /**
     *
     * @return
     * The spotify
     */
    public String getSpotify() {
        return spotify;
    }

    /**
     *
     * @param spotify
     * The spotify
     */
    public void setSpotify(String spotify) {
        this.spotify = spotify;
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
        parcel.writeString(spotify);
    }

    protected ExternalUrls(Parcel in){
        this.spotify = in.readString();
    }

    public static final Parcelable.Creator<ExternalUrls> CREATOR = new Parcelable.Creator<ExternalUrls>() {
        @Override
        public ExternalUrls createFromParcel(Parcel source) {
            return new ExternalUrls(source);
        }

        @Override
        public ExternalUrls[] newArray(int size) {
            return new ExternalUrls[size];
        }
    };
}