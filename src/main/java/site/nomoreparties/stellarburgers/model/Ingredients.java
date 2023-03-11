package site.nomoreparties.stellarburgers.model;

//Класс для сериализации json в тело запроса POST orders (создание заказа)

import java.util.List;

public class Ingredients {

    private List<String> ingredients;

    public Ingredients() {
    }

    public Ingredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }
    public void addIngredient(String ingredient) {
        this.ingredients.add(ingredient);
    }
}
