package com.supportportal.service;

import java.util.Date;
import java.util.List;

import com.supportportal.domain.Document;
import com.supportportal.domain.User;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;
public interface DocumentService {

    public void store(MultipartFile file);

    Document save(long id, Date dateCreation,
                  String description, String nomDocument, String file,
                  String typeDocument, boolean archive, boolean etat, User docUser);

    List<Document> findAll();

    int totalDocument();
    int totalDocumentArchived();
    int totalDocumentNonArchive();
    int totalDocumentPrive();
    int totalDocumentPublic();

    public List<Document> findDocArchiveByUtilisateur(User user) ;

    List<Document> findDocumentByArchiveTrue();

    List<Document> findDocumentByArchiveFalseEtatPublic();

    List<Document> findDocumentByArchiveFalseEtatPrive();

    Document updateDocument(Document document);
    void deleteDocument(long id);
    Resource loadFileAsResource(String fileName);
    List<Document> findDocumentByUsername(User docUser);

    Resource loadFile(String filename);
    Document getDocumentById(long id);
    List<Document> findByUtilisateur(User docUser);
}
