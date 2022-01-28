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

package com.linetoart.core.solver;

import com.linetoart.core.basic.DepthMatchReport;
import com.linetoart.core.basic.L2ADefault;
import com.linetoart.core.basic.Bresenham;
import com.linetoart.core.basic.PixelActionListener;
import com.linetoart.core.solver.model.ComputeData;
import com.linetoart.core.solver.model.ComputeNail;
import com.linetoart.core.solver.model.NailsShapes;
import com.linetoart.core.solver.model.L2ASolution;
import com.linetoart.core.tool.ImageConvertor;

import java.awt.*;

public abstract class AbstractDepthSolver implements L2ASolver {

    public L2ASolution solve(Image image, int nailNum) {

        ComputeData computeData = new ComputeData(image, NailsShapes.OVAL);
        L2ASolution l2ASolution = new L2ASolution(computeData.getComputeNails(nailNum));
        int[][] blackDepth = ImageConvertor.pixelsToGrayDepthArray(computeData.getComputePixels());

        this.crossThreads(blackDepth, l2ASolution, (x, y) -> {
            blackDepth[x][y] += L2ADefault.lineDepth;
            if (blackDepth[x][y] > 255) {
                blackDepth[x][y] = 255;
            }
            if (blackDepth[x][y] < 0) {
                blackDepth[x][y] = 0;
            }
        });

        return l2ASolution;
    }

    protected DepthMatchReport analyse(int startPin, int endPin, int[][] depth, ComputeNail[] computeNails) {

        DepthMatchReport depthMatchReport = new DepthMatchReport();
        try {
            Bresenham.plotLine(computeNails[startPin].centerX, computeNails[startPin].centerY,
                    computeNails[endPin].centerX, computeNails[endPin].centerY, (x, y) -> depthMatchReport.compare(0x00, depth[x][y]));
        } catch (Exception e) {
            System.out.println(computeNails[startPin].centerX);
            System.out.println(computeNails[startPin].centerY);
        }
        return depthMatchReport;
    }

    protected abstract void crossThreads(int[][] pixels, L2ASolution l2ASolution, PixelActionListener listener);
}
