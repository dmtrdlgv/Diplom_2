package site.nomoreparties.stellarburgers.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Order {

    @SerializedName("_id")
    private String id;
    private List<String> ingredients;
    private String status;
    private String name;
    private String createdAt;
    private String updatedAt;
    private int number;

    public Order() {
    }

    public Order(String id, List<String> ingredients, String status, String name, String createdAt, String updatedAt,
                 int number) {
        this.id = id;
        this.ingredients = ingredients;
        this.status = status;
        this.name = name;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.number = number;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
