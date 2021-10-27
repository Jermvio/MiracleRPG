package knikita.model;

public class allowed_recipes extends Model {
    public long user_id;

    public int recipe_id;

    public allowed_recipes() {
    }

    public allowed_recipes(long user_id, int recipe_id) {
        this.user_id = user_id;
        this.recipe_id = recipe_id;
    }

    public long getUser_id() {
        return user_id;
    }

    public Model setUser_id(long user_id) {
        this.user_id = user_id;
        return this;
    }

    public int getRecipe_id() {
        return recipe_id;
    }

    public void setRecipe_id(int recipe_id) {
        this.recipe_id = recipe_id;
    }
}
