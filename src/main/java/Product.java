import java.time.Instant;

public class Product {
    private int id;
    private String name;
    private double price;
    private Instant creationDateTime;
    private Category category;

    public Product(){}

    public Product(int id, String name, double price, Instant creationDateTime,  Category category) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.creationDateTime = creationDateTime;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public Instant getCreationDateTime() {
        return creationDateTime;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setCreationDateTime(Instant creationDateTime) {
        this.creationDateTime = creationDateTime;
    }

    public Category getCategory() {
        return category;
    }

    public String getCategoryName() {
        return category == null ? null : category.getName();
    }

    public void setCategory(Category cat) {
        this.category = cat;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", creationDateTime=" + creationDateTime +
                ", category= " + category +
                '}';
    }
}
