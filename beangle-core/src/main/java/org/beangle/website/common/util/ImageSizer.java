package org.beangle.website.common.util;

import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.OutputStream;

import javax.swing.ImageIcon;

import com.sun.image.codec.jpeg.JPEGEncodeParam;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.FileOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.sun.image.codec.jpeg.JPEGImageEncoder;
import com.sun.image.codec.jpeg.JPEGCodec;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;

public class ImageSizer {

    public static final MediaTracker tracker = new MediaTracker(
            new Component() {

                private static final long serialVersionUID = 1234162663955668507L;
            });

    /**
     * @param originalFile
     *            原图像
     * @param resizedFile
     *            压缩后的图像
     * @param width
     *            图像宽
     * @param format
     *            图片格式 jpg, png, gif(非动画)
     * @throws IOException
     */
    public static void resize(File originalFile, File resizedFile, int width,
            String format) throws IOException {
        if (format != null && "gif".equals(format.toLowerCase())) {
            resize(originalFile, resizedFile, width, 1);
            return;
        } else if (format != null && "bmp".equals(format.toLowerCase())) {
            doCompress(originalFile.getPath(), width, (float) (width * 0.75), 1, "", false);
            return;
        }
//		FileInputStream fis = new FileInputStream(originalFile);
//		ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
//		int readLength = -1;
//		int bufferSize = 1024;
//		byte bytes[] = new byte[bufferSize];
//		while ((readLength = fis.read(bytes, 0, bufferSize)) != -1) {
//			byteStream.write(bytes, 0, readLength);
//		}
//		byte[] in = byteStream.toByteArray();
//		fis.close();
//		byteStream.close();
//
//		Image inputImage = Toolkit.getDefaultToolkit().createImage(in);
//		waitForImage(inputImage);
//		int imageWidth = inputImage.getWidth(null);
//		if (imageWidth < 1)
//			throw new IllegalArgumentException("image width " + imageWidth
//					+ " is out of range");
//		int imageHeight = inputImage.getHeight(null);
//		if (imageHeight < 1)
//			throw new IllegalArgumentException("image height " + imageHeight
//					+ " is out of range");
//
//		// Create output image.
//		//int height = -1;
//		double scaleW = (double) imageWidth / (double) width;
//		double scaleY = (double) imageHeight / (double) height;
//		if (scaleW >= 0 && scaleY >= 0) {
//			if (scaleW > scaleY) {
//				height = -1;
//			} else {
//				width = -1;
//			}
//		}
//		Image outputImage = inputImage.getScaledInstance(width, height,
//				java.awt.Image.SCALE_DEFAULT);
//		checkImage(outputImage);
//		encode(new FileOutputStream(resizedFile), outputImage, format);
        FileInputStream fis = new FileInputStream(originalFile);
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        int readLength = -1;
        int bufferSize = 1024;
        byte bytes[] = new byte[bufferSize];
        while ((readLength = fis.read(bytes, 0, bufferSize)) != -1) {
            byteStream.write(bytes, 0, readLength);
        }
        byte[] in = byteStream.toByteArray();
        fis.close();
        byteStream.close();

        Image inputImage = Toolkit.getDefaultToolkit().createImage(in);
        waitForImage(inputImage);
        int imageWidth = inputImage.getWidth(null);
        if (imageWidth < 1) {
            throw new IllegalArgumentException("image width " + imageWidth + " is out of range");
        }
        int imageHeight = inputImage.getHeight(null);
        if (imageHeight < 1) {
            throw new IllegalArgumentException("image height " + imageHeight + " is out of range");
        }

        // Create output image.
        int height = -1;
        double scaleW = (double) imageWidth / (double) width;
        double scaleY = (double) imageHeight / (double) height;
        if (scaleW >= 0 && scaleY >= 0) {
            if (scaleW > scaleY) {
                height = -1;
            } else {
                width = -1;
            }
        }
        Image outputImage = inputImage.getScaledInstance(width, height, java.awt.Image.SCALE_DEFAULT);
        checkImage(outputImage);
        encode(new FileOutputStream(resizedFile), outputImage, format);
    }

    /** Checks the given image for valid width and height. */
    private static void checkImage(Image image) {
        waitForImage(image);
        int imageWidth = image.getWidth(null);
        if (imageWidth < 1) {
            throw new IllegalArgumentException("image width " + imageWidth
                    + " is out of range");
        }
        int imageHeight = image.getHeight(null);
        if (imageHeight < 1) {
            throw new IllegalArgumentException("image height " + imageHeight
                    + " is out of range");
        }
    }

    /**
     * Waits for given image to load. Use before querying image
     * height/width/colors.
     */
    private static void waitForImage(Image image) {
        try {
            tracker.addImage(image, 0);
            tracker.waitForID(0);
            tracker.removeImage(image, 0);
        } catch (InterruptedException e) {
        }
    }

    /** Encodes the given image at the given quality to the output stream. */
    private static void encode(OutputStream outputStream, Image outputImage,
            String format) throws java.io.IOException {
        int outputWidth = outputImage.getWidth(null);
        if (outputWidth < 1) {
            throw new IllegalArgumentException("output image width "
                    + outputWidth + " is out of range");
        }
        int outputHeight = outputImage.getHeight(null);
        if (outputHeight < 1) {
            throw new IllegalArgumentException("output image height "
                    + outputHeight + " is out of range");
        }

        // Get a buffered image from the image.
        BufferedImage bi = new BufferedImage(outputWidth, outputHeight,
                BufferedImage.TYPE_INT_RGB);
        Graphics2D biContext = bi.createGraphics();
        biContext.drawImage(outputImage, 0, 0, null);
        ImageIO.write(bi, format, outputStream);
        System.out.println("----the end ------");
        outputStream.flush();
        outputStream.close();
    }

    /**
     * 缩放gif图片
     *
     * @param originalFile
     *            原图片
     * @param resizedFile
     *            缩放后的图片
     * @param newWidth
     *            宽度
     * @param quality
     *            缩放比例 (等比例)
     * @throws IOException
     */
    private static void resize(File originalFile, File resizedFile,
            int newWidth, float quality) throws IOException {
        if (quality < 0 || quality > 1) {
            throw new IllegalArgumentException(
                    "Quality has to be between 0 and 1");
        }
        ImageIcon ii = new ImageIcon(originalFile.getCanonicalPath());
        Image i = ii.getImage();
        Image resizedImage = null;
        int iWidth = i.getWidth(null);
        int iHeight = i.getHeight(null);
        if (iWidth > iHeight) {
            resizedImage = i.getScaledInstance(newWidth, (newWidth * iHeight)
                    / iWidth, Image.SCALE_SMOOTH);
        } else {
            resizedImage = i.getScaledInstance((newWidth * iWidth) / iHeight,
                    newWidth, Image.SCALE_SMOOTH);
        }
        // This code ensures that all the pixels in the image are loaded.
        Image temp = new ImageIcon(resizedImage).getImage();
        // Create the buffered image.
        BufferedImage bufferedImage = new BufferedImage(temp.getWidth(null),
                temp.getHeight(null), BufferedImage.TYPE_INT_RGB);
        // Copy image to buffered image.
        Graphics g = bufferedImage.createGraphics();
        // Clear background and paint the image.
        g.setColor(Color.white);
        g.fillRect(0, 0, temp.getWidth(null), temp.getHeight(null));
        g.drawImage(temp, 0, 0, null);
        g.dispose();
        // Soften.
        float softenFactor = 0.05f;
        float[] softenArray = {0, softenFactor, 0, softenFactor,
            1 - (softenFactor * 4), softenFactor, 0, softenFactor, 0};
        Kernel kernel = new Kernel(3, 3, softenArray);
        ConvolveOp cOp = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
        bufferedImage = cOp.filter(bufferedImage, null);
        // Write the jpeg to a file.
        FileOutputStream out = new FileOutputStream(resizedFile);
        // Encodes image as a JPEG data stream
        JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
        JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(bufferedImage);
        param.setQuality(quality, true);
        encoder.setJPEGEncodeParam(param);
        encoder.encode(bufferedImage);
    }

    private static void doCompress(String oldFile, int width, float height,
            float quality, String smallIcon, boolean percentage) {
        if (oldFile != null && width > 0 && height > 0) {
            String newImage = null;
            try {
                File file = new File(oldFile);

                Image srcFile = ImageIO.read(file);
                int new_w = width;
                int new_h = (int) height;
                if (percentage) {
                    double rate1 = ((double) srcFile.getWidth(null))
                            / (double) width + 0.1;
                    double rate2 = ((double) srcFile.getHeight(null))
                            / (double) height + 0.1;
                    double rate = rate1 > rate2 ? rate1 : rate2;
                    new_w = (int) (((double) srcFile.getWidth(null)) / rate);
                    new_h = (int) (((double) srcFile.getHeight(null)) / rate);
                }
                BufferedImage tag = new BufferedImage(new_w, new_h,
                        BufferedImage.TYPE_INT_RGB);
                tag.getGraphics().drawImage(srcFile, 0, 0, new_w, new_h, null);
                String filePrex = oldFile.substring(0, oldFile.lastIndexOf('.'));
                newImage = filePrex + smallIcon
                        + oldFile.substring(filePrex.length());
                FileOutputStream out = new FileOutputStream(newImage);
                JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
                JPEGEncodeParam jep = JPEGCodec.getDefaultJPEGEncodeParam(tag);
                jep.setQuality(quality, true);
                encoder.encode(tag, jep);
                out.close();
                srcFile.flush();
            } catch (FileNotFoundException e) {
            } catch (IOException e) {
            }
        }
    }

    /**
     * 保存图像
     * @param img0 上传的图像文件
     * @param path 保存路径
     * @param width 指定宽度
     * @param height 指定高度
     */
//	    public static void saveImg(MultipartFile img0, String path, Integer width, Integer height) {
//	        try {
//	            BufferedImage bimg = ImageIO.read(img0.getInputStream());
//	            saveImg(bimg, width, height, path);
//	        } catch (IOException ex) {
//	            Logger.getLogger(IMGUtil.class.getName()).log(Level.SEVERE, null, ex);
//	        }
//	    }
    /**
     * 读取一个图片并缩放到指定高宽
     * @param formPath 源图片路径
     * @param toPath 生成图片路径
     * @param width 生成图片宽度
     * @param height 生成图片高度
     */
    public static void saveImg(String formPath, String toPath, Integer width, Integer height) {
        try {
            BufferedImage bimg = ImageIO.read(new File(formPath));
            saveImg(bimg, width, height, toPath);
        } catch (IOException ex) {
            Logger.getLogger(ImageSizer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *保存一个图片到指定高宽
     * @param bimg 源图片
     * @param width 生成图片宽度
     * @param height 生成图片高度
     * @param toPath 生成图片路径
     * @throws java.io.IOException
     */
    public static void saveImg(BufferedImage bimg, Integer width, Integer height, String toPath) throws IOException {
        boolean proportion = true;//是否等比例缩放
        File toImg = new File(toPath);
        int newWidth;
        int newHeight;
        // 判断是否是等比缩放
        if (proportion) {
            // 为等比缩放计算输出的图片宽度及高度
            double rate1 = ((double) bimg.getWidth()) / (double) width + 0.1;
            double rate2 = ((double) bimg.getHeight()) / (double) height + 0.1;
            // 根据缩放比率大的进行缩放控制
            double rate = rate1 > rate2 ? rate1 : rate2;
            newWidth = (int) (((double) bimg.getWidth()) / rate);
            newHeight = (int) (((double) bimg.getHeight()) / rate);
        } else {
            newWidth = width; // 输出的图片宽度
            newHeight = height; // 输出的图片高度
        }
        if(newWidth > bimg.getWidth() || newHeight > bimg.getHeight()){
            return;
        }
        BufferedImage tag = new BufferedImage((int) newWidth, (int) newHeight, BufferedImage.TYPE_INT_RGB);
        tag.getGraphics().drawImage(bimg.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH), 0, 0, null);
        FileOutputStream out = new FileOutputStream(toImg);
        // JPEGImageEncoder可适用于其他图片类型的转换
        JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
        encoder.encode(tag);
        out.close();
        /*
        File toImg = new File(toPath);
        double ratio = 1.0;//缩放比例
        if ((bimg.getWidth() > width) || (bimg.getHeight() > height)) {
        ratio = Math.min(width * 1.0 / bimg.getWidth(), height * 1.0 / bimg.getHeight());
        }
        Image timg = bimg.getScaledInstance(width, height, BufferedImage.SCALE_SMOOTH);
        AffineTransformOp op = new AffineTransformOp(AffineTransform.getScaleInstance(ratio, ratio), null);
        timg = op.filter(bimg, null);
        ImageIO.write((BufferedImage) timg, toPath.substring(toPath.lastIndexOf(".") + 1), toImg);*/
    }
}
