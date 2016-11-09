package es.upm.miw.gestordespotify.model.api;

import es.upm.miw.gestordespotify.model.api.pojo.searchalbumartist.SearchAlbumArtist;
import es.upm.miw.gestordespotify.model.api.pojo.searchartists.SearchArtist;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APISpotify {

    @GET("/v1/search")
    Call<SearchArtist> searchArtist(@Query("q") String q, @Query("type") String type);

    @GET("/v1/artists/{id}/albums")
    Call<SearchAlbumArtist> seartAlbumsOfArtist(@Path("id") String id);

}
