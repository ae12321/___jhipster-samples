package com.mycompany.myapp.custom.repository;

import com.mycompany.myapp.custom.dto.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    // 3.
    // a.idのidは、Author.javaのプロパティ名
    @Query("select a from Author a join fetch a.books where a.id = :data")
    Author findAuthorByIdJoinFetch(@Param("data") Long id);
}
