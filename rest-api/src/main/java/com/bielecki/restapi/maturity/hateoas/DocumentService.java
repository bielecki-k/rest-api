package com.bielecki.restapi.maturity.hateoas;

import com.bielecki.restapi.document.Document;
import com.bielecki.restapi.maturity.util.DataFixtureUtils;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController("documentServiceL3")
@RequestMapping("/api/maturity/l3/documents")
public class DocumentService {

    private List<Document> documents = DataFixtureUtils.initDocuments();

    @GetMapping("/{number}")
    public Resource<Document> getDocument(@PathVariable("number") long number) {
        return documents.stream().filter(doc -> doc.getNumber() == number).findAny()
                .map(doc -> {
                    Resource<Document> docResource = new Resource<>(doc);
                    docResource.add(linkTo(methodOn(DocumentService.class).getDocument(number))
                            .withSelfRel());
                    return docResource;
                }).orElse(null);
        //postman:
        //http://localhost:8080/api/maturity/l3/documents/{doc_number}
    }

    @GetMapping(params = {"title","number"})
    public ResponseEntity<List<Document>> getDocumentsByTitleAndNumber(@RequestParam("title") String title,
                                                                       @RequestParam("number") long number){

        return ResponseEntity.ok()
                .header("Cache-Control", "max-age"+"=3600")
                .body(documents.stream()
                        .filter(doc -> title.equals(doc.getTitle()) && number==doc.getNumber())
                        .collect(Collectors.toList())
                )
                ;
        //postman:
        //http://localhost:8080/api/maturity/l2/documents?title=doc1&number=0

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
    @ResponseStatus(HttpStatus.CREATED)
    public void AddDocument(@RequestBody Document document){

        documents.add(document);
    }

    @DeleteMapping("/{number}")
    public ResponseEntity<Object> deleteDocument(@PathVariable long number) {
        boolean anyElementRemoved = documents.removeIf(document -> document.getNumber() == number);
        if (anyElementRemoved) return ResponseEntity.ok().build();
        return ResponseEntity.notFound().build();
    }

}
