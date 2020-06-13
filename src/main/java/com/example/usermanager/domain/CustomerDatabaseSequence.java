package com.example.usermanager.domain;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "database_sequences")
@Getter
public class CustomerDatabaseSequence {
    @Id
    private String id;
    private long seq;

    public void setSeq(long seq) {
        this.seq = seq;
    }
}
