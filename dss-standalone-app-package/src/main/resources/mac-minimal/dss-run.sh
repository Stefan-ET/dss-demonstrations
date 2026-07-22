#!/bin/bash
DIR="$(cd "$(dirname "$0")" && pwd)"

# macOS has no system package manager equivalent of "apt install openjfx".
# You must download the JavaFX SDK for macOS (aarch64 for Apple Silicon, x64
# for Intel) from https://gluonhq.com/products/javafx/ and either:
#   a) export JAVAFX_HOME=/path/to/javafx-sdk-XX.X.X   before running this script, or
#   b) place the extracted SDK at ~/javafx-sdk (default fallback below)

if [ -z "$JAVAFX_HOME" ]; then
  if [ -d "$HOME/javafx-sdk/lib" ]; then
    JAVAFX_HOME="$HOME/javafx-sdk"
  else
    echo "JavaFX SDK not found."
    echo "Download it from https://gluonhq.com/products/javafx/ (macOS, aarch64 or x64),"
    echo "then either export JAVAFX_HOME=/path/to/javafx-sdk-XX.X.X or extract it to $HOME/javafx-sdk"
    exit 1
  fi
fi

if [ ! -d "$JAVAFX_HOME/lib" ]; then
  echo "JAVAFX_HOME is set to '$JAVAFX_HOME' but '$JAVAFX_HOME/lib' does not exist."
  exit 1
fi

java --module-path "$JAVAFX_HOME/lib" --add-modules javafx.fxml,javafx.controls -jar "$DIR/dss-app.jar"
