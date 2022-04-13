package dev.pages.ahsan40.dlf.main;

import java.io.File;

public class TextFile {
    boolean ignoreEmptyLines;
    boolean ignoreWhiteSpace;
    boolean caseSensitive;
    File file;

    public TextFile(boolean ignoreEmptyLines, boolean ignoreWhiteSpace, boolean caseSensitive, File file) {
        this.ignoreEmptyLines = ignoreEmptyLines;
        this.ignoreWhiteSpace = ignoreWhiteSpace;
        this.caseSensitive = caseSensitive;
        this.file = file;
    }
}
