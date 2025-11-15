# 🔑 NVD API Key Setup Guide

OWASP Dependency-Check requires access to the National Vulnerability Database (NVD) to scan for vulnerabilities. Starting in 2023, NVD requires an API key for reliable access.

## 🎯 Why You Need an NVD API Key

- **Required for Updates**: Without an API key, you'll get 403/404 errors when trying to update vulnerability data
- **Better Performance**: API key provides faster and more reliable access
- **Free**: The API key is completely free, just requires registration
- **Rate Limits**: Without a key, you're subject to strict rate limits that can cause failures

## 📝 Step-by-Step Setup

### Step 1: Get Your Free API Key

1. Visit: https://nvd.nist.gov/developers/request-an-api-key
2. Fill out the registration form:
   - **First Name**: Your first name
   - **Last Name**: Your last name
   - **Email**: Your email address
   - **Organization**: Your organization name (or "Personal" for personal projects)
   - **Reason**: Select "General Use" or describe your use case
3. Accept the terms and conditions
4. Submit the form
5. **Check your email** for the API key (usually arrives within minutes)

### Step 2: Add API Key to GitHub Secrets

1. Go to your GitHub repository
2. Navigate to: **Settings** → **Secrets and variables** → **Actions**
3. Click **New repository secret**
4. Name: `NVD_API_KEY`
5. Value: Paste your API key from the email
6. Click **Add secret**

### Step 3: Verify Configuration

The workflow is already configured to use the secret automatically. When you run the security scan:

- ✅ If `NVD_API_KEY` secret exists: Uses API key for fast updates
- ⚠️ If no secret: Uses cached data (if available) or fails gracefully

## 🔍 Testing Locally

### Option 1: Use API Key (Recommended)

1. Get your API key from NVD (see Step 1 above)
2. Set it as an environment variable:
   ```bash
   export NVD_API_KEY="your-api-key-here"
   ```
3. Run dependency check:
   ```bash
   mvn org.owasp:dependency-check-maven:check -Dnvd.api.key=$NVD_API_KEY
   ```

### Option 2: Use Cached Data (Limited)

If you've run dependency-check before with an API key, you can use cached data:

```bash
mvn org.owasp:dependency-check-maven:check -DautoUpdate=false
```

**Note**: This only works if you have cached data from a previous run.

## 🐛 Troubleshooting

### Error: "NVD returned a 403 or 404 error"

**Cause**: No API key or invalid API key

**Solution**:
1. Verify API key is correct
2. Check if API key is set in GitHub secrets
3. For local testing, ensure environment variable is set

### Error: "No documents exist"

**Cause**: No cached data and no API key to download data

**Solution**:
1. Get an NVD API key (free)
2. Add it to GitHub secrets
3. Or wait for cache to be populated

### Error: Rate limit exceeded

**Cause**: Too many requests without API key

**Solution**:
1. Get an NVD API key (increases rate limits)
2. The workflow already includes `nvdApiDelay` configuration to respect rate limits

## 📊 API Key Benefits

| Feature | Without API Key | With API Key |
|---------|----------------|--------------|
| Update Speed | Slow (rate limited) | Fast |
| Reliability | May fail (403/404) | Reliable |
| Rate Limits | Strict (5 requests/min) | Higher limits |
| First Run | May fail | Works immediately |

## 🔒 Security Notes

- **API Key is Free**: No cost associated
- **Public Repositories**: Safe to use in public repos (key is stored as secret)
- **Rate Limits**: API key increases rate limits but still has limits
- **No Personal Data**: API key doesn't expose personal information

## 📚 Additional Resources

- [NVD API Documentation](https://nvd.nist.gov/developers/vulnerabilities)
- [OWASP Dependency-Check NVD API Key Guide](https://github.com/jeremylong/DependencyCheck?tab=readme-ov-file#nvd-api-key-highly-recommended)
- [Request NVD API Key](https://nvd.nist.gov/developers/request-an-api-key)

## ✅ Quick Checklist

- [ ] Requested NVD API key from https://nvd.nist.gov/developers/request-an-api-key
- [ ] Received API key via email
- [ ] Added `NVD_API_KEY` secret to GitHub repository
- [ ] Verified workflow runs successfully
- [ ] (Optional) Tested locally with API key

---

**Note**: The workflow is configured to work with or without an API key, but **an API key is highly recommended** for reliable operation.

