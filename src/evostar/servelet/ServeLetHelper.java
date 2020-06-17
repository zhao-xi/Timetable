package evostar.servelet;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class ServeLetHelper {
    //解析参数
    public static HashMap<String,String> parseParam(String args){
        String[] paramGroup = args.split("&");
        System.out.println(Arrays.deepToString(paramGroup));
        HashMap<String,String> hashMap = new HashMap<>();
        for(int i =0;i<paramGroup.length;i++){
            String[] params = paramGroup[i].split("=");
            hashMap.put(params[0],params[1]);
        }
        return hashMap;
    }

    //判断参数是否存在
    public static boolean checkParam(String[] paramKeys,String args){
        HashMap<String,String> params = ServeLetHelper.parseParam(args);
        for(int i =0;i<paramKeys.length;i++){
            if(!params.containsKey(paramKeys[i])){
                return false;
            }
        }
        return true;
    }

    //字符串转化为md5加密
    public static String md5(String value) throws NoSuchAlgorithmException {
        //1. 获得md5加密算法工具类
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        //2. 加密的结果为十进制
        byte[] md5Bytes = messageDigest.digest(value.getBytes());
        //3. 将md5加密算法值转化为16进制
        BigInteger bigInteger = new BigInteger(1, md5Bytes);
        return bigInteger.toString(16);
    }
}
