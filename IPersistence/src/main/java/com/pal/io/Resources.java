package com.pal.io;

import java.io.InputStream;

/**
 * @author pal
 * @date 2020/8/28 10:14 上午
 */
public class Resources {

    /**
     * 根据配置文件的路径将配置文件加载成字节输入流、存到内存
     * @param path
     * @return
     */
    public static InputStream getResourceAsSteam(String path){
        // Class.getClassLoader.getResourceAsStream(String path) ： 默认则是从ClassPath根下获取，path不能以’/'开头，最终是由   ClassLoader获取资源。
        InputStream resourceAsSteam = Resources.class.getClassLoader().getResourceAsStream(path);
        return resourceAsSteam;
    }
}
