package com.bielecki.restapi.document;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
    public void addDocument(@RequestBody Document document){ // { "title": "eoeo" }
        documents.add(document);
    }

    @PostMapping(value = "/{docNumber}/tags", consumes = MediaType.TEXT_PLAIN_VALUE)
    public void addTag(@PathVariable long docNumber, @RequestBody String tag){
            documents.stream().filter(doc->doc.getNumber()==docNumber)
                    .findAny().ifPresent(doc->doc.getTags().add(tag));
//            1:post http://localhost:8080/api/documents
//        {
//            "number":0,
//                "title": "eoeo",
//                "tags": ["ioio","fifo"]
//        }
//        2:
//        post http://localhost:8080/api/documents/0/tags
//            smth

    }



}
