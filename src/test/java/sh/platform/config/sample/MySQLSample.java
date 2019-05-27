package sh.platform.config;

import javax.sql.DataSource;

public class MySQLTemplate {

    public static void main(String[] args) {
        Config config = new Config();
        MySQL mySQL = config.getCredential("database", MySQL::new);
        DataSource dataSource = mySQL.get();

    }
}
