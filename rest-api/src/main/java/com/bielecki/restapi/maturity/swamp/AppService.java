package com.bielecki.restapi.maturity.swamp;

import com.bielecki.restapi.document.Document;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/api/maturity/l0/service")
public class AppService {

    private List<Document> documents = initDocuments();

    @PostMapping
    public ResponseEntity<Object> handleCommand(@RequestBody Command command) {
        if ("getAllDocuments".equals(command.getAction())) {
            return ResponseEntity.ok(documents);
        }
        else if ("addDocument".equals(command.getAction())) {
            documents.add(command.getDocumentToAdd());
            return ResponseEntity.ok().build();
        }
        else if ("deleteDocument".equals(command.getAction())) {
            documents.removeIf(document -> document
                    .getNumber() == command.getNumberToRemove());
            return ResponseEntity.ok().build();
        }
        else if ("getAllProfiles".equals(command.getAction())) {
            return ResponseEntity.ok(allProfiles());
        }
        else if ("downloadFile".equals(command.getAction())) {
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_PNG)
                    .body(new ClassPathResource("/images/image" +
                            ".png", getClass()));
        }
        else if ("managePermissions".equals(command.getAction())) {
            // TODO: change permissions
            return ResponseEntity.ok().build();
        }
        else {
            throw new IllegalArgumentException("Unknown action");
        }
    }

    private List<? extends Object> allProfiles() {
        return List
                .of(new Profile("goobar"), new Profile("foobar"),
                        new Profile("hoobar"));
    }

    private Document documentWithTitle(String documentTitle) {
        Document document = new Document(documentTitle);
        document.setNumber(new Random().nextLong());
        document.setTags(List.of("tag0", "tag1", "tag2"));
        return document;
    }

    private List<Document> initDocuments() {
        ArrayList<Document> documents = new ArrayList<>();
        documents.add(documentWithTitle("doc0"));
        documents.add(documentWithTitle("doc1"));
        documents.add(documentWithTitle("doc2"));
        return documents;
    }

}
