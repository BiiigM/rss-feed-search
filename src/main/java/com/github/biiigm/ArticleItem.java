package com.github.biiigm;

import com.github.biiigm.annotations.XMLClass;
import com.github.biiigm.annotations.XMLNode;

@XMLClass
public class ArticleItem {

    @XMLNode(name = "title")
    private String title;
    @XMLNode(name = "link")
    private String link;
    @XMLNode(name = "pubDate")
    private String publishDate;
    @XMLNode(name = "description")
    private String description;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Title: " + title + "\n" + "Link: " + link + "\n" + "Published"
                + " at: " + publishDate + "\n" + "Description: " + description + "\n";
    }
}
