package by.innowise.sales_analysis;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AnalyticsServiceTest {
    private AnalyticsService analyticsService;
    private List<Order> orders;

    @BeforeEach
    void setUp() {
        analyticsService = new AnalyticsService();
        LocalDateTime now = LocalDateTime.now();

        Customer customer1 = new Customer("cust1", "John Doe",
                "john@example.com", now.minusDays(10), 30, "New York");
        Customer customer2 = new Customer("cust2", "Alice Smith",
                "alice@example.com", now.minusDays(5), 25, "London");
        Customer customer3 = new Customer("cust3", "Bob Johnson",
                "bob@example.com", now.minusDays(15), 40, "New York");
        Customer customer4 = new Customer("cust4", "Charlie Brown",
                "charlie@example.com", now.minusDays(20), 35, "Paris");

        Order order1 = new Order("order1", now.minusDays(2), customer1,
                List.of(
                        new OrderItem("Laptop", 2, 1200.0, Category.ELECTRONICS),
                        new OrderItem("Mouse", 1, 50.0, Category.ELECTRONICS)
                ),
                OrderStatus.DELIVERED);

        Order order2 = new Order("order2", now.minusDays(1), customer2,
                List.of(
                        new OrderItem("Phone", 1, 800.0, Category.ELECTRONICS),
                        new OrderItem("Case", 2, 25.0, Category.ELECTRONICS)
                ),
                OrderStatus.PROCESSING);

        Order order3 = new Order("order3", now.minusDays(3), customer3,
                List.of(
                        new OrderItem("T-Shirt", 3, 20.0, Category.CLOTHING),
                        new OrderItem("Laptop", 1, 1200.0, Category.ELECTRONICS)
                ),
                OrderStatus.DELIVERED);

        Order order4 = new Order("order4", now.minusDays(4), customer1,
                List.of(
                        new OrderItem("Book", 1, 15.0, Category.BOOKS),
                        new OrderItem("Headphones", 1, 100.0, Category.ELECTRONICS)
                ),
                OrderStatus.DELIVERED);

        Order order5 = new Order("order5", now.minusDays(5), customer1,
                List.of(
                        new OrderItem("Monitor", 1, 300.0, Category.ELECTRONICS),
                        new OrderItem("Keyboard", 1, 80.0, Category.ELECTRONICS)
                ),
                OrderStatus.SHIPPED);

        Order order6 = new Order("order6", now.minusDays(6), customer1,
                List.of(new OrderItem("Desk", 1, 200.0, Category.HOME)),
                OrderStatus.DELIVERED);

        Order order7 = new Order("order7", now.minusDays(7), customer1,
                List.of(new OrderItem("Chair", 2, 150.0, Category.HOME)),
                OrderStatus.DELIVERED);

        Order order8 = new Order("order8", now.minusDays(8), customer4,
                List.of(new OrderItem("Perfume", 1, 75.0, Category.BEAUTY)),
                OrderStatus.DELIVERED);

        orders = List.of(order1, order2, order3, order4, order5, order6, order7, order8);
    }

    @Test
    void testGetUniqueCities_shouldReturnDistinctCities() {
        List<String> result = analyticsService.getUniqueCities(orders);

        assertEquals(3, result.size());
        assertTrue(result.contains("New York"));
        assertTrue(result.contains("London"));
        assertTrue(result.contains("Paris"));
    }

    @Test
    void testGetTotalIncome_shouldSumOnlyDeliveredOrders() {
        double result = analyticsService.getTotalIncome(orders);

        // order1: 1200*2 + 50*1 = 2450
        // order3: 20*3 + 1200*1 = 1260
        // order4: 15*1 + 100*1 = 115
        // order6: 200*1 = 200
        // order7: 150*2 = 300
        // order8: 75*1 = 75
        // Total: 2450 + 1260 + 115 + 200 + 300 + 75 = 4400
        assertEquals(4400.0, result, 0.001);
    }

    @Test
    void testGetMostPopularProduct_shouldReturnProductWithHighestTotalQuantity() {
        String result = analyticsService.getMostPopularProduct(orders);

        // Laptop: 2 (order1) + 1 (order3) = 3
        // T-Shirt: 3
        // Mouse: 1
        // Phone: 1
        // Case: 2
        // Book: 1
        // Headphones: 1
        // Monitor: 1
        // Keyboard: 1
        // Desk: 1
        // Chair: 2
        // Perfume: 1
        assertTrue(result.equals("Laptop") || result.equals("T-Shirt"));
    }

    @Test
    void testGetAverageCheckForDeliveredOrders_shouldCalculateCorrectAverage() {
        double result = analyticsService.getAverageCheckForDeliveredOrders(orders);

        // order1 (2450), order3 (1260), order4 (115), order6 (200), order7 (300), order8 (75)
        // Sum: 4400
        // Quantity: 6
        // Average: 4400 / 6 = 733.333
        assertEquals(733.333, result, 0.001);
    }

    @Test
    void getCustomersWithFiveOrders_shouldReturnCustomersWithAtLeastFiveOrders() {
        List<Customer> result = analyticsService.getCustomersWithFiveOrders(orders);

        assertEquals(1, result.size());
        assertEquals("John Doe", result.getFirst().getName());
    }
}