package com.taole.framework.service;

import com.taole.framework.annotation.ReturnCodeInfo;
import com.taole.framework.util.ClassUtils;
import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class GenerateLocalReturnCode<P, R> {
    NORM<P, String> norm;
    //要读取的xml文件路径
    private String path;

    //要生成的返回码页面路径
    private String generateReturnCodePath;

    public GenerateLocalReturnCode(String path, String generateReturnCodePath) {
        this.path = path;
        this.generateReturnCodePath = generateReturnCodePath;
    }

    public GenerateLocalReturnCode(String path, String generateReturnCodePath, NORM norm) {
        this.path = path;
        this.generateReturnCodePath = generateReturnCodePath;
        this.norm = norm;
    }

    public GenerateLocalReturnCode generateReturnCode() throws SecurityException,
            NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException {

        List<String> packageList = getPackageList(path);
        for (String packageName : packageList) {
            if (!packageName.equals("com.taole.framework.rest")) {
                //获取指定包下所有类
                List<Class<?>> classes = ClassUtils.getClasses(packageName);
                for (Class<?> c : classes) {
                    for (Method method : GenerateLocalReturnCode.class.getClassLoader().loadClass(c.getName()).getMethods()) {
                        //如果包含ReturnCodeInfo注解的方法
                        if (method.isAnnotationPresent(ReturnCodeInfo.class)) {
                            ReturnCodeInfo returnCodeInfo = method.getAnnotation(ReturnCodeInfo.class);
                            System.out.println(method.getName());
                            String returnCode = returnCodeInfo.value();
                            String className = null;
                            if (null != norm)
                                className = norm.call((P) packageName);
                            else
                                className = packageName.split("service.rest")[0] + "utils.ReturnCodeDefine";
                            if (org.apache.commons.lang3.StringUtils.isBlank(className)) continue;
                            Class<?> returnCodeClass = null;
                            try {
                                returnCodeClass = Class.forName(className);
                            } catch (ClassNotFoundException e) {
                                continue;
                            }
                            Method getReturnCodeForSwagger = returnCodeClass.getMethod("getReturnCodeForSwagger", String.class, String.class);
                            String returnMessage = (String) getReturnCodeForSwagger.invoke(String.class, returnCode, className);
                            //生成html页面
                            if (StringUtils.isNotBlank(returnMessage)) {
                                writeFile(generateReturnCodePath, returnMessage, c.getName(), method.getName(), returnCodeClass);
                            }
                        }
                    }
                }
            }
        }
        return this;
    }


    public void writeFile(String filePath, String content, String serviceName,
                          String methodName, Class<?> returnCodeClass) {
        BufferedWriter writer = null;
        try {
            File floderFile = new File(filePath);
            if (!floderFile.exists()) {
                floderFile.mkdir();
            }

            serviceName = serviceName.split("\\.")[(serviceName.split("\\.").length) - 1];

            String classPath = returnCodeClass.getResource("").toString();
            classPath = classPath.substring(classPath.indexOf("lib") + 4, classPath.length());
            //String moduleName = classPath.split("-1.0")[0];
            String moduleName = classPath.split("-")[0] + classPath.split("-")[1];
            String fileName = moduleName + "_" + serviceName + "_" + methodName + ".html";
            File file = new File(filePath + File.separator + fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
            OutputStreamWriter write = new OutputStreamWriter(
                    new FileOutputStream(file), "gbk");
            writer = new BufferedWriter(write);
            writer.write(content);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public static List<String> getPackageList(String filePath) {

        //存储web项目的所有controller包地址
        List<String> packageList = new ArrayList<String>();

        //1、创建一个DocumentBuilderFactory的对象
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        //2、创建一个DocumentBuilder的对象
        try {
            //创建DocumentBuilder对象
            DocumentBuilder db = dbf.newDocumentBuilder();
            //3、通过DocumentBuilder对象的parser方法加载books.xml文件到当前项目下
            /*注意导入Document对象时，要导入org.w3c.dom.Document包下的*/
            Document document = db.parse(filePath);//传入文件名可以是相对路径也可以是绝对路径
            //获取所有context:component-scan节点的集合
            NodeList list = document.getElementsByTagName("context:component-scan");
            //遍历每一个节点
            for (int i = 0; i < list.getLength(); i++) {
                //未知节点属性的个数和属性名时:
                //通过 item(i)方法 获取一个节点，nodelist的索引值从0开始
                Node book = list.item(i);
                //获取节点的所有属性集合
                NamedNodeMap attrs = book.getAttributes();
                //遍历的属性
                for (int j = 0; j < attrs.getLength(); j++) {
                    //通过item(index)方法获取节点的某一个属性
                    Node attr = attrs.item(j);

                    packageList.add(attr.getNodeValue());
                }
            }

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return packageList;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getGenerateReturnCodePath() {
        return generateReturnCodePath;
    }

    public void setGenerateReturnCodePath(String generateReturnCodePath) {
        this.generateReturnCodePath = generateReturnCodePath;
    }


    public static interface NORM<P, R> {
        R call(P param);
    }
}
