package com.kumori.koumokug;

import java.io.File;

import com.tencent.cos.xml.CosXmlService;
import com.tencent.cos.xml.CosXmlServiceConfig;
import com.tencent.cos.xml.transfer.COSXMLUploadTask;
import com.tencent.cos.xml.transfer.TransferConfig;
import com.tencent.cos.xml.transfer.TransferManager;
import com.tencent.qcloud.core.auth.QCloudCredentialProvider;
import com.tencent.qcloud.core.auth.ShortTimeCredentialProvider;

import android.content.Context;
import android.os.Environment;

public class Cos {

	void cosUpdate(final Context context,String f) {
		
		File file = new File(Environment.getExternalStorageDirectory(), f);
		
		String region = "ap-beijing";

		// 创建 CosXmlServiceConfig 对象，根据需要修改默认的配置参数
		CosXmlServiceConfig serviceConfig = new CosXmlServiceConfig.Builder().setRegion(region).isHttps(true).builder();

		String secretId = "AKIDmLekaBhLuDzZv1oUmpdkeJirkPq3rkDQ"; // 永久密钥 secretId
		String secretKey = "6wXuKTNDn7BDI1Vx7QWLuEI6duwjGWzD"; // 永久密钥 secretKey

		// 初始化 {@link QCloudCredentialProvider} 对象，来给 SDK 提供临时密钥
		// keyDuration 密钥有效期，单位为秒
		
		QCloudCredentialProvider credentialProvider = new ShortTimeCredentialProvider(secretId, secretKey, 300);

		CosXmlService cosXmlService = new CosXmlService(context, serviceConfig, credentialProvider);

		// 初始化 TransferConfig
		TransferConfig transferConfig = new TransferConfig.Builder().build();

		// 初始化 TransferManager
		TransferManager transferManager = new TransferManager(cosXmlService, transferConfig);

		String bucket = "test-1300909478"; // 存储桶，格式：BucketName-APPID
		String cosPath = f; // 对象在存储桶中的位置标识符，即称对象键
		String srcPath = file.toString(); // 本地文件的绝对路径
		String uploadId = null; // 若存在初始化分块上传的 UploadId，则赋值对应的 uploadId 值用于续传；否则，赋值 null
		// 上传对象
		COSXMLUploadTask cosxmlUploadTask = transferManager.upload(bucket, cosPath, srcPath, uploadId);
	}
}