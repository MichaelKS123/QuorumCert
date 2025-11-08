/**
 * Block.java
 * Represents a single block in the blockchain
 * Author: Michael Semera
 */

import java.time.LocalDateTime;
import java.security.MessageDigest;
import java.nio.charset.StandardCharsets;

/**
 * Block class representing a single block in the blockchain
 */
public class Block {
    private final int index;
    private final LocalDateTime timestamp;
    private final String previousHash;
    private String hash;
    private int nonce;
    private Certificate certificate;
    private final int difficulty;
    
    /**
     * Constructor for regular block
     */
    public Block(int index, String previousHash, Certificate certificate, int difficulty) {
        this.index = index;
        this.timestamp = LocalDateTime.now();
        this.previousHash = previousHash;
        this.certificate = certificate;
        this.difficulty = difficulty;
        this.nonce = 0;
        this.hash = mineBlock();
    }
    
    /**
     * Constructor for genesis block
     */
    public Block(int index, String previousHash, int difficulty) {
        this.index = index;
        this.timestamp = LocalDateTime.now();
        this.previousHash = previousHash;
        this.certificate = null;
        this.difficulty = difficulty;
        this.nonce = 0;
        this.hash = mineBlock();
    }
    
    /**
     * Calculate hash for this block
     */
    public String calculateHash() {
        String data = index + 
                     timestamp.toString() + 
                     previousHash + 
                     (certificate != null ? certificate.getCertificateHash() : "GENESIS") +
                     nonce;
        
        return hashString(data);
    }
    
    /**
     * Mine block with proof-of-work
     */
    private String mineBlock() {
        String target = new String(new char[difficulty]).replace('\0', '0');
        String calculatedHash;
        
        do {
            nonce++;
            calculatedHash = calculateHash();
        } while (!calculatedHash.substring(0, difficulty).equals(target));
        
        return calculatedHash;
    }
    
    /**
     * SHA-256 hashing function
     */
    private String hashString(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            
            return hexString.toString();
        } catch (Exception e) {
            throw new RuntimeException("Error hashing: " + e.getMessage());
        }
    }
    
    /**
     * Validate block integrity
     */
    public boolean isValid() {
        // Check if hash matches calculated hash
        if (!hash.equals(calculateHash())) {
            return false;
        }
        
        // Check proof-of-work
        String target = new String(new char[difficulty]).replace('\0', '0');
        return hash.substring(0, difficulty).equals(target);
    }
    
    /**
     * Tamper with certificate (for demonstration)
     */
    public void tamperCertificate(Certificate newCertificate) {
        this.certificate = newCertificate;
        // Don't recalculate hash - this simulates tampering
    }
    
    // Getters
    public int getIndex() { return index; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public String getPreviousHash() { return previousHash; }
    public String getHash() { return hash; }
    public int getNonce() { return nonce; }
    public Certificate getCertificate() { return certificate; }
    public int getDifficulty() { return difficulty; }
    
    @Override
    public String toString() {
        return String.format("Block #%d [Hash: %s, PrevHash: %s, Nonce: %d]",
            index, hash.substring(0, 10) + "...", 
            previousHash.substring(0, 10) + "...", nonce);
    }
}