package com.bielecki.restapi;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

@RestController
@RequestMapping("/api/documents")
public class DocumentRestApiController {

    private Collection<Document> documents = new ArrayList<>();

    @GetMapping
    public Iterable<Document> getDocument(){
        return documents;
    }


    @PostMapping
    public void addDocument(@RequestBody Document document){
        documents.add(document);
    }



}
