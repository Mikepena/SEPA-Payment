import org.junit.Test;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

class CheckIBANTest {

    CheckIBAN ibanChecker = new CheckIBAN();


    //IBAN Account Check Test

    @ParameterizedTest
    @MethodSource("inputAccounts")
    void testSepaMandatory(List<BankAccount> accounts, String iban, boolean expected) {
        boolean result = ibanChecker.checkIBANIsCorrectWithAccounts(iban, accounts);
        Assertions.assertEquals(expected, result);
    }

    private static Stream<Arguments> inputAccounts() {
        return Stream.of(
                Arguments.of( //Bankaccount gefunden
                        List.of(new BankAccount("Mike", "Raiffeisen", "AT", "91", "32000", "00002789102"), new BankAccount("Peter", "Volksbank", "DE", "20","20000", "00002349202")), "AT913200000002789102", true
                ),
                Arguments.of( //Bankaccount nicht gefunden
                        List.of(new BankAccount("Mike", "Raiffeisen", "AT", "91", "32000", "00002789102"), new BankAccount("Peter", "Volksbank", "DE", "20","20000", "00002349202")), "AT203200000002789102", false
                )
        );
    }

    //IBAN Länge Test
    @ParameterizedTest
    @CsvSource({
            "Hello, false", //Länge inkorrekt mit Buchstaben
            "9103295, false", //Länge inkorrekt mit Zahlen
            "AT913200000003623648, true" //IBAN Länge korrekt


    })
    void ibanLengthTest(String input, boolean expected) {
        boolean result = ibanChecker.checkIBANLength((input));
        Assertions.assertEquals(expected, result);
    }

    //Länder Code Test
    @ParameterizedTest
    @CsvSource({
            "DE288493029485019280, false", //Länder Code mit falschem Land
            "91329898989812345856, false", //Länder Code besteht aus Zahlen
            "AT913200000003623648, true" //Länder Code ist AT


    })
    void ibanCheckCountryCode(String input, boolean expected) {
        boolean result = ibanChecker.checkIBANCountryCodeAustria((input));
        Assertions.assertEquals(expected, result);
    }

    //Prüfziffern Test
    @ParameterizedTest
    @CsvSource({
            "AT008493029485019280, false", //Prüfziffern unter 1
            "AT988493029485019280, false", //Prüfziffern über 97
            "ATAT9898989812345856, false", //Prüfziffern keine Zahlen
            "AT910100000003623648, true", //Prüfziffern genau 1
            "AT919700000003623648, true", //Prüfziffern genau 97
            "AT913200000003623648, true" //Prüfziffern zwischen 1 und 97


    })
    void ibanCheckDigitsTest(String input, boolean expected) {
        boolean result = ibanChecker.checkIBANDigits((input));
        Assertions.assertEquals(expected, result);
    }

    //Bankleitzahl Test
    //10 000 und 99 999
    @ParameterizedTest
    @CsvSource({
            "AT10SE93029485019280, false", //Bankleitzahl mit Buchstaben
            "AT200898989812345856, false", //Bankleitzahl unter 10 000
            "AT911000000003623648, true", //Bankleitzahl genau 10 000
            "AT919999900003623648, true", //Bankleitzahl genau 99999
            "AT913200000003623648, true" //Bankleitzahl zwischen 10000 und 99999


    })
    void ibanCheckCodeNumber(String input, boolean expected) {
        boolean result = ibanChecker.checkBankCodeNumber((input));
        Assertions.assertEquals(expected, result);
    }

    //IBAN Kontonummer Test
    @ParameterizedTest
    @CsvSource({

            "AT200898900000000000, false", //Kontonummer 0
            "AT911000000003623648, true", //Kontonummer korrekt



    })
    void ibanCheckAccountNumber(String input, boolean expected) {
        boolean result = ibanChecker.checkAccountNumber((input));
        Assertions.assertEquals(expected, result);
    }

    //IBAN kompletter Check
    @ParameterizedTest
    @CsvSource({

            "DE201898900000012345, false", //Ländercode falsch
            "AT001898900000012345, false", //Prüfziffer falsch
            "AT200898900000012345, false", //Bankleitzahl falsch
            "AT201898900000000000, false", //Kontonummer falsch
            "DE982898900000012345, false", //Ländercode und Prüfziffer falsch
            "DE980898900000012345, false", //Ländercode, Prüfziffer und Bankleitzahl falsch
            "DE990898900000000000, false", //Ländercode, Prüfziffer, Bankleitzahl und Kontonummer falsch
            "AT990898900000012345, false", //Prüfziffer und Bankleitzahl falsch
            "AT992898900000000000, false", //Prüfziffer und Kontonummer falsch
            "DE200898900000012345, false", //Bankleitzahl und Ländercode falsch
            "DE203898900000000000, false", //Kontonummer und Ländercode falsch
            "DE983898900000000000, false", //Kontonummer, Ländercode, Prüfziffer falsch

            "AT911000000003623648, true", //IBAN korrekt



    })
    void ibanCheck(String input, boolean expected) {
        boolean result = ibanChecker.checkIBAN((input));
        Assertions.assertEquals(expected, result);
    }







}