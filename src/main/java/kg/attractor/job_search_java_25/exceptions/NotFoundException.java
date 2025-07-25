package kg.attractor.job_search_java_25.exceptions;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String prefix) {
        super(prefix + "not found");
    }
}
