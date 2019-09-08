package com.bubiche.em.components;


public class Reflector {
    public Reflector(Character reflectorType) throws IllegalArgumentException {
        if (reflectorType != 'B' && reflectorType != 'C') {
            throw new IllegalArgumentException("Currently only support reflector type B or C");
        }

        if (Character.toUpperCase(reflectorType) == 'B') {
            wiring = "YRUHQSLDPXNGOKMIEBFZCWVJAT";

        } else if (Character.toUpperCase(reflectorType) == 'C') {
            wiring = "FVPJIAOYEDRZXWGCTKUQSBNMHL";
        }
    }

    public Character getReflectedCharacter(Character c) throws IllegalArgumentException {
        char upperC = Character.toUpperCase(c);
        int idx = CommonConfigs.ALPHABET.indexOf(upperC);
        if (idx == -1) {
            throw new IllegalArgumentException("Character must be in the alphabet" + CommonConfigs.ALPHABET);
        }

        return wiring.charAt(idx);
    }

    // wiring corresponds to character in the same position in the alphabet, e.g. for type B, A -> Y
    private String wiring;
}
