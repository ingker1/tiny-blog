package com.ingker.blogvue.util;

import java.util.List;

public class Page<T> {

    private final List<T> content;  // 当前页数据
    private final int totalElements; // 总记录数
    private final int totalPages;    // 总页数
    private final int currentPage;   // 当前页
    private final int size;          // 每页大小

    public Page(List<T> content, int totalElements, int currentPage, int size) {
        this.content = content;
        this.totalElements = totalElements;
        this.currentPage = currentPage;
        this.size = size;
        this.totalPages = (int) Math.ceil((double) totalElements / size); // 计算总页数
    }

    // Getters and Setters
    public List<T> getContent() {
        return content;
    }

    public int getTotalElements() {
        return totalElements;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public int getSize() {
        return size;
    }
}
