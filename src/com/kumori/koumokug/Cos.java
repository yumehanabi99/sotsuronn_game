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

		// ���� CosXmlServiceConfig ���󣬸�����Ҫ�޸�Ĭ�ϵ����ò���
		CosXmlServiceConfig serviceConfig = new CosXmlServiceConfig.Builder().setRegion(region).isHttps(true).builder();

		String secretId = "AKIDmLekaBhLuDzZv1oUmpdkeJirkPq3rkDQ"; // ������Կ secretId
		String secretKey = "6wXuKTNDn7BDI1Vx7QWLuEI6duwjGWzD"; // ������Կ secretKey

		// ��ʼ�� {@link QCloudCredentialProvider} �������� SDK �ṩ��ʱ��Կ
		// keyDuration ��Կ��Ч�ڣ���λΪ��
		
		QCloudCredentialProvider credentialProvider = new ShortTimeCredentialProvider(secretId, secretKey, 300);

		CosXmlService cosXmlService = new CosXmlService(context, serviceConfig, credentialProvider);

		// ��ʼ�� TransferConfig
		TransferConfig transferConfig = new TransferConfig.Builder().build();

		// ��ʼ�� TransferManager
		TransferManager transferManager = new TransferManager(cosXmlService, transferConfig);

		String bucket = "test-1300909478"; // �洢Ͱ����ʽ��BucketName-APPID
		String cosPath = f; // �����ڴ洢Ͱ�е�λ�ñ�ʶ�������ƶ����
		String srcPath = file.toString(); // �����ļ��ľ���·��
		String uploadId = null; // �����ڳ�ʼ���ֿ��ϴ��� UploadId����ֵ��Ӧ�� uploadId ֵ�������������򣬸�ֵ null
		// �ϴ�����
		COSXMLUploadTask cosxmlUploadTask = transferManager.upload(bucket, cosPath, srcPath, uploadId);
	}
}