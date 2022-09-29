package dev.pages.ahsan40.dlf.main;

import java.io.File;

public class TextFile {
    private boolean ignoreEmptyLines;
    private boolean ignoreWhiteSpace;
    private boolean caseSensitive;
    private File file;

    public TextFile(boolean ignoreEmptyLines, boolean ignoreWhiteSpace, boolean caseSensitive, File file) {
        this.ignoreEmptyLines = ignoreEmptyLines;
        this.ignoreWhiteSpace = ignoreWhiteSpace;
        this.caseSensitive = caseSensitive;
        this.file = file;
    }

    public boolean isIgnoreEmptyLines() {
        return ignoreEmptyLines;
    }

    public void setIgnoreEmptyLines(boolean ignoreEmptyLines) {
        this.ignoreEmptyLines = ignoreEmptyLines;
    }

    public boolean isIgnoreWhiteSpace() {
        return ignoreWhiteSpace;
    }

    public void setIgnoreWhiteSpace(boolean ignoreWhiteSpace) {
        this.ignoreWhiteSpace = ignoreWhiteSpace;
    }

    public boolean isCaseSensitive() {
        return caseSensitive;
    }

    public void setCaseSensitive(boolean caseSensitive) {
        this.caseSensitive = caseSensitive;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
}
