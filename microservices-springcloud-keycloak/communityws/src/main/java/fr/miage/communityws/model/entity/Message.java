package fr.miage.communityws.model.entity;

public class Message {

    private String idMember;
    private String content;
    private Integer index;

    public Message(String idMember, String content, Integer index) {
        this.idMember = idMember;
        this.content = content;
        this.index = index;
    }

    public Message() {
    }

    public String getIdMember() {
        return idMember;
    }

    public void setIdMember(String member) {
        this.idMember = member;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }
}
