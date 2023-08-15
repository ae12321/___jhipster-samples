package com.mycompany.myapp.custom.resource;

import com.mycompany.myapp.custom.dto.Author;
import com.mycompany.myapp.custom.dto.AuthorDetail;
import com.mycompany.myapp.custom.dto.Book;
import com.mycompany.myapp.custom.repository.AuthorDetailRepository;
import com.mycompany.myapp.custom.repository.AuthorRepository;
import com.mycompany.myapp.custom.repository.BookRepository;
import java.util.Arrays;
import java.util.Random;
import java.util.UUID;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sample")
public class SampleResource {

    private final AuthorRepository authorRepository;
    private final AuthorDetailRepository authorDetailRepository;
    private final BookRepository bookRepository;

    public SampleResource(AuthorRepository authorRepository, AuthorDetailRepository authorDetailRepository, BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.authorDetailRepository = authorDetailRepository;
        this.bookRepository = bookRepository;
    }

    @GetMapping("/asdf1")
    public void asdf1() {
        String uuid = UUID.randomUUID().toString();
        String prefix = uuid.split("-")[0];

        // author detail
        var authorDetail = new AuthorDetail();
        authorDetail.setAge(new Random().nextInt(100));

        // author
        var author = new Author();
        author.setName("author-" + prefix);
        author.setAuthorDetail(authorDetail);

        authorRepository.save(author);
    }

    @GetMapping("/asdf1a")
    public void asdf1a(@RequestParam("id") Long id) {
        var author = authorRepository.findById(id).get();
        System.out.println("-----");
        System.out.println(author);
        System.out.println(author.getAuthorDetail());

        var authorDetail = authorDetailRepository.findById(id).get();
        System.out.println("-----");
        System.out.println(authorDetail);
        System.out.println(authorDetail.getAuthor());
    }

    @GetMapping("/asdf2")
    public void asdf2() {
        String uuid = UUID.randomUUID().toString();
        String prefix = uuid.split("-")[0];

        // author detail
        var authorDetail = new AuthorDetail();
        authorDetail.setAge(new Random().nextInt(100));

        // author
        var author = new Author();
        author.setName("author-" + prefix);
        author.setAuthorDetail(authorDetail);

        // add books
        var book1 = new Book();
        book1.setTitle("111");
        var book2 = new Book();
        book2.setTitle("222");

        author.add(book1);
        author.add(book2);
        // // public void addを使用しない場合
        // book1.setAuthor(author);
        // book2.setAuthor(author);
        // author.setBooks(Arrays.asList(book1, book2));

        authorRepository.save(author);
    }

    @GetMapping("/asdf3")
    public void asdf3(@RequestParam("id") Long id) {
        // String toStringに記載するものは、循環してはいけない。エラーになる

        // // @OneToManyはLazy fetchのため、リクエスト直後に参照先のデータは取得されない
        // // このエラーが出る：failed to lazily initialize a collection of role: com.mycompany.myapp.custom.dto.Author.books, could not initialize proxy - no Session
        // var author = authorRepository.findById(id).get();
        // System.out.println(author);
        // System.out.println(author.getBooks());

        // // 1. 簡単な対処方法は、fetch = FetchType.EAGER をAuthor.javaにつける
        // System.out.println(author.getBooks());

        // // 2. bookのリストを取得する専用のメソッドを作成する
        // var books = bookRepository.findBooksByAuthorId(id);
        // System.out.println(books);

        // 3. joinfetchで一気に取得する
        var author = authorRepository.findAuthorByIdJoinFetch(id);
        System.out.println("----------");
        System.out.println(author);
        System.out.println(author.getAuthorDetail());
        System.out.println(author.getBooks());

        // update
        author.setName("123");
        var book = new Book();
        book.setTitle("333");
        author.add(book);
        authorRepository.save(author);
    }
}
