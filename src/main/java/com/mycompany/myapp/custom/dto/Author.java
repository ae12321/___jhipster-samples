package com.mycompany.myapp.custom.dto;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "author")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "author_id")
    private Long id;

    @Column(name = "author_name")
    private String name;

    // 自身に対するアクション時に参照先をどうするか
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "author_detail_id")
    private AuthorDetail authorDetail;

    // Bookクラス内のauthorに対応するプロパティ名を指定
    @OneToMany(
        mappedBy = "author",
        cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH },
        // 1.
        // fetch = FetchType.EAGER
        fetch = FetchType.LAZY
    )
    private List<Book> books;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AuthorDetail getAuthorDetail() {
        return authorDetail;
    }

    public void setAuthorDetail(AuthorDetail authorDetail) {
        this.authorDetail = authorDetail;
    }

    @Override
    public String toString() {
        return "Author [id=" + id + ", name=" + name + ", authorDetail=" + authorDetail + "]";
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    // authorの所持booksに対し追加と同時に、追加物に自身を指定する
    public void add(Book book) {
        if (books == null) {
            books = new ArrayList<>();
        }
        books.add(book);
        book.setAuthor(this);
    }
}
