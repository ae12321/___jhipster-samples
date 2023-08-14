package com.mycompany.myapp.custom.resource;

import com.mycompany.myapp.custom.dto.Author;
import com.mycompany.myapp.custom.dto.AuthorDetail;
import com.mycompany.myapp.custom.repository.AuthorDetailRepository;
import com.mycompany.myapp.custom.repository.AuthorRepository;
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

    public SampleResource(AuthorRepository authorRepository, AuthorDetailRepository authorDetailRepository) {
        this.authorRepository = authorRepository;
        this.authorDetailRepository = authorDetailRepository;
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
}
