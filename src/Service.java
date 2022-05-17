import java.util.Optional;

public class Service {

    public Optional<String> transformEmptyStringToOptional(String input) {
        if(input.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(input);
        }
    }
}
