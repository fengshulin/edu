package com.lifeng.util;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.OutputStream;
import java.util.Hashtable;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.StringUtils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;


public class QRCodeUtil
{
	// 字节集
	private static final String	CHARSET			= "UTF-8";
	// 二维码图片类型
	public static final String	DEFAULT_FORMAT	= "jpg";
	// 黑格子颜色
	private static final int	QRCOLOR_BLACK	= 0xff000000;
	// 白格子颜色
	private static final int	QRCOLOR_WHITE	= 0xFFFFFFFF;

	/**
	 * 生成二维码图片缓存
	 * @param content 内容
	 * @param imgPath LOGO图片路径
	 * @param needCompress LOGO图片压缩 
	 * @throws Exception
	 */
	private static BufferedImage createImage(String content, int size, String forecolor, String backcolor, String imgPath, boolean needCompress) throws Exception
	{
		// 设置生成二维码图片参数
		Hashtable<EncodeHintType,Object> hints = new Hashtable<EncodeHintType,Object>();
		// 设置二维码容错率
		hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
		// 设置二维码字节集
		hints.put(EncodeHintType.CHARACTER_SET, CHARSET); 
		// 对二维码内容编码
		BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, size, size, hints);
		// 创建二维码缓存图片
		BufferedImage image = new BufferedImage(bitMatrix.getWidth(), bitMatrix.getHeight(), BufferedImage.TYPE_INT_RGB);
		// 处理二维码前景格子颜色
		int rgb_black = StringUtils.isBlank(forecolor) ? QRCOLOR_BLACK : Integer.parseInt(forecolor.replaceAll("#", ""), 16);
		// 处理二维码背景格子颜色
		int rgb_white = StringUtils.isBlank(backcolor) ? QRCOLOR_WHITE : Integer.parseInt(backcolor.replaceAll("#", ""), 16);
		// 按x轴和y轴坐标轮询生成二维码格子
		for (int x = 0; x < image.getWidth(); x++)
		{
			for (int y = 0; y < image.getHeight(); y++)
			{
				// 在缓存图片的相应坐标画格子
				image.setRGB(x, y, bitMatrix.get(x, y) ? rgb_black : rgb_white);
			}
		}
		// 判断二维码是否包含LOGO图标
		if (!StringUtils.isBlank(imgPath))
		{
			// 向二维码图片中央插入LOGO图片
			QRCodeUtil.insertImage(image, imgPath, needCompress);
		}
		// 返回生成的二维码图片
		return image;
	}

	/**
	 * 插入LOGO图片
	 * @param source 二维码图片缓存
	 * @param imgPath LOGO图片路径
	 * @param needCompress LOGO图片压缩
	 * @throws Exception
	 */
	private static void insertImage(BufferedImage source, String imgPath, boolean needCompress) throws Exception
	{
		File file = new File(imgPath);
		if (!file.exists())
		{
			throw new java.lang.RuntimeException("文件[" + imgPath + "]不存在!");
		}
		Image src = ImageIO.read(file);
		int width = src.getWidth(null);
		int height = src.getHeight(null);
		int qr_width = source.getWidth();
		int qr_height = source.getHeight();
		int max_width = qr_width / 5;
		int max_height = qr_height / 5;
		// 压缩LOGO图片尺寸
		if (needCompress && (width > max_width || height > max_height))
		{
			width = width > max_width ? max_width : width;
			height = height > max_height ? max_height : height;
			Image image = src.getScaledInstance(width, height, Image.SCALE_SMOOTH);
			BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			Graphics g = tag.getGraphics();
			g.drawImage(image, 0, 0, null); // 绘制缩小后的图
			g.dispose();
			src = image;
		}
		// 插入LOGO
		Graphics2D graph = source.createGraphics();
		int x = (qr_width - width) / 2;
		int y = (qr_height - height) / 2;
		graph.drawImage(src, x, y, width, height, null);
		Shape shape = new RoundRectangle2D.Float(x, y, width, width, 6, 6);
		graph.setStroke(new BasicStroke(3f));
		graph.draw(shape);
	    //指定弧度的圆角矩形
        RoundRectangle2D.Float round = new RoundRectangle2D.Float(x, y, width, width,20,20);
        graph.setColor(Color.white);
		graph.draw(round);
		
		graph.dispose();
	}

	/**
	 * 生成二维码图片(含LOGO图片)
	 * @param content 二维码内容
	 * @param imgPath LOGO图片路径
	 * @param fileName 二维码图片名称
	 * @param destPath 二维码图片生成路径
	 * @param needCompress LOGO图片压缩
	 * @throws Exception
	 */
	public static String encode(String content, int size, String imgPath, String fileName, String forecolor, String backcolor, String destPath, boolean needCompress) throws Exception
	{
		// 生成二维码图片缓存(含LOGO图片)
		BufferedImage image = QRCodeUtil.createImage(content, size, forecolor, backcolor, imgPath, needCompress);
		// 检查二维码图片生成路径,若不存在则创建(含父路径)
		File file = new File(destPath);
		if (!file.exists() && !file.isDirectory())
		{
			file.mkdirs();
		}
		// 二维码图片生成
		String link = destPath.endsWith("/") ? "" : "/";
		String url = destPath + link + fileName + "." + DEFAULT_FORMAT;
		ImageIO.write(image, DEFAULT_FORMAT, new File(url));
		return url;
	}

	/**
	 * 二维码图片生成(不含LOGO图片)
	 * @param content 二维码内容
	 * @param imgPath LOGO图片路径
	 * @param fileName 二维码图片名称
	 * @param destPath 二维码图片生成路径
	 * @throws Exception
	 */
	public static String encode(String content, int size, String fileName, String forecolor, String backcolor, String destPath) throws Exception
	{
		return QRCodeUtil.encode(content, size, null, fileName, forecolor, backcolor, destPath, false);
	}

	/**
	 * 生成二维码输出流(含LOGO图片)
	 * @param content 二维码内容
	 * @param imgPath LOGO图片路径
	 * @param output 输出流
	 * @param needCompress LOGO图片压缩
	 * @throws Exception
	 */
	public static void encode(String content, int size, String forecolor, String backcolor, String imgPath, OutputStream output, boolean needCompress) throws Exception
	{
		BufferedImage image = QRCodeUtil.createImage(content, size, forecolor, backcolor, imgPath, needCompress);
		ImageIO.write(image, DEFAULT_FORMAT, output);
	}

	/**
	 * 生成二维码输出流(不含LOGO图片)
	 * @param content 二维码内容
	 * @param output 输出流
	 * @throws Exception
	 */
	public static void encode(String content, int size, OutputStream output) throws Exception
	{
		QRCodeUtil.encode(content, size, null, null, null, output, false);
	}

	/**
	 * 解析二维码图片文件
	 * @param file 二维码文件
	 * @return
	 * @throws Exception
	 */
	public static String decode(File file) throws Exception
	{
		if (file == null || !file.exists())
		{
			throw new java.lang.RuntimeException("文件不存在!");
		}
		BufferedImageLuminanceSource source = new BufferedImageLuminanceSource(ImageIO.read(file));
		BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
		Hashtable<DecodeHintType,Object> hints = new Hashtable<DecodeHintType,Object>();
		hints.put(DecodeHintType.CHARACTER_SET, CHARSET);
		Result result = new MultiFormatReader().decode(bitmap, hints);
		return result.getText();
	}

	/**
	 * 解析二维码
	 * @param path
	 * @return
	 * @throws Exception
	 */
	public static String decode(String path) throws Exception
	{
		return QRCodeUtil.decode(new File(path));
	}

	public static void main(String[] args) throws Exception
	{
		String text = "二维码测试!";
		String url = QRCodeUtil.encode(text, 500, "C:/Users/solin/Desktop/logo4.jpg", "large1",null , null, "C:/Users/solin/Pictures", true);
		System.out.println(url);
	}
}