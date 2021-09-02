package com.supportportal.repository;

import com.supportportal.domain.Document;
import com.supportportal.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DocumentRepository extends JpaRepository<Document,Long> {
    @Query("SELECT COUNT(d) FROM Document d ")
    int totalDocument();

    @Query("SELECT COUNT(d) FROM Document d Where d.archive=false ")
    int totalDocumentNonArchive();

    @Query("SELECT COUNT(d) FROM Document d Where d.archive=true ")
    int totalDocumentArchived();


    @Query("SELECT COUNT(d) FROM Document d Where  d.etat=false ")
    int totalDocumentPrive();
    @Query("SELECT COUNT(d) FROM Document d Where d.etat=true")
    int totalDocumentPublic();



    List<Document> findDocumentByArchiveTrue();

    List<Document> findDocumentByArchiveFalseAndEtatTrue();
    List<Document> findDocumentByArchiveFalseAndEtatFalse();

    @Query("SELECT d FROM Document d Where d.archive=false AND d.etat=false AND d.docUser.id=:id")
    List<Document> findDocumentByDocUserIdAndEtatFalseAndArchiveFalse(@Param("id") Long id);

    @Query("SELECT d FROM Document d Where d.archive=true  AND d.docUser.id=:id")
    List<Document> findDocumentByDocUserIdAndArchiveTrue(@Param("id")Long id);

    List<Document> findDocumentByDocUserIdAndArchiveFalse(Long id);

    public Document findDocumentById(long id);

}
