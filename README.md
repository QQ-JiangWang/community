## 旺仔笔记

##脚本
```sql
CREATE CACHED TABLE PUBLIC.USER(
    ID INTEGER DEFAULT (NEXT VALUE FOR PUBLIC.SYSTEM_SEQUENCE_6A15D33D_85B1_4772_8B50_EF82BD8A4090) NOT NULL NULL_TO_DEFAULT SEQUENCE PUBLIC.SYSTEM_SEQUENCE_6A15D33D_85B1_4772_8B50_EF82BD8A4090,
    ACCOUNT_ID VARCHAR(100),
    NAME VARCHAR(50),
    TOKEN CHAR(36),
    GMT_CREATE BIGINT,
    GMT_MODIFIED BIGINT
)


```