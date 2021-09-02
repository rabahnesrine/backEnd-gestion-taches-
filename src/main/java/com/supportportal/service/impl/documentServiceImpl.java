package com.supportportal.service.impl;

import com.supportportal.domain.Attachement;
import com.supportportal.domain.Document;
import com.supportportal.domain.User;
import com.supportportal.repository.DocumentRepository;
import com.supportportal.service.DocumentService;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class documentServiceImpl implements DocumentService {


    private final Path rootLocation = Paths.get("C:/Users/USER/supportportal/Document");

    @Autowired
    private DocumentRepository documentRepository;

    @Override
    public void store(MultipartFile file) {
        try {
            System.out.println(file.getOriginalFilename());
            System.out.println(rootLocation.toUri());
            Files.copy(file.getInputStream(), this.rootLocation.resolve(file.getOriginalFilename()));
        } catch (Exception e) {
            throw new RuntimeException("FAIL!");
        }

    }

    @Override
    public Document save(long id, Date dateCreation,
                         String description, String nomDocument, String file,
                         String typeDocument, boolean archive, boolean etat, User docUser) {
        Document newDoc = new Document();
        newDoc.setId(id);
        int length = file.length();
        String path = file.substring(12, length);
        newDoc.setArchive(false);
        newDoc.setFile(path);
        newDoc.setDateCreation(new Date());
        newDoc.setDescription(description);
        newDoc.setDocUser(docUser);
        newDoc.setNomDocument(nomDocument);
        newDoc.setEtat(etat);

        newDoc.setTypeDocument(typeDocument);
        return documentRepository.save(newDoc);

    }

    public Document updateDocument(Document document) {
        return documentRepository.saveAndFlush(document);
    }

    @Override
    public List<Document> findAll() {
        return documentRepository.findAll();
    }

    @Override
    public int totalDocument() {

        return documentRepository.totalDocument();
    }

    @Override
    public List<Document> findDocumentByArchiveTrue() {

        return documentRepository.findDocumentByArchiveTrue();
    }

    @Override
    public List<Document> findDocumentByArchiveFalseEtatPublic() {

        return documentRepository.findDocumentByArchiveFalseAndEtatTrue();
    }

    @Override
    public List<Document> findDocumentByArchiveFalseEtatPrive() {

        return documentRepository.findDocumentByArchiveFalseAndEtatFalse();
    }

    @Override
    public int totalDocumentArchived() {

        return documentRepository.totalDocumentArchived();
    }


    @Override
    public int totalDocumentPrive() {

        return documentRepository.totalDocumentPrive();
    }
    @Override
    public int totalDocumentPublic() {

        return documentRepository.totalDocumentPublic();
    }


    @Override
    public int totalDocumentNonArchive() {
        return documentRepository.totalDocumentNonArchive();
    }

    @Override
    public void deleteDocument(long id) {
        documentRepository.deleteById(id);

    }

    public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = this.rootLocation.resolve(fileName).normalize();
            System.out.println("filePath : Service"+ filePath);
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) {
                System.out.println("resource : Service"+ resource);
                return resource;
            } else {
                throw new RuntimeException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new RuntimeException("File not found " + fileName, ex);
        }
    }

    @Override
    public Resource loadFile(String filename) {

        try {
            Path file = rootLocation.resolve(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("FAIL!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("FAIL!");
        }
    }

    @Override
    public List<Document> findDocumentByUsername(User utilisateur) {

        return documentRepository.findDocumentByDocUserIdAndEtatFalseAndArchiveFalse(utilisateur.getId());
    }

    @Override
    public Document getDocumentById(long id) {

        return documentRepository.findDocumentById(id);
    }


    @Override
    public List<Document> findDocArchiveByUtilisateur(User user) {

        return documentRepository.findDocumentByDocUserIdAndArchiveTrue(user.getId());
    }


    @Override
    public List<Document> findByUtilisateur(User user) {

        return documentRepository.findDocumentByDocUserIdAndArchiveFalse(user.getId());
    }

}
