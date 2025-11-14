package imdb.config;

import org.hibernate.dialect.pagination.AbstractLimitHandler;
import org.hibernate.query.spi.Limit;

public class SQLiteLimitHandler extends AbstractLimitHandler {

    @Override
    public String processSql(String sql, Limit limit) {
        final boolean hasOffset = limit != null && limit.getFirstRow() != null && limit.getFirstRow() > 0;
        StringBuilder sb = new StringBuilder(sql.length() + 20);
        sb.append(sql);

        if (limit != null && limit.getMaxRows() != null) {
            sb.append(" LIMIT ").append(limit.getMaxRows());
            if (hasOffset) {
                sb.append(" OFFSET ").append(limit.getFirstRow());
            }
        } else if (hasOffset) {
            sb.append(" LIMIT -1 OFFSET ").append(limit.getFirstRow());
        }

        return sb.toString();
    }

    @Override
    public boolean supportsLimit() {
        return true;
    }
}
