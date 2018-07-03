package com.fanzhuo.framework.license;

import com.fanzhuo.framework.util.DateUtil;
import com.fanzhuo.framework.util.NetworkUtil;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;
import java.util.Properties;

/**
 * 2018-07-03.
 */
public class License {
    private static final Logger log = LoggerFactory.getLogger(License.class);

    private String type;// Server, Client
    private String dateExpired;
    private String[] mac;
    private String[] ip;

    private String user;// Developer
    private String address;
    private String contact;
    private String tel;
    private String zip;// 邮编

    private String sign;

    private boolean valid = false;
//	private boolean verified = false;

    public License() {

    }

    /**
     * 读取许可文件
     *
     * @return
     */
    public boolean readFile() {
        InputStream inStream = null;
        try {
            URL url = License.class.getClassLoader().getResource("license");// file name
            inStream = new FileInputStream(url.getPath());
            // read file
//			StringBuilder sb = new StringBuilder();
//			byte[] byteTmp = new byte[1024];
//			while (inStream.read(byteTmp) != -1) {
//				sb.append(new String(byteTmp));
//				byteTmp = new byte[1024];
//			}
//			String licStr = sb.toString();
            // read file
            byte[] bytes = new byte[inStream.available()];
            inStream.read(bytes);
            String licStr = new String(bytes);
//			log.info(licStr);

            // decode
            byte[] data = LicenseGenerator.decryptPBE(Base64.decodeBase64(licStr));
//			log.info(new String(data));
            // load
            Properties prop = new Properties();
            prop.load(new ByteArrayInputStream(data));

            // property
            this.setType(prop.getProperty("type"));
            this.setDateExpired(prop.getProperty("dateExpired"));
            this.setMac(StringUtils.split(prop.getProperty("mac"), ","));
            this.setIp(StringUtils.split(prop.getProperty("ip"), ","));
            this.setUser(prop.getProperty("user"));
            this.setAddress(prop.getProperty("address"));
            this.setContact(prop.getProperty("contact"));
            this.setTel(prop.getProperty("tel"));
            this.setZip(prop.getProperty("zip"));
            this.setSign(prop.getProperty("sign"));
            return true;
        } catch (Exception e) {
//			log.error("read license error", e);
        } finally {
            IOUtils.closeQuietly(inStream);
        }
        return false;
    }

    /**
     * 校验许可文件
     *
     * @return
     */
    public boolean verify() {
        try {
            if (StringUtils.isBlank(this.getSign())) {
                this.setValid(false);
                return false;
            }

            // dsa
            if (!LicenseGenerator.verifyDSA(this.getSignValues().getBytes(), this.getSign())) {
                return false;
            }

            // dateExpired
            if (StringUtils.isNotBlank(this.getDateExpired())) {
                Date date = DateUtils.parseDate(this.getDateExpired(), DateUtil.PATTERN_DATE);
                if (date.before(new Date())) {
                    return false;
                }
            }

            // mac, license and server
            if (ArrayUtils.isNotEmpty(this.getMac())) {
                boolean hasMac = false;
                for (String mac : this.getMac()) {
                    if (NetworkUtil.containsLocalMac(mac)) {
                        hasMac = true;
                        break;
                    }
                }
                if (!hasMac) {
                    return false;
                }
            }

            // ip, license and server
            if (ArrayUtils.isNotEmpty(this.getIp())) {
                boolean hasIp = false;
                for (String ip : this.getIp()) {
                    if (NetworkUtil.containsLocalIp(ip)) {
                        hasIp = true;
                        break;
                    }
                }
                if (!hasIp) {
                    return false;
                }
            }

            this.setValid(true);
            return true;
        } catch (Exception e) {
            this.setValid(false);
//			log.error("verify dsa error", e);
        }
        return false;
    }

    public String getSignValues() {
        StringBuilder sb = new StringBuilder("")
                .append(StringUtils.trimToEmpty(this.getType()))
                .append(StringUtils.trimToEmpty(this.getDateExpired()))
                .append(StringUtils.trimToEmpty(StringUtils.join(this.getMac(), ",")))
                .append(StringUtils.trimToEmpty(StringUtils.join(this.getIp(), ",")))
                .append(StringUtils.trimToEmpty(this.getUser()))
                .append(StringUtils.trimToEmpty(this.getAddress()))
                .append(StringUtils.trimToEmpty(this.getContact()))
                .append(StringUtils.trimToEmpty(this.getTel()))
                .append(StringUtils.trimToEmpty(this.getZip()));
        return sb.toString();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("")
                .append("type=").append(StringUtils.trimToEmpty(this.getType())).append("\n")
                .append("dateExpired=").append(StringUtils.trimToEmpty(this.getDateExpired())).append("\n")
                .append("mac=").append(StringUtils.trimToEmpty(StringUtils.join(this.getMac(), ","))).append("\n")
                .append("ip=").append(StringUtils.trimToEmpty(StringUtils.join(this.getIp(), ","))).append("\n")
                .append("user=").append(StringUtils.trimToEmpty(this.getUser())).append("\n")
                .append("address=").append(StringUtils.trimToEmpty(this.getAddress())).append("\n")
                .append("contact=").append(StringUtils.trimToEmpty(this.getContact())).append("\n")
                .append("tel=").append(StringUtils.trimToEmpty(this.getTel())).append("\n")
                .append("zip=").append(StringUtils.trimToEmpty(this.getZip())).append("\n")
                .append("sign=").append(StringUtils.trimToEmpty(this.getSign())).append("\n");
        return sb.toString();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDateExpired() {
        return dateExpired;
    }

    public void setDateExpired(String dateExpired) {
        this.dateExpired = dateExpired;
    }

    public String[] getMac() {
        return mac;
    }

    public void setMac(String... mac) {
        this.mac = mac;
    }

    public String[] getIp() {
        return ip;
    }

    public void setIp(String... ip) {
        this.ip = ip;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

}
