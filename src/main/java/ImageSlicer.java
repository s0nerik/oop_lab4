import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ImageSlicer {

    private final File rootFolder;

    public ImageSlicer(File rootFolder) {
        this.rootFolder = rootFolder;
    }

    private List<File> getImageFiles() {
        List<File> images = new ArrayList<>();
        File[] rootFiles = rootFolder.listFiles();
        for (File file : rootFiles) {
            if (file.getName().matches("[^\\.]+\\.((jpg)|(jpeg))")) {
                images.add(file);
            }
        }
        return images;
    }

    private void sliceAll(List<File> files) {
        for (File file : files) {
            try {
                BufferedImage image = ImageIO.read(file);

                int halfWidth = image.getWidth() / 2;

                BufferedImage leftImage = new BufferedImage(halfWidth, image.getHeight(), image.getType());
                BufferedImage rightImage = new BufferedImage(halfWidth, image.getHeight(), image.getType());

                Graphics leftGraphics = leftImage.getGraphics();
                Graphics rightGraphics = rightImage.getGraphics();

                leftGraphics.drawImage(image,
                        0, 0, halfWidth, image.getHeight(),
                        0, 0, halfWidth, image.getHeight(),
                        null);

                rightGraphics.drawImage(image,
                        0, 0, halfWidth, image.getHeight(),
                        halfWidth, 0, image.getWidth(), image.getHeight(),
                        null);

                String truncatedName = file.getName().replaceFirst("[\\.][^\\.]+$", "");
                File leftFile = new File(rootFolder, truncatedName + "-left.jpeg");
                File rightFile = new File(rootFolder, truncatedName + "-right.jpeg");

                leftFile.createNewFile();
                rightFile.createNewFile();

                ImageIO.write(leftImage, "jpg", leftFile);
                ImageIO.write(rightImage, "jpg", rightFile);

                leftGraphics.dispose();
                rightGraphics.dispose();

            } catch (IOException ignore) {}
        }
    }

    public void run() {
        sliceAll(getImageFiles());
    }

}
