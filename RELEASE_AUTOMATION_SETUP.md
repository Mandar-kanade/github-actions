# Release Automation Setup Guide

This guide explains how to set up and use the continuous release automation workflow.

## Overview

The release automation system maintains a persistent pull request from `develop` → `main` that:
- Automatically updates when new commits are added to `develop`
- Automatically rebases to stay current with both branches
- Calculates the next version based on semantic commit messages
- Updates version files automatically
- Requires manual version approval before merging
- Creates releases automatically when merged

## Prerequisites

1. **Branch Structure**: Ensure you have `develop` and `main` branches
2. **Semantic Commits**: Use semantic commit messages (see below)
3. **Branch Protection**: Configure branch protection rules (see below)
4. **GitHub Actions Permissions**: Enable PR creation permissions (see below)

## Semantic Commit Messages

The version calculation is based on semantic commit message conventions:

- **`feat:`** or **`feature:`** → Minor version bump (1.0.0 → 1.1.0)
- **`fix:`** or **`bugfix:`** → Patch version bump (1.0.0 → 1.0.1)
- **`BREAKING CHANGE:`** or **`!:`** → Major version bump (1.0.0 → 2.0.0)

### Examples

```bash
feat: add user authentication
feat(api): implement new endpoint
fix: resolve memory leak
fix(database): correct connection pooling
BREAKING CHANGE: remove deprecated API endpoint
```

## Workflow Files

The system consists of three workflow files:

1. **`.github/workflows/release-automation.yml`**
   - Maintains the persistent release PR
   - Runs on push to `develop` and on schedule (hourly)
   - Calculates version and updates `pom.xml`
   - Creates/updates the release PR

2. **`.github/workflows/release-on-merge.yml`**
   - Triggers when the release PR is merged to `main`
   - Creates git tag
   - Creates GitHub Release with changelog
   - Builds and uploads artifacts
   - Pushes Docker image with release tag

3. **`.github/workflows/version-approval.yml`**
   - Handles version approval via PR comments
   - Updates check status when `/approve-version` is commented

## Setup Instructions

### 1. Initial Setup

The workflows will automatically create the release PR on the first run. To trigger manually:

1. Go to Actions → Release Automation
2. Click "Run workflow"
3. Select `develop` branch
4. Click "Run workflow"

### 2. Enable GitHub Actions PR Permissions

**CRITICAL**: The workflow needs permission to create pull requests:

1. Go to **Settings** → **Actions** → **General**
2. Scroll to **Workflow permissions**
3. Select **Read and write permissions**
4. ✅ Check **"Allow GitHub Actions to create and approve pull requests"**
5. Click **Save**

Without this setting, the workflow will fail with a 403 error when trying to create the release PR.

### 3. Configure Branch Protection Rules

To require version approval before merging:

1. Go to **Settings** → **Branches**
2. Add a rule for `main` branch:
   - ✅ Require a pull request before merging
   - ✅ Require approvals (1)
   - ✅ Require status checks to pass before merging
   - Add check: **"Version Approval Required"**
   - ✅ Require branches to be up to date before merging

### 4. Version Approval Process

When a release PR is created or updated:

1. **Review the version**: Check that the calculated version is correct
2. **Approve the version**: 
   - Option A: Comment `/approve-version` on the PR
   - Option B: Approve the PR through GitHub UI (requires branch protection)
3. **Merge**: Once approved, merge the PR to `main`

### 5. Automatic Release

When the PR is merged:
- ✅ Git tag is created (e.g., `v1.2.3`)
- ✅ GitHub Release is created with changelog
- ✅ JAR artifact is uploaded to the release
- ✅ Docker image is built and pushed with release tag
- ✅ `develop` branch version is bumped to next snapshot

## How It Works

### Version Calculation

The system analyzes commits since the last release tag:

```bash
# Example: Last tag was v1.0.0
# Commits since then:
feat: add new feature      → Minor bump → v1.1.0
fix: bug fix              → Patch bump → v1.1.1
feat: another feature     → Minor bump → v1.2.0
BREAKING CHANGE: ...      → Major bump → v2.0.0
```

The highest priority change determines the version bump.

### PR Maintenance

The release PR (`release/develop-to-main`) is automatically:
- Updated when new commits land in `develop`
- Rebased on `main` to stay current
- Version-bumped based on new commits

### Release Artifacts

When a release is created, the following artifacts are generated:

- **Git Tag**: `v1.2.3` (semantic versioning)
- **GitHub Release**: Includes changelog and download links
- **JAR File**: `product-service-1.2.3.jar`
- **Docker Image**: `ghcr.io/owner/repo:v1.2.3`

## Troubleshooting

### PR Not Updating

If the PR doesn't update automatically:
1. Check workflow runs in Actions
2. Ensure `develop` branch has new commits
3. Manually trigger the workflow if needed

### Version Calculation Incorrect

If the version is wrong:
1. Check commit messages follow semantic conventions
2. Verify the last release tag exists
3. Review the version calculation script output

### Approval Check Not Showing

If the "Version Approval Required" check doesn't appear:
1. Ensure the workflow ran successfully
2. Check that the PR is from `release/develop-to-main` branch
3. Verify branch protection is configured correctly

### Release Not Created

If release isn't created after merge:
1. Check workflow runs in Actions
2. Verify the PR was merged (not closed)
3. Ensure the PR head branch starts with `release/`

## Manual Override

If you need to manually set a version:

1. Edit `pom.xml` directly in the release PR branch
2. Commit the change
3. The PR will update automatically

## Best Practices

1. **Use semantic commits**: Always use `feat:`, `fix:`, or `BREAKING CHANGE:`
2. **Review versions**: Always review the calculated version before approving
3. **Test before release**: Ensure CI checks pass before merging
4. **Monitor releases**: Check that releases are created successfully

## Scripts

The system includes helper scripts:

- `scripts/calculate-version.sh`: Calculates next version from commits
- `scripts/update-pom-version.py`: Updates version in pom.xml
- `scripts/update-version.sh`: General version update utility

## Support

For issues or questions:
1. Check workflow logs in Actions
2. Review this documentation
3. Check GitHub Issues for known problems

