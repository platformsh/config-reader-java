package sh.platform.config.sample;

import sh.platform.config.Config;
import sh.platform.config.MySQL;

import javax.sql.DataSource;

public class MySQLSample {

    public static void main(String[] args) {
        Config config = new Config();
        MySQL mySQL = config.getCredential("database", MySQL::new);
        DataSource dataSource = mySQL.get();

    }
}
