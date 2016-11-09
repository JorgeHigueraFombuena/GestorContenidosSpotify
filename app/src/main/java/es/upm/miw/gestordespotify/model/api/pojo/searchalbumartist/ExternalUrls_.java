package es.upm.miw.gestordespotify.model.api.pojo.searchalbumartist;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Generated("org.jsonschema2pojo")
public class ExternalUrls_ {

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

}