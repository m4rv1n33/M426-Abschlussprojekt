package nusextended.m426;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UnitTestExample {

    private ClassUnderTest classUnderTest;

    @BeforeEach
    void setUp() {
        /*
        Initialize the class under test
        Class needs to be replaced for actual testing
        */
        classUnderTest = new ClassUnderTest();
    }

    @Test
    @DisplayName("description of expected behavior when condition is met")
    void shouldDoSomethingWhenConditionIsMet() {
        // Arrange
        int expected = 50;

        // Act
        int actual = classUnderTest.methodToTest();

        // Assert
        assertEquals(expected, actual, "Optional failure message");
    }
}