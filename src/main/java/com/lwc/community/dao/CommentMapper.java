package com.lwc.community.dao;

import com.lwc.community.entity.Comment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author 刘文长
 * @version 1.0
 */
@Mapper
public interface CommentMapper {


    List<Comment> selectCommentsByEntity(int entityType, int entityId, int offset, int limit);

    int selectCountByEntity(int entityType, int entityId);

    int insertComment(Comment comment);
    Comment selectCommentById(int id);
}
