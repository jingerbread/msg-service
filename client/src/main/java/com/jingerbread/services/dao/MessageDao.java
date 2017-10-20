package com.jingerbread.services.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageDao extends JpaRepository<MessageEntity, Long> {

    @Query("select m from MessageEntity m where m.author = :author")
    List<MessageEntity> listMessages(@Param("author") String author);
}
