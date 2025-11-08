# QuorumCert - Quick Start Guide

**Get your blockchain certificate system running in 3 minutes! 🔐**

---

## ⚡ Super Quick Start

### Step 1: Check Java (30 seconds)

```bash
# Check Java version
java -version
```

Need Java 11+? Install from [Oracle](https://www.oracle.com/java/technologies/downloads/)

### Step 2: Compile (30 seconds)

```bash
# Navigate to project folder
cd QuorumCert

# Compile all files
javac *.java
```

### Step 3: Run (10 seconds)

```bash
java QuorumCert
```

**Done!** QuorumCert is now running! 🎉

---

## 🎯 First 5 Minutes Tutorial

### 1. Issue Your First Certificate (1 min)

```
Select: 1 (Issue New Certificate)

Enter:
- Student Name: John Smith
- Student ID: JS-2024-001
- Degree: BSc Computer Science
- Institution: University of Cambridge
- Year: 2024
- Grade: First Class Honours
- Field: Computer Science

✅ Certificate issued!
📝 Save the Certificate ID!
```

### 2. View the Blockchain (30 sec)

```
Select: 5 (View Blockchain)

You'll see:
┌────────────────────────────────────┐
│ BLOCK #0 (Genesis)                 │
├────────────────────────────────────┤
│ Hash: 0000a7f3c2e9...             │
└────────────────────────────────────┘
        ↓
┌────────────────────────────────────┐
│ BLOCK #1                           │
│ Certificate: John Smith - BSc      │
│ Hash: 0000b8d4a3f1...             │
└────────────────────────────────────┘
```

### 3. Verify Certificate (1 min)

```
Select: 2 (Verify Certificate)
Enter Certificate ID: CERT-A7B3C9D2

Result:
✅ CERTIFICATE VERIFIED - AUTHENTIC
   Status: VALID
   Confidence: 100%
```

### 4. See the Magic - Tampering Demo (2 min)

```
Select: 9 (Simulate Tampering Attack)
Type: yes

Watch:
✓ Blockchain valid before attack
⚙ Modifying block #1...
✗ TAMPERING DETECTED!
⚙ Blockchain restored

🛡️ This proves immutability!
```

---

## 📋 Menu Overview

```
1. Issue Certificate      → Add new credential
2. Verify Certificate     → Check authenticity
3. View Details          → See full info
4. List All             → Browse certificates
5. View Blockchain      → See chain structure
6. Validate Integrity   → Check for tampering
7. Export              → Save to JSON
8. Statistics          → View metrics
9. Demo Tampering      → Security demonstration
0. Exit                → Close application
```

---

## 💡 Common Tasks

### Issue Multiple Certificates

Just repeat option 1 for each certificate. Each gets added as a new block in the chain.

### Find a Certificate

```
Option 3 → View Certificate Details
Enter the Certificate ID
Full information displayed
```

### Check System Health

```
Option 8 → Display Statistics

Shows:
- Total blocks
- Certificates issued
- Verification count
- Mining performance
- Top institutions
```

### Export for Backup

```
Option 7 → Export Blockchain
Enter filename: my_blockchain
File saved as: my_blockchain.json
```

---

## 🔧 Troubleshooting

### "Command not found: javac"

**Problem:** Java not installed

**Fix:**
```bash
# Ubuntu/Debian
sudo apt install openjdk-11-jdk

# macOS
brew install openjdk@11

# Windows
Download from Oracle website
```

### "Error: Could not find main class"

**Problem:** Not compiled or wrong directory

**Fix:**
```bash
# Ensure you're in the right directory
ls *.java

# Recompile
javac *.java

# Run
java QuorumCert
```

### Slow Mining

**Normal!** Proof-of-work takes time:
- Difficulty 4 = ~200ms per block
- This proves computational work
- Security through effort

---

## 🎓 Understanding the Output

### Certificate ID Format

```
CERT-A7B3C9D2
  │    └─ Random hex (8 chars)
  └─ Prefix identifies it as certificate
```

### Hash Format

```
0000a7f3c2e9b1d4f8c3a2e1...
└┬─┘
  └─ Leading zeros = proof-of-work
     More zeros = more difficult
```

### Block Number

```
Block #0 = Genesis (first block, no cert)
Block #1 = First certificate
Block #2 = Second certificate
...
```

---

## 🚀 Next Steps

### For Learning

1. **Issue 5-10 certificates** to build a chain
2. **View blockchain** to see structure
3. **Try tampering demo** to understand security
4. **Validate chain** to see verification process

### For Portfolio

1. **Take screenshots** of key features
2. **Export blockchain** to show data
3. **Document insights** in your portfolio
4. **Explain concepts** in your own words

### For Development

1. **Read the code** - Well commented
2. **Modify difficulty** - See performance impact
3. **Add features** - Extend functionality
4. **Experiment** - Break and fix things

---

## 📊 Sample Session

```
$ java QuorumCert

═══════════════════════════════════════════
   QUORUMCERT - Certificate Verifier
   Author: Michael Semera
═══════════════════════════════════════════

⚙️  Initializing blockchain...
   Creating genesis block... ✓
   System ready!

MAIN MENU
─────────────────────────────────────────
1. Issue New Certificate
2. Verify Certificate
...

Enter choice: 1

Student Name: Alice Johnson
Degree: MSc Data Science
Institution: Oxford University
Year: 2024
Grade: Distinction

✅ Certificate issued!
   Certificate ID: CERT-F3A8B2C1
   Blockchain Hash: 0000d7e2f1a8...
   Block #1 created

Enter choice: 2

Certificate ID: CERT-F3A8B2C1

⚙️  Verifying...

✅ CERTIFICATE VERIFIED - AUTHENTIC
   Student: Alice Johnson
   Degree: MSc Data Science
   Institution: Oxford University
   Confidence: 100%
```

---

## ✅ Quick Verification Checklist

After setup, you should be able to:

- [ ] Compile without errors
- [ ] Run QuorumCert
- [ ] See welcome banner
- [ ] Issue a certificate
- [ ] Verify a certificate
- [ ] View blockchain
- [ ] See statistics
- [ ] Export to JSON

---

## 💬 Quick Q&A

**Q: How long does mining take?**  
A: 100-500ms per block with difficulty 4.

**Q: Is data saved?**  
A: In memory only. Export to JSON for persistence.

**Q: Can I modify certificates?**  
A: No! That's the point - immutability.

**Q: What if I close the program?**  
A: Data lost unless exported. Real systems use databases.

**Q: Is this production-ready?**  
A: Educational demo. Real systems need databases, networking, etc.

---

## 🎯 Success Indicators

You're doing great if:

✅ Blockchain validates successfully  
✅ Certificates are verifiable  
✅ Tampering is detected  
✅ Hashes start with 0000  
✅ Chain visualization makes sense  

---

## 📚 What to Learn Next

1. **Blockchain Basics**
   - Read Bitcoin whitepaper
   - Understand Proof-of-Work
   - Learn about consensus

2. **Cryptography**
   - SHA-256 algorithm
   - Digital signatures
   - Public-key cryptography

3. **Distributed Systems**
   - CAP theorem
   - Byzantine fault tolerance
   - Consensus mechanisms

4. **Java Advanced**
   - Concurrency
   - Networking (sockets)
   - Serialization

---

## 🔗 Useful Commands

```bash
# Compile
javac *.java

# Run
java QuorumCert

# Clean up
rm *.class

# Count lines of code
find . -name "*.java" | xargs wc -l

# View exported blockchain
cat my_blockchain.json | python -m json.tool
```

---

**Ready to start?**

```bash
javac *.java && java QuorumCert
```

**Have fun exploring blockchain technology! 🔐**

---

**Created by:** Michael Semera  
**For:** QuorumCert - Blockchain Certificate Verifier  
**Version:** 1.0.0