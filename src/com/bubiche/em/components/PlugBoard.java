package com.bubiche.em.components;

import javafx.util.Pair;

import javax.swing.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlugBoard {
    public PlugBoard(List<Pair<Character, Character>> pluggedPairs) {
        for (Pair<Character, Character> pluggedPair : pluggedPairs) {
            wiring.put(pluggedPair.getKey(), pluggedPair.getValue());
            wiring.put(pluggedPair.getValue(), pluggedPair.getKey());
        }
    }

    public Character getPluggedCharacter(Character c) throws IllegalArgumentException {
        Character upperC = Character.toUpperCase(c);
        int idx = CommonConfigs.ALPHABET.indexOf(upperC);
        if (idx == -1) {
            throw new IllegalArgumentException("Character must be in the alphabet" + CommonConfigs.ALPHABET);
        }

        return wiring.getOrDefault(upperC, upperC);
    }

    // additional mapping from some character x to some character y
    private Map<Character, Character> wiring = new HashMap<>();
}
