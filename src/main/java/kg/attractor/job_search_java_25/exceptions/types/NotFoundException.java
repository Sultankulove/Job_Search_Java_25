package kg.attractor.job_search_java_25.exceptions.types;

import java.util.NoSuchElementException;

public class NotFoundException extends NoSuchElementException {
  public NotFoundException(String prefix) { super(prefix + " not found"); }
}