package es.upm.miw.gestordespotify.model.bd.entities;

/**
 * Created by Jorge on 08/11/2016.
 */

public class Artist {

    private int id;

    private String idApi;

    private String artistName;

    private int popularity;

    private double rating;

    public Artist(int id, String idApi, String artistName, int popularity, double rating) {
        this.id = id;
        this.idApi = idApi;
        this.artistName = artistName;
        this.popularity = popularity;
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

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public int getPopularity() {
        return popularity;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
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

        Artist artist = (Artist) o;

        if (id != artist.id) return false;
        if (popularity != artist.popularity) return false;
        if (Double.compare(artist.rating, rating) != 0) return false;
        if (idApi != null ? !idApi.equals(artist.idApi) : artist.idApi != null) return false;
        return artistName != null ? artistName.equals(artist.artistName) : artist.artistName == null;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id;
        result = 31 * result + (idApi != null ? idApi.hashCode() : 0);
        result = 31 * result + (artistName != null ? artistName.hashCode() : 0);
        result = 31 * result + popularity;
        temp = Double.doubleToLongBits(rating);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
