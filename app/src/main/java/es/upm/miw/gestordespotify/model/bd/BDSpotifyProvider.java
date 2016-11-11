package es.upm.miw.gestordespotify.model.bd;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import es.upm.miw.gestordespotify.main.MainActivity;
import es.upm.miw.gestordespotify.model.bd.entities.Album;


public class BDSpotifyProvider extends ContentProvider {

    private static final String AUTHORITY =
            BDSpotifyProvider.class.getPackage().getName() + ".provider";
    private static final String ENTITY_ARTIST = "artist";
    private static final String ENTITY_ALBUM = "album";


    private static final String URI_ARTISTS = "content://" + AUTHORITY + "/" + ENTITY_ARTIST;
    private static final Uri CONTENT_URI_ARTISTS = Uri.parse(URI_ARTISTS);

    private static final String URI_ALBUMS = "content://" + AUTHORITY + "/" + ENTITY_ALBUM;
    private static final Uri CONTENT_URI_ALBUMS = Uri.parse(URI_ALBUMS);


    private static final int ID_URI_ARTIST      = 1;
    private static final int ID_URI_UNIQUE_ARTIST = 2;
    private static final int ID_URI_ALBUM      = 3;
    private static final int ID_URI_UNIQUE_ALBUM = 4;

    private static final UriMatcher uriMatcher;


    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, ENTITY_ARTIST, ID_URI_ARTIST);
        uriMatcher.addURI(AUTHORITY, ENTITY_ARTIST + "/#", ID_URI_UNIQUE_ARTIST);
        uriMatcher.addURI(AUTHORITY, ENTITY_ALBUM, ID_URI_ALBUM);
        uriMatcher.addURI(AUTHORITY, ENTITY_ALBUM + "/#", ID_URI_UNIQUE_ALBUM);
    }

    private BDCache bd;

    @Override
    public boolean onCreate() {
        bd = new BDCache(getContext(), null);
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        // Si es una consulta a un ID concreto se añade el WHERE
        String where = getWhere(uri, selection);
        String tableName = getTableName(uri);

        SQLiteDatabase db = bd.getReadableDatabase();
        String[] aux = null;
        aux = selectionArgs;
        if(selection.contains("like")){
            selectionArgs = null;
        }

        Cursor c = db.query(
                tableName,
                projection,
                where,
                selectionArgs,
                null, null, sortOrder);
        if(!c.moveToFirst()){
            switch (tableName){
                case SpotifyContract.artistTable.TABLE_NAME:
                    List<String> stringList = Arrays.asList(where.split("like"));
                    for (int i = 0; i < stringList.size(); i++) {
                        stringList.set(i, stringList.get(i).trim());
                    }
                    Log.i(MainActivity.TAG,where);
                    int pos = stringList.indexOf(SpotifyContract.artistTable.COL_NAME_ARTIST_NAME);
                    if(pos>-1){
                        new BDCache(getContext(),null).getArtistList(aux[pos]);
                    }
                    break;
                case SpotifyContract.albumTable.TABLE_NAME:
                    List<String> stringList2 = Arrays.asList(where.split("="));
                    int pos2 = stringList2.indexOf(SpotifyContract.albumTable.COL_NAME_ALBUM_NAME);
                    int pos3 = stringList2.indexOf(SpotifyContract.albumTable.COL_NAME_ARTIST_ID);
                    Log.i(MainActivity.TAG,where);
                    Cursor c2 = db.query(
                            SpotifyContract.artistTable.TABLE_NAME,
                            null,
                            "_id=?",
                            new String[]{selectionArgs[pos3]},
                            null, null, null);
                    if(c2.moveToFirst()) {
                        String idApi = c2.getString(c2.getColumnIndex(SpotifyContract.artistTable.COL_NAME_ID_API));
                        Log.i(MainActivity.TAG,"Encuentro algo " + idApi);
                        List<Album> list = new BDCache(getContext(), null).getAlbumsOfAnArtist(
                                Integer.parseInt(selectionArgs[pos3]),
                                idApi
                        );

                    }
                    break;
                default:
            }
        }
        return c;
    }

    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)) {
            case ID_URI_ARTIST:
                return "vnd.android.cursor.dir/vnd.miw.artist";
            case ID_URI_UNIQUE_ARTIST:
                return "vnd.android.cursor.item/vnd.miw.artist";
            case ID_URI_ALBUM:
                return "vnd.android.cursor.dir/vnd.miw.album";
            case ID_URI_UNIQUE_ALBUM:
                return "vnd.android.cursor.item/vnd.miw.album";
            default:
                return null;
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long regId;

        String tableName = getTableName(uri);

        SQLiteDatabase db = bd.getWritableDatabase();
        regId = db.insert(
                tableName,
                null,
                values
        );

        return ContentUris.withAppendedId(getUri(uri), regId);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        String where = getWhere(uri, selection);
        String tableName = getTableName(uri);

        SQLiteDatabase db = bd.getWritableDatabase();

        return db.delete(
                tableName,
                where,
                selectionArgs
        );
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        String where = getWhere(uri, selection);
        String tableName = getTableName(uri);

        SQLiteDatabase db = bd.getWritableDatabase();

        return db.update(
                tableName,
                values,
                where,
                selectionArgs
        );
    }

    private String getTableName(Uri uri){
        switch (uriMatcher.match(uri)){
            case ID_URI_ARTIST:
            case ID_URI_UNIQUE_ARTIST:
                return SpotifyContract.artistTable.TABLE_NAME;
            case ID_URI_ALBUM:
            case ID_URI_UNIQUE_ALBUM:
                return SpotifyContract.albumTable.TABLE_NAME;
            default:
                return null;
        }
    }

    private String getWhere(Uri uri, String where){
        switch (uriMatcher.match(uri)){
            case ID_URI_UNIQUE_ARTIST:
            case ID_URI_UNIQUE_ALBUM:
                return "_id = " + uri.getLastPathSegment();
            default:
                return where;
        }
    }

    private Uri getUri(Uri uri){
        switch (uriMatcher.match(uri)){
            case ID_URI_ARTIST:
            case ID_URI_UNIQUE_ARTIST:
                return CONTENT_URI_ARTISTS;
            case ID_URI_ALBUM:
            case ID_URI_UNIQUE_ALBUM:
                return CONTENT_URI_ALBUMS;
            default:
                return null;
        }
    }
}
