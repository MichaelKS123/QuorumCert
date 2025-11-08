/**
 * BlockchainStatistics.java
 * Provides statistical analysis of the blockchain
 * Author: Michael Semera
 */

import java.util.*;
import java.util.stream.Collectors;

/**
 * Statistics calculator for blockchain and certificates
 */
public class BlockchainStatistics {
    private final CertificateBlockchain blockchain;
    private final CertificateManager certificateManager;
    
    public BlockchainStatistics(CertificateBlockchain blockchain, 
                               CertificateManager certificateManager) {
        this.blockchain = blockchain;
        this.certificateManager = certificateManager;
    }
    
    /**
     * Get total number of blocks
     */
    public int getTotalBlocks() {
        return blockchain.getChainLength();
    }
    
    /**
     * Get chain length
     */
    public int getChainLength() {
        return blockchain.getChainLength();
    }
    
    /**
     * Get genesis block hash
     */
    public String getGenesisBlockHash() {
        Block genesis = blockchain.getBlock(0);
        return genesis != null ? genesis.getHash() : "N/A";
    }
    
    /**
     * Get latest block hash
     */
    public String getLatestBlockHash() {
        Block latest = blockchain.getLatestBlock();
        return latest != null ? latest.getHash() : "N/A";
    }
    
    /**
     * Get total certificates
     */
    public int getTotalCertificates() {
        return certificateManager.getCertificateCount();
    }
    
    /**
     * Get verifications today
     */
    public int getVerificationsToday() {
        return certificateManager.getVerificationCount();
    }
    
    /**
     * Get unique institutions count
     */
    public int getUniqueInstitutions() {
        return certificateManager.getUniqueInstitutions().size();
    }
    
    /**
     * Check if chain is valid
     */
    public boolean isChainValid() {
        return blockchain.isChainValid();
    }
    
    /**
     * Get average mining time
     */
    public long getAverageMiningTime() {
        int blocks = getTotalBlocks();
        if (blocks <= 1) return 0;
        return blockchain.getTotalMiningTime() / (blocks - 1);
    }
    
    /**
     * Get total hash operations (approximate)
     */
    public long getTotalHashOperations() {
        List<Block> chain = blockchain.getChain();
        long total = 0;
        for (Block block : chain) {
            total += block.getNonce();
        }
        return total;
    }
    
    /**
     * Get top institutions
     */
    public Map<String, Integer> getTopInstitutions(int limit) {
        Map<String, Integer> stats = certificateManager.getInstitutionStatistics();
        
        return stats.entrySet()
            .stream()
            .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
            .limit(limit)
            .collect(Collectors.toMap(
                Map.Entry::getKey,
                Map.Entry::getValue,
                (e1, e2) -> e1,
                LinkedHashMap::new
            ));
    }
    
    /**
     * Get certificates by year distribution
     */
    public Map<Integer, Integer> getCertificatesByYear() {
        Map<Integer, Integer> distribution = new HashMap<>();
        for (Certificate cert : certificateManager.getAllCertificates()) {
            int year = cert.getYear();
            distribution.put(year, distribution.getOrDefault(year, 0) + 1);
        }
        return distribution;
    }
    
    /**
     * Get average nonce value
     */
    public double getAverageNonce() {
        List<Block> chain = blockchain.getChain();
        if (chain.isEmpty()) return 0;
        
        long totalNonce = 0;
        for (Block block : chain) {
            totalNonce += block.getNonce();
        }
        return (double) totalNonce / chain.size();
    }
    
    /**
     * Get blockchain health score (0-100)
     */
    public int getHealthScore() {
        int score = 0;
        
        // Chain validity (40 points)
        if (isChainValid()) {
            score += 40;
        }
        
        // Certificate count (30 points)
        int certCount = getTotalCertificates();
        if (certCount >= 10) score += 30;
        else if (certCount >= 5) score += 20;
        else if (certCount >= 1) score += 10;
        
        // Chain length (20 points)
        int chainLength = getChainLength();
        if (chainLength >= 10) score += 20;
        else if (chainLength >= 5) score += 15;
        else if (chainLength >= 2) score += 10;
        
        // Mining efficiency (10 points)
        long avgMiningTime = getAverageMiningTime();
        if (avgMiningTime < 1000) score += 10;
        else if (avgMiningTime < 5000) score += 7;
        else if (avgMiningTime < 10000) score += 5;
        
        return score;
    }
}