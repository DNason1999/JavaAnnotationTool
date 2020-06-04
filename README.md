# JavaAnnotationTool
An image annotation tool in Java for platform independence 

This tool is meant to be used for creating point annotations on an image set.
These coordinates can be used to train a CNN along with the original images.

This tool is designed for use in python environments as the output.txt file will be
in a very python operable format.

## How to Use

1. Unzip the JavaAnnotationTool.zip wherever you want this tool

2. Put images inside the images directory

3. Open the JavaAnnotationTool.jar file

4. Work through the images, marking the points in the order you want them to be in the output

5. Once finished, the ouput.txt should be generated and you can process that however you want

  #### Output example:
  This will be the text output in a file called output.txt \
 `0 [120,50]:[65,150]:\n`<br>
 `1 [50,125]:[40,40]:[10,20]\n`<br>
 `2 \n`<br>
 `3 [12,150]:[70,10]:\n`

  Line 2 is an example of a skipped image, where no points were placed and only a line break is output.
## Notes

- The save and exit process will restart you at the last position as saved in the config.txt file
  changing the "lastPathNum" will change the image you start / resume working on.

- Going back to previous images using the "Previous Image" button will DELETE the previous entry,
    this means if you go back 10 images, you will have to reprocess those 10 images.

- If you skip an image by not placing any points on the image, the output.txt will contain only a 
    newline escape ("\n") for that line in the output.txt file corresponding to that image's position
    in the list of images. 
