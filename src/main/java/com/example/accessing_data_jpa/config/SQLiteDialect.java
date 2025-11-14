package com.example.accessing_data_jpa.config;

import org.hibernate.dialect.Dialect;
import org.hibernate.dialect.identity.IdentityColumnSupport;
import org.hibernate.dialect.identity.IdentityColumnSupportImpl;
import org.hibernate.dialect.pagination.LimitHandler;

public class SQLiteDialect extends Dialect {

    private static final LimitHandler LIMIT_HANDLER = new SQLiteLimitHandler(); // <- use your custom handler

    @Override
    public IdentityColumnSupport getIdentityColumnSupport() {
        return new IdentityColumnSupportImpl();
    }

    @Override
    public LimitHandler getLimitHandler() {
        return LIMIT_HANDLER; // now returns your custom handler
    }

    @Override
    public boolean dropConstraints() { return false; }

    @Override
    public boolean hasAlterTable() { return false; }

    @Override
    public boolean qualifyIndexName() { return false; }

    @Override
    public boolean supportsTemporaryTables() { return true; }

    @Override
    public boolean supportsCurrentTimestampSelection() { return true; }

    @Override
    public boolean isCurrentTimestampSelectStringCallable() { return false; }

    @Override
    public String getSelectGUIDString() { return "select hex(randomblob(16))"; }

    @Override
    public boolean supportsUnionAll() { return true; }
}
