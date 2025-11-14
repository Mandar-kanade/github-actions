#!/bin/bash
# Script to calculate next version based on semantic commit messages

set -e

BASE_BRANCH=${1:-main}
HEAD_BRANCH=${2:-develop}

# Get the last release tag
LAST_TAG=$(git describe --tags --abbrev=0 2>/dev/null || echo "")

if [ -z "$LAST_TAG" ]; then
  echo "No previous tag found, starting from v1.0.0"
  LAST_TAG="v1.0.0"
fi

# Remove 'v' prefix if present
LAST_VERSION=${LAST_TAG#v}

# Ensure we're on the head branch
if git show-ref --verify --quiet refs/heads/"$HEAD_BRANCH"; then
  git checkout "$HEAD_BRANCH"
else
  git fetch origin "$HEAD_BRANCH" || true
  git checkout -b "$HEAD_BRANCH" "origin/$HEAD_BRANCH" 2>/dev/null || git checkout "$HEAD_BRANCH"
fi
git pull origin "$HEAD_BRANCH" 2>/dev/null || true

# Get commits between last tag and HEAD (including commit body for BREAKING CHANGE)
if git rev-parse --verify "$LAST_TAG" >/dev/null 2>&1; then
  # Get commit subjects and bodies
  COMMITS=$(git log "${LAST_TAG}..HEAD" --pretty=format:"%s%n%b" 2>/dev/null || echo "")
else
  # If tag doesn't exist in current branch, get recent commits
  COMMITS=$(git log --pretty=format:"%s%n%b" -n 50 2>/dev/null || echo "")
fi

echo "Last version: $LAST_VERSION"
echo "Analyzing commits since $LAST_TAG..."

# Determine version bump type
MAJOR=false
MINOR=false
PATCH=false

if [ -n "$COMMITS" ]; then
  while IFS= read -r line; do
    # Skip empty lines
    [ -z "$line" ] && continue
    
    # Check for breaking changes in subject or body
    if echo "$line" | grep -qiE "(BREAKING CHANGE|!):"; then
      MAJOR=true
      echo "  Found BREAKING CHANGE: $line"
    # Check for BREAKING CHANGE in commit body (common pattern)
    elif echo "$line" | grep -qiE "^BREAKING CHANGE:"; then
      MAJOR=true
      echo "  Found BREAKING CHANGE in body: $line"
    # Check for features
    elif echo "$line" | grep -qiE "^(feat|feature)(\(.+\))?:"; then
      MINOR=true
      echo "  Found feature: $line"
    # Check for fixes
    elif echo "$line" | grep -qiE "^(fix|bugfix)(\(.+\))?:"; then
      PATCH=true
      echo "  Found fix: $line"
    fi
  done <<< "$COMMITS"
fi

# Parse current version
IFS='.' read -r MAJOR_V MINOR_V PATCH_V <<< "$LAST_VERSION"
MAJOR_V=${MAJOR_V:-1}
MINOR_V=${MINOR_V:-0}
PATCH_V=${PATCH_V:-0}

# Calculate next version
if [ "$MAJOR" = true ]; then
  NEW_MAJOR=$((MAJOR_V + 1))
  NEW_VERSION="${NEW_MAJOR}.0.0"
  VERSION_TYPE="major"
elif [ "$MINOR" = true ]; then
  NEW_MINOR=$((MINOR_V + 1))
  NEW_VERSION="${MAJOR_V}.${NEW_MINOR}.0"
  VERSION_TYPE="minor"
elif [ "$PATCH" = true ]; then
  NEW_PATCH=$((PATCH_V + 1))
  NEW_VERSION="${MAJOR_V}.${MINOR_V}.${NEW_PATCH}"
  VERSION_TYPE="patch"
else
  # No version change needed
  NEW_VERSION="${MAJOR_V}.${MINOR_V}.${PATCH_V}"
  VERSION_TYPE="none"
fi

# Validate that new version is greater than previous version
if [ "$VERSION_TYPE" != "none" ]; then
  # Compare versions
  if [ "$MAJOR_V" -lt "${NEW_VERSION%%.*}" ] || \
     ([ "$MAJOR_V" -eq "${NEW_VERSION%%.*}" ] && [ "$MINOR_V" -lt "${NEW_VERSION#*.}" ]) || \
     ([ "$MAJOR_V" -eq "${NEW_VERSION%%.*}" ] && [ "$MINOR_V" -eq "${NEW_VERSION#*.}" ] && [ "$PATCH_V" -lt "${NEW_VERSION##*.}" ]); then
    # Version is greater, which is correct
    :
  else
    echo "⚠️ Warning: Calculated version $NEW_VERSION may not be greater than $LAST_VERSION"
  fi
fi

echo "Next version: $NEW_VERSION ($VERSION_TYPE)"
echo "version=$NEW_VERSION"
echo "type=$VERSION_TYPE"

