package es.upm.miw.gestordespotify.main;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import es.upm.miw.gestordespotify.R;
import es.upm.miw.gestordespotify.model.api.pojo.searchartists.Item;


public class MyAdapter extends ArrayAdapter<Item> {

    private Context context;
    private int resourceId;
    private List objects;

    MyAdapter(Context context, int resource, List objects) {
        super(context, resource, objects);
        this.context = context;
        this.objects = objects;
        this.resourceId = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        RelativeLayout vista;
        if(convertView != null){
            vista = (RelativeLayout) convertView;
        }
        else {
            LayoutInflater inf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            vista = (RelativeLayout) inf.inflate(resourceId, parent, false);
        }

        Item item = (Item) objects.get(position);

        if(!item.getImages().isEmpty()){
            Picasso.with(context).load(item.getImages().get(0).getUrl()).into((ImageView) vista.findViewById(R.id.image));
        }
        else{
            Log.i(MainActivity.TAG,"Imágenes vacía");
        }
        ((TextView)vista.findViewById(R.id.artistName)).setText(item.getName());

        return vista;
    }
}
