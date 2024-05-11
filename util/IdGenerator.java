package util;

import data.AbstractDAO;

/**
 * Class that generates an id for a new object.
 */
public class IdGenerator {

    public static Integer generateId(MaxIdFlagEnum flag) {
        int maxId = Math.toIntExact(AbstractDAO.selectMaxId(flag));
        return maxId + 1;
    }
}
