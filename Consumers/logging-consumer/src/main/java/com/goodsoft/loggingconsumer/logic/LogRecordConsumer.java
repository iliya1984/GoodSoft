package com.goodsoft.loggingconsumer.logic;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.goodsoft.infra.modulecore.logging.models.LogRecordMessage;
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
import java.util.Date;

@Component
public class LogRecordConsumer
{
    @Autowired
    private Environment _environment;

    public void receiveMessage(LogRecordMessage message) throws IOException {

        var filePath = createFilePath(message);
        File file = new File(filePath);

        if(false == file.exists())
        {
            file.getParentFile().getParentFile().mkdir();
            file.getParentFile().mkdir();
            file.createNewFile();
        }

        var logRecordEntry = mapToLogRecordEntry(message);

        var mapper = new ObjectMapper();
        var content = mapper.writeValueAsString(logRecordEntry);

        Files.write(Paths.get(filePath), content.getBytes(), StandardOpenOption.APPEND);
    }

    private String createFilePath(LogRecordMessage message)
    {
        var date = new Date(System.currentTimeMillis());
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

    private LogRecordEntry mapToLogRecordEntry(LogRecordMessage message)
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

        return entry;
    }
}
