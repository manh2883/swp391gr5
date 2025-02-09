package DAO;

import DBContext.DBContext;
import Models.Post;
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
    

    public void incrementViewCount(int postId) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public Post getPostById(int postId) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}