package es.upm.miw.gestordespotify.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import es.upm.miw.gestordespotify.R;
import es.upm.miw.gestordespotify.artist.ViewArtist;
import es.upm.miw.gestordespotify.model.api.APISpotify;
import es.upm.miw.gestordespotify.model.api.Types;
import es.upm.miw.gestordespotify.model.api.pojo.searchartists.SearchArtist;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends Activity {

    public final static String URI = "https://api.spotify.com/";

    public final static String TAG = "MIWUPM";

    private ListView listView;

    private APISpotify service;

    private SearchArtist result;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URI)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(APISpotify.class);
        listView = (ListView) findViewById(R.id.listView);
        listView.setFastScrollEnabled(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), ViewArtist.class);
                intent.putExtra(ViewArtist.TAG_BUNDLE,result.getArtists().getItems().get(i));
                startActivity(intent);
            }
        });
    }


    public void buscarSerie(View v){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URI)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(APISpotify.class);
        //Log.i(TAG,((EditText) findViewById(R.id.text)).getText().toString());
        final Call<SearchArtist> listaSeries = service.searchArtist(((EditText) findViewById(R.id.text)).getText().toString(), Types.ARTIST.toString());
        listaSeries.enqueue(new Callback<SearchArtist>() {
            @Override
            public void onResponse(Call<SearchArtist> call, Response<SearchArtist> response) {
                result = response.body();
                MyAdapter myAdapter = new MyAdapter(
                        getBaseContext(),
                        R.layout.element,
                        result.getArtists().getItems()
                );
                listView.setAdapter(myAdapter);
                Log.i(TAG,result.toString());
            }

            @Override
            public void onFailure(Call<SearchArtist> call, Throwable t) {
                Log.e(TAG,t.toString());
            }
        });
    }
}
