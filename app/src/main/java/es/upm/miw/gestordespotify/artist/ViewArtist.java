package es.upm.miw.gestordespotify.artist;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import es.upm.miw.gestordespotify.R;
import es.upm.miw.gestordespotify.main.MainActivity;
import es.upm.miw.gestordespotify.model.api.APISpotify;
import es.upm.miw.gestordespotify.model.bd.BDCache;
import es.upm.miw.gestordespotify.model.bd.IBDCache;
import es.upm.miw.gestordespotify.model.bd.entities.Album;
import es.upm.miw.gestordespotify.model.bd.entities.Artist;

public class ViewArtist extends Activity implements IBDCache<Album> {

    public static String TAG_BUNDLE = "artist";

    private APISpotify service;

    private ListView listView;

    private BDCache bdCache;

    private List<Album> list;

    private Artist artist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_artist);

        bdCache = new BDCache(getBaseContext(), this);

        artist =  getIntent().getParcelableExtra(TAG_BUNDLE);
        setTitle(artist.getArtistName());
        ((TextView)findViewById(R.id.artistNameArtist)).setText(artist.getArtistName());
        Picasso.with(getBaseContext()).load(artist.getImage()).into((ImageView) findViewById(R.id.imageArtist));

        listView = (ListView) findViewById(R.id.listViewAlbums);
        listView.setFastScrollEnabled(true);

        new AsyncTask<Integer, Integer, Integer>() {
            @Override
            protected Integer doInBackground(Integer... lists) {
                list = bdCache.getAlbumsOfAnArtist(artist.getId(), artist.getIdApi());
                return list.size();
            }
            @Override
            protected void onPostExecute(Integer aVoid) {
                super.onPostExecute(aVoid);
                MyAdapter myAdapter = new MyAdapter(
                        getBaseContext(),
                        R.layout.element_album,
                        list
                );
                listView.setAdapter(myAdapter);
            }
        }.execute();
        /*
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MainActivity.URI)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(APISpotify.class);

        Call<SearchAlbumArtist> albums = service.seartAlbumsOfArtist(artist.getIdApi());

        albums.enqueue(new Callback<SearchAlbumArtist>() {
            @Override
            public void onResponse(Call<SearchAlbumArtist> call, Response<SearchAlbumArtist> response) {
                List<es.upm.miw.gestordespotify.model.api.pojo.searchalbumartist.Item> items = response.body().getItems();

                for(int i = 0; i < items.size(); i++){
                    for(int j = i+1; j < items.size(); j++){
                        if(items.get(i).equals(items.get(j))){
                            items.remove(j);
                        }
                    }
                }

                MyAdapter myAdapter = new MyAdapter(
                        getBaseContext(),
                        R.layout.element_album,
                        items
                );
                listView.setAdapter(myAdapter);
                Log.i(MainActivity.TAG, response.body().toString());
            }

            @Override
            public void onFailure(Call<SearchAlbumArtist> call, Throwable t) {

            }
        });*/
    }


    @Override
    public void updateView(List<Album> list) {
        MyAdapter myAdapter = new MyAdapter(
                getBaseContext(),
                R.layout.element_album,
                list
        );
        listView.setAdapter(myAdapter);
    }
}
