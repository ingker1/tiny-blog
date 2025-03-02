package com.ingker.blogvue.controller;

import com.ingker.blogvue.dto.CommentDTO;
import com.ingker.blogvue.dto.CommentListDTO;
import com.ingker.blogvue.entity.Comment;
import com.ingker.blogvue.service.CommentService;
import com.ingker.blogvue.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class CommentController {
    @Autowired
    CommentService commentService;

    @GetMapping("/comments/{id}")
    public ResponseEntity<List<CommentDTO>> get(@PathVariable("id") Integer articleId) {
        try {
            List<CommentDTO> comments = commentService.listByArticle(articleId);
            return ResponseEntity.ok(comments); // 返回 200 和查询结果
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null); // 返回 500 错误
        }
    }

    @GetMapping("/admin/comments")
    public ResponseEntity<Page<CommentListDTO>> getAll(
            @RequestParam(value="page", defaultValue = "0") Integer page,
            @RequestParam(value="limit", defaultValue = "10") Integer limit,
            @RequestParam("sort") String sort,
            @RequestParam(value="order", defaultValue = "desc") String order) {
        try {
            Page<CommentListDTO> comments = commentService.list(page, limit, sort, order);
            return ResponseEntity.ok(comments); // 返回 200 和查询结果
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null); // 返回 500 错误
        }
    }

    @PostMapping("/comments")
    public ResponseEntity<Object> add(@RequestBody Comment bean) {
        try {
            commentService.add(bean);
            return ResponseEntity.status(HttpStatus.CREATED).body(bean); // 返回 201 和创建的评论对象
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("添加评论失败: " + e.getMessage()); // 返回 500 错误
        }
    }

    @DeleteMapping("/admin/comments/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") Integer id) {
        try {
            commentService.delete(id);
            return ResponseEntity.noContent().build(); // 返回 204 无内容
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("删除评论失败: " + e.getMessage()); // 返回 500 错误
        }
    }

    @GetMapping("/admin/comments/unread")
    public ResponseEntity<Integer> countUnread() {
        try {
            return ResponseEntity.ok(commentService.getUnread()); // 返回 200 和查询结果
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null); // 返回 500 错误
        }
    }

    @PutMapping("/admin/comments/read/{id}")
    public ResponseEntity<Object> readMark(@PathVariable("id") Integer id) {
        try {
            commentService.readMark(id);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(null); // 返回 200
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null); // 返回 500 错误
        }
    }

    @PutMapping("/admin/comments/trash/{id}")
    public ResponseEntity<Object> trashMark(@PathVariable("id") Integer id) {
        try {
            commentService.trashMark(id);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(null); // 返回 200
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null); // 返回 500 错误
        }
    }

    @PutMapping("/admin/comments/recycle/{id}")
    public ResponseEntity<Object> recycle(@PathVariable("id") Integer id) {
        try {
            commentService.recycle(id);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(null); // 返回 200
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null); // 返回 500 错误
        }
    }
}
