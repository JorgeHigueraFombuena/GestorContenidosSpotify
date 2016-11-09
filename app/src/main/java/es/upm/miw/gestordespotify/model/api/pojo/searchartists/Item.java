package es.upm.miw.gestordespotify.model.api.pojo.searchartists;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.ToStringBuilder;

import es.upm.miw.gestordespotify.model.api.pojo.ExternalUrls;
import es.upm.miw.gestordespotify.model.api.pojo.Followers;
import es.upm.miw.gestordespotify.model.api.pojo.Image;

@Generated("org.jsonschema2pojo")
public class Item implements Parcelable {

    @SerializedName("external_urls")
    @Expose
    private ExternalUrls externalUrls;
    @SerializedName("followers")
    @Expose
    private Followers followers;
    @SerializedName("genres")
    @Expose
    private List<String> genres = new ArrayList<String>();
    @SerializedName("href")
    @Expose
    private String href;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("images")
    @Expose
    private List<Image> images = new ArrayList<Image>();
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("popularity")
    @Expose
    private Integer popularity;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("uri")
    @Expose
    private String uri;

    /**
     *
     * @return
     * The externalUrls
     */
    public ExternalUrls getExternalUrls() {
        return externalUrls;
    }

    /**
     *
     * @param externalUrls
     * The external_urls
     */
    public void setExternalUrls(ExternalUrls externalUrls) {
        this.externalUrls = externalUrls;
    }

    /**
     *
     * @return
     * The followers
     */
    public Followers getFollowers() {
        return followers;
    }

    /**
     *
     * @param followers
     * The followers
     */
    public void setFollowers(Followers followers) {
        this.followers = followers;
    }

    /**
     *
     * @return
     * The genres
     */
    public List<String> getGenres() {
        return genres;
    }

    /**
     *
     * @param genres
     * The genres
     */
    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

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
     * The id
     */
    public String getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The images
     */
    public List<Image> getImages() {
        return images;
    }

    /**
     *
     * @param images
     * The images
     */
    public void setImages(List<Image> images) {
        this.images = images;
    }

    /**
     *
     * @return
     * The name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     * The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     * The popularity
     */
    public Integer getPopularity() {
        return popularity;
    }

    /**
     *
     * @param popularity
     * The popularity
     */
    public void setPopularity(Integer popularity) {
        this.popularity = popularity;
    }

    /**
     *
     * @return
     * The type
     */
    public String getType() {
        return type;
    }

    /**
     *
     * @param type
     * The type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     *
     * @return
     * The uri
     */
    public String getUri() {
        return uri;
    }

    /**
     *
     * @param uri
     * The uri
     */
    public void setUri(String uri) {
        this.uri = uri;
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
        parcel.writeTypedObject(externalUrls, i);
        parcel.writeTypedObject(followers, i);
        parcel.writeStringList(genres);
        parcel.writeString(href);
        parcel.writeString(id);
        parcel.writeTypedList(images);
        parcel.writeString(name);
        parcel.writeInt(popularity);
        parcel.writeString(type);
        parcel.writeString(uri);
    }

    protected Item(Parcel in){
        this.externalUrls = in.readTypedObject(ExternalUrls.CREATOR);
        this.followers = in.readTypedObject(Followers.CREATOR);
        in.readStringList(genres);
        this.href = in.readString();
        this.id = in.readString();
        in.readTypedList(images, Image.CREATOR);
        this.name = in.readString();
        this.popularity = in.readInt();
        this.type = in.readString();
        this.uri = in.readString();
    }

    public static final Parcelable.Creator<Item> CREATOR = new Parcelable.Creator<Item>(){
        @Override
        public Item createFromParcel(Parcel parcel) {
            return new Item(parcel);
        }

        @Override
        public Item[] newArray(int i) {
            return new Item[i];
        }
    };
}