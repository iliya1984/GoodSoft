package com.goodsoft.consumersindexer.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.UUID;

@Document(indexName = "customers")
public class CustomerEntry
{
    @Id
    private String id;

    @Getter
    @Setter
    private String customerId;

    @Getter
    @Setter
    private String firstName;

    @Getter
    @Setter
    private String lastName;
}
