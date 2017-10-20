package com.jingerbread.services;

import com.jingerbread.services.dao.MessageDao;
import com.jingerbread.services.dao.MessageEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Stream;

@Service
@Transactional
public class MessageDaoService {

    @Autowired
    private MessageDao messageDao;

    @Transactional(readOnly = true)
    public Stream<MessageEntity> streamMessages(String author) {
        return messageDao.listMessages(author).stream();
    }
}
