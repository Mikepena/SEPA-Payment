import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class CheckSEPATest {

    CheckSEPA sepaChecker = new CheckSEPA();


    //Test checkSepaMandatory
    @ParameterizedTest
    @MethodSource("inputSepaMandatory")
    void testSepaMandatory(Payment payment, boolean expected) {
        boolean result = sepaChecker.checkSEPAMandatory(payment);
        Assertions.assertEquals(expected, result);
    }

    private static Stream<Arguments> inputSepaMandatory() {
        return Stream.of(
                Arguments.of( //Payment OK
                        new Payment("AT913200000002471928", "AT285000000023495829", "Mike", "Peter", 20), true
                ),
                Arguments.of( //Empfänger IBAN falsch
                        new Payment("DE003200000002471928", "AT285000000023495829", "Mike", "Peter", 20), false
                ),
                Arguments.of( //Auftraggeber IBAN falsch
                        new Payment("AT913200000002471928", "DE995000000023495829", "Mike", "Peter", 20), false
                ),
                Arguments.of( //Empfänger und Auftraggeber IBAN falsch
                        new Payment("DE993200000002471928", "AT005000000023495829", "Mike", "Peter", 20), false
                ),
                Arguments.of( //Empfänger Name falsch
                        new Payment("AT913200000002471928", "AT285000000023495829", "asdiasjdjwiondwinjdjansdjknasjkndjnewiunfniufnsdnjfnsdjfinsdjifninwefienfrifnisdjnfj", "Peter", 20), false
                ),
                Arguments.of( //Auftraggeber Name falsch
                        new Payment("AT913200000002471928", "AT285000000023495829", "Mike", "asdiasjdjwiondwinjdjansdjknasjkndjnewiunfniufnsdnjfnsdjfinsdjifninwefienjiasdndwiuansnduwaunisdj", 20), false
                ),
                Arguments.of( //Empfänger und Auftraggeber Name falsch
                        new Payment("AT913200000002471928", "AT285000000023495829", "asdiasjdjwiondwinjdjansdjknasjkndjnewiunfniufnsdnjfnsdjfinsdjifninwefienfrifnisdjnfj", "asdiasjdjwiondwinjdjansdjknasjkndjnewiunfniufnsdnjfnsdjfinsdjifninwefienjiasdndwiuansnduwaunisdj", 20), false
                ),
                Arguments.of( //Betrag falsch
                        new Payment("AT913200000002471928", "AT285000000023495829", "Mike", "Peter", 20.385), false
                ),
                Arguments.of( //Alles falsch
                        new Payment("DE003200000002471928", "AT995000000023495829", "asdiasjdjwiondwinjdjansdjknasjkndjnewiunfniufnsdnjfnsdjfinsdjifninwefienfrifnisdjnfj", "asdiasjdjwiondwinjdjansdjknasjkndjnewiunfniufnsdnjfnsdjfinsdjifninwefienjiasdndwiuansnduwaunisdj", 20.385), false
                ),
                Arguments.of( //Betrag und Empfänger, Auftraggeber Name falsch
                        new Payment("AT913200000002471928", "AT285000000023495829", "asdiasjdjwiondwinjdjansdjknasjkndjnewiunfniufnsdnjfnsdjfinsdjifninwefienfrifnisdjnfj", "asdiasjdjwiondwinjdjansdjknasjkndjnewiunfniufnsdnjfnsdjfinsdjifninwefienjiasdndwiuansnduwaunisdj", 20.98844), false
                ),
                Arguments.of( //Betrag und Empfänger, Auftraggeber IBAN falsch
                        new Payment("DE993200000002471928", "AT005000000023495829", "Mike", "Peter", 20.929349), false
                )
        );
    }

    //Test checkSepaOptional
    @ParameterizedTest
    @MethodSource("inputSepaOptional")
    void testSepaOptional(Payment payment, boolean expected) {
        boolean result = sepaChecker.checkSEPAOptional(payment);
        Assertions.assertEquals(expected, result);
    }


    private static Stream<Arguments> inputSepaOptional() {
        return Stream.of(
                Arguments.of( //Payment OK mit allen Optionalen Feldern + Verwendungszweck
                        new Payment("AT913200000002471928", "AT285000000023495829", "Mike", "Peter", 20, Optional.of("Hütteldorfer Straße"), Optional.of("Beingasse"), Optional.of("Verwendungszweck"), Optional.empty()), true
                ),
                Arguments.of( //Payment OK mit allen Optionalen Feldern + Zahlungsreferenz
                        new Payment("AT913200000002471928", "AT285000000023495829", "Mike", "Peter", 20, Optional.of("Hütteldorfer Straße"), Optional.of("Beingasse"), Optional.empty(), Optional.of("Zahlungsreferenz")), true
                ),
                Arguments.of( //Payment OK mit Adressen Leer + Zahlungsreferenz
                        new Payment("AT913200000002471928", "AT285000000023495829", "Mike", "Peter", 20, Optional.empty(), Optional.empty(), Optional.empty(), Optional.of("Zahlungsreferenz")), true
                ),
                Arguments.of( //Payment OK mit Adressen Leer + Verwendungszweck
                        new Payment("AT913200000002471928", "AT285000000023495829", "Mike", "Peter", 20, Optional.empty(), Optional.empty(), Optional.of("Verwendungszweck"), Optional.empty()), true
                ),
                Arguments.of( //Payment OK mit Adressen Falsch
                        new Payment("AT913200000002471928", "AT285000000023495829", "Mike", "Peter", 20, Optional.of("Eine ganz lange Straße die dann falsch ist weil sie über 70 zeichen hat"), Optional.of("Noch eine ganz lange Straße die dann falsch ist weil sie über 70 zeichen hat"), Optional.empty(), Optional.of("Verwendungszweck")), false
                ),
                Arguments.of( //Payment OK mit Adressen Leer und ohne Verwendungszweck/Zahlungsreferenz
                        new Payment("AT913200000002471928", "AT285000000023495829", "Mike", "Peter", 20, Optional.empty(),Optional.empty(),Optional.empty(),Optional.empty()), true
                )
        );
    }
    //Bankkonto Namen Test
    @ParameterizedTest
    @CsvSource({
            "asdiasjdjwiondwinjdjansdjknasjkndjnewiunfniufnsdnjfnsdjfinsdjifninwefienfrifnisdjnfj, false", //Länge inkorrekt
            "910329592384828357245874395873489573947589374975893475734895783947589378957394753785, false", //Länge inkorrekt mit Zahlen
            "Mike, true" //IBAN Länge korrekt


    })
    void sepaAccountNameTest(String input, boolean expected) {
        boolean result = sepaChecker.checkAccountName((input));
        Assertions.assertEquals(expected, result);
    }

    //Betrag Test
    @ParameterizedTest
    @CsvSource({
            "0.00, false", //Betrag mit genau 0 Euro falsch
            "0.034, false", //Betrag mit 3 Kommastellen falsch
            "99.3892, false", //Betrag mit 4 Kommastellen falsch
            "2.50, true", //Betrag mit 2 Kommastellen korrekt
            "2.5, true", //Betrag mit 1 Kommastellen korrekt
            "99.00, true" //Betrag ohne Kommastellen korrekt



    })
    void sepaAmountTest(double input, boolean expected) {
        boolean result = sepaChecker.checkAmount((input));
        Assertions.assertEquals(expected, result);
    }

    //Adresse Test

    @Test //Adresse mit mehr als 70 Zeichen
    void testAdressFalse() {
        Optional<String> input = Optional.of("Eine ganz lange Straße die dann falsch ist weil sie über 70 zeichen hat");
        boolean result = sepaChecker.checkAdress(input);
        Assertions.assertFalse( result);
    }
    @Test //Adresse mit weniger als 70 Zeichen
    void testAdressTrue() {
        Optional<String> input = Optional.of("Hütteldorfer Straße");
        boolean result = sepaChecker.checkAdress(input);
        Assertions.assertTrue( result);
    }
    @Test //Adresse leer
    void testAdressEmpty() {
        Optional<String> input = Optional.empty();
        boolean result = sepaChecker.checkAdress(input);
        Assertions.assertTrue(input.isEmpty());
        Assertions.assertTrue( result);
    }

    //Verwendungszweck Test

    @Test //Verwendungszweck mit mehr als 140 Zeichen
    void testPurposeOfUseMoreThan140Characters() {
        Optional<String> input = Optional.of("nasjdknsjidnsufnfuivnduivndvidjnfvuidfnvuidfjvdjvkndfviuwuieunvfiudnjfdfjvndfivudnfiuvfdnvuifdnvjdfndjifsdgdfnjngbuvifdunvdiufnvuidfnuvinfsdf");
        boolean result = sepaChecker.checkPurposeOfUse(input);
        Assertions.assertFalse(result);
    }
    @Test //Verwendungszweck nicht alphanumerisch
    void testPurposeOfUseNotAlphaNumeric() {
        Optional<String> input = Optional.of("EineÜberweisung");
        boolean result = sepaChecker.checkPurposeOfUse(input);
        Assertions.assertFalse(result);
    }

    @Test //Verwendungszweck mit weniger als 140 Zeichen
    void testPurposeOfUseLessThan140Characters() {
        Optional<String> input = Optional.of("EineUEberweisung");
        boolean result = sepaChecker.checkPurposeOfUse(input);
        Assertions.assertTrue(result);
    }

    @Test
    void testPurposeOfUseEmpty() {
        Optional<String> input = Optional.empty();
        boolean result = sepaChecker.checkPurposeOfUse(input);
        Assertions.assertTrue(result);
    }

    //Zahlungsreferenz Test

    @Test //Zahlungsreferenz zu lang
    void testPaymentReferenceMoreThan35Characters() {
        Optional<String> input = Optional.of("dsjisudhndisuhdsuihdsihidusihdsuhudsi");
        boolean result = sepaChecker.checkPaymentReference(input);
        Assertions.assertFalse(result);
    }

    @Test //Zahlungsreferenz nicht Alphanumerisch
    void testPaymentReferenceNotAlphaNumeric() {
        Optional<String> input = Optional.of("dsd??!!");
        boolean result = sepaChecker.checkPaymentReference(input);
        Assertions.assertFalse(result);
    }

    @Test //Zahlungsreferenz korrekt
    void testPaymentReferenceAlphanumericAndLessThan35Characters() {
        Optional<String> input = Optional.of("EineZahlungsreferenz");
        boolean result = sepaChecker.checkPaymentReference(input);
        Assertions.assertTrue(result);
    }
    @Test //Zahlungsreferenz leer
    void testPaymentReferenceIsEmpty() {
        Optional<String> input = Optional.empty();
        boolean result = sepaChecker.checkPaymentReference(input);
        Assertions.assertTrue(result);
    }


}