package com.androidsoft.mynotes_2017144235.pojo;

public class Note {
    private long noteId;
    private String userPhone;
    private String content;
    private String time;

    public Note() {
    }
    public Note(String userPhone, String content, String time) {
        this.userPhone= userPhone;
        this.content = content;
        this.time = time;
    }

    public Note(long noteId, String userPhone, String content, String time) {
        this.noteId = noteId;
        this.userPhone = userPhone;
        this.content = content;
        this.time = time;
    }

    public long getNoteId() {
        return noteId;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public String getContent() {
        return content;
    }

    public String getTime() {
        return time;
    }

    public void setNoteId(long noteId) {
        this.noteId = noteId;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Note{" +
                "noteId=" + noteId +
                ", userPhone='" + userPhone + '\'' +
                ", content='" + content + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
