package pl.cinemaproject.repository.generic;

import java.util.List;
import java.util.Optional;

public interface CrudRepository<T, ID> {

    Optional<T> add(T item);

    List<T> addAll(List<T> items);

    Optional<T> update(T item, ID id);

    Optional<T> findById(ID id);

    List<T> findLast(int limit);

    List<T> findAll();

    List<T> findAllByID(List<ID> ids);

    Optional<T> deleteById(ID id);

    List<T> deleteAll();

}
