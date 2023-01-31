package com.distribuida.config;

import com.distribuida.db.Book;
import io.helidon.dbclient.DbMapper;
import io.helidon.dbclient.spi.DbMapperProvider;

import java.util.Optional;

public class BookMapperProvider implements DbMapperProvider {
    public static final BookMapper MAPPER = new BookMapper();

    @Override
    @SuppressWarnings("unchecked")
    public <T> Optional<DbMapper<T>> mapper(Class<T> type) {
        return type.equals(Book.class) ? Optional.of((DbMapper<T>) MAPPER) : Optional.empty();
    }
}
