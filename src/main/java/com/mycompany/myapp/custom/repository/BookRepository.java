package com.mycompany.myapp.custom.repository;

import com.mycompany.myapp.custom.dto.Book;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    @Query(value = "select b from Book b where author_id = :data")
    List<Book> findBooksByAuthorId(@Param("data") Long id);

    // resolve lazy fetch
    @Query(value = "select b from Book b join fetch b.tags where b.id = :bookId")
    Book findBooksAndTagsByBookId(@Param("bookId") Long id);
}
