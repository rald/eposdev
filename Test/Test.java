import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Base64;
import javax.imageio.ImageIO;

class Test {

	public static void main(String[] args) throws Exception {

		BufferedImage bi1 = ImageIO.read( new File("products/oishi.png") );
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ImageIO.write(bi1, "png", bos );
		byte [] data = bos.toByteArray();

		byte[] encodedBytes = Base64.getEncoder().encode(data);
		System.out.println("encodedBytes " + new String(encodedBytes));
		byte[] decodedBytes = Base64.getDecoder().decode(encodedBytes);
		System.out.println("decodedBytes " + new String(decodedBytes));

		ByteArrayInputStream bis = new ByteArrayInputStream(decodedBytes);
		BufferedImage bi2=ImageIO.read(bis);
		ImageIO.write(bi2,"png",new File("oishi.png"));
	}
}
