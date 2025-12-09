import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DateRetrieverTest {
    @Test
    void testGetAllCategories(){
        DateRetriever dr = new DateRetriever();
        List<Category> categories = dr.getAllCategory();
        assertNotNull(categories);
        assertTrue(categories.size()>0);
        assertFalse(categories.isEmpty());
        System.out.println(categories);
    }

    @Test
    void testGetAllProducts(){
        DateRetriever dr = new DateRetriever();
        List<Product> page = dr.getAllProduct(2,3);
        assertFalse(page.isEmpty());
        assertTrue(page.size()>= 1);
        System.out.println(page);
    }

    @Test
    void testSearchProductByNameAndByCategoryNameAndBoth(){
        DateRetriever dr = new DateRetriever();
        List<Product> products = dr.getProductsByCriteria("Laptop", "informatique", null, null);
        assertFalse(products.isEmpty());
        assertTrue(products.size()>0);
        System.out.println(products);
    }

    @Test
    void testSearchProductByDateRange(){
        Instant marsDebut = Instant.parse("2024-03-01T00:00:00Z");
        Instant marsFin = Instant.parse("2024-03-31T23:59:00Z");
        DateRetriever dr = new DateRetriever();
        List<Product> products = dr.getProductsByCriteria(null, null, marsDebut, marsFin);
        assertFalse(products.isEmpty());
        assertTrue(products.stream()
                .allMatch(product -> product.getCreationDateTime().isAfter(marsDebut) &&  product.getCreationDateTime().isBefore(marsFin))
        );
        System.out.println(products);
    }

    @Test
    void testSearchProductByCategoryNameAndDateRange(){
        Instant marsDebut = Instant.parse("2024-03-01T00:00:00Z");
        Instant marsFin = Instant.parse("2024-03-31T23:59:00Z");
        DateRetriever dr = new DateRetriever();
        List<Product> products = dr.getProductsByCriteria(null, "Accessoire", marsDebut, marsFin);
        assertFalse(products.isEmpty());
        assertTrue(products.stream()
                .allMatch(product -> "Accessoires".equalsIgnoreCase(product.getCategory().getName()) && product.getCreationDateTime().isAfter(marsDebut)
                && product.getCreationDateTime().isBefore(marsFin))
        );
    }

    @Test
    void testGetProductByCriteria(){
        DateRetriever dr = new DateRetriever();
        List<Product> products = dr.getProductsByCriteria("phone", "Mobile", null, null, 1, 2);
        assertFalse(products.isEmpty());
        assertTrue(products.size() <= 2);
        assertTrue(products.stream()
                .allMatch(product -> "Mobile".equalsIgnoreCase(product.getCategory().getName()))
        );
    }
}