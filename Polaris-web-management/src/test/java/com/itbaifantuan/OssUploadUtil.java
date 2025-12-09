package com.itbaifantuan;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.common.auth.CredentialsProviderFactory;
import com.aliyun.oss.common.auth.EnvironmentVariableCredentialsProvider;

import java.io.File;
import java.util.UUID;

public class OssUploadUtil {

    public static void main(String[] args) {
        // 1. 配置你的 OSS 信息
        // Endpoint以广州为例，其它Region请按实际情况填写。
        String endpoint = "https://oss-cn-guangzhou.aliyuncs.com";
        // 填写你已经存在的 Bucket 名称
        String bucketName = "baifantuan-java-ai";
        // 你要上传的本地文件的完整路径
        String localFilePath = "C:\\Users\\31755\\OneDrive\\Desktop\\头像背景图片\\微信图片_20241017123129.jpg";

        // 从环境变量中获取访问凭证。
        // 确保你已经配置了 OSS_ACCESS_KEY_ID 和 OSS_ACCESS_KEY_SECRET 环境变量。
        EnvironmentVariableCredentialsProvider credentialsProvider;
        try {
            credentialsProvider = CredentialsProviderFactory.newEnvironmentVariableCredentialsProvider();
        } catch (com.aliyuncs.exceptions.ClientException e) {
            System.out.println("获取环境变量中的访问凭证失败！请检查是否配置了 OSS_ACCESS_KEY_ID 和 OSS_ACCESS_KEY_SECRET。");
            System.out.println("错误信息: " + e.getMessage());
            return;
        }

        // 创建 OSSClient 实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, credentialsProvider);

        try {
            // 2. 准备上传
            File localFile = new File(localFilePath);
            if (!localFile.exists()) {
                System.out.println("本地文件不存在: " + localFilePath);
                return;
            }

            // 获取文件扩展名
            String originalFilename = localFile.getName();
            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));

            // 创建一个新的、唯一的文件名，以避免在 Bucket 中文件名冲突
            String objectName = UUID.randomUUID().toString() + extension;
            System.out.println("准备上传文件: " + originalFilename + " 到 Bucket: " + bucketName + "，新名称为: " + objectName);

            // 3. 执行上传
            ossClient.putObject(bucketName, objectName, localFile);

            System.out.println("文件上传成功！");
            // 你可以在这里拼接并打印出文件的访问 URL
            // URL 格式: https://<BucketName>.<Endpoint>/<ObjectName>
            String fileUrl = "https://" + bucketName + ".oss-cn-guangzhou.aliyuncs.com/" + objectName;
            System.out.println("文件访问 URL: " + fileUrl);

        } catch (OSSException oe) {
            System.out.println("上传失败，OSS 服务端返回错误。");
            System.out.println("Error Message:" + oe.getErrorMessage());
            System.out.println("Error Code:" + oe.getErrorCode());
            System.out.println("Request ID:" + oe.getRequestId());
            System.out.println("Host ID:" + oe.getHostId());
        } catch (ClientException ce) {
            System.out.println("上传失败，客户端遇到问题，例如网络问题。");
            System.out.println("Error Message:" + ce.getMessage());
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
    }
}