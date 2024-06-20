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
        assertFalse(validator.validate(null), "Null input should return false");
    }

    @Test
    void testEmptyInput() {
        assertTrue(validator.validate(""), "Empty input should return true");
    }

    @Test
    void testSinglePairOfBrackets() {
        assertTrue(validator.validate("()"), "Single pair of brackets should return true");
        assertTrue(validator.validate("[]"), "Single pair of brackets should return true");
        assertTrue(validator.validate("{}"), "Single pair of brackets should return true");
    }

    @Test
    void testNestedBrackets() {
        assertTrue(validator.validate("([])"), "Nested brackets should return true");
        assertTrue(validator.validate("{[()]}"), "Nested brackets should return true");
    }

    @Test
    void testUnmatchedBrackets() {
        assertFalse(validator.validate("("), "Unmatched opening bracket should return false");
        assertFalse(validator.validate(")"), "Unmatched closing bracket should return false");
        assertFalse(validator.validate("({)}"), "Incorrectly nested brackets should return false");
        assertFalse(validator.validate("[(])"), "Incorrectly nested brackets should return false");
    }

    @Test
    void testSequentialBrackets() {
        assertTrue(validator.validate("()[]{}"), "Sequential brackets should return true");
        assertFalse(validator.validate("([)]"), "Incorrectly nested sequential brackets should return false");
    }

    @Test
    void testComplexValidBrackets() {
        assertTrue(validator.validate("((({[{}]})))"), "Complex valid brackets should return true");
    }

    @Test
    void testComplexInvalidBrackets() {
        assertFalse(validator.validate("((({[{}]}))"), "Complex invalid brackets should return false");
    }

    @Test
    void testValidExpression() {
        assertTrue(validator.validate(
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
        assertFalse(validator.validate(
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
