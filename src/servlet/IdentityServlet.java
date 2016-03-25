package servlet;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

@WebServlet("/IdentityServlet")
public class IdentityServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -479885884254942306L;

	public static final char[] CHARS = { '2', '3', '4', '5', '6', '7', '8',
			'9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J', 'K', 'L', 'M',
			'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };

	public static Random random = new Random();

	public static String getRandomString() {
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < 6; i++) {
			buffer.append(CHARS[random.nextInt(CHARS.length)]);
		}
		return buffer.toString();
	}

	//获取随机颜色
	public static Color getRandomColor() {
		return new Color(random.nextInt(255), random.nextInt(255), random
				.nextInt(255));
	}

	//返回某颜色的反色
	public static Color getReverseColor(Color c) {
		return new Color(255 - c.getRed(), 255 - c.getGreen(), 255 - c
				.getBlue());
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try{
			response.setContentType("image/jpeg");
	
			String randomString = getRandomString();
			request.getSession(true).setAttribute("randomString", randomString);
	
			int width = 100;
			int height = 30;
	
			Color color = getRandomColor();//随即颜色用于背景色
			Color reverse = getReverseColor(color);//反色用于前景色
	
			BufferedImage bi = new BufferedImage(width, height,
					BufferedImage.TYPE_INT_RGB);//创建彩色图片
			Graphics2D g = bi.createGraphics();//获取绘图对象
			g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));//设置字体
			g.setColor(color);
			g.fillRect(0, 0, width, height);//绘制背景
			g.setColor(reverse);
			g.drawString(randomString, 18, 20);
			for (int i = 0, n = random.nextInt(100); i < n; i++) {
				g.drawRect(random.nextInt(width), random.nextInt(height), 1, 1);
			}
	
			// 转成JPEG格式
			ServletOutputStream out = response.getOutputStream();
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);//编码器
			encoder.encode(bi);//对图片进行编码
			out.flush();
			out.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		System.out.println(getRandomString());
	}
}

