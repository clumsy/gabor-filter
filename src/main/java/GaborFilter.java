import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;

/**
 * The class represents Gabor Filter implementation
 *
 * @author Alexander Jipa
 * @version 0.1, 08/28/2011
 */
public class GaborFilter {
   private static final double DEFAULT_ORIENTATION = 0;
   private static final double DEFAULT_WAVE_LENGTH = 1;
   private static final double DEFAULT_PHASE_OFFSET = 0;
   private static final double DEFAULT_ASPECT_RATIO = 0.5;
   private static final double DEFAULT_BANDWIDTH = 1;
   private static final int DEFAULT_WIDTH = 5;
   private static final int DEFAULT_HEIGHT = 5;


   private static final double MIN_PHASE_OFFSET = -2*Math.PI;
   private static final double MAX_PHASE_OFFSET = 2*Math.PI;

   private static final double MIN_ORIENTATION = -2*Math.PI;
   private static final double MAX_ORIENTATION = 2*Math.PI;

   private static final double MIN_ASPECT_RATIO = 0;
   private static final double MAX_ASPECT_RATIO = 1;

   private double orientation = DEFAULT_ORIENTATION;
   private double waveLength = DEFAULT_WAVE_LENGTH;
   private double phaseOffset = DEFAULT_PHASE_OFFSET;
   private double aspectRatio = DEFAULT_ASPECT_RATIO;
   private double bandwidth = DEFAULT_BANDWIDTH;

   private int width;
   private int height;

   public GaborFilter() {
      this(DEFAULT_WIDTH, DEFAULT_HEIGHT);
   }

   public GaborFilter(int width, int height) {
      this(DEFAULT_WIDTH, DEFAULT_HEIGHT, DEFAULT_WAVE_LENGTH);
   }

   public GaborFilter(int width, int height, double waveLength) {
      this(DEFAULT_WIDTH, DEFAULT_HEIGHT, DEFAULT_WAVE_LENGTH, DEFAULT_ORIENTATION);
   }

   public GaborFilter(int width, int height, double waveLength, double orientation) {
      this(DEFAULT_WIDTH, DEFAULT_HEIGHT, DEFAULT_WAVE_LENGTH, DEFAULT_ORIENTATION, DEFAULT_PHASE_OFFSET);
   }

   public GaborFilter(int width, int height, double waveLength, double orientation, double phaseOffset) {
      this(DEFAULT_WIDTH, DEFAULT_HEIGHT, DEFAULT_WAVE_LENGTH, DEFAULT_ORIENTATION, DEFAULT_PHASE_OFFSET, DEFAULT_ASPECT_RATIO);
   }

   public GaborFilter(int width, int height, double waveLength, double orientation, double phaseOffset, double aspectRatio) {
      this(DEFAULT_WIDTH, DEFAULT_HEIGHT, DEFAULT_WAVE_LENGTH, DEFAULT_ORIENTATION, DEFAULT_PHASE_OFFSET, DEFAULT_ASPECT_RATIO, DEFAULT_BANDWIDTH);
   }

   public GaborFilter(int width, int height, double waveLength, double orientation, double phaseOffset, double aspectRatio, double bandwidth) {
      this.width = width;
      this.height = height;
      this.waveLength = waveLength;
      this.orientation = orientation;
      this.phaseOffset = phaseOffset;
      this.aspectRatio = aspectRatio;
      this.bandwidth = bandwidth;
   }

   public double getOrientation() {
      return orientation;
   }

   public void setOrientation(double orientation) {
      if(orientation <= MAX_ORIENTATION && orientation >= MIN_ORIENTATION) {
         this.orientation = orientation;
      } else {
         System.out.println("The Orientation should be in the range [" + MIN_ORIENTATION + "; " + MAX_ORIENTATION +"]");
      }
   }

   public double getWaveLength() {
      return waveLength;
   }

   public void setWaveLength(double waveLength) {
      if(waveLength > 0) {
         this.waveLength = waveLength;
      } else {
         System.out.println("The Wave Length should be a positive number");
      }
   }

   public double getPhaseOffset() {
      return phaseOffset;
   }

   public void setPhaseOffset(double phaseOffset) {
      if(phaseOffset <= MAX_PHASE_OFFSET && phaseOffset >= MIN_PHASE_OFFSET) {
         this.phaseOffset = phaseOffset;
      } else {
         System.out.println("The Phase Offset should be in the range [" + MIN_PHASE_OFFSET + "; " + MAX_PHASE_OFFSET +"]");
      }
   }

   public double getAspectRatio() {
      return aspectRatio;
   }

   public void setAspectRatio(double aspectRatio) {
      if(aspectRatio <= MAX_ASPECT_RATIO && aspectRatio >= MIN_ASPECT_RATIO) {
         this.aspectRatio = aspectRatio;
      } else {
         System.out.println("The Aspect Ratio should be in the range [" + MIN_ASPECT_RATIO + "; " + MAX_ASPECT_RATIO +"]");
      }
   }

   public double getBandwidth() {
      return bandwidth;
   }

   public void setBandwidth(double bandwidth) {
      this.bandwidth = bandwidth;
   }

   private static double calculateSigma(double waveLength, double bandwidth) {
      return waveLength*Math.sqrt(Math.log(2)/2)*(Math.pow(2, bandwidth) + 1)/((Math.pow(2, bandwidth) - 1)*Math.PI);
   }

   public int getWidth() {
      return width;
   }

   public void setWidth(int width) {
      this.width = width;
   }

   public int getHeight() {
      return height;
   }

   public void setHeight(int height) {
      this.height = height;
   }

   public ConvolveOp getConvolveOp() {
      float[] data = new float[width*height];
      for(int x = 1; x <= width; x++) {
         for(int y = 1; y <= height; y++) {
            double x1 = x*Math.cos(orientation) + y*Math.sin(orientation);
            double y1 = -x*Math.sin(orientation) + y*Math.cos(orientation);
            data[x + y - 2] = (float)(Math.exp(-(Math.pow(x1, 2) + Math.pow(aspectRatio, 2)*Math.pow(y1, 2))/(2*Math.pow(calculateSigma(waveLength, bandwidth), 2)))*Math.cos(2*Math.PI*x1/waveLength + phaseOffset));
         }
      }
      Kernel kernel = new Kernel(width, height, data);
      return new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
   }

   // TODO: Multiple orientations option
}