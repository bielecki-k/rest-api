package com.bielecki.restapi.maturity.methods;

import com.bielecki.restapi.document.Document;
import com.bielecki.restapi.maturity.uil.DataFixtureUtils;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("documentServiceL2")
@RequestMapping("/api/maturity/l2/documents")
public class DocumentService {

    private List<Document> documents = DataFixtureUtils.initDocuments();

    @GetMapping
    public ResponseEntity<List<Document>> getAllDocuments(){
        return ResponseEntity.ok().header("Cache-Control", "max-age"+"=3600").body(documents);
    }

    @GetMapping(produces = MediaType.TEXT_PLAIN_VALUE )
    public String getAllTitles(){
        return documents.stream()
                .map(Document::getTitle)
                .reduce((acc,curr) -> (String.join(",",acc,curr)))
                .orElse("");
        //postman headers:
        //Key       Value
        //Accept    text/plain
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
