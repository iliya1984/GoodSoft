package com.goodsoft.loggingconsumer.models;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LogFileEntry
{
    @Getter
    @Setter
    private String correlationId;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private String dateCreated;

    @Getter
    @Setter
    private String dateUpdated;

    @Getter
    @Setter
    private List<LogRecordEntry> records;
}
