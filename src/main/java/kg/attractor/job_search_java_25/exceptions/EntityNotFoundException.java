package kg.attractor.job_search_java_25.exceptions;

import kg.attractor.job_search_java_25.model.User;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String message) {
      super(message);
    }

    public EntityNotFoundException(Class<?> entityClass, Object id) {
    super(entityClass.getSimpleName() + " с ID " + id + " не найден");
  }
}
