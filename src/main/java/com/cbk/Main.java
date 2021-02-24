package com.cbk;

import com.cbk.service.DBService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        URL url = AppMain.class.getClassLoader().getResource("app.fxml");

        if (url == null) {
            throw new RuntimeException("app.fxml 文件没有找到");
        }

        Parent root = FXMLLoader.load(url);
        Scene scene = new Scene(root);

        stage.setTitle("本地文件搜索工具");
        stage.setWidth(1000);
        stage.setHeight(1000);
        stage.setScene(scene);
        stage.show();

        // 数据库初始化
        new DBService().init();
    }
}