package com.taole.toolkit.util.concurrence;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
/**
 * @Date 1026/12/2
 * @author ChengJ
 * @desc Socket线程事务处理（暂未完成）
 *
 */
public class Handler2Socket implements Runnable{  
    private Socket socket;  
    public Handler2Socket(Socket socket){  
        this.socket=socket;  
    }  
      
    private PrintWriter getWriter(Socket socket)throws IOException{  
        OutputStream socketOut=socket.getOutputStream();      
        return new PrintWriter(socketOut, true);//参数为true表示每写一行，PrintWriter缓存就自动溢出，把数据写到目的  
    }  
      
    private BufferedReader getReader(Socket socket)throws IOException {  
        InputStream socketIn=socket.getInputStream();  
        return new BufferedReader(new InputStreamReader(socketIn));  
          
    }  
    public String echo(String msg){  
        return "echo:"+msg;  
          
    }  
    public void run(){  
        try {  
            //得到客户端的地址和端口号  
            System.out.println("New connection accepted"+socket.getInetAddress()+":"+socket.getPort());  
            BufferedReader br=getReader(socket);  
            PrintWriter pw=getWriter(socket);    
            String msg=null;  
            while ((msg=br.readLine())!=null) {  
                System.out.println(msg);  
                pw.println(echo(msg));  
                if(msg.endsWith("bye")){  
                    break;  
                }  
            }  
        } catch (IOException e) {  
            e.printStackTrace();  
        }finally{  
            try {  
                if(socket!=null){  
                    socket.close();  
                }  
                  
            } catch (IOException  e) {  
                e.printStackTrace();  
            }  
        }  
    }  
}  