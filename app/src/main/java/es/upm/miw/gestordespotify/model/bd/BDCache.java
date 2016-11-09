package es.upm.miw.gestordespotify.model.bd;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import es.upm.miw.gestordespotify.model.api.pojo.searchartists.Item;
import es.upm.miw.gestordespotify.model.bd.entities.Artist;

public class BDCache {

    private BDSpotify bd;

    public BDCache(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        this.bd = new BDSpotify(context, name, factory, version);
    }

    public Artist cachedArtist(Item artist){
        Artist result = bd.getArtistByName(artist.getName());
        if(result == null){
            result = bd.insertArtist(
                    artist.getId(),
                    artist.getName(),
                    artist.getPopularity(),
                    0);
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



}
