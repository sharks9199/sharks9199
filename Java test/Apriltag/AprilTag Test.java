import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.Videoio;
import apriltag.*;
import java.util.List;

public class AprilTagRecognition {
    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        VideoCapture capture = new VideoCapture(0);
        capture.set(Videoio.CAP_PROP_FRAME_WIDTH, 640);
        capture.set(Videoio.CAP_PROP_FRAME_HEIGHT, 480);
        Mat frame = new Mat();
        AprilTagDetector detector = new AprilTagDetector();
        while (capture.read(frame)) {
            List<AprilTagDetection> detections = detector.detect(frame);
            for (AprilTagDetection detection : detections) {
                detection.drawOnImage(frame);
            }
            Core.putText(frame, "Number of tags detected: " + detections.size(), new Point(10, 30), Core.FONT_HERSHEY_SIMPLEX, 0.5, new Scalar(0, 0, 255), 2);
            imshow("AprilTag Detection", frame);
            if (waitKey(1) == 27) {
                break;
            }
        }
    }
}
