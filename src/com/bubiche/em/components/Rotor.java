package com.bubiche.em.components;

import java.util.*;

// https://en.wikipedia.org/wiki/Enigma_rotor_details

public class Rotor {
    public Rotor(String rotorNumber, char windowLetter_, Rotor nextRotor_, Rotor prevRotor_) throws IllegalArgumentException {
        if (!Arrays.asList(VALID_ROTOR_NUMBERS).contains(rotorNumber)) {
            throw new IllegalArgumentException("Rotor number must be in " + Arrays.toString(VALID_ROTOR_NUMBERS));
        }

        windowLetter = Character.toUpperCase(windowLetter_);
        offset = CommonConfigs.ALPHABET.indexOf(windowLetter);
        if (offset == -1) {
            throw new IllegalArgumentException("Window letter must be in the alphabet " + CommonConfigs.ALPHABET);
        }

        forwardWiring = FORWARD_WIRINGS.get(rotorNumber);
        backwardWiring = BACKWARD_WIRINGS.get(rotorNumber);
        notch = TURNOVER_NOTCHES.get(rotorNumber);
        nextRotor = nextRotor_;
        prevRotor = prevRotor_;
    }

    public void updateSetting(Character windowLetter_) throws IllegalArgumentException {
        windowLetter = Character.toUpperCase(windowLetter_);
        offset = CommonConfigs.ALPHABET.indexOf(windowLetter);
        if (offset == -1) {
            throw new IllegalArgumentException("Window letter must be in the alphabet " + CommonConfigs.ALPHABET);
        }
    }

    // step the rotor
    public void step() {
        if (nextRotor != null && windowLetter == notch) {
            nextRotor.step();
        }
        offset = (offset + 1) % CommonConfigs.ALPHABET_SIZE;
        windowLetter = CommonConfigs.ALPHABET.charAt(offset);
    }

    // encode character at index idx and return encoded index
    // index is relative to window (window letter has index = 0, loop around the alphabet)
    public int encodeChar(int idx, boolean isForward) {
        String wiring = isForward ? forwardWiring : backwardWiring;
        char outputChar = wiring.charAt((idx + offset) % CommonConfigs.ALPHABET_SIZE);
        int outputIdx = (CommonConfigs.ALPHABET.indexOf(outputChar) - offset + (offset > CommonConfigs.ALPHABET.indexOf(outputChar) ? CommonConfigs.ALPHABET_SIZE : 0)) % CommonConfigs.ALPHABET_SIZE;
        if (nextRotor != null && isForward) {
            return nextRotor.encodeChar(outputIdx, isForward);
        }
        else if (prevRotor != null && !isForward) {
            return prevRotor.encodeChar(outputIdx, isForward);
        }
        else {
            return outputIdx;
        }
    }

    public void setNextRotor(Rotor nextRotor_) {
        nextRotor = nextRotor_;
    }

    public void setPrevRotor(Rotor prevRotor_) {
        prevRotor = prevRotor_;
    }

    // Rotor's attribute
    private String forwardWiring;
    private String backwardWiring;
    private Character notch;
    private  int offset;
    private Character windowLetter;
    private Rotor nextRotor;
    private Rotor prevRotor;

    // static constants
    private static final String[] VALID_ROTOR_NUMBERS = new String[]{"I", "II", "III"};

    private static final Map<String, String> FORWARD_WIRINGS = new HashMap<>() {{
        put("I", "EKMFLGDQVZNTOWYHXUSPAIBRCJ");
        put("II", "AJDKSIRUXBLHWTMCQGZNPYFVOE");
        put("III", "BDFHJLCPRTXVZNYEIWGAKMUSQO");
    }};

    private static final Map<String, String> BACKWARD_WIRINGS = new HashMap<>() {{
        put("I", "UWYGADFPVZBECKMTHXSLRINQOJ");
        put("II", "AJPCZWRLFBDKOTYUQGENHXMIVS");
        put("III", "TAGBPCSDQEUFVNZHYIXJWLRKOM");
    }};

    private static final Map<String, Character> TURNOVER_NOTCHES = new HashMap<>() {{
        put("I", 'Q');
        put("II", 'E');
        put("III", 'V');
    }};
}
