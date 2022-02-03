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

import com.linetoart.core.L2ASolution;
import com.linetoart.core.model.Nail;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class L2Art implements L2ASolution {

    private final Nail[] nails;
    private final Map<Integer, Set<Integer>> net;
    private final List<Integer> path;

    public L2Art(Nail[] nails) {
        this.nails = nails;
        this.path = new ArrayList<>();
        this.net = new ConcurrentHashMap<>();
    }

    public boolean hasEdge(int start, int end) {
        return this.net.get(start) != null && this.net.get(start).contains(end);
    }

    public void addEdge(int n) {
        if (this.path.size() == 0) {
            this.path.add(n);
            this.net.put(n, new HashSet<>());
        }
        Integer current = path.get(path.size() - 1);
        this.path.add(n);
        this.net.computeIfAbsent(current, (k) -> new HashSet<>()).add(n);
        this.net.computeIfAbsent(n, (k) -> new HashSet<>()).add(current);
    }

    public Nail[] getAllNails() {
        return nails;
    }

    public List<Integer> getPath() {
        return this.path;
    }

    public Map<Integer, Set<Integer>> getNet() {
        return this.net;
    }
}
