package com.liangxunwang.unimanager.util;

/**
 * Created by zhl on 2015/1/29.
 */
public class Constants {
        //服务器地址
        public static final String URL = "http://120.27.41.21:8070/";
//        public static final String URL = "http://192.168.0.224:8080/";

        //七牛云存储
        public static final String QINIU_URL = "http://7xt74j.com1.z0.glb.clouddn.com/";
        public static final String QINIU_SPACE = "paopao-pic";
        public static final String FILE_PATH = "D://recordfile";

        //百度推送
        public static final String API_KEY = "9rc4UmyvXfVgniywpipcbxYW";
        public static final String SECRET_KEY = "hDbXD0tV5SDgUs6zHQG7DnD456bjUCLY";
        public static final String IOS_API_KEY = "W38xecAXVYDY955Shv0GGc8f";
        public static final String IOS_SECRET_KEY = "ZrhZicrOHvexKhRoKWFIdyaEsCTrUeom";

        public static final String SAVE_ERROR = "save_error";

        public static final String HAS_ZAN = "has_zan";

        public static final String HAS_CODE = "has_code";

        public static final String NO_SEND_CODE = "no_send_code";

        public static final String SEND_SMS_ERROR = "send_sms_error";

        public static final String HAS_EXISTS = "has_exists";

        public static final String TOO_MANY_CODE = "too_many_code";

        public static final String CODE_NOT_EQUAL = "code_not_equal";

        public static final String PHONE_ERROR = "phone_error";
        public static final String HX_ERROR = "hx_error";


        public static final String SMS_MESSAGE_URL = "http://60.209.7.78:8080/smsServer/submit";


        //默认头像
        public static final String[] PHOTOURLS = {
                "upload/pic1.jpg",
                "upload/pic2.jpg",
                "upload/pic3.jpg",
                "upload/pic4.jpg",
                "upload/pic5.jpg",
                "upload/pic6.jpg",
                "upload/pic7.jpg",
                "upload/pic8.jpg",
                "upload/pic9.jpg",
                "upload/pic10.jpg",
                "upload/pic11.jpg",
                "upload/pic12.jpg",
                "upload/pic13.jpg",
                "upload/pic14.jpg",
                "upload/pic15.jpg",
                "upload/pic16.jpg",
                "upload/pic17.jpg",
                "upload/pic18.jpg",
                "upload/pic19.jpg",
                "upload/pic20.jpg",
                "upload/pic21.jpg",
                "upload/pic22.jpg",
                "upload/pic23.jpg",
                "upload/pic24.jpg",
                "upload/pic25.jpg",
                "upload/pic26.jpg",
                "upload/pic27.jpg",
                "upload/pic28.jpg",
                "upload/pic29.jpg",
                "upload/pic30.jpg",
                "upload/pic31.jpg",
                "upload/pic32.jpg",
                "upload/pic33.jpg",
                "upload/pic34.jpg",
                "upload/pic35.jpg",
                "upload/pic36.jpg",
                "upload/pic37.jpg",
                "upload/pic38.jpg",
                "upload/pic39.jpg",
                "upload/pic40.jpg",
                "upload/pic41.jpg",
                "upload/pic42.jpg",
                "upload/pic43.jpg",
                "upload/pic44.jpg",
                "upload/pic45.jpg",
                "upload/pic46.jpg",
                "upload/pic47.jpg",
                "upload/pic48.jpg",
                "upload/pic49.jpg",
                "upload/pic50.jpg",
                "upload/pic51.jpg",
                "upload/pic52.jpg",
                "upload/pic53.jpg",
                "upload/pic54.jpg",
                "upload/pic55.jpg",
                "upload/pic56.jpg",
                "upload/pic57.jpg",
                "upload/pic58.jpg",
                "upload/pic59.jpg",
                "upload/pic60.jpg",
                "upload/pic61.jpg"
        };

        public static final Long DAY_MILLISECOND = 86400000L;

        //----------------支付宝------------------
        //商户PID
        public static final String PARTNER = "2088411171875209";
        //商户收款账号
        public static final String SELLER = "18605438888@163.com";
        //商户私钥，pkcs8格式
        public static final String RSA_PRIVATE = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBANQs4I2oiVOvzLk0h63cnqvZuAd6WHzew0+XCsg2M57J2bC+yHpSwxKHi6Aw3xoAhpNWkkYzAUo4rjYhCfcYy5YlNd+S4nTvlqV4O1ncyqfKE4Rwe7/WJI546FcqwE8FNam7Z+Noc7ffSq3Nt+Sy4ZzbDnMGIrVooEV2FWJBqGpVAgMBAAECgYAY0mM2z1e5faCSri2NTnkVm7Pm1A5UFW99+Sqev5CMbArWnYswnEwL9+WCyRTgot0bkY4tPjxRZY4j8PuMd88hO/tHqpDJKqNgg8VqSVvYHi49JLI6kub0uXWz8C6uPGASEwfBI7wXkXfxE4x9bZYyIL+SjRPHa90/pA7ZjY38wQJBAPchNXCxX0sCnZVhApW2EM0oznRpv6rmi3N5IgHd8hcIDnGDR1neVhUONlB6wSf7vg1noOb4ijVBKZuBAqABkgUCQQDbynvtWpjZ0SEOoRvZ7LTWcJfnDkCBvNbECvVBXykKam2Kip5HGtTEE0/wcsOCw2vpJDkblLuGBb2eD4/MrFgRAkEAoJ554EO4HsWVbDg/+NP7eYxTGwB8LXRoIGYTf0kkhmmbEZX0cul543d4P7h5ACazcHzw7A2PrmsqwsIH1b0PjQJBAMRKqDcorE4LybwpApi0ICVL1T4TqmSz5BKdvr+tXxfgSQhsGENkoRSkMsaJmDaWlS9YnHAXewqM4fXh7Alc+iECQQC3mgswgqbsjqj1Es7LnwpgXvEH4iDChf3wW4evsCuZi7iAGd0i+VdAfigzpjEKs/6BxYN6aA+FwA0IVpnVyo4q";
        //支付宝公钥
        public static final String RSA_PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDULOCNqIlTr8y5NIet3J6r2bgHelh83sNPlwrINjOeydmwvsh6UsMSh4ugMN8aAIaTVpJGMwFKOK42IQn3GMuWJTXfkuJ075aleDtZ3MqnyhOEcHu/1iSOeOhXKsBPBTWpu2fjaHO330qtzbfksuGc2w5zBiK1aKBFdhViQahqVQIDAQAB";

}
