package ru.glowbyte.impl;

import ru.glowbyte.interfaces.IBracketsValidator;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;

public class BracketsValidator implements IBracketsValidator {
    private static final Map<Character, Character> BRACKETS;

    static {
        BRACKETS = Map.of(
                ')', '(',
                ']', '[',
                '}', '{'
        );
    }

    @Override
    public boolean isValid(String content) {
        if (content == null || content.isEmpty()) {
            return content != null;
        }

        Deque<Character> deque = new ArrayDeque<>();

        for (char bracket : content.toCharArray()) {
            if (BRACKETS.containsValue(bracket)) {
                deque.push(bracket);
            } else if (BRACKETS.containsKey(bracket) && (deque.isEmpty() || !BRACKETS.get(bracket).equals(deque.pop()))) {
                return false;
            }

        }
        return deque.isEmpty();
    }
}
