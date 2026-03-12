# ezsb MODULE

**所属**: `com.github.east196.ezsb` - 主应用模块

## OVERVIEW
Spring Boot 应用核心模块，包含入口点、基础架构组件和数据存储集成。

## STRUCTURE
```
ezsb/
├── core/       # BaseController, BaseService, BaseDao
├── mybatis/    # QueryGenerator (动态查询构造器)
├── dds/        # 动态数据源工具
├── shiro/      # JWT 认证 + 数据权限规则
├── hbase/      # HBase GIS 集成
├── es/         # Elasticsearch 控制器
├── redis/      # Redis 工具类
├── influxdb/   # InfluxDB 时序数据库
├── mvc/        # 文件上传控制器
└── jpa/        # JPA 工具类
```

## WHERE TO LOOK
| 组件 | 文件 | 用途 |
|------|------|------|
| 应用入口 | `Application.java` | `@SpringBootApplication` |
| CRUD 控制器 | `core/BaseController.java` | 泛型 CRUD 端点模板 |
| 服务接口 | `core/BaseService.java` | `@FunctionalInterface` + default 方法 |
| 动态查询 | `mybatis/QueryGenerator.java` | 635 行，高级查询构造 |
| JWT 工具 | `shiro/JwtUtil.java` | Token 生成/验证 |
| 数据源切换 | `dds/DynamicDataSource.java` | 运行时数据源路由 |

## KEY PATTERNS
- **Controller 继承**: `extends BaseController<Entity, ID>` 自动获得 CRUD
- **Service 实现**: `implements BaseService<Entity, ID>` + 重写 `getRepository()`
- **动态查询**: `QueryGenerator.initQueryWrapper(entity, params)` 构造 MyBatis-Plus 查询
- **数据权限**: `SysPermissionDataRule` 注解实现行级权限

## NOTES
- `pom.xml` mainClass 配置错误，实际入口在 `ezsb/Application.java`
- 动态数据源依赖 `dynamic-datasource-spring-boot-starter`
- Shiro 安全配置在 `shiro/` 子包