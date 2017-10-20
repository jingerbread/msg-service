package com.jingerbread.services.dao;

import com.jingerbread.data.Utils;
import com.jingerbread.data.Message;
import com.jingerbread.data.ReceivedMessage;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Setter
@Entity
@Table(name = "messages")
public class MessageEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String text;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private Date created;

    @Column(nullable = false)
    private Date serverReceived;

    @Column(nullable = false)
    private Date clientReceived;

    public MessageEntity(ReceivedMessage message, Date clientReceived) {
        Message msg = message.getMessage();
        this.author = msg.getAuthor();
        this.text = msg.getText();
        this.title = msg.getTitle();
        LocalDate localDate = Utils.parseDate(msg.getCreated());
        if (localDate != null) {
            this.created = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        }
        this.serverReceived = message.getReceived();
        this.clientReceived = clientReceived;
    }
}
