package com.bielecki.restapi.document;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/documents")
public class DocumentRestApiController {

    private Collection<Document> documents = new ArrayList<>();

    @GetMapping(params = "title") //http://localhost:8080/api/documents?title=document1
    public Stream<Document> findDocByTitle(@RequestParam("title") String title){
        return documents.stream().filter(doc-> title.equals(doc.getTitle()));
    }

    @DeleteMapping("/{id}")
    public void deleteDocument(@PathVariable("id") long number){
        documents.removeIf(doc -> doc.getNumber()==number);
    }

    @PatchMapping("/{number}")
    public void updateDocument(@PathVariable long number, @RequestBody Document  newPartialDocument) {
        findDocumentByNumber(number).ifPresent(doc -> {
            if (newPartialDocument.getTitle() != null)
                doc.setTitle(newPartialDocument.getTitle());
            if (newPartialDocument.getTags() != null)
                doc.setTags(newPartialDocument.getTags());
        });
    }

    @PutMapping("/{number}")
    public void replaceDocument(@PathVariable long number, @RequestBody Document newDocument) {
        findDocumentByNumber(number).ifPresent(document -> {
            document.setTitle(newDocument.getTitle());
            document.setTags(newDocument.getTags());
        });
    }

    @GetMapping
    public Iterable<Document> getDocuments(){
        return documents;
    }

    @GetMapping("/{number}")
    public Optional<Document> getDocument(@PathVariable long number){
        return findDocumentByNumber(number);
    }

    @GetMapping("/{number}/title")
    public Optional<String> getTitleOfDocument(@PathVariable long number){
        return findDocumentByNumber(number).map(Document::getTitle);
    }

    @PostMapping
    public void addDocument(@RequestBody Document document){ // { "title": "eoeo" }
        documents.add(document);
    }

    @PostMapping(value = "/{docNumber}/tags", consumes = MediaType.TEXT_PLAIN_VALUE)
    public void addTag(@PathVariable long number, @RequestBody String tag){
        findDocumentByNumber(number)
                .ifPresent(doc->doc.getTags().add(tag));
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

    private Optional<Document> findDocumentByNumber(long number) {
        return documents.stream()
                .filter(doc -> doc.getNumber() == number)
                .findAny();
    }



}
