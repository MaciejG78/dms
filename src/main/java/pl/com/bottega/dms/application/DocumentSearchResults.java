package pl.com.bottega.dms.application;

import java.util.List;

public class DocumentSearchResults {

    private List<DocumentDto> documents;
    private Integer pageNumber;
    private Integer perPage;

    private Long pageCount;

    public Integer getPerPage() {
        return perPage;
    }

    public Long getPageCount() {
        return pageCount;
    }

    public void setPagesCount(Long pageCount) {
        this.pageCount = pageCount;
    }

    public List<DocumentDto> getDocuments() {
        return documents;
    }

    public void setDocuments(List<DocumentDto> documents) {
        this.documents = documents;
    }


    public Long getPagesCount() {
        return pageCount;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPerPage(Integer perPage) {
        this.perPage = perPage;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }
}
