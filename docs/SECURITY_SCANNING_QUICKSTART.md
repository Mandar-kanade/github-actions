# 🚀 Security Scanning Quick Start Guide

This is a quick reference guide for the security scanning pipeline. For detailed information, see [SECURITY_SCANNING_SETUP.md](./SECURITY_SCANNING_SETUP.md).

## ⚡ Quick Overview

The security scanning pipeline automatically runs when a PR is merged to the `develop` branch. It includes:

1. **🔐 Secret Detection** - Finds hardcoded secrets
2. **🛡️ Dependency Vulnerability Scan** - Checks for known CVEs
3. **📜 License Compliance** - Verifies dependency licenses
4. **🔬 SAST Analysis** - Static security analysis

## 🎯 How It Works

### Automatic Trigger

The workflow runs automatically when:
- ✅ A Pull Request is merged into `develop` branch

### Manual Trigger

You can also trigger it manually:
1. Go to **Actions** tab in GitHub
2. Select **🔒 Security Scans** workflow
3. Click **Run workflow** → **Run workflow**

## 📊 Viewing Results

### Step 1: Navigate to Actions
Go to your repository → **Actions** tab

### Step 2: Find the Workflow Run
Look for **🔒 Security Scans** workflow runs

### Step 3: View Summary
Click on a workflow run to see:
- ✅ Job status for each scan
- 📊 Summary in the workflow summary section
- 📁 Artifacts with detailed reports

### Step 4: Download Reports
1. Scroll to the **Artifacts** section
2. Download the reports you need:
   - `gitleaks-report` - Secret detection findings
   - `dependency-check-reports` - Vulnerability scan results
   - `license-reports` - License compliance info
   - `spotbugs-reports` - SAST analysis results

## 🔧 Common Tasks

### Adding False Positives to Gitleaks

Edit `.gitleaks.toml`:

```toml
[allowlist]
paths = [
    '''path/to/your/file/.*'''
]
```

### Adjusting Vulnerability Threshold

Edit `pom.xml`:

```xml
<plugin>
    <groupId>org.owasp</groupId>
    <artifactId>dependency-check-maven</artifactId>
    <configuration>
        <failBuildOnCVSS>7.0</failBuildOnCVSS>  <!-- Change this value -->
    </configuration>
</plugin>
```

### Adjusting SpotBugs Sensitivity

Edit `pom.xml`:

```xml
<plugin>
    <groupId>com.github.spotbugs</groupId>
    <artifactId>spotbugs-maven-plugin</artifactId>
    <configuration>
        <threshold>Medium</threshold>  <!-- Low, Medium, or High -->
    </configuration>
</plugin>
```

## ⚠️ Troubleshooting

### Workflow Not Running?

**Check:**
- ✅ PR was **merged** (not just closed)
- ✅ Target branch is `develop`
- ✅ Workflow file exists at `.github/workflows/security-scan.yml`

### Scan Taking Too Long?

**First Run**: OWASP Dependency-Check downloads vulnerability data (~500MB). This is normal and cached for future runs.

**Subsequent Runs**: Should be faster due to caching.

### Too Many False Positives?

1. **Gitleaks**: Add patterns to `.gitleaks.toml` allowlist
2. **SpotBugs**: Increase threshold in `pom.xml`
3. **Dependency-Check**: Create `.dependency-check-suppressions.xml`

## 📚 Key Files

| File | Purpose |
|------|---------|
| `.github/workflows/security-scan.yml` | Workflow definition |
| `.gitleaks.toml` | Gitleaks configuration |
| `pom.xml` | Maven plugins configuration |
| `SECURITY_SCANNING_SETUP.md` | Detailed documentation |

## 🆘 Need Help?

1. Check [SECURITY_SCANNING_SETUP.md](./SECURITY_SCANNING_SETUP.md) for detailed docs
2. Review workflow logs in GitHub Actions
3. Check scan reports in artifacts
4. Consult tool documentation (links in main doc)

---

**Quick Tip**: All scans are configured to be non-blocking (won't fail the build) but will report findings. Review the reports and address issues as needed.

