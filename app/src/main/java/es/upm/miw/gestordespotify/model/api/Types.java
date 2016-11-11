package es.upm.miw.gestordespotify.model.api;

/**
 * Created by Jorge on 08/11/2016.
 */

public enum Types {
    ARTIST("artist"),
    ALBUM("album");

    private String message;

    Types(String message){
        this.message = message;
    }

    @Override
    public String toString(){
        return this.message;
    }
}
