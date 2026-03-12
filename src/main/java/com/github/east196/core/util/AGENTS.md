# core/util MODULE

**所属**: `com.github.east196.core.util` - 共享工具库

## OVERVIEW
17 个工具类，涵盖 ID 生成、日期处理、加密、验证、反射、线程池等。

## WHERE TO LOOK
| 工具 | 文件 | 行数 | 用途 |
|------|------|------|------|
| 雪花 ID | `SnowFlakeUtil.java` | ~150 | 分布式唯一 ID |
| 日期处理 | `CustomDateUtils.java` | 1368 | 复杂日期格式化/解析 |
| 加密 | `EncryptionUtil.java` | 1005 | AES/DES/MD5/SHA |
| 验证器 | `Validators.java` | 663 | 字段校验规则 |
| 日期简化 | `DateUtil.java` | ~100 | 常用日期操作 |
| JSON | `JsonUtil.java` | ~150 | JSON 序列化 |
| 反射 | `Reflections.java` | 310 | 类/字段反射 |
| Map 操作 | `MapUtil.java` | 297 | Map 转换工具 |
| 线程池 | `ThreadPoolUtil.java` | ~80 | 线程池管理 |
| 密码 | `PasswordUtil.java` | ~100 | 密码加密验证 |
| 类型转换 | `Converts.java` | ~100 | 类型转换器 |

## UNIQUE UTILITIES
- **SnowFlakeUtil**: Twitter 雪花算法实现，含 `main()` 测试入口
- **CustomDateUtils**: 1368 行，支持中文日期、季度、周计算
- **EncryptionUtil**: 1005 行，完整加密套件

## NOTES
- 命名规范: `XxxUtil.java` 或 `Xxxs.java` (复数形式)
- 大部分工具类有 `private` 构造函数，静态方法调用
- 依赖 Hutool: `cn.hutool.*` 作为基础工具库