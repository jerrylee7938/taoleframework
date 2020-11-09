<%@ page contentType="image/jpeg" %>
<%@ page language="java"  pageEncoding="gb2312"%>
<%@ page import="java.awt.Graphics" %>
<%@ page import="java.awt.Color" %>
<%@ page import="java.awt.Font" %>
<%@ page import="javax.imageio.ImageIO" %>
<%@ page import="java.awt.image.BufferedImage" %>
<%@ page import="java.util.Random" %>
<jsp:directive.page import="com.sun.image.codec.jpeg.JPEGImageEncoder"/>
<jsp:directive.page import="com.sun.image.codec.jpeg.JPEGCodec"/>
<%
System.setProperty("java.awt.headless", "true");

response.setHeader("Pragma","No-cache");
response.setHeader("Cache-Control","no-cache");
response.setDateHeader("Expires", 0);

//set width and height
int width = 60 ; 
int height = 20;

BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

Graphics g = image.getGraphics();

g.setColor(new Color(250,250,250));
g.fillRect(0, 0, width, height);

g.setFont(new Font("Courier New",Font.BOLD,18));

g.setColor(new Color(30,30,30));
Random random = new Random();
for (int i = 0; i < 6; i++){
    int x = random.nextInt(width);
    int y = random.nextInt(height);
    int xl = random.nextInt(20);
    int yl = random.nextInt(20);
    g.drawLine(x,y,x+xl,y+yl);
}

String code = "";
for (int i = 0; i < 4; i++){
    String rand=String.valueOf(random.nextInt(10));
    code += rand;
    g.setColor(new Color(0,0,0));
    g.drawString(rand,13*i+6,16);
}

Cookie[] cookies=request.getCookies(); 
String cookieName="code"; 
Cookie cookie=new Cookie(cookieName, code); 
response.addCookie(cookie);
//session.setAttribute("code",code);
//System.out.println(session.getAttribute("code"));
g.dispose();

//ImageIO.write(image, "JPEG", response.getOutputStream());
JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(response.getOutputStream()); 
encoder.encode(image); 

out.clear();
out = pageContext.pushBody();
%> 
