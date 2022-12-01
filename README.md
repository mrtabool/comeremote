### Database Create/Update/Rollback
1. Create/Update:

```
cd liquibase
mvn liquibase:update
cd ..
```

2. Rollback to `some-tag` tag:

```
cd liquibase
mvn liquibase:rollback -D"liquibase.rollbackTag=some-tag"
cd ..