package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.web.rest.dto.MyUser;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/my-resource-01")
public class MyResource01 {

    // application.ymlからのデータ取得
    @Value("${myapp.version.name}")
    private String myappVersionName;

    @Value("${myapp.version.number}")
    private int myappVersionNumber;

    // GET http://localhost:9000/api/my-resource-01/asdf1
    //  content-type: text/plain;charset=UTF-8
    @GetMapping("/asdf1")
    public ResponseEntity<String> asdf1() {
        return new ResponseEntity<String>(String.format("adsf1: %s, %d", myappVersionName, myappVersionNumber), HttpStatus.OK);
    }

    // GET http://localhost:9000/api/my-resource-01/asdf2?name=bob&age=32
    //  content-type: text/plain;charset=UTF-8
    @GetMapping("/asdf2")
    public ResponseEntity<String> asdf2(String name, int age) {
        return new ResponseEntity<String>(String.format("adsf2: %s, %d", name, age), HttpStatus.OK);
    }

    // GET http://localhost:9000/api/my-resource-01/asdf3?name=bob&age=11
    //  content-type: application/json
    @GetMapping("/asdf3")
    public ResponseEntity<String> asdf3(String name, int age) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // json形式ではないため、フロント側のパース(SyntaxError: Unexpected token ~)で失敗する
        // String body = String.format("{name: %s, age: %d}", name, age);
        String body = String.format("{\"name\": \"%s\", \"age\": %d}", name, age);

        return new ResponseEntity<String>(body, headers, HttpStatus.OK);
    }

    // Getで任意のRequestBodyを受け取る場合、クエリパラメタではなくbodyにJSON形式で指定するとGETできる
    // @GetMapping("/asdf4")
    // public ResponseEntity<Void> asdf4(@RequestBody Map<String, Object> bookingRequest) {

    @PostMapping("/asdf4")
    // 任意の形式で受け取る場合、以下ではなく
    // public ResponseEntity<String> asdf4(@RequestBody Object obj) {
    // こちら
    public ResponseEntity<Map<String, Object>> asdf4(@RequestBody Map<String, Object> reqBody) {
        var result = new HashMap<String, Object>();

        try {
            System.out.println(reqBody.get("a"));
            result.put("aaa", reqBody.get("a").toString() + reqBody.get("a").toString());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        try {
            System.out.println(reqBody.get("b"));
            result.put("bbb", (int) reqBody.get("b") * 2);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        try {
            System.out.println(reqBody.get("c"));
            result.put("ccc", !(Boolean) reqBody.get("c"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return ResponseEntity.ok().body(result);
    }

    // GET http://localhost:9000/api/my-resource-01/asdf5/bob/32
    @GetMapping("/asdf5/{name}/{age}")
    public ResponseEntity<Map<String, Object>> asdf5(
        @PathVariable(name = "name") String userName,
        @PathVariable(name = "age") int userAge
    ) {
        var result = new HashMap<String, Object>();
        result.put("userName", "Mr." + userName);
        result.put("userAge", userAge * 2);

        return ResponseEntity.ok().body(result);
    }

    // GET http://localhost:9000/api/my-resource-01/asdf6?name=bob&age=12
    @GetMapping("/asdf6")
    public ResponseEntity<Map<String, Object>> asdf6(
        @RequestParam(name = "name") String userName,
        @RequestParam(name = "age") int userAge
    ) {
        var result = new HashMap<String, Object>();
        result.put("userName", "Mr." + userName);
        result.put("userAge", userAge * 2);

        return ResponseEntity.ok().body(result);
    }

    // GET http://localhost:9000/api/my-resource-01/asdf7/bob?age=23
    @GetMapping("/asdf7/{name}")
    public ResponseEntity<Map<String, Object>> asdf7(
        @PathVariable(name = "name") String userName,
        @RequestParam(name = "age") int userAge
    ) {
        var result = new HashMap<String, Object>();
        result.put("userName", "Mr." + userName);
        result.put("userAge", userAge * 2);

        return ResponseEntity.ok().body(result);
    }

    // { "name": "bob", "age": 12 }: ok
    // { "name": "bob", "age": 12, "hasBaby": true }: ok(指定外分は除外)
    // { "name": "bob", "ageage": 12, "hasBaby": true }: ok(受け入れはＯＫ。ただし内部で処理するときはプリミティブ値のデフォルト)
    @PostMapping("/asdf8")
    public ResponseEntity<Map<String, Object>> asdf8(@RequestBody MyUser myUser) {
        var result = new HashMap<String, Object>();
        result.put("userName", "Mr." + myUser.name());
        result.put("userAge", myUser.age() * 3);

        return ResponseEntity.ok().body(result);
    }
}
