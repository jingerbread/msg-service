package com.jingerbread.data.msg;

import com.jingerbread.controllers.MessageOutputEventSource;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith(SpringRunner.class)
@TestPropertySource(properties = { "test.value=test" })
@SpringBootTest
public class MessageSenderTest {

    @Autowired
    private MessageOutputEventSource eventSource;

    @Value("${test.value}")
    private String testValue;

    @Test
    public void testProperties() {
        Assert.assertEquals("test", testValue);
    }

    @Test
    public void testSendMessage() {
        eventSource.sendText("Test message");
    }
}
