package SQL;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

class SQL {
    String url;
    String name;
    String password;
    public SQL(){
        Properties config = new Properties();
        try {
            config.load(new FileInputStream("src/main/resources/config.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.url = config.getProperty("url");
        this.name = config.getProperty("name");
        this.password = config.getProperty("password");
    }
    public Statement retStatement() throws SQLException {
        return DriverManager.getConnection(url, name, password).createStatement();
    }

    public List<String> select(String req) {
        List<String> return_list = new ArrayList<>();
        try {
            ResultSet set = retStatement().executeQuery(req);

            while (set.next()) {
                for (int i = 1; i <= set.getMetaData().getColumnCount(); i++) {
                    return_list.add(set.getString(i));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return return_list;
    }

    public void insert(String req) {
        try {
            retStatement().executeUpdate(req);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
