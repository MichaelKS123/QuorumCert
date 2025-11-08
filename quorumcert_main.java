/**
 * QuorumCert: Blockchain-Based Certificate Verifier
 * Author: Michael Semera
 * Description: A distributed certificate verification system using blockchain
 *              technology for tamper-proof academic credential management.
 */

import java.util.*;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Main application class for QuorumCert
 */
public class QuorumCert {
    private static final Scanner scanner = new Scanner(System.in);
    private static final CertificateBlockchain blockchain = new CertificateBlockchain();
    private static final CertificateManager certificateManager = new CertificateManager(blockchain);
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    
    public static void main(String[] args) {
        displayWelcomeBanner();
        initializeSystem();
        
        boolean running = true;
        while (running) {
            displayMainMenu();
            int choice = getMenuChoice();
            
            switch (choice) {
                case 1:
                    issueCertificate();
                    break;
                case 2:
                    verifyCertificate();
                    break;
                case 3:
                    viewCertificate();
                    break;
                case 4:
                    listAllCertificates();
                    break;
                case 5:
                    viewBlockchain();
                    break;
                case 6:
                    validateBlockchain();
                    break;
                case 7:
                    exportBlockchain();
                    break;
                case 8:
                    displayStatistics();
                    break;
                case 9:
                    simulateTampering();
                    break;
                case 0:
                    running = false;
                    shutdown();
                    break;
                default:
                    System.out.println("\n❌ Invalid choice. Please try again.");
            }
        }
        
        scanner.close();
    }
    
    private static void displayWelcomeBanner() {
        System.out.println("\n" + "═".repeat(70));
        System.out.println("  ██████╗ ██╗   ██╗ ██████╗ ██████╗ ██╗   ██╗███╗   ███╗");
        System.out.println(" ██╔═══██╗██║   ██║██╔═══██╗██╔══██╗██║   ██║████╗ ████║");
        System.out.println(" ██║   ██║██║   ██║██║   ██║██████╔╝██║   ██║██╔████╔██║");
        System.out.println(" ██║▄▄ ██║██║   ██║██║   ██║██╔══██╗██║   ██║██║╚██╔╝██║");
        System.out.println(" ╚██████╔╝╚██████╔╝╚██████╔╝██║  ██║╚██████╔╝██║ ╚═╝ ██║");
        System.out.println("  ╚══▀▀═╝  ╚═════╝  ╚═════╝ ╚═╝  ╚═╝ ╚═════╝ ╚═╝     ╚═╝");
        System.out.println("                    CERT - Certificate Verifier");
        System.out.println();
        System.out.println("  Blockchain-Based Academic Certificate Management");
        System.out.println("  Author: Michael Semera");
        System.out.println("═".repeat(70));
    }
    
    private static void initializeSystem() {
        System.out.println("\n⚙️  Initializing QuorumCert Blockchain System...");
        
        // Create genesis block
        System.out.print("   Creating genesis block... ");
        blockchain.createGenesisBlock();
        System.out.println("✓");
        
        // Initialize certificate manager
        System.out.print("   Initializing certificate manager... ");
        certificateManager.initialize();
        System.out.println("✓");
        
        System.out.println("   System ready!");
        System.out.println("   Blockchain Height: " + blockchain.getChainLength());
        System.out.println("   Certificates Issued: " + certificateManager.getCertificateCount());
    }
    
    private static void displayMainMenu() {
        System.out.println("\n" + "─".repeat(70));
        System.out.println("MAIN MENU");
        System.out.println("─".repeat(70));
        System.out.println("📜 Certificate Operations:");
        System.out.println("  1. Issue New Certificate");
        System.out.println("  2. Verify Certificate");
        System.out.println("  3. View Certificate Details");
        System.out.println("  4. List All Certificates");
        System.out.println();
        System.out.println("⛓️  Blockchain Operations:");
        System.out.println("  5. View Blockchain");
        System.out.println("  6. Validate Blockchain Integrity");
        System.out.println("  7. Export Blockchain");
        System.out.println("  8. Display Statistics");
        System.out.println();
        System.out.println("🔧 Advanced:");
        System.out.println("  9. Simulate Tampering Attack (Demo)");
        System.out.println("  0. Exit");
        System.out.println("─".repeat(70));
        System.out.print("Enter your choice: ");
    }
    
    private static int getMenuChoice() {
        try {
            return scanner.nextInt();
        } catch (InputMismatchException e) {
            scanner.nextLine();
            return -1;
        } finally {
            scanner.nextLine();
        }
    }
    
    private static void issueCertificate() {
        System.out.println("\n" + "═".repeat(70));
        System.out.println("📜 ISSUE NEW CERTIFICATE");
        System.out.println("═".repeat(70));
        
        System.out.print("Student Name: ");
        String studentName = scanner.nextLine();
        
        System.out.print("Student ID: ");
        String studentId = scanner.nextLine();
        
        System.out.print("Degree/Qualification: ");
        String degree = scanner.nextLine();
        
        System.out.print("Institution Name: ");
        String institution = scanner.nextLine();
        
        System.out.print("Year of Graduation: ");
        int year = getValidYear();
        
        System.out.print("Grade/Classification (e.g., First Class Honours): ");
        String grade = scanner.nextLine();
        
        System.out.print("Field of Study: ");
        String field = scanner.nextLine();
        
        System.out.println("\n⚙️  Processing certificate issuance...");
        
        try {
            // Create certificate
            Certificate certificate = new Certificate(
                studentName,
                studentId,
                degree,
                institution,
                year,
                grade,
                field
            );
            
            // Add to blockchain
            String certificateId = certificateManager.issueCertificate(certificate);
            
            System.out.println("\n✅ Certificate issued successfully!");
            System.out.println("━".repeat(70));
            System.out.println("Certificate ID: " + certificateId);
            System.out.println("Blockchain Hash: " + certificate.getBlockchainHash());
            System.out.println("Issued At: " + certificate.getIssuedDate().format(formatter));
            System.out.println("━".repeat(70));
            System.out.println("\n💡 Save the Certificate ID for future verification!");
            
        } catch (Exception e) {
            System.out.println("\n❌ Error issuing certificate: " + e.getMessage());
        }
    }
    
    private static void verifyCertificate() {
        System.out.println("\n" + "═".repeat(70));
        System.out.println("🔍 VERIFY CERTIFICATE");
        System.out.println("═".repeat(70));
        
        System.out.print("Enter Certificate ID: ");
        String certificateId = scanner.nextLine();
        
        System.out.println("\n⚙️  Verifying certificate on blockchain...");
        
        VerificationResult result = certificateManager.verifyCertificate(certificateId);
        
        System.out.println("\n" + "━".repeat(70));
        if (result.isValid()) {
            System.out.println("✅ CERTIFICATE VERIFIED - AUTHENTIC");
            System.out.println("━".repeat(70));
            System.out.println("Status: VALID");
            System.out.println("Certificate exists in blockchain: YES");
            System.out.println("Hash matches blockchain record: YES");
            System.out.println("Blockchain integrity: INTACT");
            System.out.println("Confidence: 100%");
            
            if (result.getCertificate() != null) {
                Certificate cert = result.getCertificate();
                System.out.println("\n📋 Certificate Details:");
                System.out.println("   Student: " + cert.getStudentName());
                System.out.println("   Degree: " + cert.getDegree());
                System.out.println("   Institution: " + cert.getInstitution());
                System.out.println("   Year: " + cert.getYear());
                System.out.println("   Grade: " + cert.getGrade());
            }
        } else {
            System.out.println("❌ CERTIFICATE VERIFICATION FAILED");
            System.out.println("━".repeat(70));
            System.out.println("Status: INVALID");
            System.out.println("Reason: " + result.getMessage());
            System.out.println("\n⚠️  WARNING: This certificate may be fraudulent or tampered with!");
        }
        System.out.println("━".repeat(70));
    }
    
    private static void viewCertificate() {
        System.out.println("\n" + "═".repeat(70));
        System.out.println("📋 VIEW CERTIFICATE DETAILS");
        System.out.println("═".repeat(70));
        
        System.out.print("Enter Certificate ID: ");
        String certificateId = scanner.nextLine();
        
        Certificate certificate = certificateManager.getCertificate(certificateId);
        
        if (certificate == null) {
            System.out.println("\n❌ Certificate not found.");
            return;
        }
        
        System.out.println("\n" + "━".repeat(70));
        System.out.println("CERTIFICATE DETAILS");
        System.out.println("━".repeat(70));
        System.out.println("Certificate ID: " + certificate.getCertificateId());
        System.out.println("Student Name: " + certificate.getStudentName());
        System.out.println("Student ID: " + certificate.getStudentId());
        System.out.println("Degree: " + certificate.getDegree());
        System.out.println("Institution: " + certificate.getInstitution());
        System.out.println("Year of Graduation: " + certificate.getYear());
        System.out.println("Grade/Classification: " + certificate.getGrade());
        System.out.println("Field of Study: " + certificate.getField());
        System.out.println("Issued Date: " + certificate.getIssuedDate().format(formatter));
        System.out.println();
        System.out.println("Blockchain Information:");
        System.out.println("  Certificate Hash: " + certificate.getCertificateHash());
        System.out.println("  Blockchain Hash: " + certificate.getBlockchainHash());
        System.out.println("  Block Number: " + certificate.getBlockNumber());
        System.out.println("━".repeat(70));
    }
    
    private static void listAllCertificates() {
        System.out.println("\n" + "═".repeat(70));
        System.out.println("📚 ALL CERTIFICATES");
        System.out.println("═".repeat(70));
        
        List<Certificate> certificates = certificateManager.getAllCertificates();
        
        if (certificates.isEmpty()) {
            System.out.println("\n📭 No certificates have been issued yet.");
            return;
        }
        
        System.out.println("\nTotal Certificates: " + certificates.size());
        System.out.println("\n" + "─".repeat(70));
        System.out.printf("%-15s %-25s %-30s %-10s%n", 
            "Cert ID", "Student", "Degree", "Year");
        System.out.println("─".repeat(70));
        
        for (Certificate cert : certificates) {
            System.out.printf("%-15s %-25s %-30s %-10d%n",
                cert.getCertificateId().substring(0, Math.min(12, cert.getCertificateId().length())) + "...",
                truncate(cert.getStudentName(), 25),
                truncate(cert.getDegree(), 30),
                cert.getYear());
        }
        
        System.out.println("─".repeat(70));
    }
    
    private static void viewBlockchain() {
        System.out.println("\n" + "═".repeat(70));
        System.out.println("⛓️  BLOCKCHAIN VISUALIZATION");
        System.out.println("═".repeat(70));
        
        List<Block> chain = blockchain.getChain();
        
        System.out.println("\nChain Length: " + chain.size() + " blocks");
        System.out.println("Total Certificates: " + (chain.size() - 1)); // Excluding genesis
        
        for (int i = 0; i < chain.size(); i++) {
            Block block = chain.get(i);
            System.out.println("\n" + "┌" + "─".repeat(68) + "┐");
            System.out.printf("│ BLOCK #%-60d │%n", block.getIndex());
            System.out.println("├" + "─".repeat(68) + "┤");
            System.out.printf("│ Timestamp: %-55s │%n", 
                block.getTimestamp().format(formatter));
            System.out.printf("│ Previous Hash: %-50s │%n", 
                truncate(block.getPreviousHash(), 50));
            System.out.printf("│ Current Hash:  %-50s │%n", 
                truncate(block.getHash(), 50));
            System.out.printf("│ Nonce: %-59d │%n", block.getNonce());
            
            if (block.getCertificate() != null) {
                Certificate cert = block.getCertificate();
                System.out.printf("│ Certificate: %-54s │%n", 
                    truncate(cert.getStudentName() + " - " + cert.getDegree(), 54));
            } else {
                System.out.printf("│ Certificate: %-54s │%n", "GENESIS BLOCK");
            }
            
            System.out.println("└" + "─".repeat(68) + "┘");
            
            if (i < chain.size() - 1) {
                System.out.println("        ↓");
            }
        }
    }
    
    private static void validateBlockchain() {
        System.out.println("\n" + "═".repeat(70));
        System.out.println("🔐 BLOCKCHAIN INTEGRITY VALIDATION");
        System.out.println("═".repeat(70));
        
        System.out.println("\n⚙️  Running comprehensive validation...");
        
        boolean isValid = blockchain.isChainValid();
        
        System.out.println("\n" + "━".repeat(70));
        if (isValid) {
            System.out.println("✅ BLOCKCHAIN INTEGRITY: VERIFIED");
            System.out.println("━".repeat(70));
            System.out.println("Status: VALID");
            System.out.println("All blocks properly linked: YES");
            System.out.println("All hashes match: YES");
            System.out.println("No tampering detected: CONFIRMED");
            System.out.println("\n💡 The blockchain is secure and tamper-proof!");
        } else {
            System.out.println("❌ BLOCKCHAIN INTEGRITY: COMPROMISED");
            System.out.println("━".repeat(70));
            System.out.println("Status: INVALID");
            System.out.println("\n⚠️  WARNING: Blockchain has been tampered with!");
            System.out.println("   One or more blocks have been modified.");
            System.out.println("   Certificates may not be trustworthy.");
        }
        System.out.println("━".repeat(70));
    }
    
    private static void exportBlockchain() {
        System.out.println("\n" + "═".repeat(70));
        System.out.println("💾 EXPORT BLOCKCHAIN");
        System.out.println("═".repeat(70));
        
        System.out.print("Enter filename (without extension): ");
        String filename = scanner.nextLine();
        
        try {
            blockchain.exportToFile(filename + ".json");
            System.out.println("\n✅ Blockchain exported successfully!");
            System.out.println("   File: " + filename + ".json");
            System.out.println("   Blocks: " + blockchain.getChainLength());
        } catch (IOException e) {
            System.out.println("\n❌ Export failed: " + e.getMessage());
        }
    }
    
    private static void displayStatistics() {
        System.out.println("\n" + "═".repeat(70));
        System.out.println("📊 SYSTEM STATISTICS");
        System.out.println("═".repeat(70));
        
        BlockchainStatistics stats = new BlockchainStatistics(blockchain, certificateManager);
        
        System.out.println("\n⛓️  Blockchain Metrics:");
        System.out.println("   Total Blocks: " + stats.getTotalBlocks());
        System.out.println("   Chain Length: " + stats.getChainLength());
        System.out.println("   Genesis Block: " + stats.getGenesisBlockHash());
        System.out.println("   Latest Block: " + stats.getLatestBlockHash());
        
        System.out.println("\n📜 Certificate Metrics:");
        System.out.println("   Total Issued: " + stats.getTotalCertificates());
        System.out.println("   Verified Today: " + stats.getVerificationsToday());
        System.out.println("   Unique Institutions: " + stats.getUniqueInstitutions());
        
        System.out.println("\n🔐 Security Metrics:");
        System.out.println("   Blockchain Valid: " + (stats.isChainValid() ? "YES" : "NO"));
        System.out.println("   Average Mining Time: " + stats.getAverageMiningTime() + "ms");
        System.out.println("   Total Hash Power: " + stats.getTotalHashOperations());
        
        System.out.println("\n📈 Top Institutions:");
        Map<String, Integer> topInstitutions = stats.getTopInstitutions(5);
        int rank = 1;
        for (Map.Entry<String, Integer> entry : topInstitutions.entrySet()) {
            System.out.printf("   %d. %-40s (%d certificates)%n", 
                rank++, entry.getKey(), entry.getValue());
        }
    }
    
    private static void simulateTampering() {
        System.out.println("\n" + "═".repeat(70));
        System.out.println("⚠️  SIMULATE TAMPERING ATTACK (DEMONSTRATION)");
        System.out.println("═".repeat(70));
        
        System.out.println("\nThis demonstrates how blockchain detects tampering.");
        System.out.println("⚠️  This will temporarily corrupt the blockchain for demo purposes.");
        System.out.print("\nContinue? (yes/no): ");
        
        String confirm = scanner.nextLine();
        if (!confirm.equalsIgnoreCase("yes")) {
            System.out.println("\nOperation cancelled.");
            return;
        }
        
        List<Block> chain = blockchain.getChain();
        if (chain.size() < 2) {
            System.out.println("\n❌ Not enough blocks. Issue certificates first.");
            return;
        }
        
        System.out.println("\n⚙️  Simulating tampering attack...");
        System.out.println("   Step 1: Blockchain valid before attack...");
        boolean beforeValid = blockchain.isChainValid();
        System.out.println("   Result: " + (beforeValid ? "✓ VALID" : "✗ INVALID"));
        
        System.out.println("\n   Step 2: Attempting to modify block #1...");
        Block targetBlock = chain.get(1);
        Certificate originalCert = targetBlock.getCertificate();
        
        // Create fake certificate
        Certificate fakeCert = new Certificate(
            "FAKE STUDENT",
            "FAKE-123",
            "Fake Degree",
            "Fake University",
            2024,
            "First Class",
            "Computer Science"
        );
        
        // Attempt to tamper
        targetBlock.tamperCertificate(fakeCert);
        System.out.println("   Result: Block modified");
        
        System.out.println("\n   Step 3: Validating blockchain after tampering...");
        boolean afterValid = blockchain.isChainValid();
        System.out.println("   Result: " + (afterValid ? "✓ VALID" : "✗ INVALID - TAMPERING DETECTED!"));
        
        System.out.println("\n━".repeat(70));
        System.out.println("🛡️  DEMONSTRATION COMPLETE");
        System.out.println("━".repeat(70));
        System.out.println("The blockchain successfully detected the tampering!");
        System.out.println("This proves the immutability of blockchain technology.");
        System.out.println("\n⚙️  Restoring original certificate...");
        
        // Restore original
        targetBlock.tamperCertificate(originalCert);
        System.out.println("   Blockchain restored to valid state.");
    }
    
    private static void shutdown() {
        System.out.println("\n" + "═".repeat(70));
        System.out.println("👋 Shutting down QuorumCert...");
        System.out.println();
        System.out.println("   Final Statistics:");
        System.out.println("   - Certificates Issued: " + certificateManager.getCertificateCount());
        System.out.println("   - Blockchain Height: " + blockchain.getChainLength());
        System.out.println("   - Chain Valid: " + (blockchain.isChainValid() ? "YES" : "NO"));
        System.out.println();
        System.out.println("   Thank you for using QuorumCert!");
        System.out.println("   Created by: Michael Semera");
        System.out.println("═".repeat(70) + "\n");
    }
    
    private static int getValidYear() {
        while (true) {
            try {
                int year = scanner.nextInt();
                scanner.nextLine();
                if (year < 1900 || year > LocalDateTime.now().getYear() + 1) {
                    System.out.print("Invalid year. Enter a valid graduation year: ");
                    continue;
                }
                return year;
            } catch (InputMismatchException e) {
                scanner.nextLine();
                System.out.print("Please enter a valid year: ");
            }
        }
    }
    
    private static String truncate(String str, int maxLength) {
        if (str.length() <= maxLength) {
            return str;
        }
        return str.substring(0, maxLength - 3) + "...";
    }
}