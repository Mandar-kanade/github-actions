#!/bin/bash
# Script to update version in pom.xml

set -e

VERSION=$1
POM_FILE=${2:-pom.xml}

if [ -z "$VERSION" ]; then
  echo "Usage: $0 <version> [pom-file]"
  exit 1
fi

if [ ! -f "$POM_FILE" ]; then
  echo "Error: $POM_FILE not found"
  exit 1
fi

# Update version in pom.xml (only the project version, not parent or dependencies)
if [[ "$OSTYPE" == "darwin"* ]]; then
  # macOS
  sed -i '' "s|<version>\([^<]*\)</version>|<version>${VERSION}</version>|" "$POM_FILE"
else
  # Linux
  sed -i "s|<version>\([^<]*\)</version>|<version>${VERSION}</version>|" "$POM_FILE"
fi

# Verify the change
if grep -q "<version>${VERSION}</version>" "$POM_FILE"; then
  echo "✅ Successfully updated version to ${VERSION} in ${POM_FILE}"
else
  echo "❌ Failed to update version"
  exit 1
fi

