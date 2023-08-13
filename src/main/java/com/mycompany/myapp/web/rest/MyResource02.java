package com.mycompany.myapp.web.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.myapp.domain.Company;
import com.mycompany.myapp.repository.CompanyRepository;
import com.mycompany.myapp.web.rest.dto.MyCustomData1;
import io.undertow.util.BadRequestException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/my-resource-02")
public class MyResource02 {

    private final CompanyRepository companyRepository;

    public MyResource02(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @GetMapping("/asdf1")
    public ResponseEntity<String> asdf1() {
        Long len = companyRepository.count();
        return new ResponseEntity<String>(String.format("len: %d", len), HttpStatus.OK);
    }

    @GetMapping("/asdf2/company")
    public ResponseEntity<List<Company>> asdf2() {
        List<Company> companies = companyRepository.findAll();
        return new ResponseEntity<List<Company>>(companies, HttpStatus.OK);
    }

    @GetMapping("/asdf3/company/{companyId}")
    public ResponseEntity<Company> asdf3(@PathVariable(name = "companyId") Long theId) {
        Company company = companyRepository.findById(theId).get();
        return new ResponseEntity<Company>(company, HttpStatus.OK);
    }

    @PostMapping("/asdf4/company")
    public ResponseEntity<Company> asdf4(@RequestBody Company theCompany) throws URISyntaxException {
        Company company = companyRepository.save(theCompany);
        return ResponseEntity.created(new URI("/api/asdf4/company/" + company.getId())).body(company);
    }

    @DeleteMapping("/asdf5/company/{companyId}")
    public ResponseEntity<Void> asdf5(@PathVariable(name = "companyId") Long theId) {
        companyRepository.deleteById(theId);
        // Company company = companyRepository.save(theCompany);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/asdf5/company/{companyId}")
    public ResponseEntity<Company> asdf6(
        @PathVariable(name = "companyId", required = false) Long companyId,
        @RequestBody Company theCompany
    ) throws BadRequestException {
        if (theCompany.getId() == null) {
            throw new BadRequestException();
        }
        if (companyId != theCompany.getId()) {
            throw new BadRequestException();
        }
        if (!companyRepository.existsById(companyId)) {
            throw new BadRequestException();
        }

        Company company = companyRepository.save(theCompany);
        return ResponseEntity.ok().body(company);
    }

    // sqlの列名がそのまま返されるパターン
    @GetMapping("/asdf7")
    public ResponseEntity<List<Map<String, Object>>> asdf7() {
        List<Map<String, Object>> myJoinEmployees = companyRepository.getJoinedData().get();
        return new ResponseEntity<List<Map<String, Object>>>(myJoinEmployees, HttpStatus.OK);
    }

    // sqlの列名＝＞POJOに食わせクラスのリストとして返す（ｓｑｌの列名＝＞正式な列名）
    @GetMapping("/asdf8")
    public ResponseEntity<List<MyCustomData1>> asdf8() {
        List<Map<String, Object>> myJoinEmployees = companyRepository.getJoinedData().get();
        List<MyCustomData1> result = myJoinEmployees
            .stream()
            .map(one -> {
                var mapper = new ObjectMapper();
                var converted = mapper.convertValue(one, MyCustomData1.class);
                return converted;
            })
            .toList();

        return new ResponseEntity<List<MyCustomData1>>(result, HttpStatus.OK);
    }

    @GetMapping("/asdf9")
    public ResponseEntity<List<MyCustomData1>> asdf9(@RequestParam("id") Long companyId) {
        List<Map<String, Object>> myjoinCompanies = companyRepository.getJoinedData2(companyId).get();
        List<MyCustomData1> result = myjoinCompanies
            .stream()
            .map(one -> {
                var mapper = new ObjectMapper();
                var converted = mapper.convertValue(one, MyCustomData1.class);
                return converted;
            })
            .toList();

        return new ResponseEntity<List<MyCustomData1>>(result, HttpStatus.OK);
    }
}
