package com.loloao.vo;

import com.loloao.entity.Article;

public class ArticleVo extends Article {

    private Integer year;

    private Integer month;

    private Long tagId;

    private Long categoryId;

    private Integer count;

    private String keyword;

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Long getTagId() {
        return tagId;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public String toString() {
        return "ArticleVo{" +
                "year='" + year + '\'' +
                ", month='" + month + '\'' +
                ", tagId=" + tagId +
                ", categoryId=" + categoryId +
                ", count=" + count +
                ", keyword=" + keyword +
                '}';
    }
}