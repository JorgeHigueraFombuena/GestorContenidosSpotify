package es.upm.miw.gestordespotify.model.bd;

import android.provider.BaseColumns;

public class SpotifyContract {

    public final static String BD_NAME = "spotify";

    private SpotifyContract(){}

    public static class artistTable implements BaseColumns {

        public final static String TABLE_NAME = "artists";

        public final static String COL_NAME_ID = _ID;
        public final static String COL_NAME_ID_API = "id_api";
        public final static String COL_NAME_ARTIST_NAME = "artist_name";
        public final static String COL_NAME_IMAGE = "image";
        public final static String COL_NAME_POPULARITY = "popularity";
        public final static String COL_NAME_RATING = "rating";

    }

    public static class albumTable implements BaseColumns {

        public final static String TABLE_NAME = "albums";

        public final static String COL_NAME_ID = _ID;
        public final static String COL_NAME_ID_API = "id_api";
        public final static String COL_NAME_ARTIST_ID = "artist_id";
        public final static String COL_NAME_ALBUM_NAME = "album_name";
        public final static String COL_NAME_IMAGE = "image";
        public final static String COL_NAME_RATING = "rating";

    }

}
