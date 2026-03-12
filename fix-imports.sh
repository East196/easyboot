#!/bin/bash
# 修复 Spring Boot 3 的 import 变更

echo "修复 javax.* -> jakarta.* ..."

# 查找所有 Java 文件
find src -name "*.java" -type f | while read file; do
    # 替换 import 语句
    sed -i \
        -e 's/import javax\.servlet\./import jakarta.servlet./g' \
        -e 's/import javax\.persistence\./import jakarta.persistence./g' \
        -e 's/import javax\.validation\./import jakarta.validation./g' \
        -e 's/import javax\.annotation\./import jakarta.annotation./g' \
        -e 's/import javax\.transaction\./import jakarta.transaction./g' \
        "$file"
done

echo "修复 Swagger 注解 -> SpringDoc ..."

# 替换 Swagger 注解为 SpringDoc
find src -name "*.java" -type f | while read file; do
    sed -i \
        -e 's/import io\.swagger\.annotations\.Api;/import io.swagger.v3.oas.annotations.tags.Tag;/g' \
        -e 's/import io\.swagger\.annotations\.ApiOperation;/import io.swagger.v3.oas.annotations.Operation;/g' \
        -e 's/import io\.swagger\.annotations\.ApiParam;/import io.swagger.v3.oas.annotations.Parameter;/g' \
        -e 's/import io\.swagger\.annotations\.ApiModel;/import io.swagger.v3.oas.annotations.media.Schema;/g' \
        -e 's/import io\.swagger\.annotations\.ApiModelProperty;/import io.swagger.v3.oas.annotations.media.Schema;/g' \
        -e 's/@Api\(/@Tag(name = /g' \
        -e 's/@Api$/\/@Tag(name = "")/g' \
        -e 's/@ApiOperation(/@Operation(summary = /g' \
        -e 's/@ApiParam(/@Parameter(description = /g' \
        -e 's/@ApiModel(/@Schema(description = /g' \
        -e 's/@ApiModelProperty(/@Schema(description = /g' \
        "$file"
done

echo "修复 JTS 包名变更..."

find src -name "*.java" -type f | while read file; do
    sed -i \
        -e 's/import com\.vividsolutions\.jts\./import org.locationtech.jts./g' \
        "$file"
done

echo "修复完成！"
