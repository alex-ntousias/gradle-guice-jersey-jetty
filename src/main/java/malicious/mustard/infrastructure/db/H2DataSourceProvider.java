package malicious.mustard.infrastructure.db;

import malicious.mustard.config.DbConfig;
import org.h2.jdbcx.JdbcConnectionPool;
import org.skife.jdbi.v2.DBI;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;
import javax.sql.DataSource;

@Singleton
public class H2DataSourceProvider implements Provider<DBI> {

    private final DBI dbi;

    @Inject
    public H2DataSourceProvider(DbConfig dbConfig) {
        final DataSource ds = JdbcConnectionPool
                .create(dbConfig.getDataSourceClass(), dbConfig.getUser(), dbConfig.getPassword());
        dbi = new DBI(ds);
        dbi.open();
    }

    @Override
    public DBI get() {
        return dbi;
    }
}
