package com.ingker.blogvue.mapper;


import com.ingker.blogvue.dto.ArchiveListDTO;
import com.ingker.blogvue.dto.ArticleArchive;
import com.ingker.blogvue.entity.Archive;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface ArchiveMapper {
    /**
     *
     * 根据文章id查询存档分类*/
    @Select("""
            SELECT ar.*
            FROM archive ar
            INNER JOIN archive_relationship arr
            ON ar.archive_id = arr.archive_id
            INNER JOIN article at
            ON at.article_id = arr.article_id
            WHERE at.article_id = #{articleId}
            AND ar.taxonomy = #{taxonomy}""")
    List<Archive> getByArticleId(Integer articleId, String taxonomy);

    @Select("""
            <script>
            SELECT at.article_id, ar.archive_id, ar.archive_name, ar.taxonomy
            FROM archive ar
            INNER JOIN archive_relationship arr
            ON ar.archive_id = arr.archive_id
            INNER JOIN article at
            ON at.article_id = arr.article_id
            WHERE at.article_id IN
            <foreach collection='articleIds' item='articleId' open='(' separator=',' close=')'>
                #{articleId}
            </foreach>
            AND ar.taxonomy = #{taxonomy}
            </script>""")
    @Results({
            @Result(column = "article_id", property = "articleId"),  // 映射 articleId
            @Result(column = "archive_id", property = "archive.archiveId"),  // 映射 Archive 的 archiveId
            @Result(column = "archive_name", property = "archive.archiveName"),  // 映射 Archive 的 name
            @Result(column = "taxonomy", property = "archive.taxonomy")  // 映射 Archive 的 taxonomy
    })
    List<ArticleArchive> getByArticleIds(List<Integer> articleIds, String taxonomy);

    /**
     * 根据分类方法查询存档分类
     * 返回分类名称和其总数
     * */
    @Select("""
            SELECT ar.archive_id, ar.archive_name, ar.taxonomy, COUNT(arr.archive_id)
            FROM archive ar
            LEFT JOIN archive_relationship arr ON ar.archive_id = arr.archive_id
            WHERE ar.taxonomy = #{taxonomy}
            GROUP BY ar.archive_id, ar.archive_name, ar.taxonomy
            ORDER BY ${sort} ${order}
            limit #{limit} offset #{offset}
            """)
    List<ArchiveListDTO> getByTaxonomyWithCount(String taxonomy, String sort, String order, Integer limit, Integer offset);

    @Select("""
            SELECT ar.archive_id, ar.archive_name, ar.taxonomy, COUNT(arr.archive_id)
            FROM archive ar
            LEFT JOIN archive_relationship arr ON ar.archive_id = arr.archive_id
            WHERE ar.taxonomy = #{taxonomy}
            GROUP BY ar.archive_id, ar.archive_name, ar.taxonomy
            ORDER BY ${sort} ${order}
            limit #{limit} offset #{offset}
            """)
    List<Archive> getByTaxonomy(String taxonomy, String sort, String order, Integer limit, Integer offset);
    @Select("""
            SELECT COUNT(*) AS total_count
            FROM (
                SELECT ar.archive_name
                FROM archive ar
                LEFT JOIN archive_relationship arr ON ar.archive_id = arr.archive_id
                WHERE ar.taxonomy = #{taxonomy}
                GROUP BY ar.archive_id, ar.archive_name, ar.taxonomy
            ) AS grouped
            """)
    Integer countByTaxonomy(String taxonomy);

    /**
     * archive_name和taxonomy有唯一性约束
     * DUPLICATE KEY UPDATE，即使数据已经存在，也不会插入新记录，而是更新已有记录。
     * 使用 LAST_INSERT_ID(archiveId)，这样不管是新插入的还是已存在的，都能返回 archiveId
     * */
    @Insert("""
            INSERT INTO archive(archive_name, taxonomy)
            VALUES(#{archiveName}, #{taxonomy})
            ON DUPLICATE KEY UPDATE
            archive_name = VALUES(archive_name), taxonomy = VALUES(taxonomy),
            archive_id = LAST_INSERT_ID(archive_id)
            """)
    @Options(useGeneratedKeys = true, keyProperty = "archiveId")
    void add(Archive archive);

    @Delete("DELETE FROM archive WHERE archive_id = #{id}")
    void delete(Integer id);

    @Update("UPDATE archive SET archive_name = #{archiveName}, taxonomy = #{taxonomy} WHERE archive_id = #{archiveId}")
    void update(Archive archive);
}
