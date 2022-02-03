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

package com.linetoart.core.model;

import com.linetoart.core.basic.L2ADefault;
import com.linetoart.core.tool.ImageConvertor;

import java.awt.*;

public class ComputeData {

    private final int[][] computePixels;

    private final NailsFormation nailsFormation;

    /**
     * @param image input image
     */
    public ComputeData(Image image) {
        this(image, NailsFormation.OVAL);
    }

    public ComputeData(Image image, NailsFormation formation) {
        int originalWidth = image.getWidth(null);
        int originalHeight = image.getHeight(null);
        int computeWidth;
        int computeHeight;
        if (originalWidth >= originalHeight) {
            computeHeight = L2ADefault.shapeEdgeMin;
            computeWidth = (int) (originalWidth * L2ADefault.shapeEdgeMin / (float) originalHeight);
        } else {
            computeWidth = L2ADefault.shapeEdgeMin;
            computeHeight = (int) (originalHeight * L2ADefault.shapeEdgeMin / (float) originalWidth);
        }

        this.computePixels = ImageConvertor.imageToPixelArray(image, computeWidth, computeHeight);
        this.nailsFormation = formation;
    }

    private Nail[] formOval(int nailNum) {
        Nail[] nails = new Nail[nailNum];
        int width = this.computePixels.length;
        int height = this.computePixels[0].length;
        float[] center = new float[]{(width - 1) / 2f, (height - 1) / 2f};
        float a = width / 2f;
        float b = height / 2f;

        for (int i = 0; i < nails.length; i++) {
            int x = (int) (center[0] + a * Math.cos(Math.toRadians(i * 360f / nailNum)));
            int y = (int) (center[1] + b * Math.sin(Math.toRadians(i * 360f / nailNum)));
            nails[i] = new Nail(i + 1, x, y);
        }

        return nails;
    }

    private Nail[] formRectangle(int nailNum) {
        //TODO
        return null;
    }

    private Nail[] formHeart(int nailNum) {
        //TODO
        return null;
    }

    /**
     * get all the nails on the canvas
     *
     * @param nailNum nail total numbers on the canvas
     * @return all nails
     */
    public Nail[] getComputeNails(int nailNum) {

        switch (this.nailsFormation) {
            case OVAL:
                return this.formOval(nailNum);
            case RECTANGLE:
                return this.formRectangle(nailNum);
            case HEART:
                return this.formHeart(nailNum);
            default:
                return null;
        }
    }

    /**
     * @return the pixel array for the processing
     */
    public int[][] getComputePixels() {

        if (this.nailsFormation == NailsFormation.OVAL) {
            int width = this.computePixels.length;
            int height = this.computePixels[0].length;
            float[] center = new float[]{(width - 1) / 2f, (height - 1) / 2f};
            float a = width / 2f;
            float b = height / 2f;
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    if ((x - center[0]) * (x - center[0]) / ((a + 1) * (a + 1)) + (y - center[1]) * (y - center[1]) / ((b + 1) * (b + 1)) > 1) {
                        this.computePixels[x][y] = 0; //transparent
                    }
                }
            }
            return this.computePixels;
        }
        return this.computePixels;
    }
}
