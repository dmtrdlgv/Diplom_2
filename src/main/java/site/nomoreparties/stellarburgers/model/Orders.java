package site.nomoreparties.stellarburgers.model;

//Класс для десериализации json из тела ответа GET orders || orders/all (списка заказов)

import java.util.List;

public class Orders {

    private boolean success;
    private String name;
    private List<Order> orders;
    private int total;
    private int totalToday;

    public Orders() {
    }

    public Orders(boolean success, String name, List<Order> orders, int total, int totalToday) {
        this.success = success;
        this.name = name;
        this.orders = orders;
        this.total = total;
        this.totalToday = totalToday;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getTotalToday() {
        return totalToday;
    }

    public void setTotalToday(int totalToday) {
        this.totalToday = totalToday;
    }
}
