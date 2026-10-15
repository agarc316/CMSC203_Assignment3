
import junit.framework.TestCase;

public class CryptoManagerTestStudent extends TestCase
{
    public void testIsStringInBounds()
    {
        assertTrue(CryptoManager.isStringInBounds("HELLO WORLD!"));
        assertFalse(CryptoManager.isStringInBounds("Hello World!"));
        assertFalse(CryptoManager.isStringInBounds("HELLO~"));
    }

    public void testVigenereEncryption()
    {
        String plainText = "HELLO WORLD";
        String key = "KEY";

        String encryptedText = CryptoManager.vigenereEncryption(plainText, key);

        assertEquals("3*E74YB4K7)", encryptedText);
    }

    public void testVigenereDecryption()
    {
        String encryptedText = "3*E74YB4K7)";
        String key = "KEY";

        String decryptedText = CryptoManager.vigenereDecryption(encryptedText, key);

        assertEquals("HELLO WORLD", decryptedText);
    }

    public void testPlayfairEncryption()
    {
        String plainText = "JAVA CODE";
        String key = "CAT";

        String encryptedText = CryptoManager.playfairEncryption(plainText, key);

        assertEquals("ITQE GHEF", encryptedText);
    }

    public void testPlayfairDecryption()
    {
        String encryptedText = "ITQE GHEF";
        String key = "CAT";

        String decryptedText = CryptoManager.playfairDecryption(encryptedText, key);

        assertEquals("JAVA CODE", decryptedText);
    }

    public void testCaesarEncryption()
    {
        String plainText = "JAVA 2026!";
        int key = 3;

        String encryptedText = CryptoManager.caesarEncryption(plainText, key);

        assertEquals("MDYD#5359$", encryptedText);
    }

    public void testCaesarDecryption()
    {
        String encryptedText = "MDYD#5359$";
        int key = 3;

        String decryptedText = CryptoManager.caesarDecryption(encryptedText, key);

        assertEquals("JAVA 2026!", decryptedText);
    }
}
