package pdfextraction;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import org.apache.pdfbox.io.IOUtils;
import org.apache.poi.xslf.usermodel.*;

public class BPowerPointGenerator {

	/**
	 *
	 * @param projectRoot - root path of project
	 * @param presentation - arraylist containing all the slides
	 * @throws IOException
	 */
    public static void create(String projectRoot, ArrayList<Slide> presentation) throws IOException {

	// Get the template folder path (project directory)

	String templatePath = projectRoot + "/pdfextraction/content/templates/Pine design.pptx";

	// Use template for PowerPoint
	XMLSlideShow ppt = new XMLSlideShow(new FileInputStream(templatePath));

	// Delete any existing slides in template
	for (int i = ppt.getSlides().size() - 1; i >= 0; i--) {
	    ppt.removeSlide(i);
	}

	// load slide master info at index 0 this will allow you to select layout
	// options
	XSLFSlideMaster slideOptions = ppt.getSlideMasters().get(0);

	// from the layout options we wish to use TITLE layout for the first slide
	XSLFSlideLayout titleForm = slideOptions.getLayout(SlideLayout.TITLE);

	// now we have loaded the layout, we create a new slide with said layout
	XSLFSlide slide1 = ppt.createSlide(titleForm);

	// add a title to the title slide from the first slide object in presentation
	// array list
	XSLFTextShape title1 = slide1.getPlaceholder(0);
	title1.setText(presentation.get(0).getTitle());

	// set subtitle to empty
	XSLFTextShape subtitle1 = slide1.getPlaceholder(1);
	subtitle1.setText("");

	// for every section we create one slide
	for (int i = 1; i < presentation.size(); i++) {

		// get title and body format
	    XSLFSlideLayout titleAndBody = slideOptions.getLayout(SlideLayout.TITLE_AND_CONTENT);

		// create new slide called midSlide with titleAndBody format
	    XSLFSlide midSlide = ppt.createSlide(titleAndBody);

		// set title and body of slide to text from that slide object/section.txt file
	    XSLFTextShape slideTitle = midSlide.getPlaceholder(0);
	    slideTitle.setText(presentation.get(i).getTitle());
	    XSLFTextShape slideBody = midSlide.getPlaceholder(1);

		slideBody.clearText();

	    // to get body of text we will need to use the section.txt file, this is done
	    // through getSectionBody helper
	    String bodyText = presentation.get(i).getParagraph();

	    slideBody.addNewTextParagraph().addNewTextRun().setText(bodyText);

		if (presentation.get(i).getImage().size() != 0) {
			for (int j = 0; j < presentation.get(i).getImage().size(); j++) {
				XSLFSlide imgSlide = ppt.createSlide();
				int imageNum = presentation.get(i).getImage().get(j) + 1;
				File image = new File(projectRoot + "/pdfextraction/content/output/image_" + imageNum + ".png");
				byte[] picture = IOUtils.toByteArray(new FileInputStream(image));
				XSLFPictureData idx = ppt.addPicture(picture, XSLFPictureData.PictureType.PNG);
				XSLFPictureShape pic = imgSlide.createPicture(idx);

				// Calculate the center position of the slide
				Dimension slideBounds = imgSlide.getSlideShow().getPageSize();
				int centerX = slideBounds.width / 2;
				int centerY = slideBounds.height / 2;

				// Calculate the center position of the image
				int imageWidth = pic.getPictureData().getImageDimension().width;
				int imageHeight = pic.getPictureData().getImageDimension().height;
				int imageX = centerX - (imageWidth / 2);
				int imageY = centerY - (imageHeight / 2);

				// Set the position of the image at the center
				pic.setAnchor(new Rectangle(imageX, imageY, imageWidth, imageHeight));
			}
		}
	}

	// creating an FileOutputStream object
	File file = new File(presentation.get(0).getTitle() + ".pptx");
	FileOutputStream out = new FileOutputStream(projectRoot + "/pdfextraction/content/output/" + file);

	// saving the changes to a file
	ppt.write(out);
	System.out.println("Presentation created successfully");
	out.close();
	ppt.close();
    }
}
