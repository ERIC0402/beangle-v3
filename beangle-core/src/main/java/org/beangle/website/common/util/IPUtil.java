/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.beangle.website.common.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;

/**
 *
 * @author CHII
 */
public class IPUtil {
    /**
     * IP集合
     */
    private List<Long> list;
    /**
     * IP范围
     */
    private Map<Long, Long> map;

    public IPUtil() {
        this.list = new ArrayList<Long>();
        this.map = new HashMap<Long, Long>();
    }

    public IPUtil(String ipstr) {
        this();
        if(StringUtils.isEmpty(ipstr)){
            return;
        }
        String[] ss = ipstr.split(",");
        for(String s : ss){
            if(s.indexOf(".") < 0){//不包含小数点的字符忽略
                continue;
            }
            if(s.indexOf("-") > 0){
                String[] nn = s.split("-");
                if(nn.length != 2){//IP范围不是2个忽略
                    continue;
                }
                Long n1 = getLong(nn[0]);
                Long n2 = getLong(nn[1]);
                if(n1 == null || n2 == null){//IP格式不正确忽略
                    continue;
                }
                map.put(Math.min(n1, n2), Math.max(n1, n2));
            }else{
                Long n = getLong(s);
                if(n == null ){//IP格式不正确忽略
                    continue;
                }
                list.add(n);
            }
        }
    }

    public List<Long> getList() {
        return list;
    }

    public void setList(List<Long> list) {
        this.list = list;
    }

    public Map<Long, Long> getMap() {
        return map;
    }

    public void setMap(Map<Long, Long> map) {
        this.map = map;
    }

    private Long getLong(String s) {
        if(StringUtils.isEmpty(s)){
            return null;
        }
        String[] ss = s.split("\\.");
        if(ss.length != 4){
            return null;
        }
        try {
            Long n = 0L;
            for(int i = ss.length - 1,j=1; i >=0; i--,j*=1000){
                n+= Long.parseLong(ss[i]) * j;
            }
            return n;
        } catch (Exception e) {
            return null;
        }
    }

    public Boolean contains(String remoteAddr) {
        Long n = getLong(remoteAddr);
        if(n == null){
        	return false;
        }
        for(Long l : list){
            if(n.equals(l)){
                return true;
            }
        }
        for(Long l : map.keySet()){
            if(n >= l && n <= map.get(l)){
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        IPUtil i = new IPUtil();
        System.out.println(i.getLong("192.168.0.28"));
    }

	public static boolean isIpValid(String remoteAddr, String xzipd) {
		IPUtil iu = new IPUtil(xzipd);
		return iu.contains(remoteAddr);
	}
}
