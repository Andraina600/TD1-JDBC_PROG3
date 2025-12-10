import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class DateRetriever {
    public List<Category> getAllCategory(){
        List<Category> categories = new ArrayList<>();
        String sql = "SELECT id, name FROM product_category ORDER BY id DESC";

        try (Connection conn = DBConnection.getDBConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery();
        ) {
            while (rs.next()) {
                Category cate = new Category();
                cate.setId(rs.getInt("id"));
                cate.setName(rs.getString("name"));
                categories.add(cate);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return categories;
    }

    public List<Product> getAllProduct(int page, int size){
        List<Product> products = new ArrayList<>();
        int offset = (page - 1) * size;

        String sql = "SELECT product.id, product.name, product.price, product.creation_datetime, product_category.name AS category_name " +
                "FROM product LEFT JOIN product_category " +
                "ON product_category.product_id = product.id ORDER BY product.id LIMIT ? OFFSET ? ";
        try (Connection conn = DBConnection.getDBConnection();
             PreparedStatement ps = conn.prepareStatement(sql)
        ){
            ps.setInt(1 , size);
            ps.setInt(2, offset);

            try (ResultSet rs = ps.executeQuery()){
                while (rs.next()) {
                    Product pro = mapResultSetToProduct(rs);
                    products.add(pro);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return products;
    }

    public List<Product> getProductsByCriteria(String productName, String categoryName, Instant creationMin, Instant creationMax){
        List<Product> products = new ArrayList<>();
        List<Object> params = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT product.id, product.name, product.price, product.creation_datetime, product_category.name AS category_name " +
                "FROM product LEFT JOIN product_category ON product_category.product_id = product.id WHERE 1 = 1 ");
        if(productName != null && !productName.isEmpty()){
            sql.append(" AND product.name ILIKE ?");
            params.add("%"+productName+"%");
        }

        if(categoryName != null && !categoryName.isEmpty()){
            sql.append(" AND product_category.name ILIKE ?");
            params.add("%"+categoryName+"%");
        }
        if(creationMin != null){
            sql.append(" AND product.creation_datetime >= ? ");
            params.add(Timestamp.from(creationMin));
        }
        if(creationMax != null){
            sql.append(" AND product.creation_datetime <= ? ");
            params.add(Timestamp.from(creationMax));
        }
        sql.append(" ORDER BY product.id");

        try(Connection conn = DBConnection.getDBConnection();
            PreparedStatement ps = conn.prepareStatement(sql.toString());
        ) {
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }
            try (ResultSet rs = ps.executeQuery()){
                while (rs.next()) {
                    products.add(mapResultSetToProduct(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return products;
    }

    public List<Product> getProductsByCriteria(String productName, String categoryName, Instant creationMin, Instant creationMax, int page, int size){
        List<Product> products = new ArrayList<>();
        List<Object> params = new ArrayList<>();
        int offset = (page - 1) * size;
        StringBuilder sql = new StringBuilder("SELECT product.id, product.name, product.price, product.creation_datetime, product_category.name AS category_name " +
                "FROM product LEFT JOIN product_category ON product_category.product_id = product.id WHERE 1 = 1 ");
        if(productName != null && !productName.isEmpty()){
            sql.append(" AND product.name ILIKE ?");
            params.add("%"+productName+"%");
        }

        if(categoryName != null && !categoryName.isEmpty()){
            sql.append(" AND product_category.name ILIKE ?");
            params.add("%"+categoryName+"%");
        }
        if(creationMin != null){
            sql.append(" AND product.creation_datetime >= ? ");
            params.add(Timestamp.from(creationMin));
        }

        if(creationMax != null){
            sql.append(" AND product.creation_datetime <= ? ");
            params.add(Timestamp.from(creationMax));
        }

        sql.append(" ORDER BY product.id LIMIT ? OFFSET ? ");
        params.add(size);
        params.add(offset);

        try(Connection conn = DBConnection.getDBConnection();
            PreparedStatement ps = conn.prepareStatement(sql.toString());
        ) {
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }
            try (ResultSet rs = ps.executeQuery()){
                while (rs.next()) {
                    products.add(mapResultSetToProduct(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return products;
    }

    private Product mapResultSetToProduct(ResultSet rs) throws SQLException {
        Product p = new Product();
        p.setId(rs.getInt("id"));
        p.setName(rs.getString("name"));
        p.setPrice(rs.getDouble("price"));

        p.setCreationDateTime(rs.getTimestamp("creation_datetime").toInstant());

        String catName = rs.getString("category_name");
        if(catName != null){
            Category cat = new Category();
            cat.setName(catName);
            p.setCategory(cat);
        }
        return p;
    }
}
