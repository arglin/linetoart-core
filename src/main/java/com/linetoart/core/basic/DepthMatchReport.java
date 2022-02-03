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

package com.linetoart.core.basic;

public class DepthMatchReport {

    private long affectedPixelNum = 0;
    private double pixelDiffSum = 0;

    /**
     * ARGB pixel
     *
     * @param bp pixel be compared
     * @param tp pixel to compare
     */
    public void compare(int bp, int tp) {
        affectedPixelNum++;
        pixelDiffSum += tp - bp;
    }

    public long getAffectedPixelNum() {
        return affectedPixelNum;
    }

    public float getAccuracy() {
        return affectedPixelNum != 0 ? (float) (1 - pixelDiffSum / (255d * affectedPixelNum)) : 0;
    }

    public boolean isBetterThan(DepthMatchReport other) {

        return (this.getAccuracy() > other.getAccuracy() ||
                (this.getAccuracy() == other.getAccuracy()
                        && this.getAffectedPixelNum() > other.getAffectedPixelNum()));
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("PixelReport{");
        sb.append("affectedPixelNum=").append(affectedPixelNum);
        sb.append(", pixelDiffSum=").append(pixelDiffSum);
        sb.append(", accuracy=").append(this.getAccuracy());
        sb.append('}');
        return sb.toString();
    }
}
