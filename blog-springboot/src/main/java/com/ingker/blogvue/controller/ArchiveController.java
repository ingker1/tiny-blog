package com.ingker.blogvue.controller;

import com.ingker.blogvue.dto.ArchiveListDTO;
import com.ingker.blogvue.entity.Archive;
import com.ingker.blogvue.service.ArchiveService;
import com.ingker.blogvue.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class ArchiveController {
    @Autowired
    ArchiveService archiveService;

    @GetMapping("/admin/archives/count")
    public ResponseEntity<Page<ArchiveListDTO>> listWithCount(
             @RequestParam(value="page", defaultValue = "0") Integer page,
             @RequestParam(value="limit", defaultValue = "10") Integer limit,
             @RequestParam(value="sort", defaultValue = "archive_name") String sort,
             @RequestParam(value="order", defaultValue = "desc") String order,
             @RequestParam(value="taxonomy") String taxonomy) {
        page = page<0?0:page;
        try {
            Page<ArchiveListDTO> archivePage = archiveService.listWithCount(limit, page, sort, order, taxonomy);
            return ResponseEntity.ok(archivePage); // 返回 200 和查询结果
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null); // 返回 500 错误
        }
    }

    @GetMapping("/admin/archives")
    public ResponseEntity<Page<Archive>> list(
            @RequestParam(value="page", defaultValue = "0") Integer page,
            @RequestParam(value="limit", defaultValue = "10") Integer limit,
            @RequestParam(value="sort", defaultValue = "archive_name") String sort,
            @RequestParam(value="order", defaultValue = "desc") String order,
            @RequestParam(value="taxonomy") String taxonomy) {
        page = page<0?0:page;
        try {
            Page<Archive> archivePage = archiveService.list(limit, page, sort, order, taxonomy);
            return ResponseEntity.ok(archivePage); // 返回 200 和查询结果
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null); // 返回 500 错误
        }
    }

    @PostMapping("/admin/archives")
    public ResponseEntity<Object> add(@RequestBody Archive bean) {
        try {
            archiveService.add(bean);
            return ResponseEntity.status(HttpStatus.CREATED).body(bean); // 返回 201 和创建的评论对象
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR) // 返回 500 错误
                    .body("添加归档失败" + e.getMessage());
        }
    }

    @DeleteMapping("/admin/archives/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") Integer id) {
        try {
            archiveService.delete(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); // 返回 204 无内容
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR) // 返回 500 错误
                    .body("删除归档失败" + e.getMessage());
        }
    }

    @PutMapping("/admin/archives")
    public ResponseEntity<Object> update(@RequestBody Archive bean) {
        try {
            archiveService.update(bean);
            return ResponseEntity.ok(bean); // 返回 200 和更新的评论对象
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR) // 返回 500 错误
                    .body("更新归档失败" + e.getMessage());
        }
    }
}
