import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ServiceTest {

    private final Service service = new Service();


    //transform string to optional test
    @Test
    void transformStringOptionalTrue() {
        String input = "Test";
        Optional<String> result = service.transformEmptyStringToOptional(input);
        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(input, result.get());
    }

    @Test
    void transformStringOptionalEmpty() {
        String input = "";
        Optional<String> result = service.transformEmptyStringToOptional(input);
        Assertions.assertTrue(result.isEmpty());
    }



}