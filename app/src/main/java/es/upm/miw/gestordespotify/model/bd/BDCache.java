package es.upm.miw.gestordespotify.model.bd;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.jar.Attributes;

import es.upm.miw.gestordespotify.R;
import es.upm.miw.gestordespotify.main.MainActivity;
import es.upm.miw.gestordespotify.main.MyAdapter;
import es.upm.miw.gestordespotify.model.api.APISpotify;
import es.upm.miw.gestordespotify.model.api.Types;
import es.upm.miw.gestordespotify.model.api.pojo.searchalbumartist.SearchAlbumArtist;
import es.upm.miw.gestordespotify.model.api.pojo.searchartists.Item;
import es.upm.miw.gestordespotify.model.api.pojo.searchartists.SearchArtist;
import es.upm.miw.gestordespotify.model.bd.entities.Album;
import es.upm.miw.gestordespotify.model.bd.entities.Artist;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static es.upm.miw.gestordespotify.main.MainActivity.TAG;

public class BDCache {

    public final static String URI = "https://api.spotify.com/";

    private APISpotify service;

    private BDSpotify bd;

    private Context context;

    private IBDCache activity;

    private int count = 0;

    public BDCache(Context context, IBDCache activity){
        this.context = context;
        this.activity = activity;
        this.bd = new BDSpotify(context);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URI)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(APISpotify.class);
    }

    public List<Artist> getArtistList(final String name){
        List<Artist> result;

        result = bd.getAllArtistsLikeName(name);

        Call<SearchArtist> listaArtistas = service.searchArtist(name, Types.ARTIST.toString());
        listaArtistas.enqueue(new Callback<SearchArtist>() {
            @Override
            public void onResponse(Call<SearchArtist> call, Response<SearchArtist> response) {
                Log.i(MainActivity.TAG, count +" Acabo de entrar " + response.body().getArtists().getItems().size());
                count++;
                SearchArtist result = response.body();
                boolean update = cachedArtistList(result.getArtists().getItems());
                if(update && activity != null){
                    activity.updateView(bd.getAllArtistsLikeName(name));
                }
            }

            @Override
            public void onFailure(Call<SearchArtist> call, Throwable t) {
                Log.e(TAG,t.toString());
            }
        });

        return result;
    }

    private boolean cachedArtist(Item artist){
        boolean res = bd.checkIfArtistExist(artist.getId());
        if(!res){
            bd.insertArtist(
                    artist.getId(),
                    artist.getName(),
                    artist.getImages().isEmpty() ? "" : artist.getImages().get(0).getUrl(),
                    artist.getPopularity(),
                    0);
            res = true;
        }
        else {
            Artist result = bd.getArtistByName(artist.getName());
            if (result != null && result.getImage().isEmpty() && !artist.getImages().isEmpty()) {
                bd.updateArtist(
                        result.getId(),
                        result.getIdApi(),
                        result.getArtistName(),
                        artist.getImages().get(0).getUrl(),
                        result.getPopularity(),
                        result.getRating());
                res = true;
            }
            else{
                res = false;
            }
        }
        return res;
    }

    private boolean cachedArtistList(List<Item> list){
        //List<Artist> result = new ArrayList<>();
        boolean res = false;
        for(Item it : list){
            //result.add(cachedArtist(it));
            res = cachedArtist(it) || res;
        }
        //return result;
        return res;
    }

    public Artist getArtistByName(String artistName){
        return bd.getArtistByName(artistName);
    }

    public boolean exitstArtist(String artistName){
        return bd.getArtistByName(artistName) != null;
    }

    public boolean cachedAlbum(es.upm.miw.gestordespotify.model.api.pojo.searchalbumartist.Item album, int idArtist){
        boolean res = bd.checkIfAlbumExist(album.getId());
        if(!res){
            bd.insertAlbum(
                    album.getId(),
                    idArtist,
                    album.getName(),
                    album.getImages().isEmpty() ? "" : album.getImages().get(0).getUrl(),
                    0);
            res = true;
        }
        else {
            Album result = bd.getAlbumByName(album.getName());
            if (result != null && result.getImage().isEmpty() && !album.getImages().isEmpty()) {
                bd.updateAlbum(
                        result.getId(),
                        result.getIdApi(),
                        result.getIdArtist(),
                        result.getAlbumName(),
                        album.getImages().get(0).getUrl(),
                        result.getRating());
                res = true;
            }
            else{
                res=false;
            }
        }
        return res;
    }

    public boolean cachedAlbumList(List<es.upm.miw.gestordespotify.model.api.pojo.searchalbumartist.Item> list, int idArtist){
        List<Album> result = new ArrayList<>();
        boolean res = false;
        for(es.upm.miw.gestordespotify.model.api.pojo.searchalbumartist.Item it : list){
            res = cachedAlbum(it, idArtist) || res;
        }
        return res;
    }

    public Album getAlbumByName(String albumName){
        return bd.getAlbumByName(albumName);
    }

    public List<Album> getAlbumsOfAnArtist(final int idArtist, String idApi){
        List<Album> list;

        list = bd.getAllAlbumsOfArtist(idArtist);

        Call<SearchAlbumArtist> listaAlbums = service.seartAlbumsOfArtist(idApi);
        listaAlbums.enqueue(new Callback<SearchAlbumArtist>() {
            @Override
            public void onResponse(Call<SearchAlbumArtist> call, Response<SearchAlbumArtist> response) {

                SearchAlbumArtist result = response.body();
                Log.i(MainActivity.TAG, count +" Acabo de entrar " + response.body().getItems().size());
                count++;
                boolean update = cachedAlbumList(result.getItems(),idArtist);
                if(update && activity != null){
                    activity.updateView(bd.getAllAlbumsOfArtist(idArtist));
                }
            }

            @Override
            public void onFailure(Call<SearchAlbumArtist> call, Throwable t) {
                Log.e(TAG,t.toString());
            }
        });
        return list;
    }

    public boolean exitstAlbum(String albumName){
        return bd.getAlbumByName(albumName) != null;
    }

    public SQLiteDatabase getReadableDatabase() {
        return bd.getReadableDatabase();
    }

    public SQLiteDatabase getWritableDatabase(){
        return bd.getWritableDatabase();
    }

}
