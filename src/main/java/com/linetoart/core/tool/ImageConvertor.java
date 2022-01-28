/*
 * MIT License
 *
 * Copyright (c) 2022.
 *
 * Author: arglin
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.linetoart.core.tool;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class ImageConvertor {

    public static int[][] pixelsToGrayDepthArray(int[][] pixels) {

        int width = pixels.length;
        int height = pixels[0].length;
        int[][] result = new int[width][height];
        for (int h = 0; h < height; h++) {
            for (int w = 0; w < width; w++) {
                int r = (pixels[w][h] & 0xFF0000) >> 16;
                int g = (pixels[w][h] & 0xFF00) >> 8;
                int b = (pixels[w][h] & 0xFF);
                int depth = (r + g + b) / 3;
                result[w][h] = depth;
            }
        }
        return result;
    }

    public static int[][] imageToPixelArray(Image image, int width, int height) {

        BufferedImage bufImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = bufImage.createGraphics();
        g2.drawImage(image, 0, 0, width, height, null);
        g2.dispose();
        int[][] result = new int[width][height];

        int[] pixels = ((DataBufferInt) bufImage.getRaster().getDataBuffer()).getData();
        for (int pixel = 0, row = 0, col = 0; pixel < pixels.length - 1; pixel++) {

            result[row++][col] = pixels[pixel];
            if (row == width) {
                row = 0;
                col++;
            }
        }
        return result;
    }
}
