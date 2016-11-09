package es.upm.miw.gestordespotify.model.bd;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

/**
 * Created by Jorge on 09/11/2016.
 */

public class BDSpotifyProvider extends ContentProvider {

    private static final String AUTHORITY =
            BDSpotifyProvider.class.getPackage().getName() + ".provider";
    private static final String ENTITY_ARTIST = "artist";


    private static final String uri = "content://" + AUTHORITY + "/" + ENTITY_ARTIST;
    private static final Uri CONTENT_URI = Uri.parse(uri);


    private static final int ID_URI_ARTIST      = 1;
    private static final int ID_URI_UNIQUE_ARTIST = 2;
    private static final UriMatcher uriMatcher;


    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, ENTITY_ARTIST, ID_URI_ARTIST);
        uriMatcher.addURI(AUTHORITY, ENTITY_ARTIST + "/#", ID_URI_UNIQUE_ARTIST);
    }

    private BDCache bd;

    @Override
    public boolean onCreate() {
        bd = new BDCache(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        // Si es una consulta a un ID concreto se añade el WHERE
        String where = selection;
        if (uriMatcher.match(uri) == ID_URI_UNIQUE_ARTIST) {
            where = "_id = " + uri.getLastPathSegment();
        }

        SQLiteDatabase db = bd.getReadableDatabase();

        return db.query(
                SpotifyContract.artistTable.TABLE_NAME,
                projection,
                where,
                selectionArgs,
                null, null, sortOrder);
    }

    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)) {
            case ID_URI_ARTIST:
                return "vnd.android.cursor.dir/vnd.miw.artist";
            case ID_URI_UNIQUE_ARTIST:
                return "vnd.android.cursor.item/vnd.miw.artist";
            default:
                return null;
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        return null;
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        return 0;
    }
}
