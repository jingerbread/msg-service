package com.jingerbread.services;

import com.jingerbread.MessageSink;
import com.jingerbread.data.ReceivedMessage;
import com.jingerbread.data.Utils;
import com.jingerbread.services.dao.MessageDao;
import com.jingerbread.services.dao.MessageEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.Date;

@Slf4j
@Component
@EnableBinding(MessageSink.class)
public class MessageProcessorService {

    @Autowired
    private MessageDao messageDao;

    @Autowired
    private PlatformTransactionManager txManager;

    @StreamListener(MessageSink.INPUT)
    public void handle(Object input) {//todo add json/msg converter
        String messageString = Utils.readMessage(input);
        if (messageString == null) {
            return;
        }
        log.info("Received message: {}", messageString);
        ReceivedMessage msg = Utils.parse(messageString);
        if (msg == null) {
            log.error("Message {} wasn't parsed", messageString);
            return;
        }
        Date clientReceived = new Date();
        MessageEntity messageEntity = new MessageEntity(msg, clientReceived);

        TransactionDefinition transactionDefinition = new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = txManager.getTransaction(transactionDefinition);
        try {
            messageDao.save(messageEntity);
            txManager.commit(status);
        } catch (Exception e) {
            log.error("Can't save message {}", messageEntity, e);
            txManager.rollback(status);
        }
    }


}
