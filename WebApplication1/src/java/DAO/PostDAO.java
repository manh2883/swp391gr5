package DAO;

import DBContext.DBContext;
import Models.Post;
import Models.PostDetail;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class PostDAO extends DBContext {

    public List<Post> getLatestPosts(int limit) {
        String query = "SELECT * FROM Post WHERE status = 1 ORDER BY created_at DESC LIMIT ?;";
        List<Post> posts = new ArrayList<>();

        try {
            DBContext db = new DBContext();
            java.sql.Connection con = db.getConnection();
            PreparedStatement stm = con.prepareStatement(query);
            stm.setInt(1, limit);
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                Post post = new Post();
                post.setPostId(rs.getInt("post_id"));
                post.setTitle(rs.getString("title"));
                post.setStatus(rs.getInt("status"));
                post.setViewCount(rs.getInt("view_count"));
                post.setCreatedAt(rs.getTimestamp("created_at"));
                post.setUpdatedAt(rs.getTimestamp("updated_at"));
                post.setCreatedBy(rs.getInt("created_by"));
                post.setUpdatedBy(rs.getInt("updated_by"));
                post.setPublishedAt(rs.getTimestamp("published_at"));
                posts.add(post);
            }

            rs.close();
            stm.close();
            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return posts;
    }

    public Post getPostById(int postId) {
        String query = "SELECT * FROM Post WHERE post_id = ?";
        try {
            DBContext db = new DBContext();
            java.sql.Connection con = db.getConnection();
            PreparedStatement stm = con.prepareStatement(query);
            stm.setInt(1, postId);
            ResultSet rs = stm.executeQuery();

            if (rs.next()) {
                Post post = new Post();
                post.setPostId(rs.getInt("post_id"));
                post.setTitle(rs.getString("title"));
                post.setStatus(rs.getInt("status"));
                post.setViewCount(rs.getInt("view_count"));
                post.setCreatedAt(rs.getTimestamp("created_at"));
                post.setUpdatedAt(rs.getTimestamp("updated_at"));
                post.setCreatedBy(rs.getInt("created_by"));
                post.setUpdatedBy(rs.getInt("updated_by"));
                post.setPublishedAt(rs.getTimestamp("published_at"));
                return post;
            }

            rs.close();
            stm.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void incrementViewCount(int postId) {
        String query = "UPDATE Post SET view_count = view_count + 1 WHERE post_id = ?";
        try {
            DBContext db = new DBContext();
            java.sql.Connection con = db.getConnection();
            PreparedStatement stm = con.prepareStatement(query);
            stm.setInt(1, postId);
            stm.executeUpdate();
            stm.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public PostDetail getPostDetailByPostId(int postId) {
        String query = "SELECT * FROM post_detail WHERE post_id = ?;";
        try {
            DBContext db = new DBContext();
            java.sql.Connection con = db.getConnection();
            PreparedStatement stm = con.prepareStatement(query);
            stm.setInt(1, postId);

            System.out.println("Executing query: " + query + " with postId=" + postId);

            ResultSet rs = stm.executeQuery();

            if (rs.next()) {
                PostDetail postDetail = new PostDetail();
                postDetail.setPostDetailId(rs.getInt("post_detail_id"));
                postDetail.setPostId(rs.getInt("post_id"));
                postDetail.setContent(rs.getString("content"));

                System.out.println("Found PostDetail: " + postDetail);
                return postDetail;
            } else {
                System.out.println("No PostDetail found for postId=" + postId);
            }

            rs.close();
            stm.close();
            con.close();
        } catch (SQLException e) {
            System.out.println("Error getting PostDetail: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
}
