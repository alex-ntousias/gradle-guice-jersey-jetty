package malicious.mustard.modules;

import com.google.inject.AbstractModule;
import malicious.mustard.config.H2DataSourceProvider;
import org.skife.jdbi.v2.DBI;

public class DbModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(DBI.class).toProvider(H2DataSourceProvider.class);
    }
}
