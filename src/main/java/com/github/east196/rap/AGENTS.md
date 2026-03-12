# rap MODULE

**所属**: `com.github.east196.rap` - REST API 组件

## OVERVIEW
RESTful API 功能模块，提供日志、定时任务、短信、IP 查询、验证码等独立服务。

## STRUCTURE
```
rap/
├── log/        # 日志服务 (es/ 正常日志, normal/ ES 日志)
├── job/        # Quartz 定时任务管理
├── sms/        # 短信发送服务
├── ip/         # IP 地理位置查询
├── captcha/    # 图形验证码生成
└── dict/       # 数据字典服务
```

## WHERE TO LOOK
| 组件 | 控制器 | 功能 |
|------|--------|------|
| 定时任务 | `job/QuartzJobController.java` | Quartz 任务 CRUD |
| 短信 | `sms/SmsController.java` | 短信发送接口 |
| IP 查询 | `ip/IpInfoController.java` | IP 地理位置查询 |
| 验证码 | `captcha/CaptchaController.java` | 图形验证码生成 |
| 日志 | `log/LogController.java` | 操作日志查询 |
| 字典 | `dict/DictService.java` | 数据字典服务 |

## KEY PATTERNS
- **独立模块**: 每个子包为独立功能单元，可单独引入
- **Service 接口**: `XxxService.java` + `XxxServiceImpl.java` 模式
- **异常处理**: `CustomExceptionHandler.java` 全局异常处理器
- **常量定义**: `Constant.java` 模块级常量

## NOTES
- 验证码生成使用 `CreateVerifyCode.java` (303 行)
- ES 日志服务依赖 Elasticsearch
- 模块可独立部署为微服务