package es.upm.miw.gestordespotify.model.bd;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import es.upm.miw.gestordespotify.model.api.pojo.searchartists.Item;
import es.upm.miw.gestordespotify.model.bd.entities.Artist;

public class BDCache {

    private BDSpotify bd;

    public BDCache(Context context){
        this.bd = new BDSpotify(context );
    }

    public Artist cachedArtist(Item artist){
        Artist result = bd.getArtistByName(artist.getName());
        if(result == null){
            result = bd.insertArtist(
                    artist.getId(),
                    artist.getName(),
                    artist.getImages().isEmpty() ? "" : artist.getImages().get(0).getUrl(),
                    artist.getPopularity(),
                    0);
        }
        else if(result.getImage().isEmpty() && !artist.getImages().isEmpty()){
            bd.updateArtist(
                    String.valueOf(result.getId()),
                    result.getIdApi(),
                    result.getArtistName(),
                    artist.getImages().get(0).getUrl(),
                    result.getPopularity(),
                    result.getRating());
        }
        return result;
    }

    public List<Artist> cachedArtistList(List<Item> list){
        List<Artist> result = new ArrayList<>();
        for(Item it : list){
            result.add(cachedArtist(it));
        }
        return result;
    }

    public Artist getArtistByName(String artistName){
        return bd.getArtistByName(artistName);
    }

    public boolean exitstArtist(String artistName){
        return bd.getArtistByName(artistName) != null;
    }

    public SQLiteDatabase getReadableDatabase() {
        return bd.getReadableDatabase();
    }

}
