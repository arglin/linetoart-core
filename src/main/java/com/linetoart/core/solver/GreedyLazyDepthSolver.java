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
import com.linetoart.core.model.Nail;

public class GreedyLazyDepthSolver extends AbstractDepthSolver {

    @Override
    protected void crossThreads(int[][] depth, L2Art l2Art, PixelActionListener listener) {

        Nail[] nails = l2Art.getAllNails();
        int start = 0;
        int next = start;
        int crossNum = 0;
        while (crossNum < L2ADefault.maxTurns && next != -1) {
            next = this.findBestNextNail(depth, start, nails, l2Art);
            if (next != -1) {
                l2Art.addEdge(next);
                Bresenham.plotLine(nails[start].centerX, nails[start].centerY,
                        nails[next].centerX, nails[next].centerY, listener);
                start = next;
                crossNum++;
            }
        }
    }

    protected int findBestNextNail(int[][] depth, Integer start, Nail[] nails, L2Art l2Art) {

        DepthMatchReport bestMr = new DepthMatchReport();
        int bestEnd = -1;
        for (int i = 0; i < nails.length; i++) {
            if (i == start) continue;
            if (l2Art.hasEdge(start, i)) continue;
            if (Math.abs(start - i) < 10) continue;
            DepthMatchReport mr = this.analyse(start, i, depth, nails);

            if (mr.isBetterThan(bestMr) && mr.getAccuracy() >= L2ADefault.minAccurate) {
                bestMr = mr;
                bestEnd = i;
            }
        }
        return bestEnd;
    }
}
