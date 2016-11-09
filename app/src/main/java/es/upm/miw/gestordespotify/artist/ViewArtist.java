package es.upm.miw.gestordespotify.artist;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import es.upm.miw.gestordespotify.R;
import es.upm.miw.gestordespotify.main.MainActivity;
import es.upm.miw.gestordespotify.model.api.APISpotify;
import es.upm.miw.gestordespotify.model.api.pojo.searchartists.Item;
import es.upm.miw.gestordespotify.model.api.pojo.searchalbumartist.SearchAlbumArtist;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ViewArtist extends Activity {

    public static String TAG_BUNDLE = "artist";

    private APISpotify service;

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_artist);

        Item artist =  getIntent().getParcelableExtra(TAG_BUNDLE);
        setTitle(artist.getName());
        ((TextView)findViewById(R.id.artistNameArtist)).setText(artist.getName());
        Picasso.with(getBaseContext()).load(artist.getImages().get(0).getUrl()).into((ImageView) findViewById(R.id.imageArtist));

        listView = (ListView) findViewById(R.id.listViewAlbums);
        listView.setFastScrollEnabled(true);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MainActivity.URI)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(APISpotify.class);

        Call<SearchAlbumArtist> albums = service.seartAlbumsOfArtist(artist.getId());

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
        });
    }



}
