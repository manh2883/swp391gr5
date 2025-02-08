package DAO;

import DBContext.DBContext;
import Models.Post;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

public class PostDAO {
    private DBContext dbContext;

    public PostDAO() {
        dbContext = new DBContext();
    }

    public List<Post> getAllPosts() {
        List<Post> posts = new ArrayList<>();
        String query = "SELECT * FROM posts";
        
        try (Connection con = dbContext.getConnection();
             PreparedStatement stm = con.prepareStatement(query);
             ResultSet rs = stm.executeQuery()) {
            
            while (rs.next()) {
                posts.add(new Post(
                    rs.getInt("id"),
                    rs.getString("title"),
                    rs.getInt("status"),
                    rs.getInt("view_count"),
                    rs.getTimestamp("created_at"),
                    rs.getTimestamp("updated_at"),
                    rs.getInt("created_by"),
                    rs.getInt("updated_by"),
                    rs.getTimestamp("published_at")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return posts;
    }

    public Post getPostById(int id) {
        String query = "SELECT * FROM posts WHERE id = ?";
        
        try (Connection con = dbContext.getConnection();
             PreparedStatement stm = con.prepareStatement(query)) {
            
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            
            if (rs.next()) {
                return new Post(
                    rs.getInt("id"),
                    rs.getString("title"),
                    rs.getInt("status"),
                    rs.getInt("view_count"),
                    rs.getTimestamp("created_at"),
                    rs.getTimestamp("updated_at"),
                    rs.getInt("created_by"),
                    rs.getInt("updated_by"),
                    rs.getTimestamp("published_at")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
