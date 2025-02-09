/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

public class PostDetail {

    private int postDetailId;
    private int postId;
    private String content;

    public PostDetail() {
    }

    public PostDetail(int postDetailId, int postId, String content) {
        this.postDetailId = postDetailId;
        this.postId = postId;
        this.content = content;
    }

    public int getPostDetailId() {
        return postDetailId;
    }

    public void setPostDetailId(int postDetailId) {
        this.postDetailId = postDetailId;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "PostDetail{" + "postDetailId=" + postDetailId + ", postId=" + postId + ", content='" + content + '\'' + '}';
    }
}
