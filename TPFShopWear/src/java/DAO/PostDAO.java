//package DAO;
//
//import DBContext.DBContext;
//import Models.Post;
//import Models.PostDetail;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Timestamp;
//import java.util.ArrayList;
//import java.util.List;
//
//public class PostDAO extends DBContext {
//
//    public List<Post> getLatestPosts(int limit) {
//        String query = "SELECT * FROM Post WHERE status = 1 ORDER BY created_at DESC LIMIT ?;";
//        List<Post> posts = new ArrayList<>();
//
//        try {
//            DBContext db = new DBContext();
//            java.sql.Connection con = db.getConnection();
//            PreparedStatement stm = con.prepareStatement(query);
//            stm.setInt(1, limit);
//            ResultSet rs = stm.executeQuery();
//
//            while (rs.next()) {
//                Post post = new Post();
//                post.setPostId(rs.getInt("post_id"));
//                post.setTitle(rs.getString("title"));
//                post.setStatus(rs.getInt("status"));
//                post.setViewCount(rs.getInt("view_count"));
//                post.setCreatedAt(rs.getTimestamp("created_at"));
//                post.setUpdatedAt(rs.getTimestamp("updated_at"));
//                post.setCreatedBy(rs.getInt("created_by"));
//                post.setUpdatedBy(rs.getInt("updated_by"));
//                post.setPublishedAt(rs.getTimestamp("published_at"));
//                posts.add(post);
//            }
//
//            rs.close();
//            stm.close();
//            con.close();
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return posts;
//    }
//
//    public Post getPostById(int postId) {
//        String query = "SELECT * FROM Post WHERE post_id = ?";
//        try {
//            DBContext db = new DBContext();
//            java.sql.Connection con = db.getConnection();
//            PreparedStatement stm = con.prepareStatement(query);
//            stm.setInt(1, postId);
//            ResultSet rs = stm.executeQuery();
//
//            if (rs.next()) {
//                Post post = new Post();
//                post.setPostId(rs.getInt("post_id"));
//                post.setTitle(rs.getString("title"));
//                post.setStatus(rs.getInt("status"));
//                post.setViewCount(rs.getInt("view_count"));
//                post.setCreatedAt(rs.getTimestamp("created_at"));
//                post.setUpdatedAt(rs.getTimestamp("updated_at"));
//                post.setCreatedBy(rs.getInt("created_by"));
//                post.setUpdatedBy(rs.getInt("updated_by"));
//                post.setPublishedAt(rs.getTimestamp("published_at"));
//                return post;
//            }
//
//            rs.close();
//            stm.close();
//            con.close();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    public void incrementViewCount(int postId) {
//        String query = "UPDATE Post SET view_count = view_count + 1 WHERE post_id = ?";
//        try {
//            DBContext db = new DBContext();
//            java.sql.Connection con = db.getConnection();
//            PreparedStatement stm = con.prepareStatement(query);
//            stm.setInt(1, postId);
//            stm.executeUpdate();
//            stm.close();
//            con.close();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public PostDetail getPostDetailByPostId(int postId) {
//        String query = "SELECT * FROM post_detail WHERE post_id = ?;";
//        try {
//            DBContext db = new DBContext();
//            java.sql.Connection con = db.getConnection();
//            PreparedStatement stm = con.prepareStatement(query);
//            stm.setInt(1, postId);
//
//            System.out.println("Executing query: " + query + " with postId=" + postId);
//
//            ResultSet rs = stm.executeQuery();
//
//            if (rs.next()) {
//                PostDetail postDetail = new PostDetail();
//                postDetail.setPostDetailId(rs.getInt("post_detail_id"));
//                postDetail.setPostId(rs.getInt("post_id"));
//                postDetail.setContent(rs.getString("content"));
//
//                System.out.println("Found PostDetail: " + postDetail);
//                return postDetail;
//            } else {
//                System.out.println("No PostDetail found for postId=" + postId);
//            }
//
//            rs.close();
//            stm.close();
//            con.close();
//        } catch (SQLException e) {
//            System.out.println("Error getting PostDetail: " + e.getMessage());
//            e.printStackTrace();
//        }
//        return null;
//    }
//    
//public boolean createPost(Post post, PostDetail postDetail) {
//    String postQuery = "INSERT INTO Post (title, status, view_count, created_at, updated_at, created_by, updated_by, published_at) "
//            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
//    String detailQuery = "INSERT INTO post_detail (post_id, content) VALUES (?, ?)";
//    
//    try {
//        DBContext db = new DBContext();
//        java.sql.Connection con = db.getConnection();
//        con.setAutoCommit(false);
//        
//        try {
//            // Insert Post
//            PreparedStatement postStm = con.prepareStatement(postQuery, PreparedStatement.RETURN_GENERATED_KEYS);
//            postStm.setString(1, post.getTitle());
//            postStm.setInt(2, post.getStatus());
//            postStm.setInt(3, post.getViewCount());
//            postStm.setTimestamp(4, new Timestamp(post.getCreatedAt().getTime()));
//            postStm.setTimestamp(5, new Timestamp(post.getUpdatedAt().getTime()));
//            postStm.setInt(6, post.getCreatedBy());
//            postStm.setInt(7, post.getUpdatedBy());
//            postStm.setTimestamp(8, new Timestamp(post.getPublishedAt().getTime()));
//            
//            int postRows = postStm.executeUpdate();
//            
//            if (postRows > 0) {
//                ResultSet rs = postStm.getGeneratedKeys();
//                if (rs.next()) {
//                    int postId = rs.getInt(1);
//                    
//                    // Insert PostDetail
//                    PreparedStatement detailStm = con.prepareStatement(detailQuery);
//                    detailStm.setInt(1, postId);
//                    detailStm.setString(2, postDetail.getContent());
//                    
//                    int detailRows = detailStm.executeUpdate();
//                    
//                    if (detailRows > 0) {
//                        con.commit();
//                        rs.close();
//                        postStm.close();
//                        detailStm.close();
//                        return true;
//                    }
//                    detailStm.close();
//                }
//                rs.close();
//            }
//            postStm.close();
//            
//            con.rollback();
//            return false;
//            
//        } catch (SQLException e) {
//            con.rollback();
//            e.printStackTrace();
//            return false;
//        } finally {
//            con.setAutoCommit(true);
//            con.close();
//        }
//        
//    } catch (SQLException e) {
//        e.printStackTrace();
//        return false;
//    }
//}
//}
