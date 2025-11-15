# 🔒 Security Scan Setup Guide - Step by Step

This guide provides detailed, step-by-step instructions to set up and validate all security scans in your GitHub Actions workflow.

---

## 📋 Prerequisites

Before starting, ensure you have:
- ✅ GitHub repository with Actions enabled
- ✅ Maven project (`pom.xml`)
- ✅ Java 17+ installed (for local testing)
- ✅ Git configured

---

## ✅ Step 1: Verify Current Configuration

### 1.1 Check Workflow File

Verify the workflow file exists:
```bash
ls -la .github/workflows/security-scan.yml
```

**Expected**: File should exist and be readable.

### 1.2 Check Gitleaks Configuration

Verify Gitleaks config exists:
```bash
ls -la .gitleaks.toml
```

**Expected**: File should exist with allowlist configurations.

### 1.3 Check Maven Plugins

Verify plugins are in `pom.xml`:
```bash
grep -E "dependency-check-maven|license-maven-plugin|spotbugs-maven-plugin" pom.xml
```

**Expected**: All three plugins should be found.

---

## 🔧 Step 2: Configure Maven Plugins (Already Done ✅)

The following plugins have been added to `pom.xml`:

### 2.1 OWASP Dependency-Check Plugin ✅

**Location**: `pom.xml` → `<build><plugins>`

**Plugin Details**:
- **GroupId**: `org.owasp`
- **ArtifactId**: `dependency-check-maven`
- **Version**: `9.0.9`
- **Configuration**:
  - Fails build on CVSS ≥ 7.0
  - Generates ALL report formats (HTML, JSON, XML)

**Verify**:
```bash
mvn help:describe -Dplugin=org.owasp:dependency-check-maven -Dgoal=check
```

### 2.2 License Maven Plugin ✅

**Location**: `pom.xml` → `<build><plugins>`

**Plugin Details**:
- **GroupId**: `com.mycila`
- **ArtifactId**: `license-maven-plugin`
- **Version**: `5.0.0`
- **Configuration**:
  - Non-strict mode (won't fail build)
  - Checks Java, XML, and Properties files

**Verify**:
```bash
mvn help:describe -Dplugin=com.mycila:license-maven-plugin -Dgoal=check
```

### 2.3 SpotBugs Plugin with FindSecBugs ✅

**Location**: `pom.xml` → `<build><plugins>`

**Plugin Details**:
- **GroupId**: `com.github.spotbugs`
- **ArtifactId**: `spotbugs-maven-plugin`
- **Version**: `4.8.3.6`
- **FindSecBugs**: `1.13.0`
- **Configuration**:
  - Maximum effort analysis
  - Low threshold (reports all issues)
  - XML and HTML output

**Verify**:
```bash
mvn help:describe -Dplugin=com.github.spotbugs:spotbugs-maven-plugin -Dgoal=check
```

---

## 🧪 Step 3: Test Locally (Optional but Recommended)

### 3.1 Test OWASP Dependency-Check

```bash
# First run downloads vulnerability database (~500MB)
mvn org.owasp:dependency-check-maven:check

# Expected: Scan completes, reports generated in target/
ls -la target/dependency-check-report.*
```

**Expected Output**:
- `target/dependency-check-report.html`
- `target/dependency-check-report.json`
- `target/dependency-check-report.xml`

**Troubleshooting**:
- If plugin not found: Check internet connection and Maven Central access
- If slow: First run downloads NVD database, subsequent runs are faster

### 3.2 Test License Check

```bash
mvn license:check

# Generate dependency tree (fallback used in workflow)
mvn dependency:tree > dependency-tree.txt
mvn dependency:list > dependency-list.txt
```

**Expected**: Command completes (may show warnings, but shouldn't fail)

### 3.3 Test SpotBugs

```bash
# Compile first
mvn clean compile

# Run SpotBugs
mvn spotbugs:check

# Generate HTML report
mvn spotbugs:spotbugs
```

**Expected Output**:
- `target/spotbugsXml.xml`
- `target/spotbugs.html`

**Note**: SpotBugs may find issues - this is expected. Review reports to understand findings.

---

## 🚀 Step 4: Verify Workflow Configuration

### 4.1 Check Workflow Triggers

Open `.github/workflows/security-scan.yml` and verify triggers:

```yaml
on:
  pull_request:
    types: [opened, synchronize, reopened, closed]
    branches:
      - develop
      - main
  push:
    branches:
      - release/**
  workflow_dispatch:
```

**What This Means**:
- ✅ Triggers on PR open/update (early detection)
- ✅ Triggers on PR merge (final validation)
- ✅ Triggers on release branches (pre-release check)
- ✅ Manual trigger available

### 4.2 Verify All Jobs Have Correct Conditions

All jobs should have the same trigger condition:
```yaml
if: |
  github.event_name == 'workflow_dispatch' ||
  github.event_name == 'push' ||
  (github.event_name == 'pull_request' && (
    github.event.action == 'opened' ||
    github.event.action == 'synchronize' ||
    github.event.action == 'reopened' ||
    (github.event.action == 'closed' && github.event.pull_request.merged == true)
  ))
```

---

## 📝 Step 5: Commit and Push Changes

### 5.1 Stage Changes

```bash
git add pom.xml
git add .github/workflows/security-scan.yml
git add docs/SECURITY_SCAN_VALIDATION.md
git add docs/SECURITY_SCAN_SETUP_GUIDE.md
```

### 5.2 Commit

```bash
git commit -m "feat: Add security scanning Maven plugins and improve SDLC integration

- Add OWASP Dependency-Check plugin for vulnerability scanning
- Add License Maven plugin for license compliance
- Add SpotBugs plugin with FindSecBugs for SAST analysis
- Update workflow triggers for early detection (PR open/update)
- Add pre-release trigger for release branches
- Improve security gate reporting"
```

### 5.3 Push to Feature Branch

```bash
git push origin feature/security-scan-pipeline
```

---

## ✅ Step 6: Test Workflow Execution

### 6.1 Create Test Pull Request

1. Create a new branch:
   ```bash
   git checkout -b test/security-scan-validation
   ```

2. Make a small change (e.g., add a comment to a Java file)

3. Commit and push:
   ```bash
   git add .
   git commit -m "test: Trigger security scans"
   git push origin test/security-scan-validation
   ```

4. Create PR targeting `develop` branch

### 6.2 Verify Workflow Triggers

1. Go to GitHub → Actions tab
2. You should see "🔒 Security Scans" workflow running
3. Click on the workflow run to see job execution

### 6.3 Check Each Job

Verify all 5 jobs run:
1. ✅ **Secret Detection (Gitleaks)** - Should complete
2. ✅ **Dependency Vulnerability Scan (OWASP)** - Should complete
3. ✅ **License Compliance Check** - Should complete
4. ✅ **SAST Analysis (SpotBugs)** - Should complete
5. ✅ **Security Gate** - Should aggregate results

### 6.4 Review Artifacts

After workflow completes:
1. Scroll to bottom of workflow run
2. Check "Artifacts" section
3. Download and verify:
   - `gitleaks-report` (JSON/TXT files)
   - `dependency-check-reports` (HTML/JSON/XML)
   - `license-reports` (TXT files)
   - `spotbugs-reports` (XML/HTML)

---

## 🔍 Step 7: Validate Scan Results

### 7.1 Secret Detection Results

**Check**: No secrets detected (or false positives documented)

**If secrets found**:
1. Rotate exposed secrets immediately
2. Remove from codebase
3. Add to `.gitleaks.toml` allowlist if false positive

### 7.2 Dependency Vulnerability Results

**Check**: Review HTML report for vulnerabilities

**If vulnerabilities found**:
1. Check CVSS scores
2. Update dependencies if patches available
3. Document risk acceptance if update not possible

### 7.3 License Compliance Results

**Check**: Review dependency licenses

**Action**: Ensure licenses are compatible with your project license

### 7.4 SAST Results

**Check**: Review SpotBugs HTML report

**Focus**: Security issues tagged with "FindSecBugs"

**Action**: Fix high-priority security issues

---

## 🎯 Step 8: Configure Branch Protection (Recommended)

To enforce security scans before merge:

1. Go to Repository Settings → Branches
2. Add rule for `develop` branch:
   - ✅ Require status checks to pass
   - Select: "🛡️ Security Gate"
   - ✅ Require branches to be up to date

This ensures PRs cannot merge if security scans fail.

---

## 📊 Step 9: Monitor and Maintain

### 9.1 Regular Review Schedule

- **Weekly**: Review dependency vulnerabilities
- **Monthly**: Review SpotBugs findings
- **Quarterly**: Review license compliance

### 9.2 Update Plugins

Check for plugin updates:
```bash
# Check latest versions
mvn versions:display-plugin-updates
```

Update versions in `pom.xml` properties section.

### 9.3 Monitor Workflow Performance

- Track execution times
- Optimize slow scans
- Review failure rates

---

## 🐛 Troubleshooting

### Issue: Workflow Doesn't Trigger

**Solution**:
- Verify workflow file is in `.github/workflows/`
- Check branch name matches trigger configuration
- Ensure Actions are enabled in repository settings

### Issue: Maven Plugin Not Found

**Solution**:
- Check internet connectivity in GitHub Actions
- Verify plugin versions are correct
- Check Maven Central accessibility

### Issue: OWASP Dependency-Check Times Out

**Solution**:
- First run downloads large database (~500MB)
- Subsequent runs use cache and are faster
- Consider increasing workflow timeout if needed

### Issue: SpotBugs Finds Too Many Issues

**Solution**:
- Adjust `threshold` in `pom.xml` (Low → Medium → High)
- Create exclusion file for known false positives
- Focus on security issues (FindSecBugs tagged)

### Issue: License Check Fails

**Solution**:
- Review dependency licenses
- Update `pom.xml` configuration if needed
- Document license exceptions

---

## ✅ Validation Checklist

Use this checklist to verify everything is working:

### Configuration Files
- [x] `.gitleaks.toml` exists and is valid
- [x] `.github/workflows/security-scan.yml` exists
- [x] `.github/actions/setup-java-maven/action.yml` exists
- [x] OWASP Dependency-Check plugin in `pom.xml`
- [x] License Maven plugin in `pom.xml`
- [x] SpotBugs plugin in `pom.xml`
- [x] FindSecBugs plugin in `pom.xml`

### Workflow Configuration
- [x] Secret detection step configured
- [x] Dependency scan step configured
- [x] License check step configured
- [x] SAST step configured
- [x] Security gate step configured
- [x] Artifact uploads configured
- [x] Summary generation configured

### SDLC Integration
- [x] Triggers on PR open ✅
- [x] Triggers on PR update ✅
- [x] Triggers on PR merge ✅
- [x] Triggers on release branches ✅
- [x] Manual trigger available ✅

### Testing
- [ ] Workflow runs on PR creation
- [ ] All jobs complete successfully
- [ ] Artifacts are generated
- [ ] Reports are readable
- [ ] Security gate works correctly

---

## 📚 Additional Resources

- [OWASP Dependency-Check Documentation](https://jeremylong.github.io/DependencyCheck/)
- [License Maven Plugin Documentation](https://www.mojohaus.org/license-maven-plugin/)
- [SpotBugs Documentation](https://spotbugs.github.io/)
- [FindSecBugs Documentation](https://find-sec-bugs.github.io/)
- [Gitleaks Documentation](https://github.com/gitleaks/gitleaks)

---

## 🎉 Success Criteria

Your security scanning setup is complete when:

1. ✅ All Maven plugins are configured in `pom.xml`
2. ✅ Workflow triggers on PR open/update/merge
3. ✅ All 4 security scans run successfully
4. ✅ Artifacts are generated and downloadable
5. ✅ Security gate aggregates results correctly
6. ✅ No critical configuration errors

---

**Next Steps**: Create a test PR to validate the complete setup!

