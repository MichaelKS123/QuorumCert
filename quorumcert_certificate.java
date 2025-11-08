/**
 * Certificate.java
 * Represents an academic certificate
 * Author: Michael Semera
 */

import java.time.LocalDateTime;
import java.security.MessageDigest;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

/**
 * Certificate class representing an academic credential
 */
public class Certificate {
    private final String certificateId;
    private final String studentName;
    private final String studentId;
    private final String degree;
    private final String institution;
    private final int year;
    private final String grade;
    private final String field;
    private final LocalDateTime issuedDate;
    private final String certificateHash;
    private String blockchainHash;
    private int blockNumber;
    
    /**
     * Constructor
     */
    public Certificate(String studentName, String studentId, String degree,
                      String institution, int year, String grade, String field) {
        this.certificateId = generateCertificateId();
        this.studentName = studentName;
        this.studentId = studentId;
        this.degree = degree;
        this.institution = institution;
        this.year = year;
        this.grade = grade;
        this.field = field;
        this.issuedDate = LocalDateTime.now();
        this.certificateHash = calculateCertificateHash();
        this.blockchainHash = "";
        this.blockNumber = -1;
    }
    
    /**
     * Generate unique certificate ID
     */
    private String generateCertificateId() {
        return "CERT-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
    
    /**
     * Calculate hash of certificate data
     */
    private String calculateCertificateHash() {
        String data = certificateId +
                     studentName +
                     studentId +
                     degree +
                     institution +
                     year +
                     grade +
                     field +
                     issuedDate.toString();
        
        return hashString(data);
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
            throw new RuntimeException("Error hashing certificate: " + e.getMessage());
        }
    }
    
    /**
     * Verify certificate hash
     */
    public boolean verifyHash() {
        return certificateHash.equals(calculateCertificateHash());
    }
    
    /**
     * Set blockchain information
     */
    public void setBlockchainInfo(String blockchainHash, int blockNumber) {
        this.blockchainHash = blockchainHash;
        this.blockNumber = blockNumber;
    }
    
    // Getters
    public String getCertificateId() { return certificateId; }
    public String getStudentName() { return studentName; }
    public String getStudentId() { return studentId; }
    public String getDegree() { return degree; }
    public String getInstitution() { return institution; }
    public int getYear() { return year; }
    public String getGrade() { return grade; }
    public String getField() { return field; }
    public LocalDateTime getIssuedDate() { return issuedDate; }
    public String getCertificateHash() { return certificateHash; }
    public String getBlockchainHash() { return blockchainHash; }
    public int getBlockNumber() { return blockNumber; }
    
    @Override
    public String toString() {
        return String.format("Certificate[ID=%s, Student=%s, Degree=%s, Institution=%s, Year=%d]",
            certificateId, studentName, degree, institution, year);
    }
    
    /**
     * Get certificate details as formatted string
     */
    public String getDetails() {
        StringBuilder sb = new StringBuilder();
        sb.append("Certificate ID: ").append(certificateId).append("\n");
        sb.append("Student: ").append(studentName).append(" (").append(studentId).append(")\n");
        sb.append("Degree: ").append(degree).append("\n");
        sb.append("Institution: ").append(institution).append("\n");
        sb.append("Year: ").append(year).append("\n");
        sb.append("Grade: ").append(grade).append("\n");
        sb.append("Field: ").append(field).append("\n");
        sb.append("Issued: ").append(issuedDate).append("\n");
        sb.append("Certificate Hash: ").append(certificateHash).append("\n");
        if (!blockchainHash.isEmpty()) {
            sb.append("Blockchain Hash: ").append(blockchainHash).append("\n");
            sb.append("Block Number: ").append(blockNumber).append("\n");
        }
        return sb.toString();
    }
}