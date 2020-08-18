package com.longcheng.volunteer.modular.mine.relationship.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Burning on 2018/9/3.
 */

public class RelationshipBookData {
    @SerializedName("chatuser")
    protected RelationshipUser user;
    @SerializedName("my_book_info")
    protected RelationshipBook book;

    public RelationshipUser getUser() {
        return user;
    }

    public void setUser(RelationshipUser user) {
        this.user = user;
    }

    public RelationshipBook getBook() {
        return book;
    }

    public void setBook(RelationshipBook book) {
        this.book = book;
    }
}
