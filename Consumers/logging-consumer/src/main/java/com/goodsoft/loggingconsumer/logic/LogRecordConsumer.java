package com.goodsoft.loggingconsumer.logic;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.goodsoft.infra.modulecore.logging.models.LogRecordMessage;
import com.goodsoft.loggingconsumer.models.LogFileEntry;
import com.goodsoft.loggingconsumer.models.LogRecordEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@Component
public class LogRecordConsumer
{
    @Autowired
    private Environment _environment;

    public void receiveMessage(LogRecordMessage message) throws IOException
    {
        var date = new Date(System.currentTimeMillis());
        var formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        var formattedDate = formatter.format(date);

        var mapper = new ObjectMapper();

        var filePath = createFilePath(date ,message);
        File file = new File(filePath);
        String content = "";
        LogFileEntry logFile = null;

        if(false == file.exists())
        {
            createNewFile(message, filePath, file, formattedDate, mapper);
        }

        var existingContent = Files.readString(Paths.get(filePath));

        logFile = mapper.readValue(existingContent, LogFileEntry.class);

        var records = logFile.getRecords();
        if(records == null)
        {
            records = new ArrayList<>();
            logFile.setRecords(records);
        }

        var logRecord = mapToLogRecordEntry(formattedDate, message);
        records.add(0, logRecord);

        content = mapper.writeValueAsString(logFile);

        Files.write(Paths.get(filePath), content.getBytes(), StandardOpenOption.WRITE);
    }

    private void createNewFile(LogRecordMessage message, String filePath, File file, String formattedDate, ObjectMapper mapper) throws IOException
    {
        file.getParentFile().getParentFile().mkdir();
        file.getParentFile().mkdir();
        file.createNewFile();

        var logFile = new LogFileEntry();
        logFile.setDateCreated(formattedDate);
        logFile.setDateUpdated(formattedDate);
        logFile.setRecords(new ArrayList<>());

        if(message != null && message.getMetadata() != null)
        {
            logFile.setCorrelationId(message.getMetadata().getCorrelationId());
        }

        var path = Paths.get(filePath);
        logFile.setName(path.getFileName().toString());

        var content =  mapper.writeValueAsString(logFile);

        Files.write(Paths.get(filePath), content.getBytes(), StandardOpenOption.APPEND);
    }

    private String createFilePath(Date date, LogRecordMessage message)
    {

        var formatter = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");

        var fileNameSyfix = formatter.format(date);
        if(message != null && message.getMetadata() != null)
        {
            var correlationId = message.getMetadata().getCorrelationId();
            if(correlationId != null && false == correlationId.isEmpty())
            {
                fileNameSyfix = correlationId;
            }
        }

        var outputDirectory = _environment.getProperty("outputdirectory");

        var dateFormatter = new SimpleDateFormat("yyyy-MM-dd");

        var filePath = outputDirectory + "\\" + dateFormatter.format(date) + "\\Logs_" + fileNameSyfix + ".json";
        return filePath;
    }

    private LogRecordEntry mapToLogRecordEntry(String formattedDate, LogRecordMessage message)
    {
        var entry = new LogRecordEntry();

        var metadata = message.getMetadata();
        if(metadata != null )
        {
            entry.setCorrelationId(metadata.getCorrelationId());
            entry.setDomainName(metadata.getDomainName());
            entry.setModuleName(metadata.getModuleName());
        }

        entry.setMessage(message.getMessage());
        entry.setCode(message.getCode());
        entry.setLevel(message.getLevel());
        entry.setStackTrace(message.getStackTrace());
        entry.setDateCreated(formattedDate);

        return entry;
    }
}
