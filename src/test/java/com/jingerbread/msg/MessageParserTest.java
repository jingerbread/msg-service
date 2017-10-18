package com.jingerbread.msg;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.jingerbread.controllers.MessageController;
import com.jingerbread.msg.utils.JSONReader;
import com.jingerbread.response.MessageResponseRoot;
import com.jingerbread.response.MessageValidationError;
import com.jingerbread.response.OperationStatus;
import com.jingerbread.response.ValidationError;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class MessageParserTest {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Autowired
    private MessageController messageController;

    @Test
    public void testSerializeMessages() throws Exception {
        Messages messages = new Messages(Collections.singletonList(new Message("Hello", "Hello World!", "Mike", "20.07.2017")));
        String json = MAPPER.writeValueAsString(messages);
        log.info("{}", json);
        JSONReader<Messages> jsonReader = new JSONReader<>(Messages.class);
        Messages result = jsonReader.readJSON(json);
        Assert.assertNotNull(result);
        Assert.assertEquals("Hello World!", result.getValues().get(0).getText());
    }

    @Test
    public void testSerializeErrors() throws JsonProcessingException {
        MessageValidationError error1 = new MessageValidationError(0, Arrays.asList(new ValidationError("title", "field is empty"), new ValidationError("text", "field is empty")));
        List<MessageValidationError> validationErrors = Collections.singletonList(error1);

        MessageResponseRoot responseRoot = MessageResponseRoot.validationError(OperationStatus.VALIDATION_ERROR, validationErrors);
        MAPPER.enable(SerializationFeature.INDENT_OUTPUT);
        String json = MAPPER.writeValueAsString(responseRoot);
        Assert.assertNotNull(json);
        log.info("{}", json);
    }

    @Test
    public void testValidationErrors() throws JsonProcessingException {
        Messages messages = new Messages(Collections.singletonList(new Message("", null, "Mike", "20.07.2017")));
        String msgJson = MAPPER.writeValueAsString(messages);
        log.info("{}", msgJson);

        MessageResponseRoot responseRoot = messageController.addMessages(msgJson);

        Assert.assertFalse(responseRoot.isSuccessful());

        MAPPER.enable(SerializationFeature.INDENT_OUTPUT);
        String responseJson = MAPPER.writeValueAsString(responseRoot);
        log.info("{}", responseJson);
    }

    @Test
    public void testReceivedMessageSerialize() throws JsonProcessingException {
        Message msg = new Message("Hello", "Hello World!", "Mike", "20.07.2017");
        ReceivedMessage receivedMessage = new ReceivedMessage(msg, new Date());

        String json = MAPPER.writeValueAsString(receivedMessage);
        Assert.assertNotNull(json);
        log.info("{}", json);
    }
}
