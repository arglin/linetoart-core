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

public class Bresenham {

    private static void plotLineLow(int x0, int y0, int x1, int y1, PixelActionListener listener) {
        int dx = x1 - x0;
        int dy = Math.abs(y1 - y0);
        int yi = y1 < y0 ? -1 : 1;

        int D = 2 * dy - dx;
        int y = y0;
        int x = x0;

        while (x <= x1) {
            listener.action(x, y);
            if (D > 0) {
                y += yi;
                D += 2 * (dy - dx);
            } else {
                D += 2 * dy;
            }
            x++;
        }
    }

    private static void plotLineHigh(int x0, int y0, int x1, int y1, PixelActionListener listener) {
        int dx = Math.abs(x1 - x0);
        int dy = y1 - y0;
        int xi = x1 < x0 ? -1 : 1;

        int D = 2 * dx - dy;
        int y = y0;
        int x = x0;

        while (y <= y1) {
            listener.action(x, y);
            if (D > 0) {
                x += xi;
                D += 2 * (dx - dy);
            } else {
                D += 2 * dx;
            }
            y++;
        }
    }

    public static void plotLine(int x0, int y0, int x1, int y1, PixelActionListener listener) {
        if (Math.abs(y1 - y0) < Math.abs(x1 - x0)) {
            if (x0 > x1) {
                plotLineLow(x1, y1, x0, y0, listener);
            } else {
                plotLineLow(x0, y0, x1, y1, listener);
            }
        } else {
            if (y0 > y1) {
                plotLineHigh(x1, y1, x0, y0, listener);
            } else {
                plotLineHigh(x0, y0, x1, y1, listener);
            }
        }
    }
}
