Here is my ditherer!

It takes an image as input and outputs the same image using a limited color pallete. 

run it by following these steps:

1) cd to JavaDithering
2) javac App.java
3) App.java inputImageFileLocation ditheringAlgorithm outputImageFileLocation

Example arguments that will work: App.java birds.png jarvis result 3

you can use "birds.png" as inputImageFileLocation run with one of the example images saved in the project.

ditheringAlgorithm choices are "stucki", "jarvis", and "floydsteinberg"

if no outputImageFileLocation is specified, or if outputImageFileLocation is "result", it will save the result within the project to src\images\result.png

there is also a 4th optional argument to change the color pallete. the options are:
4 = blue, green, red, violet, yellow, aqua
3 = black, white,  blue, green, red, violet, yellow, aqua
2 = black, white,  blue, green, red
1 = black, white



