package com.goodsoft.infra.gscore.logging.implementations;

import com.goodsoft.infra.gscore.logging.abstractions.ILogger;
import com.goodsoft.infra.gscore.logging.abstractions.ILoggerFactory;
import com.goodsoft.infra.gscore.logging.models.RequestMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class LoggerFactory implements ILoggerFactory
{
    @Autowired
    private ApplicationContext context;

    @Override
    public ILogger create(String correlationId)
    {
        var logger = (ILogger)context.getBean("logger");

        logger.setCorrelationId(correlationId);

        return logger;
    }
}
