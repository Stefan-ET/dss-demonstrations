#!/bin/bash
DIR="$(cd "$(dirname "$0")" && pwd)"

# macOS Gatekeeper note: if this is the first run of an unsigned/ad-hoc build,
# the bundled java binary may be blocked. If you see "cannot be opened because
# the developer cannot be verified", run once:
#   xattr -cr "$DIR"
# then re-run this script.

"$DIR/java/bin/java" \
  --module-path "$DIR/fx-sdk/lib" \
  --add-modules=javafx.fxml,javafx.controls \
  -jar "$DIR/dss-app.jar"
