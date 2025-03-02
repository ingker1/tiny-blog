package com.ingker.blogvue.service;

import com.ingker.blogvue.dto.CommentDTO;
import com.ingker.blogvue.dto.CommentListDTO;
import com.ingker.blogvue.entity.Comment;
import com.ingker.blogvue.mapper.ArticleMapper;
import com.ingker.blogvue.mapper.CommentMapper;
import com.ingker.blogvue.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class CommentService {
    @Autowired
    CommentMapper commentMapper;

    @Autowired
    ArticleMapper articleMapper;

    @Transactional
    public List<CommentDTO> listByArticle(Integer articleId) {
        // 获取所有评论
        List<Comment> allComments = commentMapper.getByArticleId(articleId);

        // 根据父子关系构建评论树
        List<CommentDTO> rootComments = buildCommentTree(allComments);

        // 展开所有子评论，包括子评论的子评论
        for (CommentDTO rootComment : rootComments) {
            flattenAllChildren(rootComment);
        }

        return rootComments;
    }

    // 根据父子关系构建评论树
    private List<CommentDTO> buildCommentTree(List<Comment> allComments) {
        // 将评论转换为DTO
        Map<Integer, CommentDTO> allCommentDTOs = convertToCommentDTOMap(allComments);

        List<CommentDTO> rootComments = new ArrayList<>();

        // 遍历所有评论DTO，将子评论嵌套到父评论
        for (CommentDTO commentDTO : allCommentDTOs.values()) {
            if (commentDTO.getParentId() == 0) {
                // 根评论，添加到根评论列表
                rootComments.add(commentDTO);
            } else {
                // 子评论，嵌套到对应的父评论
                CommentDTO parentCommentDTO = allCommentDTOs.get(commentDTO.getParentId());
                if (parentCommentDTO != null) {
                    parentCommentDTO.getChildren().add(commentDTO);
                }
            }
        }

        return rootComments;
    }


    // 将评论列表转换为评论DTO的Map
    private Map<Integer, CommentDTO> convertToCommentDTOMap(List<Comment> allComments) {
        Map<Integer, CommentDTO> allCommentDTOs = new HashMap<>();

        // 遍历所有评论，创建DTO并填充数据
        for (Comment comment : allComments) {
            CommentDTO commentDTO = new CommentDTO();
            commentDTO.setCommentId(comment.getCommentId());
            commentDTO.setContent(comment.getContent());
            commentDTO.setAuthor(comment.getAuthor());
            commentDTO.setPublishDate(comment.getPublishDate());
            commentDTO.setAnonymousStatus(comment.getAnonymousStatus());
            commentDTO.setParentId(comment.getParentId());
            commentDTO.setArticleId(comment.getArticleId());
            commentDTO.setStatus(comment.getStatus());
            commentDTO.setChildren(new ArrayList<>());
            allCommentDTOs.put(comment.getCommentId(), commentDTO);
        }

        return allCommentDTOs;
    }


    // 展开所有的子评论，包括子评论的后代评论
    private void flattenAllChildren(CommentDTO parentComment) {
        // 新建一个临时列表，包含所有直接和间接的子评论
        List<CommentDTO> flattenedChildren = new ArrayList<>();

        for (CommentDTO child : parentComment.getChildren()) {
            flattenedChildren.add(child);  // 添加当前子评论
            // 递归处理更深层次的子评论
            flattenAllChildren(child);
            // 将该评论的所有子评论（包括更深层次的子评论）加到flattenedChildren中
            flattenedChildren.addAll(child.getChildren());
            child.setChildren(new ArrayList<>());  // 清空子评论的子评论
        }

        // 设置当前父评论的子评论为展开后的所有子评论
        parentComment.setChildren(flattenedChildren);
    }

    @Transactional
    public Page<CommentListDTO> list(Integer page, Integer size, String sort, String order) {
        if (!Arrays.asList("asc", "desc", "ASC", "DESC").contains(order)) {
            order = "desc"; // 默认排序顺序
        }
        sort = "publish_date";

        Integer offset = size * (page - 1); // page 从 1 开始
        List<CommentListDTO> comments = commentMapper.getAll(sort, order, size, offset);
        Integer total = commentMapper.count();

        System.out.printf("分页查询所有评论，页数：%d，每页个数：%d，总记录数：%d，排序：发布时间，顺序：%s%n",
                page, size, total, order);
        return new Page<>(comments, total, page, size);
    }

    @Transactional
    public void add(Comment comment) {
        try {
            commentMapper.add(comment);
            System.out.println("添加评论成功: " + comment);
        } catch (Exception e) {
            System.out.println("添加评论失败: " + e.getMessage());
            throw new RuntimeException(e); // 重新抛出
        }
    }

    @Transactional
    public void delete(Integer id) {
        try {
            commentMapper.delete(id);
            System.out.println("删除评论成功, ID: " + id);
        } catch (Exception e) {
            System.out.println("删除评论失败: " + e.getMessage());
            throw new RuntimeException(e); // 重新抛出
        }
    }

    @Transactional
    public Integer getUnread() {
        return commentMapper.countUnread();
    }

    @Transactional
    public void readMark(Integer id) {
        try {
            commentMapper.readMark(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional
    public void trashMark(Integer id) {
        try {
            commentMapper.trashMark(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional
    public void recycle(Integer id) {
        try {
            commentMapper.recycle(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
