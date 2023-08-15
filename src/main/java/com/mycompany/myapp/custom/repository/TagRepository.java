package com.mycompany.myapp.custom.repository;

import com.mycompany.myapp.custom.dto.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
    // find tags by bookid
    @Query(value = "select t from Tag t join fetch t.books where t.id = :tagId")
    Tag findTagsByTagId(@Param("tagId") Long id);
}
