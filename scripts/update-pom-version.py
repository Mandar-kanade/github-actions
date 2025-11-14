#!/usr/bin/env python3
"""
Update version in pom.xml file.
Only updates the project version, not parent or dependency versions.
"""
import sys
import re

def update_version(pom_file, new_version):
    """Update version in pom.xml"""
    try:
        # Read the file
        with open(pom_file, 'r', encoding='utf-8') as f:
            content = f.read()
        
        # Use regex to find and replace the project version
        # Match <version>X.Y.Z-SNAPSHOT</version> that comes after <artifactId>product-service</artifactId>
        # This ensures we only update the project version, not parent or dependency versions
        pattern = r'(<artifactId>product-service</artifactId>\s*<version>)([^<]+)(</version>)'
        
        def replace_version(match):
            return match.group(1) + new_version + match.group(3)
        
        updated_content = re.sub(pattern, replace_version, content)
        
        if updated_content == content:
            # Try simpler pattern - just the first version tag after artifactId
            pattern = r'(<artifactId>product-service</artifactId>.*?<version>)([^<]+)(</version>)'
            updated_content = re.sub(pattern, replace_version, content, flags=re.DOTALL)
        
        if updated_content == content:
            print("❌ Could not find version to update in pom.xml")
            return False
        
        # Write back to file
        with open(pom_file, 'w', encoding='utf-8') as f:
            f.write(updated_content)
        
        print(f"✅ Updated version to {new_version}")
        return True
        
    except Exception as e:
        print(f"❌ Error: {e}")
        return False

if __name__ == '__main__':
    if len(sys.argv) != 3:
        print("Usage: update-pom-version.py <pom-file> <new-version>")
        sys.exit(1)
    
    pom_file = sys.argv[1]
    new_version = sys.argv[2]
    
    if update_version(pom_file, new_version):
        sys.exit(0)
    else:
        sys.exit(1)

