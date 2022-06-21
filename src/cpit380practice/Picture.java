package cpit380practice;

import java.awt.*;
import java.awt.font.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.text.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import javax.swing.JOptionPane;
//------------------------------------------------------------------------------

/**
 * A class that represents a picture. This class inherits from SimplePicture and
 * allows the student to add functionality to the Picture class.
 *
 * Copyright Georgia Institute of Technology 2004
 *
 * @author Barbara Ericson ericson@cc.gatech.edu
 */
public class Picture extends SimplePicture {
    ///////////////////// constructors /////////////////////////////////

    /**
     * Constructor that takes no arguments
     */
    public Picture() {
        /* not needed but use it to show students the implicit call to
         * super() child constructors always call a parent constructor
         */
        super();
    }

    /**
     * Constructor that takes a file name and creates the picture
     *
     * @param fileName the name of the file to create the picture from
     */
    public Picture(String fileName) {
        // let the parent class handle this fileName
        super(fileName);
    }

    /**
     * Constructor that takes the width and height
     *
     * @param width the width of the desired picture
     * @param height the height of the desired picture
     */
    public Picture(int width, int height) {
        // let the parent class handle this width and height
        super(width, height);
    }

    /**
     * Constructor that takes a width and height for a new picture and sets the
     * color for the new picture
     *
     * @param width the width of the desired picture
     * @param height the height of the desired picture
     * @param theColor the color for the background
     */
    public Picture(int width, int height, Color theColor) {
        // let the parent class handle these parameters
        super(width, height, theColor);
    }

    /**
     * Constructor that takes a picture and creates a copy of that picture
     */
    public Picture(Picture copyPicture) {
        // let the parent class do the copy
        super(copyPicture);
    }

    ////////////////////// methods /////////////////////////////////////
    /**
     * Method to return a string with information about this picture.
     *
     * @return a string with information about the picture such as fileName,
     * height, and width.
     */
    public String toString() {
        String output = "Picture, filename " + this.getFileName()
                + " height " + this.getHeight()
                + " width " + this.getWidth();
        return output;

    }

    public void makeSunset(double factor) {
        Pixel[] pixelArray = this.getPixels();
        Pixel pixel = null;
        int value = 0;
        int i = 0;

        // loop through all the pixels
        while (i < pixelArray.length) {
            // get the current pixel
            pixel = pixelArray[i];

            // change the blue value
            value = pixel.getBlue();
            pixel.setBlue((int) (value * factor / 100));

            // change the green value
            value = pixel.getGreen();
            pixel.setGreen((int) (value * factor / 100));

            // increment the index
            i++;
        }
    }

    public void modifyTowards(Picture finalPict, int index, double dist) {
        Pixel[] pixelArray = this.getPixels();
        Pixel[] finalPixelArray = finalPict.getPixels();
        Pixel currPixel = null;
        Pixel finalPixel = null;
        double redDist, blueDist, greenDist;
        Color pictColor = null;

        // loop through the array of pixels
        for (int i = 0; i < pixelArray.length; i++) {
            currPixel = pixelArray[i];
            finalPixel = finalPixelArray[i];
            redDist = finalPixel.getRed() - currPixel.getRed();
            blueDist = finalPixel.getBlue() - currPixel.getBlue();
            greenDist = finalPixel.getGreen() - currPixel.getGreen();
            pictColor = new Color((int) (index / dist * redDist + currPixel.getRed()),
                    (int) (index / dist * greenDist + currPixel.getGreen()),
                    (int) (index / dist * blueDist + currPixel.getBlue()));
            currPixel.setColor(pictColor);
        }
    }

    public void posterize() {
        Pixel pixel = null;
        int redValue = 0;
        int greenValue = 0;
        int blueValue = 0;

        // loop through the pixels
        for (int x = 0; x < this.getWidth(); x++) {
            for (int y = 0; y < this.getHeight(); y++) {

                // get the current pixel and colors
                pixel = this.getPixel(x, y);
                redValue = pixel.getRed();
                greenValue = pixel.getGreen();
                blueValue = pixel.getBlue();

                // check for red range and change color
                if (redValue < 64) {
                    redValue = 31;
                } else if (redValue < 128) {
                    redValue = 95;
                } else if (redValue < 192) {
                    redValue = 159;
                } else {
                    redValue = 223;
                }

                // check for green range
                if (greenValue < 64) {
                    greenValue = 31;
                } else if (greenValue < 128) {
                    greenValue = 95;
                } else if (greenValue < 192) {
                    greenValue = 159;
                } else {
                    greenValue = 223;
                }

                // check for blue range
                if (blueValue < 64) {
                    blueValue = 31;
                } else if (blueValue < 128) {
                    blueValue = 95;
                } else if (blueValue < 192) {
                    blueValue = 159;
                } else {
                    blueValue = 223;
                }

                // set the colors
                pixel.setRed(redValue);
                pixel.setGreen(greenValue);
                pixel.setBlue(blueValue);
            }
        }
    }

    public void copy(Picture source, int x, int y) {
        // get the graphics object
        Graphics g = this.getGraphics();

        // copy the image
        g.drawImage(source.getImage(), x, y, null);
    }

    public Picture scale(double xFactor, double yFactor) {
        // set up the scale tranform
        AffineTransform scaleTransform = new AffineTransform();
        scaleTransform.scale(xFactor, yFactor);

        // create a new picture object that is the right size
        Picture result = new Picture((int) (getWidth() * xFactor),
                (int) (getHeight() * yFactor));

        // get the graphics 2d object to draw on the result
        Graphics graphics = result.getGraphics();
        Graphics2D g2 = (Graphics2D) graphics;

        // draw the current image onto the result image scaled
        g2.drawImage(this.getImage(), scaleTransform, null);

        return result;
    }

    public void drawString(String text, int x, int y,
            Font font, Color color) {

        // get the graphics object
        Graphics g = this.getGraphics();

        // set the color
        g.setColor(color);

        // set the font
        g.setFont(font);

        // draw the string
        g.drawString(text, x, y);
    }

    public void drawString(String text, int x, int y) {

        this.drawString(text, x, y,
                new Font("Arial", Font.BOLD, 24),
                Color.BLACK);
    }

    public void drawHorizontalCenteredString(String text,
            int y) {

        // get the graphics object
        Graphics g = this.getGraphics();

        // create the font object
        Font font = new Font("Arial", Font.BOLD, 24);

        // set the color
        g.setColor(Color.black);

        // set the font
        g.setFont(font);

        // get the font metrics
        FontMetrics fontMetrics = g.getFontMetrics();

        // get the width of the string
        int strWidth = fontMetrics.stringWidth(text);

        // calculate the center of the  picture
        int center = (int) (this.getWidth() * 0.5);

        // draw the string centered in x
        g.drawString(text,
                center - (int) (strWidth * 0.5),
                y);
    }

    public void chromakey(Picture newBg, Color color, double dist) {
        Pixel currPixel = null;
        Pixel newPixel = null;

        // loop through the columns
        for (int x = 0; x < getWidth(); x++) {

            // loop through the rows
            for (int y = 0; y < getHeight(); y++) {

                // get the current pixel
                currPixel = this.getPixel(x, y);

                /* if the color at the current pixel is mostly blue
                 * (blue value is greater than red and green combined),
                 * then use the new background color
                 */
                double currDist = currPixel.colorDistance(color);
                if (currDist <= dist) {
                    newPixel = newBg.getPixel(x, y);
                    currPixel.setColor(newPixel.getColor());
                }
            }
        }
    }

    public void swapBackground(Picture oldBackground,
            Picture newBackground,
            double threshold) {
        Pixel currPixel = null;
        Pixel oldPixel = null;
        Pixel newPixel = null;

        // loop through the columns
        for (int x = 0; x < getWidth(); x++) {

            // loop through the rows
            for (int y = 0; y < getHeight(); y++) {

                // get the current pixel and old background pixel
                currPixel = this.getPixel(x, y);
                oldPixel = oldBackground.getPixel(x, y);

                /* if the distance between the current pixel color and
                 * the old background pixel color is less than the
                 * threshold then swap in the new background pixel
                 */
                double distance
                        = currPixel.colorDistance(oldPixel.getColor());
                if (distance < threshold) {
                    newPixel = newBackground.getPixel(x, y);
                    currPixel.setColor(newPixel.getColor());
                }
            }
        }
    }

    public void lighten(Color compColor, double distance,
            int numTimes) {
        Pixel pixel = null;
        Color color = null;

        // loop through the columns (x direction)
        for (int x = 0; x < getWidth(); x++) {
            // loop through the rows (y direction)
            for (int y = 0; y < getHeight(); y++) {
                // get pixel at the x and y location
                pixel = getPixel(x, y);

                // get the distance to the passed color
                double colorDist = pixel.colorDistance(compColor);

                /* if the color distance is less than or equal to
                 * the passed distance then lighten the pixel
                 */
                if (colorDist <= distance) {
                    color = pixel.getColor();
                    for (int i = 0; i < numTimes; i++) {
                        color.brighter();
                    }
                    pixel.setColor(color);
                }
            }
        }
    }

    public void changeRedAndGreen(double redMult,
            double greenMult) {
        Pixel[] pixelArray = this.getPixels();
        Pixel pixel = null;
        int value = 0;
        int index = 0;

        // loop through all the pixels
        while (index < pixelArray.length) {
            // get the current pixel
            pixel = pixelArray[index];

            // change the red value
            value = pixel.getRed();
            pixel.setRed((int) (value * redMult));

            // change the green value
            value = pixel.getGreen();
            pixel.setGreen((int) (value * greenMult));

            // increment the index
            index++;
        }
    }

    public void posterize(int numLevels) {
        Pixel pixel = null;
        int redValue = 0;
        int greenValue = 0;
        int blueValue = 0;
        int increment = (int) (256.0 / numLevels);
        int bottomValue, topValue, middleValue = 0;

        // loop through the pixels
        for (int x = 0; x < this.getWidth(); x++) {
            for (int y = 0; y < this.getHeight(); y++) {

                // get the current pixel and colors
                pixel = this.getPixel(x, y);
                redValue = pixel.getRed();
                greenValue = pixel.getGreen();
                blueValue = pixel.getBlue();

                // loop through the number of levels
                for (int i = 0; i < numLevels; i++) {
                    // compute the bottom, top, and middle values
                    bottomValue = i * increment;
                    topValue = (i + 1) * increment;
                    middleValue = (int) ((bottomValue + topValue - 1) / 2.0);

                    // check if current values are in current range and if so
                    // set them to the middle value
                    if (bottomValue <= redValue && redValue < topValue) {
                        pixel.setRed(middleValue);
                    }
                    if (bottomValue <= greenValue && greenValue < topValue) {
                        pixel.setGreen(middleValue);
                    }
                    if (bottomValue <= blueValue && blueValue < topValue) {
                        pixel.setBlue(middleValue);
                    }
                }
            }
        }
    }

    public Picture blendPictures(Picture sourcePict, double sourceAlpha) {
        int resultWidth = Math.max(sourcePict.getWidth(),
                this.getWidth());
        int resultHeight = Math.max(sourcePict.getHeight(),
                this.getHeight());

        // create new picture
        Picture result = new Picture(resultWidth, resultHeight);

        // get the Graphics2D object
        Graphics g = result.getGraphics();
        Graphics2D g2 = (Graphics2D) g;

        // set up source picture blend and draw it
        g2.setComposite(
                AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
                        (float) sourceAlpha));
        g2.drawImage(sourcePict.getImage(), 0, 0,
                sourcePict.getWidth(), sourcePict.getHeight(), null);

        // set up orig picture blend and draw it
        g2.setComposite(
                AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
                        (float) (1.0 - sourceAlpha)));
        g2.drawImage(this.getImage(), 0, 0, this.getWidth(),
                this.getHeight(), null);

        return result;
    }

    public Picture rotate(int degrees) {
        // set up the rotation tranform
        AffineTransform rotateTransform = new AffineTransform();
        rotateTransform.rotate(Math.toRadians(degrees));
        Rectangle2D rect
                = getTransformEnclosingRect(rotateTransform);

        // create a new picture object big enough to hold the result no
        // matter what the rotation is
        Picture result = new Picture((int) (Math.ceil(rect.getWidth())),
                (int) (Math.ceil(rect.getHeight())));

        // get the graphics 2d object from the result
        Graphics graphics = result.getGraphics();
        Graphics2D g2 = (Graphics2D) graphics;

        // save the current transformation and set-up to center the
        // rotated image
        AffineTransform savedTrans = g2.getTransform();
        AffineTransform centerTrans = new AffineTransform();
        centerTrans.translate(0 - rect.getX(), 0 - rect.getY());
        g2.setTransform(centerTrans);

        // draw the current image onto the result image rotated
        g2.drawImage(this.getImage(), rotateTransform, null);

        // reset g2 transformation to the saved one
        g2.setTransform(savedTrans);

        return result;
    }
    //---------------------------------------------------

    public static int otsuTreshold(Picture original) {

        int[] histogram = imageHistogram(original);
        int total = original.getHeight() * original.getWidth();

        float sum = 0;
        for (int i = 0; i < 256; i++) {
            sum += i * histogram[i];
        }

        float sumB = 0;
        int wB = 0;
        int wF = 0;

        float varMax = 0;
        int threshold = 0;

        for (int i = 0; i < 256; i++) {
            wB += histogram[i];
            if (wB == 0) {
                continue;
            }
            wF = total - wB;

            if (wF == 0) {
                break;
            }

            sumB += (float) (i * histogram[i]);
            float mB = sumB / wB;
            float mF = (sum - sumB) / wF;

            float varBetween = (float) wB * (float) wF * (mB - mF) * (mB - mF);

            if (varBetween > varMax) {
                varMax = varBetween;
                threshold = i;
            }
        }

        return threshold;

    }
// Return histogram of grayscale image

    public static int[] imageHistogram(Picture input) {
        Pixel pixel = null;
        int[] histogram = new int[256];

        for (int i = 0; i < histogram.length; i++) {
            histogram[i] = 0;
        }

        for (int i = 0; i < input.getWidth(); i++) {
            for (int j = 0; j < input.getHeight(); j++) {
                pixel = input.getPixel(j, j);
                int red = pixel.getColor().getRed();
                histogram[red]++;
            }
        }
        return histogram;
    }
    //--------------------------------------------------------------------

    public void mirrorHorizontalBottomToTop() {
        int height = this.getHeight();
        int mirrorPoint = height / 2;
        Pixel topPixel = null;
        Pixel bottomPixel = null;

        // loop through the columns
        for (int x = 0; x < getWidth(); x++) {
            // loop from 1 to just before the mirror point
            for (int y = 0; y < mirrorPoint; y++) {
                topPixel = getPixel(x, y);
                bottomPixel = getPixel(x, height - 1 - y);
                topPixel.setColor(bottomPixel.getColor());
            }
        }
    }

    /**
     * Mirror diagonal from 0,0 to width-1,height-1
     */
    public void ReflectD1_T2B() {
        Pixel leftPixel = null;
        Pixel rightPixel = null;
        for (int y = 1; y < this.getHeight(); y++) {
            for (int x = 0; x < y; x++) {
                rightPixel = this.getPixel(x, y);
                leftPixel = this.getPixel(y, x);
                rightPixel.setColor(leftPixel.getColor());
            }
        }
    }

    public void ReflectD1_B2T() {
        Pixel leftPixel = null;
        Pixel rightPixel = null;
        for (int y = 1; y < this.getHeight(); y++) {
            for (int x = 0; x < y; x++) {
                leftPixel = this.getPixel(x, y);
                rightPixel = this.getPixel(y, x);
                rightPixel.setColor(leftPixel.getColor());
            }
        }
    }

    /**
     * Method to mirror around a diagonal line from width-1,0 to 0,height-1
     */
    public void ReflectD2_T2B() {
        Pixel leftPixel = null;
        Pixel rightPixel = null;
        int height = this.getHeight();
        for (int y = height - 2; y >= 0; y--) {
            for (int x = 0; x < height - 1 - y; x++) {
                leftPixel = this.getPixel(x, y);
                rightPixel = this.getPixel(height - 1 - y, height - 1 - x);
                rightPixel.setColor(leftPixel.getColor());
            }
        }
    }

    public void ReflectD2_B2T() {
        Pixel leftPixel = null;
        Pixel rightPixel = null;
        int height = this.getHeight();
        for (int y = height - 2; y >= 0; y--) {
            for (int x = 0; x < height - 1 - y; x++) {
                rightPixel = this.getPixel(x, y);
                leftPixel = this.getPixel(height - 1 - y, height - 1 - x);
                rightPixel.setColor(leftPixel.getColor());
            }
        }
    }

    /**
     * Method to allow the user to pick a file name and show a picture. This
     * method will also return the created picture object.
     *
     * @return the picture object created from the picked file name
     */
    public static Picture pickAndShow() {
        String fileName = FileChooser.pickAFile();
        Picture picture = new Picture(fileName);
        picture.show();
        return picture;
    }

    /**
     * Method to show a specific picture where the file name to show is
     * specified in the method. To change which picture you want to show you can
     * edit the method and recompile it.
     *
     * @return the created picture object
     */
    public static Picture showSpecificPicture() {
        String myFile = "C:/intro-prog-java/mediasources/katie.jpg";
        Picture myPicture = new Picture(myFile);
        myPicture.show();
        return myPicture;
    }

    /**
     * Method to show a picture created from a passed file name
     *
     * @param fileName the name of the file to create the picture from
     * @return the created picture object
     */
    public static Picture showNamed(String fileName) {
        Picture myPicture = new Picture(fileName);
        myPicture.show();
        return myPicture;
    }

    public void copy(Picture sourcePicture,
            int startX, int startY,
            int endX, int endY,
            int targetStartX, int targetStartY) {
        Pixel sourcePixel = null;
        Pixel targetPixel = null;

        // loop through the x values
        for (int x = startX, tx = targetStartX;
                x < endX && x < sourcePicture.getWidth()
                && tx < this.getWidth();
                x++, tx++) {
            int i = 0;
            // loop through the y values
            for (int y = startY, ty = targetStartY;
                    y < endY && y < sourcePicture.getHeight()
                    && ty < this.getHeight();
                    y++, ty++) {
                sourcePixel = sourcePicture.getPixel(x, y);
                targetPixel = this.getPixel(tx, ty);
                targetPixel.setColor(sourcePixel.getColor());
            }
        }
//        return targetPixel;
    }

    public Picture rotateRight() {
        Picture target = new Picture(this.getHeight(), this.getWidth());
        Pixel sourcePixel = null;
        Pixel targetPixel = null;

        // loop through the columns
        for (int sourceX = 0; sourceX < this.getWidth(); sourceX++) {
            // loop through the rows
            for (int sourceY = 0; sourceY < this.getHeight(); sourceY++) {
                // set the target pixel color to the source pixel color

                // make 2 loop to move on width and hight
                // 1- take the pixel in the srcPic on (x,y)
                // 2- take the pixel in the targetPic but swap between x and y after that use the formula of Rright (H-1-y,x)
                // 3- in the pixel of the target we change the color with color of the pixel src
                sourcePixel = this.getPixel(sourceX, sourceY);
                targetPixel = target.getPixel(this.getHeight() - 1 - sourceY, sourceX);
                targetPixel.setColor(sourcePixel.getColor());
            }
        }
        return target;
    }

    // this method is in picture class
    public Picture gray2Binary(int threshold) {

        Picture target = new Picture(this.getWidth(), this.getHeight());
        int avar;
        for (int x = 0; x < this.getWidth(); x++) {
            for (int y = 0; y < this.getHeight(); y++) {
                avar = (int) Math.abs(this.getPixel(x, y).getAverage());
                if (avar < threshold) {
                    target.getPixel(x, y).setColor(Color.BLACK);
                }
            }
        }
        return target;
    }

    public Picture scaleUp(int numTimes) {
        Picture targetPicture = new Picture(this.getWidth() * numTimes, this.getHeight() * numTimes);
        Pixel sourcePixel = null;
        Pixel targetPixel = null;
        int targetX = 0;
        int targetY = 0;

        // loop through the source picture columns
        for (int sourceX = 0; sourceX < this.getWidth(); sourceX++) {
            // loop through the source picture rows
            for (int sourceY = 0; sourceY < this.getHeight(); sourceY++) {
                // get the source pixel
                sourcePixel = this.getPixel(sourceX, sourceY);

                // loop copying to the target y
                for (int indexY = 0; indexY < numTimes; indexY++) {
                    // loop copying to the target x
                    for (int indexX = 0; indexX < numTimes; indexX++) {
                        targetX = sourceX * numTimes + indexX;
                        targetY = sourceY * numTimes + indexY;
                        targetPixel = targetPicture.getPixel(targetX, targetY);
                        targetPixel.setColor(sourcePixel.getColor());
                    }
                }
            }
        }

        return targetPicture;

    }

    public Picture ourScaleDown(int w, int h, int numTimes) {
        Picture targetPicture = new Picture(w / numTimes + 1, h / numTimes + 1);
        Pixel sourcePixel = null;
        Pixel targetPixel = null;
        for (int sourceX = 0, targetX = 0;
                sourceX < w;
                sourceX += numTimes, targetX++) {
            for (int sourceY = 0, targetY = 0; sourceY < h; sourceY += numTimes, targetY++) {
                sourcePixel = this.getPixel(sourceX, sourceY);
                targetPixel = targetPicture.getPixel(targetX, targetY);
                targetPixel.setColor(sourcePixel.getColor());
            }
        }
        return targetPicture;

    }

    public Picture ourScaleUp(int w, int h, int numTimes) {
        Picture targetPicture = new Picture(w * numTimes, h * numTimes);
        Pixel sourcePixel;
        Pixel targetPixel;
        int targetX = 0;
        int targetY = 0;

        for (int sourceX = 0; sourceX < w; sourceX++) {
            for (int sourceY = 0; sourceY < h; sourceY++) {
                sourcePixel = this.getPixel(sourceX, sourceY);
                for (int indexY = 0; indexY < numTimes; indexY++) {
                    for (int indexX = 0; indexX < numTimes; indexX++) {
                        targetX = sourceX * numTimes + indexX;
                        targetY = sourceY * numTimes + indexY;
                        targetPixel = targetPicture.getPixel(targetX, targetY);
                        targetPixel.setColor(sourcePixel.getColor());
                    }
                }
            }
        }
        return targetPicture;
    }

    public Picture scaleDown(int numTimes) {

        Picture targetPicture = new Picture(this.getWidth(), this.getHeight());
        Pixel sourcePixel = null;
        Pixel targetPixel = null;

        // loop through the source picture columns
        for (int sourceX = 0, targetX = 0; sourceX < targetPicture.getWidth(); sourceX += numTimes, targetX++) {

            for (int sourceY = 0, targetY = 0; sourceY < targetPicture.getHeight(); sourceY += numTimes, targetY++) {

                sourcePixel = this.getPixel(sourceX, sourceY);

                targetPixel = targetPicture.getPixel(targetX, targetY);

                targetPixel.setColor(sourcePixel.getColor());

            }
        }

        return targetPicture;

    }

    public void darken() {
        Pixel[] pixelArray = this.getPixels();
        Color color = null;
        Pixel pixel = null;

        // loop through all the pixels
        for (int i = 0; i < pixelArray.length; i++) {
            // get the current pixel
            pixel = pixelArray[i];

            // get the current color
            color = pixel.getColor();

            // get a darker color
            color = color.darker();

            // set the pixel color to the darker color
            pixel.setColor(color);
        }
    }

    public void rotate180() {
        Picture copy = new Picture(this);
        Pixel startPixel = null;
        Pixel endPixel = null;
        for (int y = 0; y < this.getHeight(); y++) {
            for (int x = 0; x < this.getWidth(); x++) {
                // make 2 loop to move on width and hight
                // 1- take the pixel in the srcPic on (x,y)
                // 2- take the pixel in the targetPic but swap between x and y after that use the formula of R 180 (w-1-x,H-1-y)
                // 3- in the pixel of the target we change the color with color of the pixel src
                startPixel = copy.getPixel(x, y);
                endPixel = this.getPixel(this.getWidth() - 1 - x, this.getHeight() - 1 - y);
                endPixel.setColor(startPixel.getColor());
            }
        }

    }

    public void lighten() {
        Pixel pixel = null;
        Color color = null;

        // loop through the columns (x direction)
        for (int x = 0; x < getWidth(); x++) {
            // loop through the rows (y direction)
            for (int y = 0; y < getHeight(); y++) {
                // get pixel at the x and y location
                pixel = getPixel(x, y);

                // get the current color
                color = pixel.getColor();

                // get a lighter color
                color = color.brighter();

                // set the pixel color to the lighter color
                pixel.setColor(color);
            }
        }

    }

    public int[] ourComputingHistogram() {

        int[] histo = new int[256];
        for (Pixel pixel : this.getPixels()) {
            int i = (int) pixel.getAverage();
            histo[i]++;
        }
        return histo;
    }

    public void makeSunset() {
        Pixel[] pixelArray = this.getPixels();
        Pixel pixel = null;
        int value = 0;
        int i = 0;

        // loop through all the pixels
        while (i < pixelArray.length) {
            // get the current pixel
            pixel = pixelArray[i];

            // change the blue value
            value = pixel.getBlue();
            pixel.setBlue((int) (value * 0.7));

            // change the green value
            value = pixel.getGreen();
            pixel.setGreen((int) (value * 0.7));

            // increment the index
            i++;
        }
    }

    public void grayscale() {
        Pixel[] pixelArray = this.getPixels();
        Pixel pixel = null;
        int intensity = 0;

        // loop through all the pixels
        for (int i = 0; i < pixelArray.length; i++) {
            // get the current pixel
            pixel = pixelArray[i];

            // compute the intensity of the pixel (average value)
            intensity = (int) ((pixel.getRed() + pixel.getGreen()
                    + pixel.getBlue()) / 3);

            // set the pixel color to the new color
            pixel.setColor(new Color(intensity, intensity, intensity));

        }

    }

    public int brightness(Picture p) {
        p.grayscale();
        Pixel P = null;
        int sum = 0;
        int index = 0;
        for (int x = 0; x < this.getWidth(); x++) {
            for (int y = 0; y < this.getHeight(); y++) {
                P = p.getPixel(x, y);
                double c = P.getAverage();
                sum += (int) c;
                index++;
            }
        }
        int brightness = sum / index;

        return brightness;
    }

    public void GaussianFilter3x3() {
        int x = this.getWidth();
        int y = this.getHeight();
        Picture Image = new Picture(x, y);
        Image.copy(this, 0, 0, x, y, 0, 0);
        double filter[][] = {{0.075, 0.125, 0.075},
        {0.125, 0.200, 0.125},
        {0.075, 0.125, 0.075}};
        for (int v = 1; v <= y - 2; v++) {
            for (int u = 1; u <= x - 2; u++) {
                double sumRed = 0, sumGreen = 0, sumBlue = 0;
                for (int j = -1; j <= 1; j++) {
                    for (int i = -1; i <= 1; i++) {
                        double c = filter[j + 1][i + 1];
                        int red = Image.getPixel(u + i, v + j).getRed();
                        sumRed += red * c;
                        int green = Image.getPixel(u + i, v + j).getGreen();
                        sumGreen += green * c;
                        int blue = Image.getPixel(u + i, v + j).getBlue();
                        sumBlue += blue * c;

                    }
                }
                int Red = (int) Math.round(sumRed);
                int Green = (int) Math.round(sumGreen);
                int Blue = (int) Math.round(sumBlue);
                this.getPixel(u, v).setColor(new Color(Red, Green, Blue));
            }
        }
    }//
    //

    public void sopelFilter() {

        int[][] Gx = {{-1, -2, -1}, {0, 0, 0}, {1, 2, 1}};
        int[][] Gy = {{-1, -0, 1}, {-2, 0, 2}, {-1, 0, 1}};
        int colorValue = 0;
        int[][] tamplet;

        int width = this.getWidth();
        int height = this.getHeight();

        tamplet = new int[width][height];
        this.grayscale();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                colorValue = this.getPixel(x, y).getRed();
                tamplet[x][y] = colorValue;

            }
        }

        for (int y = 1; y < height - 1; y++) {
            for (int x = 1; x < width - 1; x++) {
                int Tx = (Gx[0][0] * tamplet[x - 1][y - 1]) + (Gx[0][1] * tamplet[x][y - 1])
                        + (Gx[0][2] * tamplet[x + 1][y - 1]) + (Gx[1][0] * tamplet[x - 1][y])
                        + (Gx[1][1] * tamplet[x][y]) + (Gx[1][2] * tamplet[x + 1][y])
                        + (Gx[2][0] * tamplet[x - 1][y + 1]) + (Gx[2][1] * tamplet[x][y + 1])
                        + (Gx[2][2] * tamplet[x + 1][y + 1]);

                int Ty = (Gy[0][0] * tamplet[x - 1][y - 1]) + (Gy[0][1] * tamplet[x][y - 1])
                        + (Gy[0][2] * tamplet[x + 1][y - 1]) + (Gy[1][0] * tamplet[x - 1][y])
                        + (Gy[1][1] * tamplet[x][y]) + (Gy[1][2] * tamplet[x + 1][y])
                        + (Gy[2][0] * tamplet[x - 1][y + 1]) + (Gy[2][1] * tamplet[x][y + 1])
                        + (Gy[2][2] * tamplet[x + 1][y + 1]);

                int pixel = (int) Math.sqrt((Tx * Tx) + (Ty * Ty));
                if (pixel > 255) {
                    pixel = 255;
                } else if (pixel < 1) {
                    pixel = 0;
                }
                Color pix = new Color(pixel, pixel, pixel);
                this.getPixel(x, y).setColor(pix);

            }
        }
    }

    public void LaplacianFilter() {
        for (int x = 1, xSample = 0; x < this.getWidth() - 1; x++, xSample++) {
            for (int y = 1, ySample = 0; y < this.getHeight() - 1; y++, ySample++) {

                Pixel pixel1 = this.getPixel(x - 1, y - 1);
                Pixel pixel2 = this.getPixel(x - 1, y);
                Pixel pixel3 = this.getPixel(x - 1, y + 1);
                Pixel pixel4 = this.getPixel(x, y - 1);
                Pixel pixel5 = this.getPixel(x, y);
                Pixel pixel6 = this.getPixel(x, y + 1);
                Pixel pixel7 = this.getPixel(x + 1, y - 1);
                Pixel pixel8 = this.getPixel(x + 1, y);
                Pixel pixel9 = this.getPixel(x + 1, y + 1);

                int redValue
                        = (-1 * pixel1.getRed()) + (-1 * pixel2.getRed())
                        + (-1 * pixel3.getRed()) + (-1 * pixel4.getRed())
                        + (15 * pixel5.getRed()) + (-1 * pixel6.getRed())
                        + (-1 * pixel7.getRed()) + (-1 * pixel8.getRed())
                        + (-1 * pixel9.getRed());

                if (redValue < 0) {
                    redValue = 0;
                } else if (redValue > 255) {
                    redValue = 255;
                }

                Color newColor = new Color(redValue, redValue, redValue);
                this.getPixel(x, y).setColor(newColor);
            }
        }
//        if (FilterSize % 2 == 0) { // if number is even
//            return;
//        }
//
//        int sumR, sumG, sumB;
//
//        double filter[][] = {{-0.075, -0.125, -0.075},
//        {-0.125, 2.0, -0.125},
//        {-0.075, -0.125, -0.075}};
//
//        int start = (int) Math.floor(FilterSize / 2);
//
//        for (int i = start; i <= this.getWidth() - (start + 1); i++) {
//            for (int j = start; j <= this.getHeight() - (start + 1); j++) {
//                sumR = 0;
//                sumG = 0;
//                sumB = 0;
//                for (int k = -start; k <= start; k++) {
//                    for (int l = -start; l <= start; l++) {
//                        double c = filter[k + 1][l + 1];
//                        int red = this.getPixel(i + k, j + 1).getRed();
//                        sumR += red * c;
//                        int green = this.getPixel(i + k, j + 1).getGreen();
//                        sumG += green * c;
//                        int blue = this.getPixel(i + k, j + 1).getBlue();
//                        sumB += blue * c;
//                    }
//                }
//                int Red = (int) Math.min(FilterSize, Math.max(0, sumR));
//                int Green = (int) Math.min(FilterSize, Math.max(0, sumG));
//                int Blue = (int) Math.min(FilterSize, Math.max(0, sumB));
//
//                this.getPixel(i, j).setBlue(sumB);
//                this.getPixel(i, j).setGreen(sumG);
//                this.getPixel(i, j).setRed(sumR);
//            }
//        }
    }

    public void BoxFilter3x3() {
        int x = this.getWidth();
        int y = this.getHeight();
        Picture Image = new Picture(x, y);
        Image.copy(this, 0, 0, x, y, 0, 0);
        for (int v = 1; v <= y - 2; v++) {
            for (int u = 1; u <= x - 2; u++) {
                int sumRed = 0, sumGreen = 0, sumBlue = 0;
                for (int j = -1; j <= 1; j++) {
                    for (int i = -1; i <= 1; i++) {
                        int red = Image.getPixel(u + i, v + j).getRed();
                        sumRed += red;
                        int green = Image.getPixel(u + i, v + j).getGreen();
                        sumGreen += green;
                        int blue = Image.getPixel(u + i, v + j).getBlue();
                        sumBlue += blue;
                    }
                }
                int Red = (int) Math.round(sumRed / 9.0);
                int Green = (int) Math.round(sumGreen / 9.0);
                int Blue = (int) Math.round(sumBlue / 9.0);
                this.getPixel(u, v).setColor(new Color(Red, Green, Blue));
            }
        }
    }

    public void MinFilter() {

        int x = this.getWidth();
        int y = this.getHeight();
        Picture copy = new Picture(x, y);
        copy.copy(this, 0, 0, x, y, 0, 0);
        int Red[] = new int[9];
        int Green[] = new int[9];
        int Blue[] = new int[9];
        for (int v = 1; v <= y - 2; v++) {
            for (int u = 1; u <= x - 2; u++) {
                int k = 0;
                for (int j = -1; j <= 1; j++) {
                    for (int i = -1; i <= 1; i++) {
                        Red[k] = copy.getPixel(u + i, v + j).getRed();
                        Green[k] = copy.getPixel(u + i, v + j).getGreen();
                        Blue[k] = copy.getPixel(u + i, v + j).getBlue();
                        k++;
                    }
                }
                Arrays.sort(Red);
                Arrays.sort(Green);
                Arrays.sort(Blue);
                this.getPixel(u, v).setColor(new Color(Red[0], Green[0], Blue[0]));
            }
        }
    }

    public void MinFilter(int FilterSize) {
        if (FilterSize % 2 == 0) { // if number is even
            return;
        }
        int Red[] = new int[FilterSize * FilterSize];
        int Green[] = new int[FilterSize * FilterSize];
        int Blue[] = new int[FilterSize * FilterSize];
        int x = this.getWidth();
        int y = this.getHeight();
        Picture copy = new Picture(x, y);
        copy.copy(this, 0, 0, x, y, 0, 0);

        int start = (int) Math.floor(FilterSize / 2);

        for (int v = start; v <= y - (start + 1); v++) {
            for (int u = start; u <= x - (start + 1); u++) {
                int k = 0;
                for (int j = -start; j <= start; j++) {
                    for (int i = -start; i <= start; i++) {

                        Red[k] = copy.getPixel(u + i, v + j).getRed();
                        Green[k] = copy.getPixel(u + i, v + j).getGreen();
                        Blue[k] = copy.getPixel(u + i, v + j).getBlue();
                        k++;
                    }
                }
                Arrays.sort(Red);
                Arrays.sort(Green);
                Arrays.sort(Blue);
                this.getPixel(u, v).setColor(new Color(Red[0], Green[0], Blue[0]));
            }
        }

    }

    public void medianfilter() {

        //filter 3*3
        int[] pixel = new int[9];

        //go thru all the image
        for (int i = 1; i < this.getWidth() - 1; i++) {
            for (int j = 1; j < this.getHeight() - 1; j++) {

                //get the each pixel and it's color
                pixel[0] = this.getPixel(i - 1, j - 1).getRed();
                pixel[1] = this.getPixel(i - 1, j).getRed();
                pixel[2] = this.getPixel(i - 1, j + 1).getRed();
                pixel[3] = this.getPixel(i, j + 1).getRed();
                pixel[4] = this.getPixel(i + 1, j + 1).getRed();
                pixel[5] = this.getPixel(i + 1, j).getRed();
                pixel[6] = this.getPixel(i + 1, j - 1).getRed();
                pixel[7] = this.getPixel(i, j - 1).getRed();
                pixel[8] = this.getPixel(i, j).getRed();

                //sort the pixels
                Arrays.sort(pixel);

                //take middle pixel at (x,y) and set it to the picture
                this.getPixel(i, j).setColor(new Color(pixel[4], pixel[4], pixel[4]));

            }
        }
    }

    public void MedianWeightedFilter(int FilterSize) {
        if (FilterSize % 2 == 0) { // if number is even
            return;
        }

        // generating random weighted matrix
        int[][] WeightedMatrix = new int[FilterSize][FilterSize];
        for (int i = 0; i < FilterSize; i++) {
            for (int j = 0; j < FilterSize; j++) {
                WeightedMatrix[i][j] = 1 + (int) (Math.random() * 5); // 1 to 5
                System.out.print(WeightedMatrix[i][j] + ", ");
            }
        }

        int start = (int) Math.floor(FilterSize / 2);

        for (int i = start; i <= this.getWidth() - (start + 1); i++) {
            for (int j = start; j <= this.getHeight() - (start + 1); j++) {
                ArrayList<Integer> red = new ArrayList<>();
                ArrayList<Integer> green = new ArrayList<>();
                ArrayList<Integer> blue = new ArrayList<>();
                int x = 0;
                int x2 = 0;

                for (int k = -start; k <= start; k++) {
                    for (int l = -start; l <= start; l++) {

                        for (int m = 0; m < WeightedMatrix[x][x2]; m++) {
                            red.add(this.getPixel(k + i, l + j).getRed());
                            green.add(this.getPixel(k + i, l + j).getGreen());
                            blue.add(this.getPixel(k + i, l + j).getBlue());
                        }
                    }
                    x++;
                    x2++;
                }
                Collections.sort(red);
                Collections.sort(green);
                Collections.sort(blue);
                int middle = FilterSize * FilterSize / 2;

                if (red.size() % 2 == 0) { // array size is even - get avg of two middle pixels
                    int avgR = (red.get(middle) + red.get(middle - 1)) / 2;
                    int avgG = (green.get(middle) + green.get(middle - 1)) / 2;
                    int avgB = (blue.get(middle) + blue.get(middle - 1)) / 2;
                    this.getPixel(i, j).setColor(new Color(avgR, avgG, avgB));
                } else {
                    this.getPixel(i, j).setColor(new Color(red.get(middle), green.get(middle), blue.get(middle)));
                }
            }
        }
    }

    public void MaxFilter() {

        int x = this.getWidth();
        int y = this.getHeight();
        Picture copy = new Picture(x, y);
        copy.copy(this, 0, 0, x, y, 0, 0);
        int Red[] = new int[9];
        int Green[] = new int[9];
        int Blue[] = new int[9];
        for (int v = 1; v <= y - 2; v++) {
            for (int u = 1; u <= x - 2; u++) {
                int k = 0;
                for (int j = -1; j <= 1; j++) {
                    for (int i = -1; i <= 1; i++) {
                        Red[k] = copy.getPixel(u + i, v + j).getRed();
                        Green[k] = copy.getPixel(u + i, v + j).getGreen();
                        Blue[k] = copy.getPixel(u + i, v + j).getBlue();
                        k++;
                    }
                }
                Arrays.sort(Red);
                Arrays.sort(Green);
                Arrays.sort(Blue);
                this.getPixel(u, v).setColor(new Color(Red[8], Green[8], Blue[8]));
            }
        }
    }

    public void MaxFilter(int FilterSize) {
        if (FilterSize % 2 == 0) { // if number is even
            return;
        }
        int Red[] = new int[FilterSize * FilterSize];
        int Green[] = new int[FilterSize * FilterSize];
        int Blue[] = new int[FilterSize * FilterSize];

        int x = this.getWidth();
        int y = this.getHeight();

        Picture copy = new Picture(x, y);
        copy.copy(this, 0, 0, x, y, 0, 0);

        int start = (int) Math.floor(FilterSize / 2);
        // the beggining of 
        for (int v = start; v <= y - (start + 1); v++) {
            for (int u = start; u <= x - (start + 1); u++) {
                int k = 0;
                for (int j = -start; j <= start; j++) {
                    for (int i = -start; i <= start; i++) {

                        Red[k] = copy.getPixel(u + i, v + j).getRed();
                        Green[k] = copy.getPixel(u + i, v + j).getGreen();
                        Blue[k] = copy.getPixel(u + i, v + j).getBlue();
                        k++;
                    }
                }
                Arrays.sort(Red);
                Arrays.sort(Green);
                Arrays.sort(Blue);
                this.getPixel(u, v).setColor(new Color(Red[FilterSize * FilterSize - 1], Green[FilterSize * FilterSize - 1], Blue[FilterSize * FilterSize - 1]));
            }
        }
    }

    public void sepiaTint() {
        Pixel pixel = null;
        double redValue = 0;
        double greenValue = 0;
        double blueValue = 0;

        // first change the current picture to grayscale
        this.grayscale();

        // loop through the pixels
        for (int x = 0; x < this.getWidth(); x++) {
            for (int y = 0; y < this.getHeight(); y++) {
                // get the current pixel and color values
                pixel = this.getPixel(x, y);
                redValue = pixel.getRed();
                greenValue = pixel.getGreen();
                blueValue = pixel.getBlue();

                // tint the shadows darker
                if (redValue < 60) {
                    redValue = redValue * 0.9;
                    greenValue = greenValue * 0.9;
                    blueValue = blueValue * 0.9;
                } // tint the midtones a light brown
                // by reducing the blue
                else if (redValue < 190) {
                    blueValue = blueValue * 0.8;
                } // tint the highlights a light yellow
                // by reducing the blue
                else {
                    blueValue = blueValue * 0.9;
                }

                // set the colors
                pixel.setRed((int) redValue);
                pixel.setGreen((int) greenValue);
                pixel.setBlue((int) blueValue);
            }
        }
    }

    public void negate() {
        Pixel[] pixelArray = this.getPixels();
        Pixel pixel = null;
        int redValue, blueValue, greenValue = 0;

        // loop through all the pixels
        for (int i = 0; i < pixelArray.length; i++) {
            // get the current pixel
            pixel = pixelArray[i];

            // get the current red, green, and blue values
            redValue = pixel.getRed();
            greenValue = pixel.getGreen();
            blueValue = pixel.getBlue();

            // set the pixel's color to the new color
            pixel.setColor(new Color(255 - redValue,
                    255 - greenValue,
                    255 - blueValue));
        }
    }

    public void AddValueColor(Picture sourcePicture, int Red_value, int Green_value, int Blue_value) {
        Pixel sourcePixel = null;

        for (int x = 0; x < sourcePicture.getWidth(); x++) {
            for (int y = 0; y < sourcePicture.getHeight(); y++) {
                sourcePixel = sourcePicture.getPixel(x, y);
                if (Red_value == -1) {
                    sourcePixel.setRed(sourcePixel.getRed());
                } else {
                    sourcePixel.setRed(Red_value);
                }

                if (Green_value == -1) {
                    sourcePixel.setRed(sourcePixel.getGreen());
                } else {
                    sourcePixel.setGreen(Green_value);
                }

                if (Blue_value == -1) {
                    sourcePixel.setRed(sourcePixel.getBlue());
                } else {
                    sourcePixel.setBlue(Blue_value);
                }
            }
        }
    }

    public void crop(Picture sourcePicture, int startX, int startY, int endX, int endY, int targetStartX, int targetStartY) {

        Pixel sourcePixel = null;
        Pixel targetPixel = null;

        // loop through the x values
        for (int x = startX, tx = targetStartX; x < endX; x++, tx++) {
            // loop through the y values
            for (int y = startY, ty = targetStartY; y < endY; y++, ty++) {
                // copy the source color to the target color
                sourcePixel = sourcePicture.getPixel(x, y);
                targetPixel = this.getPixel(tx, ty);
                targetPixel.setColor(sourcePixel.getColor());
            }
        }
    }

    public Picture Rotate() {
        Picture target = new Picture(this.getHeight(),
                this.getWidth());
        Pixel sourcePixel = null;
        Pixel targetPixel = null;

        // loop through the columns
        for (int sourceX = 0;
                sourceX < this.getWidth();
                sourceX++) {
            // loop through the rows
            for (int sourceY = 0;
                    sourceY < this.getHeight();
                    sourceY++) {
                // set the target pixel color to the source pixel color
                sourcePixel = this.getPixel(sourceX, sourceY);
                targetPixel = target.getPixel(sourceY,
                        this.getWidth() - 1 - sourceX);
                targetPixel.setColor(sourcePixel.getColor());
            }
        }
        return target;
    }

    public Picture mirrorAllHorizontal() {
        int resHeight = this.getHeight() * 2;
        Picture result = new Picture(this.getWidth(),
                resHeight);
        int mirrorPoint = this.getHeight();
        Pixel topPixel = null;
        Pixel bottomPixel = null;

        // loop through all the rows
        for (int y = 0; y < mirrorPoint; y++) {
            // loop from 0 to the mirror point
            for (int x = 0; x < this.getWidth(); x++) {
                topPixel = getPixel(x, y);
                bottomPixel = result.getPixel(x, y);
                bottomPixel.setColor(topPixel.getColor());
                bottomPixel = result.getPixel(x, resHeight - 1 - y);
                bottomPixel.setColor(topPixel.getColor());
            }
        }
        return result;
    }

    public Picture mirrorAllVertical() {
        int resWidth = this.getWidth() * 2;
        Picture result = new Picture(resWidth,
                this.getHeight());
        int mirrorPoint = this.getWidth();
        Pixel leftPixel = null;
        Pixel rightPixel = null;

        // loop through all the rows
        for (int y = 0; y < getHeight(); y++) {
            // loop from 0 to the mirror point
            for (int x = 0; x < mirrorPoint; x++) {
                leftPixel = getPixel(x, y);
                rightPixel = result.getPixel(x, y);
                rightPixel.setColor(leftPixel.getColor());
                rightPixel = result.getPixel(resWidth - 1 - x, y);
                rightPixel.setColor(leftPixel.getColor());
            }
        }
        return result;
    }

    public void Collage() {
        Picture source1Picture = new Picture(FileChooser.pickAFile());
        Picture source2Picture = new Picture(source1Picture);
        int targetBottomY = this.getHeight() - 5;

        // copy source1Picture to 0, targetBottomY - height
        source2Picture.lighten();
        this.copy(source2Picture, 0, 0,
                source2Picture.getWidth(), source2Picture.getHeight(),
                0, targetBottomY - source2Picture.getHeight());

        source2Picture.makeSunset();
        // copy source2Picture to 100, targetBottomY - height
        this.copy(source2Picture, 0, 0,
                source2Picture.getWidth(), source2Picture.getHeight(),
                150, targetBottomY - source1Picture.getHeight() - 50);

        source1Picture.sepiaTint();
        // copy negated source1Picture to 200
        this.copy(source1Picture, 0, 0,
                source1Picture.getWidth(), source1Picture.getHeight(),
                300, targetBottomY - source1Picture.getHeight() - 50);

        // clear the blue from source 2 picture
        // source2Picture.clearBlue();
        source1Picture.negate();
        // copy negated source1Picture to 400
        this.copy(source1Picture, 0, 0,
                source1Picture.getWidth(), source1Picture.getHeight(),
                450, targetBottomY - source1Picture.getHeight());

    }

    void createAndInitScrollingImage() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void blendPictures() {
        Picture source1Picture = new Picture(FileChooser.pickAFile());
        Picture source2Picture = new Picture(FileChooser.pickAFile());

        // declare the source and target pixel variables
        Pixel source1Pixel = null;
        Pixel source2ixel = null;
        Pixel targetPixel = null;

        /* declare the target x and source x since we will need
         * the values after the for loop
         */
        int sourceX = 0;
        int targetX = 0;

        // copy the first 150 pixels of katie to the canvas
        for (; sourceX < 150; sourceX++, targetX++) {
            for (int sourceY = 0, targetY = 0;
                    sourceY < source1Picture.getHeight();
                    sourceY++, targetY++) {
                source1Pixel = source1Picture.getPixel(sourceX, sourceY);
                targetPixel = this.getPixel(targetX, targetY);
                targetPixel.setColor(source1Pixel.getColor());
            }
        }

        /* copy 50% of katie and 50% of jenny till
         * the end of katie's width
         */
        for (; sourceX < source1Picture.getWidth();
                sourceX++, targetX++) {
            for (int sourceY = 0, targetY = 0;
                    sourceY < source1Picture.getHeight();
                    sourceY++, targetY++) {
                source1Pixel = source1Picture.getPixel(sourceX, sourceY);
                source2ixel
                        = source2Picture.getPixel(sourceX - 150, sourceY);
                targetPixel = this.getPixel(targetX, targetY);
                targetPixel.setColor(
                        new Color((int) (source1Pixel.getRed() * 0.5
                                + source2ixel.getRed() * 0.5),
                                (int) (source1Pixel.getGreen() * 0.5
                                + source2ixel.getGreen() * 0.5),
                                (int) (source1Pixel.getBlue() * 0.5
                                + source2ixel.getBlue() * 0.5)));
            }
        }

        // copy the rest of Jenny
        sourceX = sourceX - 150;
        for (; sourceX < source2Picture.getWidth();
                sourceX++, targetX++) {
            for (int sourceY = 0, targetY = 0;
                    sourceY < source2Picture.getHeight();
                    sourceY++, targetY++) {
                source2ixel = source2Picture.getPixel(sourceX, sourceY);
                targetPixel = this.getPixel(targetX, targetY);
                targetPixel.setColor(source2ixel.getColor());
            }
        }
    }

    public void removeRedEye(int startX, int startY, int endX, int endY, Color newColor) {
        Pixel pixel = null;
        /* loop through the pixels in the rectangle defined by the
         startX, startY, and endX and endY */
        for (int x = startX; x < endX; x++) {
            for (int y = startY; y < endY; y++) {
                // get the current pixel
                pixel = getPixel(x, y);

                // if the color is near red then change it
                if (pixel.colorDistance(Color.red) < 167) {
                    pixel.setColor(newColor);
                }
            }
        }

    }

    public void edgeDetection(int amount) {
        Pixel topPixel = null;
        Pixel bottomPixel = null;
        double topAverage = 0.0;
        double bottomAverage = 0.0;
        int endY = this.getHeight() - 1;

        /* loop through y values from 0 to height - 1
         * (since compare to below pixel) */
        for (int y = 0; y < endY; y++) {

            // loop through the x values from 0 to width
            for (int x = 0; x < this.getWidth(); x++) {

                // get the top and bottom pixels
                topPixel = this.getPixel(x, y);
                bottomPixel = this.getPixel(x, y + 1); // bottom pixel

                // get the color averages for the two pixels
                topAverage = topPixel.getAverage();
                bottomAverage = bottomPixel.getAverage();

                /* check if the absolute value of the difference
                 * is less than the amount */
                if (Math.abs(topAverage - bottomAverage) < amount) {
                    topPixel.setColor(Color.WHITE);
                    // else set the color to black
                } else { // edge is detected.
                    topPixel.setColor(Color.BLACK);
                }
            }
        }
    }

    // new edgeDetection method that takes left and right pixels with top and bottom.
    // for not gray sacle
    public void edgeDetection_LR(int amount) {
        Pixel topPixel = null;
        Pixel bottomPixel = null;
        Pixel leftPixel = null;
        Pixel rightPixel = null;
        double topAverage = 0.0;
        double bottomAverage = 0.0;
        double rightAverage = 0.0;
        double leftAverage = 0.0;

        int endY = this.getHeight() - 1;

        /* loop through y values from 0 to height - 1
         * (since compare to below pixel) */
        for (int y = 0; y < endY; y++) {

            // loop through the x values from 0 to width
            for (int x = 1; x < this.getWidth() - 1; x++) {

                // get the top and bottom pixels
                topPixel = this.getPixel(x, y);
                bottomPixel = this.getPixel(x, y + 1);
                leftPixel = this.getPixel(x - 1, y);
                rightPixel = this.getPixel(x + 1, y);

                // get the color averages for the two pixels
                topAverage = topPixel.getAverage();
                bottomAverage = bottomPixel.getAverage();
                leftAverage = leftPixel.getAverage();
                rightAverage = rightPixel.getAverage();

                /* check if the absolute value of the difference
                 * is less than the amount */
                if ((Math.abs(topAverage - bottomAverage) < amount) || (Math.abs(rightAverage - leftAverage) < amount)) {
                    topPixel.setColor(Color.WHITE);
                    // else set the color to black
                } else { // edge is detected.
                    topPixel.setColor(Color.BLACK);
                }
            }
        }
    }

    // add two filters
    public void chromakey(Picture newBg) {
        Pixel currPixel = null;
        Pixel newPixel = null;

        // loop through the columns
        for (int x = 0; x < getWidth(); x++) {

            // loop through the rows
            for (int y = 0; y < getHeight(); y++) {

                // get the current pixel
                currPixel = this.getPixel(x, y);

                /* if the color at the current pixel is mostly blue
                 * (blue value is greater than red and green
                 * combined), then use new background color
                 */
                if (currPixel.getRed() + currPixel.getGreen()
                        < currPixel.getBlue()) {
                    newPixel = newBg.getPixel(x, y);
                    currPixel.setColor(newPixel.getColor());
                }
            }
        }
    }

    public void swapBackground(Picture oldBackground,
            Picture newBackground) {
        Pixel currPixel = null;
        Pixel oldPixel = null;
        Pixel newPixel = null;

        // loop through the columns
        for (int x = 0; x < getWidth(); x++) {

            // loop through the rows
            for (int y = 0; y < getHeight(); y++) {

                // get the current pixel and old background pixel
                currPixel = this.getPixel(x, y);
                oldPixel = oldBackground.getPixel(x, y);
                /* if the distance between the current pixel color
                 * and the old background pixel color is less
                 * than the 15 then swap in the new background pixel
                 */
                if (currPixel.colorDistance(oldPixel.getColor()) < 15.0) {
                    newPixel = newBackground.getPixel(x, y);
                    currPixel.setColor(newPixel.getColor());
                }
            }
        }
    }

    /**
     * Method to rotate the current picture left 90 degrees
     *
     * @return the new picture
     */
    public Picture rotateLeft() {
        Picture target = new Picture(this.getHeight(), this.getWidth());
        Pixel sourcePixel = null;
        Pixel targetPixel = null;

        // loop through the columns
        for (int sourceX = 0; sourceX < this.getWidth(); sourceX++) {
            // loop through the rows
            for (int sourceY = 0; sourceY < this.getHeight(); sourceY++) {
                // set the target pixel color to the source pixel color

                // make 2 loop to move on width and hight
                // 1- take the pixel in the srcPic on (x,y)
                // 2- take the pixel in the targetPic but swap between x and y after that use the formula of Rright (y,w-1-x)
                // 3- in the pixel of the target we change the color with color of the pixel src
                sourcePixel = this.getPixel(sourceX, sourceY);
                targetPixel = target.getPixel(sourceY, this.getWidth() - 1 - sourceX);
                targetPixel.setColor(sourcePixel.getColor());
            }
        }
        return target;
    }

    // this method changes all colors by the values entered through user interface, i.e. GUI
    // author : saim_rasheed@hotmail.com
    public Picture my_changeAllColors(double redFact, double greenFact, double blueFact) {
        Pixel[] arrayPixel = this.getPixels();
        Pixel sourcePixel = null;
        int index = 0;
        int value = 0;

        while (index < arrayPixel.length) {
            sourcePixel = arrayPixel[index];

            //read write red color
            if (redFact != -1) {
                value = sourcePixel.getRed();
                value = (int) (value * redFact);
                sourcePixel.setRed(value);
            } else {
                sourcePixel.setRed(sourcePixel.getRed());
            }

            //read write green color
            if (greenFact != -1) {
                value = sourcePixel.getGreen();
                value = (int) (value * greenFact);
                sourcePixel.setGreen(value);
            } else {
                sourcePixel.setGreen(sourcePixel.getGreen());
            }

            // read write blue color
            if (blueFact != -1) {
                value = sourcePixel.getBlue();
                value = (int) (value * blueFact);
                sourcePixel.setBlue(value);
            } else {
                sourcePixel.setBlue(sourcePixel.getBlue());
            }
            index = index + 1;
        }
        return this;
    }

    // This method will compute and plot the histogram of the Input Image.
    // author : saim_rasheed@hotmail.com
    public int[] myHistogram() {
        this.grayscale();
        int[] histArray = new int[256];
        Pixel sourcepixel = null;
        int val = 0;
        for (int Sx = 0; Sx < this.getWidth(); Sx++) {
            for (int Sy = 0; Sy < this.getHeight(); Sy++) {
                sourcepixel = this.getPixel(Sx, Sy);
                val = sourcepixel.getGreen();
                histArray[val] = histArray[val] + 1;
            }
        }
        return histArray;
    }

    /**
     * Method to create a flower collage
     */
    public void createCollage() {

        // create the flower pictures
        Picture flower1Picture
                = new Picture(FileChooser.pickAFile());
        Picture flower2Picture
                = new Picture(FileChooser.pickAFile());
        int end1X = flower1Picture.getWidth();
        int end2X = flower2Picture.getWidth();
        int end1Y = flower1Picture.getHeight();
        int end2Y = flower2Picture.getHeight();

        // copy the first flower picture to the
        // top left corner of the canvas
        this.copy(flower1Picture, 0, 0, end1X, end1Y,
                0, 0);

        /* copy the flower2 picture starting with
    * x = 100 in the canvas
         */
        this.copy(flower2Picture, 0, 0, end2X, end2Y,
                100, 0);

        // copy the flower1 negated to x = 200 in the canvas
        flower1Picture.negate();
        this.copy(flower1Picture, 0, 0, end1X, end1Y,
                200, 0);

        /* clear the blue in flower 2 picture and
    * add at x=300 in the canvas
         */
        this.copy(flower2Picture, 0, 0, end2X, end2Y,
                300, 0);

        // copy the negated flower 1 to x=400
        this.copy(flower1Picture, 0, 0, end1X, end1Y,
                400, 0);

        this.mirrorAllHorizontal();
    }

    public void RemoveRedEye(int threshold, int Sx, int Sy, int Ex, int Ey) {

        for (int x = Sx; x < Ex; x++) {
            for (int y = Sy; y < Ey; y++) {

                if (this.getPixel(x, y).colorDistance(Color.red) < threshold) {
                    this.getPixel(x, y).setColor(Color.black);
                }
            }
        }
    }

    public double Brightness() {
        Pixel[] pixelArray = this.getPixels();
        double PixelsInstenses = 0;
        double brightness = 0.0;
        // loop through all the pixels
        for (Pixel currntPixel : pixelArray) {
            PixelsInstenses += currntPixel.getAverage();

        }
        brightness = PixelsInstenses / pixelArray.length;
        return brightness;
    }

    public double Contrast() {
        int[] Array = new int[256];

        for (int H = 0; H < this.getHeight(); H++) {
            for (int W = 0; W < this.getWidth(); W++) {
                Pixel pixel = this.getPixel(W, H);
                int average = (int) pixel.getAverage();
                Array[average]++;
            }
        }

        int max = 0;
        int min = 1;

        for (int i = 0; i < Array.length; i++) {
            if (Array[i] > max) {
                max = Array[i];
            }
        }

        for (int j = 0; j < Array.length; j++) {
            if (Array[j] < min && Array[j] != 0) {
                min = Array[j];
            }
        }

        double contrast = (double) (max - min) / (double) (max + min);

        return contrast;
    }

    public Picture Contrast(Picture pic, double silderValue) {
        Picture newpic = new Picture(pic.getWidth(), pic.getHeight());
        // we have an array of histogram with the range 0 to 255
        int[] histogram = new int[256];
        Pixel startp = null;
        Pixel targetp = null;
        int low = 0, high = 0, lowValue = 4000, highValue = 0, min, max;

        //
        for (int v = 0; v < pic.getHeight(); v++) {
            for (int u = 0; u < pic.getWidth(); u++) {
                startp = pic.getPixel(u, v);
                histogram[(int) startp.getAverage()] = histogram[(int) startp.getAverage()] + 1;
                // for example : histogram[4] = histogram[4]+ 1;
            }
        }
        int i = 0;
        while (i < histogram.length) {
            if (histogram[i] < lowValue && histogram[i] > 0) {
                lowValue = histogram[i];
                low = i;
            }
            if (highValue < histogram[i]) {
                highValue = histogram[i];
                high = i;
            }
            i++;
        }
        // this while loop is for cal the highValue and lowValue

        double v1 = (259 * (silderValue + 255));
        double v2 = (255 * (259 - silderValue));
        double factor = v1 / v2;

        for (int v = 0; v < newpic.getHeight(); v++) {
            for (int u = 0; u < newpic.getWidth(); u++) {

                int red = (int) (factor * ((pic.getPixel(u, v).getRed() - 128)) + 128);
                int green = (int) (factor * ((pic.getPixel(u, v).getGreen() - 128)) + 128);
                int blue = (int) (factor * ((pic.getPixel(u, v).getBlue() - 128)) + 128);

                if (red > 255) {
                    red = 255;
                } else if (red < 0) {
                    red = 0;
                }

                if (green > 255) {
                    green = 255;
                } else if (green < 0) {
                    green = 0;
                }

                if (blue > 255) {
                    blue = 255;
                } else if (blue < 0) {
                    blue = 0;
                }

                newpic.getPixel(u, v).setColor(new Color(red, green, blue));
            }
        }
        return newpic;
    }

    public static void main(String[] args) {

    }

    public void BoxFilter(int FilterSize) {
        if (FilterSize % 2 == 0) { // if number is even
            return;
        }

        int sumR, sumG, sumB;

        // 3*3 =1
        // 5*5 =2
        // 7*7 =3
        // etc...
        int start = (int) Math.floor(FilterSize / 2);

        for (int i = start; i <= this.getWidth() - (start + 1); i++) {
            for (int j = start; j <= this.getHeight() - (start + 1); j++) {
                sumR = 0;
                sumG = 0;
                sumB = 0;
                for (int k = -start; k <= start; k++) {
                    for (int l = -start; l <= start; l++) {
                        sumR += this.getPixel(i + k, j + l).getRed();
                        sumG += this.getPixel(i + k, j + l).getGreen();
                        sumB += this.getPixel(i + k, j + l).getBlue();
                    }
                }
                sumR /= Math.round(FilterSize * FilterSize);
                sumG /= Math.round(FilterSize * FilterSize);
                sumB /= Math.round(FilterSize * FilterSize);
                this.getPixel(i, j).setRed(sumR);
                this.getPixel(i, j).setGreen(sumG);
                this.getPixel(i, j).setBlue(sumB);
            }
        }
    }

    // not working.
    public void gaussianFilter(int FilterSize, int P) {

        // first make the filter array
        int[][] filter = new int[FilterSize][FilterSize];

        filter[FilterSize / 2][FilterSize / 2] = P; // middle pixel.

        // num of outer layers
        // 3*3 will be 1 layer
        // 5*5 2 layers ...
        int remaining = 1 - P;
        int numOfLayers = (int) Math.floor(FilterSize / 2);
        int weight[] = new int[numOfLayers];
        for (int i = 0; i < numOfLayers; i++) {
            remaining /= 2;
            weight[i] = remaining;
            System.out.println("Layer " + i + " got " + remaining);
        }

        // first layer should get remaming/2
        // second layer should get half of the reamining from the first layer
        // etc
        for (int i = 0; i < FilterSize; i++) { // Distirbute the remaining on the rest of the filter
            for (int j = 0; j < FilterSize; j++) {
                if (i == FilterSize / 2 && j == FilterSize / 2) { // middle pixel do nothing
                    continue;
                }

            }
        }

    }

    public void gaussianFilter3x3(int choice) {
        double[][] filter = null;
        if (choice == 0) {
            filter = new double[][]{
                {0.075, 0.125, 0.075},
                {0.125, 0.200, 0.125},
                {0.075, 0.125, 0.075}
            };
        } else if (choice == 1) {
            filter = new double[][]{
                {0.05, 0.075, 0.05},
                {0.075, 0.500, 0.075},
                {0.05, 0.075, 0.05}
            };
        } else if (choice == 2) {
            filter = new double[][]{
                {0.02, 0.03, 0.02},
                {0.03, 0.800, 0.03},
                {0.02, 0.03, 0.02}
            };
        }

        int sumR, sumG, sumB;

        for (int i = 1; i <= this.getWidth() - 2; i++) {
            for (int j = 1; j <= this.getHeight() - 2; j++) {
                sumR = 0;
                sumG = 0;
                sumB = 0;
                for (int k = -1; k <= 1; k++) {
                    for (int l = -1; l <= 1; l++) {
                        sumR += filter[k + 1][l + 1] * this.getPixel(i + k, j + l).getRed();
                        sumG += filter[k + 1][l + 1] * this.getPixel(i + k, j + l).getGreen();
                        sumB += filter[k + 1][l + 1] * this.getPixel(i + k, j + l).getBlue();
                    }
                }
                this.getPixel(i, j).setRed(sumR);
                this.getPixel(i, j).setGreen(sumG);
                this.getPixel(i, j).setBlue(sumB);
            }
        }

    }

} // end of class Picture, put all new methods before this
