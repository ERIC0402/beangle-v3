package org.beangle.database;

import org.apache.commons.lang3.StringUtils;
import org.beangle.security.codec.DESDecrypt;
import org.beangle.security.codec.DESEncrypt;
import org.beangle.security.codec.DESFactory;
import org.beangle.security.codec.EncryptUtil;

public class CryptDataSource extends org.beangle.database.ComboPooledDataSource {
	private final String publicKey = "6B6BDF4328D069FFD14E8AA8EF0244FA";
	private DESDecrypt desDecrypt;
	private DESEncrypt desEncrypt;
	private String hexstr;

	public CryptDataSource() {

	}

	/**
	 * 加密
	 * 
	 * @param key
	 * @param jdbcUrl
	 * @param user
	 * @param password
	 */
	public CryptDataSource(String key, String jdbcUrl, String user, String password) {
		StringBuilder sb = new StringBuilder("加密内容：\n");
		sb.append("key:").append(key).append("\n");
		key = EncryptUtil.encode(key).toUpperCase();
		sb.append("key:").append(key).append("\n");
		key = EncryptUtil.encode(publicKey + key).toUpperCase();
		desEncrypt = new DESEncrypt(key.getBytes());
		jdbcUrl = encrypt(jdbcUrl);
		user = encrypt(user);
		password = encrypt(password);
		sb.append("jdbcUrl:").append(jdbcUrl).append("\n");
		sb.append("user:").append(user).append("\n");
		sb.append("password:").append(password);
		hexstr = sb.toString();
	}
	
	public String decrypt(String hexstr) {
		if(desDecrypt == null){
			return hexstr;
		}
		String str = null;
		try {
			str = new String(desDecrypt.doDecrypt(DESFactory.fromHexString(hexstr)));
		} catch (Exception e) {
		}
		return str;
	}

	public String encrypt(String str) {
		String hexstr = null;
		try {
			hexstr = DESFactory.toHexString(desEncrypt.doEncrypt(str.getBytes()));
		} catch (Exception e) {
		}
		return hexstr;
	}

	public String getHexstr() {
		return hexstr;
	}
	
	public void setKey(String key) {
		if(StringUtils.isNotBlank(key)){
			key = EncryptUtil.encode(publicKey + key).toUpperCase();
			desDecrypt = new DESDecrypt(key.getBytes());
		}
	}
	
	@Override
	public void setJdbcUrl(String jdbcUrl) {
		if(jdbcUrl.indexOf(":") < 0){
			jdbcUrl = decrypt(jdbcUrl);
		}
		super.setJdbcUrl(jdbcUrl);
	}
	
	@Override
	public void setUser(String user) {
		user = decrypt(user);
		super.setUser(user);
	}
	
	@Override
	public void setPassword(String password) {
		password = decrypt(password);
		super.setPassword(password);
	}
	
	public void init(){
		
	}

	public static void main(String[] args) {
		CryptDataSource cds = null;
		cds = new CryptDataSource("usst-fwpt", "jdbc:oracle:thin:@192.168.1.220:1521:orcl", "U_JWCUSER", "123456");
		System.out.println(cds.getHexstr());
		cds = new CryptDataSource("usst-fwpt", "jdbc:oracle:thin:@192.168.1.220:1521:orcl", "U_FWPT", "123456");
		System.out.println(cds.getHexstr());
		cds = new CryptDataSource("usst-fwpt", "jdbc:oracle:thin:@192.168.88.128:1521:orcl", "U_JWCUSER", "123456");
		System.out.println(cds.getHexstr());
	}
}
