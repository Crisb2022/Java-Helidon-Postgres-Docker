package com.distribuida.rest;

import com.distribuida.db.Book;
import com.distribuida.servicios.BookService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@ApplicationScoped
@Path("/books")
@Produces("application/json")
@Consumes("application/json")
public class BookRest {
    @Inject
    private BookService bookService;

    @GET
    @Path("/{id}")
    public Book findOneById(@PathParam("id") int id) throws ExecutionException, InterruptedException {
        return this.bookService.findById(id);
    }

    @GET
    public List<?> findAll() throws ExecutionException, InterruptedException {
        return this.bookService.findAll();
    }

    @PUT
    public Map<String, Long> update(Book book) {
        return Map.of("rowsChanged", this.bookService.updateBook(book));
    }

    @POST
    public Map<String, Long> save(Book book) {
        return Map.of("rowsChanged", this.bookService.createBook(book));
    }

    @DELETE
    @Path("/{id}")
    public Map<String, Long> delete(@PathParam("id") int id) {
        return Map.of("rowsChanged", this.bookService.deleteBook(id));
    }
}
