package nusextended.m426;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UnitTestExampleTest {

    private ClassUnderTest classUnderTest;

    @BeforeEach
    void setUp() {
        // Initialize the class under test before each test method
        classUnderTest = new ClassUnderTest();
    }

    @Test
    @DisplayName("should return expected value for valid input")
    void shouldReturnExpectedValueForValidInput() {
        // Arrange
        int expected = 50;

        // Act
        int actual = classUnderTest.methodToTest();

        // Assert
        assertEquals(expected, actual, "Method should return expected value");
    }

    @Test
    @DisplayName("should throw exception when invalid input provided")
    void shouldThrowExceptionWhenInvalidInputProvided() {
        // Arrange & Act & Assert
        assertThrows(IllegalArgumentException.class,
            () -> classUnderTest.methodWithValidation(null),
            "Method should throw exception for null input");
    }

    @Test
    @DisplayName("should return true when condition is met")
    void shouldReturnTrueWhenConditionIsMet() {
        // Arrange
        String validInput = "valid";

        // Act
        boolean result = classUnderTest.isValid(validInput);

        // Assert
        assertTrue(result, "Validation should succeed for valid input");
    }

    @Test
    @DisplayName("should return false when condition is not met")
    void shouldReturnFalseWhenConditionIsNotMet() {
        // Arrange
        String invalidInput = "";

        // Act
        boolean result = classUnderTest.isValid(invalidInput);

        // Assert
        assertFalse(result, "Validation should fail for empty input");
    }
}