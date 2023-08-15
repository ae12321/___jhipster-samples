package com.mycompany.myapp.custom.resource;

import com.mycompany.myapp.custom.dto.Author;
import com.mycompany.myapp.custom.dto.AuthorDetail;
import com.mycompany.myapp.custom.dto.Book;
import com.mycompany.myapp.custom.dto.BookReveiw;
import com.mycompany.myapp.custom.dto.Tag;
import com.mycompany.myapp.custom.repository.AuthorDetailRepository;
import com.mycompany.myapp.custom.repository.AuthorRepository;
import com.mycompany.myapp.custom.repository.BookRepository;
import com.mycompany.myapp.custom.repository.TagRepository;
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
    private final TagRepository tagRepository;

    public SampleResource(
        AuthorRepository authorRepository,
        AuthorDetailRepository authorDetailRepository,
        BookRepository bookRepository,
        TagRepository tagRepository
    ) {
        this.authorRepository = authorRepository;
        this.authorDetailRepository = authorDetailRepository;
        this.bookRepository = bookRepository;
        this.tagRepository = tagRepository;
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

    @GetMapping("/asdf4")
    public void asdf4(@RequestParam("id") Long id) {
        // // 今回のメイン
        Book book = new Book();
        book.setTitle("book1");

        System.out.println("-----------------------------------");
        System.out.println("22222");
        // bookに突っ込むもの
        BookReveiw review1 = new BookReveiw();
        review1.setDescription("review1");
        book.add(review1);
        BookReveiw review2 = new BookReveiw();
        review2.setDescription("review2");
        book.add(review2);

        System.out.println("-----------------------------------");
        System.out.println("33333");
        bookRepository.save(book);
    }

    @GetMapping("/asdf5")
    public void asdf5() {
        Book book1 = new Book();
        book1.setTitle("book1");

        Tag tag1 = new Tag();
        tag1.setName("tag1");
        book1.addTag(tag1);
        Tag tag2 = new Tag();
        tag2.setName("tag2");
        book1.addTag(tag2);

        //
        System.out.println("--------------------");
        System.out.println(book1);
        System.out.println(book1.getTags());

        bookRepository.save(book1);
    }

    @GetMapping("/asdf6")
    public void asdf6(@RequestParam("bookid") Long id) {
        var book = bookRepository.findBooksAndTagsByBookId(id);

        System.out.println("--------------------");
        System.out.println(book);
        System.out.println(book.getTags());
    }

    @GetMapping("/asdf7")
    public void asdf7(@RequestParam("tagid") Long id) {
        var tag = tagRepository.findTagsByTagId(id);

        System.out.println("--------------------");
        System.out.println(tag);
        System.out.println(tag.getBooks());
    }

    @GetMapping("/asdf8")
    public void asdf8() {
        Long bookId = 13L; // すでに登録されている前提

        // // asdf8 で既存に対し新規追加する場合
        // // lazyfetchのため参照先が取得できず、addTagできない
        // Book book = bookRepository.findById(bookId).get();

        Book book = bookRepository.findBooksAndTagsByBookId(bookId);

        System.out.println("--------------------");
        System.out.println("1111");
        System.out.println(book);
        System.out.println(book.getTags());

        Tag tag3 = new Tag();
        tag3.setName("tag3");
        book.addTag(tag3);

        System.out.println("--------------------");
        System.out.println("2222");
        // System.out.println(book);
        // System.out.println(book.getTags());

        bookRepository.save(book);
    }
}
