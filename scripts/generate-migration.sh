#!/usr/bin/env sh

set -e

if [ "$#" -ne 1 ]; then
  echo "Usage: $0 <migration_name>"
  exit 1
fi

OUTPUT="$(dirname "$(dirname "$0")")/src/main/resources/db/migration/V$(date +%Y%m%d%H%M%S)__$1.sql"

touch $OUTPUT
echo "Migration created at $OUTPUT"
