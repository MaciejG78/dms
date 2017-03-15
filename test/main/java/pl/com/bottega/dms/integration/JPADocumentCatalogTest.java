package pl.com.bottega.dms.integration;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import pl.com.bottega.dms.application.DocumentQuery;
import pl.com.bottega.dms.application.DocumentSearchResults;
import pl.com.bottega.dms.application.user.AuthProcess;
import pl.com.bottega.dms.application.user.CreateAccountCommand;
import pl.com.bottega.dms.application.user.LoginCommand;
import pl.com.bottega.dms.infrastructure.JPADocumentCatalog;
import pl.com.bottega.dms.infrastructure.JPQLDocumentCatalog;
import pl.com.bottega.dms.shared.AuthHelper;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class JPADocumentCatalogTest {

    @Autowired
    private JPADocumentCatalog catalog;
    //private JPQLDocumentCatalog catalog;

    @Autowired
    private AuthHelper authHelper;

    @Before
    public void authenticate() {
        authHelper.authenticate();
    }


    @Test
    @Sql("/fixtures/documentByPhrase.sql")
    @Transactional
    public void shouldFindDocumentsByPhrase(){
        //when
        DocumentQuery documentQuery = new DocumentQuery();
        documentQuery.setPhrase("fancy");
        DocumentSearchResults searchResults = catalog.find(documentQuery);

        //then
        assertThat(searchResults.getDocuments().size()).isEqualTo(3);
    }

    @Test
    @Sql("/fixtures/documentByPhrase.sql")
    @Transactional
    public void shouldFindDocumentsByPhraseAndStatus() {
        //when
        DocumentQuery documentQuery = new DocumentQuery();
        documentQuery.setPhrase("fancy");
        documentQuery.setStatus("DRAFT");
        DocumentSearchResults searchResults = catalog.find(documentQuery);

        //then
        assertThat(searchResults.getDocuments().size()).isEqualTo(2);
        assertThat(searchResults.getDocuments().get(0).getNumber()).isEqualTo("0");
        assertThat(searchResults.getDocuments().get(1).getNumber()).isEqualTo("fancy");

    }

    @Test
    @Sql("/fixtures/documentByPhrase.sql")
    @Transactional
    public void shouldFindDocumentsByCreatorId() {
        //when
        DocumentQuery documentQuery = new DocumentQuery();
        documentQuery.setCreatorId(1L);
        DocumentSearchResults searchResults = catalog.find(documentQuery);

        //then
        assertThat(searchResults.getDocuments().size()).isEqualTo(3);
        assertThat(searchResults.getDocuments().get(0).getNumber()).isEqualTo("0");
        assertThat(searchResults.getDocuments().get(1).getNumber()).isEqualTo("3");
        assertThat(searchResults.getDocuments().get(2).getNumber()).isEqualTo("5");
    }

    @Test
    @Sql("/fixtures/documentByPhrase.sql")
    @Transactional
    public void shouldFindDocumentsByEditorId() {
        //when
        DocumentQuery documentQuery = new DocumentQuery();
        documentQuery.setEditorId(1L);
        DocumentSearchResults searchResults = catalog.find(documentQuery);

        //then
        assertThat(searchResults.getDocuments().size()).isEqualTo(1);
        assertThat(searchResults.getDocuments().get(0).getNumber()).isEqualTo("5");
    }

    @Test
    @Sql("/fixtures/documentByPhrase.sql")
    @Transactional
    public void shouldFindDocumentsByVerifierId() {
        //when
        DocumentQuery documentQuery = new DocumentQuery();
        documentQuery.setVerifierId(2L);
        DocumentSearchResults searchResults = catalog.find(documentQuery);

        //then
        assertThat(searchResults.getDocuments().size()).isEqualTo(1);
        assertThat(searchResults.getDocuments().get(0).getNumber()).isEqualTo("5");
    }

    @Test
    @Sql("/fixtures/documentByPhrase.sql")
    @Transactional
    public void shouldFindDocumentsByPublisherId() {
        //when
        DocumentQuery documentQuery = new DocumentQuery();
        documentQuery.setPublisherId(2L);
        DocumentSearchResults searchResults = catalog.find(documentQuery);

        //then
        assertThat(searchResults.getDocuments().size()).isEqualTo(1);
        assertThat(searchResults.getDocuments().get(0).getNumber()).isEqualTo("5");
    }

    @Test
    @Sql("/fixtures/documentByPhrase.sql")
    @Transactional
    public void shouldFindDocumentsByCreatedAt() {
        //when
        DocumentQuery documentQuery = new DocumentQuery();
        documentQuery.setCreatedAfter(LocalDateTime.parse("2017-01-01T10:30"));
        documentQuery.setCreatedBefore(LocalDateTime.parse("2017-01-01T12:00"));
        DocumentSearchResults searchResults = catalog.find(documentQuery);

        //then
        assertThat(searchResults.getDocuments().size()).isEqualTo(2);
        assertThat(searchResults.getDocuments().get(0).getNumber()).isEqualTo("0");
        assertThat(searchResults.getDocuments().get(1).getNumber()).isEqualTo("2");
    }
    @Test
    @Sql("/fixtures/documentByPhrase.sql")
    @Transactional
    public void shouldFindDocumentsByChangedAt() {
        //when
        DocumentQuery documentQuery = new DocumentQuery();
        documentQuery.setChangedAfter(LocalDateTime.parse("2017-01-05T10:30"));
        documentQuery.setChangedBefore(LocalDateTime.parse("2017-02-05T12:00"));
        DocumentSearchResults searchResults = catalog.find(documentQuery);

        //then
        assertThat(searchResults.getDocuments().size()).isEqualTo(1);
        assertThat(searchResults.getDocuments().get(0).getNumber()).isEqualTo("5");
    }

    @Test
    @Sql("/fixtures/documentByPhrase.sql")
    @Transactional
    public void shouldFindDocumentsByVerifiedAt() {
        //when
        DocumentQuery documentQuery = new DocumentQuery();
        documentQuery.setVerifiedAfter(LocalDateTime.parse("2017-01-01T10:30"));
        documentQuery.setVerifiedBefore(LocalDateTime.parse("2017-05-05T12:00"));
        DocumentSearchResults searchResults = catalog.find(documentQuery);

        //then
        assertThat(searchResults.getDocuments().size()).isEqualTo(1);
        assertThat(searchResults.getDocuments().get(0).getNumber()).isEqualTo("5");
    }

    @Test
    @Sql("/fixtures/documentByPhrase.sql")
    @Transactional
    public void shouldFindDocumentsByPublishedAt() {
        //when
        DocumentQuery documentQuery = new DocumentQuery();
        documentQuery.setPublishedAfter(LocalDateTime.parse("2017-01-01T10:30"));
        documentQuery.setPublishedBefore(LocalDateTime.parse("2017-02-05T12:00"));
        DocumentSearchResults searchResults = catalog.find(documentQuery);

        //then
        assertThat(searchResults.getDocuments().size()).isEqualTo(1);
        assertThat(searchResults.getDocuments().get(0).getNumber()).isEqualTo("5");
    }

    @Test
    @Sql("/fixtures/documentByPhrase.sql")
    @Transactional
    public void shouldReturnPaginatedResults() {
        //when
        DocumentQuery documentQuery = new DocumentQuery();
        documentQuery.setPageNumber(2);
        documentQuery.setPerPage(2);
        DocumentSearchResults searchResults = catalog.find(documentQuery);

        //then
        assertThat(searchResults.getDocuments().size()).isEqualTo(2);
        assertThat(searchResults.getDocuments().get(0).getNumber()).isEqualTo("2");
        assertThat(searchResults.getDocuments().get(1).getNumber()).isEqualTo("3");
        assertThat(searchResults.getPagesCount()).isEqualTo(3L);
        assertThat(searchResults.getPageNumber()).isEqualTo(2);
        assertThat(searchResults.getPerPage()).isEqualTo(2);
    }

    @Test
    @Sql("/fixtures/documentByPhrase.sql")
    @Transactional
    public void shouldFindedDocumentsSortByTitleDESC(){
        //when
        DocumentQuery documentQuery = new DocumentQuery();
        documentQuery.setSortBy("title");
        documentQuery.setSortOrder("DESC");
        DocumentSearchResults searchResults = catalog.find(documentQuery);

        //then
        assertThat(searchResults.getDocuments().size()).isEqualTo(5);
        assertThat(searchResults.getDocuments().get(0).getNumber()).isEqualTo("5");
    }

    @Test
    @Sql("/fixtures/documentByPhrase.sql")
    @Transactional
    public void shouldFindedDocumentsSortByCreatedAtDESC(){
        //when
        DocumentQuery documentQuery = new DocumentQuery();
        documentQuery.setSortBy("createdAt");
        documentQuery.setSortOrder("DESC");
        DocumentSearchResults searchResults = catalog.find(documentQuery);

        //then
        assertThat(searchResults.getDocuments().size()).isEqualTo(5);
        assertThat(searchResults.getDocuments().get(0).getNumber()).isEqualTo("3");

    }
}