# 🔒 Security Scanning Setup Guide

This document provides comprehensive information about the security scanning pipeline integrated into this project.

## 📋 Table of Contents

1. [Overview](#overview)
2. [Security Scans Included](#security-scans-included)
3. [Pipeline Architecture](#pipeline-architecture)
4. [Tools and Technologies](#tools-and-technologies)
5. [Workflow Triggers](#workflow-triggers)
6. [Configuration](#configuration)
7. [Understanding Scan Results](#understanding-scan-results)
8. [Troubleshooting](#troubleshooting)
9. [Best Practices](#best-practices)

## 🎯 Overview

The security scanning pipeline automatically runs comprehensive security checks when a Pull Request is merged into the `develop` branch. The pipeline includes:

- **Secret Detection**: Scans for hardcoded secrets, API keys, and passwords
- **Dependency Vulnerability Scanning**: Identifies known vulnerabilities in dependencies
- **License Compliance**: Verifies dependency licenses meet compliance requirements
- **SAST (Static Application Security Testing)**: Analyzes source code for security vulnerabilities

All scans use **free, industry-standard tools** that integrate seamlessly with GitHub Actions.

## 🔍 Security Scans Included

### 1. Secret Detection (Gitleaks)

**Purpose**: Detects hardcoded secrets, API keys, passwords, tokens, and other sensitive information in the codebase.

**Tool**: [Gitleaks](https://github.com/gitleaks/gitleaks)

**What it scans**:
- API keys (AWS, Google Cloud, Azure, etc.)
- Database credentials
- OAuth tokens
- Private keys
- Passwords
- Access tokens
- And more...

**Configuration**: `.gitleaks.toml`

**Report Location**: Workflow artifacts → `gitleaks-report`

### 2. Dependency Vulnerability Scan (OWASP Dependency-Check)

**Purpose**: Identifies known security vulnerabilities in project dependencies using the National Vulnerability Database (NVD).

**Tool**: [OWASP Dependency-Check](https://owasp.org/www-project-dependency-check/)

**What it scans**:
- Maven dependencies
- Known CVEs (Common Vulnerabilities and Exposures)
- CVSS scores (Common Vulnerability Scoring System)
- Vulnerability severity levels

**Configuration**: `pom.xml` → OWASP Dependency-Check Maven Plugin

**Threshold**: Fails build if CVSS score ≥ 7.0 (High/Critical vulnerabilities)

**Report Formats**: HTML, JSON, XML

**Report Location**: Workflow artifacts → `dependency-check-reports`

### 3. License Compliance Check

**Purpose**: Verifies that all dependencies comply with your organization's licensing requirements.

**Tool**: [License Maven Plugin](https://www.mojohaus.org/license-maven-plugin/)

**What it checks**:
- License headers in source files
- Third-party dependency licenses
- License compatibility

**Configuration**: `pom.xml` → License Maven Plugin

**Report Location**: Workflow artifacts → `license-reports`

### 4. SAST Analysis (SpotBugs + FindSecBugs)

**Purpose**: Performs static analysis of compiled bytecode to identify security vulnerabilities and code quality issues.

**Tool**: [SpotBugs](https://spotbugs.github.io/) with [FindSecBugs](https://find-sec-bugs.github.io/) plugin

**What it detects**:
- SQL injection vulnerabilities
- Cross-site scripting (XSS)
- Insecure random number generation
- Hardcoded passwords
- Weak cryptography
- Path traversal vulnerabilities
- And 100+ other security issues

**Configuration**: `pom.xml` → SpotBugs Maven Plugin with FindSecBugs

**Report Formats**: XML, HTML

**Report Location**: Workflow artifacts → `spotbugs-reports`

## 🏗️ Pipeline Architecture

The security scanning pipeline runs in parallel where possible for optimal performance:

```
┌─────────────────────────────────────────────────────────┐
│  Security Scan Workflow (Triggered on PR merge to develop) │
└─────────────────────────────────────────────────────────┘
                          │
        ┌─────────────────┼─────────────────┐
        │                 │                 │
        ▼                 ▼                 ▼
┌──────────────┐  ┌──────────────┐  ┌──────────────┐
│   Secret     │  │  Dependency  │  │   License    │
│  Detection   │  │   Scan       │  │    Check     │
│  (Gitleaks)  │  │  (OWASP)     │  │  (Maven)     │
└──────────────┘  └──────────────┘  └──────────────┘
        │                 │                 │
        └─────────────────┼─────────────────┘
                          │
                          ▼
                 ┌──────────────┐
                 │     SAST     │
                 │  (SpotBugs)  │
                 └──────────────┘
                          │
                          ▼
                 ┌──────────────┐
                 │   Security   │
                 │     Gate     │
                 └──────────────┘
```

### Job Execution Flow

1. **Phase 1**: Secret Detection (runs independently, fastest)
2. **Phase 2**: Dependency Vulnerability Scan (requires Maven setup)
3. **Phase 3**: License Compliance Check (requires Maven setup)
4. **Phase 4**: SAST Analysis (requires compiled code)
5. **Phase 5**: Security Gate (waits for all scans, generates summary)

## 🛠️ Tools and Technologies

| Tool | Type | License | Purpose |
|------|------|---------|---------|
| Gitleaks | CLI Tool | MIT | Secret detection |
| OWASP Dependency-Check | Maven Plugin | Apache 2.0 | Dependency vulnerability scanning |
| License Maven Plugin | Maven Plugin | Apache 2.0 | License compliance |
| SpotBugs | Maven Plugin | LGPL | Static analysis |
| FindSecBugs | SpotBugs Plugin | LGPL | Security-focused static analysis |

All tools are **free and open-source**.

## ⚙️ Workflow Triggers

The security scanning workflow (`security-scan.yml`) triggers automatically when:

1. **PR Merged to Develop**: When a Pull Request is merged into the `develop` branch
2. **Manual Trigger**: Can be manually triggered via GitHub Actions UI (`workflow_dispatch`)

### Trigger Configuration

```yaml
on:
  pull_request:
    types: [closed]
    branches:
      - develop
  workflow_dispatch:  # Manual trigger
```

## 🔧 Configuration

### Gitleaks Configuration

**File**: `.gitleaks.toml`

The configuration file defines:
- Which patterns to scan for (uses default Gitleaks rules)
- Paths to exclude (build artifacts, dependencies, etc.)
- Custom allowlist patterns

**Customization**: Edit `.gitleaks.toml` to add custom patterns or exclude additional paths.

### OWASP Dependency-Check Configuration

**File**: `pom.xml`

Key configuration options:
- `failBuildOnCVSS`: Build fails if CVSS score ≥ 7.0
- `format`: Report formats (ALL = HTML, JSON, XML)
- `skipProvidedScope`: Skip provided-scope dependencies
- `skipRuntimeScope`: Include runtime dependencies
- `skipTestScope`: Include test dependencies

**Customization**: Modify the plugin configuration in `pom.xml`:

```xml
<plugin>
    <groupId>org.owasp</groupId>
    <artifactId>dependency-check-maven</artifactId>
    <configuration>
        <failBuildOnCVSS>7.0</failBuildOnCVSS>  <!-- Adjust threshold -->
        <format>ALL</format>
    </configuration>
</plugin>
```

### License Maven Plugin Configuration

**File**: `pom.xml`

Key configuration options:
- `strictCheck`: Whether to fail build on license violations
- `failIfMissing`: Whether to fail if license headers are missing
- `includes`: File patterns to check
- `excludes`: File patterns to exclude

**Customization**: Modify the plugin configuration in `pom.xml`.

### SpotBugs Configuration

**File**: `pom.xml`

Key configuration options:
- `effort`: Analysis effort level (Max, Default, Min)
- `threshold`: Minimum bug priority (Low, Medium, High)
- `xmlOutput`: Generate XML report
- `htmlOutput`: Generate HTML report

**Customization**: Modify the plugin configuration in `pom.xml`:

```xml
<plugin>
    <groupId>com.github.spotbugs</groupId>
    <artifactId>spotbugs-maven-plugin</artifactId>
    <configuration>
        <effort>Max</effort>      <!-- Analysis depth -->
        <threshold>Low</threshold> <!-- Minimum priority -->
    </configuration>
</plugin>
```

## 📊 Understanding Scan Results

### Secret Detection Results

**Location**: Workflow artifacts → `gitleaks-report`

**What to look for**:
- Any detected secrets should be immediately rotated
- False positives can be added to `.gitleaks.toml` allowlist
- Review the JSON report for detailed findings

**Action Items**:
1. If secrets are found, rotate them immediately
2. Remove secrets from codebase history (consider BFG Repo-Cleaner)
3. Add false positives to allowlist

### Dependency Vulnerability Results

**Location**: Workflow artifacts → `dependency-check-reports`

**Report Formats**:
- `dependency-check-report.html`: Human-readable HTML report
- `dependency-check-report.json`: Machine-readable JSON report
- `dependency-check-report.xml`: XML report for integration

**Understanding CVSS Scores**:
- **9.0-10.0**: Critical
- **7.0-8.9**: High
- **4.0-6.9**: Medium
- **0.0-3.9**: Low

**Action Items**:
1. Review high/critical vulnerabilities immediately
2. Update dependencies to patched versions
3. If update not available, assess risk and document decision
4. For false positives, create `.dependency-check-suppressions.xml`

### License Compliance Results

**Location**: Workflow artifacts → `license-reports`

**Files**:
- `dependency-tree.txt`: Complete dependency tree
- `dependency-list.txt`: Flat list of dependencies

**Action Items**:
1. Review dependency licenses
2. Ensure licenses are compatible with your project's license
3. Document any license exceptions

### SAST Results

**Location**: Workflow artifacts → `spotbugs-reports`

**Report Formats**:
- `spotbugsXml.xml`: XML report
- `spotbugs.html`: HTML report

**Understanding Findings**:
- **Security Issues**: Marked with "FindSecBugs" tag
- **Priority Levels**: Low, Medium, High
- **Categories**: SQL Injection, XSS, Cryptography, etc.

**Action Items**:
1. Review security findings (FindSecBugs tagged)
2. Fix high-priority security issues
3. Document acceptable risks for low-priority findings

## 🐛 Troubleshooting

### Common Issues

#### 1. Gitleaks Fails with False Positives

**Solution**: Add patterns to `.gitleaks.toml` allowlist:

```toml
[allowlist]
paths = [
    '''path/to/false/positive/.*'''
]
```

#### 2. OWASP Dependency-Check Takes Too Long

**Solution**: The first run downloads vulnerability data (~500MB). Subsequent runs use cached data and are faster.

**Optimization**: The workflow caches OWASP data between runs.

#### 3. License Check Fails

**Solution**: The license check is configured with `strictCheck: false` and `failIfMissing: false`, so it won't fail the build. Review the dependency list to ensure license compliance.

#### 4. SpotBugs Finds Too Many Issues

**Solution**: Adjust the `threshold` in `pom.xml`:

```xml
<configuration>
    <threshold>Medium</threshold>  <!-- Only report Medium+ priority -->
</configuration>
```

#### 5. Workflow Doesn't Trigger

**Check**:
- PR must be merged (not just closed)
- Target branch must be `develop`
- Workflow file must be in `.github/workflows/`

#### 6. Maven Plugin Not Found

**Solution**: Ensure Maven can download plugins. Check:
- Internet connectivity in GitHub Actions
- Maven Central repository accessibility
- Plugin version compatibility

### Getting Help

1. Check workflow logs in GitHub Actions
2. Review scan reports in artifacts
3. Consult tool documentation:
   - [Gitleaks Docs](https://github.com/gitleaks/gitleaks)
   - [OWASP Dependency-Check Docs](https://jeremylong.github.io/DependencyCheck/)
   - [SpotBugs Docs](https://spotbugs.github.io/)
   - [FindSecBugs Docs](https://find-sec-bugs.github.io/)

## ✅ Best Practices

### 1. Regular Review

- Review security scan results regularly
- Address high/critical vulnerabilities promptly
- Keep dependencies up to date

### 2. False Positive Management

- Document why certain findings are acceptable
- Use suppression files for known false positives
- Review suppressions periodically

### 3. Dependency Management

- Keep dependencies minimal
- Use dependency management tools (e.g., Dependabot)
- Review dependency licenses before adding

### 4. Secret Management

- Never commit secrets to version control
- Use environment variables or secret management tools
- Rotate secrets immediately if exposed

### 5. Security Culture

- Treat security as everyone's responsibility
- Review security findings in code reviews
- Document security decisions

## 📚 Additional Resources

- [OWASP Top 10](https://owasp.org/www-project-top-ten/)
- [GitHub Security Best Practices](https://docs.github.com/en/code-security)
- [Maven Security Guide](https://maven.apache.org/guides/mini/guide-security.html)
- [SpotBugs Detectors](https://spotbugs.github.io/bugDescriptions.html)
- [FindSecBugs Detectors](https://find-sec-bugs.github.io/bugs.htm)

## 🔄 Maintenance

### Updating Tools

Tools are configured in `pom.xml` with version properties. To update:

1. Check for new versions on tool websites
2. Update version properties in `pom.xml`
3. Test the workflow
4. Update documentation if needed

### Monitoring

- Monitor workflow execution times
- Review scan result trends
- Track vulnerability remediation progress

---

**Last Updated**: $(date)
**Maintained By**: Development Team

