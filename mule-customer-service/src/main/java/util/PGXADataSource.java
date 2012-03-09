package util;

import java.sql.SQLException;

import javax.sql.DataSource;

/**
 * Workaround since PGXADataSource does not formally implement javax.sql.Datasource (which is required by Mule...)
 * @author mattias
 *
 */
public class PGXADataSource extends org.postgresql.xa.PGXADataSource implements DataSource {

	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		return false;
	}

	@Override
	public <T> T unwrap(Class<T> iface) throws SQLException {
		return null;
	}

}
