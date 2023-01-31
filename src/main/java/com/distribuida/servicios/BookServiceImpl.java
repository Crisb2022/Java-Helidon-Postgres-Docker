package com.distribuida.servicios;

import com.distribuida.config.BookMapper;
import com.distribuida.db.Book;
import io.helidon.common.reactive.Multi;
import io.helidon.dbclient.DbClient;
import io.helidon.dbclient.DbRow;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@ApplicationScoped
public class BookServiceImpl implements BookService{

    @Inject
    private DbClient dbClient;
    @Inject
    private BookMapper bookMapper;

    // Buscar a todos
    @Override
    public List<Book> findAll() throws ExecutionException, InterruptedException {
        Multi<DbRow> execute = this.dbClient
                .execute(exe -> exe.createQuery("SELECT a.id as author_id, a.first_name, a.last_name, b.id, b.title, b.isbn, b.price FROM book b JOIN author a ON b.author_id = a.id")
                        .execute());
        return execute.collectList().get().stream().map(this.bookMapper::read).collect(Collectors.toList());
    }

    // Buscar por ID
    @Override
    public Book findById(int id) throws ExecutionException, InterruptedException {
        Optional<DbRow> dbRow = this.dbClient
                .execute(exe -> exe.createGet("SELECT A.id as author_id, A.first_name, A.last_name, B.id, B.title, B.isbn, B.price FROM book B JOIN author A ON b.id = A.id WHERE B.id = :id")
                        .addParam("id", id)
                        .execute()).get();

        return dbRow.isPresent() ? this.bookMapper.read(dbRow.get()) : new Book();
    }

    // Crear libro libro
    @Override
    public long createBook(Book book) {
        var rowsChanged = 0L;
        try {
            rowsChanged = this.dbClient
                    .execute(exec -> exec
                            .insert("INSERT INTO book (author_id, isbn, title, price) VALUES(?, ?, ?, ?)",
                                    book.getAuthor().getId(), book.getIsbn(), book.getTitle(), book.getPrice()
                            )).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rowsChanged;
    }

    // Actualizar libro
    @Override
    public long updateBook(Book book) {
        var rowsChanged = 0L;
        try {
            rowsChanged = this.dbClient
                    .execute(exec -> exec
                            .update("UPDATE book SET author_id = ?, isbn = ?, title = ?, price = ? WHERE id = ?",
                                    book.getAuthor().getId(), book.getIsbn(), book.getTitle(), book.getPrice(), book.getId()
                            )).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rowsChanged;
    }

    // Borrar libro
    @Override
    public long deleteBook(int id) {
        var rowsChanged = 0L;
        try {
            rowsChanged = this.dbClient
                    .execute(exec -> exec
                            .delete("DELETE FROM book WHERE id = ?",
                                    id
                            )).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rowsChanged;
    }
}
