package com.jingerbread.service;

import com.jingerbread.data.Message;
import com.jingerbread.data.ReceivedMessage;
import com.jingerbread.data.Utils;
import com.jingerbread.services.MessageDaoService;
import com.jingerbread.services.MessageProcessorService;
import com.jingerbread.services.dao.MessageDao;
import com.jingerbread.services.dao.MessageEntity;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@DataJpaTest
public class MessageProcessorServiceTest {

    @Autowired
    private MessageDao dao;

    @Autowired
    private MessageDaoService daoService;

    @Autowired
    private MessageProcessorService processorService;

    @Test
    @Transactional
    public void testMessageDao() {
        MessageEntity messageEntity = new MessageEntity(new ReceivedMessage(
                new Message("title", "text", "author", "2017-10-21"), new Date()), new Date());
        dao.save(messageEntity);
        List<MessageEntity> msgList = daoService.streamMessages("author").collect(Collectors.toList());
        Assert.assertFalse(msgList.isEmpty());
    }

    @Test
    public void testMessageProccessing() {
        List<MessageEntity> msgList = daoService.streamMessages("author").collect(Collectors.toList());
        Assert.assertTrue(msgList.isEmpty());

        ReceivedMessage msg = new ReceivedMessage(new Message("title", "text", "author", "2017-10-21"), new Date());
        String json = Utils.toString(msg);
        processorService.handle(json);

        msgList = daoService.streamMessages("author").collect(Collectors.toList());
        Assert.assertFalse(msgList.isEmpty());
    }
}
