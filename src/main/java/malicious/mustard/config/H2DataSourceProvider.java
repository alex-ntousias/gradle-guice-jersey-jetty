package malicious.mustard.config;

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
    public H2DataSourceProvider(Config config) {
        final DataSource ds = JdbcConnectionPool
                .create(config.getDataSourceClass(), config.getUser(), config.getPassword());
        dbi = new DBI(ds);
        dbi.open();
    }

    @Override
    public DBI get() {
        return dbi;
    }
}
