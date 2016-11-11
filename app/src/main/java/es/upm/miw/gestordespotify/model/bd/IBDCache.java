package es.upm.miw.gestordespotify.model.bd;

import java.util.List;

import es.upm.miw.gestordespotify.model.bd.entities.Artist;

/**
 * Created by Jorge on 10/11/2016.
 */

public interface IBDCache<E> {

    public void updateView(List<E> list);

}
