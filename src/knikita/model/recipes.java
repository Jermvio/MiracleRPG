package knikita.model;

public class recipes extends Model {
    public int item_id;

    public int first_material_id;

    public int second_material_id;

    public int third_material_id;

    public int fourth_material_id;

    public int fifth_material_id;

    public recipes() {
    }

    public recipes(int item_id, int first_material_id, int second_material_id, int third_material_id, int fourth_material_id, int fifth_material_id) {
        this.item_id = item_id;
        this.first_material_id = first_material_id;
        this.second_material_id = second_material_id;
        this.third_material_id = third_material_id;
        this.fourth_material_id = fourth_material_id;
        this.fifth_material_id = fifth_material_id;
    }

    public int getItem_id() {
        return item_id;
    }

    public void setItem_id(int item_id) {
        this.item_id = item_id;
    }

    public int getFirst_material_id() {
        return first_material_id;
    }

    public void setFirst_material_id(int first_material_id) {
        this.first_material_id = first_material_id;
    }

    public int getSecond_material_id() {
        return second_material_id;
    }

    public void setSecond_material_id(int second_material_id) {
        this.second_material_id = second_material_id;
    }

    public int getThird_material_id() {
        return third_material_id;
    }

    public void setThird_material_id(int third_material_id) {
        this.third_material_id = third_material_id;
    }

    public int getFourth_material_id() {
        return fourth_material_id;
    }

    public void setFourth_material_id(int fourth_material_id) {
        this.fourth_material_id = fourth_material_id;
    }

    public int getFifth_material_id() {
        return fifth_material_id;
    }

    public void setFifth_material_id(int fifth_material_id) {
        this.fifth_material_id = fifth_material_id;
    }
}
