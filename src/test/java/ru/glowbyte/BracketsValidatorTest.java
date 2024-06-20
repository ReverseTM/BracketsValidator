package ru.glowbyte;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.glowbyte.impl.BracketsValidator;
import ru.glowbyte.interfaces.IBracketsValidator;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BracketsValidatorTest {

    private IBracketsValidator validator;

    @BeforeEach
    void setUp() {
        validator = new BracketsValidator();
    }

    @Test
    void testNullInput() {
        assertFalse(validator.isValid(null), "Null input should return false");
    }

    @Test
    void testEmptyInput() {
        assertTrue(validator.isValid(""), "Empty input should return true");
    }

    @Test
    void testSinglePairOfBrackets() {
        assertTrue(validator.isValid("()"), "Single pair of brackets should return true");
        assertTrue(validator.isValid("[]"), "Single pair of brackets should return true");
        assertTrue(validator.isValid("{}"), "Single pair of brackets should return true");
    }

    @Test
    void testNestedBrackets() {
        assertTrue(validator.isValid("([])"), "Nested brackets should return true");
        assertTrue(validator.isValid("{[()]}"), "Nested brackets should return true");
    }

    @Test
    void testUnmatchedBrackets() {
        assertFalse(validator.isValid("("), "Unmatched opening bracket should return false");
        assertFalse(validator.isValid(")"), "Unmatched closing bracket should return false");
        assertFalse(validator.isValid("({)}"), "Incorrectly nested brackets should return false");
        assertFalse(validator.isValid("[(])"), "Incorrectly nested brackets should return false");
    }

    @Test
    void testSequentialBrackets() {
        assertTrue(validator.isValid("()[]{}"), "Sequential brackets should return true");
        assertFalse(validator.isValid("([)]"), "Incorrectly nested sequential brackets should return false");
    }

    @Test
    void testComplexValidBrackets() {
        assertTrue(validator.isValid("((({[{}]})))"), "Complex valid brackets should return true");
    }

    @Test
    void testComplexInvalidBrackets() {
        assertFalse(validator.isValid("((({[{}]}))"), "Complex invalid brackets should return false");
    }

    @Test
    void testValidExpression() {
        assertTrue(validator.isValid(
                        """
                                {
                                  "ключ": "значение",
                                  "число": 42,
                                  "массив": [1, 2, 3, 4],
                                  "объект": {
                                    "вложенный_ключ": "вложенное_значение"
                                  }
                                }
                                """),
                "Valid expression should return true");
    }

    @Test
    void testInvalidExpression() {
        assertFalse(validator.isValid(
                        """
                                {
                                  "ключ": "значение",
                                  "число": 42,
                                  "массив": [1, 2, 3, 4,
                                  "объект": {
                                    "вложенный_ключ": "вложенное_значение",
                                  },
                                  "строка": "Пример JSON с пропущенной скобкой"
                                }
                                """),
                "Invalid expression should return false");
    }
}
