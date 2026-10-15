/**
 * This is a utility class that encrypts and decrypts a phrase using three
 * different approaches. 
 * 
 * The first approach is called the Vigenere Cipher.Vigenere encryption 
 * is a method of encrypting alphabetic text based on the letters of a keyword.
 * 
 * The second approach is Playfair Cipher. It encrypts two letters (a digraph) 
 * at a time instead of just one.
 * 
 * The third approach is Caesar Cipher. It is a simple replacement cypher. 
 * 
 * @author Huseyin Aygun
 * @version 8/3/2025
 */

public class CryptoManager { 

    private static final char LOWER_RANGE = ' ';
    private static final char UPPER_RANGE = '_';
    private static final int RANGE = UPPER_RANGE - LOWER_RANGE + 1;
    // Use 64-character matrix (8X8) for Playfair cipher  
    private static final String ALPHABET64 = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_";

    public static boolean isStringInBounds(String plainText) {
        for (int i = 0; i < plainText.length(); i++) {
            if (!(plainText.charAt(i) >= LOWER_RANGE && plainText.charAt(i) <= UPPER_RANGE)) {
                return false;
            }
        }
        return true;
    }

    
    private static String createPlayfairAlphabet(String key)
    {
    	String allCharacters = key + ALPHABET64 + " ";
    	String playfairAlphabet = "";

    	for (int i = 0; i < allCharacters.length(); i++)
    	{
    		char letter = allCharacters.charAt(i);

    		if (playfairAlphabet.indexOf(letter) == -1)
    		{
    			playfairAlphabet += letter;
    		}
    	}

    	return playfairAlphabet;
    }

    
	/**
	 * Vigenere Cipher is a method of encrypting alphabetic text 
	 * based on the letters of a keyword. It works as below:
	 * 		Choose a keyword (e.g., KEY).
	 * 		Repeat the keyword to match the length of the plaintext.
	 * 		Each letter in the plaintext is shifted by the position of the 
	 * 		corresponding letter in the keyword (A = 0, B = 1, ..., Z = 25).
	 */   


    public static String vigenereEncryption(String plainText, String key)
    {
    	if (!isStringInBounds(plainText))
    	{
    		return "The selected string is not in bounds, Try again.";
    	}

    	String encryptedText = "";

    	for (int i = 0; i < plainText.length(); i++)
    	{
    		char letter = plainText.charAt(i);
    		char keyLetter = key.charAt(i % key.length());

    		int shiftedValue = (letter - LOWER_RANGE + keyLetter - LOWER_RANGE) % RANGE;

    		encryptedText += (char)(shiftedValue + LOWER_RANGE);
    	}

    	return encryptedText;
    }


    // Vigenere Decryption
	public static String vigenereDecryption(String encryptedText, String key)
	{
		String decryptedText = "";

		for (int i = 0; i < encryptedText.length(); i++)
		{
			char letter = encryptedText.charAt(i);
			char keyLetter = key.charAt(i % key.length());

			int originalValue = (letter - LOWER_RANGE - (keyLetter - LOWER_RANGE) + RANGE) % RANGE;

			decryptedText += (char)(originalValue + LOWER_RANGE);
		}

		return decryptedText;
	}


	/**
	 * Playfair Cipher encrypts two letters at a time instead of just one.
	 * It works as follows:
	 * A matrix (8X8 in our case) is built using a keyword
	 * Plaintext is split into letter pairs (e.g., ME ET YO UR).
	 * Encryption rules depend on the positions of the letters in the matrix:
	 *     Same row: replace each letter with the one to its right.
	 *     Same column: replace each with the one below.
	 *     Rectangle: replace each letter with the one in its own row but in the column of the other letter in the pair.
	 */    
	

	public static String playfairEncryption(String plainText, String key)
	{
		if (!isStringInBounds(plainText))
		{
			return "The selected string is not in bounds, Try again.";
		}

		String playfairAlphabet = createPlayfairAlphabet(key);
		char[][] playfairMatrix = new char[8][8];
		int matrixIndex = 0;

		for (int row = 0; row < 8; row++)
		{
			for (int column = 0; column < 8; column++)
			{
				playfairMatrix[row][column] = playfairAlphabet.charAt(matrixIndex);
				matrixIndex++;
			}
		}

		String textNoSpaces = plainText.replace(" ", "");

		if (textNoSpaces.length() % 2 != 0)
		{
			textNoSpaces += "X";
		}

		String encryptedText = "";

		for (int i = 0; i < textNoSpaces.length(); i += 2)
		{
			char firstLetter = textNoSpaces.charAt(i);
			char secondLetter = textNoSpaces.charAt(i + 1);

			int firstIndex = playfairAlphabet.indexOf(firstLetter);
			int secondIndex = playfairAlphabet.indexOf(secondLetter);

			int firstRow = firstIndex / 8;
			int firstColumn = firstIndex % 8;
			int secondRow = secondIndex / 8;
			int secondColumn = secondIndex % 8;

			if (firstRow == secondRow)
			{
				encryptedText += playfairMatrix[firstRow][(firstColumn + 1) % 8];
				encryptedText += playfairMatrix[secondRow][(secondColumn + 1) % 8];
			}
			else if (firstColumn == secondColumn)
			{
				encryptedText += playfairMatrix[(firstRow + 1) % 8][firstColumn];
				encryptedText += playfairMatrix[(secondRow + 1) % 8][secondColumn];
			}
			else
			{
				encryptedText += playfairMatrix[firstRow][secondColumn];
				encryptedText += playfairMatrix[secondRow][firstColumn];
			}
		}

		String finalEncryptedText = "";
		int encryptedIndex = 0;

		for (int i = 0; i < plainText.length(); i++)
		{
			if (plainText.charAt(i) == ' ')
			{
				finalEncryptedText += " ";
			}
			else
			{
				finalEncryptedText += encryptedText.charAt(encryptedIndex);
				encryptedIndex++;
			}
		}

		if (encryptedIndex < encryptedText.length())
		{
			finalEncryptedText += encryptedText.charAt(encryptedIndex);
		}

		return finalEncryptedText;
	}

	
    // Vigenere Decryption
	public static String playfairDecryption(String encryptedText, String key)
	{
		String playfairAlphabet = createPlayfairAlphabet(key);
		char[][] playfairMatrix = new char[8][8];
		int matrixIndex = 0;

		for (int row = 0; row < 8; row++)
		{
			for (int column = 0; column < 8; column++)
			{
				playfairMatrix[row][column] = playfairAlphabet.charAt(matrixIndex);
				matrixIndex++;
			}
		}

		String textNoSpaces = encryptedText.replace(" ", "");
		String decryptedText = "";

		for (int i = 0; i < textNoSpaces.length(); i += 2)
		{
			char firstLetter = textNoSpaces.charAt(i);
			char secondLetter = textNoSpaces.charAt(i + 1);

			int firstIndex = playfairAlphabet.indexOf(firstLetter);
			int secondIndex = playfairAlphabet.indexOf(secondLetter);

			int firstRow = firstIndex / 8;
			int firstColumn = firstIndex % 8;
			int secondRow = secondIndex / 8;
			int secondColumn = secondIndex % 8;

			if (firstRow == secondRow)
			{
				decryptedText += playfairMatrix[firstRow][(firstColumn + 7) % 8];
				decryptedText += playfairMatrix[secondRow][(secondColumn + 7) % 8];
			}
			else if (firstColumn == secondColumn)
			{
				decryptedText += playfairMatrix[(firstRow + 7) % 8][firstColumn];
				decryptedText += playfairMatrix[(secondRow + 7) % 8][secondColumn];
			}
			else
			{
				decryptedText += playfairMatrix[firstRow][secondColumn];
				decryptedText += playfairMatrix[secondRow][firstColumn];
			}
		}
		
		String finalDecryptedText = "";
		int decryptedIndex = 0;

		for (int i = 0; i < encryptedText.length(); i++)
		{
			if (encryptedText.charAt(i) == ' ')
			{
				finalDecryptedText += " ";
			}
			else
			{
				finalDecryptedText += decryptedText.charAt(decryptedIndex);
				decryptedIndex++;
			}
		}

		if (finalDecryptedText.endsWith("X"))
		{
			finalDecryptedText = finalDecryptedText.substring(0, finalDecryptedText.length() - 1);
		}

		return finalDecryptedText;
	}


    /**
     * Caesar Cipher is a simple substitution cipher that replaces each letter in a message 
     * with a letter some fixed number of positions down the alphabet. 
     * For example, with a shift of 3, 'A' would become 'D', 'B' would become 'E', and so on.
     */    
 

	public static String caesarEncryption(String plainText, int key)
	{
		if (!isStringInBounds(plainText))
		{
			return "The selected string is not in bounds, Try again.";
		}

		String encryptedText = "";

		for (int i = 0; i < plainText.length(); i++)
		{
			char letter = plainText.charAt(i);

			int shiftedValue = (letter - LOWER_RANGE + key % RANGE + RANGE) % RANGE;

			encryptedText += (char)(shiftedValue + LOWER_RANGE);
		}

		return encryptedText;
	}


    // Caesar Decryption
	public static String caesarDecryption(String encryptedText, int key)
	{
		String decryptedText = "";

		for (int i = 0; i < encryptedText.length(); i++)
		{
			char letter = encryptedText.charAt(i);

			int originalValue = (letter - LOWER_RANGE - key % RANGE + RANGE) % RANGE;

			decryptedText += (char)(originalValue + LOWER_RANGE);
		}

		return decryptedText;
	}

}