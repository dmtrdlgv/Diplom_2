package site.nomoreparties.stellarburgers.model;

//Класс для десериализации json из тела ответа GET ingredients (списка ингредиентов)

import java.util.List;

public class IngredientsResponse {

    private boolean success;
    private List<Ingredient> data;

    public IngredientsResponse() {
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<Ingredient> getData() {
        return data;
    }

    public void setData(List<Ingredient> data) {
        this.data = data;
    }
}
