package com.comba.license;

//license容量校验结果枚举

public enum LicenseStatus {
	// .lic有效，解析成功。
	VALIDITY_LICENSE,
	// 不是.lic文件
	NON_LICENSE,
	// .lic文件无效，文中格式内容与详设定义中的不同
	INVALID_LICENSE,
	// .lic文件中的SN与当前主机的SN不一致,不合法的SN
	ILLEGAL_SN,
	// 公钥文件无效
	INVALID_PUBLICKEY,
	// 没有公钥文件
	NOT_PUBLICKEY,
	// 没有.lic文件
	NOT_LICENSE,
	// 未过期
	NOTEXPIRE,
	// 即将过期
	SOONEXPIRE,
	// 已过期
	EXPIRED;
}
