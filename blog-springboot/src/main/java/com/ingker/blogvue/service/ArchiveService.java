package com.ingker.blogvue.service;

import com.ingker.blogvue.dto.ArchiveListDTO;
import com.ingker.blogvue.entity.Archive;
import com.ingker.blogvue.mapper.ArchiveMapper;
import com.ingker.blogvue.mapper.ArchiveRelationshipMapper;
import com.ingker.blogvue.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class ArchiveService {
    @Autowired
    ArchiveMapper archiveMapper;

    @Autowired
    ArchiveRelationshipMapper archiveRelationshipMapper;

    /**
     * 排序字段值映射到具体的数据库列名，防止错误输入和SQL注入
     * */
    public String mapSortField(String sortField) {
        // 定义前端字段和数据库列名的映射关系
        Map<String, String> fieldMapping = new HashMap<>();
        fieldMapping.put("id", "archive_id");
        fieldMapping.put("name", "archive_name");
        fieldMapping.put("count", "count(arr.archive_id)");
        // 返回数据库列名，默认为 id
        return fieldMapping.getOrDefault(sortField, "archive_name");
    }

    @Transactional
    public Page<ArchiveListDTO> listWithCount(Integer size, Integer page, String sort, String order, String taxonomy) {
        // 验证排序方式，只允许限定的字符串，防止SQL注入
        String dbSortField = mapSortField(sort);
        if (!Arrays.asList("asc", "desc", "ASC", "DESC").contains(order)) {
            order = "asc"; // 默认排序顺序
        }

        Integer offset = size * (page - 1); // page 从 1 开始
        // 查询分页数据
        List<ArchiveListDTO> archives = archiveMapper.getByTaxonomyWithCount(taxonomy, dbSortField, order, size, offset);
        // 查询符合条件的总记录数
        int total = archiveMapper.countByTaxonomy(taxonomy);

        System.out.printf("分页查询归档：%s，页数：%d，每页个数：%d，总记录数：%d，排序：%s，顺序：%s%n",
                taxonomy, page, size, total, sort, order);
        return new Page<>(archives, total, page, size);
    }

    @Transactional
    public Page<Archive> list(Integer size, Integer page, String sort, String order,String taxonomy) {
        // 验证排序方式，只允许限定的字符串，防止SQL注入
        String dbSortField = mapSortField(sort);
        if (!Arrays.asList("asc", "desc", "ASC", "DESC").contains(order)) {
            order = "asc"; // 默认排序顺序
        }

        Integer offset = size * (page - 1); // page 从 1 开始
        // 查询分页数据
        List<Archive> archives = archiveMapper.getByTaxonomy(taxonomy, dbSortField, order, size, offset);
        // 查询符合条件的总记录数
        int total = archiveMapper.countByTaxonomy(taxonomy);

        System.out.printf("分页查询归档：%s，页数：%d，每页个数：%d，总记录数：%d，排序：%s，顺序：%s%n",
                taxonomy, page, size, total, sort, order);
        return new Page<>(archives, total, page, size);
    }

    @Transactional
    public void add(Archive archive) {
        try {
            archiveMapper.add(archive);
            System.out.println("添加归档成功: " + archive);
        } catch (Exception e)
        {
            System.out.println("添加归档失败: " + e.getMessage());
            throw new RuntimeException(e); // 重新抛出
        }
    }

    @Transactional
    public void delete(Integer id) {
        try {
            archiveMapper.delete(id);
            archiveRelationshipMapper.deleteByArchiveId(id);
            System.out.println("删除归档成功, ID: " + id);
        } catch (Exception e) {
            System.out.println("删除归档失败: " + e.getMessage());
            throw new RuntimeException(e); // 重新抛出
        }
    }

    @Transactional
    public void update(Archive archive) {
        try {
            archiveMapper.update(archive);
            System.out.println("更新归档成功: " + archive);
        } catch (Exception e) {
            System.out.println("更新归档失败: " + e.getMessage());
            throw new RuntimeException(e); // 重新抛出
        }
    }

}
