Here is my ditherer!

It takes an image as input and outputs the same image using a limited color pallete. 

run it by following these steps:

1) download project, open command prompt and cd inside of src/
2) javac App.java
3) App.java inputImageFileLocation ditheringAlgorithm outputImageFileLocation

Example arguments that will work: java App.java birds.png jarvis result

you can use "birds.png" or "dog.png" as inputImageFileLocation to run with one of the example images saved in the project, or you can save another image inside src/images/ to use that image

ditheringAlgorithm choices are "stucki", "jarvis", and "floydsteinberg"

if outputImageFileLocation is "result", it will save the result within the project to src\images\result.png

there is also a 4th optional argument to change the color pallete. the options are:
4 = blue, green, red, violet, yellow, aqua
3 = black, white,  blue, green, red, violet, yellow, aqua
2 = black, white,  blue, green, red
1 = black, white



