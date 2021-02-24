package com.cbk.dao;

import com.cbk.model.FileMeta;
import com.cbk.util.DBUtil;
import com.cbk.util.LogUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/*
厂家 —— 类（你的产品）

产品的作用：用户填写关键字，进行文件查询
产品的买家: ui.Controller
功能：关键字（1. 文件名中的一部分   2. 拼音中的一部分   3. 拼音的首字母）

最后去想: How
 */
public class QueryDAO {
    public List<FileMeta> query(String keyword) {
        try {
            String sql = "SELECT id, name, pinyin, pinyin_first, path, is_directory, size, last_modified FROM file_meta WHERE name LIKE ? OR pinyin LIKE ? OR pinyin_first LIKE ?";

            Connection c = DBUtil.getConnection();

            try (PreparedStatement s = c.prepareStatement(sql)) {
                s.setString(1, "%" + keyword + "%");    // 模糊查找的 %
                s.setString(2, "%" + keyword + "%");
                s.setString(3, "%" + keyword + "%");

                LogUtil.log("执行 SQL: %s, %s", sql, keyword);

                List<FileMeta> result = new ArrayList<>();
                try (ResultSet resultSet = s.executeQuery()) {
                    while (resultSet.next()) {
                        int id = resultSet.getInt("id");
                        String name = resultSet.getString("name");
                        String pinyin = resultSet.getString("pinyin");
                        String pinyinFirst = resultSet.getString("pinyin_first");
                        String path = resultSet.getString("path");
                        boolean directory = resultSet.getBoolean("is_directory");
                        long length = resultSet.getLong("size");
                        long lastModified = resultSet.getLong("last_modified");

                        FileMeta fileMeta = new FileMeta(id, name, pinyin, pinyinFirst, path, directory, length, lastModified);
                        result.add(fileMeta);
                    }

                    LogUtil.log("一共查询出 %d 个文件", result.size());

                    return result;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<FileMeta> queryByPath(String searchPath) {
        try {
            String sql = "SELECT id, name, pinyin, pinyin_first, path, is_directory, size, last_modified FROM file_meta WHERE path LIKE ?";

            Connection c = DBUtil.getConnection();

            try (PreparedStatement s = c.prepareStatement(sql)) {
                s.setString(1, searchPath + "%");

                LogUtil.log("执行 SQL: %s, %s", sql, searchPath);

                List<FileMeta> result = new ArrayList<>();
                try (ResultSet resultSet = s.executeQuery()) {
                    while (resultSet.next()) {
                        int id = resultSet.getInt("id");
                        String name = resultSet.getString("name");
                        String pinyin = resultSet.getString("pinyin");
                        String pinyinFirst = resultSet.getString("pinyin_first");
                        String path = resultSet.getString("path");
                        boolean directory = resultSet.getBoolean("is_directory");
                        long length = resultSet.getLong("size");
                        long lastModified = resultSet.getLong("last_modified");

                        FileMeta fileMeta = new FileMeta(id, name, pinyin, pinyinFirst, path, directory, length, lastModified);
                        result.add(fileMeta);
                    }

                    LogUtil.log("一共查询出 %d 个文件", result.size());

                    return result;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        QueryDAO queryDAO = new QueryDAO();
        List<FileMeta> fileList = queryDAO.query("banshu");
        for (FileMeta file : fileList) {
            System.out.println(file);
        }
    }
}
