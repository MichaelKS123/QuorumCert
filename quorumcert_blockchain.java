/**
 * CertificateBlockchain.java
 * Core blockchain implementation for certificate management
 * Author: Michael Semera
 */

import java.util.*;
import java.io.*;
import java.time.LocalDateTime;

/**
 * Blockchain for managing certificates
 */
public class CertificateBlockchain {
    private final List<Block> chain;
    private final int difficulty;
    private long totalMiningTime;
    private int totalBlocks;
    
    /**
     * Constructor
     */
    public CertificateBlockchain() {
        this.chain = new ArrayList<>();
        this.difficulty = 4; // Number of leading zeros required
        this.totalMiningTime = 0;
        this.totalBlocks = 0;
    }
    
    /**
     * Create genesis block
     */
    public void createGenesisBlock() {
        Block genesis = new Block(0, "0", difficulty);
        chain.add(genesis);
        totalBlocks++;
    }
    
    /**
     * Add new block with certificate
     */
    public Block addBlock(Certificate certificate) {
        long startTime = System.currentTimeMillis();
        
        String previousHash = getLatestBlock().getHash();
        int newIndex = chain.size();
        
        Block newBlock = new Block(newIndex, previousHash, certificate, difficulty);
        
        // Update certificate with blockchain info
        certificate.setBlockchainInfo(newBlock.getHash(), newIndex);
        
        chain.add(newBlock);
        totalBlocks++;
        
        long endTime = System.currentTimeMillis();
        totalMiningTime += (endTime - startTime);
        
        return newBlock;
    }
    
    /**
     * Get latest block
     */
    public Block getLatestBlock() {
        if (chain.isEmpty()) {
            return null;
        }
        return chain.get(chain.size() - 1);
    }
    
    /**
     * Get block by index
     */
    public Block getBlock(int index) {
        if (index < 0 || index >= chain.size()) {
            return null;
        }
        return chain.get(index);
    }
    
    /**
     * Find block containing certificate
     */
    public Block findBlockByCertificateId(String certificateId) {
        for (Block block : chain) {
            if (block.getCertificate() != null &&
                block.getCertificate().getCertificateId().equals(certificateId)) {
                return block;
            }
        }
        return null;
    }
    
    /**
     * Validate entire blockchain
     */
    public boolean isChainValid() {
        // Check genesis block
        if (chain.isEmpty()) {
            return false;
        }
        
        Block genesis = chain.get(0);
        if (!genesis.getPreviousHash().equals("0")) {
            return false;
        }
        
        // Validate each block
        for (int i = 1; i < chain.size(); i++) {
            Block currentBlock = chain.get(i);
            Block previousBlock = chain.get(i - 1);
            
            // Verify current block's hash
            if (!currentBlock.isValid()) {
                System.out.println("Block " + i + " hash is invalid");
                return false;
            }
            
            // Verify link to previous block
            if (!currentBlock.getPreviousHash().equals(previousBlock.getHash())) {
                System.out.println("Block " + i + " not properly linked");
                return false;
            }
            
            // Verify certificate hash if present
            if (currentBlock.getCertificate() != null) {
                if (!currentBlock.getCertificate().verifyHash()) {
                    System.out.println("Certificate in block " + i + " is tampered");
                    return false;
                }
            }
        }
        
        return true;
    }
    
    /**
     * Get blockchain statistics
     */
    public Map<String, Object> getStatistics() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("chainLength", chain.size());
        stats.put("totalBlocks", totalBlocks);
        stats.put("difficulty", difficulty);
        stats.put("averageMiningTime", totalBlocks > 1 ? totalMiningTime / (totalBlocks - 1) : 0);
        stats.put("totalMiningTime", totalMiningTime);
        stats.put("isValid", isChainValid());
        
        if (!chain.isEmpty()) {
            stats.put("genesisHash", chain.get(0).getHash());
            stats.put("latestHash", getLatestBlock().getHash());
        }
        
        return stats;
    }
    
    /**
     * Export blockchain to file
     */
    public void exportToFile(String filename) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            writer.println("{");
            writer.println("  \"blockchain\": \"QuorumCert\",");
            writer.println("  \"author\": \"Michael Semera\",");
            writer.println("  \"exportDate\": \"" + LocalDateTime.now() + "\",");
            writer.println("  \"chainLength\": " + chain.size() + ",");
            writer.println("  \"difficulty\": " + difficulty + ",");
            writer.println("  \"isValid\": " + isChainValid() + ",");
            writer.println("  \"blocks\": [");
            
            for (int i = 0; i < chain.size(); i++) {
                Block block = chain.get(i);
                writer.println("    {");
                writer.println("      \"index\": " + block.getIndex() + ",");
                writer.println("      \"timestamp\": \"" + block.getTimestamp() + "\",");
                writer.println("      \"previousHash\": \"" + block.getPreviousHash() + "\",");
                writer.println("      \"hash\": \"" + block.getHash() + "\",");
                writer.println("      \"nonce\": " + block.getNonce() + ",");
                
                if (block.getCertificate() != null) {
                    Certificate cert = block.getCertificate();
                    writer.println("      \"certificate\": {");
                    writer.println("        \"id\": \"" + cert.getCertificateId() + "\",");
                    writer.println("        \"studentName\": \"" + cert.getStudentName() + "\",");
                    writer.println("        \"degree\": \"" + cert.getDegree() + "\",");
                    writer.println("        \"institution\": \"" + cert.getInstitution() + "\",");
                    writer.println("        \"year\": " + cert.getYear() + ",");
                    writer.println("        \"hash\": \"" + cert.getCertificateHash() + "\"");
                    writer.println("      }");
                } else {
                    writer.println("      \"certificate\": null");
                }
                
                writer.print("    }");
                if (i < chain.size() - 1) {
                    writer.println(",");
                } else {
                    writer.println();
                }
            }
            
            writer.println("  ]");
            writer.println("}");
        }
    }
    
    /**
     * Get entire chain
     */
    public List<Block> getChain() {
        return new ArrayList<>(chain);
    }
    
    /**
     * Get chain length
     */
    public int getChainLength() {
        return chain.size();
    }
    
    /**
     * Get difficulty
     */
    public int getDifficulty() {
        return difficulty;
    }
    
    /**
     * Get total mining time
     */
    public long getTotalMiningTime() {
        return totalMiningTime;
    }
}