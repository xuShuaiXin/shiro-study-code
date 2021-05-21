import org.apache.shiro.crypto.hash.Md5Hash;

public class MD5Test {
    public static void main(String[] args) {
        /**
         *  Md5Hash 构造方法:
         *      Object source:          需要加密的明文
         *      Object salt:            随机盐(默认加在明文之前)
         *      int hashIterations:     随机盐散列次数(默认为 1)
         */
//        Md5Hash md5Hash = new Md5Hash("123", "aa", 1024);
        //md5Hash.toHex() 返回该密文额的16进制的形式
        System.out.println("密码====123");
        System.out.println("只加密============" + new Md5Hash("123").toHex());
        System.out.println("加密且加盐========" + new Md5Hash("123", "@012qq").toHex());
        System.out.println("加盐后散列1024次==" + new Md5Hash("123", "@012qq", 1024).toHex());

        /**
         * 只加密============202cb962ac59075b964b07152d234b70
         * 加密且加盐========4c24bafd82997081bdfed3d2a71389a4
         * 加盐后散列1024次==c1b38668efe7a2487056ff0189fe4e16
         */
    }
}
