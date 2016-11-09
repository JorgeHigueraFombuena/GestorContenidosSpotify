package es.upm.miw.gestordespotify.model.api.pojo.searchartists;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Generated("org.jsonschema2pojo")
public class SearchArtist {

    @SerializedName("artists")
    @Expose
    private Artists artists;

    /**
     *
     * @return
     * The artists
     */
    public Artists getArtists() {
        return artists;
    }

    /**
     *
     * @param artists
     * The artists
     */
    public void setArtists(Artists artists) {
        this.artists = artists;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}