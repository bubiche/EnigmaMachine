package com.bubiche.em;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Main {

    public static void main(String[] args) {
    	// Simple driver for the Enigma Machine

		List<Pair<Character, Character>> plugBoardPairs = new ArrayList<>();
		plugBoardPairs.add(new Pair<>('H', 'E'));

	    EnigmaMachine encodingEnigmaMachine = new EnigmaMachine(
	            "I", 'N',
                "II", 'P',
                "III", 'N',
                'B',
				plugBoardPairs
        );

		EnigmaMachine decodingEnigmaMachine = new EnigmaMachine(
				"I", 'N',
				"II", 'P',
				"III", 'N',
				'B',
				plugBoardPairs
		);


		String message = "Hello Friends";
	    String encodedMessage = encodingEnigmaMachine.encode(message);
	    String decodedMessage = decodingEnigmaMachine.decode(encodedMessage);

	    System.out.println("Encoded: " + encodedMessage);
	    System.out.println("Decoded: " + decodedMessage);
    }
}
