import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * The test class for GaborFilter class
 */
public class GaborFilterTest {

   @Test
   public void testImage() throws IOException {
      File file = new File("./src/test/resources/gaborred-marx.jpg");
      Image image = ImageIO.read(new File("./src/test/resources/marx.jpg"));

      BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_RGB);
      Graphics g = bufferedImage.getGraphics();
      g.drawImage(image, 0, 0, null);

      BufferedImage gaborredImage = new BufferedImage(bufferedImage.getWidth(), bufferedImage.getHeight(), BufferedImage.TYPE_INT_RGB);
      new GaborFilter(10, 10, 8, 0, Math.PI, 0.5).getConvolveOp().filter(bufferedImage, gaborredImage);
      ImageIO.write(gaborredImage, "png", file);
   }
}