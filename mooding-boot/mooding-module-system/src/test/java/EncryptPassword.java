import cn.mooding.modules.security.utils.SecurityUtils;

import java.rmi.UnknownHostException;

/**
 * @Author BlueFire
 * @Date 28/4/2021 -下午11:05
 */
public class EncryptPassword {
    public static void main(String[] args) throws UnknownHostException {
       System.out.println( SecurityUtils.encryptPassword("123456"));
    }
}
