//
//package renderer;
//import java.awt.image.BufferedImage;
//import java.awt.image.WritableRaster;
//import java.util.Hashtable;
//public class Supersampling {
//    /**
//     * Constructor for the Supersampling object, by width,
//     * height and num of additional rays added in renderer
//     * @param _outW
//     * @param _outH
//     * @param samples
//     */
//    public Supersampling(int _outW, int _outH, int samples) {
//        outWidth = _outW*2;
//        outHeight = _outH*2;
//        nSamples = samples;
//    }
//
//    /**
//     * Function that makes the supersampling effect by making the average color of supersampling^2
//     * pixels and saving it to the new image
//     * @param image
//     * @return
//     */
//    public BufferedImage superSamplingImprovement(BufferedImage image) {
//        BufferedImage output = new BufferedImage(image.getColorModel(), image.getColorModel().createCompatibleWritableRaster(outWidth, outHeight), false, new Hashtable<String, Object>());
//        WritableRaster sourceRaster = image.getRaster();
//        WritableRaster outRaster = output.getRaster();
//        int sourceNumBands = sourceRaster.getNumBands();
//
//        for(int x = 0; x < outRaster.getWidth(); x++) {
//            for(int y = 0; y < outRaster.getHeight(); y++) {
//                double[] newValues = new double[sourceNumBands];
//
//                for(int i = 0; i < nSamples; i++) {
//                    for(int j = 0; j < nSamples; j++) {
//                        for(int k = 0; k < sourceNumBands; k++) {
//
//                           // System.out.println("x*_samples+i= " + x*_samples+i +" i= "+i);
//                           // System.out.println("y*_samples+j= " + y*_samples+j +" j= "+j);
//                           // System.out.println("*****************************************");
//                            try {
//                                newValues[k] += sourceRaster.getSample(x * nSamples + i, y * nSamples + j, k);
//
//                            }
//                            catch (Exception ex){
//                                    System.out.println("width = " + sourceRaster.getWidth());
//                            System.out.println("height= "+ sourceRaster.getHeight());
//                            System.out.println("samples= "+ nSamples);
//                            //System.out.println("SystemModelTranslateY" + sourceRaster.getSys);
//                            throw ex;
//                            }
//                        }
//                    }
//                }
//
//                for(int i = 0; i < newValues.length; i++) {
//                    newValues[i] = newValues[i]/(nSamples * nSamples);
//                    outRaster.setSample(x, y, i, newValues[i]);
//                }
//
//
//            }
//        }
//
//        return output;
//    }
//
//    private int outWidth, outHeight, nSamples;
//}
