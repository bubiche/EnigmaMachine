package com.bubiche.em;

import com.bubiche.em.components.CommonConfigs;
import com.bubiche.em.components.PlugBoard;
import com.bubiche.em.components.Reflector;
import com.bubiche.em.components.Rotor;
import javafx.util.Pair;

import java.util.List;
import java.util.Map;

public class EnigmaMachine {
    // the 3 rotors will be refered to as l - m - r
    EnigmaMachine(String lNumber, Character lWindow,
                  String mNumber, Character mWindow,
                  String rNumber, Character rWindow,
                  Character reflectorType,
                  List<Pair<Character, Character>> plugBoardPairs) {
        lRotor = new Rotor(lNumber, lWindow, null, null);
        mRotor = new Rotor(mNumber, mWindow, null, null);
        rRotor = new Rotor(rNumber, rWindow, null, null);

        lRotor.setNextRotor(mRotor);
        mRotor.setNextRotor(rRotor);
        mRotor.setPrevRotor(lRotor);
        rRotor.setPrevRotor(mRotor);

        reflector = new Reflector(reflectorType);
        plugBoard = new PlugBoard(plugBoardPairs);
    }

    public void setRotorWindows(Character lWindow, Character mWindow, Character rWindow) {
        lRotor.updateSetting(lWindow);
        mRotor.updateSetting(mWindow);
        rRotor.updateSetting(rWindow);
    }

    String encode(String message) {
        String squashedMessage = message.replaceAll("\\s","");
        StringBuilder result = new StringBuilder();
        for(int i = 0, n = squashedMessage.length(); i < n ; ++i) {
            Character c = Character.toUpperCase(squashedMessage.charAt(i));
            result.append(processCharacter(c));
        }

        return result.toString();
    }

    String decode(String encodedMessage) {
        return encode(encodedMessage);
    }

    private Character processCharacter(Character c) throws IllegalArgumentException {
        // Character -> Plug Board -> L Rotor -> M Rotor -> R Rotor -> Reflector

        char upperC = Character.toUpperCase(c);
        int idx = CommonConfigs.ALPHABET.indexOf(upperC);
        if (idx == -1) {
            throw new IllegalArgumentException("Character must be in the alphabet" + CommonConfigs.ALPHABET);
        }

        Character chara = plugBoard.getPluggedCharacter(upperC);

        // step the rotor and send character through rotors to reflector
        lRotor.step();
        int leftPass = lRotor.encodeChar(CommonConfigs.ALPHABET.indexOf(chara), true);
        Character reflectorOutput = reflector.getReflectedCharacter(
                CommonConfigs.ALPHABET.charAt(leftPass % CommonConfigs.ALPHABET_SIZE)
        );

        // reflected character goes back to the rotors
        Character result = CommonConfigs.ALPHABET.charAt(
                rRotor.encodeChar(CommonConfigs.ALPHABET.indexOf(reflectorOutput), false)
        );

        return plugBoard.getPluggedCharacter(result);
    }

    private Rotor lRotor;
    private Rotor mRotor;
    private Rotor rRotor;
    private Reflector reflector;
    private PlugBoard plugBoard;
}
