/**
 * CertificateManager.java
 * Manages certificate issuance and verification
 * Author: Michael Semera
 */

import java.util.*;

/**
 * Manager for certificate operations
 */
public class CertificateManager {
    private final CertificateBlockchain blockchain;
    private final Map<String, Certificate> certificates;
    private int verificationCount;
    
    /**
     * Constructor
     */
    public CertificateManager(CertificateBlockchain blockchain) {
        this.blockchain = blockchain;
        this.certificates = new HashMap<>();
        this.verificationCount = 0;
    }
    
    /**
     * Initialize manager
     */
    public void initialize() {
        // Any initialization logic
    }
    
    /**
     * Issue a new certificate
     */
    public String issueCertificate(Certificate certificate) {
        // Add to blockchain
        Block block = blockchain.addBlock(certificate);
        
        // Store in local map for quick lookup
        certificates.put(certificate.getCertificateId(), certificate);
        
        return certificate.getCertificateId();
    }
    
    /**
     * Verify a certificate
     */
    public VerificationResult verifyCertificate(String certificateId) {
        verificationCount++;
        
        // Check if certificate exists
        if (!certificates.containsKey(certificateId)) {
            return new VerificationResult(false, 
                "Certificate not found in the system", null);
        }
        
        Certificate certificate = certificates.get(certificateId);
        
        // Verify certificate hash
        if (!certificate.verifyHash()) {
            return new VerificationResult(false, 
                "Certificate data has been tampered with", certificate);
        }
        
        // Find block containing this certificate
        Block block = blockchain.findBlockByCertificateId(certificateId);
        if (block == null) {
            return new VerificationResult(false, 
                "Certificate not found in blockchain", certificate);
        }
        
        // Verify block integrity
        if (!block.isValid()) {
            return new VerificationResult(false, 
                "Block containing certificate is invalid", certificate);
        }
        
        // Verify blockchain integrity
        if (!blockchain.isChainValid()) {
            return new VerificationResult(false, 
                "Blockchain integrity compromised", certificate);
        }
        
        // Verify certificate hash matches block
        if (!block.getHash().equals(certificate.getBlockchainHash())) {
            return new VerificationResult(false, 
                "Certificate blockchain hash mismatch", certificate);
        }
        
        return new VerificationResult(true, 
            "Certificate is valid and verified on blockchain", certificate);
    }
    
    /**
     * Get certificate by ID
     */
    public Certificate getCertificate(String certificateId) {
        return certificates.get(certificateId);
    }
    
    /**
     * Get all certificates
     */
    public List<Certificate> getAllCertificates() {
        return new ArrayList<>(certificates.values());
    }
    
    /**
     * Get certificates by institution
     */
    public List<Certificate> getCertificatesByInstitution(String institution) {
        List<Certificate> result = new ArrayList<>();
        for (Certificate cert : certificates.values()) {
            if (cert.getInstitution().equalsIgnoreCase(institution)) {
                result.add(cert);
            }
        }
        return result;
    }
    
    /**
     * Get certificates by year
     */
    public List<Certificate> getCertificatesByYear(int year) {
        List<Certificate> result = new ArrayList<>();
        for (Certificate cert : certificates.values()) {
            if (cert.getYear() == year) {
                result.add(cert);
            }
        }
        return result;
    }
    
    /**
     * Get certificate count
     */
    public int getCertificateCount() {
        return certificates.size();
    }
    
    /**
     * Get verification count
     */
    public int getVerificationCount() {
        return verificationCount;
    }
    
    /**
     * Get unique institutions
     */
    public Set<String> getUniqueInstitutions() {
        Set<String> institutions = new HashSet<>();
        for (Certificate cert : certificates.values()) {
            institutions.add(cert.getInstitution());
        }
        return institutions;
    }
    
    /**
     * Get institution statistics
     */
    public Map<String, Integer> getInstitutionStatistics() {
        Map<String, Integer> stats = new HashMap<>();
        for (Certificate cert : certificates.values()) {
            String institution = cert.getInstitution();
            stats.put(institution, stats.getOrDefault(institution, 0) + 1);
        }
        return stats;
    }
}

/**
 * Verification result class
 */
class VerificationResult {
    private final boolean valid;
    private final String message;
    private final Certificate certificate;
    
    public VerificationResult(boolean valid, String message, Certificate certificate) {
        this.valid = valid;
        this.message = message;
        this.certificate = certificate;
    }
    
    public boolean isValid() { return valid; }
    public String getMessage() { return message; }
    public Certificate getCertificate() { return certificate; }
}