# SonarCloud Quick Start Guide

## 🚀 3 Steps to Enable SonarCloud

### Step 1: Create SonarCloud Account (5 minutes)

1. **Go to SonarCloud**
   ```
   https://sonarcloud.io
   ```

2. **Sign in with GitHub**
   - Click "Log in"
   - Choose "GitHub"
   - Authorize SonarCloud

3. **Create or Join Organization**
   - Click "+" icon (top right)
   - Select "Create new organization" or join existing
   - **Note your organization key** (e.g., "mycompany" or "myusername")

4. **Import Repository**
   - Click "+" → "Analyze new project"
   - Select your repository
   - Choose "GitHub Actions" as analysis method

---

### Step 2: Generate Token (2 minutes)

1. **In SonarCloud**:
   - Click your profile → "My Account"
   - Go to "Security" tab
   - Click "Generate Token"
   - Name: `GitHub Actions Token`
   - Click "Generate"
   - **COPY THE TOKEN IMMEDIATELY** (shown only once!)

2. **In GitHub**:
   - Go to your repository
   - Settings → Secrets and variables → Actions
   - Click "New repository secret"
   - Name: `SONAR_TOKEN`
   - Value: [paste the token]
   - Click "Add secret"

---

### Step 3: Update Configuration (2 minutes)

**Edit `pom.xml` line 26:**

Replace:
```xml
<sonar.organization>your-sonarcloud-org</sonar.organization>
```

With your actual organization key from Step 1.3:
```xml
<sonar.organization>mycompany</sonar.organization>
```

**Commit and push:**
```bash
git add pom.xml
git commit -m "Configure SonarCloud organization"
git push
```

---

## ✅ Verification

1. **Check Workflow**
   - Go to GitHub → Actions tab
   - Watch the workflow run
   - Verify "🔬 SonarCloud Analysis" job passes

2. **Check SonarCloud Dashboard**
   - Go to https://sonarcloud.io
   - Select your project
   - View analysis results

---

## 🐛 Troubleshooting

### Error: "is not a valid project or module key"
**Solution**: Update `sonar.organization` in `pom.xml` with your actual organization key from SonarCloud.

### Error: "Token authentication failed"
**Solution**: Verify `SONAR_TOKEN` secret is correctly added to GitHub repository secrets.

### Error: "Quality gate failed"
**Normal!** First run establishes baseline. Focus on fixing new issues in PRs, not all existing issues.

---

## 📚 What's Next?

After successful setup:
- Review your code quality metrics in SonarCloud dashboard
- Check PR decoration (bot comments on pull requests)
- Aim for 80%+ code coverage on new code
- Fix critical and security issues first

---

## 🎯 Key Points

**Required Configuration**:
1. ✅ `SONAR_TOKEN` secret in GitHub
2. ✅ `sonar.organization` in `pom.xml` (line 26)

**What You Get**:
- Automated code quality analysis
- Security vulnerability detection
- Code coverage tracking
- Pull request decoration
- Quality gate enforcement

---

## 📞 Need Help?

- **SonarCloud Docs**: https://docs.sonarcloud.io
- **Community Forum**: https://community.sonarsource.com
- **Check workflow logs** in GitHub Actions tab for specific errors

---

**Total Setup Time**: ~10 minutes  
**Value**: Continuous code quality improvement 🚀

