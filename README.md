# Design Document: PDF to PowerPoint

## Table of Contents

- Description of Software Architecture and Target Environment
- Dynamic Model
- Interactions between classes
- Class List
- Generalization Diagram
- UI Mock-Up
- State Model
- Sequence Diagrams
- Design Issues


## Description of Software Architecture and Target Environment

The system we are designing ( _figure 1)_ will be able to take a pdf input of research from a
user and then create a summarized PowerPoint with or without photos based on the user's
preference. The user will then have the option to download the created PowerPoint allowing for
independent use including but not limited to editing, sharing and presenting. Our software runs
on a client-side model which means all processing is done on the user device. The main
limitation of this is software being potentially limited by the capabilities of the end users device,
however we have determined this is not an issue as our program is very non intensive and
requires no further hardware demands above the operating system itself. A client-side model
allows for us to offer faster performance, offline capabilities and increased user security and
confidentiality.

The first component of our software architecture is a parser that can effectively navigate
through the pdf file given and extract all text and images. The extracted text will be in a .txt
format allowing for more effective use in following steps. While the photos will remain in .jpeg
format as they will not need to be altered and must be supported in PowerPoint. The second
component will validate the file type submitted and give an appropriate error message explaining
to the user that it must be a pdf file input. The third component will be a section creator that will
take the given information from the parser and divide it up into specific topics allowing for easier
summarization and transfer to a given slide. The next component will be a summarizer that will
take the sectioned .txt files and efficiently summarize the information allowing for it to be easily
understandable yet robust. Now that the information is divided and summarized, the next
component will be able to transfer this information into singular slides on a slide show along
with images if requested. Our final component will be the functionality for the user to be able to
download the completed PowerPoint file to possibly edit, share or present.

The target environment for this program ranges greatly depending on the end user. This
software is however strictly designed for computers, whether that be a laptop or home desktop
computer, that is in regard to the end users discretion. The chosen computer however must meet
minimum functioning requirements for the given operating system as well as it must have a
minimum of 1 GB of available storage for the application itself and any possible exported
PowerPoints. This application was designed with Windows 10 architecture in mind, but it may
function fully or limitedly amongst other popular operating systems including various versions of
Windows, Mac OS and Linux. In order for this software to execute it does not require any
access to the internet however if the software is not already installed on your given computer you
may require internet access. User security is a high priority, and our software does not require
the user to enter any personal information as well as it will be unable to remember or store and
form of previous pdf’s or generated PowerPoints.


```
Figure 1 : Process Model of PDF to PPT Converter
```
## Dynamic Model

Our software will work flawlessly together allowing the user to navigate between the
steps without even knowing. Below ( _figure 2_ ) demonstrates the process from start to end of the
code running. This process begins with the pdf input from the user, where the pdf will be
validated to ensure it is the right type. If the user enters the wrong type it will give an error
message and the user will return to the stage where they can enter a pdf. Once the user has
entered a valid pdf they will be prompted with a choice where they can select either a
PowerPoint with or without photos. The program will then use their choice when designing the
final PowerPoint which the user will not be able to see the process of. Finally, the user will be
prompted with an option to download the final PowerPoint allowing them to edit, share and
present as needed. Once again, this process will incorporate many different classes and methods
that will seamlessly interact with one another allowing for a smooth user experience.

## Interactions between classes

After using the ResearchPaperParser class to parse down the research paper into more
manageable research chunk objects the Summarizer class function _summarizeText()_ will take the
chunks of research as input and complete all the necessary summarization. The way the
Summarizer class will do the summarization is by using methods from the TextAnalyzer class.
Functions such as _extractKeywords()_ and _performEntityRecognition()_ will reduce the text and
begin to form the text that will be used in the actual PowerPoint presentation. The reduced text
and images now need to be properly formatted in a slide show presentation. The


PowerPointGenerator class will do this by passing the reduced text and image research chunk
from the TextAnalyzer class as parameters to functions such as _addSlide()_ and
_addImageToSlide()._ Logically we must first define what is a slide? A slide for our purposes will
be an object with attributes including title(string), images(array of images), and
body/content(array of strings). The title, images, and text will be found in the Summarizer class
and TextAnalyzer class while the reduction of the original research text occurs. All that needs to
be done is simply use your reduced text from earlier with the appropriate setter ( _setSlideTitle()
setSlideImages()_ and _setSlideContent())_ within the Slide class to format a slide.

```
Figure 2 : Context Model of PDF to PPT Converter
```

_Figure 3:_ Class Diagrams and Relationships


## Class List

**1. ResearchPaperParser:** This class can encapsulate the functionality for parsing the
    research paper document. It can have methods like _parseDocument()_ to extract text and
    images.
**2. ImageProcessor:** This class purpose is to work with images allowing for easy resizing
    and an optimize method which can reduce the size of the image to make the process of
    development quicker.
**3. Section class:** can represent a specific section of the research paper. It can contain
    attributes such as a section title and content. You can include methods like
    _getSectionTitle()_ and _getSectionContent()_.
**4. Summarizer Class:** The Summarizer class can handle the text summarization process.
    It can take a section's content as input and provide methods like _summarizeText()_ to
    generate a summary.
**5. TextAnalyzer:** The TextAnalyzer class can be responsible for analyzing text sections
    and identifying important information. It can include methods like _extractKeywords()_ to
    extract keywords from the text, _performEntityRecognition()_ to recognize relevant entities,
    etc.
**6. Slide:** A Slide class can represent a PowerPoint slide. It can contain attributes like a slide
    title, slide content, and embedded images. You can include methods like _setSlideTitle()_
    and _setSlideContent()_.
**7. PowerPointGenerator:** The PowerPointGenerator class can handle the generation of
    PowerPoint slides. It can have methods like _createPresentation()_ to initialize a
    PowerPoint presentation, _addSlide()_ to add slides to the presentation, and
    _addImageToSlide()_ to embed images into slides.
**8. FileDownloader:** The FileDownloader class can manage the downloading functionality.
    It can include methods like _downloadFile()_ to facilitate the download of the generated
    PowerPoint presentation.


_Figure 4:_ Classes and Their Associations Diagram


_Figure 5:_ Generalization Diagram


_Figure 7_ : State Model of the Operation State for PDF to PPT Convertor


_Figure 8_ : Sequence Diagram for Error Handling of Invalid File Input


_Figure 9_ : Sequence Diagram for Downloading PPT of Different Formats


_Figure 10_ : Sequence Diagram for the Conversion of PDF to PPT


## Design Issues

```
Functional Issues
```
```
Missing Functionality: Due to time constraints and the complexity of our program, it is
possible that the image extraction functionality will not be available. Also, as noted on the
System Requirements Specification, our program may not have a GUI component due to time
constraints.
Solution: These issues could be solved by coding them with functionality first in mind rather
than polish. In other words, these components could be functioning but not be fully refined.
Useful libraries that already come with this functionality can also solve this issue.
```
```
Poor Usability: The GUI or equivalent may be challenging to use, making it difficult for users
to understand and interact with the system effectively.
Solution: Making buttons stand out and improving responsiveness. Making every action the
system performs clear and understandable.
```
```
Performance Problems: The software may suffer from performance-related issues, such as
slow GUI response times or excessive resource consumption when generating a PPT file or
summarizing text.
Solution: Frequent and extensive testing, ensuring that there are no stalling chunks of code or
excessive library usage. Eliminating bloat and repeated functionality can also solve this
problem.
```
```
Incorrect Output: The software could produce incorrect PPTs that do not align with the
expected behavior. This could arise from errors in the implementation, errors with libraries,
text parsing, or in the summarization step.
Solution: Again, frequent and extensive testing can solve this issue. Testing with a variety of
PDF files and manually verifying the output and making changes as needed.
```
```
Compatibility Issues: The software may have compatibility problems, such as being unable to
run on certain platforms that have different JVM versions or different versions of libraries that
the software depends on.
Solution: If necessary, a warning will be included with the software stating the JVM versions
that are compatible with the software and the library versions that are known to work. This will
be verified during our testing phase.
```
```
Non-Functional Issues
```
```
Modularity: The software could encounter issues where each class doesn’t have a clear and
well-defined responsibility. Two or more classes could be fulfilling the same purpose which
would cause unnecessary bloat and confusion.
Solution: Clearly outline what each class’s responsibilities are and adhere to the outline as
closely as possible. Frequent code reviews and discussions can solve this problem.
```

**Class Inter-dependencies:** Classes could depend too closely on each other which could result
in problems when changes are made to one class but not the other.
**Solution:** Make the program more modular and each method should only have a few
responsibilities. When changes are made it'll be easier to understand where the errors come
from.

**Error Handling:** Not all possible errors could be accounted for resulting in unwanted
behaviour.
**Solution:** Identify potential points of failure through frequent and thorough testing. Making
many test cases can allow us to identify and handle all errors.

**Scalability and Performance:** There could be issues with processing large research papers or
handling a large number of slides. These issues could come from inefficient algorithms when
summarizing or creating slides or by not setting a PDF size limit.
**Solution:** Testing with large PDFs and creating large PPT files. Optimizing necessary
algorithms to improve performance. Setting a PDF size limit or a PPT slide limit can also be a
solution.

**Data Persistence and Storage during Execution:** Our program could allocate more space
than it actually uses, resulting in overuse of system resources. Furthermore, our system could
not properly dispose of data that is no longer necessary after the PPT file has been created.
**Solution:** Keeping a close eye on the resources our software consumes during and after
execution can help us identify this problem. Carefully testing each class or library and
analyzing points where this can be a problem is also important.
