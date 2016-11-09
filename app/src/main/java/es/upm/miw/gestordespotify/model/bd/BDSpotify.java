package es.upm.miw.gestordespotify.model.bd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import es.upm.miw.gestordespotify.model.bd.entities.Artist;

import static es.upm.miw.gestordespotify.model.bd.SpotifyContract.artistTable;

public class BDSpotify extends SQLiteOpenHelper {


    public BDSpotify(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_SQL = "CREATE TABLE " + artistTable.TABLE_NAME + "( " +
                artistTable.COL_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                artistTable.COL_NAME_ID_API + " TEXT, " +
                artistTable.COL_NAME_ARTIST_NAME + " TEXT, " +
                artistTable.COL_NAME_POPULARITY+ " INTEGER, " +
                artistTable.COL_NAME_RATING + " DOUBLE " +
                " );";
        sqLiteDatabase.execSQL(CREATE_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String consultaSQL = "DROP TABLE IF EXISTS " + artistTable.TABLE_NAME;
        sqLiteDatabase.execSQL(consultaSQL);
    }

    public Artist insertArtist(String idApi, String artistName, int popularity, double rating){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(artistTable.COL_NAME_ID_API, idApi);
        contentValues.put(artistTable.COL_NAME_ARTIST_NAME, artistName);
        contentValues.put(artistTable.COL_NAME_POPULARITY, popularity);
        contentValues.put(artistTable.COL_NAME_RATING, rating);
        long id = db.insert(artistTable.TABLE_NAME, null, contentValues);
        return getArtistById(id);
    }

    public Artist getArtistByName(String artistName){
        Artist artist = null;
        SQLiteDatabase db = getReadableDatabase();
        String where = artistTable.COL_NAME_ARTIST_NAME + "= ?";
        Cursor cursor = db.query(
                artistTable.TABLE_NAME,
                null,
                where,
                new String[]{ artistName},
                null, null, null);

        if (cursor.moveToFirst()) {
            artist = this.createArtist(cursor);
            cursor.close();
        }
        return artist;
    }

    public Artist getArtistById(long id){
        Artist artist = null;
        SQLiteDatabase db = getReadableDatabase();
        String where = artistTable.COL_NAME_ID + "= ?";
        Cursor cursor = db.query(
                artistTable.TABLE_NAME,
                null,
                where,
                new String[]{ Long.toString(id) },
                null, null, null);

        if (cursor.moveToFirst()) {
            artist = this.createArtist(cursor);
            cursor.close();
        }
        return artist;
    }

    private Artist createArtist(Cursor c){
        return new Artist(
                c.getInt(c.getColumnIndex(artistTable.COL_NAME_ID)),
                c.getString(c.getColumnIndex(artistTable.COL_NAME_ID_API)),
                c.getString(c.getColumnIndex(artistTable.COL_NAME_ARTIST_NAME)),
                c.getInt(c.getColumnIndex(artistTable.COL_NAME_POPULARITY)),
                c.getDouble(c.getColumnIndex(artistTable.COL_NAME_RATING))
        );
    }
}
