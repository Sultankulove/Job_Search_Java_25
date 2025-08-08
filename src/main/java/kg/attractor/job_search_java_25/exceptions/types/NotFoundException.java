package kg.attractor.job_search_java_25.exceptions.types;

public class NotFoundException extends RuntimeException {
  public NotFoundException(String what) { super(what + " not found"); }
}