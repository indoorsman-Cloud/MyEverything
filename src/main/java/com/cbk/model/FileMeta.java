package com.cbk.model;

import com.cbk.util.OutputUtil;
import com.cbk.util.PinYinUtil;

import java.io.File;
import java.util.Objects;


public class FileMeta {
    private final Integer id;
    private final String name;
    private final String pinyin;
    private final String pinyinFirst;
    private final String path;
    private final boolean directory;
    private final Long size;
    private final Long lastModifiedTimestamp;

    // 提供给扫描后使用
    public FileMeta(File file) {
        this.id = null;
        this.name = file.getName();
        this.pinyin = PinYinUtil.getPinYin(name);
        this.pinyinFirst = PinYinUtil.getPinYinFirst(name);
        this.path = file.getAbsolutePath();
        this.directory = file.isDirectory();
        this.size = file.length();
        this.lastModifiedTimestamp = file.lastModified();
    }

    // 提供给DB查询后使用
    public FileMeta(Integer id, String name, String pinyin, String pinyinFirst, String path, boolean directory, Long length, Long lastModifiedTimestamp) {
        this.id = id;
        this.name = name;
        this.pinyin = pinyin;
        this.pinyinFirst = pinyinFirst;
        this.path = path;
        this.directory = directory;
        this.size = length;
        this.lastModifiedTimestamp = lastModifiedTimestamp;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPinyin() {
        return pinyin;
    }

    public String getPinyinFirst() {
        return pinyinFirst;
    }

    public String getPath() {
        return path;
    }

    public boolean isDirectory() {
        return directory;
    }

    public Long getSize() {
        return size;
    }

    public String getSizeUI() {
        return OutputUtil.formatSize(size);
    }

    public Long getLastModifiedTimestamp() {
        return lastModifiedTimestamp;
    }

    public String getLastModifiedUI() {
        return OutputUtil.formatTimestamp(lastModifiedTimestamp);
    }

    @Override
    public String toString() {
        return "FileMeta{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", pinyin='" + pinyin + '\'' +
                ", pinyinFirst='" + pinyinFirst + '\'' +
                ", path='" + path + '\'' +
                ", directory=" + directory +
                ", length=" + size +
                ", lastModifiedTimestamp=" + lastModifiedTimestamp +
                '}';
    }

    // 只要两个 FileMeta 对象的 path 一样，就是代表同一个文件
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FileMeta fileMeta = (FileMeta) o;
        return path.equals(fileMeta.path);
    }

    @Override
    public int hashCode() {
        return Objects.hash(path);
    }
}
