package pl.cinemaproject.repository.generic;

import com.google.common.base.CaseFormat;
import org.atteo.evo.inflector.English;
import org.jdbi.v3.core.Jdbi;
import pl.cinemaproject.repository.exception.RepositoryException;

import java.lang.reflect.ParameterizedType;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public abstract class AbstractCrudRepository<T, ID> implements CrudRepository<T, ID> {

    protected final Jdbi jdbi;

    private final String SELECT = "select * from ";
    private final String WHERE = " where id = ";

    public AbstractCrudRepository(DatabaseConnector databaseConnector) {
        this.jdbi = databaseConnector.getJdbi();
    }


    @SuppressWarnings("unchecked")
    private final Class<T> entityType = (Class<T>) ((ParameterizedType) super.getClass().getGenericSuperclass()).getActualTypeArguments()[0];

    @SuppressWarnings("unchecked")
    private final Class<ID> entityId = (Class<ID>) ((ParameterizedType) super.getClass().getGenericSuperclass()).getActualTypeArguments()[1];


    private final String tableName = English.plural(CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, entityType.getSimpleName()));


    @Override
    public Optional<T> add(T item) {

        var insertedRow = jdbi.withHandle(handle -> handle.execute(insertSql(item)));
        if (insertedRow > 0) {

            return findLast(1)
                    .stream()
                    .findFirst();
        }

        return Optional.empty();
    }

    @Override
    public List<T> addAll(List<T> items) {

        var insertedRows = jdbi.withHandle(handle -> handle.execute(insertManySql(items)));
        if (insertedRows > 0) {

            return findLast(insertedRows);
        }
        return List.of();
    }

    @Override
    public Optional<T> update(T item, ID id) {

        var updatedRows = jdbi.withHandle(handle -> handle.execute(updateSql(item, id)));
        if (updatedRows > 0) {

            return findById(id);
        }

        return Optional.empty();
    }

    @Override
    public Optional<T> findById(ID id) {

        var sql = SELECT + tableName + WHERE + id;

        return jdbi.withHandle(handle -> handle
                .createQuery(sql)
                .mapToBean(entityType))
                .findFirst();

    }

    @Override
    public List<T> findLast(int limit) {

        var sql = SELECT + tableName + " order by id desc limit " + limit;

        return jdbi.withHandle(handle -> handle
                .createQuery(sql))
                .mapToBean(entityType)
                .list();

    }

    @Override
    public List<T> findAll() {

        var sql = SELECT + tableName + ";";
        return jdbi.withHandle(handle -> handle
                .createQuery(sql)
                .mapToBean(entityType)
                .list());
    }

    @Override
    public List<T> findAllByID(List<ID> ids) {

        var idsAsString = ids
                .stream()
                .map(Object::toString)
                .collect(Collectors.joining(", "));
        var sql = SELECT + tableName + "where id in (" + idsAsString + ");";
        return jdbi.withHandle(handle -> handle
                .createQuery(sql))
                .mapToBean(entityType)
                .list();
    }

    @Override
    public Optional<T> deleteById(ID id) {

        var item = findById(id);
        var sql = "deleted from " + tableName + WHERE + id;
        var deletedRows = jdbi.withHandle(handle -> handle
                .execute(sql));
        if(deletedRows > 0){
            return item;
        }
        return Optional.empty();
    }

    @Override
    public List<T> deleteAll() {

        var items = findAll();
        var sql = "deleted from " + tableName + " where id >= 1";

        return null;
    }


    private String insertSql(T item) {

        return "insert into " + tableName + " " + getColumnNamesForInsert() + " values " + getColumnValuesForInsert(item) + ";";
    }

    private String insertManySql(List<T> items) {

        return "insert into " + tableName + " " + getColumnNamesForInsert() + " values " + getColumnValuesForInsertMany(items) + ";";
    }


    private String getColumnNamesForInsert() {

        return "( " + Arrays
                .stream(entityType.getDeclaredFields())
                .filter(field -> !field.getName().equalsIgnoreCase("id"))
                .map(field -> CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, field.getName()))
                .collect(Collectors.joining(", ")) + ")";
    }

    private String getColumnValuesForInsert(T item) {

        return "( " + Arrays
                .stream(entityType.getDeclaredFields())
                .filter(field -> !field.getName().equalsIgnoreCase("id"))
                .map(field -> {
                    try {
                        field.setAccessible(true);
                        if (field.getType().equals(String.class) || field.getType().equals(LocalDate.class)) {

                            return "'" + field.get(item) + "'";
                        }
                        return field.get(item).toString();
                    } catch (Exception e) {
                        throw new IllegalStateException(e.getMessage());
                    }
                }).collect(Collectors.joining(", ")) + " ) ";

    }

    private String getColumnValuesForInsertMany(List<T> item) {

        return "(" + item
                .stream()
                .map(this::getColumnValuesForInsert)
                .collect(Collectors.joining(", "));
    }

    private String updateSql(T item, ID id) {

        return "update " + tableName + " set " + getColumnNamesAndValuesForUpdate(item) + " where id = " + id;
    }


    private String getColumnNamesAndValuesForUpdate(T item) {

        return Arrays
                .stream(entityType.getDeclaredFields())
                .filter(field -> !field.getName().equalsIgnoreCase("id"))
                .map(field -> {
                    try {
                        field.setAccessible(true);
                        if (field.getType().equals(String.class)) {
                            return field.getName() + " = '" + field.get(item) + "'";
                        }
                        return field.getName() + " = " + field.get(item);
                    } catch (Exception e) {
                        throw new RepositoryException(e.getMessage());
                    }
                }).collect(Collectors.joining(", "));
    }


}
