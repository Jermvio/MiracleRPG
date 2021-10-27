package knikita.model;

import java.lang.reflect.Field;
import java.util.ArrayList;

public abstract class Model {

    public Model() {
    }

    public boolean fieldNotNull(String fieldName) {
        try {
            Field field = this.getClass().getField(fieldName);
            if (field.get(this) != null && !field.get(this).equals(0) &&
                    !field.get(this).equals(false) && !field.get(this).equals(0L)) {
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
