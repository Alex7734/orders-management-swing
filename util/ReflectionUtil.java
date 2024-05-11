package util;

import javax.swing.table.DefaultTableModel;
import java.lang.reflect.Field;
import java.util.List;

/**
 * Utility class for reflection operations on tables.
 */
public class ReflectionUtil {

    /**
     * Populates a table with the given objects.
     * @param objects the objects to populate the table with
     * @param model the table model
     */
    public static void populateTable(List<?> objects, DefaultTableModel model) {
        if (!objects.isEmpty()) {
            Class<?> clazz = objects.get(0).getClass();
            Field[] fields = clazz.getDeclaredFields();

            String[] header = new String[fields.length];
            for (int i = 0; i < fields.length; i++) {
                header[i] = fields[i].getName();
            }

            model.setColumnIdentifiers(header);
            model.setRowCount(0);

            for (Object object : objects) {
                Object[] row = new Object[fields.length];
                for (int i = 0; i < fields.length; i++) {
                    try {
                        fields[i].setAccessible(true);
                        row[i] = fields[i].get(object);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }

                model.addRow(row);
            }
        }
    }

}
