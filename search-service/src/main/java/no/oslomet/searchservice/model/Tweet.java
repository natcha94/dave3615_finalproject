package no.oslomet.searchservice.model;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class Tweet {
    private long id;
    private LocalDateTime dateTime;
    private String text;
    private long userId;
    private List<String> imagePathList = new ArrayList<>();
}
