package miscellaneous.utils.image;

import static java.lang.Math.abs;
import static java.lang.Math.cos;
import static java.lang.Math.pow;
import static java.lang.Math.sin;
import static miscellaneous.utils.math.MathUtils.isBetween;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.stream.Stream;

import javafx.util.Pair;

import javax.imageio.ImageIO;

import miscellaneous.utils.files.FileUtils;

public class RotateImage {
	private static final String OUTPUT_DIR = "src/" + FileUtils.getPathToPackage(RotateImage.class) + "data/";		
	public static void main(String[] args) throws IOException {
		double alpha = 60;
		int factor   = 4;

		BufferedImage im = ImageIO.read(new File(FileUtils.getResourceAbsolutePath("data/test2.jpg",RotateImage.class)));
		ImageIO.write(rotateImage(im,alpha),"jpg",new File(OUTPUT_DIR + String.format("result2_%d.jpg",(int) alpha)));
//		ImageIO.write(rotateImageHD(im,alpha,factor),"jpg",new File(OUTPUT_DIR + String.format("result2_%d_hd.jpg",(int) alpha)));
	}
	
    private static RenderedImage rotateImageHD(BufferedImage im, double alpha, int factor) {
    	BufferedImage resizedImage = new BufferedImage(im.getWidth()*factor,im.getHeight()*factor,BufferedImage.TYPE_INT_RGB);
    	for (int i=0 ; i<resizedImage.getWidth() ; i++)
    		for (int j=0 ; j<resizedImage.getHeight() ; j++) 
    			resizedImage.setRGB(i,j,im.getRGB(i/factor,j/factor));
    	return rotateImage(resizedImage,alpha);
	}

	private static BufferedImage rotateImage(BufferedImage im, double alpha) {
		alpha = degToRad(alpha);
		
		// compute the dimension of the new image
		double cos = cos(alpha)                     , sin = sin(alpha);
		int    w   = im.getWidth()                  , h   = im.getHeight();
		int    W   = (int) (abs(w*cos) + abs(h*sin)), H   = (int) (abs(h*cos) + abs(w*sin));
		
		BufferedImage res = new BufferedImage(W,H,BufferedImage.TYPE_INT_RGB);
		// iterate over the pixels of the new image, which coordinates are taken relatively to 
		// the center of the new image
		for (int i=-W/2 ; i<W/2 ; i++)
			for (int j=-H/2 ; j<H/2 ; j++) {
				// apply the inverse rotation to find the point of the original image that falls
				// on (i,j) after the rotation, and translate the resulting point to get its
				// coordinates in the original image
				double dx =  i*cos + j*sin + w/2;
				double dy = -i*sin + j*cos + h/2;
				
				// find the discrete point that corresponds (dx,dy) the best in the least square sense
				int cx = (int) Math.ceil (dx), cy = (int) Math.ceil (dy);
				int fx = (int) Math.floor(dx), fy = (int) Math.floor(dy);
				
				int ii = i + W/2, jj = j + H/2;
				Stream.of(new Point(cx,cy),new Point(cx,fy),new Point(fx,cy),new Point(fx,fy))
					.distinct()
					.filter(p -> isBetween(0,p.x,w) && isBetween(0,p.y,h))
					.map(p -> new Pair<>(p,pow(p.x - dx,2) + pow(p.y - dy,2)))
					.min((x,y) -> Double.compare(x.getValue(),y.getValue()))
					.ifPresent(kv -> {
						Point bestMatch = kv.getKey();
						res.setRGB(ii,jj,im.getRGB(bestMatch.x,bestMatch.y));
					});
			}
		return res;
	}

	private static double degToRad(double alpha) {
		return (alpha % 361)*Math.PI/180;
	}
}
