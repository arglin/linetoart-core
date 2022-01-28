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

import com.linetoart.core.basic.Bresenham;
import com.linetoart.core.basic.DepthMatchReport;
import com.linetoart.core.basic.L2ADefault;
import com.linetoart.core.basic.PixelActionListener;
import com.linetoart.core.solver.model.ComputeNail;
import com.linetoart.core.solver.model.L2ASolution;

public class GreedyPerfectionismDepthSolver extends AbstractDepthSolver {

    @Override
    protected void crossThreads(int[][] depth, L2ASolution l2ASolution, PixelActionListener listener) {

        ComputeNail[] computeNails = l2ASolution.getPinLocations();
        int count = 3000;
        while (count > 0) {
            int bestStart = -1;
            int bestEnd = -1;
            DepthMatchReport bestMr = new DepthMatchReport();
            for (int i = 0; i < computeNails.length; i++) {
                Object[] report = this.findBestNextPoint(depth, i, computeNails, l2ASolution);
                DepthMatchReport mr = (DepthMatchReport) report[1];
                if (mr.isBetterThan(bestMr)) {
                    bestMr = mr;
                    bestStart = i;
                    bestEnd = (int) report[0];
                }
            }

            if (bestEnd != -1) {
                Bresenham.plotLine(computeNails[bestStart].centerX, computeNails[bestStart].centerY,
                        computeNails[bestEnd].centerX, computeNails[bestEnd].centerY, (x, y) -> {
                            depth[x][y] += 20;
                            if (depth[x][y] > 255) {
                                depth[x][y] = 255;
                            }
                            if (depth[x][y] < 0) {
                                depth[x][y] = 0;
                            }
                        });
                l2ASolution.addEdge(bestEnd);
            }

            count--;
        }
    }

    protected Object[] findBestNextPoint(int[][] depth, Integer start, ComputeNail[] computeNails, L2ASolution l2ASolution) {

        DepthMatchReport bestMr = new DepthMatchReport();
        int bestEnd = -1;
        for (int i = start + 1; i < computeNails.length; i++) {
            if (l2ASolution.hasEdge(start, i)) continue;
            if (Math.abs(start - i) < 10) continue;
            DepthMatchReport mr = this.analyse(start, i, depth, computeNails);
            if (mr.isBetterThan(bestMr) && mr.getAccuracy() >= L2ADefault.minAccurate) {
                bestMr = mr;
                bestEnd = i;
            }
        }
        return new Object[]{bestEnd, bestMr};
    }
}
