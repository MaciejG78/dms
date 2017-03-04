package pl.com.bottega.dms.application;

import java.time.LocalDateTime;

public class DocumentSearchResults {
    private String phrase;
    private String status;

    private LocalDateTime createdAfter;
    private LocalDateTime createdBefore;

    private Long creatorId;

    private Long pageNumber;
    private Long perPage;

}
