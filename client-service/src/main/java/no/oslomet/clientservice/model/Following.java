package no.oslomet.clientservice.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class Following {
    private long id;
    private User user;
}
