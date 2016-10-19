package malicious.mustard.infrastructure.db;

import com.google.inject.AbstractModule;
import org.skife.jdbi.v2.DBI;

public class DbModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(DBI.class).toProvider(H2DataSourceProvider.class);
    }
}
