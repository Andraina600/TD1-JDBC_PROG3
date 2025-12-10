import java.time.Instant;

public class Main {
    public static void main(String[] args) {
       DateRetriever dr = new DateRetriever();

       dr.getAllCategory().forEach( category -> {
           System.out.println("id: " + category.getId() + ", name: " + category.getName());
       });
        System.out.println("-----------------------------------------------");
        System.out.println("Tous les produits page 1, 5 par page");
        dr.getAllProduct(1, 5).forEach(System.out::println);
        System.out.println("--------------------------------------------------");
        System.out.println("Test1: dell, null, null, null : \n");
        dr.getProductsByCriteria("dell", null, null, null).forEach(System.out::println);
        System.out.println("Test1: dell, null, null, null, 1, 5 : \n");
        dr.getProductsByCriteria("dell", null, null, null, 1, 5).forEach(System.out::println);
        System.out.println("------------------------------------");
        System.out.println("Test2: samsung, bureau, null, null");
        dr.getProductsByCriteria("Samsung", "bureau", null, null).forEach(System.out::println);
        System.out.println("---------------------------------------");
        System.out.println("Test3: produit Logitech dans Acccessoires crées après le 1er mars 2024: \n");
        Instant marsDebut = Instant.parse("2024-03-01T00:00:00Z");
        Instant marsFin = Instant.parse("2024-03-31T23:59:00Z");
        dr.getProductsByCriteria("Logitech", "accessoires", marsDebut, marsFin).forEach(System.out::println);
        System.out.println("-------------------------------------------------");
        System.out.println("Test4: 1-> null, informatique, null, null, 1, 10");
        dr.getProductsByCriteria(null, "informatique", null, null, 1 ,10).forEach(System.out::println);
        System.out.println("2-> Dell, null, null, null, 1, 5:");
        dr.getProductsByCriteria("dell", null, null, null, 1, 5).forEach(System.out::println);
        /*System.out.println("Test2: produit de la categorie informatique: \n");
        dr.getProductsByCriteria(null, "informatique", null, null).forEach(System.out::println);
        System.out.println("------------------------------------");
        System.out.println("Test3: produit crées après le 1er mars 2024: \n");

        dr.getProductsByCriteria(null, null, marsDebut, marsFin).forEach(System.out::println);
        System.out.println("-----------------------------------");
        */
    }
}
