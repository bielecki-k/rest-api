package com.bielecki.restapi.maturity.methods;

import com.bielecki.restapi.document.Document;
import com.bielecki.restapi.maturity.uil.DataFixtureUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("documentServiceL2")
@RequestMapping("/api/maturity/l2/documents")
public class DocumentService {

    private List<Document> documents = DataFixtureUtils.initDocuments();

    @GetMapping
    public List<Document> getAllDocuments(){
        return documents;
    }

    @PostMapping
    public void AddDocument(@RequestBody Document document){
        documents.add(document);
    }

    @DeleteMapping("/{number}")
    public void deleteDocument(@PathVariable long number){
        documents.removeIf(document -> document.getNumber() == number);
    }

}
