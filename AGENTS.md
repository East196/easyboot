# PROJECT KNOWLEDGE BASE

**Generated:** 2026-03-13
**Commit:** 8fcd2f4
**Branch:** upgrade/springboot3

## OVERVIEW
Spring Boot 3.2 快速开发框架，集成 MyBatis-Plus、动态数据源、Shiro 安全、Redis、HBase、Elasticsearch、InfluxDB 等企业级组件。

## STRUCTURE
```
src/main/java/com/github/east196/
├── core/       # 共享工具库：util/api/vo/json/poi/tpl/xml
├── ezsb/       # 主应用模块：入口点 + 业务基础组件
└── rap/        # REST API 组件：log/job/sms/ip/captcha

src/main/java/org/
├── springframework/data/hadoop/hbase/  # 嵌入的 HBase 操作类
└── vertx/java/core/json/               # 嵌入的 Vert.x JSON
```

## WHERE TO LOOK
| Task | Location | Notes |
|------|----------|-------|
| 应用入口 | `ezsb/Application.java` | `@SpringBootApplication` |
| CRUD 基类 | `ezsb/core/BaseController.java` | 泛型 CRUD 模板 |
| 服务基类 | `ezsb/core/BaseService.java` | `@FunctionalInterface` + default 方法 |
| API 响应 | `core/api/Result.java` | 统一返回格式 |
| 分页参数 | `core/vo/PageVo.java` | pageNumber/pageSize/sort/order |
| 动态查询 | `ezsb/mybatis/QueryGenerator.java` | 635 行，高级查询构造器 |
| ID 生成 | `core/util/SnowFlakeUtil.java` | 雪花算法 |
| 日期处理 | `core/util/CustomDateUtils.java` | 1368 行，复杂日期工具 |
| 加密工具 | `core/util/EncryptionUtil.java` | 1005 行 |

## CONVENTIONS
- **包结构**: 三层模块 (core/ezsb/rap) 共享命名空间 `com.github.east196`
- **Controller**: 继承 `BaseController<E, ID>` 获得标准 CRUD
- **Service**: 实现 `BaseService<E, ID>` 接口
- **DAO**: 继承 `BaseDao<E, ID>` (JPA) 或使用 MyBatis-Plus
- **响应格式**: 统一使用 `Result<T>` 包装，通过 `ResultUtil` 构造
- **OpenAPI**: 使用 `springdoc-openapi` (替代 Swagger)

## ANTI-PATTERNS (THIS PROJECT)
- **pom.xml mainClass 配置错误**: 指向 `com.github.east196.easyboot.Easy` 但实际入口是 `com.github.east196.ezsb.Application`
- **无测试代码**: `src/test/` 目录为空，需补充测试

## UNIQUE STYLES
- **嵌入第三方代码**: `org/springframework/data/hadoop/hbase/` 和 `org/vertx/java/core/json/` 直接嵌入项目源码
- **双语注释**: 代码注释中英文混用
- **Hutool 优先**: 大量使用 `cn.hutool.*` 工具库

## COMMANDS
```bash
# 构建
mvn clean package -DskipTests

# 运行
mvn spring-boot:run
# 或
java -jar target/easyboot.jar

# 端口
# 应用: 8888 (application.properties)
# Actuator: 9999
```

## NOTES
- Java 17 + Spring Boot 3.2.0
- 数据库默认 H2 (runtime scope)，生产需切换
- Actuator 暴露所有端点 (`management.endpoints.web.exposure.include=*`)
- 动态数据源支持: `dynamic-datasource-spring-boot-starter`