package by.innowise.sales_analysis;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AnalyticsService {
    public List<String> getUniqueCities(List<Order> orders) {
        return orders.stream()
                .map(Order::getCustomer)
                .map(Customer::getCity)
                .distinct()
                .toList();
    }

    public double getTotalIncome(List<Order> orders) {
        return orders.stream()
                .filter(order -> order.getStatus() == OrderStatus.DELIVERED)
                .flatMap(order -> order.getItems().stream())
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();
    }

    public String getMostPopularProduct(List<Order> orders) {
        return orders.stream()
                .flatMap(order -> order.getItems().stream())
                .collect(Collectors.groupingBy(OrderItem::getProductName,
                        Collectors.summingInt(OrderItem::getQuantity)))
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("Undefined :(");
    }

    public double getAverageCheckForDeliveredOrders(List<Order> orders) {
        return orders.stream()
                .filter(order -> order.getStatus() == OrderStatus.DELIVERED)
                .mapToDouble(order -> order.getItems().stream()
                        .mapToDouble(item -> item.getPrice() * item.getQuantity())
                        .sum())
                .average()
                .orElse(0);
    }

    public List<Customer> getCustomersWithFiveOrders(List<Order> orders) {
        return orders.stream()
                .collect(Collectors.groupingBy(Order::getCustomer,
                        Collectors.counting()))
                .entrySet()
                .stream()
                .filter(entry -> entry.getValue() >= 5)
                .map(Map.Entry::getKey)
                .toList();
    }
}
