package knikita.model;

import java.lang.reflect.Field;
import java.util.ArrayList;

public abstract class Model {

    protected static ArrayList<String> fieldNames = new ArrayList<>();

    public Model() {
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field f : fields) {
            fieldNames.add(f.getName());
        }
    }

    public ArrayList<String> getFieldNames() {
        return fieldNames;
    }

    public int getFieldsCount() {
        return fieldNames.size();
    }

    public ArrayList<Object> getFieldsValues() {
        ArrayList<Object> fieldsList = new ArrayList<>();
        for (Field field : this.getClass().getDeclaredFields()) {
            try {
                fieldsList.add(field.get(this));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return fieldsList;
    }

    public boolean fieldNotNull(String fieldName) {
        try {
            Field field = this.getClass().getField(fieldName);
            if (field.get(this) != null && !field.get(this).equals(0)) {
                return true;
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return false;
    }
}
