# linetoart-core
core algorithm to generate the String Art from a portrait. 

This artwork is created by crossing one single string around fixed number of nails which form a shape like a circle, square etc.


# how to use
```
L2ASolver solver = L2ASolverFactory.getSolver(L2ASolverMethods.GREEDY_LAZY_DEPTH);

/**
 * if the image is not a square, then it will take the center square part to process
 * @param image image to be processed, make sure the image size is at least 500*500
 * @param nailNum the total nail number to form the portrait
 * @return Solution
 */
L2ASolution solution = solver.solve(image, 300);

/**
 * the path to form the portrait, each node represent the nail index(starts from 0)
 */
List<Integer> path = solution.getPath();

```

# smaple
![image](https://user-images.githubusercontent.com/48913228/151481519-f33156e0-c3ec-4a60-8344-96c25c5b2aff.png)
![image](https://user-images.githubusercontent.com/48913228/151481775-3a8e25d2-fb03-4161-8e45-cb2b83193ab4.png)

