package com.bielecki.restapi.maturity.hateoas;

import com.bielecki.restapi.document.Document;
import com.bielecki.restapi.maturity.util.DataFixtureUtils;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.print.Doc;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController("documentServiceL3")
@RequestMapping(value = "/api/maturity/l3/documents", produces = "application/cnd.bielecki.v2+hal+json")
public class DocumentService {

    private List<Document> documents = DataFixtureUtils.initDocuments();

    @GetMapping(produces = "application/vnd.bielecki.v1+json")
    public List<Document> getAllDocumentsWithoutLinks(){
        return documents;
    }

    @GetMapping
    public Resources<Resource<Document>> getAllDocuments(){
        Resources<Resource<Document>> resources = new Resources<>(documents.stream().map(this::mapToResource).collect(Collectors.toList()));
        addDocsLink(resources,"self");
        addFindDocsLink(resources,"docsByTitleAndNumber", null, null);
        return resources;
    }

    @GetMapping("/{number}")
    public Resource<Document> getDocument(@PathVariable("number") long number) {
        return documents.stream().filter(doc -> doc.getNumber() == number).findAny()
                .map(this::mapToResource).orElse(null);
        //postman:
        //http://localhost:8080/api/maturity/l3/documents/{doc_number}
    }

    @GetMapping(params = {"title","number"})
    public ResponseEntity<Resources<Resource<Document>>> getDocumentsByTitleAndNumber(@RequestParam("title") String title,
                                                                                      @RequestParam("number") Long number){

        Resources<Resource<Document>> resources = new Resources<>(documents.stream()
                .filter(doc -> title.equals(doc.getTitle()) && number == doc.getNumber())
                .map(this::mapToResource)
                .collect(Collectors.toList()));
        addDocsLink(resources,"allDocs");
        addFindDocsLink(resources,"self",title,number);

        return ResponseEntity.ok()
                .header("Cache-Control", "max-age"+"=3600")
                .body(resources);


        //postman:
        //http://localhost:8080/api/maturity/l2/documents?title=doc1&number=0

    }

    @GetMapping(produces = "text/vnd.bielecki.v1+plain")
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
    public ResponseEntity addDocumentv2(@RequestBody Document document){

        documents.add(document);
        return ResponseEntity.created(URI.create(mapToResource(document).getLink("self").getHref())).build();
    }

    @PostMapping(produces = "application/vnd.bielecki.v1+json")
    @ResponseStatus(HttpStatus.CREATED)
    public void addDocument(@RequestBody Document document){

        documents.add(document);
    }

    @DeleteMapping("/{number}")
    public ResponseEntity<Object> deleteDocument(@PathVariable long number) {
        boolean anyElementRemoved = documents.removeIf(document -> document.getNumber() == number);
        if (anyElementRemoved) return ResponseEntity.ok().build();
        return ResponseEntity.notFound().build();
    }

    private Resource<Document> mapToResource(Document document) {
        Resource<Document> docResource = new Resource<>(document);
        docResource.add(linkTo(methodOn(DocumentService.class).getDocument(document.getNumber()))
                .withSelfRel());
        return docResource;
    }

    private void addFindDocsLink(Resources<Resource<Document>> resources, String rel, String title, Long number) {
        resources.add(linkTo(methodOn(DocumentService.class)
                .getDocumentsByTitleAndNumber(title,number))
                .withRel(rel));
    }

    private void addDocsLink(Resources<Resource<Document>> resources, String rel) {
        resources.add(linkTo(DocumentService.class).withRel(rel));
    }

}
