# SSO 单点登录服务

## 项目介绍

SSO（Single Sign-On）单点登录服务是一个基于JWT（JSON Web Token）的认证授权系统，允许用户通过单次登录访问多个相关但独立的系统，无需重复登录。本项目提供了JWT令牌的生成、验证和刷新功能，以及用户登录和令牌验证的API接口。

## 功能特性

- **单点登录**：用户只需登录一次即可访问所有授权的系统
- **基于JWT的无状态认证**：使用JWT进行身份验证，无需在服务端存储会话信息
- **令牌管理**：支持令牌的生成、验证和刷新
- **安全性**：使用密钥签名，确保令牌的安全性和完整性
- **可配置性**：通过配置文件灵活设置JWT参数
- **RESTful API**：提供标准的RESTful API接口

## 技术栈

- **Java 8**：核心编程语言
- **Spring Boot 2.3.12**：应用程序框架
- **JWT (JJWT 0.9.1)**：用于生成和验证JSON Web Token
- **Lombok**：减少样板代码
- **Maven**：项目管理和构建工具

## 项目结构

```
sso/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── gaoyifeng/
│   │   │           └── sso/
│   │   │               ├── config/
│   │   │               │   └── JwtConfig.java      # JWT配置类
│   │   │               ├── controller/
│   │   │               │   └── SsoController.java  # SSO控制器
│   │   │               ├── util/
│   │   │               │   └── JwtUtil.java        # JWT工具类
│   │   │               └── SsoApplication.java     # 应用程序入口
│   │   └── resources/
│   │       └── application.yml                     # 应用配置文件
│   └── test/
│       └── java/
└── pom.xml                                         # Maven配置文件
```

## 快速开始

### 前置条件

- JDK 1.8 或更高版本
- Maven 3.6 或更高版本

### 构建与运行

1. 克隆项目到本地

```bash
git clone <repository-url>
cd sso
```

2. 构建项目

```bash
mvn clean package
```

3. 运行应用

```bash
java -jar target/sso-0.0.3-SNAPSHOT.jar
```

或者使用Maven运行：

```bash
mvn spring-boot:run
```

应用将在 `http://localhost:9000` 上启动。

## API文档

### 登录接口

- **URL**: `/sso/login`
- **方法**: POST
- **描述**: 用户登录并获取JWT令牌
- **请求参数**: 用户凭证（待实现）
- **响应**: JWT令牌

### 验证接口

- **URL**: `/sso/verify`
- **方法**: POST
- **描述**: 验证JWT令牌的有效性
- **请求参数**: JWT令牌
- **响应**: 验证结果

## 配置说明

JWT相关配置在 `application.yml` 文件中：

```yaml
jwt:
  # JWT加密密钥
  secret: gaoyifeng
  # 令牌有效期（秒）
  expiration: 3600
  # 令牌签发者
  issuer: gaoyifeng
  # 令牌前缀
  tokenPrefix: "Bearer "
  # 存放令牌的请求头
  header: Authorization
```

## 依赖关系

本项目依赖于以下主要组件：

- Spring Boot Web：提供Web应用程序功能
- JJWT：用于JWT的生成和验证
- Lombok：简化Java代码
- Common模块：提供通用功能（自定义模块）

## 版本信息

- **当前版本**: 0.0.3-SNAPSHOT
- **发布日期**: 待定

## 安全说明

- JWT密钥应在生产环境中妥善保管，建议使用环境变量或配置服务器存储
- 令牌有效期应根据安全需求适当设置
- 生产环境应启用HTTPS以保护令牌传输

## 贡献指南

欢迎贡献代码或提出建议。请遵循以下步骤：

1. Fork项目
2. 创建特性分支 (`git checkout -b feature/amazing-feature`)
3. 提交更改 (`git commit -m 'Add some amazing feature'`)
4. 推送到分支 (`git push origin feature/amazing-feature`)
5. 创建Pull Request
