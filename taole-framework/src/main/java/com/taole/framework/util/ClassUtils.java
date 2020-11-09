/**
 * Project:taole-heaframework
 * File:ClassUtils.java 
 * Copyright 2004-2011  Co., Ltd. All rights reserved.
 */
package com.taole.framework.util;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * @author rory
 * @date Sep 8, 2011
 * @version $Id: ClassUtils.java 9893 2017-11-14 03:35:26Z lizhm $
 */
public final class ClassUtils {
	
	private ClassUtils() {
	}
	
	/**
	 * 根据包名取该包下面所有的类名。
	 * <p> 注意：不包含inner class.</p>
	 * @param packageName
	 * @return
	 */
	public static final List<String> getClassNamesFromPackage(String packageName) {
		List<String> classNames = new ArrayList<String>();
		URL url = ClassUtils.class.getResource("/" + packageName.replace(".", "/"));
		if (url == null) {
			return classNames;
		}
		if ("file".equals(url.getProtocol())) {
			File file = new File(url.getPath());
			if (file == null || !file.exists() || !file.isDirectory()) {
				return classNames;
			}
			File[] classFiles = file.listFiles(new FilenameFilter() {
				public boolean accept(File dir, String name) {
					return name.endsWith(".class");
				}
			});
			for (File classFile: classFiles) {
				String className = classFile.getName().split("\\.")[0];
				if (className.indexOf("$") == -1) {
					classNames.add(packageName + "." + className);
				}
			}
		} else if ("jar".equals(url.getProtocol())) {
			String path = url.getPath();
			path = path.substring(0, path.indexOf("!"));
			try {
				ZipInputStream zipInputStream = new ZipInputStream(new URL(path).openStream());
				ZipEntry entry = null;
				while ((entry = zipInputStream.getNextEntry()) != null) {
					String name = entry.getName();
					if (name.indexOf(".class") == -1 || name.indexOf("$") != -1) {
						continue;
					}
					name = name.substring(0, name.indexOf(".class")).replace("/", ".");
					if (name.startsWith(packageName)) {
						String subString = name.substring(packageName.length() + 1);
						if (subString.indexOf(".") == -1) {
							classNames.add(name);
						}
					}
				}
			} catch (Exception e) {
				throw new RuntimeException("try to get class in jar[" + path + "] error", e);
			}
		} else {
			throw new RuntimeException("don't know how to deal this url:" + url);
		}
		return classNames;
	}
	
	/** 
     * 从包package中获取所有的Class 
     * @param pack 
     * @return 
     */  
    public static List<Class<?>> getClasses(String packageName){  
          
        //第一个class类的集合  
        List<Class<?>> classes = new ArrayList<Class<?>>();  
        //是否循环迭代  
        boolean recursive = true;  
        //获取包的名字 并进行替换  
        String packageDirName = packageName.replace('.', '/');  
        //定义一个枚举的集合 并进行循环来处理这个目录下的things  
        Enumeration<URL> dirs;  
        try {  
            dirs = Thread.currentThread().getContextClassLoader().getResources(packageDirName);  
            //循环迭代下去  
            while (dirs.hasMoreElements()){  
                //获取下一个元素  
                URL url = dirs.nextElement();  
                //得到协议的名称  
                String protocol = url.getProtocol();  
                //如果是以文件的形式保存在服务器上  
                if ("file".equals(protocol)) {  
                    //获取包的物理路径  
                    String filePath = URLDecoder.decode(url.getFile(), "UTF-8");  
                    //以文件的方式扫描整个包下的文件 并添加到集合中  
                    findAndAddClassesInPackageByFile(packageName, filePath, recursive, classes);  
                } else if ("jar".equals(protocol)){  
                    //如果是jar包文件   
                    //定义一个JarFile  
                    JarFile jar;  
                    try {  
                        //获取jar  
                        jar = ((JarURLConnection) url.openConnection()).getJarFile();  
                        //从此jar包 得到一个枚举类  
                        Enumeration<JarEntry> entries = jar.entries();  
                        //同样的进行循环迭代  
                        while (entries.hasMoreElements()) {  
                            //获取jar里的一个实体 可以是目录 和一些jar包里的其他文件 如META-INF等文件  
                            JarEntry entry = entries.nextElement();  
                            String name = entry.getName();  
                            //如果是以/开头的  
                            if (name.charAt(0) == '/') {  
                                //获取后面的字符串  
                                name = name.substring(1);  
                            }  
                            //如果前半部分和定义的包名相同  
                            if (name.startsWith(packageDirName)) {  
                                int idx = name.lastIndexOf('/');  
                                //如果以"/"结尾 是一个包  
                                if (idx != -1) {  
                                    //获取包名 把"/"替换成"."  
                                    packageName = name.substring(0, idx).replace('/', '.');  
                                }  
                                //如果可以迭代下去 并且是一个包  
                                if ((idx != -1) || recursive){  
                                    //如果是一个.class文件 而且不是目录  
                                    if (name.endsWith(".class") && !entry.isDirectory()) {  
                                        //去掉后面的".class" 获取真正的类名  
                                        String className = name.substring(packageName.length() + 1, name.length() - 6);  
                                        try {  
                                            //添加到classes  
                                            classes.add(Class.forName(packageName + '.' + className));  
                                        } catch (ClassNotFoundException e) {  
                                            e.printStackTrace();  
                                        }  
                                      }  
                                }  
                            }  
                        }  
                    } catch (IOException e) {  
                        e.printStackTrace();  
                    }   
                }  
            }  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
         
        return classes;  
    }  
    
    
    /** 
     * 以文件的形式来获取包下的所有Class 
     * @param packageName 
     * @param packagePath 
     * @param recursive 
     * @param classes 
     */  
    public static void findAndAddClassesInPackageByFile(String packageName, String packagePath, final boolean recursive, List<Class<?>> classes){  
        //获取此包的目录 建立一个File  
        File dir = new File(packagePath);  
        //如果不存在或者 也不是目录就直接返回  
        if (!dir.exists() || !dir.isDirectory()) {  
            return;  
        }  
        //如果存在 就获取包下的所有文件 包括目录  
        File[] dirfiles = dir.listFiles(new FileFilter() {  
        //自定义过滤规则 如果可以循环(包含子目录) 或则是以.class结尾的文件(编译好的java类文件)  
              public boolean accept(File file) {  
                return (recursive && file.isDirectory()) || (file.getName().endsWith(".class"));  
              }  
            });  
        //循环所有文件  
        for (File file : dirfiles) {  
            //如果是目录 则继续扫描  
            if (file.isDirectory()) {  
                findAndAddClassesInPackageByFile(packageName + "." + file.getName(),  
                                      file.getAbsolutePath(),  
                                      recursive,  
                                      classes);  
            }  
            else {  
                //如果是java类文件 去掉后面的.class 只留下类名  
                String className = file.getName().substring(0, file.getName().length() - 6);  
                try {  
                    //添加到集合中去  
                    classes.add(Class.forName(packageName + '.' + className));  
                } catch (ClassNotFoundException e) {  
                    e.printStackTrace();  
                }  
            }  
        }  
    }
}
