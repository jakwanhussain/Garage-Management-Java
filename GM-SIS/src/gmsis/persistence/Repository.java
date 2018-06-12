/*
 *  This file is part of GM-SIS by #Team10
 *  No licence because QM owns our souls anyway
 */

package gmsis.persistence;

import java.io.Serializable;
import java.util.List;

/**
 * @author filip
 */
public interface Repository<T> {
    List<T> all(Class<T> class_) throws Exception;

    T get(Class<T> class_, Serializable id) throws Exception;

    T save(T object) throws Exception;

    void delete(T object) throws Exception;
}
