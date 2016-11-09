package es.upm.miw.gestordespotify.model.bd.entities;

import android.os.Parcel;
import android.os.Parcelable;

public class Album implements Parcelable {

    private int id;

    private String idApi;

    private int idArtist;

    private String albumName;

    private String image;


    private double rating;

    public Album(int id, String idApi, int idArtist, String albumName, String image, double rating) {
        this.id = id;
        this.idApi = idApi;
        this.idArtist = idArtist;
        this.albumName = albumName;
        this.image = image;
        this.rating = rating;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIdApi() {
        return idApi;
    }

    public void setIdApi(String idApi) {
        this.idApi = idApi;
    }

    public int getIdArtist() {
        return idArtist;
    }

    public void setIdArtist(int idArtist) {
        this.idArtist = idArtist;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Album album = (Album) o;

        if (idArtist != album.idArtist) return false;
        return idApi != null ? idApi.equals(album.idApi) : album.idApi == null;

    }

    @Override
    public int hashCode() {
        int result = idApi != null ? idApi.hashCode() : 0;
        result = 31 * result + idArtist;
        return result;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(idApi);
        parcel.writeInt(idArtist);
        parcel.writeString(albumName);
        parcel.writeString(image);
        parcel.writeDouble(rating);
    }

    protected Album(Parcel in){
        this.id = in.readInt();
        this.idApi = in.readString();
        this.idArtist = in.readInt();
        this.albumName = in.readString();
        this.image = in.readString();
        this.rating = in.readDouble();
    }

    public static final Parcelable.Creator<Album> CREATOR = new Parcelable.Creator<Album>(){
        @Override
        public Album createFromParcel(Parcel parcel) {
            return new Album(parcel);
        }

        @Override
        public Album[] newArray(int i) {
            return new Album[i];
        }
    };

}
