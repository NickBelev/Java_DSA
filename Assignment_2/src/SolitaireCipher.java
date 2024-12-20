/**
 * Your name here: Nicholas Belev
 * Your McGill ID here: 261076111
 **/
public class SolitaireCipher {
    public Deck key;

    public SolitaireCipher(Deck key) {
        this.key = new Deck(key); // deep copy of the deck
    }

    /*
     *TODO: Generates a keystream of the given size
     */
    public int[] getKeystream(int size) {
        /**** ADD CODE HERE ****/
        int[] keyArr = new int[size];
        for (int i = 0; i < size; i++) {
            keyArr[i] = key.generateNextKeystreamValue();
        }
        return keyArr;
    }

    /*
     *TODO: Encodes the input message using the algorithm described in the pdf.
     */
    public String encode(String msg) {
        /**** ADD CODE HERE ****/
        msg = msg.toLowerCase();
        String reformatMSG = "";
        for (int i = 0; i < msg.length(); i++) {
            if (msg.charAt(i) >= 'a' && msg.charAt(i) <= 'z') {
                reformatMSG += msg.charAt(i);
            }
        }
        String cipherMSG = "";
        int[] shiftAmt = getKeystream(reformatMSG.length());
        String alphabet = "abcdefghijklmnopqrstuvwxyz";
        for (int i = 0; i < reformatMSG.length(); i++) {
            int pos = alphabet.indexOf(reformatMSG.charAt(i));
            int encryptAmt = (shiftAmt[i] + pos) % 26;
            char encrypted = alphabet.charAt(encryptAmt);
            cipherMSG += encrypted;
        }
        return cipherMSG.toUpperCase();
    }

    /*
     *TODO: Decodes the input message using the algorithm described in the pdf.
     */
    public String decode(String msg) {
        /**** ADD CODE HERE ****/
        msg = msg.toLowerCase();
        int[] shiftAmt = getKeystream(msg.length());
        String decipherMSG = "";
        String unAlphabet = "zyxwvutsrqponmlkjihgfedcba";
        for (int i = 0; i < msg.length(); i++) {
            int pos = unAlphabet.indexOf(msg.charAt(i));
            int decryptAmt = (pos + shiftAmt[i]) % 26;
            char decrypted = unAlphabet.charAt(decryptAmt);
            decipherMSG += decrypted;
        }
        return decipherMSG.toUpperCase();

    }
}